package com.carservice.project.oper.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.ErpDriverInfoMapper;
import com.carservice.project.oper.domain.ErpDriverInfo;
import com.carservice.project.oper.service.IErpDriverInfoService;

/**
 * erp驾驶员Service业务层处理
 * 
 * @author carservice
 * @date 2020-08-27
 */
@Service
public class ErpDriverInfoServiceImpl implements IErpDriverInfoService 
{
    @Autowired
    private ErpDriverInfoMapper erpDriverInfoMapper;

    /**
     * 查询erp驾驶员
     * 
     * @param fDriverid erp驾驶员ID
     * @return erp驾驶员
     */
    @Override
    public ErpDriverInfo selectErpDriverInfoById(String fDriverid)
    {
        return erpDriverInfoMapper.selectErpDriverInfoById(fDriverid);
    }

    /**
     * 查询erp驾驶员列表
     * 
     * @param erpDriverInfo erp驾驶员
     * @return erp驾驶员
     */
    @Override
    public List<ErpDriverInfo> selectErpDriverInfoList(ErpDriverInfo erpDriverInfo)
    {
        return erpDriverInfoMapper.selectErpDriverInfoList(erpDriverInfo);
    }

    /**
     * 新增erp驾驶员
     * 
     * @param erpDriverInfo erp驾驶员
     * @return 结果
     */
    @Override
    public int insertErpDriverInfo(ErpDriverInfo erpDriverInfo)
    {
        return erpDriverInfoMapper.insertErpDriverInfo(erpDriverInfo);
    }

    /**
     * 修改erp驾驶员
     * 
     * @param erpDriverInfo erp驾驶员
     * @return 结果
     */
    @Override
    public int updateErpDriverInfo(ErpDriverInfo erpDriverInfo)
    {
        return erpDriverInfoMapper.updateErpDriverInfo(erpDriverInfo);
    }

    /**
     * 批量删除erp驾驶员
     * 
     * @param fDriverids 需要删除的erp驾驶员ID
     * @return 结果
     */
    @Override
    public int deleteErpDriverInfoByIds(String[] fDriverids)
    {
        return erpDriverInfoMapper.deleteErpDriverInfoByIds(fDriverids);
    }

    /**
     * 删除erp驾驶员信息
     * 
     * @param fDriverid erp驾驶员ID
     * @return 结果
     */
    @Override
    public int deleteErpDriverInfoById(String fDriverid)
    {
        return erpDriverInfoMapper.deleteErpDriverInfoById(fDriverid);
    }
}
