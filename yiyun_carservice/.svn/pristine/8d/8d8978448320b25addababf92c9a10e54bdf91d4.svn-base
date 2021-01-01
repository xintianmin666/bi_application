package com.carservice.framework.redis;

import com.carservice.project.oper.domain.TDriverInfo;
import com.carservice.project.oper.service.ITDriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Autowired
    ITDriverInfoService driverInfoService;

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
        if (expiredKey.contains("driver_disable_end_time:")){
            String driverId = expiredKey.split(":")[1];
            TDriverInfo driverInfo = new TDriverInfo();
            driverInfo.setDriverId(driverId);
            driverInfo.setState("1");
            driverInfo.setDisableEndTime("");
            driverInfoService.updateTDriverInfo(driverInfo);
        }
        System.out.println(expiredKey);
    }
}