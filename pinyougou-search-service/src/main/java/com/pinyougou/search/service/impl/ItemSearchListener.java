package com.pinyougou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * 导入商品数据到solr-消息消费监听器
 *
 * @author gxl
 */
@Component
public class ItemSearchListener implements MessageListener {

  private final ItemSearchService itemSearchService;

  @Autowired
  public ItemSearchListener(ItemSearchService itemSearchService) {
    this.itemSearchService = itemSearchService;
  }

  @Override
  public void onMessage(Message message) {
    TextMessage textMessage = (TextMessage) message;
    try {
      //json字符串
      String text = textMessage.getText();
      List<TbItem> itemList = JSON.parseArray(text, TbItem.class);
      itemSearchService.importList(itemList);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
