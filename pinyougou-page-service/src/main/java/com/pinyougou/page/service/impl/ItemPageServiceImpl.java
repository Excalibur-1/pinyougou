package com.pinyougou.page.service.impl;

import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxl
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {

  @Value("${pageDir}")
  private String pageDir;

  private final FreeMarkerConfigurer freeMarkerConfigurer;

  private final TbGoodsMapper goodsMapper;

  private final TbGoodsDescMapper goodsDescMapper;

  private final TbItemCatMapper itemCatMapper;

  private final TbItemMapper itemMapper;

  @Autowired
  public ItemPageServiceImpl(FreeMarkerConfigurer freeMarkerConfigurer,
                             TbGoodsMapper goodsMapper,
                             TbGoodsDescMapper goodsDescMapper,
                             TbItemCatMapper itemCatMapper,
                             TbItemMapper itemMapper) {
    this.freeMarkerConfigurer = freeMarkerConfigurer;
    this.goodsDescMapper = goodsDescMapper;
    this.goodsMapper = goodsMapper;
    this.itemCatMapper = itemCatMapper;
    this.itemMapper = itemMapper;
  }

  @Override
  public boolean genItemHtml(Long goodsId) {
    Configuration configuration = freeMarkerConfigurer.getConfiguration();
    try {
      Template template = configuration.getTemplate("item.ftl");
      //创建数据模型
      Map<String, Object> dataModel = new HashMap<>(6);

      //1.商品主表数据
      TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
      dataModel.put("goods", goods);

      //2.商品扩展表数据
      TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
      dataModel.put("goodsDesc", goodsDesc);

      //3.读取商品分类
      String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
      String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
      String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
      dataModel.put("itemCat1", itemCat1);
      dataModel.put("itemCat2", itemCat2);
      dataModel.put("itemCat3", itemCat3);

      //4.读取SKU列表
      TbItemExample example = new TbItemExample();
      Criteria criteria = example.createCriteria();
      //SPU ID
      criteria.andGoodsIdEqualTo(goodsId);
      //状态有效
      criteria.andStatusEqualTo("1");
      //按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU
      example.setOrderByClause("is_default desc");

      List<TbItem> itemList = itemMapper.selectByExample(example);
      dataModel.put("itemList", itemList);

      Writer out = new FileWriter(pageDir + goodsId + ".html");

      //输出
      template.process(dataModel, out);
      out.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean deleteItemHtml(Long[] goodsIds) {
    try {
      Arrays.asList(goodsIds).parallelStream()
          .forEach(g -> new File(pageDir + g + ".html").delete());
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
