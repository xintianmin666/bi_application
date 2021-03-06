package com.carservice.project.order.mapper;

import com.carservice.project.order.domain.TOrderPrice;

import java.util.List;

/**
 * 包拼车订单价格明细Mapper接口
 * @author carservice
 * @date 2020-08-14
 */
public interface TOrderPriceMapper {
    /**
     * 查询包拼车订单价格明细
     * @param id 包拼车订单价格明细ID
     * @return 包拼车订单价格明细
     */
    public TOrderPrice selectTOrderPriceById(Long id);

    /**
     * 查询包拼车订单价格明细列表
     * @param tOrderPrice 包拼车订单价格明细
     * @return 包拼车订单价格明细集合
     */
    public List< TOrderPrice > selectTOrderPriceList(TOrderPrice tOrderPrice);

    /**
     * 新增包拼车订单价格明细
     * @param tOrderPrice 包拼车订单价格明细
     * @return 结果
     */
    public int insertTOrderPrice(TOrderPrice tOrderPrice);

    /**
     * 修改包拼车订单价格明细
     * @param tOrderPrice 包拼车订单价格明细
     * @return 结果
     */
    public int updateTOrderPrice(TOrderPrice tOrderPrice);

    /**
     * 删除包拼车订单价格明细
     * @param id 包拼车订单价格明细ID
     * @return 结果
     */
    public int deleteTOrderPriceById(Long id);

    /**
     * 批量删除包拼车订单价格明细
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOrderPriceByIds(Long[] ids);

    void deleteOrderPriceByOrderCode(String orderCode);
}
