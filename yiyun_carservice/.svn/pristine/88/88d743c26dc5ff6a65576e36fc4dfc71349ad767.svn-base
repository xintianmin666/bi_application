package com.carservice.project.client.controller;


import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.project.shop.service.ICShopGoodsService;
import com.carservice.project.shop.service.ICShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 店铺产品Controller
 * 
 * @author carservice
 * @date 2020-12-12
 */
@RestController
@RequestMapping("/clinet/shopgoods")
public class ShopGoodsController extends BaseController
{
    @Autowired
    private ICShopService cShopService;
    @Autowired
    private ICShopGoodsService shopGoodsService;



    /**
     * 获取产品信息
     */
    @GetMapping("/getById")
    public AjaxResult getById(@RequestParam("id") Long id)
    {
        return AjaxResult.success(shopGoodsService.selectCShopGoodsById(id));
    }

    /**
     * 获取产品信息
     */
    @GetMapping("/getByShopId")
    public AjaxResult getByShopId(@RequestParam("shopId") Long shopId)
    {
        return AjaxResult.success(shopGoodsService.selectCShopGoodsByShopId(shopId));
    }

}
