package com.carservice.project.oper.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TPriceFormulaMapper;
import com.carservice.project.oper.domain.TPriceFormula;
import com.carservice.project.oper.service.ITPriceFormulaService;

/**
 * 计算公式Service业务层处理
 * 
 * @author carservice
 * @date 2020-11-19
 */
@Service
public class TPriceFormulaServiceImpl implements ITPriceFormulaService 
{
    @Autowired
    private TPriceFormulaMapper tPriceFormulaMapper;

    /**
     * 查询计算公式
     * 
     * @param id 计算公式ID
     * @return 计算公式
     */
    @Override
    public TPriceFormula selectTPriceFormulaById(Long id)
    {
        return tPriceFormulaMapper.selectTPriceFormulaById(id);
    }

    /**
     * 查询计算公式列表
     * 
     * @param tPriceFormula 计算公式
     * @return 计算公式
     */
    @Override
    public List<TPriceFormula> selectTPriceFormulaList(TPriceFormula tPriceFormula)
    {
        return tPriceFormulaMapper.selectTPriceFormulaList(tPriceFormula);
    }

    /**
     * 新增计算公式
     * 
     * @param tPriceFormula 计算公式
     * @return 结果
     */
    @Override
    public int insertTPriceFormula(TPriceFormula tPriceFormula)
    {
        return tPriceFormulaMapper.insertTPriceFormula(tPriceFormula);
    }

    /**
     * 修改计算公式
     * 
     * @param tPriceFormula 计算公式
     * @return 结果
     */
    @Override
    public int updateTPriceFormula(TPriceFormula tPriceFormula)
    {
        return tPriceFormulaMapper.updateTPriceFormula(tPriceFormula);
    }

    /**
     * 批量删除计算公式
     * 
     * @param ids 需要删除的计算公式ID
     * @return 结果
     */
    @Override
    public int deleteTPriceFormulaByIds(Long[] ids)
    {
        return tPriceFormulaMapper.deleteTPriceFormulaByIds(ids);
    }

    /**
     * 删除计算公式信息
     * 
     * @param id 计算公式ID
     * @return 结果
     */
    @Override
    public int deleteTPriceFormulaById(Long id)
    {
        return tPriceFormulaMapper.deleteTPriceFormulaById(id);
    }
}
