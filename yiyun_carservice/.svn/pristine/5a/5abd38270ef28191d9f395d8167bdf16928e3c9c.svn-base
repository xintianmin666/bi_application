package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 城市站点对象 t_site_info
 * 
 * @author carservice
 * @date 2020-05-23
 */
public class TSiteInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long siteId;

    /** 城市id */
    @Excel(name = "城市id")
    private Long cityId;
    @Excel(name = "城市")
    private String cityName;

    /** 站点代码 */
    @Excel(name = "站点代码")
    private String siteCode;

    /** 站点名称 */
    @Excel(name = "站点名称")
    private String siteName;

    /** 站点拼音 */
    @Excel(name = "站点拼音")
    private String sitePy;

    /** 站点拼音首字母 */
    @Excel(name = "站点拼音首字母")
    private String siteSp;

    /** 站点类别 */
    @Excel(name = "站点类别")
    private String siteType;

    /** 站点等级 */
    @Excel(name = "站点等级")
    private String siteLevel;

    /** 站点半径单位米 */
    @Excel(name = "站点半径单位米")
    private String siteRadius;

    /** 站点电子栅栏 */
    @Excel(name = "站点电子栅栏")
    private String siteFence;

    private String lng;

    private String lat;

    /** $column.columnComment */
    private Long createId;

    /** $column.columnComment */
    private Long updateId;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setSiteId(Long siteId)
    {
        this.siteId = siteId;
    }

    public Long getSiteId() 
    {
        return siteId;
    }
    public void setCityId(Long cityId) 
    {
        this.cityId = cityId;
    }

    public Long getCityId() 
    {
        return cityId;
    }
    public void setSiteCode(String siteCode) 
    {
        this.siteCode = siteCode;
    }

    public String getSiteCode() 
    {
        return siteCode;
    }
    public void setSiteName(String siteName) 
    {
        this.siteName = siteName;
    }

    public String getSiteName() 
    {
        return siteName;
    }
    public void setSitePy(String sitePy) 
    {
        this.sitePy = sitePy;
    }

    public String getSitePy() 
    {
        return sitePy;
    }
    public void setSiteSp(String siteSp) 
    {
        this.siteSp = siteSp;
    }

    public String getSiteSp() 
    {
        return siteSp;
    }
    public void setSiteType(String siteType)
    {
        this.siteType = siteType;
    }

    public String getSiteType()
    {
        return siteType;
    }
    public void setSiteLevel(String siteLevel)
    {
        this.siteLevel = siteLevel;
    }

    public String getSiteLevel() 
    {
        return siteLevel;
    }
    public void setSiteRadius(String siteRadius) 
    {
        this.siteRadius = siteRadius;
    }

    public String getSiteRadius() 
    {
        return siteRadius;
    }
    public void setSiteFence(String siteFence) 
    {
        this.siteFence = siteFence;
    }

    public String getSiteFence() 
    {
        return siteFence;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }
    public void setUpdateId(Long updateId) 
    {
        this.updateId = updateId;
    }

    public Long getUpdateId() 
    {
        return updateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("siteId", getSiteId())
            .append("cityId", getCityId())
            .append("siteCode", getSiteCode())
            .append("siteName", getSiteName())
            .append("sitePy", getSitePy())
            .append("siteSp", getSiteSp())
            .append("siteType", getSiteType())
            .append("siteLevel", getSiteLevel())
            .append("siteRadius", getSiteRadius())
            .append("siteFence", getSiteFence())
            .append("createId", getCreateId())
            .append("createTime", getCreateTime())
            .append("updateId", getUpdateId())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
