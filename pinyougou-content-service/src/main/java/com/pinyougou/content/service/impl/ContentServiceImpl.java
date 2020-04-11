package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.mapper.TbContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.pojo.TbContentExample;
import com.pinyougou.pojo.TbContentExample.Criteria;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

/**
 * 服务实现层
 *
 * @author gxl
 */
@Service
public class ContentServiceImpl implements ContentService {

  private final TbContentMapper contentMapper;

  private final RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public ContentServiceImpl(TbContentMapper contentMapper, RedisTemplate<String, Object> redisTemplate) {
    this.contentMapper = contentMapper;
    this.redisTemplate = redisTemplate;
  }

  @Override
  public List<TbContent> findAll() {
    return contentMapper.selectByExample(null);
  }

  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public void add(TbContent content) {
    contentMapper.insert(content);
    // 清除缓存
    redisTemplate.boundHashOps("content").delete(content.getCategoryId());
  }

  @Override
  public void update(TbContent content) {
    TbContent oldContent = contentMapper.selectByPrimaryKey(content.getId());
    // 清除之前分类的广告缓存
    redisTemplate.boundHashOps("content").delete(oldContent.getCategoryId());

    contentMapper.updateByPrimaryKey(content);
    // 清除缓存
    if (!Objects.equals(content.getCategoryId(), oldContent.getCategoryId())) {
      redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }
  }

  @Override
  public TbContent findOne(Long id) {
    return contentMapper.selectByPrimaryKey(id);
  }

  @Override
  public void delete(Long[] ids) {
    for (Long id : ids) {
      TbContent tbContent = contentMapper.selectByPrimaryKey(id);
      redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());

      contentMapper.deleteByPrimaryKey(id);
    }
  }

  @Override
  public PageResult findPage(TbContent content, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);

    TbContentExample example = new TbContentExample();
    Criteria criteria = example.createCriteria();

    if (content != null) {
      if (content.getTitle() != null && content.getTitle().length() > 0) {
        criteria.andTitleLike("%" + content.getTitle() + "%");
      }
      if (content.getUrl() != null && content.getUrl().length() > 0) {
        criteria.andUrlLike("%" + content.getUrl() + "%");
      }
      if (content.getPic() != null && content.getPic().length() > 0) {
        criteria.andPicLike("%" + content.getPic() + "%");
      }
      if (content.getStatus() != null && content.getStatus().length() > 0) {
        criteria.andStatusLike("%" + content.getStatus() + "%");
      }
    }

    Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public List<TbContent> findByCategoryId(Long categoryId) {
    // 加入缓存的代码:
    List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);

    if (list == null) {
      System.out.println("查询数据库===================");
      TbContentExample example = new TbContentExample();
      Criteria criteria = example.createCriteria();
      // 有效广告:
      criteria.andStatusEqualTo("1");

      criteria.andCategoryIdEqualTo(categoryId);
      // 排序
      example.setOrderByClause("sort_order");

      list = contentMapper.selectByExample(example);

      redisTemplate.boundHashOps("content").put(categoryId, list);
    } else {
      System.out.println("从缓存中获取====================");
    }

    return list;
  }

}
