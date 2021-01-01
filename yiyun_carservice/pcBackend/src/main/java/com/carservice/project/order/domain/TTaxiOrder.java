package com.carservice.project.order.domain;

import com.carservice.framework.aspectj.lang.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 出租车订单对象 t_taxi_order
 * @author carservice
 * @date 2020-07-03
 */
public class TTaxiOrder {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

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
     * 用车时间
     */
    @Excel(name = "用车时间")
    private String useTime;

    /**
     * 预定人
     */
    @Excel(name = "预定人")
    private String reserveName;

    /**
     * 预定人Id
     */
    @Excel(name = "预定人Id")
    private String reserveId;

    /**
     * 预定人手机号
     */
    @Excel(name = "预定人手机号")
    private String reserveMobile;

    /**
     * 下单时间
     */
    @Excel(name = "下单时间")
    private String createTime;

    /**
     * 接单或取消订单时间
     */
    @Excel(name = "接单或取消订单时间")
    private String appectTime;

    /**
     * 完成订单时间
     */
    @Excel(name = "完成订单时间")
    private String finishTime;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderCode;

    /**
     * 出租车订单类型:1.实时单 2.预约单
     */
    @Excel(name = "出租车订单类型:1.实时单 2.预约单")
    private String orderType;

    /**
     * 订单状态:1、已下单 2、已取消 3、已接单 4、已完成
     */
    @Excel(name = "订单状态:1、已下单 2、已取消 3、已接单 4、已完成 5、已派单 6、未支付 7、已支付 8、已退款")
    private Integer orderStatus;

    /**
     * 订单金额
     */
    @Excel(name = "订单金额")
    private Double orderAmount;

    /**
     * 实收金额
     */
    @Excel(name = "实收金额")
    private Double collectAmount;

    /**
     * 司机Id
     */
    @Excel(name = "司机Id")
    private String driverId;

    /**
     * 司机姓名
     */
    @Excel(name = "司机姓名")
    private String driverName;

    /**
     * 司机联系方式
     */
    @Excel(name = "司机联系方式")
    private String driverPhone;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号")
    private String driverCarNo;

    /**
     * 客户所在位置经度
     */
    @Excel(name = "客户所在位置经度")
    private String longitude;

    /**
     * 客户所在位置纬度
     */
    @Excel(name = "客户所在位置纬度")
    private String latitude;

    /**
     * 下车点
     */
    private String endLon;

    /**
     * 下车点
     */
    private String endLat;

    /**
     * 产品编号
     */
    @Excel(name = "产品编号")
    private String productCode;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String productName;

    /**
     * 站点顺序
     */
    @Excel(name = "站点顺序")
    private int orderNum;

    /**
     * 座位数
     */
    @Excel(name = "座位数")
    private int seatNum;

    /**
     * 用车人数
     */
    @Excel(name = "用车人数")
    private int useNumber;


    /**
     * 售卖座位数
     */
    @Excel(name = "售卖座位数")
    private int sellNumber;

    /**
     * 车辆性质
     */
    @Excel(name = "车辆类型")
    private String type;

    /**
     * 班次编号
     */
    @Excel(name = "班次编号")
    private String busId;


    /**
     * 开始时间
     */
//    @JsonIgnore
    private String beginTime;


    /**
     * 结束时间
     */
//    @JsonIgnore
    private String endTime;

    //    @JsonIgnore
    private String ids;

    private List< TRentalcarsMessage > passengerList;

    private String vehicleId;

    private String tjzd;

    private String carDes;

    private int passengerNum;

    /**
     * 拼车每个座位价格
     */
    private Double sellPrice;

    private String useHour;

    /**
     * 站点类型 1:上车点 2：下车点 3：不展示
     */
    private int siteType;

    public String getUseHour() {
        return useHour;
    }

    public void setUseHour(String useHour) {
        this.useHour = useHour;
    }

    public String getEndLon() {
        return endLon;
    }

    public void setEndLon(String endLon) {
        this.endLon = endLon;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public int getSiteType() {
        return siteType;
    }

    public void setSiteType(int siteType) {
        this.siteType = siteType;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(int passengerNum) {
        this.passengerNum = passengerNum;
    }

    public String getTjzd() {
        return tjzd;
    }

    public void setTjzd(String tjzd) {
        this.tjzd = tjzd;
    }

    public String getCarDes() {
        return carDes;
    }

    public void setCarDes(String carDes) {
        this.carDes = carDes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(int useNumber) {
        this.useNumber = useNumber;
    }


    public int getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(int sellNumber) {
        this.sellNumber = sellNumber;
    }

    public List< TRentalcarsMessage > getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List< TRentalcarsMessage > passengerList) {
        this.passengerList = passengerList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveMobile(String reserveMobile) {
        this.reserveMobile = reserveMobile;
    }

    public String getReserveMobile() {
        return reserveMobile;
    }

    public void setAppectTime(String appectTime) {
        this.appectTime = appectTime;
    }

    public String getAppectTime() {
        return appectTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishTime() {
        return finishTime;
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

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setCollectAmount(Double collectAmount) {
        this.collectAmount = collectAmount;
    }

    public Double getCollectAmount() {
        return collectAmount;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverCarNo(String driverCarNo) {
        this.driverCarNo = driverCarNo;
    }

    public String getDriverCarNo() {
        return driverCarNo;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("beginStation", getBeginStation())
                .append("endStation", getEndStation())
                .append("useTime", getUseTime())
                .append("reserveName", getReserveName())
                .append("reserveId", getReserveId())
                .append("reserveMobile", getReserveMobile())
                .append("createTime", getCreateTime())
                .append("appectTime", getAppectTime())
                .append("finishTime", getFinishTime())
                .append("orderCode", getOrderCode())
                .append("orderType", getOrderType())
                .append("orderStatus", getOrderStatus())
                .append("orderAmount", getOrderAmount())
                .append("collectAmount", getCollectAmount())
                .append("driverId", getDriverId())
                .append("driverName", getDriverName())
                .append("driverPhone", getDriverPhone())
                .append("driverCarNo", getDriverCarNo())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("productCode", getProductCode())
                .append("orderNum", getOrderNum())
                .append("type", getType())
                .append("productName", getProductName())
                .append("useNumber", getUseNumber())
                .append("sellNumber", getSellNumber())
                .append("busId", getBusId())
                .toString();
    }
}
