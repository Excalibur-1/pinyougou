package com.pinyougou.user.service;

import com.pinyougou.pojo.TbUser;
import entity.PageResult;

import java.util.List;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface UserService {

  /**
   * 返回全部列表
   *
   * @return List<TbUser>
   */
  List<TbUser> findAll();

  /**
   * 返回分页列表
   *
   * @param pageNum  页码
   * @param pageSize 页大小
   * @return PageResult
   */
  PageResult findPage(int pageNum, int pageSize);

  /**
   * 增加
   *
   * @param user 用户实体
   */
  void add(TbUser user);

  /**
   * 修改
   *
   * @param user 用户实体
   */
  void update(TbUser user);

  /**
   * 根据ID获取实体
   *
   * @param id 用户id
   * @return TbUser
   */
  TbUser findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids 用户id列表
   */
  void delete(Long[] ids);

  /**
   * 分页
   *
   * @param user     用户对象
   * @param pageNum  当前页 码
   * @param pageSize 每页记录数
   * @return PageResult
   */
  PageResult findPage(TbUser user, int pageNum, int pageSize);

  /**
   * 发送短信验证码
   *
   * @param phone 手机号
   */
  void createSmsCode(String phone);

  /**
   * 校验验证码
   *
   * @param phone 手机号
   * @param code  验证码
   * @return boolean
   */
  boolean checkSmsCode(String phone, String code);

}
