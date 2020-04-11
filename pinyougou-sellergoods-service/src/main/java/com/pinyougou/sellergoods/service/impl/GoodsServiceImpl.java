package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbGoodsExample;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author gxl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements GoodsService {

  private final TbGoodsDescMapper goodsDescMapper;

  private final TbItemMapper itemMapper;

  private final TbItemCatMapper itemCatMapper;

  private final TbBrandMapper brandMapper;

  private final TbSellerMapper sellerMapper;

  private final TbGoodsMapper baseMapper;

  @Autowired
  public GoodsServiceImpl(TbGoodsDescMapper goodsDescMapper,
                          TbItemMapper itemMapper,
                          TbItemCatMapper itemCatMapper,
                          TbBrandMapper brandMapper,
                          TbSellerMapper sellerMapper,
                          TbGoodsMapper baseMapper) {
    this.goodsDescMapper = goodsDescMapper;
    this.itemMapper = itemMapper;
    this.itemCatMapper = itemCatMapper;
    this.brandMapper = brandMapper;
    this.sellerMapper = sellerMapper;
    this.baseMapper = baseMapper;
  }

  @Override
  public List<TbGoods> findAll() {
    return baseMapper.selectByExample(null);
  }

  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbGoods> page = (Page<TbGoods>) baseMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public void add(Goods goods) {
    // 设置审核的状态
    goods.getGoods().setAuditStatus("0");

    // 插入商品信息
    baseMapper.insert(goods.getGoods());

    goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());

    // 插入商品的扩展信息
    goodsDescMapper.insert(goods.getGoodsDesc());

    setItemList(goods);
  }

  @Override
  public void update(Goods goods) {
    // 修改商品信息
    goods.getGoods().setAuditStatus("0");
    baseMapper.updateByPrimaryKey(goods.getGoods());
    // 修改商品扩展信息:
    goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
    // 修改SKU信息:
    // 先删除，再保存:
    // 删除SKU的信息:
    TbItemExample example = new TbItemExample();
    TbItemExample.Criteria criteria = example.createCriteria();
    criteria.andGoodsIdEqualTo(goods.getGoods().getId());
    itemMapper.deleteByExample(example);
    // 保存SKU的信息
    setItemList(goods);
  }

  @Override
  public Goods findOne(Long id) {
    Goods goods = new Goods();
    // 查询商品表的信息
    TbGoods tbGoods = baseMapper.selectByPrimaryKey(id);
    goods.setGoods(tbGoods);
    // 查询商品扩展表的信息
    TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
    goods.setGoodsDesc(tbGoodsDesc);

    // 查询SKU表的信息:
    TbItemExample example = new TbItemExample();
    TbItemExample.Criteria criteria = example.createCriteria();
    criteria.andGoodsIdEqualTo(id);
    List<TbItem> list = itemMapper.selectByExample(example);
    goods.setItemList(list);

    return goods;
  }

  @Override
  public void delete(Long[] ids) throws Exception {
    if (ids != null && ids.length > 0) {
      TbGoods tbGoods = new TbGoods();
      tbGoods.setIsDelete("1");
      TbGoodsExample example = new TbGoodsExample();
      example.createCriteria().andIdIn(Arrays.asList(ids));
      baseMapper.updateByExampleSelective(tbGoods, example);
    } else {
      throw new Exception("请选择商品");
    }
  }

  @Override
  public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    TbGoodsExample example = new TbGoodsExample();
    TbGoodsExample.Criteria criteria = example.createCriteria();

    criteria.andIsDeleteIsNull();

    setQueryCondition(goods, criteria);

    Page<TbGoods> page = (Page<TbGoods>) baseMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }


  @Override
  public void updateStatus(Long[] ids, String status) throws Exception {
    if (ids != null && ids.length > 0) {
      TbGoods tbGoods = new TbGoods();
      tbGoods.setAuditStatus(status);
      TbGoodsExample example = new TbGoodsExample();
      example.createCriteria().andIdIn(Arrays.asList(ids));
      baseMapper.updateByExampleSelective(tbGoods, example);
    } else {
      throw new Exception("请选择商品");
    }
  }

  @Override
  public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status) {

    TbItemExample example = new TbItemExample();
    TbItemExample.Criteria criteria = example.createCriteria();
    //状态
    criteria.andStatusEqualTo(status);
    //指定条件：SPU ID集合
    criteria.andGoodsIdIn(Arrays.asList(goodsIds));

    return itemMapper.selectByExample(example);
  }

  /**
   * 设置商品属性
   *
   * @param goods 商品
   */
  private void setItemList(Goods goods) {
    String value = "1";
    if (value.equals(goods.getGoods().getIsEnableSpec())) {
      // 启用规格
      // 保存SKU列表的信息:
      goods.getItemList().parallelStream().forEach(item -> {
        // 设置SKU的数据：
        StringBuilder title = new StringBuilder(goods.getGoods().getGoodsName());
        Map map = JSON.parseObject(item.getSpec(), Map.class);
        for (Object key : map.keySet()) {
          title.append(" ").append(map.get(key));
        }
        item.setTitle(title.toString());
        setValue(goods, item);
        itemMapper.insert(item);
      });
    } else {
      // 没有启用规格
      TbItem item = new TbItem();
      item.setTitle(goods.getGoods().getGoodsName());
      item.setPrice(goods.getGoods().getPrice());
      item.setNum(99999);
      item.setStatus(value);
      item.setIsDefault(value);
      item.setSpec("{}");
      setValue(goods, item);
      itemMapper.insert(item);
    }
  }

  /**
   * 设置值
   *
   * @param goods 商品
   * @param item  订单商品
   */
  private void setValue(Goods goods, TbItem item) {
    List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
    if (imageList.size() > 0) {
      item.setImage((String) imageList.get(0).get("url"));
    }

    // 保存三级分类的ID:
    item.setCategoryId(goods.getGoods().getCategory3Id());
    item.setCreateTime(new Date());
    item.setUpdateTime(new Date());
    // 设置商品ID
    item.setGoodsId(goods.getGoods().getId());
    item.setSellerId(goods.getGoods().getSellerId());

    TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
    item.setCategory(itemCat.getName());

    TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
    item.setBrand(brand.getName());

    TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
    item.setSeller(seller.getNickName());
  }

  /**
   * 设置查询条件
   *
   * @param goods    商品
   * @param criteria 查询条件
   */
  private void setQueryCondition(TbGoods goods, TbGoodsExample.Criteria criteria) {
    if (goods != null) {
      if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
        criteria.andSellerIdEqualTo(goods.getSellerId());
      }
      if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
        criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
      }
      if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
        criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
      }
      if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
        criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
      }
      if (goods.getCaption() != null && goods.getCaption().length() > 0) {
        criteria.andCaptionLike("%" + goods.getCaption() + "%");
      }
      if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
        criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
      }
      if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
        criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
      }
      if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
        criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
      }
    }
  }
}
