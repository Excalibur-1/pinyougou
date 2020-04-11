package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import com.pinyougou.pojo.TbSellerExample.Criteria;
import com.pinyougou.sellergoods.service.SellerService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 服务实现层
 *
 * @author gxl
 */
@Service
public class SellerServiceImpl implements SellerService {

  private final TbSellerMapper baseMapper;

  @Autowired
  public SellerServiceImpl(TbSellerMapper baseMapper) {
    this.baseMapper = baseMapper;
  }

  /**
   * 查询全部
   */
  @Override
  public List<TbSeller> findAll() {
    return baseMapper.selectByExample(null);
  }

  /**
   * 按分页查询
   */
  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbSeller> page = (Page<TbSeller>) baseMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  /**
   * 增加
   */
  @Override
  public void add(TbSeller seller) {
    // 设置商家状态
    seller.setStatus("0");

    seller.setCreateTime(new Date());

    baseMapper.insert(seller);
  }

  /**
   * 修改
   */
  @Override
  public void update(TbSeller seller) {
    baseMapper.updateByPrimaryKey(seller);
  }

  /**
   * 根据ID获取实体
   */
  @Override
  public TbSeller findOne(String id) {
    return baseMapper.selectByPrimaryKey(id);
  }

  /**
   * 批量删除
   */
  @Override
  public void delete(String[] ids) throws Exception {
    if (ids != null && ids.length > 0) {
      TbSellerExample example = new TbSellerExample();
      example.createCriteria().andSellerIdIn(Arrays.asList(ids));
      baseMapper.deleteByExample(example);
    } else {
      throw new Exception("请选择数据");
    }
  }

  @Override
  public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    TbSellerExample example = new TbSellerExample();
    Criteria criteria = example.createCriteria();

    setQueryCondition(seller, criteria);

    Page<TbSeller> page = (Page<TbSeller>) baseMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }


  @Override
  public void updateStatus(String sellerId, String status) {
    TbSeller seller = baseMapper.selectByPrimaryKey(sellerId);

    seller.setStatus(status);

    baseMapper.updateByPrimaryKey(seller);
  }

  /**
   * 设置查询条件
   *
   * @param seller   商家
   * @param criteria 查询对象
   */
  private void setQueryCondition(TbSeller seller, Criteria criteria) {
    if (seller != null) {
      if (seller.getSellerId() != null && seller.getSellerId().length() > 0) {
        criteria.andSellerIdLike("%" + seller.getSellerId() + "%");
      }
      if (seller.getName() != null && seller.getName().length() > 0) {
        criteria.andNameLike("%" + seller.getName() + "%");
      }
      if (seller.getNickName() != null && seller.getNickName().length() > 0) {
        criteria.andNickNameLike("%" + seller.getNickName() + "%");
      }
      if (seller.getPassword() != null && seller.getPassword().length() > 0) {
        criteria.andPasswordLike("%" + seller.getPassword() + "%");
      }
      if (seller.getEmail() != null && seller.getEmail().length() > 0) {
        criteria.andEmailLike("%" + seller.getEmail() + "%");
      }
      if (seller.getMobile() != null && seller.getMobile().length() > 0) {
        criteria.andMobileLike("%" + seller.getMobile() + "%");
      }
      if (seller.getTelephone() != null && seller.getTelephone().length() > 0) {
        criteria.andTelephoneLike("%" + seller.getTelephone() + "%");
      }
      if (seller.getStatus() != null && seller.getStatus().length() > 0) {
        criteria.andStatusLike("%" + seller.getStatus() + "%");
      }
      if (seller.getAddressDetail() != null && seller.getAddressDetail().length() > 0) {
        criteria.andAddressDetailLike("%" + seller.getAddressDetail() + "%");
      }
      if (seller.getLinkmanName() != null && seller.getLinkmanName().length() > 0) {
        criteria.andLinkmanNameLike("%" + seller.getLinkmanName() + "%");
      }
      if (seller.getLinkmanQq() != null && seller.getLinkmanQq().length() > 0) {
        criteria.andLinkmanQqLike("%" + seller.getLinkmanQq() + "%");
      }
      if (seller.getLinkmanMobile() != null && seller.getLinkmanMobile().length() > 0) {
        criteria.andLinkmanMobileLike("%" + seller.getLinkmanMobile() + "%");
      }
      if (seller.getLinkmanEmail() != null && seller.getLinkmanEmail().length() > 0) {
        criteria.andLinkmanEmailLike("%" + seller.getLinkmanEmail() + "%");
      }
      if (seller.getLicenseNumber() != null && seller.getLicenseNumber().length() > 0) {
        criteria.andLicenseNumberLike("%" + seller.getLicenseNumber() + "%");
      }
      if (seller.getTaxNumber() != null && seller.getTaxNumber().length() > 0) {
        criteria.andTaxNumberLike("%" + seller.getTaxNumber() + "%");
      }
      if (seller.getOrgNumber() != null && seller.getOrgNumber().length() > 0) {
        criteria.andOrgNumberLike("%" + seller.getOrgNumber() + "%");
      }
      if (seller.getLogoPic() != null && seller.getLogoPic().length() > 0) {
        criteria.andLogoPicLike("%" + seller.getLogoPic() + "%");
      }
      if (seller.getBrief() != null && seller.getBrief().length() > 0) {
        criteria.andBriefLike("%" + seller.getBrief() + "%");
      }
      if (seller.getLegalPerson() != null && seller.getLegalPerson().length() > 0) {
        criteria.andLegalPersonLike("%" + seller.getLegalPerson() + "%");
      }
      if (seller.getLegalPersonCardId() != null && seller.getLegalPersonCardId().length() > 0) {
        criteria.andLegalPersonCardIdLike("%" + seller.getLegalPersonCardId() + "%");
      }
      if (seller.getBankUser() != null && seller.getBankUser().length() > 0) {
        criteria.andBankUserLike("%" + seller.getBankUser() + "%");
      }
      if (seller.getBankName() != null && seller.getBankName().length() > 0) {
        criteria.andBankNameLike("%" + seller.getBankName() + "%");
      }
    }
  }

}
