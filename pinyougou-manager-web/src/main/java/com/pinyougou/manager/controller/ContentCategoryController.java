package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.pojo.TbContentCategory;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController implements Constants {

  @Reference
  private ContentCategoryService contentCategoryService;

  /**
   * 返回全部列表
   */
  @RequestMapping("/findAll")
  public List<TbContentCategory> findAll() {
    return contentCategoryService.findAll();
  }

  /**
   * 返回全部列表
   */
  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return contentCategoryService.findPage(page, rows);
  }

  /**
   * 增加
   */
  @RequestMapping("/add")
  public Result add(@RequestBody TbContentCategory contentCategory) {
    try {
      contentCategoryService.add(contentCategory);
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
  public Result update(@RequestBody TbContentCategory contentCategory) {
    try {
      contentCategoryService.update(contentCategory);
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
  public TbContentCategory findOne(Long id) {
    return contentCategoryService.findOne(id);
  }

  /**
   * 批量删除
   */
  @RequestMapping("/delete")
  public Result delete(Long[] ids) {
    try {
      contentCategoryService.delete(ids);
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
  public PageResult search(@RequestBody TbContentCategory contentCategory, int page, int rows) {
    return contentCategoryService.findPage(contentCategory, page, rows);
  }

}
