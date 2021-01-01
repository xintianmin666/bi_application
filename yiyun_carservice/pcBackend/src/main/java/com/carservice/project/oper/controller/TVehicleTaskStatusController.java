package com.carservice.project.oper.controller;

import java.util.List;

import com.carservice.project.oper.service.impl.TVehicleInfoServiceImpl;
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
import com.carservice.project.oper.domain.TVehicleTaskStatus;
import com.carservice.project.oper.service.ITVehicleTaskStatusService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 车辆运行记录Controller
 *
 * @author carservice
 * @date 2020-05-12
 */
@RestController
@RequestMapping("/oper/vehicleTaskStatus")
public class TVehicleTaskStatusController extends BaseController
{
    @Autowired
    private ITVehicleTaskStatusService tVehicleTaskStatusService;

    @Autowired
    private TVehicleInfoServiceImpl vehicleInfoService;

    /**
     * 查询车辆运行记录列表(弃用)
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:list')")
    @GetMapping("/list")
    public TableDataInfo list(TVehicleTaskStatus tVehicleTaskStatus)
    {
        startPage();
        List list = tVehicleTaskStatusService.selectTVehicleTaskStatusList(tVehicleTaskStatus);
        return getDataTable(list);
    }

    /**
     * 查询驾驶员排班记录列表
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:list')")
    @GetMapping("/driverList")
    public AjaxResult driverList(TVehicleTaskStatus tVehicleTaskStatus)
    {
        tVehicleTaskStatus.setPageSize(0);
        List list = tVehicleTaskStatusService.selectDriverTaskStatusList(tVehicleTaskStatus);
        return AjaxResult.success(list);
    }

    /**
     * 查询车辆班记录列表
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:list')")
    @GetMapping("/carList")
    public AjaxResult carList(TVehicleTaskStatus tVehicleTaskStatus)
    {
        tVehicleTaskStatus.setPageSize(0);
        List list = tVehicleTaskStatusService.selectCarTaskStatusList(tVehicleTaskStatus);
        tVehicleTaskStatus.setList(list);
        list = vehicleInfoService.selectTVehicleInfoListNotin(tVehicleTaskStatus);
        return AjaxResult.success(list);
    }

    /**
     * 导出车辆运行记录列表
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:export')")
    @Log(title = "车辆运行记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TVehicleTaskStatus tVehicleTaskStatus)
    {
        List<TVehicleTaskStatus> list = tVehicleTaskStatusService.selectTVehicleTaskStatusList(tVehicleTaskStatus);
        ExcelUtil<TVehicleTaskStatus> util = new ExcelUtil<TVehicleTaskStatus>(TVehicleTaskStatus.class);
        return util.exportExcel(list, "vehicleTaskStatus");
    }

    /**
     * 获取车辆运行记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:list')")
    @GetMapping(value = "/{vehicleTaskStatusId}")
    public AjaxResult getInfo(@PathVariable("vehicleTaskStatusId") String vehicleTaskStatusId)
    {
        return AjaxResult.success(tVehicleTaskStatusService.selectTVehicleTaskStatusById(vehicleTaskStatusId));
    }

    /**
     * 新增车辆运行记录
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:add')")
    @Log(title = "车辆运行记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TVehicleTaskStatus tVehicleTaskStatus)
    {
        return toAjax(tVehicleTaskStatusService.insertTVehicleTaskStatus(tVehicleTaskStatus));
    }

    /**
     * 修改车辆运行记录
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:edit')")
    @Log(title = "车辆运行记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TVehicleTaskStatus tVehicleTaskStatus)
    {
        return toAjax(tVehicleTaskStatusService.updateTVehicleTaskStatus(tVehicleTaskStatus));
    }

    /**
     * 删除车辆运行记录
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicleTaskStatus:remove')")
    @Log(title = "车辆运行记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{vehicleTaskStatusIds}")
    public AjaxResult remove(@PathVariable String[] vehicleTaskStatusIds)
    {
        return toAjax(tVehicleTaskStatusService.deleteTVehicleTaskStatusByIds(vehicleTaskStatusIds));
    }

}