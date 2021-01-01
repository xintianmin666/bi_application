package com.carservice.project.oper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 退单规则详情对象 t_refund_rule_detail
 * 
 * @author carservice
 * @date 2020-06-05
 */
public class TRefundRuleDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long refundRuleDetailId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long refundRuleId;

    /** 最小退单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最小退单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 最晚退单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最晚退单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 退单手续费 */
    @Excel(name = "退单手续费")
    private Double refundCharge;

    /** $column.columnComment */
    @Excel(name = "退单手续费")
    private Long createUserId;

    public void setRefundRuleDetailId(Long refundRuleDetailId) 
    {
        this.refundRuleDetailId = refundRuleDetailId;
    }

    public Long getRefundRuleDetailId() 
    {
        return refundRuleDetailId;
    }
    public void setRefundRuleId(Long refundRuleId) 
    {
        this.refundRuleId = refundRuleId;
    }

    public Long getRefundRuleId() 
    {
        return refundRuleId;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setRefundCharge(Double refundCharge) 
    {
        this.refundCharge = refundCharge;
    }

    public Double getRefundCharge() 
    {
        return refundCharge;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("refundRuleDetailId", getRefundRuleDetailId())
            .append("refundRuleId", getRefundRuleId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("refundCharge", getRefundCharge())
            .append("createUserId", getCreateUserId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
