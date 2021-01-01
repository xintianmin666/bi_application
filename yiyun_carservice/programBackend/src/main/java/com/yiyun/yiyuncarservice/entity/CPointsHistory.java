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
@Table(name = "c_points_history")
public class CPointsHistory implements Serializable {


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
     * 积分金额
     */
    @TableField("points")
    private BigDecimal points;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 变动类型(1订单增加 2订单抵扣 3退款返还 4评价增加)
     */
    @TableField("change_type")
    private String changeType;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 变动时间
     */
    @TableField("change_time")
    private String changeTime;

}
