package com.pinyougou.mapper;

import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单
 *
 * @author gxl
 */
public interface TbOrderMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbOrderExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbOrderExample example);

  /**
   * 删除
   *
   * @param orderId orderId
   * @return int
   */
  int deleteByPrimaryKey(Long orderId);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insert(TbOrder record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbOrder record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbOrder>
   */
  List<TbOrder> selectByExample(TbOrderExample example);

  /**
   * 查询
   *
   * @param orderId orderId
   * @return TbOrder
   */
  TbOrder selectByPrimaryKey(Long orderId);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbOrder record, @Param("example") TbOrderExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbOrder record, @Param("example") TbOrderExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbOrder record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbOrder record);
}