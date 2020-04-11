package com.pinyougou.mapper;

import com.pinyougou.pojo.TbAreas;
import com.pinyougou.pojo.TbAreasExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行政区域
 *
 * @author gxl
 */
public interface TbAreasMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbAreasExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbAreasExample example);

  /**
   * 删除
   *
   * @param id id
   * @return int
   */
  int deleteByPrimaryKey(Integer id);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insert(TbAreas record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbAreas record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbAreas>
   */
  List<TbAreas> selectByExample(TbAreasExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbAreas
   */
  TbAreas selectByPrimaryKey(Integer id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbAreas record, @Param("example") TbAreasExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbAreas record, @Param("example") TbAreasExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbAreas record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbAreas record);
}