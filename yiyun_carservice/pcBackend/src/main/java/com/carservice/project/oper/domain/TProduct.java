package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

import java.util.List;

/**
 * 产品信息对象 t_product
 * 
 * @author carservice
 * @date 2020-05-18
 */
public class TProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品编号 */
    @Excel(name = "产品编号")
    private String productCode;

    /** 产品展示图片B端 */
    @Excel(name = "产品展示图片B端")
    private String productPicTob;

    /** 产品展示图片C端 */
    @Excel(name = "产品展示图片C端")
    private String productPicToc;

    /** 产品详情 */
    @Excel(name = "产品详情")
    private String productDetail;

    /** 线路类型 */
    @Excel(name = "线路类型")
    private String lineType;

    /** 发车点 */
    @Excel(name = "发车点")
    private String startSiteId;

    /** 目的地点 */
    @Excel(name = "目的地点")
    private String endSiteId;

    /** 运行时间 */
    @Excel(name = "运行时间")
    private String runTime;

    /** 里程 */
    @Excel(name = "里程")
    private String mileage;

    /** 预订须知 */
    @Excel(name = "预订须知")
    private String bookingGuide;

    /** 起步距离（元） */
    @Excel(name = "起步距离", readConverterExp = "元=")
    private Long startingDistance;

    /** 起步价格（公里） */
    @Excel(name = "起步价格", readConverterExp = "公=里")
    private Double startingPrice;

    /** 超出价格(每公里) */
    @Excel(name = "超出价格(每公里)")
    private Double outOfPrice;

    /** $column.columnComment */
    @Excel(name = "超出价格(每公里)")
    private Long createId;

    /** 夜间服务时间（22:30-05:30） */
    @Excel(name = "夜间服务时间", readConverterExp = "2=2:30-05:30")
    private String nightServiceTime;

    /** 夜间服务费 */
    @Excel(name = "夜间服务费")
    private Double nightServiceCharge;

    /** 超时等待费（50元/小时） */
    @Excel(name = "超时等待费", readConverterExp = "5=0元/小时")
    private Double waitingTimeoutCharge;

    private String isSale;

    private String startCity;
    private String endCity;

    private String beginTime;
    private String endTime;


    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String departureFrequency;
    private String bcChargeType;

    public String getDepartureFrequency() {
        return departureFrequency;
    }

    public void setDepartureFrequency(String departureFrequency) {
        this.departureFrequency = departureFrequency;
    }

    public String getBcChargeType() {
        return bcChargeType;
    }

    public void setBcChargeType(String bcChargeType) {
        this.bcChargeType = bcChargeType;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getIsSale() {
        return isSale;
    }

    public void setIsSale(String isSale) {
        this.isSale = isSale;
    }

    /** 不选车型默认拼车价格(元/人) */
    @Excel(name = "不选车型默认拼车价格(元/人)")
    private Double pchePrice;

    /** 不选车型默认包车价格(元/人) */
    @Excel(name = "不选车型默认包车价格(元/人)")
    private Double bchePrice;

    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Double getPchePrice() {
        return pchePrice;
    }

    public void setPchePrice(Double pchePrice) {
        this.pchePrice = pchePrice;
    }

    public Double getBchePrice() {
        return bchePrice;
    }

    public void setBchePrice(Double bchePrice) {
        this.bchePrice = bchePrice;
    }

    public List<TProductPrice> getProductPriceList() {
        return productPriceList;
    }

    public void setProductPriceList(List<TProductPrice> productPriceList) {
        this.productPriceList = productPriceList;
    }

    public String getNightServiceTime() {
        return nightServiceTime;
    }

    public void setNightServiceTime(String nightServiceTime) {
        this.nightServiceTime = nightServiceTime;
    }

    public Double getNightServiceCharge() {
        return nightServiceCharge;
    }

    public void setNightServiceCharge(Double nightServiceCharge) {
        this.nightServiceCharge = nightServiceCharge;
    }

    public Double getWaitingTimeoutCharge() {
        return waitingTimeoutCharge;
    }

    public void setWaitingTimeoutCharge(Double waitingTimeoutCharge) {
        this.waitingTimeoutCharge = waitingTimeoutCharge;
    }

    private List<TProductPrice> productPriceList;

    private List<TProductSite> productSiteList;

    public List<TProductSite> getProductSiteList() {
        return productSiteList;
    }

    public void setProductSiteList(List<TProductSite> productSiteList) {
        this.productSiteList = productSiteList;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
    }
    public void setProductPicTob(String productPicTob) 
    {
        this.productPicTob = productPicTob;
    }

    public String getProductPicTob() 
    {
        return productPicTob;
    }
    public void setProductPicToc(String productPicToc) 
    {
        this.productPicToc = productPicToc;
    }

    public String getProductPicToc() 
    {
        return productPicToc;
    }
    public void setProductDetail(String productDetail) 
    {
        this.productDetail = productDetail;
    }

    public String getProductDetail() 
    {
        return productDetail;
    }
    public void setLineType(String lineType) 
    {
        this.lineType = lineType;
    }

    public String getLineType() 
    {
        return lineType;
    }
    public void setStartSiteId(String startSiteId) 
    {
        this.startSiteId = startSiteId;
    }

    public String getStartSiteId() 
    {
        return startSiteId;
    }
    public void setEndSiteId(String endSiteId) 
    {
        this.endSiteId = endSiteId;
    }

    public String getEndSiteId() 
    {
        return endSiteId;
    }
    public void setRunTime(String runTime) 
    {
        this.runTime = runTime;
    }

    public String getRunTime() 
    {
        return runTime;
    }
    public void setMileage(String mileage) 
    {
        this.mileage = mileage;
    }

    public String getMileage() 
    {
        return mileage;
    }
    public void setBookingGuide(String bookingGuide) 
    {
        this.bookingGuide = bookingGuide;
    }

    public String getBookingGuide() 
    {
        return bookingGuide;
    }
    public void setStartingDistance(Long startingDistance) 
    {
        this.startingDistance = startingDistance;
    }

    public Long getStartingDistance() 
    {
        return startingDistance;
    }
    public void setStartingPrice(Double startingPrice) 
    {
        this.startingPrice = startingPrice;
    }

    public Double getStartingPrice() 
    {
        return startingPrice;
    }
    public void setOutOfPrice(Double outOfPrice) 
    {
        this.outOfPrice = outOfPrice;
    }

    public Double getOutOfPrice() 
    {
        return outOfPrice;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productCode", getProductCode())
            .append("productPicTob", getProductPicTob())
            .append("productPicToc", getProductPicToc())
            .append("productDetail", getProductDetail())
            .append("lineType", getLineType())
            .append("startSiteId", getStartSiteId())
            .append("endSiteId", getEndSiteId())
            .append("runTime", getRunTime())
            .append("mileage", getMileage())
            .append("bookingGuide", getBookingGuide())
            .append("startingDistance", getStartingDistance())
            .append("startingPrice", getStartingPrice())
            .append("outOfPrice", getOutOfPrice())
            .append("createId", getCreateId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
