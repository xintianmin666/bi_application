package com.carservice.project.shop.controller;

import java.util.List;

import com.carservice.common.utils.SecurityUtils;
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
import com.carservice.project.shop.domain.CEvaluate;
import com.carservice.project.shop.service.ICEvaluateService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 用户评价Controller
 * 
 * @author carservice
 * @date 2020-12-17
 */
@RestController
@RequestMapping("/shop/evaluate")
public class CEvaluateController extends BaseController
{
    @Autowired
    private ICEvaluateService cEvaluateService;

    /**
     * 查询用户评价列表
     */
    @PreAuthorize("@ss.hasPermi('shop:evaluate:list')")
    @GetMapping("/list")
    public TableDataInfo list(CEvaluate cEvaluate)
    {
        startPage();
        List<CEvaluate> list = cEvaluateService.selectCEvaluateList(cEvaluate);
        return getDataTable(list);
    }

    /**
     * 导出用户评价列表
     */
    @PreAuthorize("@ss.hasPermi('shop:evaluate:export')")
    @Log(title = "用户评价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CEvaluate cEvaluate)
    {
        List<CEvaluate> list = cEvaluateService.selectCEvaluateList(cEvaluate);
        ExcelUtil<CEvaluate> util = new ExcelUtil<CEvaluate>(CEvaluate.class);
        return util.exportExcel(list, "evaluate");
    }

    /**
     * 获取用户评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:evaluate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cEvaluateService.selectCEvaluateById(id));
    }

    /**
     * 新增用户评价
     */
    @PreAuthorize("@ss.hasPermi('shop:evaluate:add')")
    @Log(title = "用户评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CEvaluate cEvaluate)
    {
        return toAjax(cEvaluateService.insertCEvaluate(cEvaluate));
    }

    /**
     * 修改用户评价
     */
    @PreAuthorize("@ss.hasPermi('shop:evaluate:edit')")
    @Log(title = "用户评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CEvaluate cEvaluate)
    {
        return toAjax(cEvaluateService.updateCEvaluate(cEvaluate));
    }

    /**
     * 删除用户评价
     */
    @PreAuthorize("@ss.hasPermi('shop:evaluate:remove')")
    @Log(title = "用户评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cEvaluateService.deleteCEvaluateByIds(ids));
    }
}
