package com.yiyun.yiyuncarservice.utils;

import com.yiyun.yiyuncarservice.controller.CarOrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hxx
 * @version 1.0
 * @title: RedisKeyExpirationListener
 * @projectName 01.后台代码
 * @description: TODO
 * @date 2020/3/29 14:30
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    CarOrderController carOrderController;
    private static final Logger logger = LoggerFactory.getLogger(CarOrderController.class);

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        if (expiredKey.startsWith("Order:")) {
            logger.info("测试是否能监听到失效的key===" + expiredKey);
            String[] orders = expiredKey.split(":");
            logger.info("测试是否能监听到失效的订单号===" + orders[1]);
            Map paraMap = new HashMap<>();
            paraMap.put("orderCode", orders[1].split(",")[0]);
            paraMap.put("mobile", orders[1].split(",")[1]);
            paraMap.put("orderStatus", orders[1].split(",")[2]);
            carOrderController.systemCancelOrder(paraMap);
        }
    }
}
