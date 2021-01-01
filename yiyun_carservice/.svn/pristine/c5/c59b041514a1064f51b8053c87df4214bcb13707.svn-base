package com.carservice.project.order.mapper;

import com.carservice.project.order.domain.TTaxiOrder;

import java.util.List;

/**
 * 出租车订单Mapper接口
 * @author carservice
 * @date 2020-07-03
 */
public interface TTaxiOrderMapper {
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

    public List< TTaxiOrder > selectWaitList(TTaxiOrder tTaxiOrder);

    /**
     * 查询出租车订单列表
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
     * 删除出租车订单
     * @param id 出租车订单ID
     * @return 结果
     */
    public int deleteTTaxiOrderById(Long id);

    /**
     * 批量删除出租车订单
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTTaxiOrderByIds(Long[] ids);

    public int updateOrderPrice(TTaxiOrder tTaxiOrder);

    public int updateOrderBusId(String busId, String driverId, String driverCarNo, String driverName, String driverPhone, String ids);

    public int updateOrderStatus(String orderCode);

    public TTaxiOrder selectTTaxiOrderByOrderCode(String orderCode);

    public int updateOrderByBusId(String busId);

    public int updateOrderForBusId(String busId, String driverId, String driverCarNo, String driverName, String driverPhone);

    public TTaxiOrder selectTTaxiOrderByBusId(String busId);

    public int delByBusId(String busId);

    public List< TTaxiOrder > getCanUseOrderList(TTaxiOrder tTaxiOrder);
}
