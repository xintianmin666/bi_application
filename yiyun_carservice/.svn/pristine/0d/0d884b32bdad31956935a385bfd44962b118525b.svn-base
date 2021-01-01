package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 计算公式对象 t_price_formula
 * 
 * @author carservice
 * @date 2020-11-19
 */
public class TPriceFormula extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 计算公式名称 */
    @Excel(name = "计算公式名称")
    private String name;

    /** 1：协议用户  2：非协议用户 */
    @Excel(name = "1：协议用户  2：非协议用户")
    private String userType;

    /** 1:里程收费  2：时间收费 */
    @Excel(name = "1:里程收费  2：时间收费")
    private String chargeType;

    /** 计算公式 */
    @Excel(name = "计算公式")
    private String priceFormula;

    /** 管理员设定参数 */
    @Excel(name = "管理员设定参数")
    private String adminParam;

    /** 用户参数 */
    @Excel(name = "用户参数")
    private String userParam;

    /** 1:可用  0：不可用 */
    @Excel(name = "1:可用  0：不可用")
    private String isUse;

    /** 1：全包价格  2：半包价格 */
    @Excel(name = "1：全包价格  2：半包价格")
    private String qBao;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setUserType(String userType) 
    {
        this.userType = userType;
    }

    public String getUserType() 
    {
        return userType;
    }
    public void setChargeType(String chargeType) 
    {
        this.chargeType = chargeType;
    }

    public String getChargeType() 
    {
        return chargeType;
    }
    public void setPriceFormula(String priceFormula) 
    {
        this.priceFormula = priceFormula;
    }

    public String getPriceFormula() 
    {
        return priceFormula;
    }
    public void setAdminParam(String adminParam) 
    {
        this.adminParam = adminParam;
    }

    public String getAdminParam() 
    {
        return adminParam;
    }
    public void setUserParam(String userParam) 
    {
        this.userParam = userParam;
    }

    public String getUserParam() 
    {
        return userParam;
    }
    public void setIsUse(String isUse) 
    {
        this.isUse = isUse;
    }

    public String getIsUse() 
    {
        return isUse;
    }
    public void setqBao(String qBao) 
    {
        this.qBao = qBao;
    }

    public String getqBao() 
    {
        return qBao;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("userType", getUserType())
            .append("chargeType", getChargeType())
            .append("priceFormula", getPriceFormula())
            .append("adminParam", getAdminParam())
            .append("userParam", getUserParam())
            .append("isUse", getIsUse())
            .append("qBao", getqBao())
            .toString();
    }
}
