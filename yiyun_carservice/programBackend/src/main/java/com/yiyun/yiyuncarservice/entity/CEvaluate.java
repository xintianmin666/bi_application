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
 * @Date 2020-12-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_evaluate")
public class CEvaluate implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private String createTime;

    /**
     * 默认为0
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 评价内容
     */
    @TableField("evaluate_content")
    private String evaluateContent;

    /**
     * 订单号
     */
    @TableField("order_code")
    private String orderCode;

    /**
     * 星级评分
     */
    @TableField("star_rating")
    private Integer starRating;

    /**
     * 商家Id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

}
