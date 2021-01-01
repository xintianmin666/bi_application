package com.carservice.project.shop.controller;

import java.util.List;

import com.carservice.common.utils.SecurityUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.project.shop.domain.CShop;
import com.carservice.project.shop.service.ICShopService;
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
import com.carservice.project.shop.domain.CShopGoods;
import com.carservice.project.shop.service.ICShopGoodsService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 店铺产品Controller
 * 
 * @author carservice
 * @date 2020-12-12
 */
@RestController
@RequestMapping("/shop/shopGoods")
public class CShopGoodsController extends BaseController
{
    @Autowired
    private ICShopGoodsService cShopGoodsService;
    @Autowired
    private ICShopService cShopService;

    /**
     * 查询店铺产品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shopGoods:list')")
    @GetMapping("/list")
    public TableDataInfo list(CShopGoods cShopGoods)
    {
        startPage();
//        if (SecurityUtils.getBusinessId()!=null){
//            cShopGoods.setShopId(SecurityUtils.getBusinessId());
//        }
        List<CShopGoods> list = cShopGoodsService.selectCShopGoodsList(cShopGoods);
        return getDataTable(list);
    }

    /**
     * 导出店铺产品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shopGoods:export')")
    @Log(title = "店铺产品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CShopGoods cShopGoods)
    {
        List<CShopGoods> list = cShopGoodsService.selectCShopGoodsList(cShopGoods);
        ExcelUtil<CShopGoods> util = new ExcelUtil<CShopGoods>(CShopGoods.class);
        return util.exportExcel(list, "shopGoods");
    }

    /**
     * 获取店铺产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:shopGoods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cShopGoodsService.selectCShopGoodsById(id));
    }

    /**
     * 新增店铺产品
     */
    @PreAuthorize("@ss.hasPermi('shop:shopGoods:add')")
    @Log(title = "店铺产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CShopGoods cShopGoods)
    {
        cShopGoods.setCreateBy(SecurityUtils.getUsername());
        return toAjax(cShopGoodsService.insertCShopGoods(cShopGoods));
    }

    /**
     * 修改店铺产品
     */
    @PreAuthorize("@ss.hasPermi('shop:shopGoods:edit')")
    @Log(title = "店铺产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CShopGoods cShopGoods)
    {
        cShopGoods.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(cShopGoodsService.updateCShopGoods(cShopGoods));
    }

    /**
     * 删除店铺产品
     */
    @PreAuthorize("@ss.hasPermi('shop:shopGoods:remove')")
    @Log(title = "店铺产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cShopGoodsService.deleteCShopGoodsByIds(ids));
    }
}
