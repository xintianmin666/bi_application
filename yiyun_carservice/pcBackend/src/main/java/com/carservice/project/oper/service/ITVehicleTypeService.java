package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.TVehicleType;

/**
 * 车辆类型Service接口
 * 
 * @author carservice
 * @date 2020-08-25
 */
public interface ITVehicleTypeService 
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
     * 批量删除车辆类型
     * 
     * @param vcehicleTypeIds 需要删除的车辆类型ID
     * @return 结果
     */
    public int deleteTVehicleTypeByIds(Long[] vcehicleTypeIds);

    /**
     * 删除车辆类型信息
     * 
     * @param vcehicleTypeId 车辆类型ID
     * @return 结果
     */
    public int deleteTVehicleTypeById(Long vcehicleTypeId);
}
