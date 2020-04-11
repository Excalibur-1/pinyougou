package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import entity.PageResult;

import java.util.List;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface GoodsService {

  /**
   * 返回全部列表
   *
   * @return List<TbGoods>
   */
  List<TbGoods> findAll();

  /**
   * 返回分页列表
   *
   * @param pageNum  页码
   * @param pageSize 分页大小
   * @return PageResult
   */
  PageResult findPage(int pageNum, int pageSize);

  /**
   * 增加
   *
   * @param goods 商品对象
   */
  void add(Goods goods);

  /**
   * 修改
   *
   * @param goods 商品对象
   */
  void update(Goods goods);

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return Goods
   */
  Goods findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids ids
   * @throws Exception Exception
   */
  void delete(Long[] ids) throws Exception;

  /**
   * 分页
   *
   * @param pageNum  页码
   * @param pageSize 分页大小
   * @param goods    商品
   * @return PageResult
   */
  PageResult findPage(TbGoods goods, int pageNum, int pageSize);

  /**
   * 更新状态
   *
   * @param ids    ids
   * @param status 状态
   * @throws Exception Exception
   */
  void updateStatus(Long[] ids, String status) throws Exception;

  /**
   * 根据SPU的ID集合查询SKU列表
   *
   * @param goodsIds 商品id列表
   * @param status   状态
   * @return List<TbItem>
   */
  List<TbItem> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status);
}
