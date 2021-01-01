package com.carservice.project.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 用户评价对象 c_evaluate
 * 
 * @author carservice
 * @date 2020-12-17
 */
public class CEvaluate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userId;

    /** 默认为0 */
    @Excel(name = "默认为0")
    private Integer isDelete;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String evaluateContent;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderCode;

    /** 星级评分 */
    @Excel(name = "星级评分")
    private Integer starRating;

    /** 商家Id */
    @Excel(name = "商家Id")
    private String shopId;
    private String shopName;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setIsDelete(Integer isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete() 
    {
        return isDelete;
    }
    public void setEvaluateContent(String evaluateContent) 
    {
        this.evaluateContent = evaluateContent;
    }

    public String getEvaluateContent() 
    {
        return evaluateContent;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setStarRating(Integer starRating) 
    {
        this.starRating = starRating;
    }

    public Integer getStarRating() 
    {
        return starRating;
    }
    public void setShopId(String shopId) 
    {
        this.shopId = shopId;
    }

    public String getShopId() 
    {
        return shopId;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .append("isDelete", getIsDelete())
            .append("evaluateContent", getEvaluateContent())
            .append("orderCode", getOrderCode())
            .append("starRating", getStarRating())
            .append("shopId", getShopId())
            .append("nickName", getNickName())
            .toString();
    }
}
