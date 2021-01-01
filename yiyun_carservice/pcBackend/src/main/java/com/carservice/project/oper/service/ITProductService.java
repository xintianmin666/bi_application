package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TProduct;

/**
 * 产品信息Service接口
 * 
 * @author carservice
 * @date 2020-05-18
 */
public interface ITProductService 
{
    /**
     * 查询产品信息
     * 
     * @param productId 产品信息ID
     * @return 产品信息
     */
    public TProduct selectTProductById(Long productId);

    /**
     * 查询产品信息列表
     * 
     * @param tProduct 产品信息
     * @return 产品信息集合
     */
    public List<TProduct> selectTProductList(TProduct tProduct);

    /**
     * 新增产品信息
     * 
     * @param tProduct 产品信息
     * @return 结果
     */
    public int insertTProduct(TProduct tProduct);

    /**
     * 修改产品信息
     * 
     * @param tProduct 产品信息
     * @return 结果
     */
    public int updateTProduct(TProduct tProduct);

    /**
     * 批量删除产品信息
     * 
     * @param productIds 需要删除的产品信息ID
     * @return 结果
     */
    public int deleteTProductByIds(Long[] productIds);

    /**
     * 删除产品信息信息
     * 
     * @param productId 产品信息ID
     * @return 结果
     */
    public int deleteTProductById(Long productId);
}
