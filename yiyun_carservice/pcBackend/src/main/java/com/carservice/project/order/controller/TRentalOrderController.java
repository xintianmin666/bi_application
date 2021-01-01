package com.carservice.project.order.controller;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.MyMD5Util;
import com.carservice.common.utils.RedisUtil;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.oper.domain.*;
import com.carservice.project.oper.service.ITProductService;
import com.carservice.project.oper.service.ITVehicleInfoService;
import com.carservice.project.oper.service.ITVehicleTypeService;
import com.carservice.project.oper.service.impl.ErpDriverInfoServiceImpl;
import com.carservice.project.oper.service.impl.TDriverInfoServiceImpl;
import com.carservice.project.oper.service.impl.TProductSiteServiceImpl;
import com.carservice.project.oper.service.impl.TVehicleTaskStatusServiceImpl;
import com.carservice.project.order.constant.DataConstants;
import com.carservice.project.order.domain.TRentalcarsMessage;
import com.carservice.project.order.domain.TTaxiOrder;
import com.carservice.project.order.service.ITTaxiOrderService;
import com.carservice.project.order.service.impl.TRentalcarsMessageServiceImpl;
import com.carservice.project.order.service.impl.TTaxiOrderServiceImpl;
import com.carservice.project.system.domain.SysDept;
import com.carservice.project.system.service.ISysDeptService;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 拼包车订单Controller
 * @author carservice
 * @date 2020-07-03
 */
@RestController
@RequestMapping("/order/rentalOrder")
public class TRentalOrderController extends BaseController {
    @Autowired
    private ITTaxiOrderService tTaxiOrderService;


    @Autowired
    private ITVehicleInfoService tVehicleInfoService;

    @Autowired
    private TVehicleTaskStatusServiceImpl vehicleTaskStatusService;

    @Autowired
    private TTaxiOrderServiceImpl orderService;

    @Autowired
    private TRentalcarsMessageServiceImpl rentalcarsMessageService;

    @Autowired
    private MyMD5Util myMd5Util;

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private TDriverInfoServiceImpl driverInfoService;

    @Autowired
    private ErpDriverInfoServiceImpl erpDriverInfoService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ITVehicleTypeService tVehicleTypeService;

    @Autowired
    private ITProductService productService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TProductSiteServiceImpl tProductSiteService;


    /**
     * 查询拼包车订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTaxiOrder tTaxiOrder) {
        startPage();
        List< TTaxiOrder > list = tTaxiOrderService.selectTRentalOrderList(tTaxiOrder);
        return getDataTable(list);
    }

    /**
     * 导出拼包车订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:export')")
    @Log(title = "出租车订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTaxiOrder tTaxiOrder) {
        List< TTaxiOrder > list = tTaxiOrderService.selectTRentalOrderList(tTaxiOrder);
        ExcelUtil< TTaxiOrder > util = new ExcelUtil< TTaxiOrder >(TTaxiOrder.class);
        return util.exportExcel(list, "taxiOrder");
    }

    /**
     * 获取出租车订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tTaxiOrderService.selectTTaxiOrderById(id));
    }

    public static String dealDateFormat(String oldDate) {
        String format = "";
        if (oldDate != null && oldDate != "NULL" && oldDate != "") {
            if (!oldDate.contains("Z")) {
                format = oldDate;
            } else {
                //转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
                oldDate = oldDate.replace("Z", " UTC");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                Date d = null;//Mon Mar 06 00:00:00 CST 2017
                try {
                    d = sdf1.parse(oldDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                format = sdf.format(d);//2017-03-06
            }
        }
        return format;
    }

    /**
     * 系统生成班次，并更新接客顺序
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:add')")
    @Log(title = "出租车订单", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody TTaxiOrder tTaxiOrder) {
        String[] ids = tTaxiOrder.getIds().split(",");
        Long productId = Long.valueOf(tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(ids[0])).getProductCode());
        int passengerNum = 0;
        String orderType = "";
        for (String id : ids) {
            TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(id));
            if (order.getOrderType().equals("3") || order.getOrderType().equals("6")) {
                passengerNum += order.getSeatNum();
                orderType = order.getOrderType();
            }
//            else if (order.getOrderType().equals("4")) {
//                passengerNum += order.getUseNumber();
//            }
            else {
                orderType = order.getOrderType();
                passengerNum += 1;
            }
        }
//        String vehicleId = tTaxiOrder.getVehicleId();
        String useCarStartTime = tTaxiOrder.getBeginTime();
        String useCarEndTime = tTaxiOrder.getEndTime();
//        String driverId = tTaxiOrder.getDriverId();
        String ddCode = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ddCode = vehicleTaskStatusService.insertTVehicleTaskStatus(productId, dealDateFormat(useCarStartTime), dealDateFormat(useCarEndTime), Integer.parseInt(tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(ids[0])).getOrderType()));
//        Map map = new HashMap<>();
//        map.put("ids", ids.toString());
//        map.put("budId", ddCode);
        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(ddCode);
        if (orderService.updateOrderBusId(ddCode, tVehicleTaskStatus.getDriverId(), tVehicleTaskStatus.getVehiclePlateNo(), tVehicleTaskStatus.getDriverName(), tVehicleTaskStatus.getDriverPhone(), tTaxiOrder.getIds()) > 0) {
            if (orderType.equals("3") || orderType.equals("6")) {
                rentalcarsMessageService.updateBusIdByIds(ddCode, "", "", tTaxiOrder.getIds());
            } else if (orderType.equals("5") || orderType.equals("7")) {
                rentalcarsMessageService.updateBusIdByOrderCode(ddCode, tTaxiOrder.getOrderCode());
            }
            TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
            tRentalcarsMessage.setBusId(ddCode);
            tRentalcarsMessage.setSiteType(1);
            List< TRentalcarsMessage > rentalcarsMessageList = rentalcarsMessageService.selectTRentalcarsMessageByBusId(tRentalcarsMessage);
            TProductSite tProductSite = new TProductSite();
            tProductSite.settProductId(productId);
            List< TProductSite > productSites = tProductSiteService.selectTProductSiteList(tProductSite);
            String beginLat = productSites.get(0).getSiteInfo().getLat();
            String beginLng = productSites.get(0).getSiteInfo().getLng();
            if (orderType.equals("3") || orderType.equals("6")) {
                countDistance(rentalcarsMessageList, beginLat, beginLng, useCarStartTime, 1);
            }
//            else if (orderType.equals("5") || orderType.equals("7")) {
//                TTaxiOrder taxiOrder = new TTaxiOrder();
//                taxiOrder.setBusId(ddCode);
//                List< TTaxiOrder > taxiOrderList = orderService.selectTRentalOrderList(taxiOrder);
//                String account = "dh24733"; // 用户名（必填）
//                String password = "tB1eDOsV"; // 密码（必填,明文）
//                String mobile = taxiOrderList.get(0).getReserveMobile(); // 手机号码（必填,多条以英文逗号隔开）
//                String sign = "【运泰出行】"; // 短信签名（必填）
//                String content =
//                        "您购买的"
//                                + taxiOrderList.get(0).getUseTime()
//                                + taxiOrderList.get(0).getBeginStation()
//                                + "-"
//                                + taxiOrderList.get(0).getEndStation()
//                                + "订单已生成班次，接送车车牌号为"
//                                + taxiOrderList.get(0).getDriverCarNo()
//                                + ",接送司机手机号为"
//                                + taxiOrderList.get(0).getDriverPhone()
//                                + ",司机预估接送时间为"
//                                + taxiOrderList.get(0).getUseTime()
//                                + ".请安排好出行时间，如有疑问请拨打：0553-3911111。";
//                JSONObject paramData = new JSONObject();
//                paramData.put("account", account);
//                try {
//                    paramData.put("password", myMd5Util.MD5(password).toLowerCase());
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                paramData.put("phones", mobile);
//                paramData.put("sign", sign);
//                paramData.put("content", content);
//                // 解析json
//                JSONObject smsjsonObject = null;
//                try {
//                    smsjsonObject =
//                            (JSONObject)
//                                    JSONObject.parse(
//                                            httpUtils.httpURLConnectionPOST(
//                                                    "http://www.dh3t.com/json/sms/Submit", paramData));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                sendMessage(tVehicleTaskStatus.getDriverId(), "你收到一笔新的工单");
//            }
            Map result = new HashMap();
            result.put("busId", ddCode);
//            result.put("driverName", tVehicleTaskStatus.getDriverName());
//            result.put("driverCarNo", tVehicleTaskStatus.getVehiclePlateNo());
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            result.put("busTime", formatter.format(tVehicleTaskStatus.getTaskStartTime()) + "-" + formatter.format(tVehicleTaskStatus.getTaskEndTime()));
//            result.put("productName", tVehicleTaskStatus.getProductName());
//            result.put("orderType", orderType);
//            result.put("driverMobile", tVehicleTaskStatus.getDriverPhone());
            return AjaxResult.success(result);
        } else {
            return AjaxResult.error();
        }
    }

    @PreAuthorize("@ss.hasPermi('order:taxiOrder:add')")
    @Log(title = "拼包车工单生成", businessType = BusinessType.INSERT)
    @PostMapping("/confirmNewShift")
    @Transactional
    public AjaxResult confirmNewShift(@RequestBody TTaxiOrder tTaxiOrder) {
        String[] ids = tTaxiOrder.getIds().split(",");
        String orderType = "";
        String ddCode = "";
        int passengerNum = 0;
        for (String id : ids) {
            TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(id));
            if (order.getOrderType().equals("3") || order.getOrderType().equals("6")) {
                passengerNum += order.getSeatNum();
                orderType = order.getOrderType();
                ddCode = order.getBusId();
            }
//            else if (order.getOrderType().equals("4")) {
//                passengerNum += order.getUseNumber();
//            }
            else {
                orderType = order.getOrderType();
                ddCode = order.getBusId();
                passengerNum += 1;
            }
        }
        String vehicleId = tTaxiOrder.getVehicleId();
        TVehicleInfo tVehicleInfo = tVehicleInfoService.selectTVehicleInfoById(vehicleId);
        String driverId = tTaxiOrder.getDriverId();
        TDriverInfo tDriverInfo = driverInfoService.selectTDriverInfoById(driverId);
        vehicleTaskStatusService.insertVehicleAndDriver(ddCode, vehicleId, passengerNum, driverId);
        if (orderService.updateOrderBusId(ddCode, vehicleId, tVehicleInfo.getLicenseTagno(), tDriverInfo.getName(), tDriverInfo.getPhone(), tTaxiOrder.getIds()) > 0) {
            if (orderType.equals("3") || orderType.equals("6")) {
                for (String id : ids) {
                    TTaxiOrder order = orderService.selectTTaxiOrderById(Long.valueOf(id));
                    TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
                    tRentalcarsMessage.setOrderCode(order.getOrderCode());
                    tRentalcarsMessage.setSiteType(1);
                    List< TRentalcarsMessage > tRentalcarsMessages = rentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
                    String receptionTime = tRentalcarsMessages.get(0).getReceptionTime();
                    String beginStation = order.getBeginStation();
                    String endStation = order.getEndStation();
                    String account = "dh24733"; // 用户名（必填）
                    String password = "tB1eDOsV"; // 密码（必填,明文）
                    String mobile = order.getReserveMobile(); // 手机号码（必填,多条以英文逗号隔开）
                    String sign = "【运泰出行】"; // 短信签名（必填）
                    String content =
                            "您购买的"
                                    + order.getUseTime()
                                    + beginStation
                                    + "-"
                                    + endStation
                                    + "订单已生成班次，接送车车牌号为"
                                    + order.getDriverCarNo()
                                    + ",接送司机手机号为"
                                    + order.getDriverPhone()
                                    + ",司机预估接送时间为"
                                    + receptionTime
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
                sendMessage(driverId, "你收到一笔新的工单");
            } else if (orderType.equals("5") || orderType.equals("7")) {
                TTaxiOrder taxiOrder = new TTaxiOrder();
                taxiOrder.setBusId(ddCode);
                List< TTaxiOrder > taxiOrderList = orderService.selectTRentalOrderList(taxiOrder);
//                String receptionTime = "";
//                String beginStation = tRentalcarsMessages.get(0).getBeginStation();
//                String endStation = "";
//                for (TRentalcarsMessage rentalcarsMessage : tRentalcarsMessages) {
//                    if (rentalcarsMessage.getSiteType() == 1) {
//                        receptionTime = rentalcarsMessage.getReceptionTime();
//                        beginStation = rentalcarsMessage.getBeginStation();
//                    } else if (rentalcarsMessage.getSiteType() != 1) {
//                        endStation = rentalcarsMessage.getBeginStation();
//                    }
//                }
                String account = "dh24733"; // 用户名（必填）
                String password = "tB1eDOsV"; // 密码（必填,明文）
                String mobile = taxiOrderList.get(0).getReserveMobile(); // 手机号码（必填,多条以英文逗号隔开）
                String sign = "【运泰出行】"; // 短信签名（必填）
                String content =
                        "您购买的"
                                + taxiOrderList.get(0).getUseTime()
                                + taxiOrderList.get(0).getBeginStation()
                                + "-"
                                + taxiOrderList.get(0).getEndStation()
                                + "订单已生成班次，接送车车牌号为"
                                + taxiOrderList.get(0).getDriverCarNo()
                                + ",接送司机手机号为"
                                + taxiOrderList.get(0).getDriverPhone()
                                + ",司机预估接送时间为"
                                + taxiOrderList.get(0).getUseTime()
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
                sendMessage(driverId, "你收到一笔新的工单");
            }
            TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(ddCode);
            Date date = new Date();//获取当前的日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String str = df.format(date);//获取String类型的时间
            tVehicleTaskStatus.setTaskCreatTime(str);
            vehicleTaskStatusService.updateTVehicleTaskStatus(tVehicleTaskStatus);
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    @PreAuthorize("@ss.hasPermi('order:taxiOrder:edit')")
    @Log(title = "重新计算", businessType = BusinessType.UPDATE)
    @PostMapping("/recalculate")
    public AjaxResult recalculate(@RequestBody TTaxiOrder tTaxiOrder) {
        List< TRentalcarsMessage > rentalcarsMessageList = tTaxiOrder.getPassengerList();
        String useCarStartTime = tTaxiOrder.getBeginTime();
        TRentalcarsMessage rentalcarsMessage = new TRentalcarsMessage();
//        for (TRentalcarsMessage tRentalcarsMessage : rentalcarsMessageList) {
//            if (tRentalcarsMessage.getReceptionNum() == 1) {
//                rentalcarsMessage = rentalcarsMessageService.selectTRentalcarsMessageById(tRentalcarsMessage.getId());
//                rentalcarsMessage.setReceptionTime(useCarStartTime);
//                rentalcarsMessageService.updateTRentalcarsMessage(rentalcarsMessage);
//                rentalcarsMessageList.remove(tRentalcarsMessage);
//            }
//        }
        Collections.sort(
                rentalcarsMessageList,
                new Comparator< TRentalcarsMessage >() {
                    public int compare(TRentalcarsMessage o1, TRentalcarsMessage o2) {
                        String value1 = o1.getReceptionNum() + "" == "" ? "1" : o1.getReceptionNum() + "";
                        String value2 = o2.getReceptionNum() + "" == "" ? "2" : o2.getReceptionNum() + "";
                        return value1.compareTo(value2);
                    }
                });
        rentalcarsMessage = rentalcarsMessageService.selectTRentalcarsMessageById(rentalcarsMessageList.get(0).getId());
        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(rentalcarsMessage.getBusId());
        tVehicleTaskStatus.setTaskStartTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", dealDateFormat(tTaxiOrder.getBeginTime())));
        tVehicleTaskStatus.setTaskEndTime(DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", dealDateFormat(tTaxiOrder.getEndTime())));
        vehicleTaskStatusService.updateTVehicleTaskStatus(tVehicleTaskStatus);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        useCarStartTime = formatter.format(tVehicleTaskStatus.getTaskStartTime());
        rentalcarsMessage.setReceptionTime(useCarStartTime);
        rentalcarsMessage.setReceptionNum(rentalcarsMessageList.get(0).getReceptionNum());
        rentalcarsMessageService.updateTRentalcarsMessage(rentalcarsMessage);
        String beginLat = rentalcarsMessage.getLatitude();
        String beginLng = rentalcarsMessage.getLongitude();
        rentalcarsMessageList.remove(0);
        countDistance(rentalcarsMessageList, beginLat, beginLng, useCarStartTime, 2);
        return AjaxResult.success();
    }


    public String countDistance(List< TRentalcarsMessage > rentalcarsMessageList, String beginLat, String beginLng, String useCarStartTime, int xh) {
        GlobalCoordinates user =
                new GlobalCoordinates(
                        Double.valueOf(beginLat), Double.valueOf(beginLng));

        for (TRentalcarsMessage rentalcarsMessage : rentalcarsMessageList) {
            GlobalCoordinates driver =
                    new GlobalCoordinates(Double.valueOf(rentalcarsMessage.getLatitude()), Double.valueOf(rentalcarsMessage.getLongitude()));
            BigDecimal meter1 = new BigDecimal(getDistanceMeter(user, driver, Ellipsoid.Sphere));
            rentalcarsMessage.setDistance(meter1);
        }
        if (rentalcarsMessageList.size() > 1) {
            Collections.sort(
                    rentalcarsMessageList,
                    new Comparator< TRentalcarsMessage >() {
                        public int compare(TRentalcarsMessage o1, TRentalcarsMessage o2) {
                            BigDecimal value1 = o1.getDistance() == null ? new BigDecimal("1") : o1.getDistance();
                            BigDecimal value2 = o2.getDistance() == null ? new BigDecimal("2") : o2.getDistance();
                            return value1.compareTo(value2);
                        }
                    });
        }
        TRentalcarsMessage tRentalcarsMessage = rentalcarsMessageList.get(0);
        if (xh == 1) {
            tRentalcarsMessage.setReceptionNum(xh + "");
            tRentalcarsMessage.setReceptionTime(useCarStartTime);
            rentalcarsMessageService.updateTRentalcarsMessage(tRentalcarsMessage);
            rentalcarsMessageList.remove(0);
            xh++;
            if (rentalcarsMessageList.size() > 0) {
                countDistance(rentalcarsMessageList, tRentalcarsMessage.getLatitude(), tRentalcarsMessage.getLongitude(), tRentalcarsMessage.getReceptionTime(), xh);
            } else if (rentalcarsMessageList.size() == 0) {
                return "";
            }
        } else if (xh != 1 && rentalcarsMessageList != null && rentalcarsMessageList.size() > 1) {
            tRentalcarsMessage.setReceptionNum(xh + "");
            BigDecimal hs = tRentalcarsMessage.getDistance().divide(new BigDecimal("6400")).multiply(new BigDecimal("3600000")).setScale(2, BigDecimal.ROUND_HALF_UP);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = df.parse(useCarStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date.setTime(date.getTime() + hs.longValue());
            tRentalcarsMessage.setReceptionTime(df.format(date));
            tRentalcarsMessage.setReceptionNum(xh + "");
            rentalcarsMessageService.updateTRentalcarsMessage(tRentalcarsMessage);
            rentalcarsMessageList.remove(0);
            xh++;
            countDistance(rentalcarsMessageList, tRentalcarsMessage.getLatitude(), tRentalcarsMessage.getLongitude(), tRentalcarsMessage.getReceptionTime(), xh);
        } else if (xh != 1 && rentalcarsMessageList != null && rentalcarsMessageList.size() == 1) {
            tRentalcarsMessage.setReceptionNum(xh + "");
            BigDecimal hs = tRentalcarsMessage.getDistance().divide(new BigDecimal("6400")).multiply(new BigDecimal("3600000")).setScale(2, BigDecimal.ROUND_HALF_UP);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = df.parse(useCarStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date.setTime(date.getTime() + hs.longValue());
            tRentalcarsMessage.setReceptionTime(df.format(date));
            tRentalcarsMessage.setReceptionNum(xh + "");
            rentalcarsMessageService.updateTRentalcarsMessage(tRentalcarsMessage);
        }
        return "";
    }


    public static double getDistanceMeter(
            GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {

        // 创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve =
                new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    @PreAuthorize("@ss.hasPermi('order:taxiOrder:edit')")
    @Log(title = "依据调度单号删除", businessType = BusinessType.UPDATE)
    @PostMapping("/delBusId")
    public AjaxResult delBusId(@RequestBody TTaxiOrder tTaxiOrder) {
        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(tTaxiOrder.getBusId());
        if (vehicleTaskStatusService.deleteTVehicleTaskStatusById(tVehicleTaskStatus.getVehicleTaskStatusId() + "") > 0) {
            orderService.delByBusId(tTaxiOrder.getBusId());
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getPassengerList")
    public AjaxResult getPassengerList(String busId) {
        TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
        tRentalcarsMessage.setBusId(busId);
        tRentalcarsMessage.setSiteType(1);
        List< TRentalcarsMessage > rentalcarsMessageList = rentalcarsMessageService.selectTRentalcarsMessageByBusId(tRentalcarsMessage);
        tRentalcarsMessage.setSiteType(2);
        List< TRentalcarsMessage > endList = rentalcarsMessageService.selectTRentalcarsMessageByBusId(tRentalcarsMessage);
        for (TRentalcarsMessage rentalcarsMessage : rentalcarsMessageList) {
            rentalcarsMessage.setEndStation(endList.get(0).getBeginStation());
        }
        return AjaxResult.success(rentalcarsMessageList);
    }

    @PreAuthorize("@ss.hasPermi('order:taxiOrder:add')")
    @Log(title = "出租车订单", businessType = BusinessType.INSERT)
    @PostMapping("/confirmBusData")
    @Transactional
    public AjaxResult confirmBusData(@RequestBody TTaxiOrder tTaxiOrder) {
        String[] ids = tTaxiOrder.getIds().split(",");
//        Long productId = Long.valueOf(tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(ids[0])).getProductCode());
//        int passengerNum = 0;
//        for (String id : ids) {
//            TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(id));
//            if (order.getOrderType().equals("3")) {
//                passengerNum += order.getSeatNum();
//            } else if (order.getOrderType().equals("4")) {
//                passengerNum += order.getUseNumber();
//            } else {
//                passengerNum += 1;
//            }
//        }
//        String vehicleId = tTaxiOrder.getVehicleId();
//        String useCarStartTime = tTaxiOrder.getBeginTime();
//        String useCarEndTime = tTaxiOrder.getEndTime();
//        String driverId = tTaxiOrder.getDriverId();
        String busId = "";
//        String ddCode = "";
//        ddCode = vehicleTaskStatusService.insertTVehicleTaskStatus(productId, vehicleId, passengerNum, useCarStartTime, useCarEndTime, Integer.parseInt(tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(ids[0])).getOrderType()), driverId);
////        Map map = new HashMap<>();
////        map.put("ids", ids.toString());
////        map.put("budId", ddCode);
//        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(ddCode);
        if (rentalcarsMessageService.confirmBusData(tTaxiOrder) > 0) {
//            rentalcarsMessageService.updateBusIdByIds(ddCode, tTaxiOrder.getIds());
//            if (!tVehicleTaskStatus.getDriverId().equals("")) {
//            for (String id : ids) {
//                TTaxiOrder order = orderService.selectTTaxiOrderById(Long.valueOf(id));
//                busId = order.getBusId();
//                TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
//                tRentalcarsMessage.setOrderCode(order.getOrderCode());
//                tRentalcarsMessage.setSiteType(1);
//                List< TRentalcarsMessage > tRentalcarsMessages = rentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
//                String receptionTime = tRentalcarsMessages.get(0).getReceptionTime();
//                String beginStation = order.getBeginStation();
//                String endStation = order.getEndStation();
//                String account = "dh24733"; // 用户名（必填）
//                String password = "tB1eDOsV"; // 密码（必填,明文）
//                String mobile = order.getReserveMobile(); // 手机号码（必填,多条以英文逗号隔开）
//                String sign = "【运泰出行】"; // 短信签名（必填）
//                String content =
//                        "您购买的"
//                                + order.getUseTime()
//                                + beginStation
//                                + "-"
//                                + endStation
//                                + "订单已生成班次，接送车车牌号为"
//                                + order.getDriverCarNo()
//                                + ",接送司机手机号为"
//                                + order.getDriverPhone()
//                                + ",司机预估接送时间为"
//                                + receptionTime
//                                + ".请安排好出行时间，如有疑问请拨打：0553-3911111。";
//                JSONObject paramData = new JSONObject();
//                paramData.put("account", account);
//                try {
//                    paramData.put("password", myMd5Util.MD5(password).toLowerCase());
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                paramData.put("phones", mobile);
//                paramData.put("sign", sign);
//                paramData.put("content", content);
//                // 解析json
//                JSONObject smsjsonObject = null;
//                try {
//                    smsjsonObject =
//                            (JSONObject)
//                                    JSONObject.parse(
//                                            httpUtils.httpURLConnectionPOST(
//                                                    "http://www.dh3t.com/json/sms/Submit", paramData));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                TDriverInfo tDriverInfo = driverInfoService.selectTDriverInfoById(tVehicleTaskStatus.getDriverId());
//                ErpDriverInfo erpDriverInfo = erpDriverInfoService.selectErpDriverInfoById(tDriverInfo.getErpDriverId());
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String dateString = formatter.format(tVehicleTaskStatus.getTaskStartTime());
//                if (erpDriverInfo.getfOpenId() != null && !erpDriverInfo.getfOpenId().equals("")) {
//                    Map map = new HashMap();
//                    map.put("busId", ddCode);
//                    map.put("busDate", dateString);
//                    map.put("busType", "生成班次");
//                    map.put("openid", erpDriverInfo.getfOpenId());
//                    sendMessage(map);
//                }
////                    sendMessage(tVehicleTaskStatus.getDriverId());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            sendMessage(driverId, "你收到一笔新的工单");
//            TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(busId);
//            Date date = new Date();//获取当前的日期
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            String str = df.format(date);//获取String类型的时间
//            tVehicleTaskStatus.setTaskCreatTime(str);
//            vehicleTaskStatusService.updateTVehicleTaskStatus(tVehicleTaskStatus);
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 添加到已有班次
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:edit')")
    @Log(title = "出租车订单", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional(rollbackFor = RuntimeException.class)
    public AjaxResult edit(@RequestBody TTaxiOrder tTaxiOrder) {
        String[] ids = tTaxiOrder.getIds().split(",");
        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(tTaxiOrder.getBusId());
        int passengerNum = 0;
        for (String id : ids) {
            TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(id));
            if (order.getOrderType().equals("3") || order.getOrderType().equals("6")) {
                passengerNum += order.getSeatNum();
            } else if (order.getOrderType().equals("4")) {
                passengerNum += order.getUseNumber();
            } else {
                passengerNum += 1;
            }
        }
        GlobalCoordinates driver = null;
        try {
            Map result = vehicleTaskStatusService.updateTVehicleTaskStatus(tTaxiOrder.getBusId(), passengerNum, 3);
            if (result.get("code").toString().equals("200")) {
                orderService.updateOrderBusId(tTaxiOrder.getBusId(), tVehicleTaskStatus.getDriverId(), tVehicleTaskStatus.getVehiclePlateNo(), tVehicleTaskStatus.getDriverName(), tVehicleTaskStatus.getDriverPhone(), tTaxiOrder.getIds());
//                rentalcarsMessageService.updateBusIdByIds(tTaxiOrder.getBusId(), tTaxiOrder.getIds());
                if (!tVehicleTaskStatus.getDriverId().equals("")) {
                    for (String id : ids) {
                        TTaxiOrder order = orderService.selectTTaxiOrderById(Long.valueOf(id));
                        TRentalcarsMessage tRentalcarsMessage = new TRentalcarsMessage();
                        tRentalcarsMessage.setOrderCode(tTaxiOrder.getOrderCode());
                        tRentalcarsMessage.setSiteType(1);
                        List< TRentalcarsMessage > tRentalcarsMessages = rentalcarsMessageService.selectTRentalcarsMessageList(tRentalcarsMessage);
                        tRentalcarsMessage = tRentalcarsMessages.get(0);
                        driver =
                                new GlobalCoordinates(Double.valueOf(tRentalcarsMessage.getLatitude()), Double.valueOf(tRentalcarsMessage.getLongitude()));
                        TRentalcarsMessage rentalcarsMessage = rentalcarsMessageService.selectMaxtTRentalcarsMessageById(tTaxiOrder.getBusId());
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
                        rentalcarsMessageService.updateBusIdByIds(tTaxiOrder.getBusId(),
                                rentalcarsMessage.getReceptionNum() + 1 + "", df.format(date),
                                id);
                        String account = "dh24733"; // 用户名（必填）
                        String password = "tB1eDOsV"; // 密码（必填,明文）
                        String mobile = order.getReserveMobile(); // 手机号码（必填,多条以英文逗号隔开）
                        String sign = "【运泰出行】"; // 短信签名（必填）
                        String content =
                                "您购买的"
                                        + order.getUseTime()
                                        + order.getBeginStation()
                                        + "-"
                                        + order.getEndStation()
                                        + "订单已生成班次，接送车车牌号为"
                                        + order.getDriverCarNo()
                                        + ",接送司机手机号为"
                                        + order.getDriverPhone()
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
                }
                return AjaxResult.success();
            } else {
                return AjaxResult.error(result.get("msg").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PreAuthorize("@ss.hasPermi('order:taxiOrder:edit')")
    @Log(title = "出租车订单", businessType = BusinessType.UPDATE)
    @PostMapping("/updateDriver")
    public AjaxResult updateDriver(@RequestBody TTaxiOrder tTaxiOrder) {
        TVehicleInfo tVehicleInfo = tVehicleInfoService.selectTVehicleInfoById(tTaxiOrder.getVehicleId());
        tVehicleInfo.setDriverId(tTaxiOrder.getDriverId());
        if (tVehicleInfoService.updateTVehicleInfo(tVehicleInfo) > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    /**
     * 删除出租车订单
     */
    @PreAuthorize("@ss.hasPermi('order:taxiOrder:remove')")
    @Log(title = "出租车订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tTaxiOrderService.deleteTTaxiOrderByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getCanUseCarList")
    public AjaxResult getCanUseCarList(TTaxiOrder tTaxiOrder) {
        int passengerNum = 0;
        String beginTime = "";
        String endTime = "";
        List< TVehicleInfo > vehicleInfoList = new ArrayList<>();
        if (tTaxiOrder.getIds() != null) {
            String[] ids = tTaxiOrder.getIds().split(",");
            String productCode = "";
//        List< TVehicleType > vehicleTypeList = new ArrayList<>();
            for (String id : ids) {
                TTaxiOrder order = tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(id));
                if (order.getOrderType().equals("3") || order.getOrderType().equals("6")) {
                    passengerNum += order.getSeatNum();

                }
//            else if (order.getOrderType().equals("4")) {
//                passengerNum += order.getUseNumber();
//            }
                else {
                    passengerNum += 1;
                }
            }
            beginTime = tTaxiOrder.getBeginTime();
            endTime = tTaxiOrder.getEndTime();
        } else {
            TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(tTaxiOrder.getBusId());
            passengerNum = tVehicleTaskStatus.getPassengerSeatNum();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginTime = formatter.format(tVehicleTaskStatus.getTaskStartTime());
            endTime = formatter.format(tVehicleTaskStatus.getTaskEndTime());
        }
        TVehicleInfo tVehicleInfo = new TVehicleInfo();
        tVehicleInfo.setUseCarStartTime(beginTime);
        tVehicleInfo.setUseCarEndTime(endTime);
        tVehicleInfo.setPassengerNum(passengerNum);
        if (tTaxiOrder.getDriverCarNo() == null) {
            tVehicleInfo.setLicenseTagno("");
        } else {
            tVehicleInfo.setLicenseTagno(tTaxiOrder.getDriverCarNo());
        }
        if (tTaxiOrder.getReserveName() == null || tTaxiOrder.getReserveName().equals("10")) {
            tVehicleInfo.setDeptId("");
        } else {
            tVehicleInfo.setDeptId(tTaxiOrder.getReserveName());
        }
        if (tTaxiOrder.getReserveMobile() == null || tTaxiOrder.getReserveMobile().equals("0")) {
            tVehicleInfo.setType("");
        } else {
            tVehicleInfo.setType(tTaxiOrder.getReserveMobile());
        }
        if (tTaxiOrder.getDriverName() == null) {
            tVehicleInfo.setDriverName("");
        } else {
            tVehicleInfo.setDriverName(tTaxiOrder.getDriverName());
        }
        vehicleInfoList = tVehicleInfoService.selectCanUseTVehicleInfoList(tVehicleInfo);
        return AjaxResult.success(vehicleInfoList);
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getCanUseBusList")
    public AjaxResult getCanUseBusList(TTaxiOrder tTaxiOrder) {
        String[] ids = tTaxiOrder.getIds().split(",");
        Long productCode = Long.valueOf(tTaxiOrderService.selectTTaxiOrderById(Long.valueOf(ids[0])).getProductCode());
        return AjaxResult.success(vehicleTaskStatusService.selectTVehicleTask(productCode, ids.length, tTaxiOrder.getBeginTime()));
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getCanUseDriverList")
    public AjaxResult getCanUseDriverList(TTaxiOrder tTaxiOrder) {
        TDriverInfo tDriverInfo = new TDriverInfo();
        if (tTaxiOrder.getDriverName() == null) {
            tDriverInfo.setName("");
        } else {
            tDriverInfo.setName(tTaxiOrder.getDriverName());
        }
        if (tTaxiOrder.getReserveName() == null || tTaxiOrder.getReserveName().equals("10")) {
            tDriverInfo.setDeptId("");
        } else {
            tDriverInfo.setDeptId(tTaxiOrder.getReserveName());
        }
        tDriverInfo.setTaskStartTime(tTaxiOrder.getBeginTime());
        tDriverInfo.setTaskEndTime(tTaxiOrder.getEndTime());
        return AjaxResult.success(driverInfoService.selectCanUseTDriverInfoList(tDriverInfo));
    }

//    public Object sendMessage(Map param) {
//        // 创建Httpclient对象
//        System.out.println("开始发送消息==" + new Timestamp(System.currentTimeMillis()));
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String resultString = "";
//        CloseableHttpResponse response = null;
//        Map< String, Object > result = new HashMap<>();
//        Map< String, Object > data = new HashMap<>();
//        int statusCode = 500;
//        String access_token = "";
//        StringBuilder sb = new StringBuilder();
//        sb.append("appid=" + "wx11fa562f9830884b");
////        sb.append("&secret=" + "512ee59878a2e32e9f2d6d45ffab1db5");
//        sb.append("&secret=" + "99d241c3652727e5bebe1fcddc825492");
//        sb.append("&grant_type=client_credential");
//        //    String url =
//        //        "https://api.weixin.qq.com/cgi-bin/token?appid="
//        //            + DataConstants.APPID
//        //            + "&secret="
//        //            + DataConstants.SECRET
//        //            + "&grant_type=client_credential";
//        String key = "countKey";
//        int count = 1;
//        if (redisUtil.hasKey(key)) {
//            count = (int) redisUtil.get(key);
//            count++;
//            redisUtil.del(key);
//            redisUtil.set(key, count, 0);
//        } else {
//            redisUtil.del(key);
//            redisUtil.set(key, count, 0);
//        }
//        System.out.println("调用次数====" + count);
//        JSONObject jsonObject =
//                httpUtils.httpRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", sb.toString());
//        if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
//            access_token = jsonObject.getString("access_token");
//            JSONObject paramMap = new JSONObject();
//            paramMap.put("access_token", access_token);
//            paramMap.put("touser", param.get("openid"));
//            paramMap.put("template_id", "F2fZhKpqmh-lkcnWsOqC8hbDGqUmE09UWhFtYFxrcSY");
//            paramMap.put("access_token", access_token);
//            //      paramMap.put("miniprogram_state", "developer");
//            paramMap.put("miniprogram_state", "formal");
//            paramMap.put("page", "pages/message/message/message?type=1");
//            Map< String, Object > map = new HashMap();
//            Map< String, String > map1 = new HashMap();
//            Map< String, String > map2 = new HashMap();
//            Map< String, String > map3 = new HashMap();
//            map1.put("value", param.get("busId").toString());
//            map2.put("value", param.get("busDate").toString());
//            map3.put("value", param.get("busType").toString());
//            map.put("thing9", map3);
//            map.put("number6", map1);
//            map.put("time1", map2);
//            paramMap.put("data", map);
//            JSONObject messageObject = new JSONObject();
//            try {
//                messageObject =
//                        (JSONObject)
//                                JSONObject.parse(
//                                        httpUtils.httpURLConnectionPOST(
//                                                "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="
//                                                        + access_token,
//                                                paramMap));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (messageObject.getString("errcode").equals("0")) {
//                statusCode = 200;
//                result.put("code", statusCode);
//                result.put("data", data);
//                result.put("message", "消息推送成功！");
//            } else {
//                result.put("code", statusCode);
//                result.put("data", data);
//                result.put("message", messageObject.getString("errmsg"));
//            }
//        } else {
//            result.put("code", statusCode);
//            result.put("data", data);
//            result.put("message", jsonObject.getString("errmsg"));
//        }
//        return result;
//    }

    public void sendMessage(String driverId, String msg) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient =
                new JPushClient(DataConstants.jgSecret, DataConstants.jgAppKey, null, clientConfig);
        final PushPayload payload =
                PushPayload.newBuilder()
                        .setPlatform(Platform.android_ios())
                        // 正式环境及测试环境启用开关true正式环境
                        .setOptions(Options.newBuilder().setApnsProduction(false).build())
                        .setAudience(
                                Audience.newBuilder()
                                        .addAudienceTarget(AudienceTarget.tag(driverId))
                                        //                    .addAudienceTarget(AudienceTarget.alias("alias1",
                                        // "alias2"))
                                        .build())
                        .setNotification(Notification.newBuilder().setAlert(msg).build())
                        .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);
            logger.info("短信发送结果======" + result);
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
            logger.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
            logger.error("Sendno: " + payload.getSendno());
        }
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/deptAll")
    public AjaxResult deptAll(SysDept dept) {
        dept.setDelFlag("0");
        dept.setStatus("0");
        List< SysDept > depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/vehicleTypeAll")
    public AjaxResult vehicleTypeAll(TVehicleType tVehicleType) {
        List< TVehicleType > list = tVehicleTypeService.selectTVehicleTypeList(tVehicleType);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/productCodeAll")
    public AjaxResult productCodeAll(TProduct tProduct) {
        List< TProduct > list = productService.selectTProductList(tProduct);
        return AjaxResult.success(list);
    }

    /**
     * 获取待调度的客户列表
     */
    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getWaitlist")
    public AjaxResult getWaitlist(TTaxiOrder tTaxiOrder) {
        tTaxiOrder.setBeginTime(getTransTime(tTaxiOrder.getBeginTime()));
        tTaxiOrder.setEndTime(getTransTime(tTaxiOrder.getEndTime()));
        List< TTaxiOrder > list = tTaxiOrderService.selectWaitList(tTaxiOrder);
        return AjaxResult.success(list);
    }

    public static String getTransTime(String timeStr) {


        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
        LocalDateTime date = LocalDateTime.parse(timeStr, df);
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String transTime = f2.format(date);

        return transTime;
    }


    /**
     * 获取调度班次列表
     */
    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getbusOrderlist")
    public TableDataInfo getbusOrderlist(TVehicleTaskStatus tVehicleTaskStatus) {
        startPage();
        List< TVehicleTaskStatus > list = vehicleTaskStatusService.selectTVehicleTaskStatusList2(tVehicleTaskStatus);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('order:rentalOrder:list')")
    @GetMapping("/getCanUseOrderList")
    public AjaxResult getCanUseOrderList(TTaxiOrder tTaxiOrder) {
        int passengerNum = 0;
        String beginTime = "";
        String endTime = "";
        List< TTaxiOrder > orderList = new ArrayList<>();
        TVehicleTaskStatus tVehicleTaskStatus = vehicleTaskStatusService.selectTVehicleTaskStatusByDispatchOrdercode(tTaxiOrder.getBusId());
        passengerNum = tVehicleTaskStatus.getRemainingSeatNum();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        beginTime = formatter.format(tVehicleTaskStatus.getTaskStartTime());
        tTaxiOrder.setBeginTime(beginTime);
        tTaxiOrder.setPassengerNum(passengerNum);
        tTaxiOrder.setProductCode(tVehicleTaskStatus.getProductId() + "");
        orderList = orderService.getCanUseOrderList(tTaxiOrder);
        return AjaxResult.success(orderList);
    }


}
