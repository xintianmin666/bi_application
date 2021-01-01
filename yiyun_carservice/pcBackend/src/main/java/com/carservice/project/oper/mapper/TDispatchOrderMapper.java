package com.carservice.project.oper.mapper;

import java.util.List;
import com.carservice.project.oper.domain.TDispatchOrder;

/**
 * 调度订单Mapper接口
 * 
 * @author carservice
 * @date 2020-05-16
 */
public interface TDispatchOrderMapper 
{
    /**
     * 查询调度订单
     * 
     * @param dispatchOrderId 调度订单ID
     * @return 调度订单
     */
    public TDispatchOrder selectTDispatchOrderById(String dispatchOrderId);

    /**
     * 查询调度订单列表
     * 
     * @param tDispatchOrder 调度订单
     * @return 调度订单集合
     */
    public List<TDispatchOrder> selectTDispatchOrderList(TDispatchOrder tDispatchOrder);

    /**
     * 新增调度订单
     * 
     * @param tDispatchOrder 调度订单
     * @return 结果
     */
    public int insertTDispatchOrder(TDispatchOrder tDispatchOrder);

    /**
     * 修改调度订单
     * 
     * @param tDispatchOrder 调度订单
     * @return 结果
     */
    public int updateTDispatchOrder(TDispatchOrder tDispatchOrder);

    /**
     * 删除调度订单
     * 
     * @param dispatchOrderId 调度订单ID
     * @return 结果
     */
    public int deleteTDispatchOrderById(String dispatchOrderId);

    /**
     * 批量删除调度订单
     * 
     * @param dispatchOrderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTDispatchOrderByIds(String[] dispatchOrderIds);
}
