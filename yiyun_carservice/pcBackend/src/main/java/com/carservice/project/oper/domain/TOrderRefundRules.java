package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 订单退单规则对象 t_order_refund_rules
 * 
 * @author carservice
 * @date 2020-08-27
 */
public class TOrderRefundRules extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 5:包车订单 4：拼车订单 3：拼座订单 */
    @Excel(name = "5:包车订单 4：拼车订单 3：拼座订单")
    private Integer orderType;

    /** 退费率 */
    @Excel(name = "退费率")
    private Double refundRate;

    /** 发车前多少分钟内 */
    @Excel(name = "发车前多少分钟内")
    private Long timeBefore;

    /** 退款规则名称 */
    @Excel(name = "退款规则名称")
    private String name;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderType(Integer orderType) 
    {
        this.orderType = orderType;
    }

    public Integer getOrderType() 
    {
        return orderType;
    }
    public void setRefundRate(Double refundRate) 
    {
        this.refundRate = refundRate;
    }

    public Double getRefundRate() 
    {
        return refundRate;
    }
    public void setTimeBefore(Long timeBefore) 
    {
        this.timeBefore = timeBefore;
    }

    public Long getTimeBefore() 
    {
        return timeBefore;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderType", getOrderType())
            .append("refundRate", getRefundRate())
            .append("timeBefore", getTimeBefore())
            .append("name", getName())
            .toString();
    }
}
