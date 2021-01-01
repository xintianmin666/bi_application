package com.carservice.project.shop.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import com.carservice.project.shop.domain.CShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.shop.mapper.CShopGoodsMapper;
import com.carservice.project.shop.domain.CShopGoods;
import com.carservice.project.shop.service.ICShopGoodsService;

/**
 * 店铺产品Service业务层处理
 * 
 * @author carservice
 * @date 2020-12-12
 */
@Service
public class CShopGoodsServiceImpl implements ICShopGoodsService 
{
    @Autowired
    private CShopGoodsMapper cShopGoodsMapper;

    /**
     * 查询店铺产品
     * 
     * @param id 店铺产品ID
     * @return 店铺产品
     */
    @Override
    public CShopGoods selectCShopGoodsById(Long id)
    {
        return cShopGoodsMapper.selectCShopGoodsById(id);
    }

    /**
     * 查询店铺产品列表
     * 
     * @param cShopGoods 店铺产品
     * @return 店铺产品
     */
    @Override
    @DataScope(deptAlias = "s")
    public List<CShopGoods> selectCShopGoodsList(CShopGoods cShopGoods)
    {
        return cShopGoodsMapper.selectCShopGoodsList(cShopGoods);
    }

    /**
     * 新增店铺产品
     * 
     * @param cShopGoods 店铺产品
     * @return 结果
     */
    @Override
    public int insertCShopGoods(CShopGoods cShopGoods)
    {
        cShopGoods.setCreateTime(DateUtils.getNowDate());
        return cShopGoodsMapper.insertCShopGoods(cShopGoods);
    }

    /**
     * 修改店铺产品
     * 
     * @param cShopGoods 店铺产品
     * @return 结果
     */
    @Override
    public int updateCShopGoods(CShopGoods cShopGoods)
    {
        cShopGoods.setUpdateTime(DateUtils.getNowDate());
        return cShopGoodsMapper.updateCShopGoods(cShopGoods);
    }

    /**
     * 批量删除店铺产品
     * 
     * @param ids 需要删除的店铺产品ID
     * @return 结果
     */
    @Override
    public int deleteCShopGoodsByIds(Long[] ids)
    {
        return cShopGoodsMapper.deleteCShopGoodsByIds(ids);
    }

    /**
     * 删除店铺产品信息
     * 
     * @param id 店铺产品ID
     * @return 结果
     */
    @Override
    public int deleteCShopGoodsById(Long id)
    {
        return cShopGoodsMapper.deleteCShopGoodsById(id);
    }

    /**
     * 通过店铺id获取产品列表
     * @param id
     * @return
     */
    @Override
    public List<CShopGoods> selectCShopGoodsByShopId(Long id) {
        return cShopGoodsMapper.selectCShopGoodsByShopId(id);
    }

    /**
     * 通过店铺ids获取产品列表
     * @param list
     * @return
     */
    @Override
    public List<CShopGoods> selectCShopGoodsByShopIds(List<CShop> list,String serviceType) {
        return cShopGoodsMapper.selectCShopGoodsByShopIds(list,serviceType);
    }
}
