package com.pinyougou.mapper;

import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbGoodsDescExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品详情
 *
 * @author gxl
 */
public interface TbGoodsDescMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbGoodsDescExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbGoodsDescExample example);

  /**
   * 删除
   *
   * @param goodsId goodsId
   * @return int
   */
  int deleteByPrimaryKey(Long goodsId);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insert(TbGoodsDesc record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbGoodsDesc record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbGoodsDesc>
   */
  List<TbGoodsDesc> selectByExample(TbGoodsDescExample example);

  /**
   * 查询
   *
   * @param goodsId goodsId
   * @return TbGoodsDesc
   */
  TbGoodsDesc selectByPrimaryKey(Long goodsId);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbGoodsDesc record, @Param("example") TbGoodsDescExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbGoodsDesc record, @Param("example") TbGoodsDescExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbGoodsDesc record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbGoodsDesc record);
}