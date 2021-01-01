package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * erp车辆对象 erp_car_info
 * 
 * @author carservice
 * @date 2020-08-27
 */
public class ErpCarInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String fPlateno;

    /** 单位 */
    @Excel(name = "单位")
    private String fUnitid;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String fVehicletype;

    /** 座位数 */
    @Excel(name = "座位数")
    private Integer fSeatno;

    /** 最大承载乘客数 */
    @Excel(name = "最大承载乘客数")
    private Integer fCarryno;

    /** 品牌 */
    @Excel(name = "品牌")
    private String fBrandname;

    /** 车辆状态 */
    @Excel(name = "车辆状态")
    private String fState;

    public void setfId(String fId) 
    {
        this.fId = fId;
    }

    public String getfId() 
    {
        return fId;
    }
    public void setfPlateno(String fPlateno) 
    {
        this.fPlateno = fPlateno;
    }

    public String getfPlateno() 
    {
        return fPlateno;
    }
    public void setfUnitid(String fUnitid) 
    {
        this.fUnitid = fUnitid;
    }

    public String getfUnitid() 
    {
        return fUnitid;
    }
    public void setfVehicletype(String fVehicletype) 
    {
        this.fVehicletype = fVehicletype;
    }

    public String getfVehicletype() 
    {
        return fVehicletype;
    }
    public void setfSeatno(Integer fSeatno) 
    {
        this.fSeatno = fSeatno;
    }

    public Integer getfSeatno() 
    {
        return fSeatno;
    }
    public void setfCarryno(Integer fCarryno) 
    {
        this.fCarryno = fCarryno;
    }

    public Integer getfCarryno() 
    {
        return fCarryno;
    }
    public void setfBrandname(String fBrandname) 
    {
        this.fBrandname = fBrandname;
    }

    public String getfBrandname() 
    {
        return fBrandname;
    }
    public void setfState(String fState) 
    {
        this.fState = fState;
    }

    public String getfState() 
    {
        return fState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fId", getfId())
            .append("fPlateno", getfPlateno())
            .append("fUnitid", getfUnitid())
            .append("fVehicletype", getfVehicletype())
            .append("fSeatno", getfSeatno())
            .append("fCarryno", getfCarryno())
            .append("fBrandname", getfBrandname())
            .append("fState", getfState())
            .toString();
    }
}
