package com.carservice.project.oper.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.project.oper.domain.TPriceFormula;
import com.carservice.project.oper.service.ITPriceFormulaService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 计算公式Controller
 * 
 * @author carservice
 * @date 2020-11-19
 */
@RestController
@RequestMapping("/oper/priceFormula")
public class TPriceFormulaController extends BaseController
{
    @Autowired
    private ITPriceFormulaService tPriceFormulaService;

    /**
     * 查询计算公式列表
     */
    @PreAuthorize("@ss.hasPermi('oper:priceFormula:list')")
    @GetMapping("/list")
    public TableDataInfo list(TPriceFormula tPriceFormula)
    {
        startPage();
        List<TPriceFormula> list = tPriceFormulaService.selectTPriceFormulaList(tPriceFormula);
        return getDataTable(list);
    }

    /**
     * 导出计算公式列表
     */
    @PreAuthorize("@ss.hasPermi('oper:priceFormula:export')")
    @Log(title = "计算公式", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TPriceFormula tPriceFormula)
    {
        List<TPriceFormula> list = tPriceFormulaService.selectTPriceFormulaList(tPriceFormula);
        ExcelUtil<TPriceFormula> util = new ExcelUtil<TPriceFormula>(TPriceFormula.class);
        return util.exportExcel(list, "priceFormula");
    }

    /**
     * 获取计算公式详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:priceFormula:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tPriceFormulaService.selectTPriceFormulaById(id));
    }

    /**
     * 新增计算公式
     */
    @PreAuthorize("@ss.hasPermi('oper:priceFormula:add')")
    @Log(title = "计算公式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TPriceFormula tPriceFormula)
    {
        return toAjax(tPriceFormulaService.insertTPriceFormula(tPriceFormula));
    }

    /**
     * 修改计算公式
     */
    @PreAuthorize("@ss.hasPermi('oper:priceFormula:edit')")
    @Log(title = "计算公式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TPriceFormula tPriceFormula)
    {
        return toAjax(tPriceFormulaService.updateTPriceFormula(tPriceFormula));
    }

    /**
     * 删除计算公式
     */
    @PreAuthorize("@ss.hasPermi('oper:priceFormula:remove')")
    @Log(title = "计算公式", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tPriceFormulaService.deleteTPriceFormulaByIds(ids));
    }
}
