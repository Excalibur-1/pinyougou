package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojogroup.ItemCat;
import entity.PageResult;

import java.util.List;

/**
 * 商品分类服务层接口
 *
 * @author gxl
 */
public interface ItemCatService {

  /**
   * 返回全部列表
   *
   * @return List<TbItemCat>
   */
  List<TbItemCat> findAll();

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
   * @param itemCat 商品分类实体
   */
  void add(TbItemCat itemCat);

  /**
   * 修改
   *
   * @param itemCat 商品分类实体
   */
  void update(TbItemCat itemCat);

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return TbItemCat
   */
  TbItemCat findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids id列表
   * @throws Exception Exception
   */
  void delete(Long[] ids) throws Exception;

  /**
   * 分页
   *
   * @param pageNum  当前页 码
   * @param pageSize 每页记录数
   * @param itemCat  商品分类实体
   * @return PageResult
   */
  PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize);

  /**
   * 根据父ID查询分类的方法
   *
   * @param parentId 父类id
   * @return List<TbItemCat>
   */
  List<TbItemCat> findByParentId(Long parentId);

}
