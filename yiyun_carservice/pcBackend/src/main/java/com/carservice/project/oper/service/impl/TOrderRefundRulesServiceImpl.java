package com.carservice.project.oper.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TOrderRefundRulesMapper;
import com.carservice.project.oper.domain.TOrderRefundRules;
import com.carservice.project.oper.service.ITOrderRefundRulesService;

/**
 * 订单退单规则Service业务层处理
 * 
 * @author carservice
 * @date 2020-08-27
 */
@Service
public class TOrderRefundRulesServiceImpl implements ITOrderRefundRulesService 
{
    @Autowired
    private TOrderRefundRulesMapper tOrderRefundRulesMapper;

    /**
     * 查询订单退单规则
     * 
     * @param id 订单退单规则ID
     * @return 订单退单规则
     */
    @Override
    public TOrderRefundRules selectTOrderRefundRulesById(Long id)
    {
        return tOrderRefundRulesMapper.selectTOrderRefundRulesById(id);
    }

    /**
     * 查询订单退单规则列表
     * 
     * @param tOrderRefundRules 订单退单规则
     * @return 订单退单规则
     */
    @Override
    public List<TOrderRefundRules> selectTOrderRefundRulesList(TOrderRefundRules tOrderRefundRules)
    {
        return tOrderRefundRulesMapper.selectTOrderRefundRulesList(tOrderRefundRules);
    }

    /**
     * 新增订单退单规则
     * 
     * @param tOrderRefundRules 订单退单规则
     * @return 结果
     */
    @Override
    public int insertTOrderRefundRules(TOrderRefundRules tOrderRefundRules)
    {
        return tOrderRefundRulesMapper.insertTOrderRefundRules(tOrderRefundRules);
    }

    /**
     * 修改订单退单规则
     * 
     * @param tOrderRefundRules 订单退单规则
     * @return 结果
     */
    @Override
    public int updateTOrderRefundRules(TOrderRefundRules tOrderRefundRules)
    {
        return tOrderRefundRulesMapper.updateTOrderRefundRules(tOrderRefundRules);
    }

    /**
     * 批量删除订单退单规则
     * 
     * @param ids 需要删除的订单退单规则ID
     * @return 结果
     */
    @Override
    public int deleteTOrderRefundRulesByIds(Long[] ids)
    {
        return tOrderRefundRulesMapper.deleteTOrderRefundRulesByIds(ids);
    }

    /**
     * 删除订单退单规则信息
     * 
     * @param id 订单退单规则ID
     * @return 结果
     */
    @Override
    public int deleteTOrderRefundRulesById(Long id)
    {
        return tOrderRefundRulesMapper.deleteTOrderRefundRulesById(id);
    }
}
