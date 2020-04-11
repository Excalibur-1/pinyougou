package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.PhoneFormatCheckUtils;

import java.util.List;

import static constants.Constants.ADD_FAIL;
import static constants.Constants.ADD_SUCCESS;
import static constants.Constants.DELETE_FAIL;
import static constants.Constants.DELETE_SUCCESS;
import static constants.Constants.UPDATE_FAIL;
import static constants.Constants.UPDATE_SUCCESS;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * controller
 *
 * @author gxl
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Reference
  private UserService userService;

  @RequestMapping("/findAll")
  public List<TbUser> findAll() {
    return userService.findAll();
  }

  @RequestMapping("/findPage")
  public PageResult findPage(int page, int rows) {
    return userService.findPage(page, rows);
  }

  @RequestMapping("/add")
  public Result add(@RequestBody TbUser user, String smsCode) {
    //校验验证码是否正确
    boolean checkSmsCode = userService.checkSmsCode(user.getPhone(), smsCode);
    if (!checkSmsCode) {
      return new Result(FALSE, "验证码不正确！");
    }
    try {
      userService.add(user);
      return new Result(TRUE, ADD_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, ADD_FAIL);
    }
  }

  @RequestMapping("/update")
  public Result update(@RequestBody TbUser user) {
    try {
      userService.update(user);
      return new Result(TRUE, UPDATE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, UPDATE_FAIL);
    }
  }

  @RequestMapping("/findOne")
  public TbUser findOne(@RequestParam Long id) {
    return userService.findOne(id);
  }

  @RequestMapping("/delete")
  public Result delete(@RequestParam Long[] ids) {
    try {
      userService.delete(ids);
      return new Result(TRUE, DELETE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, DELETE_FAIL);
    }
  }

  @RequestMapping("/search")
  public PageResult search(@RequestBody TbUser user, int page, int rows) {
    return userService.findPage(user, page, rows);
  }

  @RequestMapping("/sendCode")
  public Result sendCode(@RequestParam String phone) {
    if (!PhoneFormatCheckUtils.isPhoneLegal(phone)) {
      return new Result(FALSE, "手机格式不正确");
    }
    try {
      userService.createSmsCode(phone);
      return new Result(TRUE, "验证码发送成功");
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(FALSE, "验证码发送失败");
    }
  }

}
