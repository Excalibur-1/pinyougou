package com.pinyougou.search.service.impl;

import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Arrays;

/**
 * 从solr删除商品数据-消息消费监听器
 *
 * @author gxl
 */
@Component
public class ItemDeleteListener implements MessageListener {

  private final ItemSearchService itemSearchService;

  @Autowired
  public ItemDeleteListener(ItemSearchService itemSearchService) {
    this.itemSearchService = itemSearchService;
  }

  @Override
  public void onMessage(Message message) {
    ObjectMessage objectMessage = (ObjectMessage) message;
    try {
      Long[] goodsIds = (Long[]) objectMessage.getObject();
      itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
