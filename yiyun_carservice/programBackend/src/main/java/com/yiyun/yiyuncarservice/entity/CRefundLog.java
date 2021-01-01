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
 * @Date 2020-12-11
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_refund_log")
public class CRefundLog implements Serializable {


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
     * 订单类型(1.洗车 2.养护 3.维修 4.检测 5.驾培 6.保险 7.救援)
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 退款状态 1未退款 2已申请退款 3已退款 4退款失败
     */
    @TableField("refund_status")
    private String refundStatus;

    /**
     * 订单金额
     */
    @TableField("order_amount")
    private BigDecimal orderAmount;

    /**
     * 退款金额
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    /**
     * 关联商品表Id
     */
    @TableField("releation_id")
    private String releationId;

    /**
     * 第三方交互错误返回信息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 退款发生错误原因
     */
    @TableField("refund_error")
    private String refundError;

    /**
     * 退款类型:系统 人工
     */
    @TableField("refund_type")
    private String refundType;

    /**
     * 费率或手续费
     */
    @TableField("fee_rate")
    private BigDecimal feeRate;

    /**
     * 退款订单号
     */
    @TableField("refund_order_code")
    private String refundOrderCode;

    /**
     * 支付类型:1.微信原生支付 2.银联支付
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 退款时间
     */
    @TableField("refund_time")
    private String refundTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;


}
