package com.pinyougou.pojo;

import java.io.Serializable;

/**
 * 城市实体类
 *
 * @author gxl
 */
public class TbCities implements Serializable {
  private Integer id;

  private String cityId;

  private String city;

  private String provinceId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId == null ? null : cityId.trim();
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city == null ? null : city.trim();
  }

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId == null ? null : provinceId.trim();
  }
}