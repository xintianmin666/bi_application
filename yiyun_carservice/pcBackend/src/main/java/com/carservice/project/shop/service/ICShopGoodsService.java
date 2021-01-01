package com.carservice.project.shop.service;

import java.util.List;

import com.carservice.project.shop.domain.CShop;
import com.carservice.project.shop.domain.CShopGoods;

/**
 * 店铺产品Service接口
 * 
 * @author carservice
 * @date 2020-12-12
 */
public interface ICShopGoodsService 
{
    /**
     * 查询店铺产品
     * 
     * @param id 店铺产品ID
     * @return 店铺产品
     */
    public CShopGoods selectCShopGoodsById(Long id);

    /**
     * 查询店铺产品列表
     * 
     * @param cShopGoods 店铺产品
     * @return 店铺产品集合
     */
    public List<CShopGoods> selectCShopGoodsList(CShopGoods cShopGoods);

    /**
     * 新增店铺产品
     * 
     * @param cShopGoods 店铺产品
     * @return 结果
     */
    public int insertCShopGoods(CShopGoods cShopGoods);

    /**
     * 修改店铺产品
     * 
     * @param cShopGoods 店铺产品
     * @return 结果
     */
    public int updateCShopGoods(CShopGoods cShopGoods);

    /**
     * 批量删除店铺产品
     * 
     * @param ids 需要删除的店铺产品ID
     * @return 结果
     */
    public int deleteCShopGoodsByIds(Long[] ids);

    /**
     * 删除店铺产品信息
     * 
     * @param id 店铺产品ID
     * @return 结果
     */
    public int deleteCShopGoodsById(Long id);
    List<CShopGoods> selectCShopGoodsByShopId(Long id);
    List<CShopGoods> selectCShopGoodsByShopIds(List<CShop> list,String serviceType);
}
