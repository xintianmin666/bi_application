package com.yiyun.yiyuncarservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Author hxx
 * @Date 2020-12-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_order")
public class COrderVo implements Serializable {


    /**
     * 主键id
     */
    private Integer id;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)
     */
    private String orderType;

    /**
     * 订单状态(1已下单 2已取消 3已接单 4.服务中 5.已支付 6已退款)
     */
    private String orderStatus;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 使用积分
     */
    private BigDecimal usePoints;

    /**
     * 赠送积分
     */
    private BigDecimal addPoints;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 优惠券金额
     */
    private BigDecimal couponAmount;

    /**
     * 积分抵扣金额
     */
    private BigDecimal pointsAmount;

    /**
     * 实收金额
     */
    private BigDecimal collectAmount;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 订单取消时间
     */
    private String cancelTime;

    /**
     * 预约到店时间
     */
    private String bookTime;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 退款时间
     */
    private String refundTime;

    /**
     * 备注
     */
    private String remark;

    private Integer isDelete;

    /**
     * 商家id
     */
    private String shopId;

    /**
     * 客户姓名
     */
    private String userName;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 地址
     */
    private String address;

    /**
     * 救援人员
     */
    private String rescueName;

    /**
     * 救援人员电话
     */
    private String rescueMobile;

    /**
     * 救援车辆车牌号
     */
    private String rescueCarNo;

    // 商品列表
    private List< COrderGoodsVo > orderGoodsList;

    private String longitude;

    private String latitude;

    // 订单类型名称
    private String orderTypeName;

    private Integer isEvaluate;

    /**
     * 商家id
     */
    private String shopName;

    /**
     * 驾培类型或号牌种类
     */
    private String driverType;

    /**
     * 行驶证正面
     */
    private String driverLicenseFront;

    /**
     * 行驶证背面
     */
    private String driverLicenseBack;

    private String shopAddress;

    private String shopPhone;

    private String lonLat;

    private BigDecimal score;

    private String coverPic;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public Integer getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(Integer isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getLonLat() {
        return lonLat;
    }

    public void setLonLat(String lonLat) {
        this.lonLat = lonLat;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getDriverLicenseFront() {
        return driverLicenseFront;
    }

    public void setDriverLicenseFront(String driverLicenseFront) {
        this.driverLicenseFront = driverLicenseFront;
    }

    public String getDriverLicenseBack() {
        return driverLicenseBack;
    }

    public void setDriverLicenseBack(String driverLicenseBack) {
        this.driverLicenseBack = driverLicenseBack;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public List< COrderGoodsVo > getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List< COrderGoodsVo > orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
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

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }
}
