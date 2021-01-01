package com.yiyun.yiyuncarservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author hxx
 * @version 1.0
 * @title: RedisConfiguration
 * @projectName 01.后台代码
 * @description: TODO
 * @date 2020/3/29 7:47
 */
@Configuration
public class RedisListenerConfig {
  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    //        container.addMessageListener(new RedisExpiredListener(), new
    // PatternTopic("__keyevent@0__:expired"));
    return container;
  }
}
