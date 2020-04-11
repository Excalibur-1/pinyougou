package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * 商家controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/seller")
public class SellerController implements Constants {

  @Reference
  private SellerService baseService;

  /**
   * 返回全部列表
   */
  @RequestMapping("/findAll")
  public List<TbSeller> findAll() {
    return baseService.findAll();
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return baseService.findPage(page, rows);
  }

  /**
   * 增加
   */
  @RequestMapping("/add")
  public Result add(@RequestBody TbSeller seller) {
    try {
      baseService.add(seller);
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
  public Result update(@RequestBody TbSeller seller) {
    try {
      baseService.update(seller);
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
  public TbSeller findOne(String id) {
    return baseService.findOne(id);
  }

  /**
   * 批量删除
   */
  @RequestMapping("/delete")
  public Result delete(String[] ids) {
    try {
      baseService.delete(ids);
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
  public PageResult search(@RequestBody TbSeller seller, int page, int rows) {
    return baseService.findPage(seller, page, rows);
  }

  @RequestMapping("/updateStatus")
  public Result updateStatus(String sellerId, String status) {
    try {
      baseService.updateStatus(sellerId, status);
      return new Result(true, "审核状态成功");
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false, "审核状态失败");
    }
  }

}
