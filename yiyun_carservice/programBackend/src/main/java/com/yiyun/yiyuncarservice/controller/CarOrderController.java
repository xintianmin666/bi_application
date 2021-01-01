package com.yiyun.yiyuncarservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.zxing.WriterException;
import com.yiyun.yiyuncarservice.constant.DataConstants;
import com.yiyun.yiyuncarservice.entity.*;
import com.yiyun.yiyuncarservice.mapper.COrderMapper;
import com.yiyun.yiyuncarservice.mapper.CPointsHistoryMapper;
import com.yiyun.yiyuncarservice.mapper.CRefundLogMapper;
import com.yiyun.yiyuncarservice.service.*;
import com.yiyun.yiyuncarservice.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
@Slf4j
@Api(value = "订单管理类", tags = "订单管理类", description = "订单管理类")
@CrossOrigin
public class CarOrderController {

    private static final Logger logger = LoggerFactory.getLogger(CarOrderController.class);
    @Autowired
    HttpServletRequest request;
    @Autowired
    private MyMD5Util myMd5Util;
    @Autowired
    private ValidateUtil validateUtil;
    @Autowired
    private HttpPostUtil httpPostUtil;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    private RandomUtil randomUtil;
    @Autowired
    private PayUtil payUtil;
    @Autowired
    private AESUtil aesUtil;
    @Autowired
    private RefundAESutil refundAESutil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private COrderService orderService;
    @Autowired
    private COrderGoodsService orderGoodsService;
    @Autowired
    private CRefundLogService refundLogService;
    @Autowired
    private CPointsHistoryService pointsHistoryService;
    @Autowired
    private CUserInfoService userInfoService;
    @Autowired
    private CShopGoodsService shopGoodsService;
    @Autowired
    private CErrorLogService errorLogService;
    @Autowired
    private COrderMapper orderMapper;
    @Autowired
    private CRefundLogMapper refundLogMapper;
    @Autowired
    private CEvaluateService evaluateService;
    @Autowired
    private Md5Util md5Util;
    @Autowired
    private QrCodeUtil qrCodeUtil;
    @Autowired
    private SysDictDataService sysDictDataService;
    @Autowired
    private CShopService shopService;
    @Autowired
    private CPointsHistoryMapper pointsHistoryMapper;
    @Autowired
    private CPromoteService cPromoteService;
    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private CPayLogService cPayLogService;
    @Autowired
    public RedisTemplate< String, String > redisTemplate;


    public static String changeTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(time);
        Date date = new Date(lt);
        time = simpleDateFormat.format(date);
        return time;
    }

    public static String getNowDate() {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return tempDate.format(new Date());
    }

    /**
     * @return "年月日时分秒"+订单类型+4位流水号(后面依据情况增加)+2位随机数
     * @author hxx
     * @date 2020-03-12 17:18
     */
    public String generateOrderNumber() {
        String orderNumber = "";
        String serial_number = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowdate = formatter.format(new Date());
        serial_number = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(new Date());
        Random rand = new Random(); // 生成随机数
        String cardNumer = "";
        for (int a = 0; a < 2; a++) {
            cardNumer += rand.nextInt(10); // 生成2位数字
        }
        orderNumber = dateString + serial_number + cardNumer;
        return orderNumber;
    }

    @PostMapping(value = "/createOrder")
    @ApiOperation(value = "创建订单", notes = "创建订单", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object createOrder(@RequestBody Map< String, Object > param) {
        logger.info("进入创建订单===" + new Timestamp(System.currentTimeMillis()));
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        List< Map< String, Object > > resultList = new ArrayList< Map< String, Object > >();
        String inStr = "";
        String SignCode = "";
        String bookTime = "";
        bookTime = param.get("bookTime") == null ? "" : param.get("bookTime").toString();
        JSONObject data = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        Map< String, Object > schedule = new HashMap<>();
        String mobile = param.get("userPhone") == null ? "" : param.get("userPhone").toString();
        String userId = param.get("userId").toString();
        CUserInfo userInfo = userInfoService.getById(userId);
        logger.info("传入的参数值===" + param.toString());
        int orderType = Integer.parseInt(param.get("orderType").toString());
        String orderCode = "";
        if (orderType != 6) {
            orderCode = generateOrderNumber();
        } else {
            orderCode = param.get("orderCode").toString();
        }
        try {
            COrder orderInfo = new COrder();
            COrder order = new COrder();
            if (orderType == 6) {
                QueryWrapper< COrder > orderQueryWrapper = new QueryWrapper<>();
                orderQueryWrapper.eq("order_code", orderCode);
                orderInfo = orderService.getOne(orderQueryWrapper);
                order = orderInfo;
            }
            order.setOrderCode(orderCode);
            if (param.get("inviteCode") != null && !param.get("inviteCode").toString().equals("")) {
                QueryWrapper< CPromote > cPromoteQueryWrapper = new QueryWrapper<>();
                cPromoteQueryWrapper.eq("promote_code", param.get("inviteCode"));
                List< CPromote > cPromoteList = cPromoteService.list(cPromoteQueryWrapper);
                if (cPromoteList != null && cPromoteList.size() > 0) {
                    order.setInviteCode(param.get("inviteCode").toString());
                }
            }
            order.setUserPhone(mobile);
            order.setUserId(userId);
            if (param.get("shopId") != null) {
                order.setShopId(param.get("shopId").toString());
                CShop cShop = shopService.getById(param.get("shopId").toString());
                order.setShopName(cShop.getName());
            }
            String productCode = "";
            String productName = "";
            BigDecimal shopAmount = BigDecimal.ZERO;
            BigDecimal productAmount = BigDecimal.ZERO;
            BigDecimal productNum = BigDecimal.ZERO;
            BigDecimal orderAmount = BigDecimal.ZERO;
            BigDecimal collectAmount = BigDecimal.ZERO;
            BigDecimal pointsAmount = BigDecimal.ZERO;
            BigDecimal usePoints = BigDecimal.ZERO;
            BigDecimal addPoints = BigDecimal.ZERO;
            String payTime = getNowDate();
            List< String > typeList = new ArrayList<>();
            typeList.add("return");
            typeList.add("discount");
            QueryWrapper< SysDictData > sysDictDataQueryWrapper = new QueryWrapper<>();
            sysDictDataQueryWrapper.in("dict_value", typeList);
            List< SysDictData > sysDictDataList = sysDictDataService.list(sysDictDataQueryWrapper);
            BigDecimal returnRate = new BigDecimal("0.01");
            BigDecimal discountRate = new BigDecimal("1");
            for (SysDictData sysDictData : sysDictDataList) {
                if (sysDictData.getDictValue().equals("return")) {
                    returnRate = new BigDecimal(sysDictData.getDictLabel());
                } else {
                    discountRate = new BigDecimal(sysDictData.getDictLabel());
                }
            }
            if (orderType == 1 || orderType == 4) {
                productNum = new BigDecimal(param.get("goodsNum").toString());
                orderAmount = new BigDecimal(param.get("orderAmount").toString());
                collectAmount = new BigDecimal(param.get("collectAmount").toString());
                productCode = param.get("goodsCode").toString();
                productName = param.get("goodsName").toString();
                QueryWrapper< CShopGoods > goodWrapper = new QueryWrapper<>();
                goodWrapper.eq("id", productCode);
                CShopGoods shopGoods = shopGoodsService.getOne(goodWrapper);
                productAmount = shopGoods.getPayPrice();
                shopAmount = shopGoods.getPayPrice().multiply(productNum).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal bcsyjf = BigDecimal.ZERO;
                if (param.get("isUsePoints").toString().equals("1")) {
                    if (collectAmount.compareTo(BigDecimal.ZERO) == 0) {
                        try {
                            if (!md5Util.validPassword(param.get("payPassword").toString(), userInfo.getPassword())) {
                                result.put("code", statusCode);
                                result.put("data", "");
                                result.put("message", "个人支付密码错误，请重新填写!");
                                return result;
                            }
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        order.setPayTime(payTime);
                        order.setOrderStatus("3");
                    }
                    bcsyjf = orderAmount.divide(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                    usePoints = userInfo.getPoints();
                    if (usePoints.compareTo(bcsyjf) >= 0) {
                        usePoints = bcsyjf;
                    }
                    pointsAmount = usePoints.multiply(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                    shopAmount = shopAmount.subtract(pointsAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (shopAmount.compareTo(collectAmount) != 0) {
                    result.put("code", statusCode);
                    result.put("data", "");
                    result.put("message", "价格异常，下单失败，请重新下单！");
                    return result;
                } else {
                    addPoints = collectAmount.multiply(returnRate).setScale(0, BigDecimal.ROUND_DOWN);
                }
            }
            if (param.get("carNo") != null) {
                order.setCarNo(param.get("carNo").toString());
            }
            if (param.get("userName") != null) {
                order.setUserName(param.get("userName").toString());
            }
            if (param.get("remark") != null) {
                order.setRemark(param.get("remark").toString());
            }
            if (param.get("address") != null) {
                order.setAddress(param.get("address").toString());
            }
            if (param.get("longitude") != null) {
                order.setLongitude(param.get("longitude").toString());
            }
            if (param.get("latitude") != null) {
                order.setLatitude(param.get("latitude").toString());
            }
            if (param.get("driverType") != null) {
                order.setDriverType(param.get("driverType").toString());
            }
            order.setBookTime(bookTime);
            order.setOrderAmount(orderAmount);
            order.setCollectAmount(collectAmount);
            order.setUsePoints(usePoints);
            order.setAddPoints(addPoints);
            order.setPointsAmount(pointsAmount);
            order.setOrderType(orderType + "");
            order.setCreateTime(payTime);
            //微信原生支付
            order.setPayType("1");
            //银联支付
//            order.setPayType("2");

//            List< Map< String, Object > > goodsList = new ArrayList<>();
//            goodsList = (List< Map< String, Object > >) param.get("goodsList");
            //积分可抵扣金额 带获取转换参数
            if (orderType == 6 || orderType == 5) {
                if (orderType == 6) {
                    UpdateWrapper< COrder > updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", order.getId());
                    orderService.update(order, updateWrapper);
                } else {
                    orderService.save(order);
                }
                COrderGoods orderGoods = new COrderGoods();
                orderGoods.setOrderCode(orderCode);
                orderGoods.setOrderType(orderType + "");
                orderGoodsService.save(orderGoods);
            } else {
                if (orderService.save(order)) {
                    //洗车 检测线订单生成验证码
                    if (orderType == 1 || orderType == 4) {
                        for (int i = 0; i < productNum.intValue(); i++) {
                            COrderGoods orderGoods = new COrderGoods();
                            if (collectAmount.compareTo(BigDecimal.ZERO) == 0) {
                                orderGoods.setPayTime(payTime);
                                orderGoods.setPayStatus("3");
                            }
                            Map< String, Object > map = new HashMap<>();
                            if (param.get("isUsePoints").toString().equals("1")) {
                                if (usePoints.compareTo(BigDecimal.ZERO) > 0) {
                                    if (productAmount.compareTo(pointsAmount) >= 0 && pointsAmount.compareTo(BigDecimal.ZERO) > 0) {
                                        BigDecimal productCollectAmount = productAmount.subtract(pointsAmount);
                                        orderGoods.setProductCode(productCode);
                                        orderGoods.setProductName(productName);
                                        orderGoods.setOrderCode(orderCode);
                                        orderGoods.setOrderType(orderType + "");
                                        orderGoods.setCollectAmount(productCollectAmount);
                                        orderGoods.setPointsAmount(pointsAmount);
                                        orderGoods.setProductAmount(productAmount);
                                        orderGoods.setUsePoints(usePoints);
                                        orderGoods.setAddPoints(productCollectAmount.setScale(0, BigDecimal.ROUND_DOWN));
                                        orderGoods.setVerifyCode(RandomStringUtils.random(8, "1234567890"));
                                        orderGoodsService.save(orderGoods);
                                        pointsAmount = BigDecimal.ZERO;
                                        usePoints = BigDecimal.ZERO;
                                    } else if (productAmount.compareTo(pointsAmount) < 0 && pointsAmount.compareTo(BigDecimal.ZERO) > 0) {
                                        BigDecimal productCollectAmount = BigDecimal.ZERO;
                                        orderGoods.setProductCode(productCode);
                                        orderGoods.setProductName(productName);
                                        orderGoods.setOrderCode(orderCode);
                                        orderGoods.setOrderType(orderType + "");
                                        orderGoods.setCollectAmount(productCollectAmount);
                                        orderGoods.setDiscountAmount(productAmount);
                                        orderGoods.setProductAmount(productAmount);
                                        //计算使用积分
                                        BigDecimal bcsyjf = productAmount.divide(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                                        orderGoods.setUsePoints(bcsyjf);
                                        //计算本次返还积分
                                        BigDecimal bcfhjf = productCollectAmount.multiply(returnRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                                        orderGoods.setAddPoints(bcfhjf.setScale(0, BigDecimal.ROUND_DOWN));
                                        //洗车 养护订单生成验证码
                                        orderGoods.setVerifyCode(RandomStringUtils.random(8, "1234567890"));
                                        pointsAmount = pointsAmount.subtract(productAmount);
                                        usePoints = usePoints.subtract(bcsyjf);
                                        orderGoodsService.save(orderGoods);
                                    }
                                } else {
                                    orderGoods.setProductCode(productCode);
                                    orderGoods.setProductName(productName);
                                    orderGoods.setOrderCode(orderCode);
                                    orderGoods.setOrderType(orderType + "");
                                    orderGoods.setCollectAmount(productAmount);
                                    orderGoods.setPointsAmount(pointsAmount);
                                    orderGoods.setProductAmount(productAmount);
                                    orderGoods.setUsePoints(usePoints);
                                    orderGoods.setAddPoints(productAmount.setScale(0, BigDecimal.ROUND_DOWN));
                                    orderGoods.setVerifyCode(RandomStringUtils.random(8, "1234567890"));
                                    orderGoodsService.save(orderGoods);
                                }
                                //具体商品下单 若使用积分减去使用积分，取消退换积分
                                if (orderGoods.getUsePoints() != null && orderGoods.getUsePoints().compareTo(BigDecimal.ZERO) > 0) {
                                    map.clear();
                                    map.put("points", orderGoods.getUsePoints());
                                    map.put("orderCode", orderCode);
                                    map.put("userId", order.getUserId());
                                    map.put("changeType", 2);
                                    map.put("productName", orderGoods.getProductName());
                                    updatePointsHistory(map);
                                }
                            } else {
                                BigDecimal productCollectAmount = productAmount.divide(productNum).setScale(2, BigDecimal.ROUND_HALF_UP);
                                ;
                                orderGoods.setProductCode(productCode);
                                orderGoods.setProductName(productName);
                                orderGoods.setOrderCode(orderCode);
                                orderGoods.setOrderType(orderType + "");
                                orderGoods.setCollectAmount(productCollectAmount);
                                orderGoods.setPointsAmount(BigDecimal.ZERO);
                                orderGoods.setProductAmount(productAmount);
                                orderGoods.setUsePoints(BigDecimal.ZERO);
                                orderGoods.setAddPoints(productCollectAmount.setScale(0, BigDecimal.ROUND_DOWN));
                                orderGoods.setVerifyCode(RandomStringUtils.random(8, "1234567890"));
                                orderGoodsService.save(orderGoods);
                            }
                            //实付金额为0 增加返还积分
                            if (collectAmount.compareTo(BigDecimal.ZERO) == 0 && orderGoods.getAddPoints().setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) > 0) {
                                Map< String, Object > paraMap = new HashMap<>();
                                paraMap.put("points", orderGoods.getAddPoints());
                                paraMap.put("orderCode", orderCode);
                                paraMap.put("userId", order.getUserId());
                                paraMap.put("changeType", 1);
                                paraMap.put("productName", orderGoods.getProductName());
                                updatePointsHistory(paraMap);
                            }
                        }
                    } else {
                        COrderGoods orderGoods = new COrderGoods();
                        orderGoods.setOrderCode(orderCode);
                        orderGoods.setOrderType(orderType + "");
                        orderGoodsService.save(orderGoods);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        // 除救援订单24小时不接单则取消
        redisTemplate.opsForValue().set("Order:" + orderCode + "," + mobile + ",1", orderCode, 24, TimeUnit.HOURS);
        statusCode = 200;
        result.put("code", statusCode);
        result.put("data", orderCode);
        result.put("message", "所选产品已下单成功，请支付或待商家联系！");
        return result;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Map< String, Object > CalculatePrice(Map paraMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_code", orderCode);
        COrder order = orderService.getOne(orderQueryWrapper);
        QueryWrapper< COrderGoods > orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("order_code", orderCode);
        COrderGoods orderGoods = orderGoodsService.getOne(orderGoodsQueryWrapper);
        BigDecimal pointsAmount = BigDecimal.ZERO;
        BigDecimal usePoints = BigDecimal.ZERO;
        BigDecimal addPoints = BigDecimal.ZERO;
        BigDecimal collectAmount = order.getOrderAmount();
        BigDecimal bcsyjf = BigDecimal.ZERO;
        CUserInfo userInfo = userInfoService.getById(order.getUserId());
        List< String > typeList = new ArrayList<>();
        typeList.add("return");
        typeList.add("discount");
        QueryWrapper< SysDictData > sysDictDataQueryWrapper = new QueryWrapper<>();
        sysDictDataQueryWrapper.in("dict_value", typeList);
        List< SysDictData > sysDictDataList = sysDictDataService.list(sysDictDataQueryWrapper);
        BigDecimal returnRate = new BigDecimal("0.01");
        BigDecimal discountRate = new BigDecimal("1");
        for (SysDictData sysDictData : sysDictDataList) {
            if (sysDictData.getDictValue().equals("return")) {
                returnRate = new BigDecimal(sysDictData.getDictLabel());
            } else {
                discountRate = new BigDecimal(sysDictData.getDictLabel());
            }
        }
        if (paraMap.get("isUsePoints").toString().equals("1")) {
            bcsyjf = order.getOrderAmount().divide(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            usePoints = userInfo.getPoints();
            if (usePoints.compareTo(bcsyjf) >= 0) {
                usePoints = bcsyjf;
            }
            pointsAmount = usePoints.multiply(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            collectAmount = collectAmount.subtract(pointsAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        addPoints = collectAmount.multiply(returnRate).setScale(0, BigDecimal.ROUND_DOWN);
        String payTime = getNowDate();
        order.setCollectAmount(collectAmount);
        order.setUsePoints(usePoints);
        order.setAddPoints(addPoints);
        order.setPointsAmount(pointsAmount);
        if (collectAmount.compareTo(BigDecimal.ZERO) == 0) {
            try {
                if (!md5Util.validPassword(paraMap.get("payPassword").toString(), userInfo.getPassword())) {
                    result.put("code", statusCode);
                    result.put("data", "");
                    result.put("message", "个人支付密码错误，请重新填写!");
                    return result;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            order.setPayTime(payTime);
            order.setOrderStatus("5");
        }
        UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.eq("id", order.getId());
        if (orderService.update(order, orderUpdateWrapper)) {
            orderGoods.setCollectAmount(collectAmount);
            orderGoods.setUsePoints(usePoints);
            orderGoods.setAddPoints(addPoints);
            orderGoods.setPointsAmount(pointsAmount);
            if (collectAmount.compareTo(BigDecimal.ZERO) == 0) {
                orderGoods.setPayTime(payTime);
                orderGoods.setPayStatus("5");
            }
            UpdateWrapper< COrderGoods > orderGoodsUpdateWrapper = new UpdateWrapper<>();
            orderGoodsUpdateWrapper.eq("id", orderGoods.getId());
            orderGoodsService.update(orderGoods, orderGoodsUpdateWrapper);
            //更新积分明细表
            Map< String, Object > map = new HashMap<>();
            map.put("points", usePoints);
            map.put("orderCode", orderCode);
            map.put("userId", order.getUserId());
            map.put("changeType", 2);
            map.put("productName", orderGoods.getProductName());
            updatePointsHistory(map);
            if (collectAmount.compareTo(BigDecimal.ZERO) == 0 && orderGoods.getAddPoints().setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) > 0) {
                map.clear();
                map.put("points", orderGoods.getAddPoints());
                map.put("orderCode", orderCode);
                map.put("userId", order.getUserId());
                map.put("changeType", 1);
                map.put("productName", orderGoods.getProductName());
                updatePointsHistory(map);
            }
            result.put("code", 200);
            result.put("data", "");
            result.put("message", "订单计算成功!");
        }
        return result;
    }

    @PostMapping(value = "/payOrder")
    @ApiOperation(value = "订单支付接口", notes = "订单支付接口", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object payOrder(@RequestBody Map paraMap) throws Exception {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_code", orderCode);
        orderQueryWrapper.eq("order_status", 2);
        COrder begintripOrder = orderService.getOne(orderQueryWrapper);
        if (begintripOrder != null) {
            result.put("code", statusCode);
            result.put("data", dataResult);
            result.put("message", "订单已关闭，无法下单!");
            return result;
        } else {
            QueryWrapper< COrder > tripOrderQueryWrapper = new QueryWrapper<>();
            tripOrderQueryWrapper.eq("order_code", orderCode);
            COrder tripOrder = orderService.getOne(tripOrderQueryWrapper);
            if (tripOrder == null) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "订单号系统中不存在!");
                return result;
            } else if ((tripOrder.getOrderType().equals("1") || tripOrder.getOrderType().equals("4")) && !tripOrder.getOrderStatus().equals("1")) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "订单号系统中不是待支付状态!");
                return result;
            } else if (!tripOrder.getOrderType().equals("1") && !tripOrder.getOrderType().equals("4") && !tripOrder.getOrderStatus().equals("4")) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "订单号系统中不是待支付状态!");
                return result;
            }
            Map resultMap = new HashMap();
            try {
                if (!tripOrder.getOrderType().equals("1") && !tripOrder.getOrderType().equals("4")) {
                    resultMap = CalculatePrice(paraMap);
                    if (resultMap.get("code").toString().equals("500")) {
                        result.put("code", statusCode);
                        result.put("data", "");
                        result.put("message", resultMap.get("message"));
                        return result;
                    }
                }
                if (tripOrder.getCollectAmount().compareTo(BigDecimal.ZERO) > 0) {
                    String message = queryWeChatOrder(paraMap);
                    if (message.equals("订单已关闭")) {
                        String orderAmount =
                                tripOrder.getCollectAmount() == null
                                        ? "0"
                                        : tripOrder
                                        .getCollectAmount()
                                        .multiply(new BigDecimal(100))
                                        .stripTrailingZeros()
                                        .toPlainString();
                        logger.info("订单信息：" + tripOrder.toString());
                        logger.info("订单交易金额：" + orderAmount);
                        JSONObject jsonObject = new JSONObject();
                        String nonce_str = randomUtil.getRandomString(32);
                        String body = "车服务商品";
                        String sign = "";
                        String instr = "";
                        Map< String, String > packageParams = new HashMap< String, String >();
                        QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("id", tripOrder.getUserId());
                        CUserInfo userInfo = userInfoService.getOne(queryWrapper);
                        packageParams.put("appid", DataConstants.APPID);
                        packageParams.put("mch_id", DataConstants.mchId);
                        packageParams.put("body", body);
                        packageParams.put("nonce_str", nonce_str);
                        packageParams.put("notify_url", DataConstants.payNotifyUrl); // 支付成功后的回调地址
                        packageParams.put("out_trade_no", orderCode); // 商户订单号
                        packageParams.put("spbill_create_ip", "122.51.193.171"); // 终端ip地址
                        packageParams.put("total_fee", orderAmount + ""); // 支付金额，这边需要转成字符串类型，否则后面的签名会失败
                        packageParams.put("trade_type", "JSAPI"); // 支付方式
                        packageParams.put("openid", userInfo.getOpenid());
                        logger.info("支付信息：" + packageParams.toString());

                        instr = PayUtil.createLinkString(packageParams);
                        sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
                        // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
                        String xml =
                                "<xml>"
                                        + "<appid>"
                                        + DataConstants.APPID
                                        + "</appid>"
                                        + "<body>"
                                        + body
                                        + "</body>"
                                        + "<mch_id>"
                                        + DataConstants.mchId
                                        + "</mch_id>"
                                        + "<nonce_str>"
                                        + nonce_str
                                        + "</nonce_str>"
                                        + "<notify_url>"
                                        + DataConstants.payNotifyUrl
                                        + "</notify_url>"
                                        + "<openid>"
                                        + userInfo.getOpenid()
                                        + "</openid>"
                                        + "<out_trade_no>"
                                        + orderCode
                                        + "</out_trade_no>"
                                        + "<spbill_create_ip>"
                                        + "122.51.193.171"
                                        + "</spbill_create_ip>"
                                        + "<total_fee>"
                                        + orderAmount
                                        + "</total_fee>"
                                        + "<trade_type>"
                                        + "JSAPI"
                                        + "</trade_type>"
                                        + "<sign>"
                                        + sign
                                        + "</sign>"
                                        + "</xml>";
                        String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                        logger.info("调试模式_统一下单接口 请求XML数据：" + xml);
                        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);
                        // 调用统一下单接口，并接受返回的结果
                        String res = payUtil.httpRequest(payUrl, "POST", xml);

                        logger.info("调试模式_统一下单接口 返回XML数据：" + res);

                        // 将解析结果存储在HashMap中
                        System.out.println("微信支付返回html==" + res);
                        Map map = PayUtil.doXMLParse(res);
                        String return_code = (String) map.get("return_code"); // 返回状态码
                        System.out.println("预付单号===" + map.get("prepay_id"));
                        String prepay_id = null;
                        if (return_code.equals("SUCCESS") || prepay_id != null) {
                            prepay_id = (String) map.get("prepay_id"); // 返回的预付单信息
                            dataResult.put("nonceStr", nonce_str);
                            dataResult.put("package", "prepay_id=" + prepay_id);
                            Long timeStamp = System.currentTimeMillis() / 1000;
                            dataResult.put(
                                    "timeStamp", timeStamp + ""); // 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                            // 拼接签名需要的参数
                            String stringSignTemp =
                                    "appId="
                                            + DataConstants.APPID
                                            + "&nonceStr="
                                            + nonce_str
                                            + "&package=prepay_id="
                                            + prepay_id
                                            + "&signType=MD5&timeStamp="
                                            + timeStamp;
                            // 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                            String paySign =
                                    PayUtil.sign(stringSignTemp, DataConstants.PaySECRET, "utf-8").toUpperCase();

                            dataResult.put("paySign", paySign);
                            dataResult.put("signType", "MD5");
                            String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
                            String account = "dh24733"; // 用户名（必填）
                            String password = "tB1eDOsV"; // 密码（必填,明文）
                            String mobile = tripOrder.getUserPhone(); // 手机号码（必填,多条以英文逗号隔开）
                            String smssign = "【运泰出行】"; // 短信签名（必填）
                            String orderType = tripOrder.getOrderType().equals("1") ? "洗车" : tripOrder.getOrderType().equals("2") ? "养护" : tripOrder.getOrderType().equals("3") ? "维修" : tripOrder.getOrderType().equals("4") ? "检测" : tripOrder.getOrderType().equals("5") ? "驾培" : tripOrder.getOrderType().equals("6") ? "保险" : "救援";
                            String content =
                                    "您所购"
                                            + orderType
                                            + "订单,订单号为"
                                            + tripOrder.getOrderCode()
                                            + "的订单已完成支付，如有疑问请拨打：0553-3911111。";
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
                                                        httpPostUtil.httpURLConnectionPOST(
                                                                "http://www.dh3t.com/json/sms/Submit", paramData));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            statusCode = 200;
                            result.put("code", statusCode);
                            result.put("data", dataResult);
                            result.put("message", "微信订单下单成功!");
                            return result;
                        } else {
                            // 保存日志
                            CErrorLog tripErrorLog = new CErrorLog();
                            tripErrorLog.setOrderCode(orderCode);
                            tripErrorLog.setErrorMsg("微信订单下单失败,失败原因========" + map.get("return_message").toString());
                            tripErrorLog.setErrorType("7");
                            tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                            errorLogService.save(tripErrorLog);
                            logger.info("----------微信订单下单失败-------");
                            result.put("code", statusCode);
                            result.put("data", dataResult);
                            result.put("message", "微信订单下单失败!");
                            return result;
                        }
                    } else {
                        statusCode = 200;
                        result.put("code", statusCode);
                        result.put("data", "");
                        result.put("message", "微信订单下单成功!");
                        return result;
                    }
                } else {
                    if (tripOrder.getOrderStatus().equals("1")) {
                        tripOrder.setOrderStatus("3");
                    } else {
                        tripOrder.setOrderStatus("5");
                    }
                    tripOrder.setPayTime(getNowDate());
                    UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                    orderUpdateWrapper.eq("id", tripOrder.getId());
                    orderService.update(tripOrder, orderUpdateWrapper);
                    QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("order_code", orderCode);
                    List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                    logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
                    if (orderGoodsList.size() > 0) {
                        for (COrderGoods orderGoods : orderGoodsList) {
                            if (tripOrder.getOrderStatus().equals("3")) {
                                orderGoods.setPayStatus("3");
                            } else {
                                orderGoods.setPayStatus("5");
                            }
                            orderGoods.setPayTime(getNowDate());
                            UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                            updateWrapper.eq("order_code", orderCode);
                            updateWrapper.eq("id", orderGoods.getId());
                            orderGoodsService.update(orderGoods, updateWrapper);
                            if (orderGoods.getAddPoints().setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) > 0) {
                                //更新积分明细表
                                Map< String, Object > map = new HashMap<>();
                                map.put("points", orderGoods.getAddPoints());
                                map.put("orderCode", orderCode);
                                map.put("userId", tripOrder.getUserId());
                                map.put("changeType", 1);
                                map.put("productName", orderGoods.getProductName());
                                updatePointsHistory(map);
                            }
                        }
                    }
                    statusCode = 200;
                    result.put("code", statusCode);
                    result.put("data", "");
                    result.put("message", "微信订单下单成功!");
//                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return result;
    }

    /**
     * 支付回调
     * @param request
     * @param response
     * @throws InterruptedException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/pay/notify")
    @Transactional(rollbackFor = RuntimeException.class)
    public synchronized void payNotify(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String inputLine = "";
        String notityXml = "";
        String orderCode = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            // 关闭流
            request.getReader().close();
            logger.info("微信回调内容信息：" + notityXml);
            System.out.println("微信回调内容信息====" + notityXml);
            // 解析成Map
            Map< String, String > map = payUtil.doXMLParse(notityXml);
            // 判断 支付是否成功
            if ("SUCCESS".equals(map.get("result_code"))) {
                logger.info("微信回调返回是否支付成功：是");
                // 获得 返回的商户订单号
                orderCode = (String) map.get("out_trade_no");
                int flag = 0;
                QueryWrapper< COrder > queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_code", orderCode);
                queryWrapper.in("order_status", 1, 4);
                COrder userOrder = orderService.getOne(queryWrapper);
                if (userOrder != null) {
                    if (userOrder.getOrderStatus().equals("1")) {
                        userOrder.setOrderStatus("3");
                    } else {
                        userOrder.setOrderStatus("5");
                    }
                    userOrder.setPayTime(getNowDate());
                    UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                    orderUpdateWrapper.eq("id", userOrder.getId());
                    orderService.update(userOrder, orderUpdateWrapper);
                    QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("order_code", orderCode);
                    List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                    logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
                    if (orderGoodsList.size() > 0) {
                        for (COrderGoods orderGoods : orderGoodsList) {
                            if (userOrder.getOrderStatus().equals("3")) {
                                orderGoods.setPayStatus("3");
                            } else {
                                orderGoods.setPayStatus("5");
                            }
                            orderGoods.setPayTime(getNowDate());
                            UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                            updateWrapper.eq("order_code", orderCode);
                            updateWrapper.eq("id", orderGoods.getId());
                            orderGoodsService.update(orderGoods, updateWrapper);
                            if (orderGoods.getAddPoints().setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) > 0) {
                                //更新积分明细表
                                Map< String, Object > paraMap = new HashMap<>();
                                paraMap.put("points", orderGoods.getAddPoints());
                                paraMap.put("orderCode", orderCode);
                                paraMap.put("userId", userOrder.getUserId());
                                paraMap.put("changeType", 1);
                                paraMap.put("productName", orderGoods.getProductName());
                                updatePointsHistory(paraMap);
                            }
                        }
                    }
                    flag = 1;
                }
                // 判断 是否更新成功
                if (flag == 1) {
                    // 保存日志
                    CErrorLog errorLog = new CErrorLog();
                    errorLog.setOrderCode(orderCode);
                    errorLog.setErrorMsg("银联支付回调成功,支付回调时间为===" + new Timestamp(System.currentTimeMillis()));
                    errorLog.setErrorType("2");
                    errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                    errorLogService.save(errorLog);
                    logger.info("银联回调  订单号：" + orderCode + ",修改状态成功");
                    // 封装 返回值
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("<xml>");
                    buffer.append("<return_code>SUCCESS</return_code>");
                    buffer.append("<return_message>OK</return_message>");
                    buffer.append("</xml>");

                    // 给微信服务器返回 成功标示 否则会一直询问 咱们服务器 是否回调成功
                    PrintWriter writer = response.getWriter();
                    // 返回
                    writer.print(buffer.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PostMapping(value = "/payYlOrder")
    @ApiOperation(value = "银联订单支付接口", notes = "订单支付接口", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object payYlOrder(@RequestBody Map paraMap) throws Exception {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_code", orderCode);
        orderQueryWrapper.eq("order_status", 2);
        COrder begintripOrder = orderService.getOne(orderQueryWrapper);
        if (begintripOrder != null) {
            result.put("code", statusCode);
            result.put("data", dataResult);
            result.put("message", "订单已关闭，无法下单!");
            return result;
        }
        QueryWrapper< COrder > tripOrderQueryWrapper = new QueryWrapper<>();
        tripOrderQueryWrapper.eq("order_code", orderCode);
        COrder tripOrder = orderService.getOne(tripOrderQueryWrapper);
        if (tripOrder == null) {
            result.put("code", statusCode);
            result.put("data", "");
            result.put("message", "订单号系统中不存在!");
            return result;
        } else if ((tripOrder.getOrderType().equals("1") || tripOrder.getOrderType().equals("3")) && !tripOrder.getOrderStatus().equals("1")) {
            result.put("code", statusCode);
            result.put("data", "");
            result.put("message", "订单号系统中不是待支付状态!");
            return result;
        } else if (!tripOrder.getOrderType().equals("1") && !tripOrder.getOrderType().equals("3") && !tripOrder.getOrderStatus().equals("4")) {
            result.put("code", statusCode);
            result.put("data", "");
            result.put("message", "订单号系统中不是待支付状态!");
            return result;
        }
        Map resultMap = new HashMap();
        if (!tripOrder.getOrderType().equals("1") && !tripOrder.getOrderType().equals("4")) {
            resultMap = CalculatePrice(paraMap);
            if (resultMap.get("code").toString().equals("500")) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", resultMap.get("message"));
                return result;
            }
        }
        if (tripOrder.getCollectAmount().compareTo(BigDecimal.ZERO) > 0) {
            String message = queryYlOrder(paraMap);
            if (message.equals("订单已关闭")) {
                String orderAmount =
                        tripOrder.getCollectAmount() == null
                                ? "0"
                                : tripOrder
                                .getCollectAmount()
                                .multiply(new BigDecimal(100))
                                .stripTrailingZeros()
                                .toPlainString();
                logger.info("订单号：" + tripOrder.getOrderCode());
                logger.info("订单信息：" + tripOrder.toString());
                logger.info("订单交易金额：" + orderAmount);
                QueryWrapper< CUserInfo > userQuery = new QueryWrapper<>();
                userQuery.eq("id", tripOrder.getUserId());
                CUserInfo userInfo = userInfoService.getOne(userQuery);
                StringBuilder sb = new StringBuilder();
                sb.append("merOrderId=" + tripOrder.getOrderCode());
                sb.append("&totalAmount=" + orderAmount);
                sb.append("&openid=" + userInfo.getOpenid());
                //      JSONObject jsonObject =
                //          httpPostUtil.httpRequest(
                //              "http://ytpw.whyuntai.com/testpay/miniPay/order", "POST", sb.toString());
                JSONObject jsonObject =
                        httpPostUtil.httpRequest(
                                "http://ytpw.whyuntai.com:8083/miniPay/order", "POST", sb.toString());
                if (jsonObject.getString("code").equals("SUCCESS")) {
                    Map data = (Map) jsonObject.get("data");
                    logger.info(data.toString());
                    logger.info(data.get("miniPayRequest").toString());
                    statusCode = 200;
                    result.put("code", statusCode);
                    result.put("data", data.get("miniPayRequest"));
                    result.put("message", "微信订单下单成功!");
                    redisUtil.set("OrderPay:" + orderCode, data.get("miniPayRequest"), 900);
                } else {
                    // 保存日志
                    CErrorLog tripErrorLog = new CErrorLog();
                    tripErrorLog.setOrderCode(orderCode);
                    tripErrorLog.setErrorMsg("微信订单下单失败,失败原因========" + jsonObject.getString("message"));
                    tripErrorLog.setErrorType("2");
                    tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                    errorLogService.save(tripErrorLog);
                    logger.info("----------微信订单下单失败-------");
                    result.put("code", statusCode);
                    result.put("data", dataResult);
                    result.put("message", "微信订单下单失败!");
                }
            } else {
                statusCode = 200;
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "微信订单下单成功!");
            }
        } else {
            if (tripOrder.getOrderStatus().equals("1")) {
                tripOrder.setOrderStatus("3");
            } else {
                tripOrder.setOrderStatus("5");
            }
            tripOrder.setPayTime(getNowDate());
            UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.eq("id", tripOrder.getId());
            orderService.update(tripOrder, orderUpdateWrapper);
            QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("order_code", orderCode);
            List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
            logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
            if (orderGoodsList.size() > 0) {
                for (COrderGoods orderGoods : orderGoodsList) {
                    if (tripOrder.getOrderStatus().equals("3")) {
                        orderGoods.setPayStatus("3");
                    } else {
                        orderGoods.setPayStatus("5");
                    }
                    orderGoods.setPayTime(getNowDate());
                    UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("order_code", orderCode);
                    updateWrapper.eq("id", orderGoods.getId());
                    orderGoodsService.update(orderGoods, updateWrapper);
                    //更新积分明细表
                    Map< String, Object > map = new HashMap<>();
                    map.put("points", orderGoods.getAddPoints());
                    map.put("orderCode", orderCode);
                    map.put("userId", tripOrder.getUserId());
                    map.put("changeType", 1);
                    map.put("productName", orderGoods.getProductName());
                    updatePointsHistory(map);
                }
            }
            statusCode = 200;
            result.put("code", statusCode);
            result.put("data", "");
            result.put("message", "微信订单下单成功!");
        }
        return result;
    }


    /**
     * 银联支付回调
     * @throws InterruptedException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/pay/ylnotify", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = RuntimeException.class)
    @ResponseBody
    public Object payYlNotify(@RequestBody JSONObject jsonObject) throws Exception {
        System.out.println("银联支付回调内容信息====" + jsonObject.toString());
        logger.info("银联支付回调内容信息====" + jsonObject.toString());
        String payTime = jsonObject.getString("payTime");
        String orderCode = jsonObject.getString("merOrderId");
        int flag = 0;
        try {
            QueryWrapper< COrder > queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_code", orderCode);
            queryWrapper.in("order_status", 1, 4);
            COrder userOrder = orderService.getOne(queryWrapper);
            if (userOrder != null) {
                if (userOrder.getOrderStatus().equals("1")) {
                    userOrder.setOrderStatus("3");
                } else {
                    userOrder.setOrderStatus("5");
                }
                userOrder.setPayTime(getNowDate());
                UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                orderUpdateWrapper.eq("id", userOrder.getId());
                orderService.update(userOrder, orderUpdateWrapper);
                QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("order_code", orderCode);
                List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
                if (orderGoodsList.size() > 0) {
                    for (COrderGoods orderGoods : orderGoodsList) {
                        if (userOrder.getOrderStatus().equals("3")) {
                            orderGoods.setPayStatus("3");
                        } else {
                            orderGoods.setPayStatus("5");
                        }
                        orderGoods.setPayTime(getNowDate());
                        UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("order_code", orderCode);
                        updateWrapper.eq("id", orderGoods.getId());
                        orderGoodsService.update(orderGoods, updateWrapper);
                        //更新积分明细表
                        Map< String, Object > map = new HashMap<>();
                        map.put("points", orderGoods.getAddPoints());
                        map.put("orderCode", orderCode);
                        map.put("userId", userOrder.getUserId());
                        map.put("changeType", 1);
                        map.put("productName", orderGoods.getProductName());
                        updatePointsHistory(map);
                    }
                }
                flag = 1;
            }
            // 判断 是否更新成功
            if (flag == 1) {
                // 保存日志
                CErrorLog errorLog = new CErrorLog();
                errorLog.setOrderCode(orderCode);
                errorLog.setErrorMsg("银联支付回调成功,支付回调时间为===" + new Timestamp(System.currentTimeMillis()));
                errorLog.setErrorType("2");
                errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                errorLogService.save(errorLog);
                logger.info("银联回调  订单号：" + orderCode + ",修改状态成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Map map = new HashMap();
        map.put("code", "SUCCESS");
        return map;
    }

    @PostMapping(value = "/queryYlOrder")
    @ApiOperation(value = "查询订单在银联情况", notes = "查询订单在银联情况", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public String queryYlOrder(@RequestBody Map paraMap) throws Exception {
        String message = "订单已关闭";
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        sb.append("merOrderId=" + orderCode);
        try {
            // 正式环境
            JSONObject jsonObject =
                    httpPostUtil.httpRequest(
                            "http://ytpw.whyuntai.com:8083/miniPay/orderQuery", "POST", sb.toString());
            // 测试环境
            //      JSONObject jsonObject =
            //          httpPostUtil.httpRequest(
            //              "http://ytpw.whyuntai.com/testpay/miniPay/orderQuery", "POST", sb.toString());
            if (jsonObject.getString("code").equals("SUCCESS")) {
                Map data = (Map) jsonObject.get("data");
                if (data.get("status") != null) {
                    if (data.get("status").toString().equals("TRADE_SUCCESS")) {
                        QueryWrapper< COrder > orderQueryWrapper = new QueryWrapper<>();
                        orderQueryWrapper.eq("order_code", orderCode);
                        orderQueryWrapper.in("order_status", 1, 4);
                        COrder order = orderService.getOne(orderQueryWrapper);
                        if (order != null) {
                            if (order.getOrderStatus().equals("1")) {
                                order.setOrderStatus("3");
                            } else {
                                order.setOrderStatus("5");
                            }
                            order.setPayTime(getNowDate());
                            UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                            orderUpdateWrapper.eq("id", order.getId());
                            orderService.update(order, orderUpdateWrapper);
                        }
                        QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("order_code", orderCode);
                        List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                        logger.info("微信查询订单状态后开始更新客户的付款状态===" + new Timestamp(System.currentTimeMillis()));
                        if (orderGoodsList.size() > 0) {
                            for (COrderGoods orderGoods : orderGoodsList) {
                                if (order.getOrderStatus().equals("3")) {
                                    orderGoods.setPayStatus("3");
                                } else {
                                    orderGoods.setPayStatus("5");
                                }
                                UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                                updateWrapper.eq("id", orderGoods.getId());
                                orderGoodsService.update(orderGoods, updateWrapper);
                                //更新积分明细表
                                Map< String, Object > map = new HashMap<>();
                                map.put("points", orderGoods.getAddPoints());
                                map.put("orderCode", orderCode);
                                map.put("userId", order.getUserId());
                                map.put("changeType", 1);
                                map.put("productName", orderGoods.getProductName());
                                updatePointsHistory(map);
                            }
                        }
                        message = "订单已支付";
                    } else {
                        //            jsonObject =
                        //                httpPostUtil.httpRequest(
                        //                    "http://ytpw.whyuntai.com/testpay/miniPay/close", "POST",
                        // sb.toString());
                        jsonObject =
                                httpPostUtil.httpRequest(
                                        "http://ytpw.whyuntai.com:8083/miniPay/close", "POST", sb.toString());
                        message = "订单已关闭";
                    }
                } else {
                    //          jsonObject =
                    //              httpPostUtil.httpRequest(
                    //                  "http://ytpw.whyuntai.com/testpay/miniPay/close", "POST",
                    // sb.toString());
                    jsonObject =
                            httpPostUtil.httpRequest(
                                    "http://ytpw.whyuntai.com:8083/miniPay/close", "POST", sb.toString());
                    message = "订单已关闭";
                }
            } else {
                //        jsonObject =
                //            httpPostUtil.httpRequest(
                //                "http://ytpw.whyuntai.com/testpay/miniPay/close", "POST", sb.toString());
                jsonObject =
                        httpPostUtil.httpRequest(
                                "http://ytpw.whyuntai.com:8083/miniPay/close", "POST", sb.toString());
                message = "订单已关闭";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return message;
    }

    //    @PostMapping(value = "/queryWeChatOrderInfo")
    @ApiOperation(value = "查询订单是否已经支付", notes = "查询订单是否已经支付", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public String queryWeChatOrder(@RequestBody Map paraMap) throws Exception {
        String message = "订单已关闭";
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > tripOrderQueryWrapper = new QueryWrapper<>();
        tripOrderQueryWrapper.eq("order_code", orderCode);
//        tripOrderQueryWrapper.in("order_status", 1, 4);
        COrder tripOrder = orderService.getOne(tripOrderQueryWrapper);
        if (tripOrder != null) {
            String orderAmount =
                    tripOrder.getCollectAmount() == null
                            ? "0"
                            : tripOrder
                            .getCollectAmount()
                            .multiply(new BigDecimal(100))
                            .stripTrailingZeros()
                            .toPlainString();
            logger.info("订单信息：" + tripOrder.toString());
            logger.info("订单交易金额：" + orderAmount);
            JSONObject jsonObject = new JSONObject();
            String nonce_str = randomUtil.getRandomString(32);
            String sign = "";
            String instr = "";
            Map< String, String > packageParams = new HashMap< String, String >();
            QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", tripOrder.getUserId());
            CUserInfo tripUserInfo = userInfoService.getOne(queryWrapper);
            try {
                packageParams.put("appid", DataConstants.APPID);
                packageParams.put("mch_id", DataConstants.mchId);
                packageParams.put("nonce_str", nonce_str);
                packageParams.put("out_trade_no", orderCode); // 商户订单号

                instr = PayUtil.createLinkString(packageParams);
                sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
                // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
                String xml =
                        "<xml>"
                                + "<appid>"
                                + DataConstants.APPID
                                + "</appid>"
                                + "<mch_id>"
                                + DataConstants.mchId
                                + "</mch_id>"
                                + "<nonce_str>"
                                + nonce_str
                                + "</nonce_str>"
                                + "<out_trade_no>"
                                + orderCode
                                + "</out_trade_no>"
                                + "<sign>"
                                + sign
                                + "</sign>"
                                + "</xml>";
                String payUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
                logger.info("查询订单接口请求XML数据：" + xml);
                System.out.println("查询订单接口请求XML数据：" + xml);
                // 调用统一下单接口，并接受返回的结果
                String res = payUtil.httpRequest(payUrl, "POST", xml);

                logger.info("查询订单接口返回XML数据:" + res);

                // 将解析结果存储在HashMap中
                System.out.println("查询订单接口返回XML数据:" + res);
                Map map = PayUtil.doXMLParse(res);
                String return_code = (String) map.get("return_code"); // 返回状态码
                String trade_state = null;
                if (return_code.equals("SUCCESS")) {
                    if (map.get("trade_state") != null) {
                        trade_state = (String) map.get("trade_state"); // 返回订单的交易状态
                        if (trade_state.equals("SUCCESS")) {
                            if (tripOrder.getOrderStatus().equals("1")) {
                                tripOrder.setOrderStatus("3");
                            } else {
                                tripOrder.setOrderStatus("5");
                            }
                            tripOrder.setPayTime(getNowDate());
                            UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                            orderUpdateWrapper.eq("id", tripOrder.getId());
                            orderService.update(tripOrder, orderUpdateWrapper);
                            QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("order_code", orderCode);
                            List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                            logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
                            if (orderGoodsList.size() > 0) {
                                for (COrderGoods orderGoods : orderGoodsList) {
                                    if (tripOrder.getOrderStatus().equals("3")) {
                                        orderGoods.setPayStatus("3");
                                    } else {
                                        orderGoods.setPayStatus("5");
                                    }
                                    orderGoods.setPayTime(getNowDate());
                                    UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                                    updateWrapper.eq("order_code", orderCode);
                                    updateWrapper.eq("id", orderGoods.getId());
                                    orderGoodsService.update(orderGoods, updateWrapper);
                                    //更新积分明细表
                                    Map< String, Object > parasMap = new HashMap<>();
                                    parasMap.put("points", orderGoods.getAddPoints());
                                    parasMap.put("orderCode", orderCode);
                                    parasMap.put("userId", tripOrder.getUserId());
                                    parasMap.put("changeType", 1);
                                    parasMap.put("productName", orderGoods.getProductName());
                                    updatePointsHistory(parasMap);
                                }
                            }
                            message = "订单已支付";
                        } else {
//                            nonce_str = randomUtil.getRandomString(32);
//                            packageParams.clear();
//                            packageParams.put("appid", DataConstants.APPID);
//                            packageParams.put("mch_id", DataConstants.mchId);
//                            packageParams.put("nonce_str", nonce_str);
//                            packageParams.put("out_trade_no", orderCode); // 商户订单号
//
//                            instr = PayUtil.createLinkString(packageParams);
//                            sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
//                            // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
//                            xml =
//                                    "<xml>"
//                                            + "<appid>"
//                                            + DataConstants.APPID
//                                            + "</appid>"
//                                            + "<mch_id>"
//                                            + DataConstants.mchId
//                                            + "</mch_id>"
//                                            + "<nonce_str>"
//                                            + nonce_str
//                                            + "</nonce_str>"
//                                            + "<out_trade_no>"
//                                            + orderCode
//                                            + "</out_trade_no>"
//                                            + "<sign>"
//                                            + sign
//                                            + "</sign>"
//                                            + "</xml>";
//                            payUrl = "https://api.mch.weixin.qq.com/pay/closeorder";
//                            logger.info("关闭订单接口请求XML数据:" + xml);
//                            System.out.println("关闭订单接口请求XML数据:" + xml);
//                            // 调用统一下单接口，并接受返回的结果
//                            res = payUtil.httpRequest(payUrl, "POST", xml);
                            message = "订单已关闭";
                        }
                    } else {
//                        nonce_str = randomUtil.getRandomString(32);
//                        packageParams.clear();
//                        packageParams.put("appid", DataConstants.APPID);
//                        packageParams.put("mch_id", DataConstants.mchId);
//                        packageParams.put("nonce_str", nonce_str);
//                        packageParams.put("out_trade_no", orderCode); // 商户订单号
//
//                        instr = PayUtil.createLinkString(packageParams);
//                        sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
//                        // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
//                        xml =
//                                "<xml>"
//                                        + "<appid>"
//                                        + DataConstants.APPID
//                                        + "</appid>"
//                                        + "<mch_id>"
//                                        + DataConstants.mchId
//                                        + "</mch_id>"
//                                        + "<nonce_str>"
//                                        + nonce_str
//                                        + "</nonce_str>"
//                                        + "<out_trade_no>"
//                                        + orderCode
//                                        + "</out_trade_no>"
//                                        + "<sign>"
//                                        + sign
//                                        + "</sign>"
//                                        + "</xml>";
//                        payUrl = "https://api.mch.weixin.qq.com/pay/closeorder";
//                        logger.info("关闭订单接口请求XML数据:" + xml);
//                        System.out.println("关闭订单接口请求XML数据:" + xml);
//                        // 调用统一下单接口，并接受返回的结果
//                        res = payUtil.httpRequest(payUrl, "POST", xml);
                        message = "订单已关闭";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return message;
    }

    @PostMapping(value = "/queryWeChatOrderStatus")
    @ApiOperation(value = "查询订单是否已经支付", notes = "查询订单是否已经支付", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object queryWeChatOrderStatus() throws Exception {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        QueryWrapper< CRefundLog > refundLogQueryWrapper = new QueryWrapper<>();
        refundLogQueryWrapper.in("refund_status", 1, 2);
        refundLogQueryWrapper.eq("pay_type", 1);
        List< CRefundLog > cRefundLogList = refundLogService.list(refundLogQueryWrapper);
        if (cRefundLogList != null && cRefundLogList.size() > 0) {
            for (CRefundLog cRefundLog : cRefundLogList) {
                try {
                    String orderCode = cRefundLog.getOrderCode();
                    QueryWrapper< COrder > tripOrderQueryWrapper = new QueryWrapper<>();
                    tripOrderQueryWrapper.eq("order_code", orderCode);
                    COrder tripOrder = orderService.getOne(tripOrderQueryWrapper);
                    String orderAmount =
                            tripOrder.getCollectAmount() == null
                                    ? "0"
                                    : tripOrder
                                    .getCollectAmount()
                                    .multiply(new BigDecimal(100))
                                    .stripTrailingZeros()
                                    .toPlainString();
                    logger.info("订单信息：" + tripOrder.toString());
                    logger.info("订单交易金额：" + orderAmount);
                    JSONObject jsonObject = new JSONObject();
                    String nonce_str = randomUtil.getRandomString(32);
                    String sign = "";
                    String instr = "";
                    Map< String, String > packageParams = new HashMap< String, String >();
                    QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", tripOrder.getUserId());
                    CUserInfo tripUserInfo = userInfoService.getOne(queryWrapper);
                    packageParams.put("appid", DataConstants.APPID);
                    packageParams.put("mch_id", DataConstants.mchId);
                    packageParams.put("nonce_str", nonce_str);
                    packageParams.put("out_trade_no", orderCode); // 商户订单号

                    instr = PayUtil.createLinkString(packageParams);
                    sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
                    // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
                    String xml =
                            "<xml>"
                                    + "<appid>"
                                    + DataConstants.APPID
                                    + "</appid>"
                                    + "<mch_id>"
                                    + DataConstants.mchId
                                    + "</mch_id>"
                                    + "<nonce_str>"
                                    + nonce_str
                                    + "</nonce_str>"
                                    + "<out_trade_no>"
                                    + orderCode
                                    + "</out_trade_no>"
                                    + "<sign>"
                                    + sign
                                    + "</sign>"
                                    + "</xml>";
                    String payUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
                    logger.info("查询订单接口请求XML数据：" + xml);
                    System.out.println("查询订单接口请求XML数据：" + xml);
                    // 调用统一下单接口，并接受返回的结果
                    String res = payUtil.httpRequest(payUrl, "POST", xml);

                    logger.info("查询订单接口返回XML数据:" + res);

                    // 将解析结果存储在HashMap中
                    System.out.println("查询订单接口返回XML数据:" + res);
                    Map map = PayUtil.doXMLParse(res);
                    String return_code = (String) map.get("return_code"); // 返回状态码
                    String trade_state = null;
                    if (return_code.equals("SUCCESS")) {
                        if (map.get("trade_state") != null) {
                            trade_state = (String) map.get("trade_state"); // 返回订单的交易状态
                            if (trade_state.equals("REFUND")) {
                                cRefundLog.setRefundStatus("3");
                                cRefundLog.setRefundTime(getNowDate());
                                UpdateWrapper< CRefundLog > refundLogUpdateWrapper = new UpdateWrapper<>();
                                refundLogUpdateWrapper.eq("id", cRefundLog.getId());
                                refundLogService.update(cRefundLog, refundLogUpdateWrapper);
                                statusCode = 200;
                                result.put("code", statusCode);
                                result.put("data", "");
                                result.put("message", "订单已付款!");
                                COrderGoods orderGoods = orderGoodsService.getById(cRefundLog.getReleationId());
                                if (orderGoods.getUsePoints().compareTo(BigDecimal.ZERO) > 0) {
                                    //更新积分明细表
                                    Map< String, Object > parasMap = new HashMap<>();
                                    parasMap.put("points", orderGoods.getUsePoints());
                                    parasMap.put("orderCode", orderCode);
                                    parasMap.put("userId", cRefundLog.getUserId());
                                    parasMap.put("changeType", 3);
                                    parasMap.put("productName", orderGoods.getProductName());
                                    updatePointsHistory(parasMap);
                                }
                                if (orderGoods.getAddPoints().compareTo(BigDecimal.ZERO) > 0) {
                                    //更新积分明细表
                                    Map< String, Object > parasMap = new HashMap<>();
                                    parasMap.put("points", orderGoods.getAddPoints());
                                    parasMap.put("orderCode", orderCode);
                                    parasMap.put("userId", cRefundLog.getUserId());
                                    parasMap.put("changeType", 7);
                                    parasMap.put("productName", orderGoods.getProductName());
                                    updatePointsHistory(parasMap);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        }
        return result;
    }


    @PostMapping(value = "/queryWeChatOrder")
    @ApiOperation(value = "查询订单是否已经支付", notes = "查询订单是否已经支付", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object queryWeChatOrderInfo(@RequestBody Map paraMap) throws Exception {
        String message = "订单已关闭";
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > tripOrderQueryWrapper = new QueryWrapper<>();
        tripOrderQueryWrapper.eq("order_code", orderCode);
        tripOrderQueryWrapper.in("order_status", 1, 4);
        COrder tripOrder = orderService.getOne(tripOrderQueryWrapper);
        if (tripOrder != null) {
            String orderAmount =
                    tripOrder.getCollectAmount() == null
                            ? "0"
                            : tripOrder
                            .getCollectAmount()
                            .multiply(new BigDecimal(100))
                            .stripTrailingZeros()
                            .toPlainString();
            logger.info("订单信息：" + tripOrder.toString());
            logger.info("订单交易金额：" + orderAmount);
            JSONObject jsonObject = new JSONObject();
            String nonce_str = randomUtil.getRandomString(32);
            String sign = "";
            String instr = "";
            Map< String, String > packageParams = new HashMap< String, String >();
            QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", tripOrder.getUserId());
            CUserInfo tripUserInfo = userInfoService.getOne(queryWrapper);
            try {
                packageParams.put("appid", DataConstants.APPID);
                packageParams.put("mch_id", DataConstants.mchId);
                packageParams.put("nonce_str", nonce_str);
                packageParams.put("out_trade_no", orderCode); // 商户订单号

                instr = PayUtil.createLinkString(packageParams);
                sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
                // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
                String xml =
                        "<xml>"
                                + "<appid>"
                                + DataConstants.APPID
                                + "</appid>"
                                + "<mch_id>"
                                + DataConstants.mchId
                                + "</mch_id>"
                                + "<nonce_str>"
                                + nonce_str
                                + "</nonce_str>"
                                + "<out_trade_no>"
                                + orderCode
                                + "</out_trade_no>"
                                + "<sign>"
                                + sign
                                + "</sign>"
                                + "</xml>";
                String payUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
                logger.info("查询订单接口请求XML数据：" + xml);
                System.out.println("查询订单接口请求XML数据：" + xml);
                // 调用统一下单接口，并接受返回的结果
                String res = payUtil.httpRequest(payUrl, "POST", xml);

                logger.info("查询订单接口返回XML数据:" + res);

                // 将解析结果存储在HashMap中
                System.out.println("查询订单接口返回XML数据:" + res);
                Map map = PayUtil.doXMLParse(res);
                String return_code = (String) map.get("return_code"); // 返回状态码
                String trade_state = null;
                if (return_code.equals("SUCCESS")) {
                    if (map.get("trade_state") != null) {
                        trade_state = (String) map.get("trade_state"); // 返回订单的交易状态
                        if (trade_state.equals("SUCCESS")) {
                            if (tripOrder.getOrderStatus().equals("1")) {
                                tripOrder.setOrderStatus("3");
                            } else {
                                tripOrder.setOrderStatus("5");
                            }
                            tripOrder.setPayTime(getNowDate());
                            UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                            orderUpdateWrapper.eq("id", tripOrder.getId());
                            orderService.update(tripOrder, orderUpdateWrapper);
                            QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("order_code", orderCode);
                            List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                            logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
                            if (orderGoodsList.size() > 0) {
                                for (COrderGoods orderGoods : orderGoodsList) {
                                    if (tripOrder.getOrderStatus().equals("1")) {
                                        orderGoods.setPayStatus("3");
                                    } else {
                                        orderGoods.setPayStatus("5");
                                    }
                                    orderGoods.setPayTime(getNowDate());
                                    UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                                    updateWrapper.eq("order_code", orderCode);
                                    updateWrapper.eq("id", orderGoods.getId());
                                    orderGoodsService.update(orderGoods, updateWrapper);
                                    if (orderGoods.getAddPoints().setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) > 0) {
                                        //更新积分明细表
                                        Map< String, Object > parasMap = new HashMap<>();
                                        parasMap.put("points", orderGoods.getAddPoints());
                                        parasMap.put("orderCode", orderCode);
                                        parasMap.put("userId", tripOrder.getUserId());
                                        parasMap.put("changeType", 1);
                                        parasMap.put("productName", orderGoods.getProductName());
                                        updatePointsHistory(parasMap);
                                    }
                                }
                            }
                            statusCode = 200;
                            result.put("code", statusCode);
                            result.put("data", "");
                            result.put("message", "订单已支付!");
                            return result;
                        } else {
                            QueryWrapper< CPayLog > payLogQueryWrapper = new QueryWrapper<>();
                            payLogQueryWrapper.eq("order_code", orderCode);
                            CPayLog cPayLog = cPayLogService.getOne(payLogQueryWrapper);
                            if (cPayLog == null) {
                                CPayLog payLog = new CPayLog();
                                payLog.setCreateTime(getNowDate());
                                payLog.setOrderCode(orderCode);
                                payLog.setOrderType(tripOrder.getOrderType());
                                payLog.setPayStatus("1");
                                payLog.setUserId(tripOrder.getUserId());
                                payLog.setPayType(tripOrder.getPayType());
                                cPayLogService.save(payLog);
                            }
                            result.put("code", statusCode);
                            result.put("data", "");
                            result.put("message", "订单未支付!");
                            return result;
                        }
                    } else {
                        statusCode = 200;
                        result.put("code", statusCode);
                        result.put("data", "");
                        result.put("message", "订单已支付!");
                        return result;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return result;
    }

    @PostMapping(value = "/systemQueryWeChatOrder")
    @ApiOperation(value = "查询订单是否已经支付", notes = "查询订单是否已经支付", response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object systemQueryWeChatOrder() throws Exception {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        QueryWrapper< CPayLog > payLogQueryWrapper = new QueryWrapper<>();
        payLogQueryWrapper.eq("pay_status", 1);
        List< CPayLog > cPayLogList = cPayLogService.list(payLogQueryWrapper);
        if (cPayLogList != null && cPayLogList.size() > 0) {
            for (CPayLog cPayLog : cPayLogList) {
                try {
                    String orderCode = cPayLog.getOrderCode();
                    QueryWrapper< COrder > tripOrderQueryWrapper = new QueryWrapper<>();
                    tripOrderQueryWrapper.eq("order_code", orderCode);
                    tripOrderQueryWrapper.in("order_status", 1, 4);
                    COrder tripOrder = orderService.getOne(tripOrderQueryWrapper);
                    if (tripOrder != null) {
                        String orderAmount =
                                tripOrder.getCollectAmount() == null
                                        ? "0"
                                        : tripOrder
                                        .getCollectAmount()
                                        .multiply(new BigDecimal(100))
                                        .stripTrailingZeros()
                                        .toPlainString();
                        logger.info("订单信息：" + tripOrder.toString());
                        logger.info("订单交易金额：" + orderAmount);
                        JSONObject jsonObject = new JSONObject();
                        String nonce_str = randomUtil.getRandomString(32);
                        String sign = "";
                        String instr = "";
                        Map< String, String > packageParams = new HashMap< String, String >();
                        QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("id", tripOrder.getUserId());
                        CUserInfo tripUserInfo = userInfoService.getOne(queryWrapper);
                        packageParams.put("appid", DataConstants.APPID);
                        packageParams.put("mch_id", DataConstants.mchId);
                        packageParams.put("nonce_str", nonce_str);
                        packageParams.put("out_trade_no", orderCode); // 商户订单号

                        instr = PayUtil.createLinkString(packageParams);
                        sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
                        // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
                        String xml =
                                "<xml>"
                                        + "<appid>"
                                        + DataConstants.APPID
                                        + "</appid>"
                                        + "<mch_id>"
                                        + DataConstants.mchId
                                        + "</mch_id>"
                                        + "<nonce_str>"
                                        + nonce_str
                                        + "</nonce_str>"
                                        + "<out_trade_no>"
                                        + orderCode
                                        + "</out_trade_no>"
                                        + "<sign>"
                                        + sign
                                        + "</sign>"
                                        + "</xml>";
                        String payUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
                        logger.info("查询订单接口请求XML数据：" + xml);
                        System.out.println("查询订单接口请求XML数据：" + xml);
                        // 调用统一下单接口，并接受返回的结果
                        String res = payUtil.httpRequest(payUrl, "POST", xml);

                        logger.info("查询订单接口返回XML数据:" + res);

                        // 将解析结果存储在HashMap中
                        System.out.println("查询订单接口返回XML数据:" + res);
                        Map map = PayUtil.doXMLParse(res);
                        String return_code = (String) map.get("return_code"); // 返回状态码
                        String trade_state = null;
                        if (return_code.equals("SUCCESS")) {
                            if (map.get("trade_state") != null) {
                                trade_state = (String) map.get("trade_state"); // 返回订单的交易状态
                                if (trade_state.equals("SUCCESS")) {
                                    if (tripOrder.getOrderStatus().equals("1")) {
                                        tripOrder.setOrderStatus("3");
                                    } else {
                                        tripOrder.setOrderStatus("5");
                                    }
                                    tripOrder.setPayTime(getNowDate());
                                    UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                                    orderUpdateWrapper.eq("id", tripOrder.getId());
                                    orderService.update(tripOrder, orderUpdateWrapper);
                                    QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                                    queryWrapper1.eq("order_code", orderCode);
                                    List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                                    logger.info("银联回调内容开始更新商品表的付款状态===" + new Timestamp(System.currentTimeMillis()));
                                    if (orderGoodsList.size() > 0) {
                                        for (COrderGoods orderGoods : orderGoodsList) {
                                            if (tripOrder.getOrderStatus().equals("3")) {
                                                orderGoods.setPayStatus("3");
                                            } else {
                                                orderGoods.setPayStatus("5");
                                            }
                                            orderGoods.setPayTime(getNowDate());
                                            UpdateWrapper< COrderGoods > updateWrapper = new UpdateWrapper<>();
                                            updateWrapper.eq("order_code", orderCode);
                                            updateWrapper.eq("id", orderGoods.getId());
                                            orderGoodsService.update(orderGoods, updateWrapper);
                                            if (orderGoods.getAddPoints().setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) > 0) {
                                                //更新积分明细表
                                                Map< String, Object > parasMap = new HashMap<>();
                                                parasMap.put("points", orderGoods.getAddPoints());
                                                parasMap.put("orderCode", orderCode);
                                                parasMap.put("userId", tripOrder.getUserId());
                                                parasMap.put("changeType", 1);
                                                parasMap.put("productName", orderGoods.getProductName());
                                                updatePointsHistory(parasMap);
                                            }
                                        }
                                    }
                                    statusCode = 200;
                                    result.put("code", statusCode);
                                    result.put("data", "");
                                    result.put("message", "订单已支付!");
                                }
                            }
                        }
                    } else {
                        statusCode = 200;
                        result.put("code", statusCode);
                        result.put("data", "");
                        result.put("message", "订单已支付!");
                    }
                    cPayLog.setPayStatus("2");
                    cPayLog.setPayTime(getNowDate());
                    UpdateWrapper< CPayLog > payLogUpdateWrapper = new UpdateWrapper<>();
                    payLogUpdateWrapper.eq("id", cPayLog.getId());
                    cPayLogService.update(cPayLog, payLogUpdateWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        }
        return result;
    }

    @PostMapping(value = "/getOrderPage")
    @ApiOperation(value = "获取订单列表页接口", notes = "获取订单列表页接口")
    public Object getOrderPage(@RequestBody Map< String, Object > paramMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        List< Map< String, Object > > resultList = new ArrayList< Map< String, Object > >();
        Map< String, Object > data = new HashMap<>();
        Integer pageNo = Integer.valueOf(paramMap.get("pageNo").toString());
        Integer pageSize = Integer.valueOf(paramMap.get("pageSize").toString());
        //    PageHelper.startPage(pageNo, pageSize);
        List< COrderVo > list = orderMapper.getOrderList(paramMap);
//        for (COrderVo cOrderVo : list) {
//        }
        log.info("获取的订单列表===" + list.toString());
        Collections.sort(
                list,
                new Comparator< COrderVo >() {
                    public int compare(COrderVo o1, COrderVo o2) {
                        String value1 = o1.getCreateTime() == null ? "1" : o1.getCreateTime().substring(0, 10);
                        String value2 = o2.getCreateTime() == null ? "2" : o2.getCreateTime().substring(0, 10);
                        return value2.compareTo(value1);
                    }
                });
        DPage< COrderVo > dpage = new DPage<>(list, pageNo, pageSize);
        //        PageInfo<TripOrderVo> TripOrderVoPage = new PageInfo<TripOrderVo>(list);
        statusCode = 200;
        result.put("code", statusCode);
        result.put("data", dpage);
        result.put("message", "查询订单信息列表成功!");
        return result;
    }

    @PostMapping(value = "/getOrderInfo")
    @ApiOperation(value = "获取订单详情接口", notes = "获取订单详情接口")
    public Object getOrderInfo(@RequestBody Map< String, Object > paramMap, HttpServletResponse response) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        List< Map< String, Object > > resultList = new ArrayList< Map< String, Object > >();
        Map< String, Object > data = new HashMap<>();
        COrderVo orderVo = orderMapper.getOrderInfo(paramMap);
        QueryWrapper< COrderGoods > queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_code", paramMap.get("orderCode"));
        List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper);
        List< COrderGoodsVo > orderGoodsVos = new ArrayList<>();
        for (COrderGoods orderGoods : orderGoodsList) {
            COrderGoodsVo orderGoodsVo = new COrderGoodsVo();
            orderGoodsVo.setProductDescribe(orderGoods.getProductDescribe());
            orderGoodsVo.setDetailId(orderGoods.getId() + "");
            orderGoodsVo.setRefundId(orderGoods.getId() + "");
            orderGoodsVo.setPayStatus(orderGoods.getPayStatus());
            orderGoodsVo.setOrderType(orderGoods.getOrderType().equals("1")
                    ? "洗车"
                    : orderGoods.getOrderType().equals("2")
                    ? "养护"
                    : orderGoods.getOrderType().equals("3")
                    ? "维修"
                    : orderGoods.getOrderType().equals("4")
                    ? "检测"
                    : orderGoods.getOrderType().equals("5")
                    ? "驾培"
                    : orderGoods.getOrderType().equals("5")
                    ? "保险"
                    : "救援");
            orderGoodsVo.setProductCode(orderGoods.getProductCode());
            orderGoodsVo.setProductName(orderGoods.getProductName());
            orderGoodsVo.setProductAmount(orderGoods.getProductAmount());
            orderGoodsVo.setAddPoints(orderGoods.getAddPoints());
            orderGoodsVo.setCollectAmount(orderGoods.getCollectAmount());
            orderGoodsVo.setPointsAmount(orderGoods.getPointsAmount());
            orderGoodsVo.setUsePoints(orderGoods.getUsePoints());
            orderGoodsVo.setPayTime(orderGoods.getPayTime());
            orderGoodsVo.setRefundTime(orderGoods.getRefundTime());
            if (orderGoods.getOrderType().equals("1") || orderGoods.getOrderType().equals("4")) {
                try {
                    orderGoodsVo.setVerifyCode(qrCodeUtil.createCode(orderGoods.getOrderCode() + "," + orderGoods.getVerifyCode()));
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            orderGoodsVos.add(orderGoodsVo);
        }
        orderVo.setOrderGoodsList(orderGoodsVos);
        statusCode = 200;
        result.put("code", statusCode);
        result.put("data", orderVo);
        result.put("message", "查询订单信息成功!");
        return result;
    }

    @PostMapping(value = "/systemCancelOrder")
    @ApiOperation(value = "取消未支付的订单")
    @Transactional(rollbackFor = RuntimeException.class)
    public String systemCancelOrder(@RequestBody Map paraMap) {
        String message = "";
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        String orderStatus = paraMap.get("orderStatus") == null ? "" : paraMap.get("orderStatus").toString();
        QueryWrapper< COrder > queryWrapperOrder = new QueryWrapper<>();
        queryWrapperOrder.eq("order_code", orderCode);
        queryWrapperOrder.eq("order_status", orderStatus);
        COrder order = orderService.getOne(queryWrapperOrder);
        if (order == null) {
            result.put("code", statusCode);
            result.put("data", "");
            result.put("message", "订单号系统中不存在或不是待支付状态!");
            // 保存日志
            CErrorLog tripErrorLog = new CErrorLog();
            tripErrorLog.setOrderCode(orderCode);
            tripErrorLog.setErrorMsg("订单号系统中不存在或不是待支付状态!");
            tripErrorLog.setErrorType("3");
            tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
            errorLogService.save(tripErrorLog);
        } else {
            cancelOrder(paraMap);
        }
        return "车票取消成功!!!";
    }

    @PostMapping(value = "/cancelOrder")
    @ApiOperation(
            value = "取消未支付的订单",
            notes =
                    "                                                                                                                                                                                                                                                                            ",
            response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object cancelOrder(@RequestBody Map paraMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String message = null;
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > queryWrapperOrder = new QueryWrapper<>();
        queryWrapperOrder.eq("order_code", orderCode);
        COrder order = orderService.getOne(queryWrapperOrder);
        if (order == null) {
            result.put("code", statusCode);
            result.put("data", "");
            result.put("message", "订单号系统中不存在!");
            return result;
        } else {
            int orderType = Integer.parseInt(order.getOrderType());
            int orderStatus = Integer.parseInt(order.getOrderStatus());
            if ((orderType == 1 || orderType == 4) && (orderStatus > 1)) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "订单已支付，不允许取消!");
                return result;
            } else if ((orderType == 5 || orderType == 6 || orderType == 7) && orderStatus > 1) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "救援、保险及驾培类订单已被接单,不允许取消!");
                return result;
            } else if ((orderType == 2 || orderType == 3) && orderStatus > 3) {
                result.put("code", statusCode);
                result.put("data", "");
                result.put("message", "养护、维修类订单已被服务,不允许取消!");
                return result;
            }
            try {
                //微信原生支付
                message = queryWeChatOrder(paraMap);
                //银联支付
//                message = queryYlOrder(paraMap);
                if (message.equals("订单已关闭")) {
                    breakpoint:
                    if (order != null) {
                        order.setOrderStatus("2");
                        order.setCancelTime(getNowDate());
                        UpdateWrapper< COrder > updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("id", order.getId());
                        if (orderService.update(order, updateWrapper)) {
                            QueryWrapper< COrderGoods > queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("order_code", orderCode);
                            List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper1);
                            for (COrderGoods orderGoods : orderGoodsList) {
                                orderGoods.setPayStatus(2 + "");
                                UpdateWrapper< COrderGoods > orderGoodsUpdateWrapper =
                                        new UpdateWrapper<>();
                                orderGoodsUpdateWrapper.eq("id", orderGoods.getId());
                                orderGoodsService.update(orderGoods, orderGoodsUpdateWrapper);
                            }
                            if (orderType == 1 || orderType == 4) {
                                //更新积分明细表
                                Map< String, Object > map = new HashMap<>();
                                map.put("points", order.getUsePoints());
                                map.put("orderCode", orderCode);
                                map.put("userId", order.getUserId());
                                map.put("changeType", 5);
                                map.put("productName", orderGoodsList.get(0).getProductName());
                                updatePointsHistory(map);
                            }
                        }
                        result.put("code", "200");
                        result.put("data", "");
                        result.put("message", "订单取消成功!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return result;
    }

    @PostMapping(value = "/refundOrder")
    @ApiOperation(value = "手工退款接口", notes = "手工退款接口")
    @Transactional(rollbackFor = RuntimeException.class)
    public Object refundOrder(HttpServletRequest request, @RequestBody Map paraMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        Map< String, Object > dataResult = new HashMap<>();
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        String userId = paraMap.get("userId") == null ? "" : paraMap.get("userId").toString();
        QueryWrapper< COrderGoods > queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", paraMap.get("refundId"));
        queryWrapper.eq("pay_status", 3);
        queryWrapper.in("order_type", 1, 4);
        COrderGoods orderGoods = orderGoodsService.getOne(queryWrapper);
        try {
            if (orderGoods == null) {
                result.put("code", statusCode);
                result.put("data", dataResult);
                result.put("message", "订单状态不是未使用状态，不允许退款!");
            } else {
                orderGoods.setPayStatus(6 + "");
                UpdateWrapper< COrderGoods > orderGoodsUpdateWrapper = new UpdateWrapper<>();
                orderGoodsUpdateWrapper.eq("id", orderGoods.getId());
                orderGoodsService.update(orderGoods, orderGoodsUpdateWrapper);
                //实付金额为0只更新积分明细表
                if (orderGoods.getCollectAmount().compareTo(BigDecimal.ZERO) == 0) {
                    Map< String, Object > map = new HashMap<>();
                    map.put("points", orderGoods.getUsePoints().compareTo(orderGoods.getAddPoints()) >= 0 ? orderGoods.getUsePoints().subtract(orderGoods.getAddPoints()) : orderGoods.getAddPoints().subtract(orderGoods.getUsePoints()));
                    map.put("orderCode", orderCode);
                    map.put("userId", userId);
                    map.put("changeType", 3);
                    map.put("productName", orderGoods.getProductName());
                    updatePointsHistory(map);
                    statusCode = 200;
                    result.put("code", statusCode);
                    result.put("data", dataResult);
                    result.put("message", "商品已退款,使用的积分已退回至你个人积分余额！");
                } else {
                    QueryWrapper< COrder > orderWrapper = new QueryWrapper<>();
                    orderWrapper.eq("order_code", orderCode);
                    COrder order = orderService.getOne(orderWrapper);
                    CRefundLog refundLog = new CRefundLog();
                    refundLog.setOrderType(orderGoods.getOrderType());
                    refundLog.setRefundType("人工");
                    // 依据订单来判断
                    refundLog.setPayType(order.getPayType());
                    BigDecimal orderAmout =
                            order.getCollectAmount() == null
                                    ? order.getOrderAmount()
                                    : order.getCollectAmount();
                    BigDecimal refundAmount =
                            orderGoods
                                    .getCollectAmount();
                    refundLog.setUserId(userId);
                    refundLog.setOrderAmount(orderAmout);
                    refundLog.setRefundAmount(refundAmount);
                    refundLog.setFeeRate(BigDecimal.ZERO);
                    refundLog.setOrderCode(orderCode);
                    // 设置退款订单号
                    refundLog.setRefundOrderCode(generateOrderNumber());
                    refundLog.setReleationId(orderGoods.getId() + "");
                    refundLog.setCreateTime(getNowDate());
                    if (refundLogService.save(refundLog)) {
                        System.out.println("发送退款短信====");
                        logger.info(
                                "发送退款短信开始===="
                                        + new Timestamp(System.currentTimeMillis())
                                        + "手机号===="
                                        + order.getUserPhone());
                        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
                        String account = "dh24733"; // 用户名（必填）
                        String password = "tB1eDOsV"; // 密码（必填,明文）
                        String mobile = order.getUserPhone(); // 手机号码（必填,多条以英文逗号隔开）
                        String sign = "【运泰出行】"; // 短信签名（必填）
                        String orderTypeName = "";
                        if (order.getOrderType().equals("1")) {
                            orderTypeName = "洗车订单";
                        } else {
                            orderTypeName = "检测线订单";
                        }
                        String content =
                                "您已退订订单号为"
                                        + orderCode
                                        + ",订单类型为"
                                        + orderTypeName
                                        + ",预约时间为"
                                        + order.getBookTime()
                                        + "订单,退单金额会在3到5工作日内返还到你的微信账户，如有疑问请拨打：0553-3911111。";
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
                                                    httpPostUtil.httpURLConnectionPOST(
                                                            "http://www.dh3t.com/json/sms/Submit", paramData));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("发送退款短信结束====");
                        logger.info(
                                "发送退款短信结束===="
                                        + new Timestamp(System.currentTimeMillis())
                                        + "手机号===="
                                        + order.getUserPhone());
                        statusCode = 200;
                        result.put("code", statusCode);
                        result.put("data", dataResult);
                        result.put("message", "商品已申请退款,退款金额会在后续3到5工作日内返还到您的微信账户！！");
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }


    @PostMapping(value = "/orderRefund")
    @ApiOperation(value = "银联批量退款接口", notes = "银联批量退款接口")
    @Transactional(rollbackFor = RuntimeException.class)
    public String orderRefund() {
        logger.info("银联退款开始");
        List< CRefundLog > refundLogList = refundLogMapper.queryYlRefundList();
        String sign = "";
        String instr = "", nonce_str = "", refundOrderCode = "", outTradeNo = "";
        for (CRefundLog refundLog : refundLogList) {
            logger.info("银联退款订单号=====" + refundLog.getOrderCode());
            // 设置商户退款单号
            refundOrderCode =
                    refundLog.getRefundOrderCode() == null
                            ? generateOrderNumber()
                            : refundLog.getRefundOrderCode();
            nonce_str = randomUtil.getRandomString(32);
            Map< String, String > packageParams = new HashMap< String, String >();
            outTradeNo = refundLog.getOrderCode();
            String total_fee =
                    refundLog.getOrderAmount() == null
                            ? "0"
                            : refundLog
                            .getOrderAmount()
                            .multiply(new BigDecimal(100))
                            .stripTrailingZeros()
                            .toPlainString();
            String refund_fee =
                    refundLog.getOrderAmount() == null
                            ? "100"
                            : refundLog
                            .getRefundAmount()
                            .multiply(new BigDecimal(100))
                            .stripTrailingZeros()
                            .toPlainString();
            StringBuilder sb = new StringBuilder();
            sb.append("merOrderId=" + refundLog.getOrderCode());
            sb.append("&refundAmount=" + refund_fee);
            try {
                logger.info("开始调用银联退款接口方法=====" + sb.toString());
                //        JSONObject jsonObject =
                //            httpPostUtil.httpRequest(
                //                "http://ytpw.whyuntai.com/testpay/miniPay/refund", "POST", sb.toString());
                JSONObject jsonObject =
                        httpPostUtil.httpRequest(
                                "http://ytpw.whyuntai.com:8083/miniPay/refund", "POST", sb.toString());
                logger.info("调用银联退款接口返回结果=====" + jsonObject.toString());
                if (jsonObject.getString("code").equals("SUCCESS")) {
                    String refundTime = getNowDate();
                    refundLog.setRefundStatus("3");
                    refundLog.setRefundTime(refundTime);
                    UpdateWrapper< CRefundLog > updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", refundLog.getId());
                    refundLogService.update(refundLog, updateWrapper);
                    COrderGoods orderGoods = orderGoodsService.getById(refundLog.getReleationId());
                    //更新退款时间
                    orderGoods.setRefundTime(refundTime);
                    UpdateWrapper< COrderGoods > orderGoodsUpdateWrapper = new UpdateWrapper<>();
                    orderGoodsUpdateWrapper.eq("id", orderGoods.getId());
                    orderGoodsService.update(orderGoods, orderGoodsUpdateWrapper);
                    //更新积分明细表
                    Map< String, Object > map = new HashMap<>();
                    map.put("points", orderGoods.getUsePoints().compareTo(orderGoods.getAddPoints()) >= 0 ? orderGoods.getUsePoints().subtract(orderGoods.getAddPoints()) : orderGoods.getAddPoints().subtract(orderGoods.getUsePoints()));
                    map.put("orderCode", refundLog.getOrderCode());
                    map.put("userId", refundLog.getUserId());
                    map.put("changeType", 3);
                    map.put("productName", orderGoods.getProductName());
                    updatePointsHistory(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return "微信退款成功";
    }

    @PostMapping(value = "/systemRefund")
    @ApiOperation(value = "系统批量退款接口", notes = "系统批量退款接口")
    @Transactional(rollbackFor = RuntimeException.class)
    public String systemRefund() {
        List< CRefundLog > tripRefundLogList = refundLogMapper.queryWaiteRefundList();
        String sign = "";
        //    String nonce_str = randomUtil.getRandomString(32);
        String instr = "", nonce_str = "", refundOrderCode = "", outTradeNo = "";
        for (CRefundLog tripRefundLog : tripRefundLogList) {
            // 设置商户退款单号
            refundOrderCode =
                    tripRefundLog.getRefundOrderCode() == null
                            ? generateOrderNumber()
                            : tripRefundLog.getRefundOrderCode();
            nonce_str = randomUtil.getRandomString(32);
            Map< String, String > packageParams = new HashMap< String, String >();
            outTradeNo = tripRefundLog.getOrderCode();
            String total_fee =
                    tripRefundLog.getOrderAmount() == null
                            ? "0"
                            : tripRefundLog
                            .getOrderAmount()
                            .multiply(new BigDecimal(100))
                            .stripTrailingZeros()
                            .toPlainString();
            String refund_fee =
                    tripRefundLog.getOrderAmount() == null
                            ? "1"
                            : tripRefundLog
                            .getRefundAmount()
                            .multiply(new BigDecimal(100))
                            .stripTrailingZeros()
                            .toPlainString();
            // 设置请求参数(小程序ID)
            packageParams.put("appid", DataConstants.APPID);
            // 设置请求参数(商户号)
            packageParams.put("mch_id", DataConstants.mchId);
            // 设置请求参数(随机字符串)
            packageParams.put("nonce_str", nonce_str);
            // 设置请求参数(商户订单号)
            packageParams.put("out_trade_no", outTradeNo);
            // 设置请求参数(商户退款单号)
            packageParams.put("out_refund_no", refundOrderCode);
            // 设置请求参数(订单金额)
            packageParams.put("total_fee", total_fee);
            // 设置请求参数(退款金额)
            packageParams.put("refund_fee", refund_fee);
            // 设置回调测试地址
            packageParams.put("notify_url", DataConstants.refundNotifyUrl);

            instr = PayUtil.createLinkString(packageParams);
            sign = PayUtil.sign(instr, DataConstants.PaySECRET, "utf-8").toUpperCase();
            StringBuffer paramBuffer = new StringBuffer();
            paramBuffer.append("<xml>");
            paramBuffer.append("<appid>" + DataConstants.APPID + "</appid>");
            paramBuffer.append("<mch_id>" + DataConstants.mchId + "</mch_id>");
            paramBuffer.append("<nonce_str>" + nonce_str + "</nonce_str>");
            paramBuffer.append("<sign>" + sign + "</sign>");
            paramBuffer.append(
                    "<out_refund_no>" + packageParams.get("out_refund_no") + "</out_refund_no>");
            paramBuffer.append("<out_trade_no>" + packageParams.get("out_trade_no") + "</out_trade_no>");
            paramBuffer.append("<refund_fee>" + packageParams.get("refund_fee") + "</refund_fee>");
            paramBuffer.append("<total_fee>" + packageParams.get("total_fee") + "</total_fee>");
            paramBuffer.append("<notify_url>" + packageParams.get("notify_url") + "</notify_url>");
            paramBuffer.append("</xml>");
            logger.info("调试模式_统一退款接口 请求XML数据：" + paramBuffer.toString());
            try {
                Map< String, String > map =
                        payUtil.doXMLParse(
                                payUtil.doRefund(
                                        request,
                                        "https://api.mch.weixin.qq.com/secapi/pay/refund",
                                        new String(paramBuffer.toString().getBytes(), "ISO8859-1")));
                logger.info("调试模式_统一退款接口 请求返回数据：" + map.toString());
                if (map != null) {
                    if (StringUtils.isNotBlank(map.get("return_code"))
                            && "SUCCESS".equals(map.get("return_code"))) {
                        if (StringUtils.isBlank(map.get("err_code_des"))) {
                            tripRefundLog.setRefundStatus("2");
                            tripRefundLog.setRefundError(map.get("err_code_des"));
                            UpdateWrapper< CRefundLog > refundLogUpdateWrapper = new UpdateWrapper<>();
                            refundLogUpdateWrapper.eq("id", tripRefundLog.getId());
                            refundLogService.update(tripRefundLog, refundLogUpdateWrapper);
                        }
                    } else {
                        CErrorLog tripErrorLog = new CErrorLog();
                        tripErrorLog.setOrderCode(tripRefundLog.getOrderCode());
                        tripErrorLog.setErrorMsg(
                                map.get("return_message") == null ? "退款失败" : map.get("return_message"));
                        tripErrorLog.setErrorType("8");
                        tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                        errorLogService.save(tripErrorLog);
                    }
                } else if (map == null) {
                    // 保存日志
                    CErrorLog tripErrorLog = new CErrorLog();
                    tripErrorLog.setOrderCode(tripRefundLog.getOrderCode());
                    tripErrorLog.setErrorMsg("退款失败");
                    tripErrorLog.setErrorType("8");
                    tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                    errorLogService.save(tripErrorLog);
                }
            } catch (UnsupportedEncodingException e) {
                // 保存日志
                CErrorLog tripErrorLog = new CErrorLog();
                tripErrorLog.setOrderCode(tripRefundLog.getOrderCode());
                tripErrorLog.setErrorMsg(e.getMessage());
                tripErrorLog.setErrorType("8");
                tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                errorLogService.save(tripErrorLog);
                logger.info("微信 退款 异常：" + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                // 保存日志
                CErrorLog tripErrorLog = new CErrorLog();
                tripErrorLog.setOrderCode(tripRefundLog.getOrderCode());
                tripErrorLog.setErrorMsg(e.getMessage());
                tripErrorLog.setErrorType("8");
                tripErrorLog.setCreateTime(new Timestamp(System.currentTimeMillis()) + "");
                errorLogService.save(tripErrorLog);
                logger.info("微信 退款 异常：" + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException();
            }
            logger.info("微信 退款 失败");
        }
        return "微信退款成功";
    }

    /**
     * 退款回调
     * @param request
     * @param response
     * @throws InterruptedException
     */
    @RequestMapping(value = "/refund/notify")
    public void refundNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("退款  微信回调接口方法 start");
        String inputLine = "";
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            // 关闭流
            request.getReader().close();
            logger.info("退款  微信回调内容信息：" + notityXml);
            // 解析成Map
            Map< String, String > map = payUtil.doXMLParse(notityXml);
            // 判断 退款是否成功
            if ("SUCCESS".equals(map.get("return_code"))) {
                logger.info("退款  微信回调返回是否退款成功：是");
                // 获得 返回的商户订单号
                //        String passMap = aesUtil.decryptData(map.get("req_info"));
                String passMap = refundAESutil.decryptData(map.get("req_info"));
                // 拿到解密信息
                map = payUtil.doXMLParse(passMap);
                // 拿到解密后的订单号
                String refundOrderNo = map.get("out_refund_no");
                logger.info("退款  微信回调返回商户订单号：" + map.get("out_trade_no"));
                int flag = 0;
                // 退款成功 修改订单状态 通知微信成功回调
                QueryWrapper< CRefundLog > queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("refund_order_code", refundOrderNo);
                queryWrapper.eq("refund_status", 2);
                CRefundLog tripRefundLog = refundLogService.getOne(queryWrapper);
                String orderNo = tripRefundLog.getOrderCode();
                if (tripRefundLog != null) {
                    tripRefundLog.setRefundStatus("3");
                    tripRefundLog.setRefundTime(getNowDate());
                    UpdateWrapper< CRefundLog > updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", tripRefundLog.getId());
                    refundLogService.update(tripRefundLog, updateWrapper);
                    COrderGoods orderGoods = orderGoodsService.getById(tripRefundLog.getReleationId());
                    if (orderGoods.getUsePoints().compareTo(BigDecimal.ZERO) > 0) {
                        //更新积分明细表
                        Map< String, Object > paraMap = new HashMap<>();
                        paraMap.put("points", orderGoods.getAddPoints());
                        paraMap.put("orderCode", orderNo);
                        paraMap.put("userId", tripRefundLog.getUserId());
                        paraMap.put("changeType", 3);
                        paraMap.put("productName", orderGoods.getProductName());
                        updatePointsHistory(paraMap);
                    }
                }
                if (flag == 1) {
                    logger.info("退款 微信回调 更改订单状态成功");
                }
            } else {
                // 获得 返回的商户订单号
                String passMap = AESUtil.decryptData(map.get("req_info"));
                // 拿到解密信息
                map = payUtil.doXMLParse(passMap);
                // 拿到解密后的退款订单号
                String refundOrderNo = map.get("out_trade_no");
                int flag = 0;
                // 退款成功 修改订单状态 通知微信成功回调
                QueryWrapper< CRefundLog > queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("refund_order_code", refundOrderNo);
                CRefundLog tripRefundLog = refundLogService.getOne(queryWrapper);
                String orderNo = tripRefundLog.getOrderCode();
                if (tripRefundLog != null) {
                    tripRefundLog.setRefundStatus("4");
                    UpdateWrapper< CRefundLog > updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", tripRefundLog.getId());
                    refundLogService.update(tripRefundLog, updateWrapper);
                }
                if (flag == 1) {
                    logger.info("退款 微信回调返回是否退款成功：否");
                }
            }
            response.setContentType("text/xml");
            // 给微信服务器返回 成功标示 否则会一直询问 咱们服务器 是否回调成功
            PrintWriter writer = response.getWriter();
            // 封装 返回值
            StringBuffer buffer = new StringBuffer();
            buffer.append("<xml>");
            buffer.append("<return_code>SUCCESS</return_code>");
            buffer.append("<return_message>OK</return_message>");
            buffer.append("</xml>");
            // 返回
            writer.print(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PostMapping(value = "/updatePointsHistory")
    @ApiOperation(
            value = "记录积分变动记录",
            notes =
                    "                                                                                                                                                                                                                                                                            ",
            response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object updatePointsHistory(@RequestBody Map paraMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String message = null;
        try {
            CPointsHistory pointsHistory = new CPointsHistory();
            pointsHistory.setChangeType(paraMap.get("changeType").toString());
            pointsHistory.setOrderCode(paraMap.get("orderCode").toString());
            pointsHistory.setChangeTime(getNowDate());
            pointsHistory.setPoints(new BigDecimal(paraMap.get("points").toString()));
            pointsHistory.setUserId(paraMap.get("userId").toString());
            if (paraMap.get("productName") != null) {
                pointsHistory.setProductName(paraMap.get("productName").toString());
            }
            if (pointsHistoryService.save(pointsHistory)) {
                String changeType = paraMap.get("changeType").toString();
                QueryWrapper< CUserInfo > queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", paraMap.get("userId"));
                CUserInfo userInfo = userInfoService.getOne(queryWrapper1);
                if (changeType.equals("2") || changeType.equals("7")) {
                    userInfo.setPoints(userInfo.getPoints().subtract(pointsHistory.getPoints()));
                } else {
                    userInfo.setPoints(userInfo.getPoints().add(pointsHistory.getPoints()));
                }
                UpdateWrapper< CUserInfo > updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", userInfo.getId());
                userInfoService.update(userInfo, updateWrapper);
            }
            result.put("code", "200");
            result.put("data", "");
            result.put("message", "订单取消成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }

    @PostMapping(value = "/getPointsList")
    @ApiOperation(
            value = "记录积分变动记录",
            notes =
                    "                                                                                                                                                                                                                                                                            ",
            response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object getPointsList(@RequestBody Map< String, Object > paramMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String message = null;
        List< Map< String, Object > > pointsList = new ArrayList<>();
        try {
            Integer pageNo = paramMap.get("pageNo") == null ? 1 : Integer.valueOf(paramMap.get("pageNo").toString());
            Integer pageSize = paramMap.get("pageSize") == null ? 10 : Integer.valueOf(paramMap.get("pageSize").toString());
            pointsList = pointsHistoryMapper.getPointsList(paramMap);
            DPage< Map< String, Object > > dpage = new DPage<>(pointsList, pageNo, pageSize);
            result.put("code", "200");
            result.put("data", dpage);
            result.put("message", "获取积分明细成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }

    @PostMapping(value = "/createEvaluate")
    @ApiOperation(
            value = "新增评价",
            notes = "",
            response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object createEvaluate(@RequestBody Map paraMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String message = null;
        try {
            CEvaluate evaluate = new CEvaluate();
            evaluate.setEvaluateContent(paraMap.get("evaluateContent") == null ? "" : paraMap.get("evaluateContent").toString());
            evaluate.setOrderCode(paraMap.get("orderCode").toString());
            evaluate.setShopId(paraMap.get("shopId").toString());
            evaluate.setCreateTime(getNowDate());
            evaluate.setStarRating(Integer.parseInt(paraMap.get("startRating") == null ? "5" : paraMap.get("startRating").toString()));
            evaluate.setUserId(paraMap.get("userId").toString());
            CUserInfo userInfo = userInfoService.getById(paraMap.get("userId").toString());
            evaluate.setNickName(userInfo.getNickName() == null ? userInfo.getUserPhone() : userInfo.getNickName());
            evaluateService.save(evaluate);
            QueryWrapper< SysDictData > sysDictDataQueryWrapper = new QueryWrapper<>();
            sysDictDataQueryWrapper.eq("dict_value", "evaluate");
            SysDictData sysDictData = sysDictDataService.getOne(sysDictDataQueryWrapper);
            Map< String, Object > map = new HashMap<>();
            map.put("points", sysDictData.getDictLabel());
            map.put("orderCode", evaluate.getOrderCode());
            map.put("userId", paraMap.get("userId").toString());
            map.put("changeType", 4);
            updatePointsHistory(map);
            CShop shop = shopService.getById(evaluate.getShopId());
            BigDecimal mqzf = shop.getScore().multiply(shop.getNum()).setScale(2, BigDecimal.ROUND_HALF_UP);
            mqzf = mqzf.add(new BigDecimal(evaluate.getStarRating() + ""));
            BigDecimal zrs = shop.getNum().add(new BigDecimal("1"));
            BigDecimal bcpf = mqzf.divide(zrs).setScale(1, BigDecimal.ROUND_HALF_UP);
            shop.setScore(bcpf);
            shop.setNum(zrs);
            UpdateWrapper< CShop > shopUpdateWrapper = new UpdateWrapper<>();
            shopUpdateWrapper.eq("id", shop.getId());
            shopService.update(shop, shopUpdateWrapper);
            result.put("code", "200");
            result.put("data", "");
            result.put("message", "订单评价成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }

//    @ApiOperation(value = "上传接口", notes = "上传接口", response = Map.class)
//    @PostMapping(value = "/upload")
//    @Transactional(rollbackFor = RuntimeException.class)
//    public Object upload(@RequestParam Map< String, Object > param) throws IOException {
//        Map< String, Object > result = new HashMap<>();
//        Map< String, Object > data = new HashMap<>();
//        int statusCode = 500;
//        MultipartFile file = null;
//        if (null != param.get("fileStr") && !param.get("fileStr").equals("")) {
//            String avatar = "data:image/png;base64," + param.get("fileStr").toString();
//            file = BASE64DecodedMultipartFile.base64ToMultipart(avatar);
//        }
//        System.out.println("文件转换成功=====" + file.getInputStream());
//        if (file != null) {
//            String orderCode = param.get("orderCode") == null ? "" : param.get("orderCode").toString();
//            if (orderCode.equals("")) {
//                orderCode = generateOrderNumber();
//            }
//            String fileType = param.get("fileType").toString();
//            try {
//                QueryWrapper< COrder > queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("order_code", orderCode);
//                COrder order = orderService.getOne(queryWrapper);
//                String headImg = uploadUtil.upload(file);
//                COrder orderInfo = new COrder();
//                if (order != null) {
//                    orderInfo = order;
//                } else {
//                    orderInfo.setOrderCode(orderCode);
//                }
//                if (fileType.equals("driverLicenseFront")) {
//                    orderInfo.setDriverLicenseFront(headImg);
//                } else if (fileType.equals("driverLicenseBack")) {
//                    orderInfo.setDriverLicenseBack(headImg);
//                }
//                if (order != null) {
//                    UpdateWrapper< COrder > updateWrapper = new UpdateWrapper<>();
//                    updateWrapper.eq("id", orderInfo.getId());
//                    orderService.update(orderInfo, updateWrapper);
//                } else {
//                    orderService.save(orderInfo);
//                }
//                data.put("filePath", headImg);
//                data.put("orderCode", orderCode);
//                statusCode = 200;
//                result.put("code", statusCode);
//                result.put("data", data);
//                result.put("message", "图片上传成功!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        } else {
//            result.put("code", statusCode);
//            result.put("data", data);
//            result.put("message", "文件不存在!");
//        }
//        return result;
//    }

    @ApiOperation(value = "上传接口", notes = "上传接口", response = Map.class)
    @PostMapping(value = "/upload")
    @Transactional(rollbackFor = RuntimeException.class)
    public Object upload(@RequestParam("file") MultipartFile file, String orderCode, String fileType) throws IOException {
        Map< String, Object > result = new HashMap<>();
        Map< String, Object > data = new HashMap<>();
        int statusCode = 500;
        System.out.println("文件转换成功=====" + file.getInputStream());
        if (file != null) {
            if (orderCode.equals("")) {
                orderCode = generateOrderNumber();
            }
            try {
                QueryWrapper< COrder > queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_code", orderCode);
                COrder order = orderService.getOne(queryWrapper);
                String headImg = uploadUtil.upload(file);
                COrder orderInfo = new COrder();
                if (order != null) {
                    orderInfo = order;
                } else {
                    orderInfo.setOrderCode(orderCode);
                }
                if (fileType.equals("driverLicenseFront")) {
                    orderInfo.setDriverLicenseFront(headImg);
                } else if (fileType.equals("driverLicenseBack")) {
                    orderInfo.setDriverLicenseBack(headImg);
                }
                if (order != null) {
                    UpdateWrapper< COrder > updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", orderInfo.getId());
                    orderService.update(orderInfo, updateWrapper);
                } else {
                    orderService.save(orderInfo);
                }
                data.put("filePath", headImg);
                data.put("orderCode", orderCode);
                statusCode = 200;
                result.put("code", statusCode);
                result.put("data", data);
                result.put("message", "图片上传成功!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        } else {
            result.put("code", statusCode);
            result.put("data", data);
            result.put("message", "文件不存在!");
        }
        return result;
    }


    @PostMapping(value = "/payFailOrder")
    @ApiOperation(
            value = "支付订单",
            notes =
                    "                                                                                                                                                                                                                                                                            ",
            response = Map.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Object payFailOrder(@RequestBody Map paraMap) {
        Map< String, Object > result = new HashMap<>();
        int statusCode = 500;
        String message = null;
        String orderCode = paraMap.get("orderCode") == null ? "" : paraMap.get("orderCode").toString();
        QueryWrapper< COrder > queryWrapperOrder = new QueryWrapper<>();
        queryWrapperOrder.eq("order_code", orderCode);
        COrder order = orderService.getOne(queryWrapperOrder);
        try {
            if (!order.getOrderType().equals("1") && !order.getOrderType().equals("4")) {
                //更新积分明细表
                Map< String, Object > map = new HashMap<>();
                map.put("points", order.getUsePoints());
                map.put("orderCode", orderCode);
                map.put("userId", order.getUserId());
                map.put("changeType", 6);
                updatePointsHistory(map);
                order.setUsePoints(BigDecimal.ZERO);
                order.setAddPoints(BigDecimal.ZERO);
                UpdateWrapper< COrder > orderUpdateWrapper = new UpdateWrapper<>();
                orderUpdateWrapper.eq("id", order.getId());
                orderService.update(order, orderUpdateWrapper);
                QueryWrapper< COrderGoods > queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_code", orderCode);
                List< COrderGoods > orderGoodsList = orderGoodsService.list(queryWrapper);
                for (COrderGoods orderGoods : orderGoodsList) {
                    orderGoods.setUsePoints(BigDecimal.ZERO);
                    orderGoods.setUsePoints(BigDecimal.ZERO);
                    UpdateWrapper< COrderGoods > orderGoodsUpdateWrapper = new UpdateWrapper<>();
                    orderGoodsUpdateWrapper.eq("id", orderGoods.getId());
                    orderGoodsService.update(orderGoods, orderGoodsUpdateWrapper);
                }
            }
            result.put("code", "200");
            result.put("data", "");
            result.put("message", "积分返还成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }


}
