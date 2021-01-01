package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TProductMapper;
import com.carservice.project.oper.domain.TProduct;
import com.carservice.project.oper.service.ITProductService;

/**
 * 产品信息Service业务层处理
 * 
 * @author carservice
 * @date 2020-05-18
 */
@Service
public class TProductServiceImpl implements ITProductService 
{
    @Autowired
    private TProductMapper tProductMapper;

    /**
     * 查询产品信息
     * 
     * @param productId 产品信息ID
     * @return 产品信息
     */
    @Override
    public TProduct selectTProductById(Long productId)
    {
        return tProductMapper.selectTProductById(productId);
    }

    /**
     * 查询产品信息列表
     * 
     * @param tProduct 产品信息
     * @return 产品信息
     */
    @Override
    @DataScope(deptAlias="p")
    public List<TProduct> selectTProductList(TProduct tProduct)
    {
        return tProductMapper.selectTProductList(tProduct);
    }

    /**
     * 新增产品信息
     * 
     * @param tProduct 产品信息
     * @return 结果
     */
    @Override
    public int insertTProduct(TProduct tProduct)
    {
        tProduct.setCreateTime(DateUtils.getNowDate());
        return tProductMapper.insertTProduct(tProduct);
    }

    /**
     * 修改产品信息
     * 
     * @param tProduct 产品信息
     * @return 结果
     */
    @Override
    public int updateTProduct(TProduct tProduct)
    {
        return tProductMapper.updateTProduct(tProduct);
    }

    /**
     * 批量删除产品信息
     * 
     * @param productIds 需要删除的产品信息ID
     * @return 结果
     */
    @Override
    public int deleteTProductByIds(Long[] productIds)
    {
        return tProductMapper.deleteTProductByIds(productIds);
    }

    /**
     * 删除产品信息信息
     * 
     * @param productId 产品信息ID
     * @return 结果
     */
    @Override
    public int deleteTProductById(Long productId)
    {
        return tProductMapper.deleteTProductById(productId);
    }
}
