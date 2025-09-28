package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis序列化器
        //不加序列化器中文会以hex形式显示.此处报类型转换异常的错误 记得去重新设置redis配置，对值的序列化使用GenericJackson2JsonRedisSerializer()
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
