package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 服务实现层
 *
 * @author gxl
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

  private final TbSpecificationOptionMapper specificationOptionMapper;

  private final TbSpecificationMapper baseMapper;

  @Autowired
  public SpecificationServiceImpl(TbSpecificationOptionMapper specificationOptionMapper,
                                  TbSpecificationMapper baseMapper) {
    this.specificationOptionMapper = specificationOptionMapper;
    this.baseMapper = baseMapper;
  }

  /**
   * 查询全部
   */
  @Override
  public List<TbSpecification> findAll() {
    return baseMapper.selectByExample(null);
  }

  /**
   * 按分页查询
   */
  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbSpecification> page = (Page<TbSpecification>) baseMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  /**
   * 增加
   */
  @Override
  public void add(Specification specification) throws Exception {
    //获取规格实体
    TbSpecification tbSpecification = getTbSpecification(specification);

    baseMapper.insert(tbSpecification);

    addSpecificationOption(specification, tbSpecification);
  }

  /**
   * 修改
   */
  @Override
  public void update(Specification specification) throws Exception {
    TbSpecification tbSpecification = getTbSpecification(specification);

    baseMapper.updateByPrimaryKey(tbSpecification);

    //删除原来规格对应的规格选项
    TbSpecificationOptionExample example = new TbSpecificationOptionExample();
    TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
    criteria.andSpecIdEqualTo(tbSpecification.getId());
    specificationOptionMapper.deleteByExample(example);

    addSpecificationOption(specification, tbSpecification);
  }


  /**
   * 根据ID获取实体
   */
  @Override
  public Specification findOne(Long id) {

    Specification specification = new Specification();
    //获取规格实体
    TbSpecification tbSpecification = baseMapper.selectByPrimaryKey(id);
    specification.setSpecification(tbSpecification);

    //获取规格选项列表
    TbSpecificationOptionExample example = new TbSpecificationOptionExample();
    TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
    criteria.andSpecIdEqualTo(id);
    List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);

    specification.setSpecificationOptionList(specificationOptionList);

    //返回组合实体类
    return specification;
  }

  /**
   * 批量删除
   */
  @Override
  public void delete(Long[] ids) {
    if (ids != null && ids.length > 0) {
      List<Long> list = Arrays.asList(ids);
      //删除规格表数据
      TbSpecificationExample example1 = new TbSpecificationExample();
      example1.createCriteria().andIdIn(list);
      baseMapper.deleteByExample(example1);

      //删除规格选项表数据
      TbSpecificationOptionExample example2 = new TbSpecificationOptionExample();
      TbSpecificationOptionExample.Criteria criteria = example2.createCriteria();
      criteria.andSpecIdIn(list);
      specificationOptionMapper.deleteByExample(example2);
    }
  }


  @Override
  public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    TbSpecificationExample example = new TbSpecificationExample();
    Criteria criteria = example.createCriteria();

    Optional.ofNullable(specification).ifPresent(s -> {
      if (s.getSpecName() != null && s.getSpecName().length() > 0) {
        criteria.andSpecNameLike("%" + s.getSpecName() + "%");
      }
    });

    Page<TbSpecification> page = (Page<TbSpecification>) baseMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public List<Map> selectOptionList() {
    return baseMapper.selectOptionList();
  }

  //=====================私有方法专区========================//

  /**
   * 检查规格属性
   *
   * @param specification 规格
   * @return List<TbSpecification>
   * @throws Exception Exception
   */
  private List<TbSpecification> checkAndGetTbSpecifications(TbSpecification specification) throws Exception {
    if (specification == null) {
      throw new Exception("请输入规格信息");
    }
    if (StringUtils.isBlank(specification.getSpecName())) {
      throw new Exception("规格名称不能为空");
    }
    return this.findAll();
  }

  /**
   * 添加规格选项
   *
   * @param specification   前端传递对象
   * @param tbSpecification 数据库对象
   */
  private void addSpecificationOption(Specification specification, TbSpecification tbSpecification) {
    //获取规格选项集合
    List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
    specificationOptionList.parallelStream().forEach(s -> {
      //设置规格ID
      s.setSpecId(tbSpecification.getId());
      //新增规格
      specificationOptionMapper.insert(s);
    });
  }

  /**
   * 获取规格实体
   *
   * @param specification 前端传递对象
   * @return TbSpecification
   * @throws Exception Exception
   */
  @NotNull
  private TbSpecification getTbSpecification(Specification specification) throws Exception {
    //获取规格实体
    TbSpecification tbSpecification = specification.getSpecification();

    //校验数据
    List<TbSpecification> tbSpecifications = checkAndGetTbSpecifications(tbSpecification);
    if (tbSpecifications.parallelStream().anyMatch(s -> s.getSpecName().equals(tbSpecification.getSpecName()))) {
      throw new Exception("不能与已有的规格名称重复");
    }
    return tbSpecification;
  }

}
