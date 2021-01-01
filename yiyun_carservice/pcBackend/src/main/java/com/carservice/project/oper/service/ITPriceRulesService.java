package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TPriceRules;

/**
 * 计价规则Service接口
 * 
 * @author carservice
 * @date 2020-11-06
 */
public interface ITPriceRulesService 
{
    /**
     * 查询计价规则
     * 
     * @param id 计价规则ID
     * @return 计价规则
     */
    public TPriceRules selectTPriceRulesById(Long id);

    /**
     * 查询计价规则列表
     * 
     * @param tPriceRules 计价规则
     * @return 计价规则集合
     */
    public List<TPriceRules> selectTPriceRulesList(TPriceRules tPriceRules);

    /**
     * 新增计价规则
     * 
     * @param tPriceRules 计价规则
     * @return 结果
     */
    public int insertTPriceRules(TPriceRules tPriceRules);

    /**
     * 修改计价规则
     * 
     * @param tPriceRules 计价规则
     * @return 结果
     */
    public int updateTPriceRules(TPriceRules tPriceRules);

    /**
     * 批量删除计价规则
     * 
     * @param ids 需要删除的计价规则ID
     * @return 结果
     */
    public int deleteTPriceRulesByIds(Long[] ids);

    /**
     * 删除计价规则信息
     * 
     * @param id 计价规则ID
     * @return 结果
     */
    public int deleteTPriceRulesById(Long id);
}
