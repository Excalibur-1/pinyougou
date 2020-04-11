package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author gxl
 */
@RestController
@RequestMapping("/login")
public class LoginController {

  /**
   * 返回当前登录的用户名
   *
   * @return Map
   */
  @RequestMapping("/name")
  public Map<String, String> name() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    Map<String, String> map = new HashMap<>(1);
    map.put("loginName", name);
    return map;
  }
}
