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
@Table(name = "c_user_info")
public class CUserInfo implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 用户姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 0未知 1男性 2女性
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 身份证号码
     */
    @TableField("idcard")
    private String idcard;

    /**
     * 头像
     */
    @TableField("head_img")
    private String headImg;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)
     */
    @TableField("vip_level")
    private String vipLevel;

    /**
     * 推广码(推广用户字段不为空)
     */
    @TableField("promote_code")
    private String promoteCode;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;

    @TableField("session_key")
    private String sessionKey;

    /**
     * 用户来源(1.普通用户 2.推广用户)
     */
    @TableField("source")
    private String source;

    /**
     * 积分余额
     */
    @TableField("points")
    private BigDecimal points;

    /**
     * 登录密码
     */
    @TableField("password")
    private String password;

    /**
     * 支付密码
     */
    @TableField("pay_password")
    private String payPassword;

    /**
     * 默认为0
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 车牌号
     */
    @TableField("car_no")
    private String carNo;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    @TableField("openid")
    private String openid;

}
