package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.TbUserExample;
import com.pinyougou.pojo.TbUserExample.Criteria;
import com.pinyougou.user.service.UserService;
import entity.PageResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import util.RandomCodeUtils;

import javax.jms.Destination;
import javax.jms.MapMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Constants.RedisCacheKey.SMS_CODE;

/**
 * 服务实现层
 *
 * @author gxl
 */
@Service
public class UserServiceImpl implements UserService {

  @Value("${template_code}")
  private String templateCode;

  @Value("${sign_name}")
  private String signName;

  private final TbUserMapper userMapper;

  private final RedisTemplate<String, String> redisTemplate;

  private final JmsTemplate jmsTemplate;

  private final Destination smsDestination;

  @Autowired
  public UserServiceImpl(TbUserMapper userMapper,
                         RedisTemplate<String, String> redisTemplate,
                         JmsTemplate jmsTemplate,
                         Destination smsDestination) {
    this.userMapper = userMapper;
    this.redisTemplate = redisTemplate;
    this.jmsTemplate = jmsTemplate;
    this.smsDestination = smsDestination;
  }

  @Override
  public List<TbUser> findAll() {
    return userMapper.selectByExample(null);
  }

  @Override
  public PageResult findPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(null);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public void add(TbUser user) {
    //用户注册时间
    Date date = new Date();
    user.setCreated(date);
    //修改时间
    user.setUpdated(date);
    //注册来源,当前写死，后期从前端传递
    user.setSourceType("1");
    //密码加密
    user.setPassword(DigestUtils.md5Hex(user.getPassword()));
    userMapper.insert(user);
  }

  @Override
  public void update(TbUser user) {
    userMapper.updateByPrimaryKey(user);
  }

  @Override
  public TbUser findOne(Long id) {
    return userMapper.selectByPrimaryKey(id);
  }

  @Override
  public void delete(Long[] ids) {
    for (Long id : ids) {
      userMapper.deleteByPrimaryKey(id);
    }
  }

  @Override
  public PageResult findPage(TbUser user, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    TbUserExample example = getTbUserExample(user);
    Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(example);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Override
  public void createSmsCode(final String phone) {
    //1.生成一个6位随机数（验证码）
    final String smsCode = RandomCodeUtils.getRandomCode();

    //2.将验证码放入redis
    redisTemplate.boundHashOps(SMS_CODE).put(phone, smsCode);

    //3.将短信内容发送给activeMQ
    jmsTemplate.send(smsDestination, session -> {
      MapMessage message = session.createMapMessage();
      //手机号
      message.setString("mobile", phone);
      //验证码
      message.setString("template_code", templateCode);
      //签名
      message.setString("sign_name", signName);
      //使用map的原因是增强扩展性，因为后期可能会有其他参数加入
      Map<String, String> map = new HashMap<>(1);
      map.put("number", smsCode);
      message.setString("param", JSON.toJSONString(map));
      return message;
    });
  }

  @Override
  public boolean checkSmsCode(String phone, String code) {
    String systemCode = (String) redisTemplate.boundHashOps(SMS_CODE).get(phone);
    if (systemCode == null) {
      return false;
    }
    return systemCode.equals(code);
  }

  @NotNull
  private TbUserExample getTbUserExample(TbUser user) {
    TbUserExample example = new TbUserExample();
    Criteria criteria = example.createCriteria();

    if (user != null) {
      if (user.getUsername() != null && user.getUsername().length() > 0) {
        criteria.andUsernameLike("%" + user.getUsername() + "%");
      }
      if (user.getPassword() != null && user.getPassword().length() > 0) {
        criteria.andPasswordLike("%" + user.getPassword() + "%");
      }
      if (user.getPhone() != null && user.getPhone().length() > 0) {
        criteria.andPhoneLike("%" + user.getPhone() + "%");
      }
      if (user.getEmail() != null && user.getEmail().length() > 0) {
        criteria.andEmailLike("%" + user.getEmail() + "%");
      }
      if (user.getSourceType() != null && user.getSourceType().length() > 0) {
        criteria.andSourceTypeLike("%" + user.getSourceType() + "%");
      }
      if (user.getNickName() != null && user.getNickName().length() > 0) {
        criteria.andNickNameLike("%" + user.getNickName() + "%");
      }
      if (user.getName() != null && user.getName().length() > 0) {
        criteria.andNameLike("%" + user.getName() + "%");
      }
      if (user.getStatus() != null && user.getStatus().length() > 0) {
        criteria.andStatusLike("%" + user.getStatus() + "%");
      }
      if (user.getHeadPic() != null && user.getHeadPic().length() > 0) {
        criteria.andHeadPicLike("%" + user.getHeadPic() + "%");
      }
      if (user.getQq() != null && user.getQq().length() > 0) {
        criteria.andQqLike("%" + user.getQq() + "%");
      }
      if (user.getIsMobileCheck() != null && user.getIsMobileCheck().length() > 0) {
        criteria.andIsMobileCheckLike("%" + user.getIsMobileCheck() + "%");
      }
      if (user.getIsEmailCheck() != null && user.getIsEmailCheck().length() > 0) {
        criteria.andIsEmailCheckLike("%" + user.getIsEmailCheck() + "%");
      }
      if (user.getSex() != null && user.getSex().length() > 0) {
        criteria.andSexLike("%" + user.getSex() + "%");
      }
    }
    return example;
  }

}
