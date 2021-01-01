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
@Table(name = "c_userlevel_history")
public class CUserlevelHistory implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 会员等级(1.普通会员 2.Plus会员 3.VIP会员 4.车会员)
     */
    @TableField("vip_level")
    private String vipLevel;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;

    /**
     * 变动时间
     */
    @TableField("change_time")
    private String changeTime;

}
