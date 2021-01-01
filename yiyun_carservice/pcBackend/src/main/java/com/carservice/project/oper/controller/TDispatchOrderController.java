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
import com.carservice.project.oper.domain.TDispatchOrder;
import com.carservice.project.oper.service.ITDispatchOrderService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 调度订单Controller
 * 
 * @author carservice
 * @date 2020-05-16
 */
@RestController
@RequestMapping("/.oper/dispatchOrder")
public class TDispatchOrderController extends BaseController
{
    @Autowired
    private ITDispatchOrderService tDispatchOrderService;

    /**
     * 查询调度订单列表
     */
    @PreAuthorize("@ss.hasPermi('.oper:dispatchOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(TDispatchOrder tDispatchOrder)
    {
        startPage();
        List<TDispatchOrder> list = tDispatchOrderService.selectTDispatchOrderList(tDispatchOrder);
        return getDataTable(list);
    }

    /**
     * 导出调度订单列表
     */
    @PreAuthorize("@ss.hasPermi('.oper:dispatchOrder:export')")
    @Log(title = "调度订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TDispatchOrder tDispatchOrder)
    {
        List<TDispatchOrder> list = tDispatchOrderService.selectTDispatchOrderList(tDispatchOrder);
        ExcelUtil<TDispatchOrder> util = new ExcelUtil<TDispatchOrder>(TDispatchOrder.class);
        return util.exportExcel(list, "dispatchOrder");
    }

    /**
     * 获取调度订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('.oper:dispatchOrder:query')")
    @GetMapping(value = "/{dispatchOrderId}")
    public AjaxResult getInfo(@PathVariable("dispatchOrderId") String dispatchOrderId)
    {
        return AjaxResult.success(tDispatchOrderService.selectTDispatchOrderById(dispatchOrderId));
    }

    /**
     * 新增调度订单
     */
    @PreAuthorize("@ss.hasPermi('.oper:dispatchOrder:add')")
    @Log(title = "调度订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TDispatchOrder tDispatchOrder)
    {
        return toAjax(tDispatchOrderService.insertTDispatchOrder(tDispatchOrder));
    }

    /**
     * 修改调度订单
     */
    @PreAuthorize("@ss.hasPermi('.oper:dispatchOrder:edit')")
    @Log(title = "调度订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TDispatchOrder tDispatchOrder)
    {
        return toAjax(tDispatchOrderService.updateTDispatchOrder(tDispatchOrder));
    }

    /**
     * 删除调度订单
     */
    @PreAuthorize("@ss.hasPermi('.oper:dispatchOrder:remove')")
    @Log(title = "调度订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dispatchOrderIds}")
    public AjaxResult remove(@PathVariable String[] dispatchOrderIds)
    {
        return toAjax(tDispatchOrderService.deleteTDispatchOrderByIds(dispatchOrderIds));
    }
}
