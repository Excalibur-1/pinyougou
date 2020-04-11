package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import constants.Constants;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * 品牌控制器
 *
 * @author gxl
 */
@RestController
@RequestMapping("/brand")
public class BrandController implements Constants {

  @Reference
  private BrandService brandService;

  @RequestMapping("/findAll")
  public List<TbBrand> findAll() {
    return brandService.findAll();
  }

  @RequestMapping("/findPage")
  public PageResult findPage(int page, int size) {
    return brandService.findPage(page, size);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Result add(@RequestBody TbBrand brand) {
    try {
      brandService.add(brand);
      return new Result(TRUE, ADD_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, ADD_FAIL);
    }
  }

  @RequestMapping("/findOne")
  public TbBrand findOne(Long id) {
    return brandService.findOne(id);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public Result update(@RequestBody TbBrand brand) {
    try {
      brandService.update(brand);
      return new Result(TRUE, UPDATE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, UPDATE_FAIL);
    }
  }

  @RequestMapping("/delete")
  public Result delete(Long[] ids) {
    try {
      brandService.delete(ids);
      return new Result(TRUE, DELETE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, DELETE_FAIL);
    }
  }

  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public PageResult search(@RequestBody TbBrand brand, int page, int size) {
    return brandService.findPage(brand, page, size);
  }

  @RequestMapping("/selectOptionList")
  public List<Map> selectOptionList() {
    return brandService.selectOptionList();
  }

}
