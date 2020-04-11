package com.pinyougou.mapper;

import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbAddressExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地址mapper
 *
 * @author gxl
 */
public interface TbAddressMapper {

  /**
   * 根据条件统计数据
   *
   * @param example 条件
   * @return int
   */
  int countByExample(TbAddressExample example);

  /**
   * 根据条件删除数据
   *
   * @param example 条件
   * @return int
   */
  int deleteByExample(TbAddressExample example);

  /**
   * 根据id删除数据
   *
   * @param id id
   * @return int
   */
  int deleteByPrimaryKey(Long id);

  /**
   * 添加数据
   *
   * @param record 地址对象
   * @return int
   */
  int insert(TbAddress record);

  /**
   * 添加数据（不为空）
   *
   * @param record 地址对象
   * @return int
   */
  int insertSelective(TbAddress record);

  /**
   * 根据条件查询数据
   *
   * @param example 查询条件
   * @return List<TbAddress>
   */
  List<TbAddress> selectByExample(TbAddressExample example);

  /**
   * 根据id查询地址
   *
   * @param id id
   * @return TbAddress
   */
  TbAddress selectByPrimaryKey(Long id);

  /**
   * 更新（不为空）
   *
   * @param record  地址对象
   * @param example 更新条件
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbAddress record, @Param("example") TbAddressExample example);

  /**
   * 更新
   *
   * @param record  地址对象
   * @param example 更新条件
   * @return int
   */
  int updateByExample(@Param("record") TbAddress record, @Param("example") TbAddressExample example);

  /**
   * 根据id更新（不为空）
   *
   * @param record 地址对象
   * @return int
   */
  int updateByPrimaryKeySelective(TbAddress record);

  /**
   * 根据id更新
   *
   * @param record 地址对象
   * @return int
   */
  int updateByPrimaryKey(TbAddress record);
}