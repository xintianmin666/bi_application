package com.carservice.project.miniProgram.service;


import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.project.oper.domain.*;
import com.carservice.project.oper.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 定制线路业务层处理
 * 
 * @author carservice
 * @date 2020-04-30
 */
@Service
public class CustomLineServiceImpl
{
    @Autowired
    private TProductMapper tProductMapper;
    @Autowired
    private TProductSiteMapper productSiteMapper;
    @Autowired
    private TProductPriceMapper tProductPriceMapper;
    @Autowired
    private TVehicleTypeMapper tVehicleTypeMapper;
    @Autowired
    private TOperCityInfoMapper tOperCityInfoMapper;
    @Autowired
    private TSiteInfoMapper tSiteInfoMapper;
    @Autowired
    private TVehicleTaskStatusMapper vehicleTaskStatusMapper;

    /**
     * 获取线路信息（开始站点城市和目的地站点城市相符的线路）
     *      * @param operCityId1  开始城市id
     *      * @param operCityId2  目的城市id
     */
    public List<TProduct> getProductList(Long operCityId1, Long operCityId2) {
        List<TProduct> productList = tProductMapper.getProductListByStartAndEndCityId(operCityId1,operCityId2);
        return productList;
    }
    /**
     * 通过产品id获取产品
     * @param productId
     * @return
     */
    public TProduct getProductById(Long productId){
        TProduct product = tProductMapper.selectTProductById(productId);
        return product;
    }

    /**
     * 通过产品id获取可用车类型
     * @param productId
     * @param useTime 用车时间
     * @param outOfDistance  超多范围公里数
     * @return
     */
    public List<TProductPrice> getCarTypeByProductId(Long productId,String useTime,int outOfDistance){
        TProduct product = getProductById(productId);
        //夜间服务费
        Double nightServiceCharge = product.getNightServiceCharge();
        Boolean isNightService = false;
        //超出每公里费
        Double outOfPrice = product.getOutOfPrice();
        String nightServiceTime = product.getNightServiceTime();
        outOfPrice = outOfPrice*outOfDistance;//超出费用
        List<TProductPrice> productPriceList = product.getProductPriceList();
        return  productPriceList;
    }

    /**
     * 通过城市id获取站点
     * @param operCityId 城市id
     * @return
     */
    public List<TSiteInfo> getSiteByCityId(Long operCityId) {
        TSiteInfo tSiteInfo = new TSiteInfo();
        tSiteInfo.setCityId(operCityId);
        List<TSiteInfo> siteInfoList = tSiteInfoMapper.selectTSiteInfoList(tSiteInfo);
        return siteInfoList;
    }

    /**
     * 获取可用城市列表
     * @return
     */
    public List<TOperCityInfo> getCityList() {
        TOperCityInfo tOperCityInfo = new TOperCityInfo();
        tOperCityInfo.setIsvaliable("1");//展示已生效的城市
        return tOperCityInfoMapper.selectTOperCityInfoList(tOperCityInfo);
    }

    /**
     * 通过出发地城市id 获取目的地城市列表
     * @param operCityId  出发地城市id
     * @return
     */
    public List<TOperCityInfo> getEndCityList(Long operCityId) {
        return null;
    }


    /**
     * 查询线路产品列表
     * @param startCity
     * @param endCity
     * @return
     */
    public List<TProduct> getProductList(String startCity, String endCity,String lineType) {
        List<TProduct> productList = tProductMapper.selectProductListByStartAndEndCity(startCity,endCity,lineType);
        return  productList;
    }

    /**
     * 获取产品站点信息
     * @param productId
     * @return
     */
    public List<TProductSite> getProductSiteList(Long productId) {
        TProductSite tProductSite = new TProductSite();
        tProductSite.settProductId(productId);
        return productSiteMapper.selectTProductSiteList(tProductSite);
    }

    public List<TVehicleType> getProductCarType(Long productId) {
        List<TVehicleType>  vehicleTypeList = tProductMapper.getProductCarTypeByProductId(productId);
        return vehicleTypeList;

    }
    public List<TVehicleType> getBestBcheGoods(Long productId,int passengerNum) {
        Map map = new HashMap();
        map.put("productId",productId);
        map.put("passengerNum",passengerNum);
        List<TVehicleType>  vehicleTypeList = tProductMapper.getBestBcheGoods(map);
        return vehicleTypeList;

    }



    public TProductPrice getProductPrice(Long productId, String vehicleTypeId) {
        TProductPrice tProductPrice = new TProductPrice();
        tProductPrice.setProductId(productId);
        tProductPrice.setCarType(vehicleTypeId);
        TProductPrice productPrice = tProductPriceMapper.selectTProductPriceList(tProductPrice).get(0);
        return productPrice;
    }

    /**
     * 获取可用的拼车班次
     * @param productId
     * @param time
     * @return
     */
    public List<TVehicleTaskStatus> getPCshift(Long productId, String time) {
        return  vehicleTaskStatusMapper.getPCshift(productId,time);
    }
}