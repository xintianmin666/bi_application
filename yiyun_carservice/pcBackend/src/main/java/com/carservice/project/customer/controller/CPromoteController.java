package com.carservice.project.customer.controller;

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
import com.carservice.project.customer.domain.CPromote;
import com.carservice.project.customer.service.ICPromoteService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 推广人员Controller
 * 
 * @author carservice
 * @date 2020-12-18
 */
@RestController
@RequestMapping("/customer/promote")
public class CPromoteController extends BaseController
{
    @Autowired
    private ICPromoteService cPromoteService;


    /**
     * 查询推广人员列表
     */
    @PreAuthorize("@ss.hasPermi('customer:promote:list')")
    @GetMapping("/list")
    public TableDataInfo list(CPromote cPromote)
    {
        startPage();
        List<CPromote> list = cPromoteService.selectCPromoteList(cPromote);
        return getDataTable(list);
    }

    /**
     * 导出推广人员列表
     */
    @PreAuthorize("@ss.hasPermi('customer:promote:export')")
    @Log(title = "推广人员", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CPromote cPromote)
    {
        List<CPromote> list = cPromoteService.selectCPromoteList(cPromote);
        ExcelUtil<CPromote> util = new ExcelUtil<CPromote>(CPromote.class);
        return util.exportExcel(list, "promote");
    }

    /**
     * 获取推广人员详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:promote:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cPromoteService.selectCPromoteById(id));
    }

    /**
     * 新增推广人员
     */
    @PreAuthorize("@ss.hasPermi('customer:promote:add')")
    @Log(title = "推广人员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CPromote cPromote)
    {
        if (cPromoteService.selectCPromoteByPhone(cPromote.getPromotePhone())!=null)
        {
            return AjaxResult.error("新增失败，手机号"+cPromote.getPromotePhone()+"已存在");
        }
        if (cPromoteService.selectCPromoteByCode(cPromote.getPromoteCode())!=null)
        {
            return AjaxResult.error("新增失败，推广码"+cPromote.getPromoteCode()+"已存在");
        }
        return toAjax(cPromoteService.insertCPromote(cPromote));
    }

    /**
     * 修改推广人员
     */
    @PreAuthorize("@ss.hasPermi('customer:promote:edit')")
    @Log(title = "推广人员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CPromote cPromote)
    {
        CPromote cPromote1 = cPromoteService.selectCPromoteByPhone(cPromote.getPromotePhone());
        if (cPromote1!=null
                && !cPromote1.getId().equals(cPromote.getId()))
        {
            return AjaxResult.error("修改失败，手机号"+cPromote.getPromotePhone()+"已存在");
        }
        CPromote cPromote2 = cPromoteService.selectCPromoteByCode(cPromote.getPromotePhone());
        if (cPromote2!=null
                && !cPromote1.getId().equals(cPromote.getId()))
        {
            return AjaxResult.error("修改失败，推广码"+cPromote.getPromoteCode()+"已存在");
        }
        return toAjax(cPromoteService.updateCPromote(cPromote));
    }

    /**
     * 删除推广人员
     */
    @PreAuthorize("@ss.hasPermi('customer:promote:remove')")
    @Log(title = "推广人员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cPromoteService.deleteCPromoteByIds(ids));
    }
}
