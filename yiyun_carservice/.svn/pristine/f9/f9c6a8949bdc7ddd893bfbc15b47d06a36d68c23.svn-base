package com.carservice.project.order.service.impl;

import com.carservice.project.order.domain.TRentalcarsMessage;
import com.carservice.project.order.domain.TTaxiOrder;
import com.carservice.project.order.mapper.TRentalcarsMessageMapper;
import com.carservice.project.order.service.ITRentalcarsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拼包车订单乘客Service业务层处理
 * @author carservice
 * @date 2020-08-13
 */
@Service
public class TRentalcarsMessageServiceImpl implements ITRentalcarsMessageService {
    @Autowired
    private TRentalcarsMessageMapper tRentalcarsMessageMapper;

    /**
     * 查询拼包车订单乘客
     * @param id 拼包车订单乘客ID
     * @return 拼包车订单乘客
     */
    @Override
    public TRentalcarsMessage selectTRentalcarsMessageById(Long id) {
        return tRentalcarsMessageMapper.selectTRentalcarsMessageById(id);
    }

    @Override
    public TRentalcarsMessage selectMaxtTRentalcarsMessageById(String busId) {
        return tRentalcarsMessageMapper.selectMaxtTRentalcarsMessageById(busId);
    }

    /**
     * 查询拼包车订单乘客列表
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 拼包车订单乘客
     */
    @Override
    public List< TRentalcarsMessage > selectTRentalcarsMessageList(TRentalcarsMessage tRentalcarsMessage) {
        return tRentalcarsMessageMapper.selectTRentalcarsMessageList(tRentalcarsMessage);
    }

    @Override
    public List< TRentalcarsMessage > selectTRentalcarsMessageDetail(TRentalcarsMessage tRentalcarsMessage) {
        return tRentalcarsMessageMapper.selectTRentalcarsMessageDetail(tRentalcarsMessage);
    }

    @Override
    public List< TRentalcarsMessage > selectTRentalcarsMessageByBusId(TRentalcarsMessage tRentalcarsMessage) {
        return tRentalcarsMessageMapper.selectTRentalcarsMessageByBusId(tRentalcarsMessage);
    }

    /**
     * 新增拼包车订单乘客
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 结果
     */
    @Override
    public int insertTRentalcarsMessage(TRentalcarsMessage tRentalcarsMessage) {
        return tRentalcarsMessageMapper.insertTRentalcarsMessage(tRentalcarsMessage);
    }

    /**
     * 修改拼包车订单乘客
     * @param tRentalcarsMessage 拼包车订单乘客
     * @return 结果
     */
    @Override
    public int updateTRentalcarsMessage(TRentalcarsMessage tRentalcarsMessage) {
        return tRentalcarsMessageMapper.updateTRentalcarsMessage(tRentalcarsMessage);
    }

    /**
     * 批量删除拼包车订单乘客
     * @param ids 需要删除的拼包车订单乘客ID
     * @return 结果
     */
    @Override
    public int deleteTRentalcarsMessageByIds(Long[] ids) {
        return tRentalcarsMessageMapper.deleteTRentalcarsMessageByIds(ids);
    }

    /**
     * 删除拼包车订单乘客信息
     * @param id 拼包车订单乘客ID
     * @return 结果
     */
    @Override
    public int deleteTRentalcarsMessageById(Long id) {
        return tRentalcarsMessageMapper.deleteTRentalcarsMessageById(id);
    }

    @Override
    public int updateBusIdByIds(String busId, String receptionNum, String receptionTime, String ids) {
        return tRentalcarsMessageMapper.updateBusIdByIds(busId, receptionNum, receptionTime, ids);
    }

    @Override
    public int updatePassengerNum(int passengerNum, String orderCode) {
        return tRentalcarsMessageMapper.updatePassengerNum(passengerNum, orderCode);
    }

    @Override
    public int updateBusIdByOrderCode(String busId, String orderCode) {
        return tRentalcarsMessageMapper.updateBusIdByOrderCode(busId, orderCode);
    }

    @Override
    public int confirmBusData(TTaxiOrder tTaxiOrder) {
        List< TRentalcarsMessage > passengerList = tTaxiOrder.getPassengerList();
        for (TRentalcarsMessage tRentalcarsMessage : passengerList) {
            TRentalcarsMessage rentalcarsMessage = tRentalcarsMessageMapper.selectTRentalcarsMessageById(tRentalcarsMessage.getId());
            rentalcarsMessage.setReceptionNum(tRentalcarsMessage.getReceptionNum());
            String format = "";
            String oldDate = tRentalcarsMessage.getReceptionTime();
            if (oldDate != null && oldDate != "NULL" && oldDate != "") {
                if (!oldDate.contains("Z")) {
                    format = oldDate;
                } else {
                    //转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
                    oldDate = oldDate.replace("Z", " UTC");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                    Date d = null;//Mon Mar 06 00:00:00 CST 2017
                    try {
                        d = sdf1.parse(oldDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    format = sdf.format(d);//2017-03-06
                }
            }
            rentalcarsMessage.setReceptionTime(format);
            tRentalcarsMessageMapper.updateTRentalcarsMessage(tRentalcarsMessage);
        }
        return 1;
    }

    private static boolean isDate(String date) {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }
}
