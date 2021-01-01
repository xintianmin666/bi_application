package com.carservice.project.oper.domain;

import com.carservice.project.system.domain.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 计价规则对象 t_price_rules
 * 
 * @author carservice
 * @date 2020-11-06
 */
public class TPriceRules extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 所属部门id */
    @Excel(name = "所属部门id")
    private String deptId;

    /** 用户类型1：协议用户  2：非协议用户 */
    @Excel(name = "用户类型1：协议用户  2：非协议用户")
    private String userType;

    /** 1:里程收费  2：时间收费 */
    @Excel(name = "1:里程收费  2：时间收费")
    private String chargeType;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String carType;

    /** 标准价   元 */
    @Excel(name = "标准价   元")
    private Double basePrice;

    /** 标定里程   公里 */
    @Excel(name = "标定里程   公里")
    private Long baseDistance;

    /** 每公里费用 元 */
    @Excel(name = "每公里费用 元")
    private Double oneKmPrice;

    /** 超出标定里程每公里费用 元 */
    @Excel(name = "超出标定里程每公里费用 元")
    private Double outDistancePrice;

    private String otherPrice;
    private String otherPriceRemark;
    private String formulaName;
    private String priceFormula;
    private String userParam;
    private String totalPrice;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserParam() {
        return userParam;
    }

    public void setUserParam(String userParam) {
        this.userParam = userParam;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public String getPriceFormula() {
        return priceFormula;
    }

    public void setPriceFormula(String priceFormula) {
        this.priceFormula = priceFormula;
    }





    public String getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(String otherPrice) {
        this.otherPrice = otherPrice;
    }

    public String getOtherPriceRemark() {
        return otherPriceRemark;
    }

    public void setOtherPriceRemark(String otherPriceRemark) {
        this.otherPriceRemark = otherPriceRemark;
    }


    private SysDept dept;
    private TVehicleType vehicleType;

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public TVehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(TVehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }
    public void setUserType(String userType) 
    {
        this.userType = userType;
    }

    public String getUserType() 
    {
        return userType;
    }
    public void setChargeType(String chargeType) 
    {
        this.chargeType = chargeType;
    }

    public String getChargeType() 
    {
        return chargeType;
    }
    public void setCarType(String carType) 
    {
        this.carType = carType;
    }

    public String getCarType() 
    {
        return carType;
    }
    public void setBasePrice(Double basePrice) 
    {
        this.basePrice = basePrice;
    }

    public Double getBasePrice() 
    {
        return basePrice;
    }
    public void setBaseDistance(Long baseDistance) 
    {
        this.baseDistance = baseDistance;
    }

    public Long getBaseDistance() 
    {
        return baseDistance;
    }
    public void setOneKmPrice(Double oneKmPrice) 
    {
        this.oneKmPrice = oneKmPrice;
    }

    public Double getOneKmPrice() 
    {
        return oneKmPrice;
    }
    public void setOutDistancePrice(Double outDistancePrice) 
    {
        this.outDistancePrice = outDistancePrice;
    }

    public Double getOutDistancePrice() 
    {
        return outDistancePrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptId", getDeptId())
            .append("userType", getUserType())
            .append("chargeType", getChargeType())
            .append("carType", getCarType())
            .append("basePrice", getBasePrice())
            .append("baseDistance", getBaseDistance())
            .append("oneKmPrice", getOneKmPrice())
            .append("outDistancePrice", getOutDistancePrice())
            .toString();
    }
}
