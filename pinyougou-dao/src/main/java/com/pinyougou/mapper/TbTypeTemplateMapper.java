package com.pinyougou.mapper;

import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 模板
 *
 * @author gxl
 */
public interface TbTypeTemplateMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbTypeTemplateExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbTypeTemplateExample example);

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
  int insert(TbTypeTemplate record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbTypeTemplate record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbTypeTemplate>
   */
  List<TbTypeTemplate> selectByExample(TbTypeTemplateExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbTypeTemplate
   */
  TbTypeTemplate selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbTypeTemplate record, @Param("example") TbTypeTemplateExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbTypeTemplate record, @Param("example") TbTypeTemplateExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbTypeTemplate record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbTypeTemplate record);

  /**
   * 查询规格选项
   *
   * @return List<Map>
   */
  List<Map> selectOptionList();
}