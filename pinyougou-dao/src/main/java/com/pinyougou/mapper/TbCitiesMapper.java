package com.pinyougou.mapper;

import com.pinyougou.pojo.TbCities;
import com.pinyougou.pojo.TbCitiesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市
 *
 * @author gxl
 */
public interface TbCitiesMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbCitiesExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbCitiesExample example);

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
  int insert(TbCities record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbCities record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbCities>
   */
  List<TbCities> selectByExample(TbCitiesExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbCities
   */
  TbCities selectByPrimaryKey(Integer id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbCities record, @Param("example") TbCitiesExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbCities record, @Param("example") TbCitiesExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbCities record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbCities record);
}