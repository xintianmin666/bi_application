package com.carservice.project.oper.mapper;

import com.carservice.project.oper.domain.TVehicleTaskStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆运行记录Mapper接口
 * @author carservice
 * @date 2020-05-12
 */
public interface TVehicleTaskStatusMapper {
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


    /**
     * 查询调度班次管理列表
     * @param tVehicleTaskStatus 调度班次管理
     * @return 调度班次管理集合
     */
    public List< TVehicleTaskStatus > selectTVehicleTaskStatusList2(TVehicleTaskStatus tVehicleTaskStatus);

    /**
     * 新增车辆运行记录
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 结果
     */
    public int insertTVehicleTaskStatus(TVehicleTaskStatus tVehicleTaskStatus);

    /**
     * 修改车辆运行记录
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 结果
     */
    public int updateTVehicleTaskStatus(TVehicleTaskStatus tVehicleTaskStatus);

    /**
     * 删除车辆运行记录
     * @param vehicleTaskStatusId 车辆运行记录ID
     * @return 结果
     */
    public int deleteTVehicleTaskStatusById(String vehicleTaskStatusId);

    /**
     * 批量删除车辆运行记录
     * @param vehicleTaskStatusIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTVehicleTaskStatusByIds(String[] vehicleTaskStatusIds);

    public List< TVehicleTaskStatus > selectTVehicleTaskStatusListByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List< TVehicleTaskStatus > selectTVehicleTask(@Param("productId") String productId, @Param("passengerNum") String passengerNum, @Param("useCarStartTime") String useCarStartTime);

    TVehicleTaskStatus selectTVehicleTaskStatusByDispatchOrdercode(String dispatchOrdercode);

    List< TVehicleTaskStatus > selectTVehicleTaskStatusList1(TVehicleTaskStatus tVehicleTaskStatus);

    List< TVehicleTaskStatus > selectTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus);

    List< TVehicleTaskStatus > selectDriverTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus);

    List< TVehicleTaskStatus > selectCarTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus);

    List< TVehicleTaskStatus > getPCshift(@Param("productId") Long productId, @Param("time") String time);
}