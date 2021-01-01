package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

import java.util.List;

/**
 * 产品站点对象 t_product_site
 * 
 * @author carservice
 * @date 2020-08-04
 */
public class TProductSite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long productSiteId;

    /** 产品id */
    @Excel(name = "产品id")
    private Long tProductId;

    /** 站点id */
    @Excel(name = "站点id")
    private Long siteInfoId;

    /** 站点顺序 */
    @Excel(name = "站点顺序")
    private Long orderNum;

    /** 1:上车点 2：下车点 3：不展示 */
    @Excel(name = "1:上车点 2：下车点 3：不展示")
    private Long siteType;

    /** 1:使用默认电子围栏  0：不使用电子围栏 */
    @Excel(name = "1:使用默认电子围栏  0：不使用电子围栏")
    private String useSiteFence;

    private List<TProductSite> productSiteList;

    private TSiteInfo siteInfo;

    public TSiteInfo getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(TSiteInfo siteInfo) {
        this.siteInfo = siteInfo;
    }

    public List<TProductSite> getProductSiteList() {
        return productSiteList;
    }

    public void setProductSiteList(List<TProductSite> productSiteList) {
        this.productSiteList = productSiteList;
    }

    public void setProductSiteId(Long productSiteId)
    {
        this.productSiteId = productSiteId;
    }

    public Long getProductSiteId() 
    {
        return productSiteId;
    }
    public void settProductId(Long tProductId) 
    {
        this.tProductId = tProductId;
    }

    public Long gettProductId() 
    {
        return tProductId;
    }
    public void setSiteInfoId(Long siteInfoId) 
    {
        this.siteInfoId = siteInfoId;
    }

    public Long getSiteInfoId() 
    {
        return siteInfoId;
    }
    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }
    public void setSiteType(Long siteType) 
    {
        this.siteType = siteType;
    }

    public Long getSiteType() 
    {
        return siteType;
    }
    public void setUseSiteFence(String useSiteFence) 
    {
        this.useSiteFence = useSiteFence;
    }

    public String getUseSiteFence() 
    {
        return useSiteFence;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productSiteId", getProductSiteId())
            .append("tProductId", gettProductId())
            .append("siteInfoId", getSiteInfoId())
            .append("orderNum", getOrderNum())
            .append("siteType", getSiteType())
            .append("useSiteFence", getUseSiteFence())
            .toString();
    }
}
