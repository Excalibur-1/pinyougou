package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/goods")
public class GoodsController implements Constants {

  @Reference
  private GoodsService goodsService;

  private final JmsTemplate jmsTemplate;

  private final Destination queueSolrDeleteDestination;

  private final Destination topicPageDeleteDestination;

  @Autowired
  public GoodsController(JmsTemplate jmsTemplate,
                         Destination queueSolrDeleteDestination,
                         Destination topicPageDeleteDestination) {
    this.jmsTemplate = jmsTemplate;
    this.queueSolrDeleteDestination = queueSolrDeleteDestination;
    this.topicPageDeleteDestination = topicPageDeleteDestination;
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findAll")
  public List<TbGoods> findAll() {
    return goodsService.findAll();
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return goodsService.findPage(page, rows);
  }

  /**
   * 增加
   */
  @RequestMapping("/add")
  public Result add(@RequestBody Goods goods) {
    try {
      // 获得商家信息:
      String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

      goods.getGoods().setSellerId(sellerId);

      goodsService.add(goods);
      return new Result(TRUE, ADD_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, ADD_FAIL);
    }
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  public Result update(@RequestBody Goods goods) {
    // 获得商家信息:
    String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

    Goods goods2 = goodsService.findOne(goods.getGoods().getId());
    if (!sellerId.equals(goods2.getGoods().getSellerId()) || !sellerId.equals(goods.getGoods().getSellerId())) {
      return new Result(FALSE, ILLEGAL_OPERATION);
    }

    try {
      goodsService.update(goods);
      return new Result(TRUE, UPDATE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, UPDATE_FAIL);
    }
  }

  /**
   * 获取实体
   */
  @RequestMapping("/findOne")
  public Goods findOne(Long id) {
    return goodsService.findOne(id);
  }

  /**
   * 批量删除
   */
  @RequestMapping("/delete")
  public Result delete(@RequestParam Long[] ids) {
    try {
      goodsService.delete(ids);

      //发送删除数据消息
      sendDeleteActiveMqMessage(ids);
      return new Result(TRUE, DELETE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, DELETE_FAIL);
    }
  }

  /**
   * 查询+分页
   */
  @RequestMapping("/search")
  public PageResult search(@RequestBody TbGoods goods, int page, int rows) {

    String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
    goods.setSellerId(sellerId);

    return goodsService.findPage(goods, page, rows);
  }

  /**
   * 发送删除数据消息到ActiveMq
   *
   * @param ids 商品id列表
   */
  private void sendDeleteActiveMqMessage(Long[] ids) {
    //从索引库中删除
    jmsTemplate.send(queueSolrDeleteDestination, session -> session.createObjectMessage(ids));

    //删除每个服务器上的商品详细页
    jmsTemplate.send(topicPageDeleteDestination, session -> session.createObjectMessage(ids));
  }

}
