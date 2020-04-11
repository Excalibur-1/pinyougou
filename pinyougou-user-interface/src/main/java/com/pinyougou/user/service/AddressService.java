package com.pinyougou.user.service;

import com.pinyougou.pojo.TbAddress;
import entity.PageResult;

import java.util.List;

/**
 * 服务层接口
 *
 * @author gxl
 */
public interface AddressService {

  /**
   * 返回全部列表
   *
   * @return List<TbAddress>
   */
  List<TbAddress> findAll();

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
   * @param address 地址对象
   */
  void add(TbAddress address);

  /**
   * 修改
   *
   * @param address 地址对象
   */
  void update(TbAddress address);

  /**
   * 根据ID获取实体
   *
   * @param id 地址id
   * @return TbAddress
   */
  TbAddress findOne(Long id);

  /**
   * 批量删除
   *
   * @param ids 地址id列表
   */
  void delete(Long[] ids);

  /**
   * 分页
   *
   * @param address  地址对象
   * @param pageNum  当前页 码
   * @param pageSize 每页记录数
   * @return PageResult
   */
  PageResult findPage(TbAddress address, int pageNum, int pageSize);

  /**
   * 查询用户地址列表
   *
   * @param userId 用户id
   * @return List<TbAddress>
   */
  List<TbAddress> findListByUserId(String userId);

}
