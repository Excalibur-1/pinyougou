package com.pinyougou.pojo;

import java.io.Serializable;

/**
 * 省份
 *
 * @author gxl
 */
public class TbProvinces implements Serializable {
  private Integer id;

  private String provinceId;

  private String province;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId == null ? null : provinceId.trim();
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province == null ? null : province.trim();
  }
}