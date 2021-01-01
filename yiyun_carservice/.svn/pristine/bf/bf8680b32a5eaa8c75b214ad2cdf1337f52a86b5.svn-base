package com.carservice.project.oper.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.ErpCarInfoMapper;
import com.carservice.project.oper.domain.ErpCarInfo;
import com.carservice.project.oper.service.IErpCarInfoService;

/**
 * erp车辆Service业务层处理
 * 
 * @author carservice
 * @date 2020-08-27
 */
@Service
public class ErpCarInfoServiceImpl implements IErpCarInfoService 
{
    @Autowired
    private ErpCarInfoMapper erpCarInfoMapper;

    /**
     * 查询erp车辆
     * 
     * @param fId erp车辆ID
     * @return erp车辆
     */
    @Override
    public ErpCarInfo selectErpCarInfoById(String fId)
    {
        return erpCarInfoMapper.selectErpCarInfoById(fId);
    }

    /**
     * 查询erp车辆列表
     * 
     * @param erpCarInfo erp车辆
     * @return erp车辆
     */
    @Override
    public List<ErpCarInfo> selectErpCarInfoList(ErpCarInfo erpCarInfo)
    {
        return erpCarInfoMapper.selectErpCarInfoList(erpCarInfo);
    }

    /**
     * 新增erp车辆
     * 
     * @param erpCarInfo erp车辆
     * @return 结果
     */
    @Override
    public int insertErpCarInfo(ErpCarInfo erpCarInfo)
    {
        return erpCarInfoMapper.insertErpCarInfo(erpCarInfo);
    }

    /**
     * 修改erp车辆
     * 
     * @param erpCarInfo erp车辆
     * @return 结果
     */
    @Override
    public int updateErpCarInfo(ErpCarInfo erpCarInfo)
    {
        return erpCarInfoMapper.updateErpCarInfo(erpCarInfo);
    }

    /**
     * 批量删除erp车辆
     * 
     * @param fIds 需要删除的erp车辆ID
     * @return 结果
     */
    @Override
    public int deleteErpCarInfoByIds(String[] fIds)
    {
        return erpCarInfoMapper.deleteErpCarInfoByIds(fIds);
    }

    /**
     * 删除erp车辆信息
     * 
     * @param fId erp车辆ID
     * @return 结果
     */
    @Override
    public int deleteErpCarInfoById(String fId)
    {
        return erpCarInfoMapper.deleteErpCarInfoById(fId);
    }
}
