package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TSiteInfo;

/**
 * 城市站点Service接口
 * 
 * @author carservice
 * @date 2020-05-23
 */
public interface ITSiteInfoService 
{
    /**
     * 查询城市站点
     * 
     * @param siteId 城市站点ID
     * @return 城市站点
     */
    public TSiteInfo selectTSiteInfoById(Long siteId);

    /**
     * 查询城市站点列表
     * 
     * @param tSiteInfo 城市站点
     * @return 城市站点集合
     */
    public List<TSiteInfo> selectTSiteInfoList(TSiteInfo tSiteInfo);

    /**
     * 新增城市站点
     * 
     * @param tSiteInfo 城市站点
     * @return 结果
     */
    public int insertTSiteInfo(TSiteInfo tSiteInfo);

    /**
     * 修改城市站点
     * 
     * @param tSiteInfo 城市站点
     * @return 结果
     */
    public int updateTSiteInfo(TSiteInfo tSiteInfo);

    /**
     * 批量删除城市站点
     * 
     * @param siteIds 需要删除的城市站点ID
     * @return 结果
     */
    public int deleteTSiteInfoByIds(Long[] siteIds);

    /**
     * 删除城市站点信息
     * 
     * @param siteId 城市站点ID
     * @return 结果
     */
    public int deleteTSiteInfoById(Long siteId);
}
