package com.pinyougou.content.service;

import com.pinyougou.pojo.TbContentCategory;
import entity.PageResult;

import java.util.List;

/**
 * 广告服务层接口
 *
 * @author gxl
 */
public interface ContentCategoryService {

  /**
   * 返回全部列表
   *
   * @return List<TbContentCategory>
   */
  List<TbContentCategory> findAll();

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
   * @param contentCategory 广告分类实体
   */
  void add(TbContentCategory contentCategory);

  /**
   * 修改
   *
   * @param contentCategory 广告分类实体
   */
  void update(TbContentCategory contentCategory);

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return TbContentCategory
   */
  TbContentCategory findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids id列表
   */
  void delete(Long[] ids);

  /**
   * 分页
   *
   * @param contentCategory 广告分类实体
   * @param pageNum         当前页码
   * @param pageSize        每页记录数
   * @return PageResult
   */
  PageResult findPage(TbContentCategory contentCategory, int pageNum, int pageSize);

}
