package com.carservice.project.business.domain;

import java.math.BigDecimal;

public class COrderGoods {
    private Integer id;

    private String orderCode;

    private String productCode;

    private String productName;

    private String productDescribe;

    private BigDecimal discountAmount;

    private BigDecimal couponAmount;

    private BigDecimal pointsAmount;

    private BigDecimal collectAmount;

    private String orderType;

    private String payStatus;

    private String payTime;

    private String refundTime;

    private BigDecimal productAmount;

    private String verifyCode;

    private BigDecimal usePoints;

    private BigDecimal addPoints;

    private String cancelTime;

    private String createTime;

    private String bookTime;

    private String userName;

    private String carNo;

    private String address;

    private String rescueName;

    private String rescueMobile;

    private String rescueCarNo;

    private String userPhone;

    private String longitude;

    private String latitude;

    private String[] productDescribeArr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe == null ? null : productDescribe.trim();
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getPointsAmount() {
        return pointsAmount;
    }

    public void setPointsAmount(BigDecimal pointsAmount) {
        this.pointsAmount = pointsAmount;
    }

    public BigDecimal getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(BigDecimal collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime == null ? null : refundTime.trim();
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
    }

    public BigDecimal getUsePoints() {
        return usePoints;
    }

    public void setUsePoints(BigDecimal usePoints) {
        this.usePoints = usePoints;
    }

    public BigDecimal getAddPoints() {
        return addPoints;
    }

    public void setAddPoints(BigDecimal addPoints) {
        this.addPoints = addPoints;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRescueName() {
        return rescueName;
    }

    public void setRescueName(String rescueName) {
        this.rescueName = rescueName;
    }

    public String getRescueMobile() {
        return rescueMobile;
    }

    public void setRescueMobile(String rescueMobile) {
        this.rescueMobile = rescueMobile;
    }

    public String getRescueCarNo() {
        return rescueCarNo;
    }

    public void setRescueCarNo(String rescueCarNo) {
        this.rescueCarNo = rescueCarNo;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String[] getProductDescribeArr() {
        return productDescribeArr;
    }

    public void setProductDescribeArr(String[] productDescribeArr) {
        this.productDescribeArr = productDescribeArr;
    }
}