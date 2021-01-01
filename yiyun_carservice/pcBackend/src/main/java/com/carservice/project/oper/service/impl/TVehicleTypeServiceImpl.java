package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TVehicleTypeMapper;
import com.carservice.project.oper.domain.TVehicleType;
import com.carservice.project.oper.service.ITVehicleTypeService;

/**
 * 车辆类型Service业务层处理
 * 
 * @author carservice
 * @date 2020-08-25
 */
@Service
public class TVehicleTypeServiceImpl implements ITVehicleTypeService 
{
    @Autowired
    private TVehicleTypeMapper tVehicleTypeMapper;

    /**
     * 查询车辆类型
     * 
     * @param vcehicleTypeId 车辆类型ID
     * @return 车辆类型
     */
    @Override
    public TVehicleType selectTVehicleTypeById(Long vcehicleTypeId)
    {
        return tVehicleTypeMapper.selectTVehicleTypeById(vcehicleTypeId);
    }

    /**
     * 查询车辆类型列表
     * 
     * @param tVehicleType 车辆类型
     * @return 车辆类型
     */
    @Override
    public List<TVehicleType> selectTVehicleTypeList(TVehicleType tVehicleType)
    {
        return tVehicleTypeMapper.selectTVehicleTypeList(tVehicleType);
    }

    /**
     * 新增车辆类型
     * 
     * @param tVehicleType 车辆类型
     * @return 结果
     */
    @Override
    public int insertTVehicleType(TVehicleType tVehicleType)
    {
        tVehicleType.setCreateTime(DateUtils.getNowDate());
        return tVehicleTypeMapper.insertTVehicleType(tVehicleType);
    }

    /**
     * 修改车辆类型
     * 
     * @param tVehicleType 车辆类型
     * @return 结果
     */
    @Override
    public int updateTVehicleType(TVehicleType tVehicleType)
    {
        return tVehicleTypeMapper.updateTVehicleType(tVehicleType);
    }

    /**
     * 批量删除车辆类型
     * 
     * @param vcehicleTypeIds 需要删除的车辆类型ID
     * @return 结果
     */
    @Override
    public int deleteTVehicleTypeByIds(Long[] vcehicleTypeIds)
    {
        return tVehicleTypeMapper.deleteTVehicleTypeByIds(vcehicleTypeIds);
    }

    /**
     * 删除车辆类型信息
     * 
     * @param vcehicleTypeId 车辆类型ID
     * @return 结果
     */
    @Override
    public int deleteTVehicleTypeById(Long vcehicleTypeId)
    {
        return tVehicleTypeMapper.deleteTVehicleTypeById(vcehicleTypeId);
    }
}
