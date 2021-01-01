package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TSiteInfoMapper;
import com.carservice.project.oper.domain.TSiteInfo;
import com.carservice.project.oper.service.ITSiteInfoService;

/**
 * 城市站点Service业务层处理
 * 
 * @author carservice
 * @date 2020-05-23
 */
@Service
public class TSiteInfoServiceImpl implements ITSiteInfoService 
{
    @Autowired
    private TSiteInfoMapper tSiteInfoMapper;

    /**
     * 查询城市站点
     * 
     * @param siteId 城市站点ID
     * @return 城市站点
     */
    @Override
    public TSiteInfo selectTSiteInfoById(Long siteId)
    {
        return tSiteInfoMapper.selectTSiteInfoById(siteId);
    }

    /**
     * 查询城市站点列表
     * 
     * @param tSiteInfo 城市站点
     * @return 城市站点
     */
    @Override
    public List<TSiteInfo> selectTSiteInfoList(TSiteInfo tSiteInfo)
    {
        return tSiteInfoMapper.selectTSiteInfoList(tSiteInfo);
    }

    /**
     * 新增城市站点
     * 
     * @param tSiteInfo 城市站点
     * @return 结果
     */
    @Override
    public int insertTSiteInfo(TSiteInfo tSiteInfo)
    {
        tSiteInfo.setCreateTime(DateUtils.getNowDate());
        return tSiteInfoMapper.insertTSiteInfo(tSiteInfo);
    }

    /**
     * 修改城市站点
     * 
     * @param tSiteInfo 城市站点
     * @return 结果
     */
    @Override
    public int updateTSiteInfo(TSiteInfo tSiteInfo)
    {
        tSiteInfo.setUpdateTime(DateUtils.getNowDate());
        return tSiteInfoMapper.updateTSiteInfo(tSiteInfo);
    }

    /**
     * 批量删除城市站点
     * 
     * @param siteIds 需要删除的城市站点ID
     * @return 结果
     */
    @Override
    public int deleteTSiteInfoByIds(Long[] siteIds)
    {
        return tSiteInfoMapper.deleteTSiteInfoByIds(siteIds);
    }

    /**
     * 删除城市站点信息
     * 
     * @param siteId 城市站点ID
     * @return 结果
     */
    @Override
    public int deleteTSiteInfoById(Long siteId)
    {
        return tSiteInfoMapper.deleteTSiteInfoById(siteId);
    }
}
