package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.Arrays;
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

  private final Destination queueSolrDestination;

  private final Destination queueSolrDeleteDestination;

  private final Destination topicPageDestination;

  private final Destination topicPageDeleteDestination;

  @Autowired
  public GoodsController(JmsTemplate jmsTemplate,
                         Destination queueSolrDestination,
                         Destination queueSolrDeleteDestination,
                         Destination topicPageDestination,
                         Destination topicPageDeleteDestination) {
    this.jmsTemplate = jmsTemplate;
    this.queueSolrDestination = queueSolrDestination;
    this.queueSolrDeleteDestination = queueSolrDeleteDestination;
    this.topicPageDestination = topicPageDestination;
    this.topicPageDeleteDestination = topicPageDeleteDestination;
  }

  /**
   * 返回全部列表
   *
   * @return List<TbGoods>
   */
  @RequestMapping("/findAll")
  public List<TbGoods> findAll() {
    return goodsService.findAll();
  }

  /**
   * 返回全部列表
   *
   * @return PageResult
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return goodsService.findPage(page, rows);
  }

  /**
   * 获取实体
   *
   * @param id id
   * @return Goods
   */
  @RequestMapping("/findOne")
  public Goods findOne(@RequestParam Long id) {
    return goodsService.findOne(id);
  }

  /**
   * 批量删除
   *
   * @param ids ids
   * @return Result
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
   *
   * @param page 分页
   * @param rows 行数
   * @return PageResult
   */
  @RequestMapping("/search")
  public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
    return goodsService.findPage(goods, page, rows);
  }

  /**
   * 审核商品状态
   *
   * @param ids    ids
   * @param status 状态
   * @return Result
   */
  @RequestMapping("/updateStatus")
  public Result updateStatus(@RequestParam Long[] ids, @RequestParam String status) {
    try {
      goodsService.updateStatus(ids, status);

      //发送保存数据消息
      sendSaveActiveMqMessage(ids, status);
      return new Result(TRUE, AUDIT_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, AUDIT_FAIL);
    }
  }

  /**
   * 发送保存数据消息到ActiveMq
   *
   * @param ids    商品id列表
   * @param status 审核状态
   */
  private void sendSaveActiveMqMessage(Long[] ids, String status) {
    //如果是审核通过
    String auditPass = "1";
    if (auditPass.equals(status)) {
      //导入到索引库
      //得到需要导入的SKU列表
      List<TbItem> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);

      //转换为json传输
      final String jsonString = JSON.toJSONString(itemList);

      //导入到商品数据solr-消息
      jmsTemplate.send(queueSolrDestination, session -> session.createTextMessage(jsonString));

      //生成商品详细页-消息
      Arrays.asList(ids).parallelStream()
          .forEach(i -> jmsTemplate.send(topicPageDestination,
              session -> session.createTextMessage(i.toString())));
    }
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