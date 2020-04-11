package com.pinyougou.mapper;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 规格
 *
 * @author gxl
 */
public interface TbSpecificationMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbSpecificationExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbSpecificationExample example);

  /**
   * 删除
   *
   * @param id id
   * @return int
   */
  int deleteByPrimaryKey(Long id);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insert(TbSpecification record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbSpecification record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbSpecification>
   */
  List<TbSpecification> selectByExample(TbSpecificationExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbSpecification
   */
  TbSpecification selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbSpecification record, @Param("example") TbSpecificationExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbSpecification record, @Param("example") TbSpecificationExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbSpecification record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbSpecification record);

  /**
   * 查询选项
   *
   * @return List<Map>
   */
  List<Map> selectOptionList();
}