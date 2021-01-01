package com.yiyun.yiyuncarservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author hxx
 * @Date 2020-12-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_order")
public class COrder implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;

    /**
     * 用户手机号码
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 订单号
     */
    @TableField("order_code")
    private String orderCode;

    /**
     * 订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 订单状态(1已下单 2已取消 3已接单 4.服务中 5.已支付 6已退款)
     */
    @TableField("order_status")
    private String orderStatus;

    /**
     * 订单金额
     */
    @TableField("order_amount")
    private BigDecimal orderAmount;

    /**
     * 使用积分
     */
    @TableField("use_points")
    private BigDecimal usePoints;

    /**
     * 赠送积分
     */
    @TableField("add_points")
    private BigDecimal addPoints;

    /**
     * 支付类型
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 折扣金额
     */
    @TableField("discount_amount")
    private BigDecimal discountAmount;

    /**
     * 优惠券金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 积分抵扣金额
     */
    @TableField("points_amount")
    private BigDecimal pointsAmount;

    /**
     * 实收金额
     */
    @TableField("collect_amount")
    private BigDecimal collectAmount;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

    /**
     * 订单取消时间
     */
    @TableField("cancel_time")
    private String cancelTime;

    /**
     * 预约到店时间
     */
    @TableField("book_time")
    private String bookTime;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private String payTime;

    /**
     * 退款时间
     */
    @TableField("refund_time")
    private String refundTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 商家id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 商家id
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 驾培类型或号牌种类
     */
    @TableField("driver_type")
    private String driverType;

    /**
     * 客户姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 车牌号
     */
    @TableField("car_no")
    private String carNo;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 客户所在位置经度
     */
    @TableField("longitude")
    private String longitude;

    /**
     * 客户所在位置纬度
     */
    @TableField("latitude")
    private String latitude;

    /**
     * 一键救援人员姓名
     */
    @TableField("rescue_name")
    private String rescueName;

    /**
     * 一键救援人员电话号码
     */
    @TableField("rescue_mobile")
    private String rescueMobile;

    /**
     * 救援车辆车牌号码
     */
    @TableField("rescue_car_no")
    private String rescueCarNo;

    /**
     * 行驶证正面
     */
    @TableField("driver_license_front")
    private String driverLicenseFront;

    /**
     * 行驶证背面
     */
    @TableField("driver_license_back")
    private String driverLicenseBack;

}
