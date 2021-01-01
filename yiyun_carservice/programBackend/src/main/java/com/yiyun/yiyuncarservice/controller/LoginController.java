package com.yiyun.yiyuncarservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yiyun.yiyuncarservice.constant.DataConstants;
import com.yiyun.yiyuncarservice.entity.CPromote;
import com.yiyun.yiyuncarservice.entity.CRelationCar;
import com.yiyun.yiyuncarservice.entity.CUserInfo;
import com.yiyun.yiyuncarservice.entity.CUserInfoVo;
import com.yiyun.yiyuncarservice.service.CPromoteService;
import com.yiyun.yiyuncarservice.service.CRelationCarService;
import com.yiyun.yiyuncarservice.service.CUserInfoService;
import com.yiyun.yiyuncarservice.service.CUserlevelHistoryService;
import com.yiyun.yiyuncarservice.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.web.bind.annotation.*;

/**
 * @author hxx
 * @title: LoginController
 * @projectName yiyuncarservice
 * @description: 微信小程序登录
 * @date 2020/12/11 10:29
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户获取授权、登录及信息管理类", tags = "用户获取授权、登录及信息管理类", description = "用户获取授权、登录及信息管理类")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private WxUtils wxUtils;

    @Autowired
    private MyMD5Util myMd5Util;

    @Autowired
    private HttpPostUtil httpPostUtil;

    @Autowired
    private CUserInfoService userInfoService;

    @Autowired
    private Md5Util md5Util;

    @Autowired
    private CRelationCarService cRelationCarService;

    @Autowired
    private CUserlevelHistoryService cUserlevelHistoryService;

    @Autowired
    private CPromoteService cPromoteService;

//    @Autowired
//    protected Mapper dozerMapper;


    @PostMapping(value = "/wxlogin")
    @ApiOperation(value = "用户获取授权及创建用户信息接口", notes = "用户获取授权及创建用户信息接口", response = Map.class)
    public Object wxLogin(@RequestBody Map< String, Object > param) {
        // 创建Httpclient对象
        System.out.println("开始调用授权==" + new Timestamp(System.currentTimeMillis()));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        Map< String, Object > result = new HashMap<>();
        Map< String, Object > data = new HashMap<>();
        int statusCode = 500;
        String url =
                "https://api.weixin.qq.com/sns/jscode2session?appid="
                        + DataConstants.APPID
                        + "&secret="
                        + DataConstants.SECRET
                        + "&js_code="
                        + param.get("code")
                        + "&grant_type=authorization_code";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        CUserInfo user = new CUserInfo();
        if (jsonObject.get("openid") == null) {
            jsonObject.clear();
            result.put("code", statusCode);
            result.put("data", data);
            result.put("message", "获取授权失败，无效的code！");
            return result;
        } else {
            QueryWrapper< CUserInfo > userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.eq("openid", jsonObject.getString("openid"));
            CUserInfo cUserInfo = userInfoService.getOne(userInfoQueryWrapper);
            if (cUserInfo != null) {
                user = cUserInfo;
            } else {
                user.setOpenid(jsonObject.getString("openid"));
                userInfoService.save(user);
            }
            data.put("userId", user.getId());
        }
        statusCode = 200;
        result.put("code", statusCode);
        result.put("data", user);
        result.put("message", "获取授权成功！");
        return result;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "用户登录接口", response = Map.class)
    public Object Login(@RequestBody Map< String, Object > param) {
        Map< String, Object > result = new HashMap<>();
        Map< String, Object > data = new HashMap<>();
        int statusCode = 500;
        CUserInfo userInfo = new CUserInfo();
        String mobile = param.get("userPhone").toString();
//        QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_phone", mobile);
//        List< CUserInfo > userList = userInfoService.list(queryWrapper);
        userInfo = userInfoService.getById(param.get("userId").toString());
        //1 验证码登录 2账号密码登录
        if (param.get("loginType").toString().equals("1")) {
            String smskey = "sms" + mobile;
            if (redisUtil.hasKey(smskey)) {
                if (redisUtil.get(smskey).equals(param.get("code").toString())) {
                    if (StringUtils.isNotBlank(userInfo.getUserPhone()) && userInfo.getUserPhone().equals("mobile")) {
                        String key = "userId" + userInfo.getId();
                        redisUtil.set(key, userInfo, 0);
                        statusCode = 200;
                        result.put("code", statusCode);
                        result.put("data", userInfo);
                        result.put("message", "用户登录成功！");
                        return result;
                    } else if (StringUtils.isNotBlank(userInfo.getUserPhone()) && !userInfo.getUserPhone().equals("mobile")) {
                        result.put("code", statusCode);
                        result.put("data", data);
                        result.put("message", "该微信号已经绑定账号" + userInfo.getUserPhone() + "，请输入其他的手机号！");
                        return result;
                    } else if (StringUtils.isBlank(userInfo.getUserPhone())) {
                        userInfo.setUserPhone(mobile);
                        QueryWrapper< CPromote > promoteWrapper = new QueryWrapper<>();
                        promoteWrapper.eq("promote_phone", mobile);
                        List< CPromote > promoteList = cPromoteService.list(promoteWrapper);
                        if (promoteList != null && promoteList.size() > 0) {
                            userInfo.setPromoteCode(promoteList.get(0).getPromoteCode());
                        }
                        userInfo.setUserPhone(mobile);
                        UpdateWrapper< CUserInfo > updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("id", param.get("userId"));
                        userInfoService.update(userInfo, updateWrapper);
                        String key = "userId" + userInfo.getId();
                        CUserInfoVo cUserInfoVo = new CUserInfoVo();
                        BeanUtils.copyProperties(userInfo, cUserInfoVo);
                        redisUtil.set(key, cUserInfoVo, 0);
                        statusCode = 200;
                        result.put("code", statusCode);
                        result.put("data", userInfo);
                        result.put("message", "用户登录成功！");
                        return result;
                    }
                } else {
                    result.put("code", statusCode);
                    result.put("data", data);
                    result.put("message", "手机验证码输入有误，请确认后重新输入！");
                    return result;
                }
            } else {
                result.put("code", statusCode);
                result.put("data", data);
                result.put("message", "手机验证码已过期，请重新发送短信获取！");
                return result;
            }
        } else {
            if (StringUtils.isBlank(userInfo.getUserPhone())) {
                userInfo.setUserPhone(mobile);
                QueryWrapper< CPromote > promoteWrapper = new QueryWrapper<>();
                promoteWrapper.eq("promote_phone", mobile);
                List< CPromote > promoteList = cPromoteService.list(promoteWrapper);
                if (promoteList != null && promoteList.size() > 0) {
                    userInfo.setPromoteCode(promoteList.get(0).getPromoteCode());
                }
                userInfo.setUserPhone(mobile);
                UpdateWrapper< CUserInfo > updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", param.get("userId"));
                userInfoService.update(userInfo, updateWrapper);
                String key = "userId" + userInfo.getId();
                CUserInfoVo cUserInfoVo = new CUserInfoVo();
                BeanUtils.copyProperties(userInfo, cUserInfoVo);
                redisUtil.set(key, cUserInfoVo, 0);
                statusCode = 200;
                result.put("code", statusCode);
                result.put("data", userInfo);
                result.put("message", "用户登录成功！");
                return result;
            } else {
                if (StringUtils.isNotBlank(userInfo.getUserPhone()) && userInfo.getUserPhone().equals("mobile")) {
                    try {
                        if (!md5Util.validPassword(param.get("password").toString(), userInfo.getPassword())) {
                            result.put("code", statusCode);
                            result.put("data", data);
                            result.put("msg", "手机号或密码错误，请重新填写后登录!");
                            return result;
                        } else {
                            String key = "userId" + userInfo.getId();
                            CUserInfoVo cUserInfoVo = new CUserInfoVo();
                            BeanUtils.copyProperties(userInfo, cUserInfoVo);
                            redisUtil.set(key, cUserInfoVo, 0);
                            statusCode = 200;
                            result.put("code", statusCode);
                            result.put("data", userInfo);
                            result.put("msg", "用户登录成功!");
                            return result;
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (StringUtils.isNotBlank(userInfo.getUserPhone()) && !userInfo.getUserPhone().equals("mobile")) {
                    result.put("code", statusCode);
                    result.put("data", data);
                    result.put("message", "该微信号已经绑定账号" + userInfo.getUserPhone() + "，请输入其他的手机号！");
                    return result;
                }
            }
        }
        return result;
    }


    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码", response = Map.class)
    @PostMapping(value = "/sendSms")
    public Object sendSms(@RequestBody Map< String, Object > param) {
        String smskey = "sms" + param.get("userPhone").toString();
        //    String validkey = "valid" + param.get("mobile").toString();
        //    SMSClient client = new SMSClient(DataConstants.jgSecret, DataConstants.jgAppKey);
        //    SMSPayload payload =
        //        SMSPayload.newBuilder()
        //            .setMobileNumber(param.get("mobile").toString())
        //            .setSignId(12733)
        //            .setTempId(1)
        //            .build();
        Map< String, Object > result = new HashMap<>();
        Map< String, Object > data = new HashMap<>();
        int statusCode = 500;
        if (redisUtil.hasKey(smskey)) {
            result.put("code", statusCode);
            result.put("data", data);
            result.put("message", "您的短信发送过于频繁，请稍后再试!");
        } else {
            String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
            String account = "dh24733"; // 用户名（必填）
            String password = "tB1eDOsV"; // 密码（必填,明文）
            String mobile = param.get("userPhone").toString(); // 手机号码（必填,多条以英文逗号隔开）
            String sign = "【易车行】"; // 短信签名（必填）
            String content = "您的手机验证码：" + code + "，有效期5分钟，请勿泄露。如非本人操作，请忽略此短信。谢谢！";
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
            JSONObject jsonObject = null;
            try {
                jsonObject =
                        (JSONObject)
                                JSONObject.parse(
                                        httpPostUtil.httpURLConnectionPOST(
                                                "http://www.dh3t.com/json/sms/Submit", paramData));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonObject.get("result").equals("0")) {
                redisUtil.del(smskey);
                redisUtil.set(smskey, code, 300);
                statusCode = 200;
                result.put("code", statusCode);
                result.put("data", data);
                result.put("message", "短信发送成功！");
            } else {
                result.put("code", statusCode);
                result.put("data", data);
                result.put("message", "短信发送失败！");
            }
        }
        return result;
    }


//    @PostMapping(value = "/register")
//    @ApiOperation(value = "用户注册", notes = "用户注册接口", response = Map.class)
//    public Object register(@RequestBody Map< String, Object > param) {
//        Map< String, Object > result = new HashMap<>();
//        Map< String, Object > data = new HashMap<>();
//        int statusCode = 500;
//        CUserInfo userInfo = new CUserInfo();
//        QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_phone", param.get("userPhone"));
//        List< CUserInfo > userList = userInfoService.list(queryWrapper);
//        //只有账号密码才能注册
//        if (userList != null) {
//            result.put("code", statusCode);
//            result.put("data", data);
//            result.put("msg", "输入的手机号已经绑定账号，请输入其他的手机号!");
//            return result;
//        } else if (userList == null) {
//            userInfo = userInfoService.getById((Long) param.get("userId"));
//            userInfo.setUserPhone(param.get("userPhone").toString());
//            try {
//                userInfo.setPassword(md5Util.getEncryptedPwd(param.get("password").toString()));
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            UpdateWrapper< CUserInfo > updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("id", param.get("userId"));
//            userInfoService.update(userInfo, updateWrapper);
//            String key = "userId" + userInfo.getId();
//            redisUtil.set(key, userInfo, 0);
//            statusCode = 200;
//            result.put("code", statusCode);
//            result.put("data", userInfo);
//            result.put("msg", "用户注册成功!");
//        }
//        return result;
//    }


//    @ApiOperation(value = "用户短信验证，并补充用户的相关信息")
//    @PostMapping(value = "/validSMSCode")
//    public Object validSMSCode(@RequestBody Map< String, Object > param) {
//        System.out.println("获取传的参数======" + param.toString());
//        Map< String, Object > result = new HashMap<>();
//        Map< String, Object > data = new HashMap<>();
//        int statusCode = 500;
//        String resultString = "";
//        if (param.get("loginType").toString().equals("1")) {
//            QueryWrapper< TripUserInfo > userQuery = new QueryWrapper<>();
//            userQuery.eq("user_phone", param.get("mobile"));
//            List< TripUserInfo > users = tripUserInfoService.list(userQuery);
//            if (users != null && users.size() > 0) {
//                result.put("code", statusCode);
//                result.put("data", data);
//                result.put("message", "输入的手机号已经绑定账号，请输入其他的手机号！");
//                return result;
//            }
//            String smskey = "sms" + param.get("mobile").toString();
//            if (redisUtil.hasKey(smskey)) {
//                if (redisUtil.get(smskey).equals(param.get("code").toString())) {
//                    String userId = param.get("userId").toString();
//                    String key = "userId" + userId;
//                    TripUserInfo userInfo = new TripUserInfo();
//                    if (redisUtil.hasKey(key)) {
//                        ValueOperations< String, TripUserInfo > operations = redisTemplate.opsForValue();
//                        userInfo = operations.get(key);
//                    } else {
//                        QueryWrapper< TripUserInfo > queryWrapper = new QueryWrapper<>();
//                        queryWrapper.eq("id", Long.valueOf(userId));
//                        userInfo = tripUserInfoService.getOne(queryWrapper);
//                    }
//                    // 推广码不为空需要绑定
//                    if (param.get("inviteCode") != null) {
//                        userInfo.setInviteCode(param.get("inviteCode").toString());
//                    }
//                    userInfo.setUserPhone(param.get("mobile").toString());
//                    userInfo.setBindTime(new Timestamp(System.currentTimeMillis()));
//                    tripUserInfoService.updateById(userInfo);
//                    redisUtil.del(key);
//                    redisUtil.set(key, userInfo, 0);
//                    statusCode = 200;
//                    result.put("code", statusCode);
//                    result.put("data", data);
//                    result.put("message", "用户绑定手机成功！");
//                } else {
//                    result.put("code", statusCode);
//                    result.put("data", data);
//                    result.put("message", "手机验证码输入有误，请确认后重新输入！");
//                }
//            } else {
//                result.put("code", statusCode);
//                result.put("data", data);
//                result.put("message", "手机验证码已过期，请重新发送短信获取！");
//            }
//        } else if (param.get("loginType").toString().equals("2")) {
//            System.out.println("开始调用微信登录===" + new Timestamp(System.currentTimeMillis()));
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//            CloseableHttpResponse response = null;
//            String url =
//                    "https://api.weixin.qq.com/sns/jscode2session?appid="
//                            + DataConstants.APPID
//                            + "&secret="
//                            + DataConstants.SECRET
//                            + "&js_code="
//                            + param.get("code")
//                            + "&grant_type=authorization_code";
//            System.out.println("获取微信授权===" + new Timestamp(System.currentTimeMillis()));
//            try {
//                // 创建uri
//                URIBuilder builder = new URIBuilder(url);
//                URI uri = builder.build();
//
//                // 创建http GET请求
//                HttpGet httpGet = new HttpGet(uri);
//
//                // 执行请求
//                response = httpclient.execute(httpGet);
//                // 判断返回状态是否为200
//                if (response.getStatusLine().getStatusCode() == 200) {
//                    resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("微信授权返回结果===" + resultString);
//            // 解析json
//            JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
//            System.out.println(
//                    "调用参数:encryptedData==="
//                            + param.get("encryptedData")
//                            + "====session_key==="
//                            + jsonObject.get("session_key")
//                            + "====iv==="
//                            + param.get("iv").toString());
//            String weChatResult =
//                    wxUtils.wxDecrypt(
//                            param.get("encryptedData").toString(),
//                            jsonObject.get("session_key").toString(),
//                            param.get("iv").toString());
//            System.out.println("解密后的内容为=====" + weChatResult);
//            JSONObject json = JSONObject.parseObject(weChatResult);
//            String phoneNumber = "";
//            if (json.containsKey("phoneNumber")) {
//                phoneNumber = json.getString("phoneNumber");
//            }
//            System.out.println("用户手机号===" + phoneNumber);
//            QueryWrapper< TripUserInfo > userQuery = new QueryWrapper<>();
//            userQuery.eq("user_phone", phoneNumber);
//            List< TripUserInfo > users = tripUserInfoService.list(userQuery);
//            if (users != null && users.size() > 0) {
//                result.put("code", statusCode);
//                result.put("data", data);
//                result.put("message", "输入的手机号已经绑定账号，请输入其他的手机号！");
//                return result;
//            }
//            String userId = param.get("userId").toString();
//            String key = "userId" + userId;
//            TripUserInfo userInfo = new TripUserInfo();
//            if (redisUtil.hasKey(key)) {
//                ValueOperations< String, TripUserInfo > operations = redisTemplate.opsForValue();
//                userInfo = operations.get(key);
//            } else {
//                QueryWrapper< TripUserInfo > queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("id", Long.valueOf(userId));
//                userInfo = tripUserInfoService.getOne(queryWrapper);
//            }
//            userInfo.setUserPhone(phoneNumber);
//            userInfo.setBindTime(new Timestamp(System.currentTimeMillis()));
//            tripUserInfoService.updateById(userInfo);
//            redisUtil.del(key);
//            redisUtil.set(key, userInfo, 0);
//            statusCode = 200;
//            result.put("code", statusCode);
//            result.put("data", data);
//            result.put("message", "用户绑定手机成功！");
//        }
//        return result;
//    }


    @ApiOperation(value = "获取用户基本信息")
    @PostMapping(value = "/getUserInfo")
    public Object getUserInfo(@RequestBody Map< String, Object > param) {
        Map< String, Object > result = new HashMap<>();
        Map< String, Object > data = new HashMap<>();
        int statusCode = 500;
        String userId = param.get("userId").toString();
        String key = "userId" + userId;
        CUserInfo userInfo = new CUserInfo();
        QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", Long.valueOf(userId));
        userInfo = userInfoService.getOne(queryWrapper);
        CUserInfoVo cUserInfoVo = new CUserInfoVo();
        BeanUtils.copyProperties(userInfo, cUserInfoVo);
        QueryWrapper< CRelationCar > queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", userId);
        List< CRelationCar > carList = cRelationCarService.list(queryWrapper1);
        cUserInfoVo.setCarList(carList);
        redisUtil.set(key, cUserInfoVo, 0);
        statusCode = 200;
        result.put("code", statusCode);
        result.put("data", cUserInfoVo);
        result.put("message", "用户信息获取成功!");
        return result;
    }

    @ApiOperation(value = "更新用户基本信息")
    @PostMapping(value = "/updateUserInfo")
    @Transactional(rollbackFor = RuntimeException.class)
    public Object updateUserInfo(@RequestBody Map< String, Object > param) {
        Map< String, Object > result = new HashMap<>();
        Map< String, Object > data = new HashMap<>();
        int statusCode = 500;
        String userId = param.get("userId").toString();
        String key = "userId" + userId;
        CUserInfo userInfo = new CUserInfo();
        try {
            if (redisUtil.hasKey(key)) {
                ValueOperations< String, CUserInfoVo > operations = redisTemplate.opsForValue();
                CUserInfoVo cUserInfoVo = operations.get(key);
                BeanUtils.copyProperties(cUserInfoVo, userInfo);
            } else {
                QueryWrapper< CUserInfo > queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", userId);
                userInfo = userInfoService.getOne(queryWrapper);
            }
            UpdateWrapper< CUserInfo > updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", userId);
            if (param.get("inviteCode") != null) {
                QueryWrapper< CPromote > promoteWrapper = new QueryWrapper<>();
                promoteWrapper.eq("promote_code", param.get("inviteCode"));
                List< CPromote > promoteList = cPromoteService.list(promoteWrapper);
                if (promoteList != null && promoteList.size() > 0) {
                    userInfo.setInviteCode(param.get("inviteCode").toString());
                }
            }
            if (param.get("carNo") != null) {
                if (param.get("changeType").toString().equals("add")) {
                    QueryWrapper< CRelationCar > carqueryWrapper = new QueryWrapper<>();
                    carqueryWrapper.eq("user_id", userId);
//                List< CRelationCar > carList = cRelationCarService.list(carqueryWrapper);
//                if (carList == null || param.get("isDefault").toString().equals("1")) {
//                    userInfo.setCarNo(param.get("carNo").toString());
//                }
                    carqueryWrapper.eq("car_no", param.get("carNo"));
                    List< CRelationCar > carList = cRelationCarService.list(carqueryWrapper);
                    if (carList == null || carList.size() == 0) {
                        CRelationCar cRelationCar = new CRelationCar();
                        cRelationCar.setIsDefault(param.get("isDefault").toString());
                        cRelationCar.setCarNo(param.get("carNo").toString());
                        cRelationCar.setUserId(userId);
                        cRelationCar.setUserPhone(userInfo.getUserPhone());
                        cRelationCarService.save(cRelationCar);
                    }
                } else {
                    QueryWrapper< CRelationCar > carqueryWrapper = new QueryWrapper<>();
                    carqueryWrapper.eq("user_id", userId);
                    carqueryWrapper.eq("car_no", param.get("carNo"));
                    CRelationCar cRelationCar = cRelationCarService.getOne(carqueryWrapper);
                    cRelationCarService.removeById(cRelationCar.getId());
                }
            }
            if (param.get("nickName") != null) {
                userInfo.setNickName(param.get("nickName").toString());
            }
            if (param.get("headImg") != null) {
                userInfo.setHeadImg(param.get("headImg").toString());
            }
            if (param.get("userPhone") != null) {
                userInfo.setUserPhone(param.get("userPhone").toString());
            }
            if (param.get("payPassword") != null) {
                try {
                    userInfo.setPayPassword(md5Util.getEncryptedPwd(param.get("payPassword").toString()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (param.get("password") != null) {
                try {
                    userInfo.setPassword(md5Util.getEncryptedPwd(param.get("password").toString()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            userInfoService.update(userInfo, updateWrapper);
            userInfo = userInfoService.getById(userId);
            CUserInfoVo cUserInfoVo = new CUserInfoVo();
            BeanUtils.copyProperties(userInfo, cUserInfoVo);
            QueryWrapper< CRelationCar > queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id", userId);
            List< CRelationCar > newCarList = cRelationCarService.list(queryWrapper1);
            cUserInfoVo.setCarList(newCarList);
            redisUtil.set(key, cUserInfoVo, 0);
            statusCode = 200;
            result.put("code", statusCode);
            result.put("data", cUserInfoVo);
            result.put("message", "更新用户信息成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }
}
