package com.carservice.project.order.service;

import com.carservice.project.order.domain.TRentalcarsMessage;
import com.carservice.project.order.domain.TTaxiOrder;

import java.util.List;

/**
 * 拼包车订单乘客Service接口
 * @author carservice
 * @date 2020-08-13
 */
public interface ITRentalcarsMessageService {
    /**
     * 查询拼包车订单乘客
     * @param id 拼包车订单乘客ID
     * @return 拼包车订单乘客
     */
    public TRentalcarsMessage selectTRentalcarsMessageById(Long id);

    TRentalcarsMessage selectMaxtTRentalcarsMessageById(String busId);

    /**
     * 查询拼包车订单乘客列表
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 拼包车订单乘客集合
     */
    public List< TRentalcarsMessage > selectTRentalcarsMessageList(TRentalcarsMessage tRentalcarsMessage);

    List< TRentalcarsMessage > selectTRentalcarsMessageDetail(TRentalcarsMessage tRentalcarsMessage);

    List< TRentalcarsMessage > selectTRentalcarsMessageByBusId(TRentalcarsMessage tRentalcarsMessage);

    /**
     * 新增拼包车订单乘客
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 结果
     */
    public int insertTRentalcarsMessage(TRentalcarsMessage tRentalcarsMessage);

    /**
     * 修改拼包车订单乘客
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 结果
     */
    public int updateTRentalcarsMessage(TRentalcarsMessage tRentalcarsMessage);

    /**
     * 批量删除拼包车订单乘客
     * @param ids 需要删除的拼包车订单乘客ID
     * @return 结果
     */
    public int deleteTRentalcarsMessageByIds(Long[] ids);

    /**
     * 删除拼包车订单乘客信息
     * @param id 拼包车订单乘客ID
     * @return 结果
     */
    public int deleteTRentalcarsMessageById(Long id);

    int updateBusIdByIds(String busId, String receptionNum, String receptionTime, String ids);

    int updatePassengerNum(int passengerNum, String ids);

    int updateBusIdByOrderCode(String busId, String orderCode);

    int confirmBusData(TTaxiOrder tTaxiOrder);
}
