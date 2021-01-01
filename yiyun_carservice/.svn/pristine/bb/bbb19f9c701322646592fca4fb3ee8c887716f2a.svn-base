package com.carservice.project.oper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 车辆类型对象 t_vehicle_type
 * 
 * @author carservice
 * @date 2020-08-14
 */
public class TVehicleType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车辆类型ID */
    private Long vcehicleTypeId;

    /** 最大承载人数（包括司机） */
    @Excel(name = "最大承载人数", readConverterExp = "包=括司机")
    private Long capacityMax;

    /** 可乘坐旅客数 */
    @Excel(name = "可乘坐旅客数")
    private Long passengerNum;

    /** 可携带没有座位数的儿童 */
    @Excel(name = "可携带没有座位数的儿童")
    private Long noSeatChildNum;

    /** 可携带行李箱备注 */
    @Excel(name = "可携带行李箱备注")
    private String suitcaseNumRemark;

    /** 车辆类型名称 */
    @Excel(name = "车辆类型名称")
    private String typeName;

    private String carPicUrl;

    /** 拼车最少人数 */
    @Excel(name = "拼车最少人数")
    private Long pincheMin;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUserId;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifyUserId;

    /** 是否有效 */
    @Excel(name = "是否有效")
    private String isvaliable;

    /** 散客拼车价格 */
    private Double pcheTocPrice;

    /** B端拼车价格 */
    private Double pcheTobPrice;

    /** 散客包车价格 */
    private Double bcheTocPrice;

    /** B端包车价格 */
    private Double bcheTobPrice;

    public Double getPcheTocPrice() {
        return pcheTocPrice;
    }

    public void setPcheTocPrice(Double pcheTocPrice) {
        this.pcheTocPrice = pcheTocPrice;
    }

    public Double getPcheTobPrice() {
        return pcheTobPrice;
    }

    public void setPcheTobPrice(Double pcheTobPrice) {
        this.pcheTobPrice = pcheTobPrice;
    }

    public Double getBcheTocPrice() {
        return bcheTocPrice;
    }

    public void setBcheTocPrice(Double bcheTocPrice) {
        this.bcheTocPrice = bcheTocPrice;
    }

    public Double getBcheTobPrice() {
        return bcheTobPrice;
    }

    public void setBcheTobPrice(Double bcheTobPrice) {
        this.bcheTobPrice = bcheTobPrice;
    }

    public String getCarPicUrl() {
        return carPicUrl;
    }

    public void setCarPicUrl(String carPicUrl) {
        this.carPicUrl = carPicUrl;
    }

    public void setVcehicleTypeId(Long vcehicleTypeId)
    {
        this.vcehicleTypeId = vcehicleTypeId;
    }

    public Long getVcehicleTypeId()
    {
        return vcehicleTypeId;
    }
    public void setCapacityMax(Long capacityMax)
    {
        this.capacityMax = capacityMax;
    }

    public Long getCapacityMax() 
    {
        return capacityMax;
    }
    public void setPassengerNum(Long passengerNum) 
    {
        this.passengerNum = passengerNum;
    }

    public Long getPassengerNum() 
    {
        return passengerNum;
    }
    public void setNoSeatChildNum(Long noSeatChildNum) 
    {
        this.noSeatChildNum = noSeatChildNum;
    }

    public Long getNoSeatChildNum() 
    {
        return noSeatChildNum;
    }
    public void setSuitcaseNumRemark(String suitcaseNumRemark) 
    {
        this.suitcaseNumRemark = suitcaseNumRemark;
    }

    public String getSuitcaseNumRemark() 
    {
        return suitcaseNumRemark;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setPincheMin(Long pincheMin) 
    {
        this.pincheMin = pincheMin;
    }

    public Long getPincheMin() 
    {
        return pincheMin;
    }
    public void setCreateUserId(String createUserId) 
    {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() 
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
    public void setModifyUserId(String modifyUserId) 
    {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyUserId() 
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
            .append("vcehicleTypeId", getVcehicleTypeId())
            .append("capacityMax", getCapacityMax())
            .append("passengerNum", getPassengerNum())
            .append("noSeatChildNum", getNoSeatChildNum())
            .append("suitcaseNumRemark", getSuitcaseNumRemark())
            .append("remark", getRemark())
            .append("typeName", getTypeName())
            .append("pincheMin", getPincheMin())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("modifyTime", getModifyTime())
            .append("modifyUserId", getModifyUserId())
            .append("isvaliable", getIsvaliable())
            .toString();
    }
}
