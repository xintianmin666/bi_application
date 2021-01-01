package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TRefundRuleMapper;
import com.carservice.project.oper.domain.TRefundRule;
import com.carservice.project.oper.service.ITRefundRuleService;

/**
 * 退单规则Service业务层处理
 * 
 * @author carservice
 * @date 2020-06-05
 */
@Service
public class TRefundRuleServiceImpl implements ITRefundRuleService 
{
    @Autowired
    private TRefundRuleMapper tRefundRuleMapper;

    /**
     * 查询退单规则
     * 
     * @param refundRuleId 退单规则ID
     * @return 退单规则
     */
    @Override
    public TRefundRule selectTRefundRuleById(Long refundRuleId)
    {
        return tRefundRuleMapper.selectTRefundRuleById(refundRuleId);
    }

    /**
     * 查询退单规则列表
     * 
     * @param tRefundRule 退单规则
     * @return 退单规则
     */
    @Override
    public List<TRefundRule> selectTRefundRuleList(TRefundRule tRefundRule)
    {
        return tRefundRuleMapper.selectTRefundRuleList(tRefundRule);
    }

    /**
     * 新增退单规则
     * 
     * @param tRefundRule 退单规则
     * @return 结果
     */
    @Override
    public int insertTRefundRule(TRefundRule tRefundRule)
    {
        tRefundRule.setCreateTime(DateUtils.getNowDate());
        return tRefundRuleMapper.insertTRefundRule(tRefundRule);
    }

    /**
     * 修改退单规则
     * 
     * @param tRefundRule 退单规则
     * @return 结果
     */
    @Override
    public int updateTRefundRule(TRefundRule tRefundRule)
    {
        return tRefundRuleMapper.updateTRefundRule(tRefundRule);
    }

    /**
     * 批量删除退单规则
     * 
     * @param refundRuleIds 需要删除的退单规则ID
     * @return 结果
     */
    @Override
    public int deleteTRefundRuleByIds(Long[] refundRuleIds)
    {
        return tRefundRuleMapper.deleteTRefundRuleByIds(refundRuleIds);
    }

    /**
     * 删除退单规则信息
     * 
     * @param refundRuleId 退单规则ID
     * @return 结果
     */
    @Override
    public int deleteTRefundRuleById(Long refundRuleId)
    {
        return tRefundRuleMapper.deleteTRefundRuleById(refundRuleId);
    }
}
