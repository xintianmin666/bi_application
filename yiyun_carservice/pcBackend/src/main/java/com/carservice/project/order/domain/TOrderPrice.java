package com.carservice.project.order.domain;

import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 包拼车订单价格明细对象 t_order_price
 * @author carservice
 * @date 2020-08-14
 */
public class TOrderPrice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 价格类型
     */
    @Excel(name = "价格类型")
    private String priceType;

    /**
     * 收费金额
     */
    @Excel(name = "收费金额")
    private Double chargeAmount;

    /**
     * 价格描述
     */
    @Excel(name = "价格描述")
    private String description;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderCode;

    private List< TOrderPrice > orderPriceList;

    public List< TOrderPrice > getOrderPriceList() {
        return orderPriceList;
    }

    public void setOrderPriceList(List< TOrderPrice > orderPriceList) {
        this.orderPriceList = orderPriceList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPriceType() {
        return priceType;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("priceType", getPriceType())
                .append("chargeAmount", getChargeAmount())
                .append("description", getDescription())
                .append("orderCode", getOrderCode())
                .toString();
    }
}
