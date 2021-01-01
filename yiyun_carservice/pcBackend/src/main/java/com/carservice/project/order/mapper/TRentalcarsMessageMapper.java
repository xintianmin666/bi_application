package com.carservice.project.order.mapper;

import com.carservice.project.order.domain.TRentalcarsMessage;

import java.util.List;

/**
 * 拼包车订单乘客Mapper接口
 * @author carservice
 * @date 2020-08-13
 */
public interface TRentalcarsMessageMapper {
    /**
     * 查询拼包车订单乘客
     * @param id 拼包车订单乘客ID
     * @return 拼包车订单乘客
     */
    public TRentalcarsMessage selectTRentalcarsMessageById(Long id);

    /**
     * 查询拼包车订单乘客列表
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 拼包车订单乘客集合
     */
    public List< TRentalcarsMessage > selectTRentalcarsMessageList(TRentalcarsMessage tRentalcarsMessage);

    public List< TRentalcarsMessage > selectTRentalcarsMessageDetail(TRentalcarsMessage tRentalcarsMessage);

    public List< TRentalcarsMessage > selectTRentalcarsMessageByBusId(TRentalcarsMessage tRentalcarsMessage);

    public TRentalcarsMessage selectMaxtTRentalcarsMessageById(String busId);

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
     * 删除拼包车订单乘客
     * @param id 拼包车订单乘客ID
     * @return 结果
     */
    public int deleteTRentalcarsMessageById(Long id);

    /**
     * 批量删除拼包车订单乘客
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTRentalcarsMessageByIds(Long[] ids);

    public int updateBusIdByIds(String busId, String receptionNum, String receptionTime, String ids);

    public int updatePassengerNum(int passengerNum, String orderCode);


    public int updateBusIdByOrderCode(String busId, String orderCode);
}
