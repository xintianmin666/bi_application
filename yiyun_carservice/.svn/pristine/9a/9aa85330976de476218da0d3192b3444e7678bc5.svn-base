package com.carservice.project.business.service;

import com.carservice.project.business.domain.COrder;
import com.carservice.project.business.domain.COrderGoods;
import com.carservice.project.business.util.ResultTab;

import java.util.List;

public interface COrderService{

    ResultTab getOrderList(COrder cOrder) throws Exception;

    void updateOrderGoods(COrderGoods cOrderGoods) throws Exception;

    void getPhone(COrderGoods cOrderGoods) throws Exception;

    void modifyBookTime(COrder cOrder) throws Exception;

    void modifyOrderPriceAndDescribe(COrderGoods cOrderGoods) throws Exception;

    List<COrderGoods> getCheckOrderGoods(COrderGoods cOrderGoods) throws Exception;

    void checkOrderGoods(COrderGoods cOrderGoods) throws Exception;

    void editRescueInfo(COrderGoods cOrderGoods) throws Exception;

    List<COrder> selectCOrderList(COrder cOrder) throws Exception;

    /**
     * 查询订单
     *
     * @param orderCode 订单ID
     * @return 订单
     */
    public List<COrderGoods> selectCOrderGoodsByOrderCode(String orderCode) throws Exception;

}
