package com.carservice.project.oper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 营运城市信息对象 t_oper_city_info
 * 
 * @author carservice
 * @date 2020-05-04
 */
public class TOperCityInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 营运城市ID */
    private Long operCityId;

    /** 营运城市名称 */
    @Excel(name = "营运城市名称")
    private String operCityName;

    /** 营运城市代码 */
    @Excel(name = "营运城市代码")
    private String operCityCode;

    /** 营运城市拼音 */
    @Excel(name = "营运城市拼音")
    private String operCityPinyin;

    /** 营运城市首拼 */
    @Excel(name = "营运城市首拼")
    private String operCitySp;

    /** 城市地区代码 */
    @Excel(name = "城市地区代码")
    private String areaCode;

    /** 城市地区上级代码 */
    @Excel(name = "城市地区上级代码")
    private String areaParentCode;
    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUserId;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 修改人 */
    @Excel(name = "修改人")
    private Long modifyUserId;

    /** 是否生效 */
    @Excel(name = "是否生效")
    private String isvaliable;

    public String getOperCitySp() {
        return operCitySp;
    }

    public void setOperCitySp(String operCitySp) {
        this.operCitySp = operCitySp;
    }

    public String getAreaParentCode() {
        return areaParentCode;
    }

    public void setAreaParentCode(String areaParentCode) {
        this.areaParentCode = areaParentCode;
    }

    public void setOperCityId(Long operCityId)
    {
        this.operCityId = operCityId;
    }

    public Long getOperCityId() 
    {
        return operCityId;
    }
    public void setOperCityName(String operCityName) 
    {
        this.operCityName = operCityName;
    }

    public String getOperCityName() 
    {
        return operCityName;
    }
    public void setOperCityCode(String operCityCode) 
    {
        this.operCityCode = operCityCode;
    }

    public String getOperCityCode() 
    {
        return operCityCode;
    }
    public void setOperCityPinyin(String operCityPinyin) 
    {
        this.operCityPinyin = operCityPinyin;
    }

    public String getOperCityPinyin() 
    {
        return operCityPinyin;
    }
    public void setAreaCode(String areaCode) 
    {
        this.areaCode = areaCode;
    }

    public String getAreaCode() 
    {
        return areaCode;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setModifyUserId(Long modifyUserId) 
    {
        this.modifyUserId = modifyUserId;
    }

    public Long getModifyUserId() 
    {
        return modifyUserId;
    }
    public void setIsvaliable(String isvaliable) 
    {
        this.isvaliable = isvaliable;
    }

    public String getIsvaliable() 
    {
        return isvaliable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("operCityId", getOperCityId())
            .append("operCityName", getOperCityName())
            .append("operCityCode", getOperCityCode())
            .append("operCityPinyin", getOperCityPinyin())
            .append("areaCode", getAreaCode())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("modifyTime", getModifyTime())
            .append("modifyUserId", getModifyUserId())
            .append("isvaliable", getIsvaliable())
            .toString();
    }
}
