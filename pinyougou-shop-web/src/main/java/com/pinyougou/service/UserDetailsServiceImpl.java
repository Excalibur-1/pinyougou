package com.pinyougou.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证类
 *
 * @author gxl
 */
public class UserDetailsServiceImpl implements UserDetailsService {

  /**
   * 有效商家
   */
  private static final String SELLER_STATUS = "1";

  /**
   * 手动注入商家接口，提供获取密码服务
   */
  private SellerService sellerService;

  public void setSellerService(SellerService sellerService) {
    this.sellerService = sellerService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //构建用户角色列表
    List<GrantedAuthority> grantAuthList = new ArrayList<>();
    //加入角色权限
    grantAuthList.add(new SimpleGrantedAuthority("ROLE_SELLER"));
    //获取商家对象
    TbSeller seller = sellerService.findOne(username);
    if (seller != null && SELLER_STATUS.equals(seller.getStatus())) {
      return new User(username, seller.getPassword(), grantAuthList);
    }
    return null;
  }

}
