package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * 商品的组合实体类
 *
 * @author gxl
 */
public class Goods implements Serializable {

  /**
   * 商品信息
   */
  private TbGoods goods;

  /**
   * 商品扩展信息
   */
  private TbGoodsDesc goodsDesc;

  /**
   * SKU的列表信息
   */
  private List<TbItem> itemList;

  public TbGoods getGoods() {
    return goods;
  }

  public void setGoods(TbGoods goods) {
    this.goods = goods;
  }

  public TbGoodsDesc getGoodsDesc() {
    return goodsDesc;
  }

  public void setGoodsDesc(TbGoodsDesc goodsDesc) {
    this.goodsDesc = goodsDesc;
  }

  public List<TbItem> getItemList() {
    return itemList;
  }

  public void setItemList(List<TbItem> itemList) {
    this.itemList = itemList;
  }
}
