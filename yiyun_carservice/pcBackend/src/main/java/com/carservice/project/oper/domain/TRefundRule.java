package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 退单规则对象 t_refund_rule
 * 
 * @author carservice
 * @date 2020-06-05
 */
public class TRefundRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long refundRuleId;

    /** 退单规则名称 */
    @Excel(name = "退单规则名称")
    private String refundRuleName;

    /** $column.columnComment */
    @Excel(name = "退单规则名称")
    private Long createUserId;

    public void setRefundRuleId(Long refundRuleId) 
    {
        this.refundRuleId = refundRuleId;
    }

    public Long getRefundRuleId() 
    {
        return refundRuleId;
    }
    public void setRefundRuleName(String refundRuleName) 
    {
        this.refundRuleName = refundRuleName;
    }

    public String getRefundRuleName() 
    {
        return refundRuleName;
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
            .append("refundRuleId", getRefundRuleId())
            .append("refundRuleName", getRefundRuleName())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .toString();
    }
}
