package com.pinyougou.search.service;

import com.pinyougou.pojo.TbItem;

import java.util.List;
import java.util.Map;

/**
 * @author gxl
 */
public interface ItemSearchService {

  /**
   * 搜索方法
   *
   * @param searchMap 搜索条件
   * @return 搜索结果
   */
  Map<String, Object> search(Map<String, Object> searchMap);

  /**
   * 导入列表
   *
   * @param list 数据列表
   */
  void importList(List<TbItem> list);

  /**
   * 删除商品列表
   *
   * @param goodsIds (SPU)
   */
  void deleteByGoodsIds(List goodsIds);

}
