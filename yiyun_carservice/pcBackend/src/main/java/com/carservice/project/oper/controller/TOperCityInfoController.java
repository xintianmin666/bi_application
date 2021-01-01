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
import com.carservice.project.oper.domain.TOperCityInfo;
import com.carservice.project.oper.service.ITOperCityInfoService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 营运城市信息Controller
 * 
 * @author carservice
 * @date 2020-05-04
 */
@RestController
@RequestMapping("/oper/city")
public class TOperCityInfoController extends BaseController
{
    @Autowired
    private ITOperCityInfoService tOperCityInfoService;

    /**
     * 查询营运城市信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:city:list')")
    @GetMapping("/list")
    public TableDataInfo list(TOperCityInfo tOperCityInfo)
    {
        startPage();
        List<TOperCityInfo> list = tOperCityInfoService.selectTOperCityInfoList(tOperCityInfo);
        return getDataTable(list);
    }

    /**
     * 导出营运城市信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:city:export')")
    @Log(title = "营运城市信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TOperCityInfo tOperCityInfo)
    {
        List<TOperCityInfo> list = tOperCityInfoService.selectTOperCityInfoList(tOperCityInfo);
        ExcelUtil<TOperCityInfo> util = new ExcelUtil<TOperCityInfo>(TOperCityInfo.class);
        return util.exportExcel(list, "city");
    }

    /**
     * 获取营运城市信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:city:query')")
    @GetMapping(value = "/{operCityId}")
    public AjaxResult getInfo(@PathVariable("operCityId") Long operCityId)
    {
        return AjaxResult.success(tOperCityInfoService.selectTOperCityInfoById(operCityId));
    }

    /**
     * 新增营运城市信息
     */
    @PreAuthorize("@ss.hasPermi('oper:city:add')")
    @Log(title = "营运城市信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TOperCityInfo tOperCityInfo)
    {
        TOperCityInfo operCityInfo = new TOperCityInfo();
        operCityInfo.setOperCityName(tOperCityInfo.getOperCityName());
        if(tOperCityInfoService.selectTOperCityInfoList(operCityInfo).size()>0){
            return AjaxResult.error("已存在"+tOperCityInfo.getOperCityName());
        }
        return toAjax(tOperCityInfoService.insertTOperCityInfo(tOperCityInfo));
    }

    /**
     * 修改营运城市信息
     */
    @PreAuthorize("@ss.hasPermi('oper:city:edit')")
    @Log(title = "营运城市信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TOperCityInfo tOperCityInfo)
    {
        return toAjax(tOperCityInfoService.updateTOperCityInfo(tOperCityInfo));
    }

    /**
     * 删除营运城市信息
     */
    @PreAuthorize("@ss.hasPermi('oper:city:remove')")
    @Log(title = "营运城市信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{operCityIds}")
    public AjaxResult remove(@PathVariable Long[] operCityIds)
    {
        return toAjax(tOperCityInfoService.deleteTOperCityInfoByIds(operCityIds));
    }

    /**
     * 修改营运城市信息
     */
    @PreAuthorize("@ss.hasPermi('oper:city:edit')")
    @Log(title = "营运城市信息", businessType = BusinessType.UPDATE)
    @PostMapping("/changeIsvaliable")
    public AjaxResult changeIsvaliable(@RequestBody TOperCityInfo tOperCityInfo)
    {
        return toAjax(tOperCityInfoService.updateTOperCityInfo(tOperCityInfo));
    }
}
