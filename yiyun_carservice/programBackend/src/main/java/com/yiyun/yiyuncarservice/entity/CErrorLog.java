package com.yiyun.yiyuncarservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description
 * @Author hxx
 * @Date 2020-12-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_error_log")
public class CErrorLog implements Serializable {


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
     * 错误类型1支付订单
     */
    @TableField("error_type")
    private String errorType;

    /**
     * 第三方交互错误返回信息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

}
