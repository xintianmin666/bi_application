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
import com.carservice.project.oper.domain.TVehicleType;
import com.carservice.project.oper.service.ITVehicleTypeService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 车辆类型Controller
 * 
 * @author carservice
 * @date 2020-08-25
 */
@RestController
@RequestMapping("/oper/vehicleType")
public class TVehicleTypeController extends BaseController
{
    @Autowired
    private ITVehicleTypeService tVehicleTypeService;

    /**
     * 查询车辆类型列表
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleType:list')")
    @GetMapping("/list")
    public TableDataInfo list(TVehicleType tVehicleType)
    {
        startPage();
        List<TVehicleType> list = tVehicleTypeService.selectTVehicleTypeList(tVehicleType);
        return getDataTable(list);
    }

    /**
     * 导出车辆类型列表
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleType:export')")
    @Log(title = "车辆类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TVehicleType tVehicleType)
    {
        List<TVehicleType> list = tVehicleTypeService.selectTVehicleTypeList(tVehicleType);
        ExcelUtil<TVehicleType> util = new ExcelUtil<TVehicleType>(TVehicleType.class);
        return util.exportExcel(list, "vehicleType");
    }

    /**
     * 获取车辆类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleType:query')")
    @GetMapping(value = "/{vcehicleTypeId}")
    public AjaxResult getInfo(@PathVariable("vcehicleTypeId") Long vcehicleTypeId)
    {
        return AjaxResult.success(tVehicleTypeService.selectTVehicleTypeById(vcehicleTypeId));
    }

    /**
     * 新增车辆类型
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleType:add')")
    @Log(title = "车辆类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TVehicleType tVehicleType)
    {
        return toAjax(tVehicleTypeService.insertTVehicleType(tVehicleType));
    }

    /**
     * 修改车辆类型
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleType:edit')")
    @Log(title = "车辆类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TVehicleType tVehicleType)
    {
        return toAjax(tVehicleTypeService.updateTVehicleType(tVehicleType));
    }

    /**
     * 删除车辆类型
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleType:remove')")
    @Log(title = "车辆类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{vcehicleTypeIds}")
    public AjaxResult remove(@PathVariable Long[] vcehicleTypeIds)
    {
        return toAjax(tVehicleTypeService.deleteTVehicleTypeByIds(vcehicleTypeIds));
    }
}
