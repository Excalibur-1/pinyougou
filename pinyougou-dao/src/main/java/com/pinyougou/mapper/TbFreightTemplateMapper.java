package com.pinyougou.mapper;

import com.pinyougou.pojo.TbFreightTemplate;
import com.pinyougou.pojo.TbFreightTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发货模板
 *
 * @author gxl
 */
public interface TbFreightTemplateMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbFreightTemplateExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbFreightTemplateExample example);

  /**
   * 删除
   *
   * @param id id
   * @return int
   */
  int deleteByPrimaryKey(Long id);

  /**
   * 插入
   *
   * @param record record
   * @return int
   */
  int insert(TbFreightTemplate record);

  /**
   * 插入
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbFreightTemplate record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbFreightTemplate>
   */
  List<TbFreightTemplate> selectByExample(TbFreightTemplateExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbFreightTemplate
   */
  TbFreightTemplate selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbFreightTemplate record, @Param("example") TbFreightTemplateExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbFreightTemplate record, @Param("example") TbFreightTemplateExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbFreightTemplate record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbFreightTemplate record);
}