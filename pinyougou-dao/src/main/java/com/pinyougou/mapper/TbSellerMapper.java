package com.pinyougou.mapper;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商家
 *
 * @author gxl
 */
public interface TbSellerMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbSellerExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbSellerExample example);

  /**
   * 删除
   *
   * @param sellerId sellerId
   * @return int
   */
  int deleteByPrimaryKey(String sellerId);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insert(TbSeller record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbSeller record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbSeller>
   */
  List<TbSeller> selectByExample(TbSellerExample example);

  /**
   * 查询
   *
   * @param sellerId sellerId
   * @return TbSeller
   */
  TbSeller selectByPrimaryKey(String sellerId);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbSeller record, @Param("example") TbSellerExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbSeller record, @Param("example") TbSellerExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbSeller record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbSeller record);
}