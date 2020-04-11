package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSeller;
import entity.PageResult;

import java.util.List;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface SellerService {

  /**
   * 返回全部列表
   *
   * @return List<TbSeller>
   */
  List<TbSeller> findAll();

  /**
   * 返回分页列表
   *
   * @param pageNum  页码
   * @param pageSize 页大小
   * @return PageResult
   */
  PageResult findPage(int pageNum, int pageSize);

  /**
   * 增加
   *
   * @param seller 商家实体
   */
  void add(TbSeller seller);

  /**
   * 修改
   *
   * @param seller 商家实体
   */
  void update(TbSeller seller);

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return TbSeller
   */
  TbSeller findOne(String id);

  /**
   * 批量删除
   *
   * @param ids id列表
   * @throws Exception Exception
   */
  void delete(String[] ids) throws Exception;

  /**
   * 分页
   *
   * @param seller   商家实体
   * @param pageNum  当前页 码
   * @param pageSize 每页记录数
   * @return PageResult
   */
  PageResult findPage(TbSeller seller, int pageNum, int pageSize);

  /**
   * 更新商家审核状态
   *
   * @param sellerId 商家id
   * @param status   状态
   */
  void updateStatus(String sellerId, String status);

}
