package com.carservice.project.oper.mapper;

import java.util.List;
import java.util.Map;

import com.carservice.project.oper.domain.TProduct;
import com.carservice.project.oper.domain.TVehicleType;
import org.apache.ibatis.annotations.Param;

/**
 * 产品信息Mapper接口
 * 
 * @author carservice
 * @date 2020-05-18
 */
public interface TProductMapper 
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
     * 删除产品信息
     * 
     * @param productId 产品信息ID
     * @return 结果
     */
    public int deleteTProductById(Long productId);

    /**
     * 批量删除产品信息
     * 
     * @param productIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTProductByIds(Long[] productIds);

    List<TProduct> getProductListByStartAndEndCityId(@Param("startCityId") Long operCityId1, @Param("endCityId")Long operCityId2);

    List<TProduct> selectProductListByStartAndEndCity(@Param("startCity")String startCity, @Param("endCity")String endCity,@Param("lineType")String lineType);

    List<TVehicleType> getProductCarTypeByProductId(Long productId);

    List<TVehicleType> getBestBcheGoods(Map paraMap);

}
