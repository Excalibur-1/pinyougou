package com.pinyougou.mapper;

import com.pinyougou.pojo.TbProvinces;
import com.pinyougou.pojo.TbProvincesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 省份
 *
 * @author gxl
 */
public interface TbProvincesMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbProvincesExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbProvincesExample example);

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
  int insert(TbProvinces record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbProvinces record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbProvinces>
   */
  List<TbProvinces> selectByExample(TbProvincesExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbProvinces
   */
  TbProvinces selectByPrimaryKey(Integer id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbProvinces record, @Param("example") TbProvincesExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbProvinces record, @Param("example") TbProvincesExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbProvinces record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbProvinces record);
}