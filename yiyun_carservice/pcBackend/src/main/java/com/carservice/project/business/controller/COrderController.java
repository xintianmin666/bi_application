package com.carservice.project.business.controller;

import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.business.domain.COrder;
import com.carservice.project.business.domain.COrderGoods;
import com.carservice.project.business.service.COrderService;
import com.carservice.project.business.util.ResultTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/business/corder")
public class COrderController extends BaseController {
    @Autowired
    private COrderService cOrderService;

    /**
     * 获取订单列表
     * @param cOrder
     * @return
     */
    @PostMapping("/getOrderList")
    public AjaxResult getOrderList(@RequestBody COrder cOrder){
        try{
            ResultTab resultTab =  cOrderService.getOrderList(cOrder);
            return AjaxResult.success(resultTab);
        }catch(Exception e){
            logger.error("getOrderList()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 修改订单
     * @return
     */
    @PostMapping("/updateOrderGoods")
    public AjaxResult updateOrderGoods(@RequestBody COrderGoods cOrderGoods){
        try{
           cOrderService.updateOrderGoods(cOrderGoods);
           return AjaxResult.success("修改成功！");
        }catch(Exception e){
            logger.error("updateOrderGoods()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 商家获取电话接口   将状态由已下单改为已接单（保养,维护,救援）
     * @return
     */
    @PostMapping("/getPhone")
    public AjaxResult getPhone(@RequestBody COrderGoods cOrderGoods){
        try{
            cOrderService.getPhone(cOrderGoods);
            return AjaxResult.success("操作成功！");
        }catch(Exception e){
            logger.error("getPhone()报错!",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 修改订单预约时间（接单状态  （保养,维护,救援））
     * @param cOrder
     * @return
     */
    @PostMapping("/modifyBookTime")
    public AjaxResult modifyBookTime(@RequestBody COrder cOrder){
        try{
            cOrderService.modifyBookTime(cOrder);
            return AjaxResult.success("预约时间修改成功！");
        }catch(Exception e){
            logger.error("modifyBookTime()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     *上传商品信息，修改价格 状态由接单转为服务中（保养,维护,救援）
     * @param cOrderGoods
     * @return
     */
    @PostMapping("/modifyOrderPriceAndDescribe")
    public AjaxResult modifyOrderPriceAndDescribe(@RequestBody COrderGoods cOrderGoods){
        try{
            cOrderService.modifyOrderPriceAndDescribe(cOrderGoods);
            return AjaxResult.success("上传商户资料，修改价格成功！");
        }catch(Exception e){
            logger.error("modifyOrderPriceAnddDescribe()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 获取核验订单信息(洗车，检测)
     * @param cOrderGoods
     * @return
     */
    @PostMapping("/getCheckOrderGoods")
    public AjaxResult getCheckOrderGoods(@RequestBody COrderGoods cOrderGoods){
        try{
            List<COrderGoods> returnPojo = cOrderService.getCheckOrderGoods(cOrderGoods);
            return AjaxResult.success("获取核验订单成功",returnPojo);
        }catch(Exception e){
            logger.error("getCheckOrderGoods()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     *核验订单产品(洗车，检测) 状态由接单 变为 已完成  产品表售卖数量+1
     * @param cOrderGoods
     * @return
     */
    @PostMapping("/checkOrderGoods")
    public AjaxResult checkOrderGoods(@RequestBody COrderGoods cOrderGoods){
        try{
            cOrderService.checkOrderGoods(cOrderGoods);
            return AjaxResult.success("核验订单成功！");
        }catch(Exception e){
            logger.error("checkOrderGoods()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 编辑救援信息（救援） 状态由 接单 变为 服务中
     * @param cOrderGoods
     * @return
     */
    @PostMapping("/editRescueInfo")
    public AjaxResult editRescueInfo(@RequestBody COrderGoods cOrderGoods){
        try{
            cOrderService.editRescueInfo(cOrderGoods);
            return AjaxResult.success("编辑救援信息成功！");
        }catch(Exception e){
            logger.error("editRescueInfo()报错！",e);
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('business:corder:list')")
    @GetMapping("/list")
    public TableDataInfo list(COrder cOrder)
    {
        try{
            startPage();

            List<COrder> list = cOrderService.selectCOrderList(cOrder);
            return getDataTable(list);
        }catch(Exception e){
            logger.error("list()报错！",e);
            return null;
        }

    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('business:corder:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(COrder cOrder)
    {
        try{
            List<COrder> list = cOrderService.selectCOrderList(cOrder);
            ExcelUtil<COrder> util = new ExcelUtil<COrder>(COrder.class);
            return util.exportExcel(list, "订单表");
        }catch(Exception e){
            logger.error("export报错！",e);
            return null;
        }

    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:corder:query')")
    @GetMapping(value = "/{orderCode}")
    public AjaxResult getGoods(@PathVariable("orderCode") String orderCode)
    {
        try{
            return AjaxResult.success(cOrderService.selectCOrderGoodsByOrderCode(orderCode));
        }catch(Exception e){
            logger.error("getGoods()报错！",e);
            return AjaxResult.error("getGoods()报错！");
        }

    }


    /**
     * 接单  后台管理端
     */
    @PostMapping("/jieDan")
    public AjaxResult jieDan(COrderGoods cOrderGoods){
        try{
            cOrderService.getPhone(cOrderGoods);
            return AjaxResult.success("操作成功！");
        }catch(Exception e){
            logger.error("getPhone()报错!",e);
            return AjaxResult.error(e.toString());
        }
    }

}
