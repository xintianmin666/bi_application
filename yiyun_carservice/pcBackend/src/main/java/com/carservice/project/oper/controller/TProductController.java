package com.carservice.project.oper.controller;

import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.oper.domain.TProduct;
import com.carservice.project.oper.service.ITProductService;
import com.carservice.project.oper.service.ITSiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品信息Controller
 * @author carservice
 * @date 2020-05-18
 */
@RestController
@RequestMapping("/oper/product")
public class TProductController extends BaseController {
    @Autowired
    private ITProductService tProductService;

    @Autowired
    private ITSiteInfoService tSiteInfoService;

    /**
     * 查询产品信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProduct tProduct) {
        startPage();
        List< TProduct > list = tProductService.selectTProductList(tProduct);
        return getDataTable(list);
    }

    /**
     * 导出产品信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:product:export')")
    @Log(title = "产品信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TProduct tProduct) {
        List< TProduct > list = tProductService.selectTProductList(tProduct);
        ExcelUtil< TProduct > util = new ExcelUtil< TProduct >(TProduct.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 获取产品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:product:query')")
    @GetMapping("/getInfo")
    public AjaxResult getInfo(@RequestParam("productId") Long productId) {

        return AjaxResult.success(tProductService.selectTProductById(productId));
    }

    /**
     * 新增产品信息
     */
    @PreAuthorize("@ss.hasPermi('oper:product:add')")
    @Log(title = "产品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProduct tProduct) {
        /*MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        List<MultipartFile> files1 = ((MultipartHttpServletRequest) request)
                .getFiles("file1");*/
        return toAjax(tProductService.insertTProduct(tProduct));
    }

    /**
     * 修改产品信息
     */
    @PreAuthorize("@ss.hasPermi('oper:product:edit')")
    @Log(title = "产品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProduct tProduct) {
        return toAjax(tProductService.updateTProduct(tProduct));
    }

    /**
     * 删除产品信息
     */
    @PreAuthorize("@ss.hasPermi('oper:product:remove')")
    @Log(title = "产品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds) {
        return toAjax(tProductService.deleteTProductByIds(productIds));
    }

//    @GetMapping("/countPrice")
//    public Object countPrice(@RequestBody Map paramMap) {
//        Map< String, Object > result = new HashMap<>();
//        int statusCode = 500;
//        TProduct tproduct = tProductService.selectTProductById(Long.valueOf(paramMap.get("productId").toString()));
//        TSiteInfo siteInfo = tSiteInfoService.selectTSiteInfoById(Long.valueOf(paramMap.get("siteId").toString()));
//        GlobalCoordinates site =
//                new GlobalCoordinates(
//                        Double.valueOf(siteInfo.getLat()), Double.valueOf(siteInfo.getLng()));
//        GlobalCoordinates passenger =
//                new GlobalCoordinates(Double.valueOf(paramMap.get("lat").toString()), Double.valueOf(paramMap.get("lng").toString()));
//        BigDecimal meter1 = new BigDecimal(getDistanceMeter(site, passenger, Ellipsoid.Sphere));
//        BigDecimal price = new BigDecimal(tproduct.getStartingPrice());
//        if (meter1.compareTo(new BigDecimal(tproduct.getStartingDistance())) > 0) {
//            BigDecimal cj = meter1.subtract(new BigDecimal(tproduct.getStartingDistance())).multiply(new BigDecimal(tproduct.getOutOfPrice())).setScale(2, BigDecimal.ROUND_HALF_UP);
//            price = price.add(cj);
//        }
//        statusCode = 200;
//        result.put("code", statusCode);
//        result.put("data", price);
//        result.put("message", "获取客户拼车上车点价格成功!");
//        return result;
//    }
//
//    public static double getDistanceMeter(
//            GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
//
//        // 创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
//        GeodeticCurve geoCurve =
//                new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
//
//        return geoCurve.getEllipsoidalDistance();
//    }
}
