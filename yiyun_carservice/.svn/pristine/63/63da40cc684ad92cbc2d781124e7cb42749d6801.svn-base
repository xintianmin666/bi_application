package com.carservice.project.oper.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class NumUtil {

    private static final Logger log = LoggerFactory.getLogger(NumUtil.class);

    private static long tmpID = 0;
    private static final long LOCK_TIME = 1;
    private static final long INCREASE_STEP = 1;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
    private static final Lock LOCK = new ReentrantLock();


    public static long nextPkId() throws InterruptedException {
        //当前：（年、月、日、时、分、秒、毫秒）
        long timeCount;
        if (LOCK.tryLock(LOCK_TIME, TimeUnit.SECONDS)) {
            timeCount = Long.parseLong(sdf.format(new Date()));
            try {
                if (tmpID < timeCount) {
                    tmpID = timeCount;
                } else {
                    tmpID += INCREASE_STEP;
                    timeCount = tmpID;
                }
                return timeCount;
            } finally {
                LOCK.unlock();
            }
        } else {
            log.error("lock failed");
            return nextPkId();

        }
    }
}

