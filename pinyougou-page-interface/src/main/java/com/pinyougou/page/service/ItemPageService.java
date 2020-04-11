package com.pinyougou.page.service;

/**
 * 商品详情页面生成接口
 *
 * @author gxl
 */
public interface ItemPageService {

  /**
   * 生成商品详细页
   *
   * @param goodsId 商品id
   * @return boolean
   */
  boolean genItemHtml(Long goodsId);

  /**
   * 删除商品详细页
   *
   * @param goodsIds 商品id列表
   * @return boolean
   */
  boolean deleteItemHtml(Long[] goodsIds);

}
