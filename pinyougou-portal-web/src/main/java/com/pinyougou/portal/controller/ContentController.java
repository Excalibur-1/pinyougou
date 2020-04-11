package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 广告控制类
 *
 * @author gxl
 */
@RestController
@RequestMapping("/content")
public class ContentController {

  @Reference
  private ContentService contentService;

  @RequestMapping("/findByCategoryId")
  public List<TbContent> findByCategoryId(Long categoryId) {
    return contentService.findByCategoryId(categoryId);
  }
}
