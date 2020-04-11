package com.pinyougou.mapper;

import com.pinyougou.pojo.TbPayLog;
import com.pinyougou.pojo.TbPayLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付日志
 *
 * @author gxl
 */
public interface TbPayLogMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbPayLogExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbPayLogExample example);

  /**
   * 删除
   *
   * @param outTradeNo outTradeNo
   * @return int
   */
  int deleteByPrimaryKey(String outTradeNo);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insert(TbPayLog record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbPayLog record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbPayLog>
   */
  List<TbPayLog> selectByExample(TbPayLogExample example);

  /**
   * 查询
   *
   * @param outTradeNo outTradeNo
   * @return TbPayLog
   */
  TbPayLog selectByPrimaryKey(String outTradeNo);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbPayLog record, @Param("example") TbPayLogExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbPayLog record, @Param("example") TbPayLogExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbPayLog record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbPayLog record);
}