package com.pinyougou.mapper;

import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品类目
 *
 * @author gxl
 */
public interface TbItemCatMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbItemCatExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbItemCatExample example);

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
  int insert(TbItemCat record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbItemCat record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbItemCat>
   */
  List<TbItemCat> selectByExample(TbItemCatExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbItemCat
   */
  TbItemCat selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbItemCat record, @Param("example") TbItemCatExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbItemCat record, @Param("example") TbItemCatExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbItemCat record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbItemCat record);

  /**
   * 查询
   *
   * @param parentId parentId
   * @return List<TbItemCat>
   */
  List<TbItemCat> selectByParentId(Long parentId);
}