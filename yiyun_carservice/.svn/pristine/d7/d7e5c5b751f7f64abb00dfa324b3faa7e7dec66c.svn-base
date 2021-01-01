package com.carservice.project.order.domain;

import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 拼包车订单乘客对象 t_rentalcars_message
 * @author carservice
 * @date 2020-08-13
 */
public class TRentalcarsMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 班次编号
     */
    @Excel(name = "班次编号")
    private String busId;

    /**
     * 用车时间
     */
    @Excel(name = "用车时间")
    private String useTime;

    /**
     * 用车人姓名
     */
    @Excel(name = "用车人姓名")
    private String reserveName;

    /**
     * 预定人手机号
     */
    @Excel(name = "预定人手机号")
    private String reserveMobile;

    /**
     * 上车点
     */
    @Excel(name = "上车点")
    private String beginStation;

    /**
     * 下车点
     */
    @Excel(name = "下车点")
    private String endStation;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderCode;

    /**
     * 订单类型:3.拼座位 4拼车 5.包车
     */
    @Excel(name = "订单类型:3.拼座位 4拼车 5.包车")
    private String orderType;


    /**
     * 客户上车点经度
     */
    @Excel(name = "客户上车点经度")
    private String longitude;

    /**
     * 客户上车点纬度
     */
    @Excel(name = "客户上车点纬度")
    private String latitude;

    /**
     * 司机Id
     */
    @Excel(name = "司机Id")
    private String driverId;

    /**
     * 司机经度
     */
    @Excel(name = "司机经度")
    private String endLongitude;

    /**
     * 司机纬度
     */
    @Excel(name = "司机纬度")
    private String endLatitude;

    /**
     * 乘客数量
     */
    @Excel(name = "乘客数量")
    private int passengerNum;

    /**
     * 站点类型 1:上车点 2：下车点 3：不展示
     */
    private int siteType;

    /**
     * 接客序号
     */
    private String receptionNum;


    /**
     * 用车时间
     */
    private String receptionTime;

    private BigDecimal distance;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusId() {
        return busId;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

    public String getReserveName() {
        return reserveName;
    }

    public void setReserveMobile(String reserveMobile) {
        this.reserveMobile = reserveMobile;
    }

    public String getReserveMobile() {
        return reserveMobile;
    }

    public void setBeginStation(String beginStation) {
        this.beginStation = beginStation;
    }

    public String getBeginStation() {
        return beginStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public int getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(int passengerNum) {
        this.passengerNum = passengerNum;
    }

    public int getSiteType() {
        return siteType;
    }

    public void setSiteType(int siteType) {
        this.siteType = siteType;
    }

    public String getReceptionNum() {
        return receptionNum;
    }

    public void setReceptionNum(String receptionNum) {
        this.receptionNum = receptionNum;
    }

    public String getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(String receptionTime) {
        this.receptionTime = receptionTime;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("busId", getBusId())
                .append("useTime", getUseTime())
                .append("reserveName", getReserveName())
                .append("reserveMobile", getReserveMobile())
                .append("beginStation", getBeginStation())
                .append("endStation", getEndStation())
                .append("orderCode", getOrderCode())
                .append("orderType", getOrderType())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("driverId", getDriverId())
                .append("endLongitude", getEndLongitude())
                .append("endLatitude", getEndLatitude())
                .append("passengerNum", getPassengerNum())
                .append("siteType", getSiteType())
                .append("receptionNum", getReceptionNum())
                .append("receptionTime", getReceptionTime())
                .toString();
    }
}
