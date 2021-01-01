package com.carservice.project.oper.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.carservice.project.oper.domain.TProduct;
import com.carservice.project.oper.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TProductSiteMapper;
import com.carservice.project.oper.domain.TProductSite;
import com.carservice.project.oper.service.ITProductSiteService;

/**
 * 产品站点Service业务层处理
 * 
 * @author carservice
 * @date 2020-08-04
 */
@Service
public class TProductSiteServiceImpl implements ITProductSiteService 
{
    @Autowired
    private TProductSiteMapper tProductSiteMapper;
    @Autowired
    private TProductMapper productMapper;

    /**
     * 查询产品站点
     * 
     * @param productSiteId 产品站点ID
     * @return 产品站点
     */
    @Override
    public TProductSite selectTProductSiteById(Long productSiteId)
    {
        return tProductSiteMapper.selectTProductSiteById(productSiteId);
    }

    /**
     * 查询产品站点列表
     * 
     * @param tProductSite 产品站点
     * @return 产品站点
     */
    @Override
    public List<TProductSite> selectTProductSiteList(TProductSite tProductSite)
    {
        return tProductSiteMapper.selectTProductSiteList(tProductSite);
    }

    /**
     * 新增产品站点
     * 
     * @param tProductSite 产品站点
     * @return 结果
     */
    @Override
    public int insertTProductSite(TProductSite tProductSite)
    {
        Long productId = tProductSite.gettProductId();
        tProductSiteMapper.deleteTProductSiteByProductId(productId);
        List<TProductSite> productSites = tProductSite.getProductSiteList();
        for (TProductSite productSite : productSites) {
            productSite.settProductId(productId);
            tProductSiteMapper.insertTProductSite(productSite);
        }
        /*Collections.sort(productSites, new Comparator<TProductSite>() {
            @Override
            public int compare(TProductSite o1, TProductSite o2) {
                return o2.getOrderNum().intValue()-o1.getOrderNum().intValue();
            }
        });*/
        List<TProductSite> productSiteList = tProductSiteMapper.selectTProductSiteList(tProductSite);

        TProduct tProduct = new TProduct();
        tProduct.setProductId(productId);
        tProduct.setStartSiteId(productSiteList.get(0).getSiteInfo().getSiteName());
        tProduct.setEndSiteId(productSiteList.get(productSiteList.size()-1).getSiteInfo().getSiteName());
        tProduct.setStartCity(productSiteList.get(0).getSiteInfo().getCityName());
        tProduct.setEndCity(productSiteList.get(productSiteList.size()-1).getSiteInfo().getCityName());
        productMapper.updateTProduct(tProduct);
        return 1;
    }

    /**
     * 修改产品站点
     * 
     * @param tProductSite 产品站点
     * @return 结果
     */
    @Override
    public int updateTProductSite(TProductSite tProductSite)
    {
        return tProductSiteMapper.updateTProductSite(tProductSite);
    }

    /**
     * 批量删除产品站点
     * 
     * @param productSiteIds 需要删除的产品站点ID
     * @return 结果
     */
    @Override
    public int deleteTProductSiteByIds(Long[] productSiteIds)
    {
        return tProductSiteMapper.deleteTProductSiteByIds(productSiteIds);
    }

    /**
     * 删除产品站点信息
     * 
     * @param productSiteId 产品站点ID
     * @return 结果
     */
    @Override
    public int deleteTProductSiteById(Long productSiteId)
    {
        return tProductSiteMapper.deleteTProductSiteById(productSiteId);
    }

}
