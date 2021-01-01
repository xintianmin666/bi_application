package com.carservice.project.client.controller;

import com.carservice.common.utils.ServletUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.shop.domain.CShop;
import com.carservice.project.shop.domain.CShopGoods;
import com.carservice.project.shop.service.ICShopGoodsService;
import com.carservice.project.shop.service.ICShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 店铺商户Controller
 * 
 * @author carservice
 * @date 2020-12-12
 */
@RestController
@RequestMapping("/clinet/shop")
public class ShopController extends BaseController
{
    @Autowired
    private ICShopService cShopService;
    @Autowired
    private ICShopGoodsService shopGoodsService;

    /**
     * 查询店铺商户列表
     * 1默认排序,2评分排序，3距离排序（距离当前多少km）
     */
    @GetMapping("/list")
    public TableDataInfo list(CShop cShop)
    {
        startPage();
        List<CShop> list = null;
        if (StringUtils.isNotEmpty(cShop.getLonLat())){//按距离排序
            cShop.setLon(cShop.getLonLat().split(",")[0]);
            cShop.setLat(cShop.getLonLat().split(",")[1]);
        }
        list = cShopService.selectShopListOrderByJuli(cShop);
        if (list.size()==0){
            return getDataTable(list);
        }
        List<CShopGoods> shopGoodsList = shopGoodsService.selectCShopGoodsByShopIds(list,cShop.getServiceType());
        Map<Long,List<CShopGoods>> map = shopGoodsList.stream().collect(Collectors.groupingBy(CShopGoods::getShopId));
        for (CShop shop : list) {
            shop.setShopGoodsList(map.get(shop.getId()));
        }
        TableDataInfo tableDataInfo = getDataTable(list);

        int pageNum = ServletUtils.getParameterToInt("pageNum");
        int pageSize = ServletUtils.getParameterToInt("pageSize");
        if ((pageNum-1)*pageSize>tableDataInfo.getTotal()){
            list =new ArrayList<>();
        }
        return getDataTable(list);
    }

    /**
     * 获取店铺商户详细信息
     */
    @GetMapping("/getById")
    public AjaxResult getInfo(@RequestParam("id") Long id)
    {
        return AjaxResult.success(cShopService.selectCShopById(id));
    }


}
