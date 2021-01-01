package com.carservice.project.oper.mapper;

import java.util.List;
import com.carservice.project.oper.domain.TProductSite;

/**
 * 产品站点Mapper接口
 * 
 * @author carservice
 * @date 2020-08-04
 */
public interface TProductSiteMapper 
{
    /**
     * 查询产品站点
     * 
     * @param productSiteId 产品站点ID
     * @return 产品站点
     */
    public TProductSite selectTProductSiteById(Long productSiteId);

    /**
     * 查询产品站点列表
     * 
     * @param tProductSite 产品站点
     * @return 产品站点集合
     */
    public List<TProductSite> selectTProductSiteList(TProductSite tProductSite);

    /**
     * 新增产品站点
     * 
     * @param tProductSite 产品站点
     * @return 结果
     */
    public int insertTProductSite(TProductSite tProductSite);

    /**
     * 修改产品站点
     * 
     * @param tProductSite 产品站点
     * @return 结果
     */
    public int updateTProductSite(TProductSite tProductSite);

    /**
     * 删除产品站点
     * 
     * @param productSiteId 产品站点ID
     * @return 结果
     */
    public int deleteTProductSiteById(Long productSiteId);

    /**
     * 批量删除产品站点
     * 
     * @param productSiteIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTProductSiteByIds(Long[] productSiteIds);


    void deleteTProductSiteByProductId(Long productId);
}
