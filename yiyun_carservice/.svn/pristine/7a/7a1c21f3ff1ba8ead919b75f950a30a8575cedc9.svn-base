package com.carservice.project.order.service;

import com.carservice.project.order.domain.TTaxiOrder;

import java.util.List;

/**
 * 出租车订单Service接口
 * @author carservice
 * @date 2020-07-03
 */
public interface ITTaxiOrderService {
    /**
     * 查询出租车订单
     * @param id 出租车订单ID
     * @return 出租车订单
     */
    public TTaxiOrder selectTTaxiOrderById(Long id);

    /**
     * 查询出租车订单列表
     * @param tTaxiOrder 出租车订单
     * @return 出租车订单集合
     */
    public List< TTaxiOrder > selectTTaxiOrderList(TTaxiOrder tTaxiOrder);

    List< TTaxiOrder > selectWaitList(TTaxiOrder tTaxiOrder);

    List< TTaxiOrder > getCanUseOrderList(TTaxiOrder tTaxiOrder);

    /**
     * 查询拼包车订单列表
     * @param tTaxiOrder 出租车订单
     * @return 出租车订单集合
     */
    public List< TTaxiOrder > selectTRentalOrderList(TTaxiOrder tTaxiOrder);

    /**
     * 新增出租车订单
     * @param tTaxiOrder 出租车订单
     * @return 结果
     */
    public int insertTTaxiOrder(TTaxiOrder tTaxiOrder);

    /**
     * 修改出租车订单
     * @param tTaxiOrder 出租车订单
     * @return 结果
     */
    public int updateTTaxiOrder(TTaxiOrder tTaxiOrder);

    /**
     * 批量删除出租车订单
     * @param ids 需要删除的出租车订单ID
     * @return 结果
     */
    public int deleteTTaxiOrderByIds(Long[] ids);

    /**
     * 删除出租车订单信息
     * @param id 出租车订单ID
     * @return 结果
     */
    public int deleteTTaxiOrderById(Long id);

    public int updateOrderPrice(TTaxiOrder tTaxiOrder);

    int updateOrderBusId(String busId, String driverId, String driverCarNo, String driverName, String driverPhone, String ids);

    int updateOrderStatus(String orderCode);

    TTaxiOrder selectTTaxiOrderByOrderCode(String orderCode);

    int updateOrderByBusId(String busId);

    TTaxiOrder selectTTaxiOrderByBusId(String busId);

    int delByBusId(String busId);
}
