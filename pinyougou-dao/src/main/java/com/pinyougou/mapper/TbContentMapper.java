package com.pinyougou.mapper;

import com.pinyougou.pojo.TbContent;
import com.pinyougou.pojo.TbContentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内容
 *
 * @author gxl
 */
public interface TbContentMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbContentExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbContentExample example);

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
  int insert(TbContent record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbContent record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbContent>
   */
  List<TbContent> selectByExample(TbContentExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbContent
   */
  TbContent selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbContent record, @Param("example") TbContentExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbContent record, @Param("example") TbContentExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbContent record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbContent record);
}