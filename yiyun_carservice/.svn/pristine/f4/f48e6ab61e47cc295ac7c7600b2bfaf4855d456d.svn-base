package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TVehicleInfo;
import com.carservice.project.oper.domain.TVehicleType;

/**
 * 车辆信息Service接口
 * 
 * @author carservice
 * @date 2020-04-29
 */
public interface ITVehicleInfoService 
{
    /**
     * 查询车辆信息
     * 
     * @param vehicleId 车辆信息ID
     * @return 车辆信息
     */
    public TVehicleInfo selectTVehicleInfoById(String vehicleId);

    /**
     * 查询车辆信息列表
     * 
     * @param tVehicleInfo 车辆信息
     * @return 车辆信息集合
     */
    public List<TVehicleInfo> selectTVehicleInfoList(TVehicleInfo tVehicleInfo);


    /**
     * 新增车辆信息
     * 
     * @param tVehicleInfo 车辆信息
     * @return 结果
     */
    public int insertTVehicleInfo(TVehicleInfo tVehicleInfo);

    /**
     * 修改车辆信息
     * 
     * @param tVehicleInfo 车辆信息
     * @return 结果
     */
    public int updateTVehicleInfo(TVehicleInfo tVehicleInfo);

    /**
     * 批量删除车辆信息
     * 
     * @param vehicleIds 需要删除的车辆信息ID
     * @return 结果
     */
    public int deleteTVehicleInfoByIds(String[] vehicleIds);

    /**
     * 删除车辆信息信息
     * 
     * @param vehicleId 车辆信息ID
     * @return 结果
     */
    public int deleteTVehicleInfoById(String vehicleId);

    public List<TVehicleType>  getCarType();

    /**
     * 查询可用的车辆（没有任务的车辆）
     * @param tVehicleInfo
     * @return
     */
    public List<TVehicleInfo> selectCanUseTVehicleInfoList(TVehicleInfo tVehicleInfo);



}
