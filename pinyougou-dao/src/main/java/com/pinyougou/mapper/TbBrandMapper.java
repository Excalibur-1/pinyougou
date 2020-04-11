package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author gxl
 */
public interface TbBrandMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbBrandExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbBrandExample example);

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
  int insert(TbBrand record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbBrand record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbBrand>
   */
  List<TbBrand> selectByExample(TbBrandExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbBrand
   */
  TbBrand selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbBrand record, @Param("example") TbBrandExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbBrand record, @Param("example") TbBrandExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbBrand record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbBrand record);

  /**
   * 查询
   *
   * @return List<Map>
   */
  List<Map> selectOptionList();
}