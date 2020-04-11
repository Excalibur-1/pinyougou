package com.pinyougou.content.service;

import com.pinyougou.pojo.TbContent;
import entity.PageResult;

import java.util.List;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface ContentService {

  /**
   * 返回全部列表
   *
   * @return List<TbContent>
   */
  List<TbContent> findAll();

  /**
   * 返回分页列表
   *
   * @param pageNum  分页页码
   * @param pageSize 分页大小
   * @return PageResult
   */
  PageResult findPage(int pageNum, int pageSize);

  /**
   * 增加
   *
   * @param content 广告实体
   */
  void add(TbContent content);

  /**
   * 修改
   *
   * @param content 广告实体
   */
  void update(TbContent content);

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return TbContent
   */
  TbContent findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids id列表
   */
  void delete(Long[] ids);

  /**
   * 分页
   *
   * @param content  广告实体
   * @param pageNum  当前页 码
   * @param pageSize 每页记录数
   * @return PageResult
   */
  PageResult findPage(TbContent content, int pageNum, int pageSize);

  /**
   * 根据广告分类ID查询广告
   *
   * @param categoryId 分类id
   * @return List<TbContent>
   */
  List<TbContent> findByCategoryId(Long categoryId);

}
