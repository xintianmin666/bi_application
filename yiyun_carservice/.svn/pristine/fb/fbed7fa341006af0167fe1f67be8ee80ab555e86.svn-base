package com.carservice.project.oper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.aspectj.lang.annotation.Excels;
import com.carservice.framework.web.domain.BaseEntity;
import com.carservice.project.system.domain.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 车辆信息对象 t_vehicle_info
 * 
 * @author carservice
 * @date 2020-04-29
 */
public class TVehicleInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 汽车ID */
    private String vehicleId;

    /** 最大承载人数 */
    @Excel(name = "最大承载人数")
    private Integer carryMax;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licenseTagno;

    /** 车辆状态 */
    @Excel(name = "车辆状态" , readConverterExp = "1=正常")
    private String vehicleState;

    /** 创建人 */
    private Long createUserId;

    /** 修改时间 */
    private Date modifyTime;

    /** 修改人 */
    private Long modifyUserId;

    /** 品牌 */
    @Excel(name = "品牌")
    private String productionCompany;


    /** 车辆用途 */
    @Excel(name = "车辆用途")
    private String purpose;

    /** 车辆性质 */
    @Excel(name = "车辆类型")
    private String type;

    /** 所属公司ID */
    private String deptId;

    /** 车辆规格 */
    @Excel(name = "车辆规格")
    private String specifications;

    /** 配套设备 */
    @Excel(name = "配套设备")
    private String supportEquipment;

    /** 是否为内部车辆；1是、0否 */
    @Excel(name = "是否为内部车辆", readConverterExp = "1=是,0=否")
    private String belong;

    private String taxiLicenseUrl;
    private String taxiLicenseNo;
    private String drivingLicenseUrl;

    private String driverId;

    
    private String useCarStartTime;

    private String useCarEndTime;

    private Integer passengerNum;

    private String vehiclePlateNo;

    public String getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    public void setVehiclePlateNo(String vehiclePlateNo) {
        this.vehiclePlateNo = vehiclePlateNo;
    }

    public String getUseCarStartTime() {
        return useCarStartTime;
    }

    public void setUseCarStartTime(String useCarStartTime) {
        this.useCarStartTime = useCarStartTime;
    }

    public String getUseCarEndTime() {
        return useCarEndTime;
    }

    public void setUseCarEndTime(String useCarEndTime) {
        this.useCarEndTime = useCarEndTime;
    }

    public Integer getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(Integer passengerNum) {
        this.passengerNum = passengerNum;
    }

    private TVehicleType vehicleType;

    private String  driverPhone;

    private String driverName;

    private String gjCarId;//公交调度平台车辆id

    public String getGjCarId() {
        return gjCarId;
    }

    public void setGjCarId(String gjCarId) {
        this.gjCarId = gjCarId;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public TVehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(TVehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTaxiLicenseUrl() {
        return taxiLicenseUrl;
    }

    public void setTaxiLicenseUrl(String taxiLicenseUrl) {
        this.taxiLicenseUrl = taxiLicenseUrl;
    }

    public String getTaxiLicenseNo() {
        return taxiLicenseNo;
    }

    public void setTaxiLicenseNo(String taxiLicenseNo) {
        this.taxiLicenseNo = taxiLicenseNo;
    }

    public String getDrivingLicenseUrl() {
        return drivingLicenseUrl;
    }

    public void setDrivingLicenseUrl(String drivingLicenseUrl) {
        this.drivingLicenseUrl = drivingLicenseUrl;
    }

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
    })
    private SysDept dept;

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public void setVehicleId(String vehicleId) 
    {
        this.vehicleId = vehicleId;
    }

    public String getVehicleId() 
    {
        return vehicleId;
    }
    public void setCarryMax(Integer carryMax) 
    {
        this.carryMax = carryMax;
    }

    public Integer getCarryMax() 
    {
        return carryMax;
    }
    public void setLicenseTagno(String licenseTagno) 
    {
        this.licenseTagno = licenseTagno;
    }

    public String getLicenseTagno() 
    {
        return licenseTagno;
    }
    public void setVehicleState(String vehicleState) 
    {
        this.vehicleState = vehicleState;
    }

    public String getVehicleState() 
    {
        return vehicleState;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setModifyUserId(Long modifyUserId) 
    {
        this.modifyUserId = modifyUserId;
    }

    public Long getModifyUserId() 
    {
        return modifyUserId;
    }
    public void setProductionCompany(String productionCompany) 
    {
        this.productionCompany = productionCompany;
    }

    public String getProductionCompany() 
    {
        return productionCompany;
    }
    public void setPurpose(String purpose) 
    {
        this.purpose = purpose;
    }

    public String getPurpose() 
    {
        return purpose;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }
    public void setSpecifications(String specifications) 
    {
        this.specifications = specifications;
    }

    public String getSpecifications() 
    {
        return specifications;
    }
    public void setSupportEquipment(String supportEquipment) 
    {
        this.supportEquipment = supportEquipment;
    }

    public String getSupportEquipment() 
    {
        return supportEquipment;
    }
    public void setBelong(String belong) 
    {
        this.belong = belong;
    }

    public String getBelong() 
    {
        return belong;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("vehicleId", getVehicleId())
            .append("carryMax", getCarryMax())
            .append("licenseTagno", getLicenseTagno())
            .append("vehicleState", getVehicleState())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("modifyTime", getModifyTime())
            .append("modifyUserId", getModifyUserId())
            .append("productionCompany", getProductionCompany())
            .append("purpose", getPurpose())
            .append("type", getType())
            .append("deptId", getDeptId())
            .append("specifications", getSpecifications())
            .append("supportEquipment", getSupportEquipment())
            .append("belong", getBelong())
            .toString();
    }
}
