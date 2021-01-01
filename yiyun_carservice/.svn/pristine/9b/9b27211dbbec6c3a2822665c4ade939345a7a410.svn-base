package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

import java.util.List;

/**
 * 产品价格对象 t_product_price
 * 
 * @author carservice
 * @date 2020-05-20
 */
public class TProductPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品价格id */
    private Long productPriceId;

    /** 产品id */
    @Excel(name = "产品id")
    private Long productId;

    /** 车座位数 */
    @Excel(name = "车座位数")
    private Long carSeatNo;

    /** 车型 */
    @Excel(name = "车型")
    private String carType;

    /** 高速设置 */
    @Excel(name = "高速设置")
    private String highSpeedSetting;

    /** 允许拼车 */
    @Excel(name = "允许拼车")
    private String pchePermission;

    /** 允许包车 */
    @Excel(name = "允许包车")
    private String bchePermission;

    /** 散客拼车价格 */
    @Excel(name = "散客拼车价格")
    private Double pcheTocPrice;

    /** B端拼车价格 */
    @Excel(name = "B端拼车价格")
    private Double pcheTobPrice;

    /** 散客包车价格 */
    @Excel(name = "散客包车价格")
    private Double bcheTocPrice;

    /** B端包车价格 */
    @Excel(name = "B端包车价格")
    private Double bcheTobPrice;

    /** $column.columnComment */
    @Excel(name = "B端包车价格")
    private Long createId;

    private TVehicleType vehicleType;

    //多少元起
    private Double startPrice;

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public TVehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(TVehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public List<TProductPrice> getProductPriceList() {
        return productPriceList;
    }

    public void setProductPriceList(List<TProductPrice> productPriceList) {
        this.productPriceList = productPriceList;
    }

    private List<TProductPrice> productPriceList;

    public void setProductPriceId(Long productPriceId)
    {
        this.productPriceId = productPriceId;
    }

    public Long getProductPriceId() 
    {
        return productPriceId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setCarSeatNo(Long carSeatNo) 
    {
        this.carSeatNo = carSeatNo;
    }

    public Long getCarSeatNo() 
    {
        return carSeatNo;
    }
    public void setHighSpeedSetting(String highSpeedSetting) 
    {
        this.highSpeedSetting = highSpeedSetting;
    }

    public String getHighSpeedSetting() 
    {
        return highSpeedSetting;
    }
    public void setPchePermission(String pchePermission) 
    {
        this.pchePermission = pchePermission;
    }

    public String getPchePermission() 
    {
        return pchePermission;
    }
    public void setBchePermission(String bchePermission) 
    {
        this.bchePermission = bchePermission;
    }

    public String getBchePermission() 
    {
        return bchePermission;
    }
    public void setPcheTocPrice(Double pcheTocPrice) 
    {
        this.pcheTocPrice = pcheTocPrice;
    }

    public Double getPcheTocPrice() 
    {
        return pcheTocPrice;
    }
    public void setPcheTobPrice(Double pcheTobPrice) 
    {
        this.pcheTobPrice = pcheTobPrice;
    }

    public Double getPcheTobPrice() 
    {
        return pcheTobPrice;
    }
    public void setBcheTocPrice(Double bcheTocPrice) 
    {
        this.bcheTocPrice = bcheTocPrice;
    }

    public Double getBcheTocPrice() 
    {
        return bcheTocPrice;
    }
    public void setBcheTobPrice(Double bcheTobPrice) 
    {
        this.bcheTobPrice = bcheTobPrice;
    }

    public Double getBcheTobPrice() 
    {
        return bcheTobPrice;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productPriceId", getProductPriceId())
            .append("productId", getProductId())
            .append("carSeatNo", getCarSeatNo())
            .append("highSpeedSetting", getHighSpeedSetting())
            .append("pchePermission", getPchePermission())
            .append("bchePermission", getBchePermission())
            .append("pcheTocPrice", getPcheTocPrice())
            .append("pcheTobPrice", getPcheTobPrice())
            .append("bcheTocPrice", getBcheTocPrice())
            .append("bcheTobPrice", getBcheTobPrice())
            .append("createId", getCreateId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
