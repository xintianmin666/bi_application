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
import com.carservice.project.customer.domain.CUserInfo;
import com.carservice.project.customer.service.ICUserInfoService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * C端用户信息Controller
 * 
 * @author carservice
 * @date 2020-12-18
 */
@RestController
@RequestMapping("/customer/cUserInfo")
public class CUserInfoController extends BaseController
{
    @Autowired
    private ICUserInfoService cUserInfoService;

    /**
     * 查询C端用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('customer:cUserInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CUserInfo cUserInfo)
    {
        startPage();
        List<CUserInfo> list = cUserInfoService.selectCUserInfoList(cUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出C端用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('customer:cUserInfo:export')")
    @Log(title = "C端用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CUserInfo cUserInfo)
    {
        List<CUserInfo> list = cUserInfoService.selectCUserInfoList(cUserInfo);
        ExcelUtil<CUserInfo> util = new ExcelUtil<CUserInfo>(CUserInfo.class);
        return util.exportExcel(list, "cUserInfo");
    }

    /**
     * 获取C端用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:cUserInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cUserInfoService.selectCUserInfoById(id));
    }

    /**
     * 新增C端用户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:cUserInfo:add')")
    @Log(title = "C端用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CUserInfo cUserInfo)
    {
        return toAjax(cUserInfoService.insertCUserInfo(cUserInfo));
    }

    /**
     * 修改C端用户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:cUserInfo:edit')")
    @Log(title = "C端用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CUserInfo cUserInfo)
    {
        return toAjax(cUserInfoService.updateCUserInfo(cUserInfo));
    }

    /**
     * 删除C端用户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:cUserInfo:remove')")
    @Log(title = "C端用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cUserInfoService.deleteCUserInfoByIds(ids));
    }
}
