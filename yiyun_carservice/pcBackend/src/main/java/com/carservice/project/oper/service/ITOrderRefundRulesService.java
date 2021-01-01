package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TOrderRefundRules;

/**
 * 订单退单规则Service接口
 * 
 * @author carservice
 * @date 2020-08-27
 */
public interface ITOrderRefundRulesService 
{
    /**
     * 查询订单退单规则
     * 
     * @param id 订单退单规则ID
     * @return 订单退单规则
     */
    public TOrderRefundRules selectTOrderRefundRulesById(Long id);

    /**
     * 查询订单退单规则列表
     * 
     * @param tOrderRefundRules 订单退单规则
     * @return 订单退单规则集合
     */
    public List<TOrderRefundRules> selectTOrderRefundRulesList(TOrderRefundRules tOrderRefundRules);

    /**
     * 新增订单退单规则
     * 
     * @param tOrderRefundRules 订单退单规则
     * @return 结果
     */
    public int insertTOrderRefundRules(TOrderRefundRules tOrderRefundRules);

    /**
     * 修改订单退单规则
     * 
     * @param tOrderRefundRules 订单退单规则
     * @return 结果
     */
    public int updateTOrderRefundRules(TOrderRefundRules tOrderRefundRules);

    /**
     * 批量删除订单退单规则
     * 
     * @param ids 需要删除的订单退单规则ID
     * @return 结果
     */
    public int deleteTOrderRefundRulesByIds(Long[] ids);

    /**
     * 删除订单退单规则信息
     * 
     * @param id 订单退单规则ID
     * @return 结果
     */
    public int deleteTOrderRefundRulesById(Long id);
}
