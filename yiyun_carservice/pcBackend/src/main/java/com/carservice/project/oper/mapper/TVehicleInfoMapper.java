package com.carservice.project.oper.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.carservice.project.oper.domain.TVehicleInfo;
import com.carservice.project.oper.domain.TVehicleTaskStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 车辆信息Mapper接口
 * 
 * @author carservice
 * @date 2020-04-29
 */
public interface TVehicleInfoMapper 
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
     * 删除车辆信息
     * 
     * @param vehicleId 车辆信息ID
     * @return 结果
     */
    public int deleteTVehicleInfoById(String vehicleId);

    /**
     * 批量删除车辆信息
     * 
     * @param vehicleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTVehicleInfoByIds(String[] vehicleIds);

    void deleteErpCarINfo();

    void addErpCarINfo(@Param("carArray") JSONArray dataArray);

    TVehicleInfo selectTVehicleInfoByLicenseTagno(String licenseTagno);

    List< TVehicleInfo > selectTVehicleInfoListNotin(TVehicleTaskStatus tVehicleTaskStatus);
}
