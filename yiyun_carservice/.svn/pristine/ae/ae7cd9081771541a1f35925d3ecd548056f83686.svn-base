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
 * @Date 2020-12-11
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_relation_car")
public class CRelationCar implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 车牌号
     */
    @TableField("car_no")
    private String carNo;

    /**
     * 是否默认 1是 2否
     */
    @TableField("is_default")
    private String isDefault;

}
