package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TDriverInfo;

/**
 * 驾驶员信息Service接口
 * 
 * @author carservice
 * @date 2020-04-28
 */
public interface ITDriverInfoService 
{
    /**
     * 查询驾驶员信息
     * 
     * @param driverId 驾驶员信息ID
     * @return 驾驶员信息
     */
    public TDriverInfo selectTDriverInfoById(String driverId);

    /**
     * 查询驾驶员信息列表
     * 
     * @param tDriverInfo 驾驶员信息
     * @return 驾驶员信息集合
     */
    public List<TDriverInfo> selectTDriverInfoList(TDriverInfo tDriverInfo);

    /**
     * 选择可使用的驾驶员列表
     * @param tDriverInfo
     * @return
     */
    public List<TDriverInfo> selectCanUseTDriverInfoList(TDriverInfo tDriverInfo);
    /**
     * 新增驾驶员信息
     * 
     * @param tDriverInfo 驾驶员信息
     * @return 结果
     */
    public int insertTDriverInfo(TDriverInfo tDriverInfo);

    /**
     * 修改驾驶员信息
     * 
     * @param tDriverInfo 驾驶员信息
     * @return 结果
     */
    public int updateTDriverInfo(TDriverInfo tDriverInfo);

    /**
     * 批量删除驾驶员信息
     * 
     * @param driverIds 需要删除的驾驶员信息ID
     * @return 结果
     */
    public int deleteTDriverInfoByIds(String[] driverIds);

    /**
     * 删除驾驶员信息信息
     * 
     * @param driverId 驾驶员信息ID
     * @return 结果
     */
    public int deleteTDriverInfoById(String driverId);


    public TDriverInfo selectTDriverInfoByErpId(String driverId);
}
