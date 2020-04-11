package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController implements Constants {

  @Reference
  private TypeTemplateService typeTemplateService;

  /**
   * 返回全部列表
   */
  @RequestMapping("/findAll")
  public List<TbTypeTemplate> findAll() {
    return typeTemplateService.findAll();
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return typeTemplateService.findPage(page, rows);
  }

  /**
   * 获取实体
   */
  @RequestMapping("/findOne")
  public TbTypeTemplate findOne(Long id) {
    return typeTemplateService.findOne(id);
  }

  /**
   * 查询+分页
   */
  @RequestMapping("/search")
  public PageResult search(@RequestBody TbTypeTemplate typeTemplate, int page, int rows) {
    return typeTemplateService.findPage(typeTemplate, page, rows);
  }

  /**
   * 查询分类模板列表（下拉框）
   *
   * @return List<Map>
   */
  @RequestMapping("/selectOptionList")
  public List<Map> selectOptionList() {
    return typeTemplateService.selectOptionList();
  }

  /**
   * 查询规格列表
   *
   * @param id 规格id
   * @return List<Map>
   */
  @RequestMapping("/selectSpecList")
  public List<Map> selectSpecList(Long id) {
    return typeTemplateService.selectSpecList(id);
  }

}
