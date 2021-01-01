package com.carservice.project.shop.mapper;

import java.util.List;
import com.carservice.project.shop.domain.CEvaluate;

/**
 * 用户评价Mapper接口
 * 
 * @author carservice
 * @date 2020-12-17
 */
public interface CEvaluateMapper 
{
    /**
     * 查询用户评价
     * 
     * @param id 用户评价ID
     * @return 用户评价
     */
    public CEvaluate selectCEvaluateById(Long id);

    /**
     * 查询用户评价列表
     * 
     * @param cEvaluate 用户评价
     * @return 用户评价集合
     */
    public List<CEvaluate> selectCEvaluateList(CEvaluate cEvaluate);

    /**
     * 新增用户评价
     * 
     * @param cEvaluate 用户评价
     * @return 结果
     */
    public int insertCEvaluate(CEvaluate cEvaluate);

    /**
     * 修改用户评价
     * 
     * @param cEvaluate 用户评价
     * @return 结果
     */
    public int updateCEvaluate(CEvaluate cEvaluate);

    /**
     * 删除用户评价
     * 
     * @param id 用户评价ID
     * @return 结果
     */
    public int deleteCEvaluateById(Long id);

    /**
     * 批量删除用户评价
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCEvaluateByIds(Long[] ids);

    List<CEvaluate> getByShopId(Long shopId);
}
