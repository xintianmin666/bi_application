package com.carservice.project.oper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.carservice.project.system.domain.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 驾驶员信息对象 t_driver_info
 *
 * @author carservice
 * @date 2020-07-01
 */
public class TDriverInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 驾驶员ID */
    private String driverId;

    private String disableEndTime;

    public String getDisableEndTime() {
        return disableEndTime;
    }

    public void setDisableEndTime(String disableEndTime) {
        this.disableEndTime = disableEndTime;
    }

    /** 驾驶员名称 */
    @Excel(name = "驾驶员名称")
    private String name;

    /** 驾驶员电话 */
    @Excel(name = "驾驶员电话")
    private String phone;

    /** 驾驶员驾照号 */
    @Excel(name = "驾驶员驾照号")
    private String driverLicenseNo;

    /** 驾驶员状态  */
    @Excel(name = "驾驶员状态 ")
    private String state;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUserId;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifyUserId;

    /** 所在公司 */
    @Excel(name = "所在公司")
    private String deptId;

    /** 所在公司 */
    @Excel(name = "所在公司")
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /** 从业资格证号 */
    @Excel(name = "从业资格证号")
    private String jobLicenseNo;

    /** 驾驶证年审期限 */
    @Excel(name = "驾驶证年审期限")
    private String driverLicenseTerm;

    /** 从业资格证年限 */
    @Excel(name = "从业资格证年限")
    private String jobLicenseTerm;

    /** 是否为内部人员；1是、0否 */
    @Excel(name = "是否为内部人员；1是、0否")
    private String belong;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String password;

    /** 车辆Id */
    @Excel(name = "车辆Id")
    private String vehicleId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licenseTagno;

    /** 驾驶证url */
    @Excel(name = "驾驶证url")
    private String driverLicenseUrl;

    /** 从业资格证url */
    @Excel(name = "从业资格证url")
    private String jobLicenseUrl;

    /** 车辆营运证号 */
    @Excel(name = "车辆营运证号")
    private String taxiLicenseNo;

    /** 车辆营运证url */
    @Excel(name = "车辆营运证url")
    private String taxiLicenseUrl;

    /** 身份证正面 */
    @Excel(name = "身份证正面")
    private String idCardFront;

    /** 身份证背面 */
    @Excel(name = "身份证背面")
    private String idCardBack;

    /** 0：未审核  ；1：已审核 */
    @Excel(name = "0：未审核  ；1：已审核")
    private String checkStatus;

    private String erpDriverId;

    private String fOpenId;

    private String taskStartTime;

    private String taskEndTime;

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getfOpenId() {
        return fOpenId;
    }

    public void setfOpenId(String fOpenId) {
        this.fOpenId = fOpenId;
    }

    public String getErpDriverId() {
        return erpDriverId;
    }

    public void setErpDriverId(String erpDriverId) {
        this.erpDriverId = erpDriverId;
    }

    private TVehicleInfo vehicleInfo;


    public TVehicleInfo getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(TVehicleInfo vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public void setDriverId(String driverId)
    {
        this.driverId = driverId;
    }

    public String getDriverId()
    {
        return driverId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setDriverLicenseNo(String driverLicenseNo)
    {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getDriverLicenseNo()
    {
        return driverLicenseNo;
    }
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
    public void setCreateUserId(String createUserId)
    {
        this.createUserId = createUserId;
    }

    public String getCreateUserId()
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
    public void setModifyUserId(String modifyUserId)
    {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyUserId()
    {
        return modifyUserId;
    }
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setJobLicenseNo(String jobLicenseNo)
    {
        this.jobLicenseNo = jobLicenseNo;
    }

    public String getJobLicenseNo()
    {
        return jobLicenseNo;
    }
    public void setDriverLicenseTerm(String driverLicenseTerm)
    {
        this.driverLicenseTerm = driverLicenseTerm;
    }

    public String getDriverLicenseTerm()
    {
        return driverLicenseTerm;
    }
    public void setJobLicenseTerm(String jobLicenseTerm)
    {
        this.jobLicenseTerm = jobLicenseTerm;
    }

    public String getJobLicenseTerm()
    {
        return jobLicenseTerm;
    }
    public void setBelong(String belong)
    {
        this.belong = belong;
    }

    public String getBelong()
    {
        return belong;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setVehicleId(String vehicleId)
    {
        this.vehicleId = vehicleId;
    }

    public String getVehicleId()
    {
        return vehicleId;
    }
    public void setLicenseTagno(String licenseTagno)
    {
        this.licenseTagno = licenseTagno;
    }

    public String getLicenseTagno()
    {
        return licenseTagno;
    }
    public void setDriverLicenseUrl(String driverLicenseUrl)
    {
        this.driverLicenseUrl = driverLicenseUrl;
    }

    public String getDriverLicenseUrl()
    {
        return driverLicenseUrl;
    }
    public void setJobLicenseUrl(String jobLicenseUrl)
    {
        this.jobLicenseUrl = jobLicenseUrl;
    }

    public String getJobLicenseUrl()
    {
        return jobLicenseUrl;
    }
    public void setTaxiLicenseNo(String taxiLicenseNo)
    {
        this.taxiLicenseNo = taxiLicenseNo;
    }

    public String getTaxiLicenseNo()
    {
        return taxiLicenseNo;
    }
    public void setTaxiLicenseUrl(String taxiLicenseUrl)
    {
        this.taxiLicenseUrl = taxiLicenseUrl;
    }

    public String getTaxiLicenseUrl()
    {
        return taxiLicenseUrl;
    }
    public void setIdCardFront(String idCardFront)
    {
        this.idCardFront = idCardFront;
    }

    public String getIdCardFront()
    {
        return idCardFront;
    }
    public void setIdCardBack(String idCardBack)
    {
        this.idCardBack = idCardBack;
    }

    public String getIdCardBack()
    {
        return idCardBack;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("driverId", getDriverId())
                .append("name", getName())
                .append("phone", getPhone())
                .append("driverLicenseNo", getDriverLicenseNo())
                .append("state", getState())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("createUserId", getCreateUserId())
                .append("modifyTime", getModifyTime())
                .append("modifyUserId", getModifyUserId())
                .append("deptId", getDeptId())
                .append("jobLicenseNo", getJobLicenseNo())
                .append("driverLicenseTerm", getDriverLicenseTerm())
                .append("jobLicenseTerm", getJobLicenseTerm())
                .append("belong", getBelong())
                .append("password", getPassword())
                .append("vehicleId", getVehicleId())
                .append("licenseTagno", getLicenseTagno())
                .append("driverLicenseUrl", getDriverLicenseUrl())
                .append("jobLicenseUrl", getJobLicenseUrl())
                .append("taxiLicenseNo", getTaxiLicenseNo())
                .append("taxiLicenseUrl", getTaxiLicenseUrl())
                .append("idCardFront", getIdCardFront())
                .append("idCardBack", getIdCardBack())
                .append("checkStatus", getCheckStatus())
                .toString();
    }
}
