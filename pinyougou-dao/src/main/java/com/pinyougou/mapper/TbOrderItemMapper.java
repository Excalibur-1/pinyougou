package com.pinyougou.mapper;

import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.TbOrderItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单详情
 *
 * @author gxl
 */
public interface TbOrderItemMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbOrderItemExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbOrderItemExample example);

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
  int insert(TbOrderItem record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbOrderItem record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbOrderItem>
   */
  List<TbOrderItem> selectByExample(TbOrderItemExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbOrderItem
   */
  TbOrderItem selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbOrderItem record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbOrderItem record);
}