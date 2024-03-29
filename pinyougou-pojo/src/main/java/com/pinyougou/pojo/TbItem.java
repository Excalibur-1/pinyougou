package com.pinyougou.pojo;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单商品实体类
 *
 * @author gxl
 */
public class TbItem implements Serializable {

  @Field
  private Long id;

  @Field("item_title")
  private String title;

  private String sellPoint;

  @Field("item_price")
  private BigDecimal price;

  private Integer stockCount;

  private Integer num;

  private String barcode;

  @Field("item_image")
  private String image;

  private Long categoryId;

  private String status;

  private Date createTime;

  @Field("item_update_time")
  private Date updateTime;

  private String itemSn;

  private BigDecimal costPrice;

  private BigDecimal marketPrice;

  private String isDefault;

  @Field("item_goods_id")
  private Long goodsId;

  private String sellerId;

  private String cartThumbnail;

  @Field("item_category")
  private String category;

  @Field("item_brand")
  private String brand;

  private String spec;

  @Field("item_seller")
  private String seller;

  @Dynamic
  @Field("item_spec_*")
  private Map<String, String> specMap;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public String getSellPoint() {
    return sellPoint;
  }

  public void setSellPoint(String sellPoint) {
    this.sellPoint = sellPoint == null ? null : sellPoint.trim();
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getStockCount() {
    return stockCount;
  }

  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode == null ? null : barcode.trim();
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image == null ? null : image.trim();
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getItemSn() {
    return itemSn;
  }

  public void setItemSn(String itemSn) {
    this.itemSn = itemSn == null ? null : itemSn.trim();
  }

  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public String getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(String isDefault) {
    this.isDefault = isDefault == null ? null : isDefault.trim();
  }

  public Long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Long goodsId) {
    this.goodsId = goodsId;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId == null ? null : sellerId.trim();
  }

  public String getCartThumbnail() {
    return cartThumbnail;
  }

  public void setCartThumbnail(String cartThumbnail) {
    this.cartThumbnail = cartThumbnail == null ? null : cartThumbnail.trim();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category == null ? null : category.trim();
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand == null ? null : brand.trim();
  }

  public String getSpec() {
    return spec;
  }

  public void setSpec(String spec) {
    this.spec = spec == null ? null : spec.trim();
  }

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller == null ? null : seller.trim();
  }

  public Map<String, String> getSpecMap() {
    return specMap;
  }

  public void setSpecMap(Map<String, String> specMap) {
    this.specMap = specMap;
  }
}