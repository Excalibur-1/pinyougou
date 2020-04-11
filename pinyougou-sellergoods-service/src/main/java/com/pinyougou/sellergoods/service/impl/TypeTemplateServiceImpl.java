package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.pojo.TbTypeTemplateExample.Criteria;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static constants.Constants.RedisCacheKey.BRAND_LIST;
import static constants.Constants.RedisCacheKey.SPEC_LIST;

/**
 * 服务实现层
 *
 * @author gxl
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

  private final TbSpecificationOptionMapper specificationOptionMapper;

  private final TbTypeTemplateMapper baseMapper;

  private final RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public TypeTemplateServiceImpl(TbSpecificationOptionMapper specificationOptionMapper,
                                 TbTypeTemplateMapper baseMapper,
                                 RedisTemplate<String, Object> redisTemplate) {
    this.specificationOptionMapper = specificationOptionMapper;
    this.baseMapper = baseMapper;
    this.redisTemplate = redisTemplate;
  }

  /**
   * 查询全部
   */
  @Override
  public List<TbTypeTemplate> findAll() {
    return baseMapper.selectByExample(null);
  }

  /**
   * 按分页查询
   */
  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) baseMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  /**
   * 增加
   */
  @Override
  public void add(TbTypeTemplate typeTemplate) throws Exception {
    checkTypeTemplateRepeat(typeTemplate);
    baseMapper.insert(typeTemplate);
  }

  /**
   * 修改
   */
  @Override
  public void update(TbTypeTemplate typeTemplate) throws Exception {
    checkTypeTemplateRepeat(typeTemplate);
    baseMapper.updateByPrimaryKey(typeTemplate);
  }

  /**
   * 根据ID获取实体
   */
  @Override
  public TbTypeTemplate findOne(Long id) {
    return baseMapper.selectByPrimaryKey(id);
  }

  /**
   * 批量删除
   */
  @Override
  public void delete(Long[] ids) {
    if (ids != null && ids.length > 0) {
      TbTypeTemplateExample example = new TbTypeTemplateExample();
      example.createCriteria().andIdIn(Arrays.asList(ids));
      baseMapper.deleteByExample(example);
    }
  }

  @Override
  public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    TbTypeTemplateExample example = new TbTypeTemplateExample();
    Criteria criteria = example.createCriteria();

    setQueryCondition(typeTemplate, criteria);

    Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) baseMapper.selectByExample(example);

    //设置缓存
    saveToRedis();
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public List<Map> selectOptionList() {
    return baseMapper.selectOptionList();
  }

  @Override
  public List<Map> selectSpecList(Long id) {
    TbTypeTemplate tbTypeTemplate = baseMapper.selectByPrimaryKey(id);
    List<Map> list = JSON.parseArray(tbTypeTemplate.getSpecIds(), Map.class);
    list.parallelStream().forEach(map -> {
      //查询规格选项列表
      TbSpecificationOptionExample example = new TbSpecificationOptionExample();
      TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
      criteria.andSpecIdEqualTo(Long.valueOf((Integer) map.get("id")));
      List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
      map.put("options", options);
    });
    return list;
  }

  //=====================私有方法专区========================//

  /**
   * 检查模板属性
   *
   * @param tbTypeTemplate 模板
   * @return List<TbTypeTemplate>
   * @throws Exception Exception
   */
  private List<TbTypeTemplate> checkAndGetTbTypeTemplate(TbTypeTemplate tbTypeTemplate) throws Exception {
    Optional.ofNullable(tbTypeTemplate).orElseThrow(() -> new Exception("请输入商品类型模板信息"));
    if (StringUtils.isBlank(tbTypeTemplate.getName())) {
      throw new Exception("商品类型不能为空");
    }
    if (StringUtils.isBlank(tbTypeTemplate.getBrandIds())) {
      throw new Exception("关联品牌不能为空");
    }
    if (StringUtils.isBlank(tbTypeTemplate.getSpecIds())) {
      throw new Exception("关联规格不能为空");
    }
    return this.findAll();
  }

  /**
   * 检查商品类型是否重复
   *
   * @param typeTemplate 模板
   * @throws Exception Exception
   */
  private void checkTypeTemplateRepeat(TbTypeTemplate typeTemplate) throws Exception {
    List<TbTypeTemplate> tbTypeTemplates = checkAndGetTbTypeTemplate(typeTemplate);
    if (tbTypeTemplates.parallelStream().anyMatch(t -> t.getName().equals(typeTemplate.getName()))) {
      throw new Exception("不能与已有的商品类型重复");
    }
  }

  /**
   * 设置查询条件
   *
   * @param typeTemplate 对象
   * @param criteria     查询条件
   */
  private void setQueryCondition(TbTypeTemplate typeTemplate, Criteria criteria) {
    Optional.ofNullable(typeTemplate).ifPresent(t -> {
      if (t.getName() != null && t.getName().length() > 0) {
        criteria.andNameLike("%" + t.getName() + "%");
      }
      if (t.getSpecIds() != null && t.getSpecIds().length() > 0) {
        criteria.andSpecIdsLike("%" + t.getSpecIds() + "%");
      }
      if (t.getBrandIds() != null && t.getBrandIds().length() > 0) {
        criteria.andBrandIdsLike("%" + t.getBrandIds() + "%");
      }
      if (t.getCustomAttributeItems() != null && t.getCustomAttributeItems().length() > 0) {
        criteria.andCustomAttributeItemsLike("%" + t.getCustomAttributeItems() + "%");
      }
    });
  }

  /**
   * 将品牌列表与规格列表放入缓存
   */
  private void saveToRedis() {
    List<TbTypeTemplate> templateList = findAll();
    templateList.parallelStream().forEach(t -> {
      //得到品牌列表
      List<Map> brandList = JSON.parseArray(t.getBrandIds(), Map.class);
      redisTemplate.boundHashOps(BRAND_LIST).put(t.getId(), brandList);

      //得到规格列表
      List<Map> specList = this.selectSpecList(t.getId());
      redisTemplate.boundHashOps(SPEC_LIST).put(t.getId(), specList);
    });
  }

}
