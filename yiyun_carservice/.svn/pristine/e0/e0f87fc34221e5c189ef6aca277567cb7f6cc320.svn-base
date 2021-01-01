package com.carservice.project.customer.mapper;

import java.util.List;
import com.carservice.project.customer.domain.CPointsHistory;

/**
 * 积分变动明细Mapper接口
 * 
 * @author carservice
 * @date 2020-12-21
 */
public interface CPointsHistoryMapper 
{
    /**
     * 查询积分变动明细
     * 
     * @param id 积分变动明细ID
     * @return 积分变动明细
     */
    public CPointsHistory selectCPointsHistoryById(Long id);

    /**
     * 查询积分变动明细列表
     * 
     * @param cPointsHistory 积分变动明细
     * @return 积分变动明细集合
     */
    public List<CPointsHistory> selectCPointsHistoryList(CPointsHistory cPointsHistory);

    /**
     * 新增积分变动明细
     * 
     * @param cPointsHistory 积分变动明细
     * @return 结果
     */
    public int insertCPointsHistory(CPointsHistory cPointsHistory);

    /**
     * 修改积分变动明细
     * 
     * @param cPointsHistory 积分变动明细
     * @return 结果
     */
    public int updateCPointsHistory(CPointsHistory cPointsHistory);

    /**
     * 删除积分变动明细
     * 
     * @param id 积分变动明细ID
     * @return 结果
     */
    public int deleteCPointsHistoryById(Long id);

    /**
     * 批量删除积分变动明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCPointsHistoryByIds(Long[] ids);
}
