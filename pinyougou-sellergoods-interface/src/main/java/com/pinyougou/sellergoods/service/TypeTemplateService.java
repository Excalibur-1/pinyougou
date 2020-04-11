package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface TypeTemplateService {

  /**
   * 返回全部列表
   *
   * @return List<TbTypeTemplate>
   */
  List<TbTypeTemplate> findAll();

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
   * @param typeTemplate 模板
   * @throws Exception Exception
   */
  void add(TbTypeTemplate typeTemplate) throws Exception;

  /**
   * 修改
   *
   * @param typeTemplate 模板
   * @throws Exception Exception
   */
  void update(TbTypeTemplate typeTemplate) throws Exception;

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return TbTypeTemplate
   */
  TbTypeTemplate findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids id列表
   */
  void delete(Long[] ids);

  /**
   * 分页
   *
   * @param typeTemplate 模板实体
   * @param pageNum      页码
   * @param pageSize     页大小
   * @return PageResult
   */
  PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize);

  /**
   * 查询分类模板列表
   *
   * @return Map
   */
  List<Map> selectOptionList();

  /**
   * 根据模板id查询模板
   *
   * @param id 模板id
   * @return List<Map>
   */
  List<Map> selectSpecList(Long id);

}
