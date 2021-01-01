package com.carservice.project.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 积分变动明细对象 c_points_history
 * 
 * @author carservice
 * @date 2020-12-21
 */
public class CPointsHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderCode;

    /** 积分金额 */
    @Excel(name = "积分金额")
    private Double points;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 变动类型(1订单增加 2订单抵扣 3退款返还 4评价增加 5取消返还) */
    @Excel(name = "变动类型(1订单增加 2订单抵扣 3退款返还 4评价增加 5取消返还)")
    private String changeType;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userId;

    /** 变动时间 */
    @Excel(name = "变动时间")
    private String changeTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setPoints(Double points) 
    {
        this.points = points;
    }

    public Double getPoints() 
    {
        return points;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setChangeType(String changeType) 
    {
        this.changeType = changeType;
    }

    public String getChangeType() 
    {
        return changeType;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setChangeTime(String changeTime) 
    {
        this.changeTime = changeTime;
    }

    public String getChangeTime() 
    {
        return changeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderCode", getOrderCode())
            .append("points", getPoints())
            .append("productName", getProductName())
            .append("changeType", getChangeType())
            .append("userId", getUserId())
            .append("changeTime", getChangeTime())
            .toString();
    }
}
