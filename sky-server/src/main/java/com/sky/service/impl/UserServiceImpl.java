package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());
        //微信服务器端没有对应的openid
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断是否是新用户,是新用户就必须创建
        User user = userMapper.selectByOpenId(openid);

        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    /**
     * 给微信服务器端发送HTTP请求获取登录用户的openid
     * @param code 微信前端传送过来的用户code
     * @return
     */
    private String getOpenid(String code) {

        HashMap<String, String> paras = new HashMap<>();
        paras.put("appid", weChatProperties.getAppid());
        paras.put("secret", weChatProperties.getSecret());
        paras.put("js_code", code);
        paras.put("grant_type", "authorization_code");
        String s = HttpClientUtil.doGet(WX_LOGIN, paras);

        JSONObject jsonObject = JSON.parseObject(s);

        return jsonObject.getString("openid");
    }
}
