package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.pojo.TbItemCatExample.Criteria;
import com.pinyougou.sellergoods.service.ItemCatService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static constants.Constants.RedisCacheKey.ITEM_CAT;

/**
 * 商品分类服务实现层
 *
 * @author gxl
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

  private final RedisTemplate<String, Long> redisTemplate;

  private final TbItemCatMapper baseMapper;

  @Autowired
  public ItemCatServiceImpl(RedisTemplate<String, Long> redisTemplate, TbItemCatMapper baseMapper) {
    this.redisTemplate = redisTemplate;
    this.baseMapper = baseMapper;
  }

  @Override
  public List<TbItemCat> findAll() {
    return baseMapper.selectByExample(null);
  }

  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbItemCat> page = (Page<TbItemCat>) baseMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public void add(TbItemCat itemCat) {
    baseMapper.insert(itemCat);
  }

  @Override
  public void update(TbItemCat itemCat) {
    baseMapper.updateByPrimaryKey(itemCat);
  }

  @Override
  public TbItemCat findOne(Long id) {
    return baseMapper.selectByPrimaryKey(id);
  }

  @Override
  public void delete(Long[] ids) throws Exception {
    if (ids != null && ids.length > 0) {
      if (Arrays.stream(ids)
          .anyMatch(id -> CollectionUtils.isNotEmpty(baseMapper.selectByParentId(id)))) {
        throw new Exception("禁止删除有下级分类的数据");
      }
      TbItemCatExample example = new TbItemCatExample();
      example.createCriteria().andIdIn(Arrays.asList(ids));
      baseMapper.deleteByExample(example);
    } else {
      throw new Exception("请选择需要删除的数据");
    }
  }

  @Override
  public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    TbItemCatExample example = new TbItemCatExample();
    Criteria criteria = example.createCriteria();

    Optional.ofNullable(itemCat)
        .ifPresent(
            i -> {
              if (i.getName() != null && i.getName().length() > 0) {
                criteria.andNameLike("%" + i.getName() + "%");
              }
            });

    Page<TbItemCat> page = (Page<TbItemCat>) baseMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public List<TbItemCat> findByParentId(Long parentId) {
    TbItemCatExample example = new TbItemCatExample();
    Criteria criteria = example.createCriteria();
    // 设置条件:
    criteria.andParentIdEqualTo(parentId);

    // 将模板ID放入缓存（以商品分类名称作为key）
    List<TbItemCat> itemCatList = findAll();
    itemCatList
        .parallelStream()
        .forEach(i -> redisTemplate.boundHashOps(ITEM_CAT).put(i.getName(), i.getTypeId()));

    // 条件查询
    return baseMapper.selectByExample(example);
  }
}
