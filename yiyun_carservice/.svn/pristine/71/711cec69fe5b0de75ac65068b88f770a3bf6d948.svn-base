package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TPriceFormula;

/**
 * 计算公式Service接口
 * 
 * @author carservice
 * @date 2020-11-19
 */
public interface ITPriceFormulaService 
{
    /**
     * 查询计算公式
     * 
     * @param id 计算公式ID
     * @return 计算公式
     */
    public TPriceFormula selectTPriceFormulaById(Long id);

    /**
     * 查询计算公式列表
     * 
     * @param tPriceFormula 计算公式
     * @return 计算公式集合
     */
    public List<TPriceFormula> selectTPriceFormulaList(TPriceFormula tPriceFormula);

    /**
     * 新增计算公式
     * 
     * @param tPriceFormula 计算公式
     * @return 结果
     */
    public int insertTPriceFormula(TPriceFormula tPriceFormula);

    /**
     * 修改计算公式
     * 
     * @param tPriceFormula 计算公式
     * @return 结果
     */
    public int updateTPriceFormula(TPriceFormula tPriceFormula);

    /**
     * 批量删除计算公式
     * 
     * @param ids 需要删除的计算公式ID
     * @return 结果
     */
    public int deleteTPriceFormulaByIds(Long[] ids);

    /**
     * 删除计算公式信息
     * 
     * @param id 计算公式ID
     * @return 结果
     */
    public int deleteTPriceFormulaById(Long id);
}
