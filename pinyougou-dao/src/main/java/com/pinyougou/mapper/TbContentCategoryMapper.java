package com.pinyougou.mapper;

import com.pinyougou.pojo.TbContentCategory;
import com.pinyougou.pojo.TbContentCategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内容分裂
 *
 * @author gxl
 */
public interface TbContentCategoryMapper {

  /**
   * 统计
   *
   * @param example example
   * @return int
   */
  int countByExample(TbContentCategoryExample example);

  /**
   * 删除
   *
   * @param example example
   * @return int
   */
  int deleteByExample(TbContentCategoryExample example);

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
  int insert(TbContentCategory record);

  /**
   * 添加
   *
   * @param record record
   * @return int
   */
  int insertSelective(TbContentCategory record);

  /**
   * 查询
   *
   * @param example example
   * @return List<TbContentCategory
            */
  List<TbContentCategory> selectByExample(TbContentCategoryExample example);

  /**
   * 查询
   *
   * @param id id
   * @return TbContentCategory
   */
  TbContentCategory selectByPrimaryKey(Long id);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExampleSelective(@Param("record") TbContentCategory record, @Param("example") TbContentCategoryExample example);

  /**
   * 更新
   *
   * @param record  record
   * @param example example
   * @return int
   */
  int updateByExample(@Param("record") TbContentCategory record, @Param("example") TbContentCategoryExample example);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKeySelective(TbContentCategory record);

  /**
   * 更新
   *
   * @param record record
   * @return int
   */
  int updateByPrimaryKey(TbContentCategory record);
}