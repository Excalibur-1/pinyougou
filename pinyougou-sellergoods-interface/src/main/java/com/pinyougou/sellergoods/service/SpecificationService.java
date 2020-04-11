package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface SpecificationService {

  /**
   * 返回全部列表
   *
   * @return List<TbSpecification>
   */
  List<TbSpecification> findAll();

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
   * @param specification 规格对象
   * @throws Exception Exception
   */
  void add(Specification specification) throws Exception;

  /**
   * 修改
   *
   * @param specification 规格对象
   * @throws Exception Exception
   */
  void update(Specification specification) throws Exception;

  /**
   * 根据ID获取实体
   *
   * @param id id
   * @return Specification
   */
  Specification findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids id列表
   */
  void delete(Long[] ids);

  /**
   * 分页
   *
   * @param specification 规格
   * @param pageNum       页码
   * @param pageSize      页大小
   * @return PageResult
   */
  PageResult findPage(TbSpecification specification, int pageNum, int pageSize);

  /**
   * 查询规格列表
   *
   * @return Map
   */
  List<Map> selectOptionList();

}
