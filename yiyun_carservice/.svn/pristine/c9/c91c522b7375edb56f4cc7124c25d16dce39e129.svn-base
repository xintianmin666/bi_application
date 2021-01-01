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
import com.carservice.project.oper.domain.TPriceRules;
import com.carservice.project.oper.service.ITPriceRulesService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 计价规则Controller
 * 
 * @author carservice
 * @date 2020-11-06
 */
@RestController
@RequestMapping("/oper/priceRules")
public class TPriceRulesController extends BaseController
{
    @Autowired
    private ITPriceRulesService tPriceRulesService;

    /**
     * 查询计价规则列表
     */
    @PreAuthorize("@ss.hasPermi('oper:priceRules:list')")
    @GetMapping("/list")
    public TableDataInfo list(TPriceRules tPriceRules)
    {
        startPage();
        List<TPriceRules> list = tPriceRulesService.selectTPriceRulesList(tPriceRules);
        return getDataTable(list);
    }

    /**
     * 导出计价规则列表
     */
    @PreAuthorize("@ss.hasPermi('oper:priceRules:export')")
    @Log(title = "计价规则", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TPriceRules tPriceRules)
    {
        List<TPriceRules> list = tPriceRulesService.selectTPriceRulesList(tPriceRules);
        ExcelUtil<TPriceRules> util = new ExcelUtil<TPriceRules>(TPriceRules.class);
        return util.exportExcel(list, "priceRules");
    }

    /**
     * 获取计价规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:priceRules:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tPriceRulesService.selectTPriceRulesById(id));
    }

    /**
     * 新增计价规则
     */
    @PreAuthorize("@ss.hasPermi('oper:priceRules:add')")
    @Log(title = "计价规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TPriceRules tPriceRules)
    {
        return toAjax(tPriceRulesService.insertTPriceRules(tPriceRules));
    }

    /**
     * 修改计价规则
     */
    @PreAuthorize("@ss.hasPermi('oper:priceRules:edit')")
    @Log(title = "计价规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TPriceRules tPriceRules)
    {
        return toAjax(tPriceRulesService.updateTPriceRules(tPriceRules));
    }

    /**
     * 删除计价规则
     */
    @PreAuthorize("@ss.hasPermi('oper:priceRules:remove')")
    @Log(title = "计价规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tPriceRulesService.deleteTPriceRulesByIds(ids));
    }
}
