package com.carservice.project.order.service.impl;

import com.carservice.project.order.domain.TOrderPrice;
import com.carservice.project.order.domain.TTaxiOrder;
import com.carservice.project.order.mapper.TOrderPriceMapper;
import com.carservice.project.order.service.ITOrderPriceService;
import com.carservice.project.order.service.ITTaxiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包拼车订单价格明细Service业务层处理
 * @author carservice
 * @date 2020-08-14
 */
@Service
public class TOrderPriceServiceImpl implements ITOrderPriceService {
    @Autowired
    private TOrderPriceMapper tOrderPriceMapper;

    @Autowired
    private ITTaxiOrderService tTaxiOrderService;

    /**
     * 查询包拼车订单价格明细
     * @param id 包拼车订单价格明细ID
     * @return 包拼车订单价格明细
     */
    @Override
    public TOrderPrice selectTOrderPriceById(Long id) {
        return tOrderPriceMapper.selectTOrderPriceById(id);
    }

    /**
     * 查询包拼车订单价格明细列表
     * @param tOrderPrice 包拼车订单价格明细
     * @return 包拼车订单价格明细
     */
    @Override
    public List< TOrderPrice > selectTOrderPriceList(TOrderPrice tOrderPrice) {
        return tOrderPriceMapper.selectTOrderPriceList(tOrderPrice);
    }

    /**
     * 新增包拼车订单价格明细
     * @param tOrderPrice 包拼车订单价格明细
     * @return 结果
     */
    @Override
    public Map insertTOrderPrice(TOrderPrice tOrderPrice) {
        String orderCode = tOrderPrice.getOrderCode();
        tOrderPriceMapper.deleteOrderPriceByOrderCode(orderCode);
        List< TOrderPrice > priceList = tOrderPrice.getOrderPriceList();
//        BigDecimal orderAmount = BigDecimal.ZERO;
        double orderAmount = 0;
        for (TOrderPrice orderPrice : priceList) {
            orderPrice.setOrderCode(orderCode);
            tOrderPriceMapper.insertTOrderPrice(orderPrice);
            orderAmount += orderPrice.getChargeAmount();
        }
        System.out.println("转换前的价格===" + orderAmount);
//        System.out.println("转换后的价格===" + orderAmount.doubleValue());
        TTaxiOrder tTaxiOrder = new TTaxiOrder();
        tTaxiOrder.setOrderCode(orderCode);
        tTaxiOrder.setOrderAmount(orderAmount);
        tTaxiOrderService.updateOrderPrice(tTaxiOrder);
        Map result = new HashMap();
        result.put("orderCode", orderCode);
        result.put("orderAmount", orderAmount);
        return result;
    }

    /**
     * 修改包拼车订单价格明细
     * @param tOrderPrice 包拼车订单价格明细
     * @return 结果
     */
    @Override
    public int updateTOrderPrice(TOrderPrice tOrderPrice) {
        return tOrderPriceMapper.updateTOrderPrice(tOrderPrice);
    }

    /**
     * 批量删除包拼车订单价格明细
     * @param ids 需要删除的包拼车订单价格明细ID
     * @return 结果
     */
    @Override
    public int deleteTOrderPriceByIds(Long[] ids) {
        return tOrderPriceMapper.deleteTOrderPriceByIds(ids);
    }

    /**
     * 删除包拼车订单价格明细信息
     * @param id 包拼车订单价格明细ID
     * @return 结果
     */
    @Override
    public int deleteTOrderPriceById(Long id) {
        return tOrderPriceMapper.deleteTOrderPriceById(id);
    }
}
