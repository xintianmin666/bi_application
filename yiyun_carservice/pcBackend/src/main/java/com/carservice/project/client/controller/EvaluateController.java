package com.carservice.project.client.controller;


import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.project.shop.service.ICEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺产品Controller
 * 
 * @author carservice
 * @date 2020-12-12
 */
@RestController
@RequestMapping("/clinet/evaluate")
public class EvaluateController extends BaseController
{
    @Autowired
    private ICEvaluateService evaluateService;

    /**
     * 获取商家评价
     */
    @GetMapping("/getByShopId")
    public AjaxResult getByShopId(@RequestParam("shopId") Long shopId)
    {
        return AjaxResult.success(evaluateService.getByShopId(shopId));
    }

}
