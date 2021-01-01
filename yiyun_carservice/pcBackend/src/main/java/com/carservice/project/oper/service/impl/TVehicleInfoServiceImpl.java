package com.carservice.project.oper.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.IdUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.common.utils.spring.SpringUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import com.carservice.framework.security.LoginUser;
import com.carservice.project.oper.domain.TVehicleInfo;
import com.carservice.project.oper.domain.TVehicleTaskStatus;
import com.carservice.project.oper.domain.TVehicleType;
import com.carservice.project.oper.mapper.TVehicleInfoMapper;
import com.carservice.project.oper.mapper.TVehicleTaskStatusMapper;
import com.carservice.project.oper.mapper.TVehicleTypeMapper;
import com.carservice.project.oper.service.ITVehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 车辆信息Service业务层处理
 * @author carservice
 * @date 2020-04-29
 */
@Service
public class TVehicleInfoServiceImpl implements ITVehicleInfoService {
    @Autowired
    private TVehicleInfoMapper tVehicleInfoMapper;
    @Autowired
    private TVehicleTypeMapper tVehicleTypeMapper;
    @Autowired
    private TVehicleTaskStatusMapper vehicleTaskStatusMapper;
    @Value("${gj.url}")
    private String gjUrl;

    /**
     * 查询车辆信息
     * @param vehicleId 车辆信息ID
     * @return 车辆信息
     */
    @Override
    public TVehicleInfo selectTVehicleInfoById(String vehicleId) {
        return tVehicleInfoMapper.selectTVehicleInfoById(vehicleId);
    }

    /**
     * 查询车辆信息列表
     * @param tVehicleInfo 车辆信息
     * @return 车辆信息
     */
    @Override
    @DataScope(deptAlias = "v")
    public List< TVehicleInfo > selectTVehicleInfoList(TVehicleInfo tVehicleInfo) {
        return tVehicleInfoMapper.selectTVehicleInfoList(tVehicleInfo);
    }
    /**
     * 查询车辆信息列表
     * @param
     * @return 车辆信息
     */
    @DataScope(deptAlias = "v")
    public List selectTVehicleInfoListNotin(TVehicleTaskStatus tVehicleTaskStatus) {
        List list = tVehicleTaskStatus.getList();
        List< TVehicleInfo > list1 = tVehicleInfoMapper.selectTVehicleInfoListNotin(tVehicleTaskStatus);
        for (TVehicleInfo vehicleInfo : list1) {
            Map map = new HashMap<>();
            vehicleInfo.setVehiclePlateNo(vehicleInfo.getLicenseTagno());
            map.put("vehiclePlateNo",vehicleInfo);
            list.add(map);
        }
        return list;
    }

    /**
     * 查询排班可用车辆信息列表
     * @param tVehicleInfo1 车辆信息
     * @return 车辆信息
     */
    @Override
    @DataScope(deptAlias = "v")
    public List< TVehicleInfo > selectCanUseTVehicleInfoList(TVehicleInfo tVehicleInfo1) {
        return dealList(tVehicleInfo1);
    }

    /**
     * 查询排班可用车辆信息列表
     * @param tVehicleInfo1 车辆信息
     * @return 车辆信息
     */
    public List< TVehicleInfo > selectCanUseTVehicleInfoListFormini(TVehicleInfo tVehicleInfo1) {
        return dealList(tVehicleInfo1);
    }

    public List< TVehicleInfo > dealList(TVehicleInfo tVehicleInfo1) {
        String carType = tVehicleInfo1.getType();//车辆类型
        int passengerNum = tVehicleInfo1.getPassengerNum();//用车人数
        String startTime = tVehicleInfo1.getUseCarStartTime();//用车开始时间
        String endTime = tVehicleInfo1.getUseCarEndTime();//用车结束时间
        List< TVehicleInfo > canUseTVehicleInfoList = new ArrayList<>();
        TVehicleInfo vehicleInfo = tVehicleInfo1;
        vehicleInfo.setVehicleState("1");// 正常可用
        /*vehicleInfo.setType(carType);//车辆类型
        vehicleInfo.setDataScope(tVehicleInfo1.getDataScope());
        vehicleInfo.setDeptId(tVehicleInfo1.getDeptId());
        vehicleInfo.setLicenseTagno(tVehicleInfo1.getLicenseTagno());
        vehicleInfo.setBelong(tVehicleInfo1.getBelong());*/
        vehicleInfo.setType(tVehicleInfo1.getType() == null ? "" : tVehicleInfo1.getType());//车辆类型
        vehicleInfo.setDeptId(tVehicleInfo1.getDeptId() == null ? "" : tVehicleInfo1.getDeptId());
        vehicleInfo.setLicenseTagno(tVehicleInfo1.getLicenseTagno() == null ? "" : tVehicleInfo1.getLicenseTagno());
        vehicleInfo.setDriverName(tVehicleInfo1.getDriverName() == null ? "" : tVehicleInfo1.getDriverName());
        List< TVehicleInfo > vehicleInfoList = tVehicleInfoMapper.selectTVehicleInfoList(vehicleInfo);
        List< TVehicleTaskStatus > tVehicleTaskStatusList = vehicleTaskStatusMapper.selectTVehicleTaskStatusListByTime(startTime, endTime);
        for (TVehicleInfo tVehicleInfo : vehicleInfoList) {
            boolean canUse = true;
            for (TVehicleTaskStatus tVehicleTaskStatus : tVehicleTaskStatusList) {
                if (tVehicleTaskStatus.getVehicleInfoId()==null){
                    continue;
                }
                if (tVehicleTaskStatus.getVehicleInfoId().equals(tVehicleInfo.getVehicleId())) {
                    //这辆车这个时间段有任务，不可用
                    canUse = false;
                    break;
                }
            }
            if (canUse && tVehicleInfo.getVehicleType().getPassengerNum() >= passengerNum) {
                canUseTVehicleInfoList.add(tVehicleInfo);
            }
        }
        //排除公交
        /*try {
            JSONObject param = new JSONObject();
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            String result = HttpUtils.httpURLConnectionPOST(gjUrl + "/pickRide/getUsableCar", param);
            JSONArray rsultArray = JSONArray.parseArray(JSONObject.parseObject(result).getString("data"));
            Iterator< TVehicleInfo > vehicleInfoIterator = canUseTVehicleInfoList.iterator();
            while (vehicleInfoIterator.hasNext()) {
                TVehicleInfo vehicleInfo1 = vehicleInfoIterator.next();
                if (StringUtils.isEmpty(vehicleInfo1.getGjCarId())) {
                    //不在公交调度系统里，不排除此车辆
                    continue;
                }
                boolean isDelete = true;
                for (Object o : rsultArray) {
                    JSONObject gj = (JSONObject) o;
                    if (vehicleInfo1.getLicenseTagno().equals(gj.getString("vehicleNum"))) {
                        isDelete = false;
                        if (!vehicleInfo1.getGjCarId().equals(gj.getString("objectID"))) {
                            vehicleInfo1.setGjCarId(gj.getString("objectID"));
                            tVehicleInfoMapper.updateTVehicleInfo(vehicleInfo1);
                        }
                        break;
                    }
                }
                if (isDelete) {
                    vehicleInfoIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return canUseTVehicleInfoList;
    }

    /**
     * 新增车辆信息
     * @param tVehicleInfo 车辆信息
     * @return 结果
     */
    @Override
    public int insertTVehicleInfo(TVehicleInfo tVehicleInfo) {

        TVehicleInfo tVehicleInfo1 = tVehicleInfoMapper.selectTVehicleInfoByLicenseTagno(tVehicleInfo.getLicenseTagno());
        if (tVehicleInfo1 != null) {
            return -1;
        }
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getLoginUser();
        tVehicleInfo.setVehicleId(IdUtils.fastSimpleUUID());
        tVehicleInfo.setCreateUserId(loginUser.getUser().getUserId());
        tVehicleInfo.setCreateTime(DateUtils.getNowDate());
        //tVehicleInfo.setGjCarId(getGjCar(tVehicleInfo.getLicenseTagno()));
        return tVehicleInfoMapper.insertTVehicleInfo(tVehicleInfo);
    }

    /**
     * 修改车辆信息
     * @param tVehicleInfo 车辆信息
     * @return 结果
     */
    @Override
    public int updateTVehicleInfo(TVehicleInfo tVehicleInfo) {
        TVehicleInfo tVehicleInfo1 = tVehicleInfoMapper.selectTVehicleInfoByLicenseTagno(tVehicleInfo.getLicenseTagno());
        if (tVehicleInfo1 != null && !tVehicleInfo1.getVehicleId().equals(tVehicleInfo.getVehicleId())) {
            return -1;
        }
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getLoginUser();
        tVehicleInfo.setModifyUserId(loginUser.getUser().getUserId());
        tVehicleInfo.setModifyTime(DateUtils.getNowDate());
        if (StringUtils.isNotEmpty(tVehicleInfo.getLicenseTagno())) {
            //tVehicleInfo.setGjCarId(getGjCar(tVehicleInfo.getLicenseTagno()));
        }
        return tVehicleInfoMapper.updateTVehicleInfo(tVehicleInfo);
    }

    /**
     * 批量删除车辆信息
     * @param vehicleIds 需要删除的车辆信息ID
     * @return 结果
     */
    @Override
    public int deleteTVehicleInfoByIds(String[] vehicleIds) {
        return tVehicleInfoMapper.deleteTVehicleInfoByIds(vehicleIds);
    }

    /**
     * 删除车辆信息信息
     * @param vehicleId 车辆信息ID
     * @return 结果
     */
    @Override
    public int deleteTVehicleInfoById(String vehicleId) {
        return tVehicleInfoMapper.deleteTVehicleInfoById(vehicleId);
    }

    @Override
    public List< TVehicleType > getCarType() {
        TVehicleType tVehicleType = new TVehicleType();
        tVehicleType.setIsvaliable("1");
        return tVehicleTypeMapper.selectTVehicleTypeList(tVehicleType);
    }

    @Transactional
    public int tongbuErpCarINfo() {
        tVehicleInfoMapper.deleteErpCarINfo();
        String result = HttpUtils.sendGet("https://api.whyuntai.com/learun/adms/wechatdriver/vehicleList", "data={%20queryJson:%27{}%27%20}");
        JSONObject resultJSON = JSONObject.parseObject(result);
        JSONArray dataArray = resultJSON.getJSONArray("data");
        tVehicleInfoMapper.addErpCarINfo(dataArray);
        return 1;
    }

    //获取公交信息
    /*public String getGjCar(String vehicleNum) {
        try {
            JSONObject param = new JSONObject();
            param.put("vehicleNum", vehicleNum);
            String gjResult = HttpUtils.httpURLConnectionPOST(gjUrl + "/pickRide/judgeCarExist", param);
            JSONObject data = (JSONObject) JSONObject.parseObject(gjResult).get("data");
            if (data.get("carInfo") != null) {
                JSONObject carInfo = (JSONObject) data.getJSONArray("carInfo").get(0);
                return carInfo.getString("objectID");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
