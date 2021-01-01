package com.carservice.project.order.controller;

import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.order.domain.TTaxiOrder;
import com.carservice.project.order.service.ITRentalcarsMessageService;
import com.carservice.project.order.service.ITTaxiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出租车订单Controller
 * @author carservice
 * @date 2020-07-03
 */
@RestController
@RequestMapping("/order/taxiOrder")
public class TTaxiOrderController extends BaseController {
    @Autowired
    private ITTaxiOrderService tTaxiOrderService;

    @Autowired
    private ITRentalcarsMessageService tRentalcarsMessageService;

    /**
     * 查询出租车订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTaxiOrder tTaxiOrder) {
        startPage();
        List< TTaxiOrder > list = tTaxiOrderService.selectTTaxiOrderList(tTaxiOrder);
        return getDataTable(list);
    }

    /**
     * 导出出租车订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:export')")
    @Log(title = "出租车订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTaxiOrder tTaxiOrder) {
        List< TTaxiOrder > list = tTaxiOrderService.selectTTaxiOrderList(tTaxiOrder);
        ExcelUtil< TTaxiOrder > util = new ExcelUtil< TTaxiOrder >(TTaxiOrder.class);
        return util.exportExcel(list, "taxiOrder");
    }

    /**
     * 获取出租车订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tTaxiOrderService.selectTTaxiOrderById(id));
    }

    /**
     * 新增出租车订单
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:add')")
    @Log(title = "出租车订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTaxiOrder tTaxiOrder) {
        return toAjax(tTaxiOrderService.insertTTaxiOrder(tTaxiOrder));
    }

    /**
     * 修改出租车订单
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:edit')")
    @Log(title = "出租车订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTaxiOrder tTaxiOrder) {
        return toAjax(tTaxiOrderService.updateTTaxiOrder(tTaxiOrder));
    }

    /**
     * 删除出租车订单
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:remove')")
    @Log(title = "出租车订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tTaxiOrderService.deleteTTaxiOrderByIds(ids));
    }

//    @PostMapping("/addOrder")
//    public Object addOrder(@RequestBody TTaxiOrder tTaxiOrder) {
//        if (tTaxiOrderService.insertTTaxiOrder(tTaxiOrder) > 0) {
//            List< TRentalcarsMessage > passengerList = tTaxiOrder.getPassengerList();
//            for (TRentalcarsMessage tRentalcarsMessage : passengerList) {
//                tRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
//                tRentalcarsMessage.setOrderType(tTaxiOrder.getOrderType());
//                tRentalcarsMessage.setReserveMobile(tTaxiOrder.getReserveMobile());
//                tRentalcarsMessage.setReserveName(tTaxiOrder.getReserveName());
//                tRentalcarsMessageService.insertTRentalcarsMessage(tRentalcarsMessage);
//            }
//        }
//        Map< String, Object > result = new HashMap<>();
//        int statusCode = 200;
//        result.put("code", statusCode);
//        result.put("data", "");
//        result.put("message", "新增客户拼包车订单成功!");
//        return result;
//    }

//    /**
//     * 修改包车订单价格并记录明细
//     */
//    @PreAuthorize("@ss.hasPermi('order:taxiOrder:editPrice')")
//    @Log(title = "修改包车订单价格", businessType = BusinessType.DELETE)
//    @PutMapping
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(tTaxiOrderService.deleteTTaxiOrderByIds(ids));
//    }

}
