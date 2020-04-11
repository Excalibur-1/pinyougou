package com.pinyougou.mapper;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品
 *
 * @author gxl
 */
public interface TbGoodsMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbGoodsExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbGoodsExample example);

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
  int insert(TbGoods record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbGoods record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbGoods>
   */
  List<TbGoods> selectByExample(TbGoodsExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbGoods
   */
  TbGoods selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbGoods record, @Param("example") TbGoodsExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbGoods record, @Param("example") TbGoodsExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbGoods record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbGoods record);
}