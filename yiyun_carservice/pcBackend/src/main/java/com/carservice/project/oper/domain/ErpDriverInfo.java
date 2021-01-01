package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * erp驾驶员对象 erp_driver_info
 * 
 * @author carservice
 * @date 2020-08-27
 */
public class ErpDriverInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private String fDriverid;

    /** 驾照类型 */
    @Excel(name = "驾照类型")
    private String fDrivingpermittype;

    /** 单位id */
    @Excel(name = "单位id")
    private String fUnitid;

    /** 部门id */
    @Excel(name = "部门id")
    private String fDeptid;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String fUnitname;

    /** 姓名 */
    @Excel(name = "姓名")
    private String fDrivername;

    /** 手机号 */
    @Excel(name = "手机号")
    private String fPhonecode;

    /**  身份证 */
    @Excel(name = " 身份证")
    private String fIdcard;

    /** 驾驶员状态 */
    @Excel(name = "驾驶员状态")
    private String fDriverstate;

    private String fOpenId;

    public String getfOpenId() {
        return fOpenId;
    }

    public void setfOpenId(String fOpenId) {
        this.fOpenId = fOpenId;
    }

    public void setfDriverid(String fDriverid)
    {
        this.fDriverid = fDriverid;
    }

    public String getfDriverid() 
    {
        return fDriverid;
    }
    public void setfDrivingpermittype(String fDrivingpermittype) 
    {
        this.fDrivingpermittype = fDrivingpermittype;
    }

    public String getfDrivingpermittype() 
    {
        return fDrivingpermittype;
    }
    public void setfUnitid(String fUnitid) 
    {
        this.fUnitid = fUnitid;
    }

    public String getfUnitid() 
    {
        return fUnitid;
    }
    public void setfDeptid(String fDeptid) 
    {
        this.fDeptid = fDeptid;
    }

    public String getfDeptid() 
    {
        return fDeptid;
    }
    public void setfUnitname(String fUnitname) 
    {
        this.fUnitname = fUnitname;
    }

    public String getfUnitname() 
    {
        return fUnitname;
    }
    public void setfDrivername(String fDrivername) 
    {
        this.fDrivername = fDrivername;
    }

    public String getfDrivername() 
    {
        return fDrivername;
    }
    public void setfPhonecode(String fPhonecode) 
    {
        this.fPhonecode = fPhonecode;
    }

    public String getfPhonecode() 
    {
        return fPhonecode;
    }
    public void setfIdcard(String fIdcard) 
    {
        this.fIdcard = fIdcard;
    }

    public String getfIdcard() 
    {
        return fIdcard;
    }
    public void setfDriverstate(String fDriverstate) 
    {
        this.fDriverstate = fDriverstate;
    }

    public String getfDriverstate() 
    {
        return fDriverstate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fDriverid", getfDriverid())
            .append("fDrivingpermittype", getfDrivingpermittype())
            .append("fUnitid", getfUnitid())
            .append("fDeptid", getfDeptid())
            .append("fUnitname", getfUnitname())
            .append("fDrivername", getfDrivername())
            .append("fPhonecode", getfPhonecode())
            .append("fIdcard", getfIdcard())
            .append("fDriverstate", getfDriverstate())
            .toString();
    }
}
