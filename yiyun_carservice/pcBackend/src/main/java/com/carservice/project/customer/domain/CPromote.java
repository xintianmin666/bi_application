package com.carservice.project.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 推广人员对象 c_promote
 * 
 * @author carservice
 * @date 2020-12-18
 */
public class CPromote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 推广码 */
    @Excel(name = "推广码")
    private String promoteCode;

    /** 推广人手机号 */
    @Excel(name = "推广人手机号")
    private String promotePhone;

    /** 推广人姓名 */
    @Excel(name = "推广人姓名")
    private String promoteName;

    /** 推广人身份证 */
    @Excel(name = "推广人身份证")
    private String promoteIdcard;

    /** 推广人职位 */
    @Excel(name = "推广人职位")
    private String promotePosition;

    /** 1:是公司；2：个人 */
    @Excel(name = "1:是公司；2：个人")
    private Integer isCompany;

    /** 来源 */
    @Excel(name = "来源")
    private Integer source;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String company;
    private String isDelete;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPromoteCode(String promoteCode) 
    {
        this.promoteCode = promoteCode;
    }

    public String getPromoteCode() 
    {
        return promoteCode;
    }
    public void setPromotePhone(String promotePhone) 
    {
        this.promotePhone = promotePhone;
    }

    public String getPromotePhone() 
    {
        return promotePhone;
    }
    public void setPromoteName(String promoteName) 
    {
        this.promoteName = promoteName;
    }

    public String getPromoteName() 
    {
        return promoteName;
    }
    public void setPromoteIdcard(String promoteIdcard) 
    {
        this.promoteIdcard = promoteIdcard;
    }

    public String getPromoteIdcard() 
    {
        return promoteIdcard;
    }
    public void setPromotePosition(String promotePosition) 
    {
        this.promotePosition = promotePosition;
    }

    public String getPromotePosition() 
    {
        return promotePosition;
    }
    public void setIsCompany(Integer isCompany) 
    {
        this.isCompany = isCompany;
    }

    public Integer getIsCompany() 
    {
        return isCompany;
    }
    public void setSource(Integer source) 
    {
        this.source = source;
    }

    public Integer getSource() 
    {
        return source;
    }
    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("promoteCode", getPromoteCode())
            .append("promotePhone", getPromotePhone())
            .append("promoteName", getPromoteName())
            .append("promoteIdcard", getPromoteIdcard())
            .append("promotePosition", getPromotePosition())
            .append("isCompany", getIsCompany())
            .append("source", getSource())
            .append("company", getCompany())
            .toString();
    }
}
