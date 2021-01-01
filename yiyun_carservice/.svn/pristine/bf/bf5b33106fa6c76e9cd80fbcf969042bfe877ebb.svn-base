package com.yiyun.yiyuncarservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author hxx
 * @Date 2020-12-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "c_shop")
public class CShop implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属部门id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 商户名称
     */
    @TableField("name")
    private String name;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 手机或座机
     */
    @TableField("tel")
    private String tel;

    /**
     * 工作内容
     */
    @TableField("work_content")
    private String workContent;

    /**
     * 作息时间
     */
    @TableField("work_time")
    private String workTime;

    /**
     * 经纬度
     */
    @TableField("lon_lat")
    private String lonLat;

    /**
     * 评分
     */
    @TableField("score")
    private BigDecimal score;

    /**
     * 评分人数
     */
    @TableField("num")
    private BigDecimal num;

    /**
     * 信用积分
     */
    @TableField("points")
    private Integer points;

    /**
     * 1:删除  0：未删除
     */
    @TableField("is_delete")
    private String isDelete;

    /**
     * 1：上架展示  2：下架
     */
    @TableField("is_show")
    private String isShow;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 列表缩略图
     */
    @TableField("cover_pic")
    private String coverPic;

    /**
     * 店铺轮播图
     */
    @TableField("shop_top_pics")
    private String shopTopPics;

    /**
     * 1:自营店 2：加盟店
     */
    @TableField("self_shop")
    private String selfShop;

    /**
     * 默认排序
     */
    @TableField("shop_order")
    private Integer shopOrder;

    /**
     * 服务类型（字典）
     */
    @TableField("service_type")
    private String serviceType;

    /**
     * 负责人
     */
    @TableField("charge_person")
    private String chargePerson;

    /**
     * 店铺类型（字典）驾培，维修，检测，施救
     */
    @TableField("shop_type")
    private String shopType;

    /**
     * 商铺标签（字典多选），可搜索
     */
    @TableField("shop_tags")
    private String shopTags;

    /**
     * 是否支持积分币抵扣 1是 2否
     */
    @TableField("can_use_points")
    private String canUsePoints;

    /**
     * 服务范围
     */
    @TableField("service_range")
    private String serviceRange;

}
