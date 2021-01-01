package com.carservice.project.oper.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
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
import com.carservice.project.oper.domain.TVehicleInfo;
import com.carservice.project.oper.service.ITVehicleInfoService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 车辆信息Controller
 * 
 * @author carservice
 * @date 2020-04-29
 */
@RestController
@RequestMapping("/oper/vehicle")
public class TVehicleInfoController extends BaseController
{
    @Autowired
    private ITVehicleInfoService tVehicleInfoService;

    /**
     * 查询可用排班车辆信息列表
     */
    @ApiOperation("查询可用排班车辆信息列表")
    @PreAuthorize("@ss.hasPermi('oper:vehicle:list')")
    @GetMapping("/listCanUse")
    public AjaxResult listCanUse(TVehicleInfo tVehicleInfo)
    {

        List<TVehicleInfo> list = tVehicleInfoService.selectCanUseTVehicleInfoList(tVehicleInfo);
        return AjaxResult.success(list);
    }


    /**
     * 查询车辆信息列表
     */
    @ApiOperation("查询车辆信息列表")
    @PreAuthorize("@ss.hasPermi('oper:vehicle:list')")
    @GetMapping("/list")
    public TableDataInfo list(TVehicleInfo tVehicleInfo)
    {
        startPage();
        List<TVehicleInfo> list = tVehicleInfoService.selectTVehicleInfoList(tVehicleInfo);
        return getDataTable(list);
    }

    /**
     * 导出车辆信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:vehicle:export')")
    @Log(title = "车辆信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TVehicleInfo tVehicleInfo)
    {
        List<TVehicleInfo> list = tVehicleInfoService.selectTVehicleInfoList(tVehicleInfo);
        ExcelUtil<TVehicleInfo> util = new ExcelUtil<TVehicleInfo>(TVehicleInfo.class);
        return util.exportExcel(list, "vehicle");
    }

    /**
     * 获取车辆信息详细信息
     */
    @ApiOperation("获取车辆信息详细信息")
    @PreAuthorize("@ss.hasPermi('oper:vehicle:query')")
    @GetMapping(value = "/{vehicleId}")
    public AjaxResult getInfo(@PathVariable("vehicleId") String vehicleId)
    {
        return AjaxResult.success(tVehicleInfoService.selectTVehicleInfoById(vehicleId));
    }

    /**
     * 新增车辆信息
     */
    @ApiOperation("新增车辆信息")
    @PreAuthorize("@ss.hasPermi('oper:vehicle:add')")
    @Log(title = "车辆信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TVehicleInfo tVehicleInfo)
    {
        int result = tVehicleInfoService.insertTVehicleInfo(tVehicleInfo);
        if (result==-1){
            return AjaxResult.error(tVehicleInfo.getLicenseTagno()+"车牌号已存在,添加失败");
        }else{
            return toAjax(result);
        }
    }

    /**
     * 修改车辆信息
     */
    @ApiOperation("修改车辆信息")
    @PreAuthorize("@ss.hasPermi('oper:vehicle:edit')")
    @Log(title = "车辆信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TVehicleInfo tVehicleInfo)
    {
        int result = tVehicleInfoService.updateTVehicleInfo(tVehicleInfo);
        if (result==-1){
            return AjaxResult.error(tVehicleInfo.getLicenseTagno()+"车牌号已存在,修改失败");
        }else{
            return toAjax(result);
        }
    }

    /**
     * 删除车辆信息
     */
    @ApiOperation("删除车辆信息")
    @PreAuthorize("@ss.hasPermi('oper:vehicle:remove')")
    @Log(title = "车辆信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{vehicleIds}")
    public AjaxResult remove(@PathVariable String[] vehicleIds)
    {
        return toAjax(tVehicleInfoService.deleteTVehicleInfoByIds(vehicleIds));
    }

    /**
     * 获取车辆类型
     * @return
     */
    @RequestMapping("getCarType")
    public AjaxResult getCarType()
    {
        return AjaxResult.success(tVehicleInfoService.getCarType());
    }

    /**
     * 获取可用车辆
     * @return
     */
    @RequestMapping("getCanUseCarList")
    public AjaxResult getCanUseCarList(TVehicleInfo tVehicleInfo)
    {
        return AjaxResult.success(tVehicleInfoService.selectCanUseTVehicleInfoList(tVehicleInfo));
    }
}

