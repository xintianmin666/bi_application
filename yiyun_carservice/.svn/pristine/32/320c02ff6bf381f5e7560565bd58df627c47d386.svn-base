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
import com.carservice.project.oper.domain.TProductSite;
import com.carservice.project.oper.service.ITProductSiteService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 产品站点Controller
 * 
 * @author carservice
 * @date 2020-08-04
 */
@RestController
@RequestMapping("/oper/productSite")
public class TProductSiteController extends BaseController
{
    @Autowired
    private ITProductSiteService tProductSiteService;

    /**
     * 查询产品站点列表
     */
    @PreAuthorize("@ss.hasPermi('oper:productSite:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProductSite tProductSite)
    {
        startPage();
        List<TProductSite> list = tProductSiteService.selectTProductSiteList(tProductSite);
        return getDataTable(list);
    }

    /**
     * 查询产品站点列表
     */
    @GetMapping("/listByProductId")
    public AjaxResult listByProductId(TProductSite tProductSite)
    {
        List<TProductSite> list = tProductSiteService.selectTProductSiteList(tProductSite);
        return AjaxResult.success(list);
    }

    /**
     * 导出产品站点列表
     */
    @PreAuthorize("@ss.hasPermi('oper:productSite:export')")
    @Log(title = "产品站点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TProductSite tProductSite)
    {
        List<TProductSite> list = tProductSiteService.selectTProductSiteList(tProductSite);
        ExcelUtil<TProductSite> util = new ExcelUtil<TProductSite>(TProductSite.class);
        return util.exportExcel(list, "productSite");
    }

    /**
     * 获取产品站点详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:productSite:query')")
    @GetMapping(value = "/{productSiteId}")
    public AjaxResult getInfo(@PathVariable("productSiteId") Long productSiteId)
    {
        return AjaxResult.success(tProductSiteService.selectTProductSiteById(productSiteId));
    }

    /**
     * 新增产品站点
     */
    @PreAuthorize("@ss.hasPermi('oper:productSite:add')")
    @Log(title = "产品站点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProductSite tProductSite)
    {
        return toAjax(tProductSiteService.insertTProductSite(tProductSite));
    }

    /**
     * 修改产品站点
     */
    @PreAuthorize("@ss.hasPermi('oper:productSite:edit')")
    @Log(title = "产品站点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProductSite tProductSite)
    {
        return toAjax(tProductSiteService.updateTProductSite(tProductSite));
    }

    /**
     * 删除产品站点
     */
    @PreAuthorize("@ss.hasPermi('oper:productSite:remove')")
    @Log(title = "产品站点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productSiteIds}")
    public AjaxResult remove(@PathVariable Long[] productSiteIds)
    {
        return toAjax(tProductSiteService.deleteTProductSiteByIds(productSiteIds));
    }
}
