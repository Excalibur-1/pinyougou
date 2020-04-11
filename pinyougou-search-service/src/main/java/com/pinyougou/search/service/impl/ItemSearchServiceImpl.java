package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static constants.Constants.RedisCacheKey.BRAND_LIST;
import static constants.Constants.RedisCacheKey.ITEM_CAT;
import static constants.Constants.RedisCacheKey.SPEC_LIST;

/**
 * @author gxl
 */
@Service(timeout = 5000)
public class ItemSearchServiceImpl implements ItemSearchService {

  private final SolrTemplate solrTemplate;

  private final RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public ItemSearchServiceImpl(SolrTemplate solrTemplate, RedisTemplate<String, Object> redisTemplate) {
    this.solrTemplate = solrTemplate;
    this.redisTemplate = redisTemplate;
  }

  @Override
  public Map<String, Object> search(Map<String, Object> searchMap) {
    Map<String, Object> map = new HashMap<>(6);

    //1.查询列表
    map.putAll(searchList(searchMap));

    //2.分组查询 商品分类列表
    List<String> categoryList = searchCategoryList(searchMap);
    map.put("categoryList", categoryList);

    //3.查询品牌和规格列表
    queryBrandListAndSpecList(searchMap, map, categoryList);

    return map;
  }

  @Override
  public void importList(List<TbItem> list) {
    solrTemplate.saveBeans(list);
    solrTemplate.commit();
  }

  @Override
  public void deleteByGoodsIds(List goodsIds) {
    Query query = new SimpleQuery("*:*");
    Criteria criteria = new Criteria("item_goods_id").in(goodsIds);
    query.addCriteria(criteria);
    solrTemplate.delete(query);
    solrTemplate.commit();
  }

  /**
   * 查询品牌和规格列表
   *
   * @param searchMap    查询条件
   * @param map          返回对象
   * @param categoryList 商品分类列表
   */
  private void queryBrandListAndSpecList(Map<String, Object> searchMap, Map<String, Object> map, List<String> categoryList) {
    String category = (String) searchMap.get("category");
    if (StringUtils.isNotBlank(category)) {
      map.putAll(searchBrandAndSpecList(category));
    } else {
      if (CollectionUtils.isNotEmpty(categoryList)) {
        map.putAll(searchBrandAndSpecList(categoryList.get(0)));
      }
    }
  }

  /**
   * 查询列表
   *
   * @param searchMap 查询条件
   * @return Map
   */
  private Map<String, Object> searchList(Map<String, Object> searchMap) {
    //高亮查询选项初始化
    HighlightQuery query = new SimpleHighlightQuery();

    //空格处理
    String keywords = (String) searchMap.get("keywords");
    //关键字去掉空格
    searchMap.put("keywords", keywords.replace(" ", ""));

    //1.1 关键字查询
    Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
    query.addCriteria(criteria);

    //1.2 按商品分类过滤
    filterByCondition(searchMap, query, "category", "item_category");

    //1.3 按品牌过滤
    filterByCondition(searchMap, query, "brand", "item_brand");

    //1.4 按规格过滤
    filterBySpec(searchMap, query);

    //1.5按价格过滤
    filterByPrice(searchMap, query);

    //1.6 分页
    paging(searchMap, query);

    //1.7 排序
    sort(searchMap, query);

    //获取高亮结果集
    HighlightPage<TbItem> page = highlightOption(query);

    Map<String, Object> map = new HashMap<>(3);
    //数据集
    map.put("rows", page.getContent());
    //总页数
    map.put("totalPages", page.getTotalPages());
    //总记录数
    map.put("total", page.getTotalElements());

    return map;
  }

  /**
   * 设置高亮选项
   *
   * @param query 查询对象
   * @return 高亮结果集
   */
  @NotNull
  private HighlightPage<TbItem> highlightOption(HighlightQuery query) {
    //高亮域
    HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");
    //前缀
    highlightOptions.setSimplePrefix("<em style='color:red'>");
    //后缀
    highlightOptions.setSimplePostfix("</em>");
    //为查询对象设置高亮选项
    query.setHighlightOptions(highlightOptions);
    //高亮页对象
    HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
    //高亮入口集合(每条记录的高亮入口)
    List<HighlightEntry<TbItem>> entryList = page.getHighlighted();
    for (HighlightEntry<TbItem> entry : entryList) {
      //获取高亮列表(高亮域的个数)
      List<HighlightEntry.Highlight> highlightList = entry.getHighlights();
      if (highlightList.size() > 0 && highlightList.get(0).getSnipplets().size() > 0) {
        TbItem item = entry.getEntity();
        item.setTitle(highlightList.get(0).getSnipplets().get(0));
      }
    }
    return page;
  }

  /**
   * 排序设置
   *
   * @param searchMap 查询条件
   * @param query     查询对象
   */
  private void sort(Map<String, Object> searchMap, HighlightQuery query) {
    //升序ASC 降序DESC
    String sortValue = (String) searchMap.get("sort");
    //排序字段
    String sortField = (String) searchMap.get("sortField");

    if (StringUtils.isNotBlank(sortValue)) {
      String asc = "ASC";
      if (asc.equals(sortValue)) {
        Sort sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
        query.addSort(sort);
      }
      String desc = "DESC";
      if (desc.equals(sortValue)) {
        Sort sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
        query.addSort(sort);
      }
    }
  }

  /**
   * 分页设置
   *
   * @param searchMap 查询条件
   * @param query     查询对象
   */
  private void paging(Map<String, Object> searchMap, HighlightQuery query) {
    //获取页码
    Integer pageNo = (Integer) searchMap.get("pageNo");
    if (pageNo == null) {
      pageNo = 1;
    }
    //获取页大小
    Integer pageSize = (Integer) searchMap.get("pageSize");
    if (pageSize == null) {
      pageSize = 20;
    }

    //起始索引
    query.setOffset((pageNo - 1) * pageSize);
    //每页记录数
    query.setRows(pageSize);
  }

  /**
   * 根据价格条件过滤
   *
   * @param searchMap 查询条件
   * @param query     查询镀锡
   */
  private void filterByPrice(Map<String, Object> searchMap, HighlightQuery query) {
    String price1 = "price";
    if (StringUtils.isNotBlank((String) searchMap.get(price1))) {
      String[] price = ((String) searchMap.get(price1)).split("-");
      //如果最低价格不等于0
      String s1 = "0";
      if (!s1.equals(price[0])) {
        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
        filterQuery.addCriteria(filterCriteria);
        query.addFilterQuery(filterQuery);
      }
      //如果最高价格不等于*
      String s = "*";
      if (!s.equals(price[1])) {
        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria filterCriteria = new Criteria("item_price").lessThanEqual(price[1]);
        filterQuery.addCriteria(filterCriteria);
        query.addFilterQuery(filterQuery);
      }
    }
  }

  /**
   * 根据规格过滤
   *
   * @param searchMap 查询条件
   * @param query     查询对象
   */
  private void filterBySpec(Map<String, Object> searchMap, HighlightQuery query) {
    String spec = "spec";
    if (searchMap.get(spec) != null) {
      Map specMap = (Map) searchMap.get(spec);
      for (Object key : specMap.keySet()) {
        FilterQuery filterQuery = new SimpleFilterQuery();
        String encodeKey = null;
        try {
          //将key进行编码
          encodeKey = URLEncoder.encode((String) key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        assert encodeKey != null;
        String replaceKey = encodeKey.replaceAll("%", "");
        Criteria filterCriteria = new Criteria("item_spec_" + replaceKey).is(specMap.get(key));
        filterQuery.addCriteria(filterCriteria);
        query.addFilterQuery(filterQuery);
      }
    }
  }

  /**
   * 设置过滤查询条件
   *
   * @param searchMap   查询条件
   * @param query       查询对象
   * @param key         查询条件key
   * @param queryColumn 查询字段
   */
  private void filterByCondition(Map<String, Object> searchMap, HighlightQuery query, String key, String queryColumn) {
    //如果用户选择了品牌或规格等条件
    if (StringUtils.isNotBlank((String) searchMap.get(key))) {
      FilterQuery filterQuery = new SimpleFilterQuery();
      Criteria filterCriteria = new Criteria(queryColumn).is(searchMap.get(key));
      filterQuery.addCriteria(filterCriteria);
      query.addFilterQuery(filterQuery);
    }
  }

  /**
   * 分组查询（查询商品分类列表）
   *
   * @return List<String>
   */
  private List<String> searchCategoryList(Map searchMap) {
    List<String> list = new ArrayList<>();
    Query query = new SimpleQuery("*:*");
    //根据关键字查询
    Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
    query.addCriteria(criteria);

    //设置分组选项
    GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
    query.setGroupOptions(groupOptions);

    //获取分组页
    GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);

    //获取分组结果对象
    GroupResult<TbItem> groupResult = page.getGroupResult("item_category");

    //获取分组入口页
    Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();

    //获取分组入口集合
    List<GroupEntry<TbItem>> entryList = groupEntries.getContent();

    entryList.parallelStream().forEach(e -> {
      //将分组的结果添加到返回值中
      list.add(e.getGroupValue());
    });

    return list;
  }

  /**
   * 根据商品分类名称查询品牌和规格列表
   *
   * @param category 商品分类名称
   * @return Map
   */
  private Map<String, Object> searchBrandAndSpecList(String category) {
    Map<String, Object> map = new HashMap<>(2);
    //1.根据商品分类名称得到模板ID
    Long templateId = (Long) redisTemplate.boundHashOps(ITEM_CAT).get(category);
    Optional.ofNullable(templateId).ifPresent(t -> {
      //2.根据模板ID获取品牌列表
      List brandList = (List) redisTemplate.boundHashOps(BRAND_LIST).get(t);
      map.put("brandList", brandList);

      //3.根据模板ID获取规格列表
      List specList = (List) redisTemplate.boundHashOps(SPEC_LIST).get(t);
      map.put("specList", specList);
    });
    return map;
  }

}
