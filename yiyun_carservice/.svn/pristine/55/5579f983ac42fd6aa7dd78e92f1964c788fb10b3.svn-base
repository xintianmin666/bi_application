package com.carservice.project.oper.service;

import com.carservice.project.oper.domain.TVehicleTaskStatus;

import java.util.List;

/**
 * 车辆运行记录Service接口
 * @author carservice
 * @date 2020-05-12
 */
public interface ITVehicleTaskStatusService {
    /**
     * 查询车辆运行记录
     * @param vehicleTaskStatusId 车辆运行记录ID
     * @return 车辆运行记录
     */
    public TVehicleTaskStatus selectTVehicleTaskStatusById(String vehicleTaskStatusId);

    /**
     * 查询车辆运行记录列表
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 车辆运行记录集合
     */
    public List< TVehicleTaskStatus > selectTVehicleTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus);

    public List< TVehicleTaskStatus > selectTVehicleTaskStatusList1(TVehicleTaskStatus tVehicleTaskStatus);

    public List< TVehicleTaskStatus > selectTVehicleTaskStatusListByTime(String startTime, String endTime);

    /**
     * 新增车辆运行记录
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 结果
     */
    public int insertTVehicleTaskStatus(TVehicleTaskStatus tVehicleTaskStatus);

    List< TVehicleTaskStatus > selectTVehicleTaskStatusList2(TVehicleTaskStatus tVehicleTaskStatus);

    /**
     * 修改车辆运行记录
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 结果
     */
    public int updateTVehicleTaskStatus(TVehicleTaskStatus tVehicleTaskStatus);

    /**
     * 批量删除车辆运行记录
     * @param vehicleTaskStatusIds 需要删除的车辆运行记录ID
     * @return 结果
     */
    public int deleteTVehicleTaskStatusByIds(String[] vehicleTaskStatusIds);

    /**
     * 删除车辆运行记录信息
     * @param vehicleTaskStatusId 车辆运行记录ID
     * @return 结果
     */
    public int deleteTVehicleTaskStatusById(String vehicleTaskStatusId);

    TVehicleTaskStatus selectTVehicleTaskStatusByDispatchOrdercode(String dispatchOrdercode);

    List selectCarTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus);

    List selectDriverTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus);
}