package com.carservice.project.oper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.OrderCodeUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import com.carservice.project.oper.domain.*;
import com.carservice.project.oper.mapper.TOrderRefundRulesMapper;
import com.carservice.project.oper.mapper.TProductMapper;
import com.carservice.project.oper.mapper.TVehicleInfoMapper;
import com.carservice.project.oper.mapper.TVehicleTaskStatusMapper;
import com.carservice.project.oper.service.ITVehicleTaskStatusService;
import com.carservice.project.order.controller.TRentalOrderController;
import com.carservice.project.order.service.impl.TTaxiOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 车辆运行记录Service业务层处理
 * @author carservice
 * @date 2020-05-12
 */
@Service
public class TVehicleTaskStatusServiceImpl implements ITVehicleTaskStatusService {
    @Autowired
    private TVehicleTaskStatusMapper tVehicleTaskStatusMapper;

    @Autowired
    private TProductMapper tProductMapper;

    @Autowired
    private TVehicleInfoMapper vehicleInfoMapper;

    @Autowired
    private TOrderRefundRulesMapper orderRefundRulesMapper;
    @Autowired
    private TDriverInfoServiceImpl driverInfoService;
    @Autowired
    private TTaxiOrderServiceImpl tTaxiOrderService;
    @Value("${gj.url}")
    private String gjUrl;

    /**
     * 查询车辆运行记录
     * @param vehicleTaskStatusId 车辆运行记录ID
     * @return 车辆运行记录
     */
    @Override
    public TVehicleTaskStatus selectTVehicleTaskStatusById(String vehicleTaskStatusId) {
        return tVehicleTaskStatusMapper.selectTVehicleTaskStatusById(vehicleTaskStatusId);
    }

    /**
     * 开始时间和结束时间车辆在用状态
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List< TVehicleTaskStatus > selectTVehicleTaskStatusListByTime(String startTime, String endTime) {
        return tVehicleTaskStatusMapper.selectTVehicleTaskStatusListByTime(startTime, endTime);
    }

    @Override
    public int insertTVehicleTaskStatus(TVehicleTaskStatus tVehicleTaskStatus) {
        return tVehicleTaskStatusMapper.insertTVehicleTaskStatus(tVehicleTaskStatus);
    }

    /**
     * 查询驾驶员排班记录列表
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 车辆运行记录
     */
    @Override
    @DataScope(deptAlias = "d")
    public List selectDriverTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus) {
        List< TVehicleTaskStatus > taskList = tVehicleTaskStatusMapper.selectDriverTaskStatusList(tVehicleTaskStatus);
        List resultList = new ArrayList<>();
        Map< String, List< TVehicleTaskStatus > > map = new HashMap();
        for (TVehicleTaskStatus vehicleTaskStatus : taskList) {
            if (map.containsKey(vehicleTaskStatus.getDriverId())) {
                List< TVehicleTaskStatus > vehicleTaskStatusList = map.get(vehicleTaskStatus.getDriverId());
                vehicleTaskStatusList.add(vehicleTaskStatus);
            } else {
                List< TVehicleTaskStatus > vehicleTaskStatusList = new ArrayList<>();
                vehicleTaskStatusList.add(vehicleTaskStatus);
                map.put(vehicleTaskStatus.getDriverId(), vehicleTaskStatusList);
            }
        }
        for (String driverId : map.keySet()) {
            List< TVehicleTaskStatus > vehicleTaskStatusList = map.get(driverId);
            Map resultMap = new HashMap();
            for (TVehicleTaskStatus vehicleTaskStatus : vehicleTaskStatusList) {
                resultMap.put("driverName", vehicleTaskStatus);
                if (resultMap.containsKey(DateUtils.dateTime(vehicleTaskStatus.getTaskStartTime()))) {
                    List< TVehicleTaskStatus > vehicleTaskStatusList1 = (List< TVehicleTaskStatus >) resultMap.get(DateUtils.dateTime(vehicleTaskStatus.getTaskStartTime()));
                    vehicleTaskStatusList1.add(vehicleTaskStatus);
                } else {
                    List< TVehicleTaskStatus > vehicleTaskStatusList1 = new ArrayList<>();
                    vehicleTaskStatusList1.add(vehicleTaskStatus);
                    resultMap.put(DateUtils.dateTime(vehicleTaskStatus.getTaskStartTime()), vehicleTaskStatusList1);
                }
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 查询车辆排班记录列表
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 车辆运行记录
     */
    @Override
    @DataScope(deptAlias = "v")
    public List selectCarTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus) {
        List< TVehicleTaskStatus > taskList = tVehicleTaskStatusMapper.selectCarTaskStatusList(tVehicleTaskStatus);
        List resultList = new ArrayList<>();
        Map< String, List< TVehicleTaskStatus > > map = new HashMap();
        for (TVehicleTaskStatus vehicleTaskStatus : taskList) {
            if (map.containsKey(vehicleTaskStatus.getVehicleInfoId())) {
                List< TVehicleTaskStatus > vehicleTaskStatusList = map.get(vehicleTaskStatus.getVehicleInfoId());
                vehicleTaskStatusList.add(vehicleTaskStatus);
            } else {
                List< TVehicleTaskStatus > vehicleTaskStatusList = new ArrayList<>();
                vehicleTaskStatusList.add(vehicleTaskStatus);
                map.put(vehicleTaskStatus.getVehicleInfoId(), vehicleTaskStatusList);
            }
        }
        for (String vehicleId : map.keySet()) {
            List< TVehicleTaskStatus > vehicleTaskStatusList = map.get(vehicleId);
            Map resultMap = new HashMap();
            for (TVehicleTaskStatus vehicleTaskStatus : vehicleTaskStatusList) {
                resultMap.put("vehiclePlateNo", vehicleTaskStatus);
                if (resultMap.containsKey(DateUtils.dateTime(vehicleTaskStatus.getTaskStartTime()))) {
                    List< TVehicleTaskStatus > vehicleTaskStatusList1 = (List< TVehicleTaskStatus >) resultMap.get(DateUtils.dateTime(vehicleTaskStatus.getTaskStartTime()));
                    vehicleTaskStatusList1.add(vehicleTaskStatus);
                } else {
                    List< TVehicleTaskStatus > vehicleTaskStatusList1 = new ArrayList<>();
                    vehicleTaskStatusList1.add(vehicleTaskStatus);
                    resultMap.put(DateUtils.dateTime(vehicleTaskStatus.getTaskStartTime()), vehicleTaskStatusList1);
                }
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 查询车辆运行记录列表
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 车辆运行记录
     */
    @Override
    public List selectTVehicleTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus) {
        String beginTime = tVehicleTaskStatus.getBeginTime();
        String endTime = tVehicleTaskStatus.getEndTime();
        List< TVehicleTaskStatus > vehicleTaskStatusList = tVehicleTaskStatusMapper.selectTVehicleTaskStatusList(tVehicleTaskStatus);
        Map< String, ArrayList< TVehicleTaskStatus > > vehicleTaskStatusMap = new HashMap< String, ArrayList< TVehicleTaskStatus > >();
        for (TVehicleTaskStatus vehicleTaskStatus : vehicleTaskStatusList) {
            if (vehicleTaskStatusMap.containsKey(vehicleTaskStatus.getVehiclePlateNo())) {
                ArrayList< TVehicleTaskStatus > list = (ArrayList) vehicleTaskStatusMap.get(vehicleTaskStatus.getVehiclePlateNo());
                list.add(vehicleTaskStatus);
            } else {
                ArrayList< TVehicleTaskStatus > list = new ArrayList<>();
                list.add(vehicleTaskStatus);
                vehicleTaskStatusMap.put(vehicleTaskStatus.getVehiclePlateNo(), list);
            }
        }
        List retrunList = new ArrayList();
        for (Map.Entry< String, ArrayList< TVehicleTaskStatus > > m : vehicleTaskStatusMap.entrySet()) {
            Map map = new HashMap();
            map.put("carno", m.getKey());
            long days = DateUtils.getDayPoor(DateUtils.parseDate(endTime), DateUtils.parseDate(beginTime));
            String dayKey = "";
            for (int i = 0; i <= days; i++) {
                dayKey = "D" + DateUtils.parseDateToStr("yyyy_MM_dd", DateUtils.addDays(DateUtils.parseDate(beginTime), i));
                List dayKeyList = new ArrayList();
                map.put(dayKey, dayKeyList);
            }
            ArrayList< TVehicleTaskStatus > list = m.getValue();
            for (TVehicleTaskStatus vehicleTaskStatus : list) {
                map.put("driverName", vehicleTaskStatus.getDriverName());
                if (vehicleTaskStatus.getTaskStartTime() != null) {
                    dayKey = "D" + DateUtils.parseDateToStr("yyyy_MM_dd", vehicleTaskStatus.getTaskStartTime());
                    List dayKeyList = new ArrayList();
                    if (map.containsKey(dayKey)) {
                        dayKeyList = (ArrayList) map.get(dayKey);
                        dayKeyList.add(vehicleTaskStatus);
                    } else {
                        dayKeyList.add(vehicleTaskStatus);
                        map.put(dayKey, dayKeyList);
                    }
                }
            }

            retrunList.add(map);
        }
        return retrunList;
    }


    /**
     * 查询调度班次管理列表
     * @param tVehicleTaskStatus 调度班次管理
     * @return 调度班次管理
     */
    @Override
    public List< TVehicleTaskStatus > selectTVehicleTaskStatusList2(TVehicleTaskStatus tVehicleTaskStatus) {
        return tVehicleTaskStatusMapper.selectTVehicleTaskStatusList2(tVehicleTaskStatus);
    }


    /**
     * 新增调度单
     * @param productId       产品id
     * @param useCarStartTime 用车开始时间
     * @param useCarEndTime   用车结束时间
     * @param tastType        3：机场巴士拼座订单  4：拼车订单  5:机场巴士包车订单，6定制客运拼座，7定制客运包车，8自由包车
     * @return ddCode 调度单号
     */
    public String insertTVehicleTaskStatus(
            Long productId, String useCarStartTime, String useCarEndTime, int tastType) {
        TVehicleTaskStatus tVehicleTaskStatus = new TVehicleTaskStatus();
        String ddCode = OrderCodeUtils.getCoder();//调度单号
        //获取产品信息
        TProduct product = tProductMapper.selectTProductById(productId);
        List< TProductSite > productSiteList = product.getProductSiteList();
        TSiteInfo firstSiteInfo = productSiteList.get(0).getSiteInfo();//第一个站点
        TSiteInfo lastSiteInfo = productSiteList.get(productSiteList.size() - 1).getSiteInfo();//最后一个站点

        tVehicleTaskStatus.setTaskStartTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", useCarStartTime));
        tVehicleTaskStatus.setTaskEndTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", useCarEndTime));
        //任务出发地id
        tVehicleTaskStatus.setTaskStartSite(String.valueOf(firstSiteInfo.getSiteId()));
        //任务出发地名称
        tVehicleTaskStatus.setTaskStartSiteName(firstSiteInfo.getSiteName());
        //任务目的地id
        tVehicleTaskStatus.setTaskEndSite(String.valueOf(lastSiteInfo.getSiteId()));
        //任务目的地名称
        tVehicleTaskStatus.setTaskEndSiteName(lastSiteInfo.getSiteName());
        //调度单号
        tVehicleTaskStatus.setDispatchOrdercode(ddCode);
        //产品名称
        tVehicleTaskStatus.setProductName(product.getProductName());
        tVehicleTaskStatus.setProductId(productId);
        tVehicleTaskStatus.setTaskType(tastType);
        tVehicleTaskStatus.setCreateTime(DateUtils.getNowDate());
        tVehicleTaskStatusMapper.insertTVehicleTaskStatus(tVehicleTaskStatus);
        return ddCode;
    }

    /**
     * 调度单添加司机和车辆
     * @param ddCode       调度单号
     * @param vehicleId    车辆id
     * @param passengerNum 用车人数
     * @param driverId     司机id
     * @return ddCode 调度单号
     */
    public String insertVehicleAndDriver(
            String ddCode, String vehicleId, int passengerNum, String driverId) {
        TVehicleTaskStatus tVehicleTaskStatus = tVehicleTaskStatusMapper.selectTVehicleTaskStatusByDispatchOrdercode(ddCode);

        //获取车辆信息
        TVehicleInfo vehicleInfo = vehicleInfoMapper.selectTVehicleInfoById(vehicleId);
        TVehicleType vehicleType = vehicleInfo.getVehicleType();//车辆类型
        //车辆乘客座位数
        tVehicleTaskStatus.setPassengerSeatNum(vehicleType.getPassengerNum().intValue());
        //车辆剩余乘客座位数
        tVehicleTaskStatus.setRemainingSeatNum(vehicleType.getPassengerNum().intValue() - passengerNum);
        tVehicleTaskStatus.setVehicleInfoId(vehicleId);
        tVehicleTaskStatus.setVehiclePlateNo(vehicleInfo.getLicenseTagno());
        if (StringUtils.isNotEmpty(driverId)) {
            TDriverInfo driverInfo = driverInfoService.selectTDriverInfoById(driverId);
            tVehicleTaskStatus.setDriverId(driverId);
            tVehicleTaskStatus.setDriverName(driverInfo.getName());
            tVehicleTaskStatus.setDriverPhone(driverInfo.getPhone());
        } else {
            tVehicleTaskStatus.setDriverId(vehicleInfo.getDriverId());
            tVehicleTaskStatus.setDriverName(vehicleInfo.getDriverName());
            tVehicleTaskStatus.setDriverPhone(vehicleInfo.getDriverPhone());
        }
        tVehicleTaskStatus.setNinPinNum(vehicleType.getPincheMin());
        int tastType = tVehicleTaskStatus.getTaskType();
        if (tastType == 3 && vehicleType.getPincheMin() <= passengerNum) {
            tVehicleTaskStatus.setNoCancel("1");//拼座车辆达到最低拼团人数，不可取消班次，不可退单
        }
        tVehicleTaskStatusMapper.updateTVehicleTaskStatus(tVehicleTaskStatus);
        //添加公交调度计划
        /*try {
            if (StringUtils.isNotEmpty(vehicleInfo.getGjCarId())) {
                String gjDdId = addGj(tVehicleTaskStatus.getTaskStartTime(), tVehicleTaskStatus.getTaskEndTime(), vehicleInfo.getGjCarId());
                tVehicleTaskStatus.setGjDdId(gjDdId);
                tVehicleTaskStatusMapper.updateTVehicleTaskStatus(tVehicleTaskStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return ddCode;
    }

    public String insertTVehicleTaskStatus(
            Long productId, String vehicleId, int passengerNum, String useCarStartTime, String useCarEndTime, int tastType, String driverId) {
        TVehicleTaskStatus tVehicleTaskStatus = new TVehicleTaskStatus();
        String ddCode = OrderCodeUtils.getCoder();//调度单号
        //获取产品信息
        TProduct product = tProductMapper.selectTProductById(productId);
        List< TProductSite > productSiteList = product.getProductSiteList();
        TSiteInfo firstSiteInfo = productSiteList.get(0).getSiteInfo();//第一个站点
        TSiteInfo lastSiteInfo = productSiteList.get(productSiteList.size() - 1).getSiteInfo();//最后一个站点
        //获取车辆信息
        TVehicleInfo vehicleInfo = vehicleInfoMapper.selectTVehicleInfoById(vehicleId);
        TVehicleType vehicleType = vehicleInfo.getVehicleType();//车辆类型
        //车辆乘客座位数
        tVehicleTaskStatus.setPassengerSeatNum(vehicleType.getPassengerNum().intValue());
        //车辆剩余乘客座位数
        tVehicleTaskStatus.setRemainingSeatNum(vehicleType.getPassengerNum().intValue() - passengerNum);
        tVehicleTaskStatus.setVehicleInfoId(vehicleId);
        tVehicleTaskStatus.setVehiclePlateNo(vehicleInfo.getLicenseTagno());
        if (StringUtils.isNotEmpty(driverId)) {
            TDriverInfo driverInfo = driverInfoService.selectTDriverInfoById(driverId);
            tVehicleTaskStatus.setDriverId(driverId);
            tVehicleTaskStatus.setDriverName(driverInfo.getName());
            tVehicleTaskStatus.setDriverPhone(driverInfo.getPhone());
        } else {
            tVehicleTaskStatus.setDriverId(vehicleInfo.getDriverId());
            tVehicleTaskStatus.setDriverName(vehicleInfo.getDriverName());
            tVehicleTaskStatus.setDriverPhone(vehicleInfo.getDriverPhone());
        }
        tVehicleTaskStatus.setTaskStartTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", useCarStartTime));
        tVehicleTaskStatus.setTaskEndTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", useCarEndTime));
        //任务出发地id
        tVehicleTaskStatus.setTaskStartSite(String.valueOf(firstSiteInfo.getSiteId()));
        //任务出发地名称
        tVehicleTaskStatus.setTaskStartSiteName(firstSiteInfo.getSiteName());
        //任务目的地id
        tVehicleTaskStatus.setTaskEndSite(String.valueOf(lastSiteInfo.getSiteId()));
        //任务目的地名称
        tVehicleTaskStatus.setTaskEndSiteName(lastSiteInfo.getSiteName());
        //调度单号
        tVehicleTaskStatus.setDispatchOrdercode(ddCode);
        //产品名称
        tVehicleTaskStatus.setProductName(product.getProductName());
        tVehicleTaskStatus.setProductId(productId);
        tVehicleTaskStatus.setTaskType(tastType);
        tVehicleTaskStatus.setCreateTime(DateUtils.getNowDate());
        tVehicleTaskStatus.setNinPinNum(vehicleType.getPincheMin());
        if (tastType == 3 && vehicleType.getPincheMin() <= passengerNum) {
            tVehicleTaskStatus.setNoCancel("1");//拼座车辆达到最低拼团人数，不可取消班次，不可退单
        }
        tVehicleTaskStatusMapper.insertTVehicleTaskStatus(tVehicleTaskStatus);
        //添加公交调度计划
        /*try {
            if (StringUtils.isNotEmpty(vehicleInfo.getGjCarId())) {
                String gjDdId = addGj(tVehicleTaskStatus.getTaskStartTime(), tVehicleTaskStatus.getTaskEndTime(), vehicleInfo.getGjCarId());
                tVehicleTaskStatus.setGjDdId(gjDdId);
                tVehicleTaskStatusMapper.updateTVehicleTaskStatus(tVehicleTaskStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return ddCode;
    }


    /**
     * 添加:发班30分钟内不可添加，座位数不够不可添加
     * 退单：已成班拼座订单不可退，未成班拼座订单可全额退
     * 已有拼座的拼车订单不可退
     * <p>
     * 修改已有的调度班次信息（加入已有的班次，退票，修改剩余座位数）
     * @param dispatchOrdercode 调度号（空，未成班退单）
     * @param passengerNum      添加拼车人数（正数），退票（负数）
     * @param orderType         操作订单类型 3：机场巴士拼座订单  4：拼车订单  5:机场巴士包车订单，6定制客运拼座，7定制客运包车 8自由包车
     * @return
     */
    public Map updateTVehicleTaskStatus(
            String dispatchOrdercode, int passengerNum, int orderType) {
        TVehicleTaskStatus vehicleTaskStatus = tVehicleTaskStatusMapper.selectTVehicleTaskStatusByDispatchOrdercode(dispatchOrdercode);
        //发班时间
        Date taskStartTime = vehicleTaskStatus.getTaskStartTime();
        //当前时间与发车时间相差多少分钟
        long difMin = (taskStartTime.getTime() - System.currentTimeMillis()) / 1000 / 60;
        Map resultMap = getRefundRate(dispatchOrdercode, orderType);

        if(orderType==3||orderType==6){
            orderType =1; //拼车
        }else{
            orderType =2;//包车
        }
        if (passengerNum > 0) {
            if (vehicleTaskStatus.getTaskStatus() != 1
                    && vehicleTaskStatus.getTaskStatus() != 2) {
                resultMap.put("code", 500);
                resultMap.put("refundRate", 0);
                resultMap.put("busIsCancel", 0);
                resultMap.put("msg", "非待班班次，不可添加");
                return resultMap;
            }
            if (difMin <= 30) {
                resultMap.put("code", 500);
                resultMap.put("refundRate", 0);
                resultMap.put("busIsCancel", 0);
                resultMap.put("msg", "发车前30分钟不可加入班次");
                return resultMap;
            } else {
                if (orderType==1) {
                    //剩余座位数
                    int remainingSeatNum = vehicleTaskStatus.getRemainingSeatNum() - passengerNum;
                    if (remainingSeatNum < 0) {
                        resultMap.put("code", 500);
                        resultMap.put("refundRate", 0);
                        resultMap.put("busIsCancel", 0);
                        resultMap.put("msg", "班次剩余座位数不够，添加失败");
                        return resultMap;
                    } else {
                        vehicleTaskStatus.setRemainingSeatNum(remainingSeatNum);
                        tVehicleTaskStatusMapper.updateTVehicleTaskStatus(vehicleTaskStatus);
                        resultMap.put("code", 200);
                        resultMap.put("refundRate", 0);
                        resultMap.put("busIsCancel", 0);
                        resultMap.put("msg", "添加到班次成功");
                        return resultMap;
                    }
                } else {
                    resultMap.put("code", 500);
                    resultMap.put("refundRate", 0);
                    resultMap.put("busIsCancel", 0);
                    resultMap.put("msg", "非拼座班次不可添加订单");
                    return resultMap;
                }
            }
        } else {
            //退单
            if (resultMap.get("code").toString().equals("200")) {
                //包车取消班次
                if (orderType==2) {
                    vehicleTaskStatus.setTaskStatus(5);
                    tVehicleTaskStatusMapper.updateTVehicleTaskStatus(vehicleTaskStatus);
                    resultMap.put("busIsCancel", 1);
                    resultMap.put("msg", "取消班次成功");
                    //cancelGj(vehicleTaskStatus.getGjDdId());
                    return resultMap;
                }
                //拼座取消班次
                else if (orderType==1) {
                    int remainingSeatNum = vehicleTaskStatus.getRemainingSeatNum() - passengerNum;
                    vehicleTaskStatus.setRemainingSeatNum(remainingSeatNum);
                    //剩余座位数和最大承载数一样，没有订单了，取消班次
                    if (remainingSeatNum >= vehicleTaskStatus.getPassengerSeatNum()) {
                        vehicleTaskStatus.setTaskStatus(5);//取消班次
                        resultMap.put("busIsCancel", 1);
                        //cancelGj(vehicleTaskStatus.getGjDdId());
                    } else {
                        resultMap.put("busIsCancel", 0);
                    }
                    tVehicleTaskStatusMapper.updateTVehicleTaskStatus(vehicleTaskStatus);
                    resultMap.put("msg", "成功");
                    return resultMap;
                }
            } else {
                resultMap.put("busIsCancel", 0);
                return resultMap;
            }
        }
        resultMap.put("code", 500);
        resultMap.put("refundRate", 0);
        resultMap.put("busIsCancel", 0);
        resultMap.put("msg", "数据有误");
        return resultMap;
    }

    /**
     * @param dispatchOrdercode
     * @param orderType         3：机场巴士拼座订单  4：拼车订单  5:机场巴士包车订单，6定制客运拼座，7定制客运包车 8自由包车
     * @return
     */
    public Map getRefundRate(String dispatchOrdercode, int orderType) {
        if(orderType==3||orderType==6){
            orderType =1; //拼车
        }else{
            orderType =2;//包车
        }

        Map resultMap = new HashMap();
        List< TOrderRefundRules > orderRefundRulesList = orderRefundRulesMapper.selectTOrderRefundRulesList(null);
        //拼座订单,没有班次号，没有成班，全额退款
        if (StringUtils.isEmpty(dispatchOrdercode)) {
            resultMap.put("code", 200);
            resultMap.put("refundRate", 1);
            resultMap.put("msg", "");
            return resultMap;
        } else {
            TVehicleTaskStatus vehicleTaskStatus = tVehicleTaskStatusMapper.selectTVehicleTaskStatusByDispatchOrdercode(dispatchOrdercode);
            //发班时间
            Date taskStartTime = vehicleTaskStatus.getTaskStartTime();
            //当前时间与发车时间相差多少分钟
            long difMin = (taskStartTime.getTime() - System.currentTimeMillis()) / 1000 / 60;
            if (difMin < 0) {
                resultMap.put("code", 500);
                resultMap.put("refundRate", 0);
                resultMap.put("msg", "已过发班时间，不可退款");
                return resultMap;
            }
            double refundRate = 1;
            long timeBefore = 0;
            for (TOrderRefundRules tOrderRefundRules : orderRefundRulesList) {
                if (tOrderRefundRules.getOrderType() == orderType
                        && tOrderRefundRules.getTimeBefore() >= difMin) {
                    timeBefore = tOrderRefundRules.getTimeBefore();
                    refundRate = tOrderRefundRules.getRefundRate();
                    resultMap.put("refundRate", refundRate);
                    break;
                }
            }
            if (vehicleTaskStatus.getTaskStatus() == 5) {
                resultMap.put("code", 500);
                resultMap.put("refundRate", 0);
                resultMap.put("msg", "班次已取消，不可操作");
                return resultMap;
            }
            //退
            //退款率0，一分钱不退，不可退款
            if (refundRate == 0) {
                resultMap.put("code", 500);
                resultMap.put("refundRate", 0);
                resultMap.put("msg", "发车前" + timeBefore + "分钟内收取全额手续费，不可退款");
                return resultMap;
            }
            resultMap.put("code", 200);
            resultMap.put("refundRate", refundRate);
            resultMap.put("msg", "");
            //cancelGj(vehicleTaskStatus.getGjDdId());
            return resultMap;

        }
    }

    /**
     * 选择符合条件已排班的车辆（筛选已有班次发车时间满足用户上车时间前后半个小时）
     * @param productId
     * @param passengerNum
     * @param useCarStartTime
     * @return
     */
    public List< TVehicleTaskStatus > selectTVehicleTask(Long productId, Integer passengerNum, String useCarStartTime) {
        return tVehicleTaskStatusMapper.selectTVehicleTask(productId.toString(), passengerNum.toString(), useCarStartTime);
    }


    /**
     * 修改车辆运行记录
     * @param tVehicleTaskStatus 车辆运行记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTVehicleTaskStatus(TVehicleTaskStatus tVehicleTaskStatus) {
        TVehicleTaskStatus vehicleTaskStatus1 = tVehicleTaskStatusMapper.selectTVehicleTaskStatusById(tVehicleTaskStatus.getVehicleTaskStatusId().toString());
        int result = tVehicleTaskStatusMapper.updateTVehicleTaskStatus(tVehicleTaskStatus);
        if (StringUtils.isNotEmpty(tVehicleTaskStatus.getDriverId())
                || StringUtils.isNotEmpty(tVehicleTaskStatus.getVehicleInfoId())) {
            //修改订单数据
            tTaxiOrderService.updateOrderByBusId(vehicleTaskStatus1.getDispatchOrdercode());
            //修改公交数据
            /*if (StringUtils.isNotEmpty(tVehicleTaskStatus.getVehicleInfoId())) {
                TVehicleInfo oldCar = vehicleInfoMapper.selectTVehicleInfoById(vehicleTaskStatus1.getVehicleInfoId());
                TVehicleInfo newCar = vehicleInfoMapper.selectTVehicleInfoById(tVehicleTaskStatus.getVehicleInfoId());
                try {
                    if (StringUtils.isNotEmpty(oldCar.getGjCarId())) {
                        //删除原公交班次
                        cancelGj(vehicleTaskStatus1.getGjDdId());
                    }
                    if (StringUtils.isNotEmpty(newCar.getGjCarId())) {
                        String gjDdId = addGj(vehicleTaskStatus1.getTaskStartTime(), vehicleTaskStatus1.getTaskEndTime(), newCar.getGjCarId());
                        tVehicleTaskStatus.setGjDdId(gjDdId);
                        tVehicleTaskStatusMapper.updateTVehicleTaskStatus(tVehicleTaskStatus);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        }
        //修改司机通知消息
        if (StringUtils.isNotEmpty(tVehicleTaskStatus.getDriverId())
                && StringUtils.isNotEmpty(String.valueOf(tVehicleTaskStatus.getVehicleTaskStatusId()))) {
            TDriverInfo driverInfo = driverInfoService.selectTDriverInfoById(tVehicleTaskStatus.getDriverId());
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Map param = new HashMap();
//                    param.put("busId", vehicleTaskStatus1.getDispatchOrdercode());
//                    param.put("busDate", vehicleTaskStatus1.getTaskStartTime());
//                    param.put("openid", driverInfo.getfOpenId());
//                    param.put("busType", "新的班次");
                    new TRentalOrderController().sendMessage(driverInfo.getDriverId(), "你收到一笔新的工单!");
                }
            }).start();
        }
        return result;
    }

    /**
     * 批量删除车辆运行记录
     * @param vehicleTaskStatusIds 需要删除的车辆运行记录ID
     * @return 结果
     */
    @Override
    public int deleteTVehicleTaskStatusByIds(String[] vehicleTaskStatusIds) {
        return tVehicleTaskStatusMapper.deleteTVehicleTaskStatusByIds(vehicleTaskStatusIds);
    }

    /**
     * 删除车辆运行记录信息
     * @param vehicleTaskStatusId 车辆运行记录ID
     * @return 结果
     */
    @Override
    public int deleteTVehicleTaskStatusById(String vehicleTaskStatusId) {
        return tVehicleTaskStatusMapper.deleteTVehicleTaskStatusById(vehicleTaskStatusId);
    }

    @Override
    public TVehicleTaskStatus selectTVehicleTaskStatusByDispatchOrdercode(String dispatchOrdercode) {
        return tVehicleTaskStatusMapper.selectTVehicleTaskStatusByDispatchOrdercode(dispatchOrdercode);
    }


    /**
     * 获取班次运行记录表
     * @param tVehicleTaskStatus
     * @return
     */
    @Override
    public List< TVehicleTaskStatus > selectTVehicleTaskStatusList1(TVehicleTaskStatus tVehicleTaskStatus) {
        return tVehicleTaskStatusMapper.selectTVehicleTaskStatusList1(tVehicleTaskStatus);
    }

/*
    //新增公交任务
    public String addGj(Date startTime, Date endTime, String gjCarId) {
        try {
            //添加公交班次
            JSONObject param = new JSONObject();
            param.put("startTime", DateUtils.getTime(startTime));
            param.put("endTime", DateUtils.getTime(endTime));
            param.put("objectID", gjCarId);
            String gjResult = HttpUtils.httpURLConnectionPOST(gjUrl + "pickRide/savePickRideTime", param);
            JSONObject data = (JSONObject) JSONObject.parseObject(gjResult).get("data");
            return data.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //取消公交任务
    public void cancelGj(String id) {
        try {
            if (StringUtils.isNotEmpty(id)) {
                //删除原公交班次
                JSONObject param = new JSONObject();
                param.put("id", id);
                HttpUtils.httpURLConnectionPOST(gjUrl + "/pickRide/deletePickRideTime", param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}