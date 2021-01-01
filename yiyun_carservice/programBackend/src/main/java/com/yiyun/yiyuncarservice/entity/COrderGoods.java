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
 * @Date 2020-12-13
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_order_goods")
public class COrderGoods implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 订单号
     */
    @TableField("order_code")
    private String orderCode;

    /**
     * 产品编号
     */
    @TableField("product_code")
    private String productCode;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 产品备注(商家端上传的图片链接)
     */
    @TableField("product_describe")
    private String productDescribe;

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
     * 订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 订单状态(1已下单 2已取消 3已接单 4.服务中 5.已支付 6已退款)
     */
    @TableField("pay_status")
    private String payStatus;

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
     * 产品金额
     */
    @TableField("product_amount")
    private BigDecimal productAmount;

    /**
     * 订单验证码
     */
    @TableField("verify_code")
    private String verifyCode;

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


}
