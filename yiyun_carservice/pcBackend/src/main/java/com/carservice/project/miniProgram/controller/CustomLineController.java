package com.carservice.project.miniProgram.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.http.HttpUtils;
import com.carservice.framework.redis.RedisCache;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.project.miniProgram.service.CustomLineServiceImpl;
import com.carservice.project.oper.domain.*;
import com.carservice.project.oper.service.impl.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;


/**
 * 定制线路Controller
 *
 */
@RestController
@RequestMapping("/miniProgram/customLine")
public class CustomLineController extends BaseController
{
    @Autowired
    private CustomLineServiceImpl customLineService;

    @Autowired
    private TVehicleTaskStatusServiceImpl vehicleTaskStatusService;
    @Autowired
    private TVehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private TDriverInfoServiceImpl driverInfoService;
    @Autowired
    private TPriceRulesServiceImpl tPriceRulesService;
    @Autowired
    private TOrderRefundRulesServiceImpl orderRefundRulesService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 通过航班号查询航班
     */
    @RequestMapping("/FlightVarListQuery")
    public AjaxResult FlightVarListQuery(@RequestBody JSONObject reqParam)
    {
        try{
            String flightNo = reqParam.getString("flightNo");
            String srchDate = reqParam.getString("srchDate");
            JSONObject data = new JSONObject();
            data.put("queryType",1);
            data.put("flightNo",flightNo);
            data.put("srchDate",srchDate);
            String result = HttpUtils.httpURLConnectionPOST("https://m.ctrip.com/restapi/soa2/14566/FlightVarListQuery",data);
            JSONObject flightJSON = JSONObject.parseObject(result);
            if (flightJSON.getIntValue("rt")==4){
                if(flightJSON.getJSONArray("flst").size()==0){
                    return AjaxResult.error("无效航班号");
                }
                JSONObject flstJSON = (JSONObject)flightJSON.getJSONArray("flst").get(0);

                JSONObject data1 = new JSONObject();
                data1.put("aPort",flstJSON.getString("acode"));
                data1.put("dPort",flstJSON.getString("dcode"));
                data1.put("fltno",flightNo);
                data1.put("queryDate",srchDate);
                JSONObject data2 = new JSONObject();
                data2.put("fltItem",data1);
                String result1 = HttpUtils.httpURLConnectionPOST("https://m.ctrip.com/restapi/soa2/14566/FlightVarDetailSearchV2",data2);
                JSONObject flightDetailJSON1 = JSONObject.parseObject(result1);
                return AjaxResult.success(flightDetailJSON1.get("varItemInfo"));
            }else{
                return AjaxResult.error(flightJSON.getString("rtmsg"));
            }
        }catch (Exception e){
            return AjaxResult.error("无效航班号");
        }


    }

    /**
     * 获取城市列表
     */
    @RequestMapping("/getCityList")
    public AjaxResult getCityList()
    {
        List<TOperCityInfo> cityList = customLineService.getCityList();
        return AjaxResult.success(cityList);
    }

    /**
     * 获取城市下站点列表
     */
    @RequestMapping("/getSite")
    public AjaxResult getSite(Long operCityId)
    {
        List<TSiteInfo> siteInfoList = customLineService.getSiteByCityId(operCityId);
        return AjaxResult.success(siteInfoList);
    }

    /**
     * 获取目的地城市列表
     */
    @RequestMapping("/getEndCityList")
    public AjaxResult getEndCityList(@RequestBody JSONObject reqParam)
    {
        Long operCityId = reqParam.getLong("operCityId");
        List<TOperCityInfo> cityList = customLineService.getEndCityList(operCityId);
        return AjaxResult.success(cityList);
    }

    /**
     * 获取线路产品信息（开始站点城市和目的地站点城市相符的线路）
     * @return
     */
    @RequestMapping("/getProductList")
    public AjaxResult getProductList(@RequestBody JSONObject reqParam)
    {
        String startCity = reqParam.getString("startCity");
                String endCity = reqParam.getString("endCity");
        String lineType = reqParam.getString("lineType");
        List<TProduct> productList = customLineService.getProductList(startCity,endCity,lineType);
        return AjaxResult.success(productList);
    }

    /**
     * 获取线路站点
     * @return
     */
    @RequestMapping("/getProductSiteList")
    public AjaxResult getProductSiteList(@RequestBody JSONObject reqParam)
    {
        Long productId = reqParam.getLong("productId");
        List<TProductSite> productSiteList = customLineService.getProductSiteList(productId);
        return AjaxResult.success(productSiteList);
    }

    /**
     * 获取产品下可用的车辆类型
     * @return
     */
    @RequestMapping("/getProductCarType")
    public AjaxResult getProductCarType(@RequestBody JSONObject reqParam)
    {
        Long productId = 1l;
        if(reqParam.get("productId")!=null
        &&StringUtils.isNotEmpty(reqParam.get("productId").toString())){
            productId = reqParam.getLong("productId");
        }
        Map<String,List<TVehicleType>> resultMap = new LinkedHashMap<>();
        List<TVehicleType> carTypeList = customLineService.getProductCarType(productId);
        /*for (TVehicleType vehicleType : carTypeList) {
            if (resultMap.containsKey(vehicleType.getTypeName())){
                resultMap.get(vehicleType.getTypeName()).add(vehicleType);
            }else{
                List<TVehicleType> vehicleTypeList = new ArrayList<>();
                vehicleTypeList.add(vehicleType);
                resultMap.put(vehicleType.getTypeName(),vehicleTypeList);
            }
        }*/
        return AjaxResult.success(carTypeList);
    }

    /**
     * 获取包车车型和价格
     * @param reqParam
     * @return
     */
    @RequestMapping("/getBcheCarType")
    public AjaxResult getBcheCarType(@RequestBody JSONObject reqParam) throws ScriptException
    {
        TPriceRules tPriceRules = new TPriceRules();
        tPriceRules.setDeptId("117");
        tPriceRules.setUserType("2");
        //在市内，按时长包车
        if("1".equals(reqParam.get("inCity"))){
            tPriceRules.setChargeType("2");//时间收费
            String startTime = reqParam.getString("startTime");
            String endTime = reqParam.getString("endTime");
            long diffMin = (DateUtils.parseDate(endTime).getTime() - DateUtils.parseDate(startTime).getTime())/1000/60;
            long diffHour= diffMin%60<=10? diffMin/60:diffMin/60+1;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseDate(startTime));
            reqParam.put("去程上车时间",calendar.get(Calendar.HOUR_OF_DAY));
            calendar.setTime(DateUtils.parseDate(endTime));
            reqParam.put("返程上车时间",calendar.get(Calendar.HOUR_OF_DAY));
            reqParam.put("用车时长",diffHour);
        }else{
            tPriceRules.setChargeType("1");//里程收费
            String days = reqParam.getString("days");
            String distance = reqParam.getString("distance");
            reqParam.put("总里程",distance);
            reqParam.put("天数",days);
        }
        List<TPriceRules> list = tPriceRulesService.selectTPriceRulesList1(tPriceRules);
        here:
        for (TPriceRules priceRules : list) {
            priceRules.setTotalPrice(priceRules.getPriceFormula());
            if (StringUtils.isEmpty(priceRules.getUserParam())){
                continue here;
            }
            String[] userParamArray =priceRules.getUserParam().split(",");
            for (String s : userParamArray) {
                if (reqParam.get(s)==null){
                    continue here;
                }
                priceRules.setTotalPrice(priceRules.getTotalPrice().replace(s,reqParam.getString(s)));
            }
            String[] otherPriceArray = priceRules.getOtherPriceRemark().split(",");
            for (String s : otherPriceArray) {
                priceRules.setTotalPrice(
                priceRules.getTotalPrice().replace(s.split(":")[0],s.split(":")[1]));
            }
            ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
            priceRules.setTotalPrice(jse.eval(priceRules.getTotalPrice()).toString());
        }
        return AjaxResult.success(list);
    }


    /**
     * 获取最优的包车产品
     * @return
     */
    @RequestMapping("/getBestBcheGoods")
    public AjaxResult getBestBcheGoods(@RequestBody JSONObject reqParam)
    {
        Long productId = reqParam.getLong("productId");
        int passengerNum = reqParam.getInteger("passengerNum");
        Map<String,List<TVehicleType>> resultMap = new LinkedHashMap<>();
        List<TVehicleType> carTypeList = customLineService.getBestBcheGoods(productId,passengerNum);
        if (carTypeList.size()>0){
            return AjaxResult.success(carTypeList.get(0));
        }else{
            return AjaxResult.success(new HashMap<>());
        }
    }

    /**
     * 获取产品下所选的车辆类型的价格
     * @return
     */
    @RequestMapping("/getProductPrice")
    public AjaxResult getProductPrice(@RequestBody JSONObject reqParam)
    {
        Long productId = reqParam.getLong("productId");
        String vehicleTypeId = reqParam.getString("vehicleTypeId");
        TProductPrice productPrice = customLineService.getProductPrice(productId,vehicleTypeId);
        return AjaxResult.success(productPrice);
    }


    /**
     * 通过线路产品id获取详情
     * @return
     */
    @RequestMapping("/getProductDetail")
    public AjaxResult getProductDetail(@RequestBody JSONObject reqParam)
    {
        Long productId = reqParam.getLong("productId");
        TProduct product = customLineService.getProductById(productId);
        return AjaxResult.success(product);
    }

    /**
     * 获取已拼成的拼车班次
     * @return
     */
    @RequestMapping("/getPCshift")
    public AjaxResult getPCshift(@RequestBody JSONObject reqParam)
    {
        Long productId = reqParam.getLong("productId");
        String time = reqParam.getString("time");//用户用车时间yyyy-MM-dd HH:mm:ss
        List<TVehicleTaskStatus> vehicleTaskStatuses = customLineService.getPCshift(productId,time);
        return AjaxResult.success(vehicleTaskStatuses);
    }



    @RequestMapping("/selectCanUseTVehicleInfoList")
    public AjaxResult selectCanUseTVehicleInfoList(TVehicleInfo tVehicleInfo)
    {
        return AjaxResult.success(vehicleInfoService.selectCanUseTVehicleInfoList(tVehicleInfo));
    }

    @RequestMapping("/insertTVehicleTaskStatus")
    public AjaxResult insertTVehicleTaskStatus(Long productId,String vehicleId,int passengerNum,String useCarStartTime,String useCarEndTime,Integer taskType)
    {
        String code = vehicleTaskStatusService.insertTVehicleTaskStatus(productId,vehicleId,passengerNum,useCarStartTime,useCarEndTime,taskType,null);
        return AjaxResult.success(code);
    }

    @RequestMapping("/selectTVehicleTask")
    public AjaxResult selectTVehicleTask(Long productId,int passengerNum,String useCarStartTime)
    {
        return AjaxResult.success(vehicleTaskStatusService.selectTVehicleTask(productId,passengerNum,useCarStartTime));
    }
    @RequestMapping("/updateTVehicleTaskStatus")
    public AjaxResult updateTVehicleTaskStatus( String dispatchOrdercode,int passengerNum,int orderType)
    {
        return AjaxResult.success(vehicleTaskStatusService.updateTVehicleTaskStatus(dispatchOrdercode,passengerNum,orderType));
    }
    @RequestMapping("/test")
    public AjaxResult test( Long productId,String useCarStartTime,String useCarEndTime,int tastType,String vehicleId,int passengerNum, String driverId)
    {
        String ddcode = vehicleTaskStatusService.insertTVehicleTaskStatus(
                 productId,   useCarStartTime,  useCarEndTime,  tastType);
        vehicleTaskStatusService.insertVehicleAndDriver(
                ddcode,  vehicleId,  passengerNum ,  driverId) ;
            return AjaxResult.success("ok");
    }

    @RequestMapping("/tongbuErpDriverINfo")
    public AjaxResult tongbuErpDriverINfo()
    {
        return AjaxResult.success(driverInfoService.tongbuErpDriverINfo());
    }
    @RequestMapping("/tongbuErpCarINfo")
    public AjaxResult tongbuErpCarINfo()
    {
        return AjaxResult.success(vehicleInfoService.tongbuErpCarINfo());
    }
    @RequestMapping("/getDriverId")
    public AjaxResult getDriverId(String id)
    {
        return AjaxResult.success(driverInfoService.selectTDriverInfoByErpId(id));
    }
    @RequestMapping("/selectTVehicleTaskStatusList1")
    public AjaxResult selectTVehicleTaskStatusList1(TVehicleTaskStatus tVehicleTaskStatus)
    {
        return AjaxResult.success(vehicleTaskStatusService.selectTVehicleTaskStatusList1(tVehicleTaskStatus));
    }

    @RequestMapping("/selectDriverTaskStatusList")
    public AjaxResult selectDriverTaskStatusList(TVehicleTaskStatus tVehicleTaskStatus)
    {
        return AjaxResult.success(vehicleTaskStatusService.selectDriverTaskStatusList(tVehicleTaskStatus));
    }

    /**
     * 获取包车导航距离
     * @return
     */
    @RequestMapping("/getBcheDistance")
    public AjaxResult getBcheDistance(@RequestBody JSONObject reqParam)
    {
        String origin = reqParam.getString("origin");
        String destination = reqParam.getString("destination");
        String waypoints = reqParam.getString("waypoints");
        String param = "nosteps=1&origin="+origin+"&destination="+destination+"&extensions=all&output=JSON&key=c49fc2f1880ce93a4acecaa34503008d&waypoints="+waypoints;
        String result = HttpUtils.sendPost("https://restapi.amap.com/v3/direction/driving",param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getString("status").equals("1")){
            if (jsonObject.getString("infocode").equals("10000")){
                JSONObject route = jsonObject.getJSONObject("route");
                JSONArray paths = route.getJSONArray("paths");
                if (paths.size()>0){
                    return AjaxResult.success(paths.get(0));
                }else{
                    return AjaxResult.error("无规划线路");
                }
            }else{
                return AjaxResult.error("错误码："+jsonObject.getString("infocode"));
            }
        }else{
            return AjaxResult.error("请求失败");
        }
    }

    /**
     * 获取退单规则
     * @return
     */
    @RequestMapping("/getRefundRules")
    public AjaxResult getRefundRules(@RequestBody JSONObject reqParam)
    {
        Map resultMap = new HashMap();
        TOrderRefundRules tOrderRefundRules = new TOrderRefundRules();
        tOrderRefundRules.setOrderType(3);
        List< TOrderRefundRules > pZuoOrderRefundRules = orderRefundRulesService.selectTOrderRefundRulesList(tOrderRefundRules);
        tOrderRefundRules.setOrderType(5);
        List< TOrderRefundRules > bCheOrderRefundRules = orderRefundRulesService.selectTOrderRefundRulesList(tOrderRefundRules);
        resultMap.put("pZuo",pZuoOrderRefundRules);
        resultMap.put("bChe",bCheOrderRefundRules);
        return AjaxResult.success(resultMap);
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        /*String strs = "总里程<=标定里程*天数?标准价*天数:标准价*天数+标准价*[(总里程-标定里程*天数)/50]*0.13";
        strs = strs.replace("总里程", "630")
                .replace("标定里程", "400")
                .replace("天数", "1")
                .replace("标准价", "750");*/

        //String strs = "if(用车时间>=9&&用车时间<=15){标准价*0.4}else if(用车时长<=5){标准价*0.6}else if(用车时长<=6){标准价*0.7}else if(用车时长<=7){标准价*0.8}else if(用车时长<=8){标准价*0.9}else{标准价}";
        String strs = "\n" +
                "function countNum(num1,num2){\n" +
                "    var num = num1+num2;\n" +
                "    return num;\n" +
                "}";
        String getFxyBb = "function 非协议全包(总里程,标定里程,天数,标准价){" +
                        "if(总里程<=标定里程*天数){" +
                "return 标准价*天数}" +
                "else{return 标准价*天数+标准价*[(总里程-标定里程*天数)/50]*0.13}"+
                "}";
        /*strs = strs.replace("用车时长", "8.1")
                .replace("用车时间", "9")
                .replace("标准价", "100");*/
        jse.eval(getFxyBb);
        Invocable invoke = (Invocable)jse;
        Object v = invoke.invokeFunction("非协议全包", 630,400,1,750);//执行js文件中的指定函数
        System.out.println(invoke);
    }

}
