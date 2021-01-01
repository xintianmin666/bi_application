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
import com.carservice.project.oper.domain.TSiteInfo;
import com.carservice.project.oper.service.ITSiteInfoService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 城市站点Controller
 * 
 * @author carservice
 * @date 2020-05-23
 */
@RestController
@RequestMapping("/oper/site")
public class TSiteInfoController extends BaseController
{
    @Autowired
    private ITSiteInfoService tSiteInfoService;

    /**
     * 查询城市站点列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TSiteInfo tSiteInfo)
    {
        startPage();
        List<TSiteInfo> list = tSiteInfoService.selectTSiteInfoList(tSiteInfo);
        return getDataTable(list);
    }

    /**
     * 查询城市站点列表
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(TSiteInfo tSiteInfo)
    {
        List<TSiteInfo> list = tSiteInfoService.selectTSiteInfoList(tSiteInfo);
        return AjaxResult.success(list);
    }

    /**
     * 导出城市站点列表
     */
    @PreAuthorize("@ss.hasPermi('oper:site:export')")
    @Log(title = "城市站点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TSiteInfo tSiteInfo)
    {
        List<TSiteInfo> list = tSiteInfoService.selectTSiteInfoList(tSiteInfo);
        ExcelUtil<TSiteInfo> util = new ExcelUtil<TSiteInfo>(TSiteInfo.class);
        return util.exportExcel(list, "site");
    }

    /**
     * 获取城市站点详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:site:query')")
    @GetMapping(value = "/{siteId}")
    public AjaxResult getInfo(@PathVariable("siteId") Long siteId)
    {
        return AjaxResult.success(tSiteInfoService.selectTSiteInfoById(siteId));
    }

    /**
     * 新增城市站点
     */
    @PreAuthorize("@ss.hasPermi('oper:site:add')")
    @Log(title = "城市站点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSiteInfo tSiteInfo)
    {
        return toAjax(tSiteInfoService.insertTSiteInfo(tSiteInfo));
    }

    /**
     * 修改城市站点
     */
    @PreAuthorize("@ss.hasPermi('oper:site:edit')")
    @Log(title = "城市站点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSiteInfo tSiteInfo)
    {
        return toAjax(tSiteInfoService.updateTSiteInfo(tSiteInfo));
    }

    /**
     * 删除城市站点
     */
    @PreAuthorize("@ss.hasPermi('oper:site:remove')")
    @Log(title = "城市站点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{siteIds}")
    public AjaxResult remove(@PathVariable Long[] siteIds)
    {
        return toAjax(tSiteInfoService.deleteTSiteInfoByIds(siteIds));
    }
}
