package com.carservice.project.miniProgram.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.carservice.common.utils.MyMD5Util;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.project.oper.domain.TDriverInfo;
import com.carservice.project.oper.domain.TProductSite;
import com.carservice.project.oper.domain.TVehicleInfo;
import com.carservice.project.oper.domain.TVehicleTaskStatus;
import com.carservice.project.oper.service.ITProductPriceService;
import com.carservice.project.oper.service.ITProductService;
import com.carservice.project.oper.service.ITSiteInfoService;
import com.carservice.project.oper.service.impl.*;
import com.carservice.project.order.controller.TRentalOrderController;
import com.carservice.project.order.domain.TRentalcarsMessage;
import com.carservice.project.order.domain.TTaxiOrder;
import com.carservice.project.order.service.ITRentalcarsMessageService;
import com.carservice.project.order.service.impl.TTaxiOrderServiceImpl;
import com.carservice.project.system.service.impl.SysDictDataServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hxx
 * @version 1.0
 * @title: OrderController
 * @projectName rentalcars
 * @description: TODO
 * @date 2020/8/14 10:22
 */
@CrossOrigin // 解决跨域
@RestController
@RequestMapping("/miniProgram/order")
public class OrderController {
    @Autowired
    private ITProductService tProductService;

    @Autowired
    private ITSiteInfoService tSiteInfoService;

    @Autowired
    private TTaxiOrderServiceImpl tTaxiOrderService;

    @Autowired
    private ITProductPriceService itProductPriceService;

    @Autowired
    private ITRentalcarsMessageService tRentalcarsMessageService;

    @Autowired
    private TVehicleTaskStatusServiceImpl vehicleTaskStatusService;

    @Autowired
    private SysDictDataServiceImpl sysDictDataService;

    @Autowired
    private TDriverInfoServiceImpl driverInfoService;

    @Autowired
    private ErpDriverInfoServiceImpl erpDriverInfoService;

    @Autowired
    private TRentalOrderController tRentalOrderController;

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private TVehicleInfoServiceImpl tVehicleInfoService;

    @Autowired
    private MyMD5Util myMd5Util;

    @Autowired
    private TProductSiteServiceImpl tProductSiteService;

    @RequestMapping("/addOrder")
    public AjaxResult addOrder(@RequestBody TTaxiOrder tTaxiOrder) {
        List< TVehicleInfo > vehicleInfoList = new ArrayList<>();
        //查询是否有可用车辆
//        if (tTaxiOrder.getBusId() == null || tTaxiOrder.getBusId().equals("")) {
//            TVehicleInfo tVehicleInfo = new TVehicleInfo();
//            tVehicleInfo.setUseCarStartTime(tTaxiOrder.getBeginTime());
//            tVehicleInfo.setUseCarEndTime(tTaxiOrder.getEndTime());
//            tVehicleInfo.setPassengerNum(tTaxiOrder.getOrderType().equals("3") ? tTaxiOrder.getSeatNum() : 1);
//            if (!tTaxiOrder.getOrderType().equals("3")) {
//                tVehicleInfo.setType("");
//            } else {
//                tVehicleInfo.setType(tTaxiOrder.getType());
//            }
//            vehicleInfoList = tVehicleInfoService.selectCanUseTVehicleInfoListFormini(tVehicleInfo);
//            if (vehicleInfoList == null || vehicleInfoList.size() == 0) {
//                return AjaxResult.error("没有可用车辆,订单新增失败");
//            }
//        }
        GlobalCoordinates driver = null;
        if (tTaxiOrderService.insertTTaxiOrder(tTaxiOrder) > 0) {
            List< TRentalcarsMessage > passengerList = tTaxiOrder.getPassengerList();
            if (tTaxiOrder.getOrderType().equals("5") || tTaxiOrder.getOrderType().equals("7") || tTaxiOrder.getOrderType().equals("8")) {
                TRentalcarsMessage btRentalcarsMessage = new TRentalcarsMessage();
                btRentalcarsMessage.setBeginStation(tTaxiOrder.getBeginStation());
                btRentalcarsMessage.setEndStation(tTaxiOrder.getEndStation());
                btRentalcarsMessage.setLatitude(tTaxiOrder.getLatitude());
                btRentalcarsMessage.setLongitude(tTaxiOrder.getLongitude());
                btRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
                btRentalcarsMessage.setOrderType(tTaxiOrder.getOrderType());
                btRentalcarsMessage.setReserveMobile(tTaxiOrder.getReserveMobile());
                btRentalcarsMessage.setReserveName(tTaxiOrder.getReserveName());
                btRentalcarsMessage.setUseTime(tTaxiOrder.getUseTime());
                btRentalcarsMessage.setSiteType(1);
                btRentalcarsMessage.setReceptionNum("1");
                btRentalcarsMessage.setReceptionTime(tTaxiOrder.getUseTime());
                TRentalcarsMessage etRentalcarsMessage = new TRentalcarsMessage();
                etRentalcarsMessage.setBeginStation(tTaxiOrder.getEndStation());
                etRentalcarsMessage.setEndStation(tTaxiOrder.getEndStation());
                etRentalcarsMessage.setLatitude(tTaxiOrder.getEndLat());
                etRentalcarsMessage.setLongitude(tTaxiOrder.getEndLon());
                etRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
                etRentalcarsMessage.setOrderType(tTaxiOrder.getOrderType());
                etRentalcarsMessage.setReserveMobile(tTaxiOrder.getReserveMobile());
                etRentalcarsMessage.setReserveName(tTaxiOrder.getReserveName());
                etRentalcarsMessage.setUseTime(tTaxiOrder.getUseTime());
                etRentalcarsMessage.setSiteType(2);
                tRentalcarsMessageService.insertTRentalcarsMessage(btRentalcarsMessage);
                tRentalcarsMessageService.insertTRentalcarsMessage(etRentalcarsMessage);
            }
            int i = 1;
            if (passengerList != null && passengerList.size() > 0) {
                for (TRentalcarsMessage tRentalcarsMessage : passengerList) {
                    tRentalcarsMessage.setBeginStation(tRentalcarsMessage.getBeginStation());
                    tRentalcarsMessage.setEndStation(tTaxiOrder.getEndStation());
                    tRentalcarsMessage.setLatitude(tRentalcarsMessage.getLatitude());
                    tRentalcarsMessage.setLongitude(tRentalcarsMessage.getLongitude());
                    tRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
                    tRentalcarsMessage.setOrderType(tTaxiOrder.getOrderType());
                    tRentalcarsMessage.setReserveMobile(tTaxiOrder.getReserveMobile());
                    tRentalcarsMessage.setReserveName(tTaxiOrder.getReserveName());
                    tRentalcarsMessage.setUseTime(tTaxiOrder.getUseTime());
                    tRentalcarsMessage.setSiteType(tRentalcarsMessage.getSiteType() + "" == null ? 1 : tRentalcarsMessage.getSiteType());
                    if (tTaxiOrder.getOrderType().equals("3") || tTaxiOrder.getOrderType().equals("6")) {
                        tRentalcarsMessage.setPassengerNum(tTaxiOrder.getSeatNum());
                        driver =
                                new GlobalCoordinates(Double.valueOf(tRentalcarsMessage.getLatitude()), Double.valueOf(tRentalcarsMessage.getLongitude()));
                    } else if (tTaxiOrder.getOrderType().equals("5") || tTaxiOrder.getOrderType().equals("7") || tTaxiOrder.getOrderType().equals("8")) {
                        i++;
                        tRentalcarsMessage.setReceptionNum(i + "");
                        tRentalcarsMessage.setReceptionTime(tTaxiOrder.getUseTime());
                    }
                    i++;
                    tRentalcarsMessageService.insertTRentalcarsMessage(tRentalcarsMessage);
                }
            }
        }
        //已选择班次直接添加班次信息
        boolean flag = false;
        if (tTaxiOrder.getBusId() != null && !tTaxiOrder.getBusId().equals("")) {
            TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(tTaxiOrder.getBusId());
            int passengerNum = 0;
            if (tTaxiOrder.getOrderType().equals("3") || tTaxiOrder.getOrderType().equals("6")) {
                passengerNum += tTaxiOrder.getSeatNum();
            }
            try {
                Map result = vehicleTaskStatusService.updateTVehicleTaskStatus(tTaxiOrder.getBusId(), passengerNum, 3);
                if (result.get("code").toString().equals("200")) {
                    tTaxiOrder.setIds(tTaxiOrder.getId() + "");
                    tTaxiOrderService.updateOrderBusId(tTaxiOrder.getBusId(), tVehicleTaskStatus.getDriverId(), tVehicleTaskStatus.getVehiclePlateNo(), tVehicleTaskStatus.getDriverName(), tVehicleTaskStatus.getDriverPhone(), tTaxiOrder.getIds());
                    TRentalcarsMessage rentalcarsMessage = tRentalcarsMessageService.selectMaxtTRentalcarsMessageById(tTaxiOrder.getBusId());
                    GlobalCoordinates user =
                            new GlobalCoordinates(
                                    Double.valueOf(rentalcarsMessage.getLatitude()), Double.valueOf(rentalcarsMessage.getLongitude()));
                    BigDecimal meter1 = new BigDecimal(getDistanceMeter(user, driver, Ellipsoid.Sphere));
                    BigDecimal hs = meter1.divide(new BigDecimal("6400")).multiply(new BigDecimal("3600000")).setScale(2, BigDecimal.ROUND_HALF_UP);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = df.parse(rentalcarsMessage.getReceptionTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date.setTime(date.getTime() + hs.longValue());
                    tRentalcarsMessageService.updateBusIdByIds(tTaxiOrder.getBusId(),
                            Integer.parseInt(rentalcarsMessage.getReceptionNum()) + 1 + "", df.format(date),
                            tTaxiOrder.getIds());
                    if (!tVehicleTaskStatus.getDriverId().equals("")) {
                        TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
                        tRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
                        tRentalcarsMessage.setSiteType(1);
                        List< TRentalcarsMessage > tRentalcarsMessages = tRentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
                        tRentalcarsMessage = tRentalcarsMessages.get(0);
                        String account = "dh24733"; // 用户名（必填）
                        String password = "tB1eDOsV"; // 密码（必填,明文）
                        String mobile = tTaxiOrder.getReserveMobile(); // 手机号码（必填,多条以英文逗号隔开）
                        String sign = "【运泰出行】"; // 短信签名（必填）
                        String content =
                                "您购买的"
                                        + tTaxiOrder.getUseTime()
                                        + tRentalcarsMessage.getBeginStation()
                                        + "-"
                                        + tRentalcarsMessage.getEndStation()
//                                        + "订单已生成班次，班次号为"
//                                        + tRentalcarsMessage.getBusId()
//                                        + ",司机预估接送时间为"
//                                        + tRentalcarsMessage.getReceptionTime()
//                                        + ".请安排好出行时间，如有疑问请拨打：0553-3911111。";
                                        + "订单已生成班次，接送车车牌号为"
                                        + tVehicleTaskStatus.getVehiclePlateNo()
                                        + ",接送司机手机号为"
                                        + tVehicleTaskStatus.getDriverPhone()
                                        + ",司机预估接送时间为"
                                        + tRentalcarsMessage.getReceptionTime()
                                        + ".请安排好出行时间，如有疑问请拨打：0553-3911111。";
                        JSONObject paramData = new JSONObject();
                        paramData.put("account", account);
                        try {
                            paramData.put("password", myMd5Util.MD5(password).toLowerCase());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        paramData.put("phones", mobile);
                        paramData.put("sign", sign);
                        paramData.put("content", content);
                        // 解析json
                        JSONObject smsjsonObject = null;
                        try {
                            smsjsonObject =
                                    (JSONObject)
                                            JSONObject.parse(
                                                    httpUtils.httpURLConnectionPOST(
                                                            "http://www.dh3t.com/json/sms/Submit", paramData));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    flag = true;
                } else {
                    flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        } else {
            flag = true;
        }
//        AjaxResult ajax = AjaxResult.success();
        if (flag) {
            return AjaxResult.success("订单新增成功");
        } else {
            return AjaxResult.error("班次添加失败,订单新增失败");
        }
    }

    public static double getDistanceMeter(
            GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {

        // 创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve =
                new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    @RequestMapping("/updateOrderStatus")
    public AjaxResult updateOrderStatus(String orderCode) {
        if (tTaxiOrderService.updateOrderStatus(orderCode) > 0) {
            return AjaxResult.success("订单状态更新成功");
        } else {
            return AjaxResult.error("订单状态更新失败");
        }
    }

    @RequestMapping("/refundOrder")
    public AjaxResult refundOrder(@RequestBody TTaxiOrder tTaxiOrder) {
        TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderByOrderCode(tTaxiOrder.getOrderCode());
        if (!order.getBusId().equals("")) {
            tTaxiOrder.setPassengerNum(-1);
            Map result = vehicleTaskStatusService.updateTVehicleTaskStatus(order.getBusId(), tTaxiOrder.getPassengerNum(), Integer.parseInt(order.getOrderType()));
            JSONObject jsonObject = new JSONObject();
            if (result.get("code").toString().equals("200")) {
                jsonObject.put("refundRate", result.get("refundRate"));
                if (result.get("busIsCancel").toString().equals("1")) {
                    TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(order.getBusId());
                    TDriverInfo tDriverInfo = driverInfoService.selectTDriverInfoById(tVehicleTaskStatus.getDriverId());
//                    Map map = new HashMap();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(tVehicleTaskStatus.getTaskStartTime());
//                    map.put("busId", tVehicleTaskStatus.getDispatchOrdercode());
//                    map.put("busDate", dateString);
//                    map.put("openid", erpDriverInfo.getfOpenId());
//                    map.put("busType", "取消班次");
//                    tRentalOrderController.sendMessage(map);
                    if (tTaxiOrder.getOrderType().equals("3") || tTaxiOrder.getOrderType().equals("6")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                TProductSite tProductSite = new TProductSite();
                                tProductSite.settProductId(Long.valueOf(order.getProductCode()));
                                List< TProductSite > productSites = tProductSiteService.selectTProductSiteList(tProductSite);
                                String beginLat = productSites.get(0).getSiteInfo().getLat();
                                String beginLng = productSites.get(0).getSiteInfo().getLng();
                                TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
                                tRentalcarsMessage.setBusId(order.getBusId());
                                tRentalcarsMessage.setSiteType(1);
                                List< TRentalcarsMessage > rentalcarsMessageList = tRentalcarsMessageService.selectTRentalcarsMessageByBusId(tRentalcarsMessage);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                tRentalOrderController.countDistance(rentalcarsMessageList, beginLat, beginLng, formatter.format(tVehicleTaskStatus.getTaskStartTime()), 1);
                                new TRentalOrderController().sendMessage(tDriverInfo.getDriverId(), "班次号为" + tVehicleTaskStatus.getDispatchOrdercode() + "的班次乘客信息发生变动,请合理安排接客时间!");
                            }
                        }).start();
                    }
                    order.setOrderStatus(2);
                    order.setBusId("");
                    order.setDriverId("");
                    order.setDriverCarNo("");
                    order.setDriverName("");
                    order.setDriverPhone("");
                    tTaxiOrderService.updateTTaxiOrder(order);
                    tRentalcarsMessageService.updateBusIdByIds("", "", "", tTaxiOrder.getOrderCode());
                } else {
                    if (tTaxiOrder.getOrderType().equals("3") || tTaxiOrder.getOrderType().equals("6")) {
                        tRentalcarsMessageService.updatePassengerNum(1, tTaxiOrder.getOrderCode());
                    } else if (order.getOrderType().equals("4")) {
                        tRentalcarsMessageService.updatePassengerNum(order.getUseNumber(), tTaxiOrder.getOrderCode());
                    } else {
//                    tTaxiOrderService.updateOrderBusId(tTaxiOrder.getBusId(), "", "", "", "", tTaxiOrder.getIds());
                        tRentalcarsMessageService.updateBusIdByIds(tTaxiOrder.getBusId(), "", "", tTaxiOrder.getOrderCode());
                    }
                }
                return AjaxResult.success(jsonObject);
            } else {
                return AjaxResult.error(result.get("msg").toString());
            }
        } else {
            Map result = vehicleTaskStatusService.updateTVehicleTaskStatus("", tTaxiOrder.getPassengerNum(), Integer.parseInt(tTaxiOrder.getOrderType()));
            JSONObject jsonObject = new JSONObject();
            if (result.get("code").toString().equals("200")) {
                jsonObject.put("refundRate", result.get("refundRate"));
            }
            return AjaxResult.success(jsonObject);
        }
    }

    @RequestMapping("/getRefundRate")
    public AjaxResult getRefundRate(@RequestBody TTaxiOrder tTaxiOrder) {
        TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderByOrderCode(tTaxiOrder.getOrderCode());
        Map result = vehicleTaskStatusService.getRefundRate(order.getBusId(), Integer.parseInt(order.getOrderType()));
        JSONObject jsonObject = new JSONObject();
        if (result.get("code").toString().equals("200")) {
            jsonObject.put("refundRate", result.get("refundRate"));
            return AjaxResult.success(jsonObject);
        } else {
            return AjaxResult.error(result.get("msg").toString());
        }
    }

    @RequestMapping("/getOrderList")
    public AjaxResult getOrderList(@RequestBody TTaxiOrder tTaxiOrder) {
        List< TTaxiOrder > list = tTaxiOrderService.selectTRentalOrderList(tTaxiOrder);
        for (TTaxiOrder taxiOrder : list) {
            if (taxiOrder.getBusId() != null) {
                TVehicleTaskStatus vehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(taxiOrder.getBusId());
                if (vehicleTaskStatus.getTaskStatus() == 3 || vehicleTaskStatus.getTaskStatus() == 4) {
                    taxiOrder.setOrderStatus(vehicleTaskStatus.getTaskStatus());
                }
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        return AjaxResult.success(jsonObject);
    }

    @RequestMapping("/getOrderDetail")
    public AjaxResult getOrderDetail(@RequestBody TTaxiOrder tTaxiOrder) {
        TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
        tRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
        tRentalcarsMessage.setSiteType(1);
        List< TRentalcarsMessage > tRentalcarsMessages = tRentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", tRentalcarsMessages);
        return AjaxResult.success(jsonObject);
    }

    @PostMapping("/getBusList")
//    public AjaxResult getBusList(String driverId, int pageNum, int pageSize) {
    public AjaxResult getBusList(@RequestBody Map< String, Object > paraMap) {
        if (paraMap.get("driverId") == null || StringUtils.isEmpty(paraMap.get("driverId").toString())) {
            return AjaxResult.error("缺少参数");
        }
        TVehicleTaskStatus tVehicleTaskStatus = new TVehicleTaskStatus();
        tVehicleTaskStatus.setPageNum(paraMap.get("pageNum") == null ? 10 : Integer.parseInt(paraMap.get("pageNum").toString()));
        tVehicleTaskStatus.setPageSize(paraMap.get("pageSize") == null ? 1 : Integer.parseInt(paraMap.get("pageSize").toString()));
        tVehicleTaskStatus.setDriverId(paraMap.get("driverId").toString());
        tVehicleTaskStatus.setTaskStatus(Integer.parseInt(paraMap.get("taskStatus").toString()));
        //设置分页规则
        PageHelper.startPage(tVehicleTaskStatus.getPageNum(), tVehicleTaskStatus.getPageSize(), "vehicle_task_status_id desc");
        PageInfo< TVehicleTaskStatus > busList = new PageInfo<>(vehicleTaskStatusService.selectTVehicleTaskStatusList1(tVehicleTaskStatus));
        if (busList.getPages() < tVehicleTaskStatus.getPageNum()) {
            busList.setList(new ArrayList<>());
        }
        return AjaxResult.success(busList);
    }

    @PostMapping("/updateBusStatus")
    public AjaxResult updateBusStatus(@RequestBody Map< String, Object > paraMap) {
        TVehicleTaskStatus vehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusById(paraMap.get("taskId").toString());
        if (Integer.parseInt(paraMap.get("taskStatus").toString()) == 2) {
            Date date = new Date();//获取当前的日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String str = df.format(date);//获取String类型的时间
            vehicleTaskStatus.setTaskCreatTime(str);
        }
        vehicleTaskStatus.setTaskStatus(Integer.parseInt(paraMap.get("taskStatus").toString()));
        if (vehicleTaskStatusService.updateTVehicleTaskStatus(vehicleTaskStatus) > 0) {
//            if (vehicleTaskStatus.getTaskType() == 4 && vehicleTaskStatus.getPzNum() > 0 && Integer.parseInt(paraMap.get("taskStatus").toString()) == 3) {
//                TTaxiOrder tTaxiOrder = tTaxiOrderService.selectTTaxiOrderByBusId(vehicleTaskStatus.getDispatchOrdercode());
//                JSONObject data = new JSONObject();
//                data.put("orderCode", tTaxiOrder.getOrderCode());
//                data.put("sellNum", vehicleTaskStatus.getPzNum());
//                data.put("refundAmount", new BigDecimal(vehicleTaskStatus.getPzNum()).multiply(new BigDecimal(tTaxiOrder.getSellPrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
//                data.put("isFee", 1);
//                data.put("refundRate", 1);
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject =
//                            (JSONObject)
//                                    JSONObject.parse(
//                                            httpUtils.httpURLConnectionPOST(
//                                                    "https://ytpw.whyuntai.com/yttrip/carOrder/refundCharterOrder", data));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (jsonObject.getString("code").equals("200")) {
//                    return AjaxResult.success("状态变更成功");
//                } else {
//                    return AjaxResult.error("状态变更失败");
//                }
//            }
            return AjaxResult.success("状态变更成功");
        } else {
            return AjaxResult.error("状态变更失败");
        }
    }

    @PostMapping("/getBusDetail")
    public AjaxResult getBusDetail(@RequestBody Map< String, Object > paraMap) {
        Map result = new HashMap<>();
        List< Map > passengerList = new ArrayList<>();
        TVehicleTaskStatus vehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusById(paraMap.get("taskId").toString());
        TTaxiOrder order = new TTaxiOrder();
        order.setBusId(vehicleTaskStatus.getDispatchOrdercode());
        List< TTaxiOrder > orderList = tTaxiOrderService.selectTRentalOrderList(order);
        result.put("busId", vehicleTaskStatus.getDispatchOrdercode());
        result.put("taskStatus", vehicleTaskStatus.getTaskStatus());
        result.put("vehicleTaskStatusId", vehicleTaskStatus.getVehicleTaskStatusId());
        result.put("routName", vehicleTaskStatus.getTaskStartSiteName() + "-" + vehicleTaskStatus.getTaskEndSiteName());
        result.put("taskName", vehicleTaskStatus.getTaskType() == 3 ? "拼座班次" : vehicleTaskStatus.getTaskType() == 4 ? "拼车班次" : "包车班次");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.put("beginTime", formatter.format(vehicleTaskStatus.getTaskStartTime()));
        result.put("endTime", formatter.format(vehicleTaskStatus.getTaskEndTime()));
        TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
        tRentalcarsMessage.setBusId(vehicleTaskStatus.getDispatchOrdercode());
        tRentalcarsMessage.setSiteType(1);
        List< TRentalcarsMessage > tRentalcarsMessages = tRentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
        tRentalcarsMessage.setSiteType(2);
        List< TRentalcarsMessage > tRentalcarsMessageList = tRentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
        String endStation = tRentalcarsMessageList.get(0).getBeginStation();
        for (TRentalcarsMessage rentalcarsMessage : tRentalcarsMessages) {
            Map map = new HashMap();
            map.put("passengerName", rentalcarsMessage.getReserveName());
            map.put("passengerPhone", rentalcarsMessage.getReserveMobile());
            map.put("useTime", rentalcarsMessage.getUseTime());
            map.put("beginStation", rentalcarsMessage.getBeginStation());
            map.put("endStation", endStation);
            map.put("latitude", rentalcarsMessage.getLatitude());
            map.put("longitude", rentalcarsMessage.getLongitude());
            map.put("receptionNum", rentalcarsMessage.getReceptionNum());
            map.put("receptionTime", rentalcarsMessage.getReceptionTime());
            if (rentalcarsMessage.getOrderType().equals("3")) {
                map.put("passengerNum", rentalcarsMessage.getPassengerNum());
            }
            passengerList.add(map);
        }
        result.put("passengerList", passengerList);
        return AjaxResult.success(result);
    }


}
