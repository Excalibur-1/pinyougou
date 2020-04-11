package com.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxl
 */
@Component
public class SolrUtil {

  /**
   * 审核通过状态
   */
  private final static String AUDIT_PASS = "1";

  private final TbItemMapper itemMapper;

  private final SolrTemplate solrTemplate;

  @Autowired
  public SolrUtil(TbItemMapper itemMapper, SolrTemplate solrTemplate) {
    this.itemMapper = itemMapper;
    this.solrTemplate = solrTemplate;
  }

  private void importItemData() {
    TbItemExample example = new TbItemExample();
    TbItemExample.Criteria criteria = example.createCriteria();
    //审核通过才导入
    criteria.andStatusEqualTo(AUDIT_PASS);
    List<TbItem> itemList = itemMapper.selectByExample(example);
    itemList.parallelStream().forEach(s -> {
      //提取规格json字符串转换为map
      Map map = JSON.parseObject(s.getSpec(), Map.class);
      Map<String, String> stringMap = new HashMap<>(1);
      for (Object aSet : map.keySet()) {
        String next = (String) aSet;
        String encode = null;
        try {
          encode = URLEncoder.encode(next, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        assert encode != null;
        stringMap.put(encode.replaceAll("%", ""), (String) map.get(next));
      }
      s.setSpecMap(stringMap);
    });
    solrTemplate.saveBeans(itemList);
    solrTemplate.commit();
  }

  public static void main(String[] args) {
    String configLocation = "classpath*:spring/applicationContext*.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
    SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
    solrUtil.importItemData();
  }
}
