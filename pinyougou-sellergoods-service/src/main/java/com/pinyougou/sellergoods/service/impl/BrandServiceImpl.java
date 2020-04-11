package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 品牌实现类
 *
 * @author gxl
 */
@Service
public class BrandServiceImpl implements BrandService {

  private final TbBrandMapper brandMapper;

  @Autowired
  public BrandServiceImpl(TbBrandMapper brandMapper) {
    this.brandMapper = brandMapper;
  }

  @Override
  public List<TbBrand> findAll() {
    return brandMapper.selectByExample(null);
  }

  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    //分页
    PageHelper.startPage(pageNum, pageSize);
    Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public void add(TbBrand brand) throws Exception {
    List<TbBrand> brandList = checkAndGetTbBrands(brand);
    if (brandList.parallelStream().anyMatch(b -> b.getName().equals(brand.getName()))) {
      throw new Exception("不能与已有的品牌名称重复");
    }
    brandMapper.insert(brand);
  }

  @Override
  public TbBrand findOne(Long id) {
    return brandMapper.selectByPrimaryKey(id);
  }

  @Override
  public void update(TbBrand brand) throws Exception {
    List<TbBrand> brandList = checkAndGetTbBrands(brand);
    boolean result = brandList.parallelStream()
        .anyMatch(b -> !b.getId().equals(brand.getId()) && b.getName().equals(brand.getName()));
    if (result) {
      throw new Exception("不能与已有的品牌名称重复");
    }
    brandMapper.updateByPrimaryKey(brand);
  }

  @Override
  public void delete(Long[] ids) {
    if (ids != null && ids.length > 0) {
      TbBrandExample example = new TbBrandExample();
      example.createCriteria().andIdIn(Arrays.asList(ids));
      brandMapper.deleteByExample(example);
    }
  }

  @Override
  public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
    //分页
    PageHelper.startPage(pageNum, pageSize);

    //构造查询条件
    TbBrandExample example = new TbBrandExample();
    Optional.ofNullable(brand).ifPresent(b -> {
      TbBrandExample.Criteria criteria = example.createCriteria();
      if (StringUtils.isNotEmpty(b.getName())) {
        criteria.andNameLike("%" + b.getName() + "%");
      }
      if (StringUtils.isNotEmpty(b.getFirstChar())) {
        criteria.andFirstCharLike("%" + b.getFirstChar() + "%");
      }
    });

    Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public List<Map> selectOptionList() {
    return brandMapper.selectOptionList();
  }

  //=====================私有方法专区========================//

  /**
   * 检查品牌信息
   *
   * @param brand 品牌对象
   * @return List<TbBrand>
   * @throws Exception Exception
   */
  private List<TbBrand> checkAndGetTbBrands(TbBrand brand) throws Exception {
    Optional.ofNullable(brand).orElseThrow(() -> new Exception("请输入品牌信息"));
    if (StringUtils.isBlank(brand.getName())) {
      throw new Exception("品牌名称不能为空");
    }
    if (StringUtils.isBlank(brand.getFirstChar())) {
      throw new Exception("品牌首字母不能为空");
    }
    return this.findAll();
  }

}
