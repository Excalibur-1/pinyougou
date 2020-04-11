package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * 删除商品详情页面消息监听器
 *
 * @author gxl
 */
@Component
public class PageDeleteListener implements MessageListener {

  private final ItemPageService itemPageService;

  @Autowired
  public PageDeleteListener(ItemPageService itemPageService) {
    this.itemPageService = itemPageService;
  }

  @Override
  public void onMessage(Message message) {
    ObjectMessage objectMessage = (ObjectMessage) message;
    try {
      Long[] goodsIds = (Long[]) objectMessage.getObject();
      itemPageService.deleteItemHtml(goodsIds);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
