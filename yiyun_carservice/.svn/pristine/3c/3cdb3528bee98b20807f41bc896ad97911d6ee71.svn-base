package com.carservice.project.oper.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import com.carservice.project.oper.domain.TVehicleTaskStatus;
import com.carservice.project.oper.mapper.TVehicleTaskStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TDriverInfoMapper;
import com.carservice.project.oper.domain.TDriverInfo;
import com.carservice.project.oper.service.ITDriverInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author carservice
 * @date 2020-04-28
 */
@Service
public class TDriverInfoServiceImpl implements ITDriverInfoService 
{
    @Autowired
    private TDriverInfoMapper tDriverInfoMapper;
    @Autowired
    private TVehicleTaskStatusMapper vehicleTaskStatusMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param driverId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TDriverInfo selectTDriverInfoById(String driverId)
    {
        return tDriverInfoMapper.selectTDriverInfoById(driverId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tDriverInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<TDriverInfo> selectTDriverInfoList(TDriverInfo tDriverInfo)
    {
        return tDriverInfoMapper.selectTDriverInfoList(tDriverInfo);
    }

    /**
     * 获取可用排班驾驶员列表
     * @param tDriverInfo
     * @return
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<TDriverInfo> selectCanUseTDriverInfoList(TDriverInfo tDriverInfo) {
        String startTime = tDriverInfo.getTaskStartTime();
        String endTime = tDriverInfo.getTaskEndTime();
        tDriverInfo.setState("1");//可用
        List<TVehicleTaskStatus> tVehicleTaskStatusList = vehicleTaskStatusMapper.selectTVehicleTaskStatusListByTime(startTime,endTime);
        List<TDriverInfo> driverInfoList = tDriverInfoMapper.selectTDriverInfoList(tDriverInfo);
        List<TDriverInfo> canUseDriverInfoList = new ArrayList<>();
        for (TDriverInfo driverInfo : driverInfoList) {
            boolean canUse = true;
            for (TVehicleTaskStatus tVehicleTaskStatus : tVehicleTaskStatusList) {
                if (tVehicleTaskStatus.getDriverId()!=null
                &&tVehicleTaskStatus.getDriverId().equals(driverInfo.getDriverId())){
                    //这辆车这个时间段有任务，不可用
                    canUse = false;
                    break;
                }
            }
            if (canUse){
                canUseDriverInfoList.add(driverInfo);
            }
        }
        return canUseDriverInfoList;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tDriverInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTDriverInfo(TDriverInfo tDriverInfo)
    {
        tDriverInfo.setCreateTime(DateUtils.getNowDate());
        return tDriverInfoMapper.insertTDriverInfo(tDriverInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tDriverInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTDriverInfo(TDriverInfo tDriverInfo)
    {
        return tDriverInfoMapper.updateTDriverInfo(tDriverInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param driverIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteTDriverInfoByIds(String[] driverIds)
    {
        return tDriverInfoMapper.deleteTDriverInfoByIds(driverIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param driverId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteTDriverInfoById(String driverId)
    {
        return tDriverInfoMapper.deleteTDriverInfoById(driverId);
    }

    @Override
    public TDriverInfo selectTDriverInfoByErpId(String erpDriverId) {
        return tDriverInfoMapper.selectTDriverInfoByErpId(erpDriverId);
    }

    @Transactional
    public int tongbuErpDriverINfo(){
        tDriverInfoMapper.deleteErpDriverINfo();
        String result = HttpUtils.sendGet("https://api.whyuntai.com/learun/adms/wechatdriver/driverlist","");
        JSONObject resultJSON = JSONObject.parseObject(result);
        JSONArray dataArray = resultJSON.getJSONArray("data");
        tDriverInfoMapper.addErpDriverINfo(dataArray);
        return 1;
    }
}


