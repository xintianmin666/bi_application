package com.carservice.project.shop.domain;

import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 店铺商户对象 c_shop
 * 
 * @author carservice
 * @date 2020-12-12
 */
public class CShop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;
    private String userName;
    private String password;

    private Long userId;
    private Long deptId;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String name;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 手机或座机 */
    @Excel(name = "手机或座机")
    private String tel;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String workContent;

    /** 作息时间 */
    @Excel(name = "作息时间")
    private String workTime;

    /** 经纬度 */
    @Excel(name = "经纬度")
    private String lonLat;

    /** 经度 */
    private String lon;
    /** 纬度 */
    private String lat;

    /** 评分 */
    @Excel(name = "评分")
    private Double score;

    /** 评分人数 */
    @Excel(name = "评分人数")
    private Long num;

    /** 信用积分 */
    @Excel(name = "信用积分")
    private Long points;

    /** 1:删除  0：未删除 */
    @Excel(name = "1:删除  0：未删除")
    private String isDelete;

    /** 1：上架展示  2：下架 */
    @Excel(name = "1：上架展示  2：下架")
    private String isShow;

    /** 列表缩略图 */
    @Excel(name = "列表缩略图")
    private String coverPic;

    /** 店铺轮播图 */
    @Excel(name = "店铺轮播图")
    private String shopTopPics;

    /** 1:自营店 2：加盟店 */
    @Excel(name = "1:自营店 2：加盟店")
    private String selfShop;

    /** 默认排序 */
    @Excel(name = "默认排序")
    private Long shopOrder;

    /** 服务类型（字典） */
    @Excel(name = "服务类型", readConverterExp = "字=典")
    private String serviceType;

    /** 负责人 */
    @Excel(name = "负责人")
    private String chargePerson;

    /** 店铺类型（字典）驾培，维修，检测，施救 */
    @Excel(name = "店铺类型", readConverterExp = "字=典")
    private String shopType;

    /** 商铺标签（字典多选），可搜索 */
    @Excel(name = "商铺标签", readConverterExp = "字=典多选")
    private String shopTags;

    /** 是否支持积分币抵扣 1是 2否 */
    @Excel(name = "是否支持积分币抵扣 1是 2否")
    private String canUsePoints;

    /** 服务范围 */
    @Excel(name = "服务范围")
    private String serviceRange;

    private String juli;

    private String orderBy;
    private List<CShopGoods> shopGoodsList;
    private String keyWords;
    private String checkreult;
    private String shopmaintype;
    private String shoprank;
    private Double shopcredit;

    public String getShoprank() {
        return shoprank;
    }

    public void setShoprank(String shoprank) {
        this.shoprank = shoprank;
    }

    public Double getShopcredit() {
        return shopcredit;
    }

    public void setShopcredit(Double shopcredit) {
        this.shopcredit = shopcredit;
    }

    public String getShopmaintype() {
        return shopmaintype;
    }

    public void setShopmaintype(String shopmaintype) {
        this.shopmaintype = shopmaintype;
    }

    public String getCheckreult() {
        return checkreult;
    }

    public void setCheckreult(String checkreult) {
        this.checkreult = checkreult;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public List<CShopGoods> getShopGoodsList() {
        return shopGoodsList;
    }

    public void setShopGoodsList(List<CShopGoods> shopGoodsList) {
        this.shopGoodsList = shopGoodsList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setWorkContent(String workContent) 
    {
        this.workContent = workContent;
    }

    public String getWorkContent() 
    {
        return workContent;
    }
    public void setWorkTime(String workTime) 
    {
        this.workTime = workTime;
    }

    public String getWorkTime() 
    {
        return workTime;
    }
    public void setLonLat(String lonLat) 
    {
        this.lonLat = lonLat;
    }

    public String getLonLat() 
    {
        return lonLat;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setNum(Long num)
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }
    public void setPoints(Long points) 
    {
        this.points = points;
    }

    public Long getPoints() 
    {
        return points;
    }
    public void setIsDelete(String isDelete) 
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete() 
    {
        return isDelete;
    }
    public void setIsShow(String isShow) 
    {
        this.isShow = isShow;
    }

    public String getIsShow() 
    {
        return isShow;
    }
    public void setCoverPic(String coverPic) 
    {
        this.coverPic = coverPic;
    }

    public String getCoverPic() 
    {
        return coverPic;
    }
    public void setShopTopPics(String shopTopPics) 
    {
        this.shopTopPics = shopTopPics;
    }

    public String getShopTopPics() 
    {
        return shopTopPics;
    }
    public void setSelfShop(String selfShop) 
    {
        this.selfShop = selfShop;
    }

    public String getSelfShop() 
    {
        return selfShop;
    }
    public void setShopOrder(Long shopOrder) 
    {
        this.shopOrder = shopOrder;
    }

    public Long getShopOrder() 
    {
        return shopOrder;
    }
    public void setServiceType(String serviceType) 
    {
        this.serviceType = serviceType;
    }

    public String getServiceType() 
    {
        return serviceType;
    }
    public void setChargePerson(String chargePerson) 
    {
        this.chargePerson = chargePerson;
    }

    public String getChargePerson() 
    {
        return chargePerson;
    }
    public void setShopType(String shopType) 
    {
        this.shopType = shopType;
    }

    public String getShopType() 
    {
        return shopType;
    }
    public void setShopTags(String shopTags) 
    {
        this.shopTags = shopTags;
    }

    public String getShopTags() 
    {
        return shopTags;
    }
    public void setCanUsePoints(String canUsePoints) 
    {
        this.canUsePoints = canUsePoints;
    }

    public String getCanUsePoints() 
    {
        return canUsePoints;
    }
    public void setServiceRange(String serviceRange) 
    {
        this.serviceRange = serviceRange;
    }

    public String getServiceRange() 
    {
        return serviceRange;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("address", getAddress())
            .append("phone", getPhone())
            .append("tel", getTel())
            .append("workContent", getWorkContent())
            .append("workTime", getWorkTime())
            .append("lonLat", getLonLat())
            .append("score", getScore())
            .append("num", getNum())
            .append("points", getPoints())
            .append("isDelete", getIsDelete())
            .append("isShow", getIsShow())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("coverPic", getCoverPic())
            .append("shopTopPics", getShopTopPics())
            .append("selfShop", getSelfShop())
            .append("shopOrder", getShopOrder())
            .append("serviceType", getServiceType())
            .append("chargePerson", getChargePerson())
            .append("shopType", getShopType())
            .append("shopTags", getShopTags())
            .append("canUsePoints", getCanUsePoints())
            .append("serviceRange", getServiceRange())
            .toString();
    }
}
