package com.carservice.project.oper.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carservice.project.oper.domain.TDriverInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 驾驶员信息Mapper接口
 * 
 * @author carservice
 * @date 2020-04-28
 */
public interface TDriverInfoMapper 
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
     * 删除驾驶员信息
     * 
     * @param driverId 驾驶员信息ID
     * @return 结果
     */
    public int deleteTDriverInfoById(String driverId);

    /**
     * 批量删除驾驶员信息
     * 
     * @param driverIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTDriverInfoByIds(String[] driverIds);

    void addErpDriverINfo(@Param("driverArray") JSONArray driverArray);

    void deleteErpDriverINfo();

    TDriverInfo selectTDriverInfoByErpId(String erpDriverId);
}
