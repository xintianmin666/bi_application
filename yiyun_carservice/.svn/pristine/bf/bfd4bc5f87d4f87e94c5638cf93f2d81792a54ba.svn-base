package com.carservice.project.order.service.impl;

import com.carservice.project.oper.domain.TVehicleTaskStatus;
import com.carservice.project.oper.service.impl.TVehicleTaskStatusServiceImpl;
import com.carservice.project.order.domain.TTaxiOrder;
import com.carservice.project.order.mapper.TTaxiOrderMapper;
import com.carservice.project.order.service.ITTaxiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出租车订单Service业务层处理
 * @author carservice
 * @date 2020-07-03
 */
@Service
public class TTaxiOrderServiceImpl implements ITTaxiOrderService {
    @Autowired
    private TTaxiOrderMapper tTaxiOrderMapper;

    @Autowired
    private TVehicleTaskStatusServiceImpl vehicleTaskStatusService;

    /**
     * 查询出租车订单
     * @param id 出租车订单ID
     * @return 出租车订单
     */
    @Override
    public TTaxiOrder selectTTaxiOrderById(Long id) {
        return tTaxiOrderMapper.selectTTaxiOrderById(id);
    }

    /**
     * 查询出租车订单列表
     * @param tTaxiOrder 出租车订单
     * @return 出租车订单
     */
    @Override
    public List< TTaxiOrder > selectTTaxiOrderList(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.selectTTaxiOrderList(tTaxiOrder);
    }

    @Override
    public List< TTaxiOrder > selectWaitList(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.selectWaitList(tTaxiOrder);
    }

    @Override
    public List< TTaxiOrder > getCanUseOrderList(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.getCanUseOrderList(tTaxiOrder);
    }

    /**
     * 查询拼包车订单列表
     * @param tTaxiOrder 出租车订单
     * @return 出租车订单
     */
    @Override
    public List< TTaxiOrder > selectTRentalOrderList(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.selectTRentalOrderList(tTaxiOrder);
    }

    /**
     * 新增出租车订单
     * @param tTaxiOrder 出租车订单
     * @return 结果
     */
    @Override
    public int insertTTaxiOrder(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.insertTTaxiOrder(tTaxiOrder);
    }

    /**
     * 修改出租车订单
     * @param tTaxiOrder 出租车订单
     * @return 结果
     */
    @Override
    public int updateTTaxiOrder(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.updateTTaxiOrder(tTaxiOrder);
    }

    /**
     * 批量删除出租车订单
     * @param ids 需要删除的出租车订单ID
     * @return 结果
     */
    @Override
    public int deleteTTaxiOrderByIds(Long[] ids) {
        return tTaxiOrderMapper.deleteTTaxiOrderByIds(ids);
    }

    /**
     * 删除出租车订单信息
     * @param id 出租车订单ID
     * @return 结果
     */
    @Override
    public int deleteTTaxiOrderById(Long id) {
        return tTaxiOrderMapper.deleteTTaxiOrderById(id);
    }

    @Override
    public int updateOrderPrice(TTaxiOrder tTaxiOrder) {
        return tTaxiOrderMapper.updateOrderPrice(tTaxiOrder);
    }

    @Override
    public int updateOrderBusId(String busId, String driverId, String driverCarNo, String driverName, String driverPhone, String ids) {
        return tTaxiOrderMapper.updateOrderBusId(busId, driverId, driverCarNo, driverName, driverPhone, ids);
    }

    @Override
    public int updateOrderStatus(String orderCode) {
        return tTaxiOrderMapper.updateOrderStatus(orderCode);
    }

    @Override
    public TTaxiOrder selectTTaxiOrderByOrderCode(String orderCode) {
        return tTaxiOrderMapper.selectTTaxiOrderByOrderCode(orderCode);
    }

    @Override
    public int updateOrderByBusId(String busId) {
        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(busId);
        return tTaxiOrderMapper.updateOrderForBusId(busId, tVehicleTaskStatus.getDriverId(), tVehicleTaskStatus.getVehiclePlateNo(), tVehicleTaskStatus.getDriverName(), tVehicleTaskStatus.getDriverPhone());
    }

    @Override
    public TTaxiOrder selectTTaxiOrderByBusId(String busId) {
        return tTaxiOrderMapper.selectTTaxiOrderByBusId(busId);
    }

    @Override
    public int delByBusId(String busId) {
        return tTaxiOrderMapper.delByBusId(busId);
    }
}
