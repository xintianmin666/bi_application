package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TProductPrice;

/**
 * 产品价格Service接口
 * 
 * @author carservice
 * @date 2020-05-20
 */
public interface ITProductPriceService 
{
    /**
     * 查询产品价格
     * 
     * @param productPriceId 产品价格ID
     * @return 产品价格
     */
    public TProductPrice selectTProductPriceById(Long productPriceId);

    /**
     * 查询产品价格列表
     * 
     * @param tProductPrice 产品价格
     * @return 产品价格集合
     */
    public List<TProductPrice> selectTProductPriceList(TProductPrice tProductPrice);

    /**
     * 新增产品价格
     * 
     * @param tProductPrice 产品价格
     * @return 结果
     */
    public int insertTProductPrice(TProductPrice tProductPrice);

    /**
     * 修改产品价格
     * 
     * @param tProductPrice 产品价格
     * @return 结果
     */
    public int updateTProductPrice(TProductPrice tProductPrice);

    /**
     * 批量删除产品价格
     * 
     * @param productPriceIds 需要删除的产品价格ID
     * @return 结果
     */
    public int deleteTProductPriceByIds(Long[] productPriceIds);

    /**
     * 删除产品价格信息
     * 
     * @param productPriceId 产品价格ID
     * @return 结果
     */
    public int deleteTProductPriceById(Long productPriceId);
}
