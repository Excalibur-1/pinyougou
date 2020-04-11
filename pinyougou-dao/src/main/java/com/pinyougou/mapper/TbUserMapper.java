package com.pinyougou.mapper;

import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.TbUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户
 *
 * @author gxl
 */
public interface TbUserMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbUserExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbUserExample example);

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
  int insert(TbUser record);

  /**
   * 天机
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbUser record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbUser>
   */
  List<TbUser> selectByExample(TbUserExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbUser
   */
  TbUser selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbUser record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbUser record);
}