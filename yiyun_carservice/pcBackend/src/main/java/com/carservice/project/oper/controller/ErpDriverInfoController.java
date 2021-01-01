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
import com.carservice.project.oper.domain.ErpDriverInfo;
import com.carservice.project.oper.service.IErpDriverInfoService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * erp驾驶员Controller
 * 
 * @author carservice
 * @date 2020-08-27
 */
@RestController
@RequestMapping("/erpDriver/erpDriver")
public class ErpDriverInfoController extends BaseController
{
    @Autowired
    private IErpDriverInfoService erpDriverInfoService;

    /**
     * 查询erp驾驶员列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpDriverInfo erpDriverInfo)
    {
        startPage();
        List<ErpDriverInfo> list = erpDriverInfoService.selectErpDriverInfoList(erpDriverInfo);
        return getDataTable(list);
    }

    /**
     * 导出erp驾驶员列表
     */
    @PreAuthorize("@ss.hasPermi('erpDriver:erpDriver:export')")
    @Log(title = "erp驾驶员", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ErpDriverInfo erpDriverInfo)
    {
        List<ErpDriverInfo> list = erpDriverInfoService.selectErpDriverInfoList(erpDriverInfo);
        ExcelUtil<ErpDriverInfo> util = new ExcelUtil<ErpDriverInfo>(ErpDriverInfo.class);
        return util.exportExcel(list, "erpDriver");
    }

    /**
     * 获取erp驾驶员详细信息
     */
    @PreAuthorize("@ss.hasPermi('erpDriver:erpDriver:query')")
    @GetMapping(value = "/{fDriverid}")
    public AjaxResult getInfo(@PathVariable("fDriverid") String fDriverid)
    {
        return AjaxResult.success(erpDriverInfoService.selectErpDriverInfoById(fDriverid));
    }

    /**
     * 新增erp驾驶员
     */
    @PreAuthorize("@ss.hasPermi('erpDriver:erpDriver:add')")
    @Log(title = "erp驾驶员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpDriverInfo erpDriverInfo)
    {
        return toAjax(erpDriverInfoService.insertErpDriverInfo(erpDriverInfo));
    }

    /**
     * 修改erp驾驶员
     */
    @PreAuthorize("@ss.hasPermi('erpDriver:erpDriver:edit')")
    @Log(title = "erp驾驶员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpDriverInfo erpDriverInfo)
    {
        return toAjax(erpDriverInfoService.updateErpDriverInfo(erpDriverInfo));
    }

    /**
     * 删除erp驾驶员
     */
    @PreAuthorize("@ss.hasPermi('erpDriver:erpDriver:remove')")
    @Log(title = "erp驾驶员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fDriverids}")
    public AjaxResult remove(@PathVariable String[] fDriverids)
    {
        return toAjax(erpDriverInfoService.deleteErpDriverInfoByIds(fDriverids));
    }
}
