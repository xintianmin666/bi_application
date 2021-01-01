package com.yiyun.yiyuncarservice.utils;

import com.yiyun.yiyuncarservice.controller.CarOrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author hxx
 * @version 1.0
 * @title: MyScheduledTask
 * @projectName yiyun
 * @description: 定时任务
 * @date 2020/2/21 16:22
 */
@Component
public class MyScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(MyScheduledTask.class);
    @Autowired
    private CarOrderController orderController;


    @Scheduled(cron = "0 0/2 * * * ?")
    public void systemRefund() {
        orderController.systemRefund();
//        orderController.orderRefund();
    }


    @Scheduled(cron = "0 */2 * * * ?")
    public void updateOrderStatus() {
        try {
            orderController.systemQueryWeChatOrder();
            orderController.queryWeChatOrderStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
