package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌接口
 *
 * @author gxl
 */
public interface BrandService {

  /**
   * 查询所有品牌
   *
   * @return List<TbBrand>
   */
  List<TbBrand> findAll();

  /**
   * 品牌列表分页
   *
   * @param pageNum  当前页
   * @param pageSize 每页记录数
   * @return 分页数据对象
   */
  PageResult findPage(int pageNum, int pageSize);

  /**
   * 增加品牌
   *
   * @param brand 品牌实体
   * @throws Exception Exception
   */
  void add(TbBrand brand) throws Exception;

  /**
   * 根据id查询品牌实体
   *
   * @param id 品牌id
   * @return 品牌实体
   */
  TbBrand findOne(Long id);

  /**
   * 修改品牌
   *
   * @param brand 品牌实体
   * @throws Exception Exception
   */
  void update(TbBrand brand) throws Exception;

  /**
   * 批量删除品牌
   *
   * @param ids id数组
   */
  void delete(Long[] ids);

  /**
   * 品牌列表条件查询
   *
   * @param brand    品牌实体
   * @param pageNum  当前页
   * @param pageSize 每页记录数
   * @return 分页数据对象
   */
  PageResult findPage(TbBrand brand, int pageNum, int pageSize);

  /**
   * 返回下拉列表数据
   *
   * @return Map
   */
  List<Map> selectOptionList();

}
