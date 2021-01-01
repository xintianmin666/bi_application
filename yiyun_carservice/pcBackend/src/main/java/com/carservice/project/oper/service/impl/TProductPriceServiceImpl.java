package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.SecurityUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TProductPriceMapper;
import com.carservice.project.oper.domain.TProductPrice;
import com.carservice.project.oper.service.ITProductPriceService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品价格Service业务层处理
 * 
 * @author carservice
 * @date 2020-05-20
 */
@Service
public class TProductPriceServiceImpl implements ITProductPriceService 
{
    @Autowired
    private TProductPriceMapper tProductPriceMapper;

    /**
     * 查询产品价格
     * 
     * @param productPriceId 产品价格ID
     * @return 产品价格
     */
    @Override
    public TProductPrice selectTProductPriceById(Long productPriceId)
    {
        return tProductPriceMapper.selectTProductPriceById(productPriceId);
    }

    /**
     * 查询产品价格列表
     * 
     * @param tProductPrice 产品价格
     * @return 产品价格
     */
    @Override
    public List<TProductPrice> selectTProductPriceList(TProductPrice tProductPrice)
    {
        return tProductPriceMapper.selectTProductPriceList(tProductPrice);
    }

    /**
     * 新增产品价格
     * 
     * @param tProductPrice 产品价格
     * @return 结果
     */
    @Override
    public int insertTProductPrice(TProductPrice tProductPrice) {
        Long productId = tProductPrice.getProductId();
        tProductPriceMapper.deleteTProductPriceByProductId(productId);
        List<TProductPrice> priceList = tProductPrice.getProductPriceList();
        for (TProductPrice productPrice : priceList) {
            productPrice.setCreateTime(DateUtils.getNowDate());
            productPrice.setCreateId(SecurityUtils.getUserId());
            productPrice.setProductId(productId);
            tProductPriceMapper.insertTProductPrice(productPrice);
        }
        return 1;
    }

    /**
     * 修改产品价格
     * 
     * @param tProductPrice 产品价格
     * @return 结果
     */
    @Override
    public int updateTProductPrice(TProductPrice tProductPrice)
    {
        return tProductPriceMapper.updateTProductPrice(tProductPrice);
    }

    /**
     * 批量删除产品价格
     * 
     * @param productPriceIds 需要删除的产品价格ID
     * @return 结果
     */
    @Override
    public int deleteTProductPriceByIds(Long[] productPriceIds)
    {
        return tProductPriceMapper.deleteTProductPriceByIds(productPriceIds);
    }

    /**
     * 删除产品价格信息
     * 
     * @param productPriceId 产品价格ID
     * @return 结果
     */
    @Override
    public int deleteTProductPriceById(Long productPriceId)
    {
        return tProductPriceMapper.deleteTProductPriceById(productPriceId);
    }
}
