package com.carservice.project.order.controller;

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
import com.carservice.project.order.domain.TRentalcarsMessage;
import com.carservice.project.order.service.ITRentalcarsMessageService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 拼包车订单乘客Controller
 * 
 * @author carservice
 * @date 2020-08-13
 */
@RestController
@RequestMapping("/order/TPassenger")
public class TRentalcarsMessageController extends BaseController
{
    @Autowired
    private ITRentalcarsMessageService tRentalcarsMessageService;

    /**
     * 查询拼包车订单乘客列表
     */
    @PreAuthorize("@ss.hasPermi('order:TPassenger:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRentalcarsMessage tRentalcarsMessage)
    {
        startPage();
        List<TRentalcarsMessage> list = tRentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
        return getDataTable(list);
    }

    /**
     * 导出拼包车订单乘客列表
     */
    @PreAuthorize("@ss.hasPermi('order:TPassenger:export')")
    @Log(title = "拼包车订单乘客", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TRentalcarsMessage tRentalcarsMessage)
    {
        List<TRentalcarsMessage> list = tRentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
        ExcelUtil<TRentalcarsMessage> util = new ExcelUtil<TRentalcarsMessage>(TRentalcarsMessage.class);
        return util.exportExcel(list, "TPassenger");
    }

    /**
     * 获取拼包车订单乘客详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:TPassenger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tRentalcarsMessageService.selectTRentalcarsMessageById(id));
    }

    /**
     * 新增拼包车订单乘客
     */
    @PreAuthorize("@ss.hasPermi('order:TPassenger:add')")
    @Log(title = "拼包车订单乘客", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRentalcarsMessage tRentalcarsMessage)
    {
        return toAjax(tRentalcarsMessageService.insertTRentalcarsMessage(tRentalcarsMessage));
    }

    /**
     * 修改拼包车订单乘客
     */
    @PreAuthorize("@ss.hasPermi('order:TPassenger:edit')")
    @Log(title = "拼包车订单乘客", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRentalcarsMessage tRentalcarsMessage)
    {
        return toAjax(tRentalcarsMessageService.updateTRentalcarsMessage(tRentalcarsMessage));
    }

    /**
     * 删除拼包车订单乘客
     */
    @PreAuthorize("@ss.hasPermi('order:TPassenger:remove')")
    @Log(title = "拼包车订单乘客", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tRentalcarsMessageService.deleteTRentalcarsMessageByIds(ids));
    }
}
