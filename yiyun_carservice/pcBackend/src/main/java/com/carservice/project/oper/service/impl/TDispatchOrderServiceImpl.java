package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TDispatchOrderMapper;
import com.carservice.project.oper.domain.TDispatchOrder;
import com.carservice.project.oper.service.ITDispatchOrderService;

/**
 * 调度订单Service业务层处理
 * 
 * @author carservice
 * @date 2020-05-16
 */
@Service
public class TDispatchOrderServiceImpl implements ITDispatchOrderService 
{
    @Autowired
    private TDispatchOrderMapper tDispatchOrderMapper;

    /**
     * 查询调度订单
     * 
     * @param dispatchOrderId 调度订单ID
     * @return 调度订单
     */
    @Override
    public TDispatchOrder selectTDispatchOrderById(String dispatchOrderId)
    {
        return tDispatchOrderMapper.selectTDispatchOrderById(dispatchOrderId);
    }

    /**
     * 查询调度订单列表
     * 
     * @param tDispatchOrder 调度订单
     * @return 调度订单
     */
    @Override
    public List<TDispatchOrder> selectTDispatchOrderList(TDispatchOrder tDispatchOrder)
    {
        return tDispatchOrderMapper.selectTDispatchOrderList(tDispatchOrder);
    }

    /**
     * 新增调度订单
     * 
     * @param tDispatchOrder 调度订单
     * @return 结果
     */
    @Override
    public int insertTDispatchOrder(TDispatchOrder tDispatchOrder)
    {
        tDispatchOrder.setCreateTime(DateUtils.getNowDate());
        return tDispatchOrderMapper.insertTDispatchOrder(tDispatchOrder);
    }

    /**
     * 修改调度订单
     * 
     * @param tDispatchOrder 调度订单
     * @return 结果
     */
    @Override
    public int updateTDispatchOrder(TDispatchOrder tDispatchOrder)
    {
        return tDispatchOrderMapper.updateTDispatchOrder(tDispatchOrder);
    }

    /**
     * 批量删除调度订单
     * 
     * @param dispatchOrderIds 需要删除的调度订单ID
     * @return 结果
     */
    @Override
    public int deleteTDispatchOrderByIds(String[] dispatchOrderIds)
    {
        return tDispatchOrderMapper.deleteTDispatchOrderByIds(dispatchOrderIds);
    }

    /**
     * 删除调度订单信息
     * 
     * @param dispatchOrderId 调度订单ID
     * @return 结果
     */
    @Override
    public int deleteTDispatchOrderById(String dispatchOrderId)
    {
        return tDispatchOrderMapper.deleteTDispatchOrderById(dispatchOrderId);
    }
}
