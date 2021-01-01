package com.carservice.project.oper.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.carservice.project.oper.domain.ErpCarInfo;
import com.carservice.project.oper.service.IErpCarInfoService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * erp车辆Controller
 * 
 * @author carservice
 * @date 2020-08-27
 */
@RestController
@RequestMapping("/oper/erpCar")
public class ErpCarInfoController extends BaseController
{
    @Autowired
    private IErpCarInfoService erpCarInfoService;

    /**
     * 查询erp车辆列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpCarInfo erpCarInfo)
    {
        startPage();
        List<ErpCarInfo> list = erpCarInfoService.selectErpCarInfoList(erpCarInfo);
        return getDataTable(list);
    }

    /**
     * 导出erp车辆列表
     */
    @PreAuthorize("@ss.hasPermi('oper:erpCar:export')")
    @Log(title = "erp车辆", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ErpCarInfo erpCarInfo)
    {
        List<ErpCarInfo> list = erpCarInfoService.selectErpCarInfoList(erpCarInfo);
        ExcelUtil<ErpCarInfo> util = new ExcelUtil<ErpCarInfo>(ErpCarInfo.class);
        return util.exportExcel(list, "erpCar");
    }

    /**
     * 获取erp车辆详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:erpCar:query')")
    @GetMapping(value = "/{fId}")
    public AjaxResult getInfo(@PathVariable("fId") String fId)
    {
        return AjaxResult.success(erpCarInfoService.selectErpCarInfoById(fId));
    }

    /**
     * 新增erp车辆
     */
    @PreAuthorize("@ss.hasPermi('oper:erpCar:add')")
    @Log(title = "erp车辆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpCarInfo erpCarInfo)
    {
        return toAjax(erpCarInfoService.insertErpCarInfo(erpCarInfo));
    }

    /**
     * 修改erp车辆
     */
    @PreAuthorize("@ss.hasPermi('oper:erpCar:edit')")
    @Log(title = "erp车辆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpCarInfo erpCarInfo)
    {
        return toAjax(erpCarInfoService.updateErpCarInfo(erpCarInfo));
    }

    /**
     * 删除erp车辆
     */
    @PreAuthorize("@ss.hasPermi('oper:erpCar:remove')")
    @Log(title = "erp车辆", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fIds}")
    public AjaxResult remove(@PathVariable String[] fIds)
    {
        return toAjax(erpCarInfoService.deleteErpCarInfoByIds(fIds));
    }

}
