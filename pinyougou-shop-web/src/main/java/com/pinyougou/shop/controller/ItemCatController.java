package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojogroup.ItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * 商品分类controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController implements Constants {

  @Reference
  private ItemCatService itemCatService;

  @Reference
  private TypeTemplateService typeTemplateService;

  /**
   * 返回全部列表
   */
  @RequestMapping("/findAll")
  public List<TbItemCat> findAll() {
    return itemCatService.findAll();
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return itemCatService.findPage(page, rows);
  }

  /**
   * 增加
   */
  @RequestMapping("/add")
  public Result add(@RequestBody TbItemCat itemCat) {
    try {
      itemCatService.add(itemCat);
      return new Result(TRUE, ADD_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, ADD_FAIL);
    }
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  public Result update(@RequestBody TbItemCat itemCat) {
    try {
      itemCatService.update(itemCat);
      return new Result(TRUE, UPDATE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, UPDATE_FAIL);
    }
  }

  /**
   * 获取实体
   */
  @RequestMapping("/findOne")
  public ItemCat findOne(Long id) {
    TbItemCat tbItemCat = itemCatService.findOne(id);
    ItemCat itemCat = new ItemCat();
    TbTypeTemplate typeTemplate = typeTemplateService.findOne(tbItemCat.getTypeId());
    BeanUtils.copyProperties(tbItemCat, itemCat);
    itemCat.setTypeTemplateName(typeTemplate.getName());
    return itemCat;
  }

  /**
   * 批量删除
   */
  @RequestMapping("/delete")
  public Result delete(Long[] ids) {
    try {
      itemCatService.delete(ids);
      return new Result(TRUE, DELETE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, DELETE_FAIL);
    }
  }

  /**
   * 查询+分页
   */
  @RequestMapping("/search")
  public PageResult search(@RequestBody TbItemCat itemCat, int page, int rows) {
    return itemCatService.findPage(itemCat, page, rows);
  }

  /**
   * 根据上级id查询商品分类列表
   *
   * @param parentId 上级id
   * @return 分类列表
   */
  @RequestMapping("/findByParentId")
  public List<ItemCat> findByParentId(Long parentId) {
    List<TbItemCat> tbItemCatList = itemCatService.findByParentId(parentId);
    List<ItemCat> itemCatList = new ArrayList<>();
    tbItemCatList.parallelStream().forEach(i -> {
      ItemCat itemCat = new ItemCat();
      TbTypeTemplate typeTemplate = typeTemplateService.findOne(i.getTypeId());
      if (typeTemplate != null) {
        BeanUtils.copyProperties(i, itemCat);
        itemCat.setTypeTemplateName(typeTemplate.getName());
        itemCatList.add(itemCat);
      }
    });
    return itemCatList;
  }
}
