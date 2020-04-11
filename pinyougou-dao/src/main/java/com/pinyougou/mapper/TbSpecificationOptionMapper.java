package com.pinyougou.mapper;

import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 规格选项
 *
 * @author gxl
 */
public interface TbSpecificationOptionMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbSpecificationOptionExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbSpecificationOptionExample example);

  /**
   * 删除
   *
   * @param id id
   * @return int
   */
  int deleteByPrimaryKey(Long id);

  /**
   * 新增
   *
   * @param record record
   * @return int
   */
  int insert(TbSpecificationOption record);

  /**
   * 新增
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbSpecificationOption record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbSpecificationOption>
   */
  List<TbSpecificationOption> selectByExample(TbSpecificationOptionExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbSpecificationOption
   */
  TbSpecificationOption selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbSpecificationOption record, @Param("example") TbSpecificationOptionExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbSpecificationOption record, @Param("example") TbSpecificationOptionExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbSpecificationOption record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbSpecificationOption record);
}