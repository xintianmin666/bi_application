package com.carservice.project.shop.mapper;

import java.util.List;

import com.carservice.project.business.domain.COrderGoods;
import com.carservice.project.shop.domain.CShop;
import com.carservice.project.shop.domain.CShopGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 店铺产品Mapper接口
 * 
 * @author carservice
 * @date 2020-12-12
 */
@Repository
@Mapper
public interface CShopGoodsMapper 
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
     * 删除店铺产品
     * 
     * @param id 店铺产品ID
     * @return 结果
     */
    public int deleteCShopGoodsById(Long id);

    /**
     * 批量删除店铺产品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCShopGoodsByIds(Long[] ids);

    List<CShopGoods> selectCShopGoodsByShopIds(@Param("list") List<CShop> list,@Param("goodsType")String serviceType);

    List<CShopGoods> selectCShopGoodsByShopId(@Param("shopId") Long shopId);

    void addSaleNum(COrderGoods cOrderGoods);
}
