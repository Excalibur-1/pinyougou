package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbItemCat;

/**
 * 商品模板
 *
 * @author gxl
 */
public class ItemCat extends TbItemCat {

  /**
   * 类型模板名称
   */
  private String typeTemplateName;

  public String getTypeTemplateName() {
    return typeTemplateName;
  }

  public void setTypeTemplateName(String typeTemplateName) {
    this.typeTemplateName = typeTemplateName;
  }
}
