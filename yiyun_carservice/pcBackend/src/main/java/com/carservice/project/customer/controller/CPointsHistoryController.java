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
import com.carservice.project.customer.domain.CPointsHistory;
import com.carservice.project.customer.service.ICPointsHistoryService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 积分变动明细Controller
 * 
 * @author carservice
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/customer/userPointsHistory")
public class CPointsHistoryController extends BaseController
{
    @Autowired
    private ICPointsHistoryService cPointsHistoryService;

    /**
     * 查询积分变动明细列表
     */
    @PreAuthorize("@ss.hasPermi('customer:userPointsHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(CPointsHistory cPointsHistory)
    {
        startPage();
        List<CPointsHistory> list = cPointsHistoryService.selectCPointsHistoryList(cPointsHistory);
        return getDataTable(list);
    }

    /**
     * 导出积分变动明细列表
     */
    @PreAuthorize("@ss.hasPermi('customer:userPointsHistory:export')")
    @Log(title = "积分变动明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CPointsHistory cPointsHistory)
    {
        List<CPointsHistory> list = cPointsHistoryService.selectCPointsHistoryList(cPointsHistory);
        ExcelUtil<CPointsHistory> util = new ExcelUtil<CPointsHistory>(CPointsHistory.class);
        return util.exportExcel(list, "userPointsHistory");
    }

    /**
     * 获取积分变动明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:userPointsHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cPointsHistoryService.selectCPointsHistoryById(id));
    }

    /**
     * 新增积分变动明细
     */
    @PreAuthorize("@ss.hasPermi('customer:userPointsHistory:add')")
    @Log(title = "积分变动明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CPointsHistory cPointsHistory)
    {
        return toAjax(cPointsHistoryService.insertCPointsHistory(cPointsHistory));
    }

    /**
     * 修改积分变动明细
     */
    @PreAuthorize("@ss.hasPermi('customer:userPointsHistory:edit')")
    @Log(title = "积分变动明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CPointsHistory cPointsHistory)
    {
        return toAjax(cPointsHistoryService.updateCPointsHistory(cPointsHistory));
    }

    /**
     * 删除积分变动明细
     */
    @PreAuthorize("@ss.hasPermi('customer:userPointsHistory:remove')")
    @Log(title = "积分变动明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cPointsHistoryService.deleteCPointsHistoryByIds(ids));
    }
}
