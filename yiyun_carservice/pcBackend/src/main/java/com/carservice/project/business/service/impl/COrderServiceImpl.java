package com.carservice.project.business.service.impl;

import com.carservice.common.constant.HttpStatus;
import com.carservice.common.exception.CustomException;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.RedisUtil;
import com.carservice.common.utils.SecurityUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import com.carservice.project.business.constant.ORDERTYPE;
import com.carservice.project.business.constant.PAYSTATUS;
import com.carservice.project.business.domain.COrder;
import com.carservice.project.business.domain.COrderGoods;
import com.carservice.project.business.mapper.COrderGoodsMapper;
import com.carservice.project.business.mapper.COrderMapper;
import com.carservice.project.shop.mapper.CShopGoodsMapper;
import com.carservice.project.business.service.COrderService;
import com.carservice.project.business.util.ResultTab;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class COrderServiceImpl implements COrderService {
    @Autowired
    private COrderMapper cOrderMapper;
    @Autowired
    private COrderGoodsMapper cOrderGoodsMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CShopGoodsMapper cShopGoodsMapper;

    @Override
    @DataScope(deptAlias = "s")
    public ResultTab getOrderList(COrder cOrder) throws Exception{

        PageHelper.startPage(cOrder.getPageNum(), cOrder.getPageSize());
        List<COrderGoods> orderList = cOrderMapper.getOrderByBusinessID(cOrder);

        PageInfo<COrderGoods> pageInfo = new PageInfo<COrderGoods>(orderList);
        ResultTab resultTab = new ResultTab(pageInfo.getTotal(),orderList,
                cOrder.getPageSize(),cOrder.getPageNum(),pageInfo.getPages());
        return resultTab;
    }

    @Override
    public void updateOrderGoods(COrderGoods cOrderGoods) throws Exception{
        Integer id = cOrderGoods.getId();
        if(id == null){
            throw new Exception();
        }
        cOrderGoodsMapper.updateByPrimaryKeySelective(cOrderGoods);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void getPhone(COrderGoods cOrderGoods) throws Exception{
        Integer id = cOrderGoods.getId();
        String orderCode = cOrderGoods.getOrderCode();
        if(id == null || StringUtils.isBlank(orderCode)){
            throw new Exception();
        }
        COrderGoods pojo = new COrderGoods();
        pojo.setId(id);
        pojo.setPayStatus(PAYSTATUS.JIE_DAN);

        cOrderGoodsMapper.updateByPrimaryKeySelective(pojo);

        COrder order = new COrder();
        order.setOrderCode(orderCode);
        order.setOrderStatus(PAYSTATUS.JIE_DAN);
        cOrderMapper.updateByOrderCode(order);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyBookTime(COrder cOrder) throws Exception{
        String orderCode = cOrder.getOrderCode();
        String bookTime = cOrder.getBookTime();
        String userPhone = cOrder.getUserPhone();
        if(StringUtils.isBlank(orderCode)){
            throw new Exception();
        }
        COrder pojo = new COrder();
        pojo.setOrderCode(orderCode);
        pojo.setBookTime(bookTime);
        cOrderMapper.updateByOrderCode(pojo);
        //将订单放入缓存
        String key = "Order:" + orderCode + "," + userPhone + ",3";
        String value = orderCode;
        Date bookTimeDate = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,bookTime);
        long time = DateUtils.getSecondPoor(bookTimeDate,new Date());
        time += 24*60*60;
        redisUtil.sSetAndTime(key,time,value);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyOrderPriceAndDescribe(COrderGoods cOrderGoods) throws Exception{
        String orderCode = cOrderGoods.getOrderCode();//订单编号
        Integer id = cOrderGoods.getId();//商品id
        String orderType = cOrderGoods.getOrderType();//商品类型
        if(id == null || StringUtils.isBlank(orderCode) || StringUtils.isBlank(orderType)){
            throw new Exception();
        }
        //修改商品详情
        String productDescribe = cOrderGoods.getProductDescribe();//产品图片url
        BigDecimal productAmount = cOrderGoods.getProductAmount();//产品价格
        COrderGoods pojo = new COrderGoods();
        pojo.setProductDescribe(productDescribe);
        pojo.setProductAmount(productAmount);//修改产品金额
        String payStatus = judgePayStatus(orderType);
        pojo.setPayStatus(payStatus);//此时状态由接单 转到 服务中
        pojo.setId(id);
        cOrderGoodsMapper.updateByPrimaryKeySelective(pojo);
        //修改订单信息
        COrder order = new COrder();
        order.setOrderStatus(payStatus);//订单状态 改为服务中
        order.setOrderCode(orderCode);
        order.setOrderAmount(productAmount);//修改订单金额
        cOrderMapper.updateByOrderCode(order);

    }

    /**
     * 如果为驾培和保险 改为完成
     * @param orderType
     * @return
     */
    private String judgePayStatus(String orderType) {
        if(orderType.equals(ORDERTYPE.BAOXIAN) || orderType.equals(ORDERTYPE.JIAPEI)){
            return PAYSTATUS.WAN_CHENG;
        }else{
            return PAYSTATUS.FU_WU;
        }
    }

    @Override
    public List<COrderGoods> getCheckOrderGoods(COrderGoods cOrderGoods) throws Exception{
       String verifyCode = cOrderGoods.getVerifyCode();
       String orderCode = cOrderGoods.getOrderCode();
       if(StringUtils.isBlank(verifyCode) || StringUtils.isBlank(orderCode)){
           throw new Exception();
       }
       COrderGoods pojo = new COrderGoods();
       pojo.setVerifyCode(verifyCode);
       pojo.setOrderCode(orderCode);
       List<COrderGoods> returnPojo = cOrderMapper.getCheckOrderGoods(pojo);
       return returnPojo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkOrderGoods(COrderGoods cOrderGoods) throws Exception{
        Integer id = cOrderGoods.getId();
        String verifyCode = cOrderGoods.getVerifyCode();
        String productCode = cOrderGoods.getProductCode();
        if(id == null || StringUtils.isBlank(verifyCode)){
            throw new Exception();
        }
        COrderGoods pojo = new COrderGoods();
        pojo.setId(id);
        pojo.setVerifyCode(verifyCode);
        pojo.setPayStatus(PAYSTATUS.WAN_CHENG);
        pojo.setPayTime(DateUtils.getTime());
        int count = cOrderGoodsMapper.updateByPrimaryKeySelective(pojo);
        if(count == 0){
            throw new Exception();
        }
        cShopGoodsMapper.addSaleNum(cOrderGoods);//产品售卖数量+1

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRescueInfo(COrderGoods cOrderGoods) throws Exception{
        Integer id = cOrderGoods.getId();
        String orderCode = cOrderGoods.getOrderCode();
        if(id == null || StringUtils.isBlank(orderCode)){
            throw new Exception();
        }
        String rescueCarNo =  cOrderGoods.getRescueCarNo();
        String rescueMobile = cOrderGoods.getRescueMobile();
        String rescueName = cOrderGoods.getRescueName();
        //修改商品
        COrderGoods pojo = new COrderGoods();
        pojo.setId(id);
        pojo.setPayStatus(PAYSTATUS.FU_WU);//状态由下单变为服务中
        cOrderGoodsMapper.updateByPrimaryKeySelective(pojo);

        //修改订单
        COrder order = new COrder();
        order.setOrderCode(orderCode);
        order.setOrderStatus(PAYSTATUS.FU_WU);
        order.setRescueCarNo(rescueCarNo);
        order.setRescueMobile(rescueMobile);
        order.setRescueName(rescueName);
        cOrderMapper.updateByOrderCode(order);

    }

    /**
     * 查询订单列表
     *
     * @param cOrder 订单
     * @return 订单
     */
    @Override
    @DataScope(deptAlias = "s")
    public List<COrder> selectCOrderList(COrder cOrder) throws Exception
    {
        List<COrder> list = cOrderMapper.selectCOrderList(cOrder);
        for(COrder temp : list){
           String orderStatus = temp.getOrderStatus();
           String orderType = temp.getOrderType();
           if(!StringUtils.isBlank(orderStatus) && !StringUtils.isBlank(orderType)){
               if(orderStatus.equals(PAYSTATUS.XIA_DAN) && !orderType.equals(ORDERTYPE.XICHE) && !orderType.equals(ORDERTYPE.JIANCE)){//未接单不展示 电话
                   temp.setUserPhone("");
               }
           }
        }
        return list;
    }

    /**
     * 查询订单
     *
     * @param orderCode 订单ID
     * @return 订单
     */
    @Override
    public List<COrderGoods> selectCOrderGoodsByOrderCode(String orderCode) throws Exception
    {
        List<COrderGoods> list = cOrderGoodsMapper.selectCOrderGoodsByOrderCode(orderCode);
        for(COrderGoods goods : list){
            String url = goods.getProductDescribe();
            if(!StringUtils.isBlank(url)){
                String[] urlArr = url.split(",");
                goods.setProductDescribeArr(urlArr);
            }
        }
        return list;
    }
}
