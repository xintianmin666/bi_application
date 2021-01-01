package com.carservice.project.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.order.domain.TOrderPrice;
import com.carservice.project.order.service.ITOrderPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包拼车订单价格明细Controller
 * @author carservice
 * @date 2020-08-14
 */
@RestController
@RequestMapping("/order/orderPrice")
public class TOrderPriceController extends BaseController {
    @Autowired
    private ITOrderPriceService tOrderPriceService;

    @Autowired
    private HttpUtils httpUtils;

    /**
     * 查询包拼车订单价格明细列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderPrice:list')")
    @GetMapping("/list")
    public TableDataInfo list(TOrderPrice tOrderPrice) {
        startPage();
        List< TOrderPrice > list = tOrderPriceService.selectTOrderPriceList(tOrderPrice);
        return getDataTable(list);
    }

    @GetMapping("/listByOrderCode")
    public AjaxResult listByOrderCode(TOrderPrice tOrderPrice) {
        List< TOrderPrice > list = tOrderPriceService.selectTOrderPriceList(tOrderPrice);
        return AjaxResult.success(list);
    }

    /**
     * 导出包拼车订单价格明细列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderPrice:export')")
    @Log(title = "包拼车订单价格明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TOrderPrice tOrderPrice) {
        List< TOrderPrice > list = tOrderPriceService.selectTOrderPriceList(tOrderPrice);
        ExcelUtil< TOrderPrice > util = new ExcelUtil< TOrderPrice >(TOrderPrice.class);
        return util.exportExcel(list, "orderPrice");
    }

    /**
     * 获取包拼车订单价格明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:orderPrice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tOrderPriceService.selectTOrderPriceById(id));
    }

    /**
     * 新增包拼车订单价格明细
     */
    @PreAuthorize("@ss.hasPermi('order:orderPrice:add')")
    @Log(title = "包拼车订单价格明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TOrderPrice tOrderPrice) {
        Map paraMap = new HashMap<>();
        paraMap = tOrderPriceService.insertTOrderPrice(tOrderPrice);
        JSONObject paramData = new JSONObject();
        paramData.put("orderCode", paraMap.get("orderCode"));
        paramData.put("orderAmount", paraMap.get("orderAmount"));
        // 解析json
        JSONObject smsjsonObject = null;
        try {
            smsjsonObject =
                    (JSONObject)
                            JSONObject.parse(
                                    httpUtils.httpURLConnectionPOST(
                                            "http://test.dtjklive.com/yttrip/carOrder/updateCharterOrderAmount", paramData));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }
//        return toAjax(tOrderPriceService.insertTOrderPrice(tOrderPrice));

    /**
     * 修改包拼车订单价格明细
     */
    @PreAuthorize("@ss.hasPermi('order:orderPrice:edit')")
    @Log(title = "包拼车订单价格明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TOrderPrice tOrderPrice) {

        return toAjax(tOrderPriceService.updateTOrderPrice(tOrderPrice));
    }

    /**
     * 删除包拼车订单价格明细
     */
    @PreAuthorize("@ss.hasPermi('order:orderPrice:remove')")
    @Log(title = "包拼车订单价格明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tOrderPriceService.deleteTOrderPriceByIds(ids));
    }
}
