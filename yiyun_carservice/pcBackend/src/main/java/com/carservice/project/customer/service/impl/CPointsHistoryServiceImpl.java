package com.carservice.project.customer.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.customer.mapper.CPointsHistoryMapper;
import com.carservice.project.customer.domain.CPointsHistory;
import com.carservice.project.customer.service.ICPointsHistoryService;

/**
 * 积分变动明细Service业务层处理
 * 
 * @author carservice
 * @date 2020-12-21
 */
@Service
public class CPointsHistoryServiceImpl implements ICPointsHistoryService 
{
    @Autowired
    private CPointsHistoryMapper cPointsHistoryMapper;

    /**
     * 查询积分变动明细
     * 
     * @param id 积分变动明细ID
     * @return 积分变动明细
     */
    @Override
    public CPointsHistory selectCPointsHistoryById(Long id)
    {
        return cPointsHistoryMapper.selectCPointsHistoryById(id);
    }

    /**
     * 查询积分变动明细列表
     * 
     * @param cPointsHistory 积分变动明细
     * @return 积分变动明细
     */
    @Override
    public List<CPointsHistory> selectCPointsHistoryList(CPointsHistory cPointsHistory)
    {
        return cPointsHistoryMapper.selectCPointsHistoryList(cPointsHistory);
    }

    /**
     * 新增积分变动明细
     * 
     * @param cPointsHistory 积分变动明细
     * @return 结果
     */
    @Override
    public int insertCPointsHistory(CPointsHistory cPointsHistory)
    {
        return cPointsHistoryMapper.insertCPointsHistory(cPointsHistory);
    }

    /**
     * 修改积分变动明细
     * 
     * @param cPointsHistory 积分变动明细
     * @return 结果
     */
    @Override
    public int updateCPointsHistory(CPointsHistory cPointsHistory)
    {
        return cPointsHistoryMapper.updateCPointsHistory(cPointsHistory);
    }

    /**
     * 批量删除积分变动明细
     * 
     * @param ids 需要删除的积分变动明细ID
     * @return 结果
     */
    @Override
    public int deleteCPointsHistoryByIds(Long[] ids)
    {
        return cPointsHistoryMapper.deleteCPointsHistoryByIds(ids);
    }

    /**
     * 删除积分变动明细信息
     * 
     * @param id 积分变动明细ID
     * @return 结果
     */
    @Override
    public int deleteCPointsHistoryById(Long id)
    {
        return cPointsHistoryMapper.deleteCPointsHistoryById(id);
    }
}
