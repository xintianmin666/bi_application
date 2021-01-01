package com.carservice.project.oper.mapper;

import java.util.List;
import com.carservice.project.oper.domain.TSiteInfo;

/**
 * 城市站点Mapper接口
 * 
 * @author carservice
 * @date 2020-05-23
 */
public interface TSiteInfoMapper 
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
     * 删除城市站点
     * 
     * @param siteId 城市站点ID
     * @return 结果
     */
    public int deleteTSiteInfoById(Long siteId);

    /**
     * 批量删除城市站点
     * 
     * @param siteIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTSiteInfoByIds(Long[] siteIds);
}
