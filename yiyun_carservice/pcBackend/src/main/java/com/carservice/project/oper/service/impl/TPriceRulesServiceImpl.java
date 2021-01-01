package com.carservice.project.oper.service.impl;

import java.util.List;

import com.carservice.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TPriceRulesMapper;
import com.carservice.project.oper.domain.TPriceRules;
import com.carservice.project.oper.service.ITPriceRulesService;

/**
 * 计价规则Service业务层处理
 * 
 * @author carservice
 * @date 2020-11-06
 */
@Service
public class TPriceRulesServiceImpl implements ITPriceRulesService 
{
    @Autowired
    private TPriceRulesMapper tPriceRulesMapper;

    /**
     * 查询计价规则
     * 
     * @param id 计价规则ID
     * @return 计价规则
     */
    @Override
    public TPriceRules selectTPriceRulesById(Long id)
    {
        return tPriceRulesMapper.selectTPriceRulesById(id);
    }

    /**
     * 查询计价规则列表
     * 
     * @param tPriceRules 计价规则
     * @return 计价规则
     */
    @Override
    @DataScope(deptAlias = "r")
    public List<TPriceRules> selectTPriceRulesList(TPriceRules tPriceRules)
    {
        return tPriceRulesMapper.selectTPriceRulesList(tPriceRules);
    }

    public List<TPriceRules> selectTPriceRulesList1(TPriceRules tPriceRules)
    {
        return tPriceRulesMapper.selectTPriceRulesList(tPriceRules);
    }

    /**
     * 新增计价规则
     * 
     * @param tPriceRules 计价规则
     * @return 结果
     */
    @Override
    public int insertTPriceRules(TPriceRules tPriceRules)
    {
        return tPriceRulesMapper.insertTPriceRules(tPriceRules);
    }

    /**
     * 修改计价规则
     * 
     * @param tPriceRules 计价规则
     * @return 结果
     */
    @Override
    public int updateTPriceRules(TPriceRules tPriceRules)
    {
        return tPriceRulesMapper.updateTPriceRules(tPriceRules);
    }

    /**
     * 批量删除计价规则
     * 
     * @param ids 需要删除的计价规则ID
     * @return 结果
     */
    @Override
    public int deleteTPriceRulesByIds(Long[] ids)
    {
        return tPriceRulesMapper.deleteTPriceRulesByIds(ids);
    }

    /**
     * 删除计价规则信息
     * 
     * @param id 计价规则ID
     * @return 结果
     */
    @Override
    public int deleteTPriceRulesById(Long id)
    {
        return tPriceRulesMapper.deleteTPriceRulesById(id);
    }
}
