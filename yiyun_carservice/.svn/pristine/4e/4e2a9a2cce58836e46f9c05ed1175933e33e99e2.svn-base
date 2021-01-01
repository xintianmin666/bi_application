package com.carservice.project.oper.controller;

import java.util.List;

import com.carservice.project.oper.domain.TProduct;
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
import com.carservice.project.oper.domain.TProductPrice;
import com.carservice.project.oper.service.ITProductPriceService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 产品价格Controller
 * 
 * @author carservice
 * @date 2020-05-20
 */
@RestController
@RequestMapping("/oper/productPrice")
public class TProductPriceController extends BaseController
{
    @Autowired
    private ITProductPriceService tProductPriceService;

    /**
     * 查询产品价格列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TProductPrice tProductPrice)
    {
        startPage();
        List<TProductPrice> list = tProductPriceService.selectTProductPriceList(tProductPrice);
        return getDataTable(list);
    }

    /**
     * 查询产品价格列表
     */
    @GetMapping("/listByProductId")
    public AjaxResult listByProductId(TProductPrice tProductPrice)
    {
        List<TProductPrice> list = tProductPriceService.selectTProductPriceList(tProductPrice);
        return AjaxResult.success(list);
    }

    /**
     * 导出产品价格列表
     */
    @PreAuthorize("@ss.hasPermi('oper:productPrice:export')")
    @Log(title = "产品价格", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TProductPrice tProductPrice)
    {
        List<TProductPrice> list = tProductPriceService.selectTProductPriceList(tProductPrice);
        ExcelUtil<TProductPrice> util = new ExcelUtil<TProductPrice>(TProductPrice.class);
        return util.exportExcel(list, "productPrice");
    }

    /**
     * 获取产品价格详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:productPrice:query')")
    @GetMapping(value = "/{productPriceId}")
    public AjaxResult getInfo(@PathVariable("productPriceId") Long productPriceId)
    {
        return AjaxResult.success(tProductPriceService.selectTProductPriceById(productPriceId));
    }

    /**
     * 新增产品价格
     */
    @PreAuthorize("@ss.hasPermi('oper:productPrice:add')")
    @Log(title = "产品价格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProductPrice tProductPrice){
        return toAjax(tProductPriceService.insertTProductPrice(tProductPrice));
    }

    /**
     * 修改产品价格
     */
    @PreAuthorize("@ss.hasPermi('oper:productPrice:edit')")
    @Log(title = "产品价格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProductPrice tProductPrice)
    {
        return toAjax(tProductPriceService.updateTProductPrice(tProductPrice));
    }

    /**
     * 删除产品价格
     */
    @PreAuthorize("@ss.hasPermi('oper:productPrice:remove')")
    @Log(title = "产品价格", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productPriceIds}")
    public AjaxResult remove(@PathVariable Long[] productPriceIds)
    {
        return toAjax(tProductPriceService.deleteTProductPriceByIds(productPriceIds));
    }
}
