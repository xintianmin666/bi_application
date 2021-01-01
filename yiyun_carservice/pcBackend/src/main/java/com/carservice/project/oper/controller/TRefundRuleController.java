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
import com.carservice.project.oper.domain.TRefundRule;
import com.carservice.project.oper.service.ITRefundRuleService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 退单规则Controller
 * 
 * @author carservice
 * @date 2020-06-05
 */
@RestController
@RequestMapping("/oper/refundRule")
public class TRefundRuleController extends BaseController
{
    @Autowired
    private ITRefundRuleService tRefundRuleService;

    /**
     * 查询退单规则列表
     */
    @PreAuthorize("@ss.hasPermi('oper:refundRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRefundRule tRefundRule)
    {
        startPage();
        List<TRefundRule> list = tRefundRuleService.selectTRefundRuleList(tRefundRule);
        return getDataTable(list);
    }

    /**
     * 导出退单规则列表
     */
    @PreAuthorize("@ss.hasPermi('oper:refundRule:export')")
    @Log(title = "退单规则", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TRefundRule tRefundRule)
    {
        List<TRefundRule> list = tRefundRuleService.selectTRefundRuleList(tRefundRule);
        ExcelUtil<TRefundRule> util = new ExcelUtil<TRefundRule>(TRefundRule.class);
        return util.exportExcel(list, "refundRule");
    }

    /**
     * 获取退单规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:refundRule:query')")
    @GetMapping(value = "/{refundRuleId}")
    public AjaxResult getInfo(@PathVariable("refundRuleId") Long refundRuleId)
    {
        return AjaxResult.success(tRefundRuleService.selectTRefundRuleById(refundRuleId));
    }

    /**
     * 新增退单规则
     */
    @PreAuthorize("@ss.hasPermi('oper:refundRule:add')")
    @Log(title = "退单规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRefundRule tRefundRule)
    {
        return toAjax(tRefundRuleService.insertTRefundRule(tRefundRule));
    }

    /**
     * 修改退单规则
     */
    @PreAuthorize("@ss.hasPermi('oper:refundRule:edit')")
    @Log(title = "退单规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRefundRule tRefundRule)
    {
        return toAjax(tRefundRuleService.updateTRefundRule(tRefundRule));
    }

    /**
     * 删除退单规则
     */
    @PreAuthorize("@ss.hasPermi('oper:refundRule:remove')")
    @Log(title = "退单规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{refundRuleIds}")
    public AjaxResult remove(@PathVariable Long[] refundRuleIds)
    {
        return toAjax(tRefundRuleService.deleteTRefundRuleByIds(refundRuleIds));
    }
}
