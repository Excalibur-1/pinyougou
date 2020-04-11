package com.pinyougou.manager.controller;

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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController implements Constants {

  @Reference
  private TypeTemplateService baseService;

  /**
   * 返回全部列表
   */
  @RequestMapping("/findAll")
  public List<TbTypeTemplate> findAll() {
    return baseService.findAll();
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return baseService.findPage(page, rows);
  }

  /**
   * 增加
   */
  @RequestMapping("/add")
  public Result add(@RequestBody TbTypeTemplate typeTemplate) {
    try {
      baseService.add(typeTemplate);
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
  public Result update(@RequestBody TbTypeTemplate typeTemplate) {
    try {
      baseService.update(typeTemplate);
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
  public TbTypeTemplate findOne(Long id) {
    return baseService.findOne(id);
  }

  /**
   * 批量删除
   */
  @RequestMapping("/delete")
  public Result delete(Long[] ids) {
    try {
      baseService.delete(ids);
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
  public PageResult search(@RequestBody TbTypeTemplate typeTemplate, int page, int rows) {
    return baseService.findPage(typeTemplate, page, rows);
  }

  /**
   * 查询分类模板列表（下拉框）
   *
   * @return Map
   */
  @RequestMapping("/selectOptionList")
  public List<Map> selectOptionList() {
    return baseService.selectOptionList();
  }

  /**
   * 查询规格列表
   *
   * @param id 规格id
   * @return List<Map>
   */
  @RequestMapping("/selectSpecList")
  public List<Map> selectSpecList(Long id) {
    return baseService.selectSpecList(id);
  }

}
