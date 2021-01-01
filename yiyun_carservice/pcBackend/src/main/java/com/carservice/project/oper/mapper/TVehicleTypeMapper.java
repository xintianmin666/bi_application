package com.carservice.project.oper.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.carservice.project.oper.domain.TVehicleType;

/**
 * 车辆类型Mapper接口
 * 
 * @author carservice
 * @date 2020-08-14
 */
public interface TVehicleTypeMapper 
{
    /**
     * 查询车辆类型
     * 
     * @param vcehicleTypeId 车辆类型ID
     * @return 车辆类型
     */
    public TVehicleType selectTVehicleTypeById(Long vcehicleTypeId);

    /**
     * 查询车辆类型列表
     * 
     * @param tVehicleType 车辆类型
     * @return 车辆类型集合
     */
    public List<TVehicleType> selectTVehicleTypeList(TVehicleType tVehicleType);

    /**
     * 新增车辆类型
     * 
     * @param tVehicleType 车辆类型
     * @return 结果
     */
    public int insertTVehicleType(TVehicleType tVehicleType);

    /**
     * 修改车辆类型
     * 
     * @param tVehicleType 车辆类型
     * @return 结果
     */
    public int updateTVehicleType(TVehicleType tVehicleType);

    /**
     * 删除车辆类型
     * 
     * @param vcehicleTypeId 车辆类型ID
     * @return 结果
     */
    public int deleteTVehicleTypeById(Long vcehicleTypeId);

    /**
     * 批量删除车辆类型
     * 
     * @param vcehicleTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTVehicleTypeByIds(Long[] vcehicleTypeIds);

    List<TVehicleType> selectTVehicleTypeByIds(String vehicleTypeIds);


}
