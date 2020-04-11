package com.pinyougou.mapper;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品
 *
 * @author gxl
 */
public interface TbItemMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbItemExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbItemExample example);

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
  int insert(TbItem record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbItem record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbItem>
   */
  List<TbItem> selectByExample(TbItemExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbItem
   */
  TbItem selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbItem record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbItem record);
}