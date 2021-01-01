package com.carservice.project.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 店铺产品对象 c_shop_goods
 * 
 * @author carservice
 * @date 2020-12-12
 */
public class CShopGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /** 商品类型：字典 0:洗车，1：养护 2:检测线 */
    @Excel(name = "商品类型：字典 0:洗车，1：养护 2:检测线")
    private String goodsType;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String goodsName;

    /** 支付价格 */
    @Excel(name = "支付价格")
    private Double payPrice;

    /** 门市价格 */
    @Excel(name = "门市价格")
    private String price;

    /** 服务标准 */
    @Excel(name = "服务标准")
    private String serviceStandard;

    /** 0：未删除  1：已删除 */
    @Excel(name = "0：未删除  1：已删除")
    private String isDelete;

    /** 售卖数量 */
    @Excel(name = "售卖数量")
    private Long saleNum;

    /** 产品头图 */
    @Excel(name = "产品头图")
    private String topPicUrl;

    /** 产品图片 */
    @Excel(name = "产品图片")
    private String picUrl;

    /** 1:上架   2：下架 */
    @Excel(name = "1:上架   2：下架")
    private Integer onShelf;

    /** 产品排序 */
    @Excel(name = "产品排序")
    private Integer goodsOrder;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setGoodsType(String goodsType) 
    {
        this.goodsType = goodsType;
    }

    public String getGoodsType() 
    {
        return goodsType;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setPayPrice(Double payPrice) 
    {
        this.payPrice = payPrice;
    }

    public Double getPayPrice() 
    {
        return payPrice;
    }
    public void setPrice(String price) 
    {
        this.price = price;
    }

    public String getPrice() 
    {
        return price;
    }
    public void setServiceStandard(String serviceStandard) 
    {
        this.serviceStandard = serviceStandard;
    }

    public String getServiceStandard() 
    {
        return serviceStandard;
    }
    public void setIsDelete(String isDelete) 
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete() 
    {
        return isDelete;
    }
    public void setSaleNum(Long saleNum) 
    {
        this.saleNum = saleNum;
    }

    public Long getSaleNum() 
    {
        return saleNum;
    }
    public void setTopPicUrl(String topPicUrl) 
    {
        this.topPicUrl = topPicUrl;
    }

    public String getTopPicUrl() 
    {
        return topPicUrl;
    }
    public void setPicUrl(String picUrl) 
    {
        this.picUrl = picUrl;
    }

    public String getPicUrl() 
    {
        return picUrl;
    }
    public void setOnShelf(Integer onShelf) 
    {
        this.onShelf = onShelf;
    }

    public Integer getOnShelf() 
    {
        return onShelf;
    }
    public void setGoodsOrder(Integer goodsOrder) 
    {
        this.goodsOrder = goodsOrder;
    }

    public Integer getGoodsOrder() 
    {
        return goodsOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("shopId", getShopId())
            .append("goodsType", getGoodsType())
            .append("goodsName", getGoodsName())
            .append("payPrice", getPayPrice())
            .append("price", getPrice())
            .append("serviceStandard", getServiceStandard())
            .append("isDelete", getIsDelete())
            .append("saleNum", getSaleNum())
            .append("topPicUrl", getTopPicUrl())
            .append("picUrl", getPicUrl())
            .append("onShelf", getOnShelf())
            .append("goodsOrder", getGoodsOrder())
            .toString();
    }
}
