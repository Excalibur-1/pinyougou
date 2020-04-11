package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听类（用于生成网页）
 *
 * @author gxl
 */
@Component
public class PageListener implements MessageListener {

  private final ItemPageService itemPageService;

  @Autowired
  public PageListener(ItemPageService itemPageService) {
    this.itemPageService = itemPageService;
  }

  @Override
  public void onMessage(Message message) {
    TextMessage textMessage = (TextMessage) message;
    try {
      String text = textMessage.getText();
      itemPageService.genItemHtml(Long.parseLong(text));
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
