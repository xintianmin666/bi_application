package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TRefundRule;

/**
 * 退单规则Service接口
 * 
 * @author carservice
 * @date 2020-06-05
 */
public interface ITRefundRuleService 
{
    /**
     * 查询退单规则
     * 
     * @param refundRuleId 退单规则ID
     * @return 退单规则
     */
    public TRefundRule selectTRefundRuleById(Long refundRuleId);

    /**
     * 查询退单规则列表
     * 
     * @param tRefundRule 退单规则
     * @return 退单规则集合
     */
    public List<TRefundRule> selectTRefundRuleList(TRefundRule tRefundRule);

    /**
     * 新增退单规则
     * 
     * @param tRefundRule 退单规则
     * @return 结果
     */
    public int insertTRefundRule(TRefundRule tRefundRule);

    /**
     * 修改退单规则
     * 
     * @param tRefundRule 退单规则
     * @return 结果
     */
    public int updateTRefundRule(TRefundRule tRefundRule);

    /**
     * 批量删除退单规则
     * 
     * @param refundRuleIds 需要删除的退单规则ID
     * @return 结果
     */
    public int deleteTRefundRuleByIds(Long[] refundRuleIds);

    /**
     * 删除退单规则信息
     * 
     * @param refundRuleId 退单规则ID
     * @return 结果
     */
    public int deleteTRefundRuleById(Long refundRuleId);
}
