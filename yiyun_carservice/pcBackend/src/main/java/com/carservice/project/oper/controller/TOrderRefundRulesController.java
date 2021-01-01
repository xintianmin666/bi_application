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
import com.carservice.project.oper.domain.TOrderRefundRules;
import com.carservice.project.oper.service.ITOrderRefundRulesService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 订单退单规则Controller
 * 
 * @author carservice
 * @date 2020-08-27
 */
@RestController
@RequestMapping("/oper/orderRefundRules")
public class TOrderRefundRulesController extends BaseController
{
    @Autowired
    private ITOrderRefundRulesService tOrderRefundRulesService;

    /**
     * 查询订单退单规则列表
     */
    @PreAuthorize("@ss.hasPermi('oper:orderRefundRules:list')")
    @GetMapping("/list")
    public TableDataInfo list(TOrderRefundRules tOrderRefundRules)
    {
        startPage();
        List<TOrderRefundRules> list = tOrderRefundRulesService.selectTOrderRefundRulesList(tOrderRefundRules);
        return getDataTable(list);
    }

    /**
     * 导出订单退单规则列表
     */
    @PreAuthorize("@ss.hasPermi('oper:orderRefundRules:export')")
    @Log(title = "订单退单规则", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TOrderRefundRules tOrderRefundRules)
    {
        List<TOrderRefundRules> list = tOrderRefundRulesService.selectTOrderRefundRulesList(tOrderRefundRules);
        ExcelUtil<TOrderRefundRules> util = new ExcelUtil<TOrderRefundRules>(TOrderRefundRules.class);
        return util.exportExcel(list, "orderRefundRules");
    }

    /**
     * 获取订单退单规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:orderRefundRules:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tOrderRefundRulesService.selectTOrderRefundRulesById(id));
    }

    /**
     * 新增订单退单规则
     */
    @PreAuthorize("@ss.hasPermi('oper:orderRefundRules:add')")
    @Log(title = "订单退单规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TOrderRefundRules tOrderRefundRules)
    {
        return toAjax(tOrderRefundRulesService.insertTOrderRefundRules(tOrderRefundRules));
    }

    /**
     * 修改订单退单规则
     */
    @PreAuthorize("@ss.hasPermi('oper:orderRefundRules:edit')")
    @Log(title = "订单退单规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TOrderRefundRules tOrderRefundRules)
    {
        return toAjax(tOrderRefundRulesService.updateTOrderRefundRules(tOrderRefundRules));
    }

    /**
     * 删除订单退单规则
     */
    @PreAuthorize("@ss.hasPermi('oper:orderRefundRules:remove')")
    @Log(title = "订单退单规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tOrderRefundRulesService.deleteTOrderRefundRulesByIds(ids));
    }
}
