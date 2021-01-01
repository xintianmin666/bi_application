package com.carservice.project.oper.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 车辆运行记录对象 t_vehicle_task_status
 * @author carservice
 * @date 2020-05-12
 */
public class TVehicleTaskStatus extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long vehicleTaskStatusId;

    /**
     * 调度单号
     */
    @Excel(name = "调度单号")
    private String dispatchOrdercode;

    /**
     * 车辆主键id
     */
    @Excel(name = "车辆主键id")
    private String vehicleInfoId;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号")
    private String vehiclePlateNo;

    /**
     * 司机id
     */
    @Excel(name = "司机id")
    private String driverId;

    /**
     * 司机姓名
     */
    @Excel(name = "司机姓名")
    private String driverName;

    /**
     * 司机联系方式
     */
    @Excel(name = "司机联系方式")
    private String driverPhone;

    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskEndTime;

    /**
     * 任务开始点名称
     */
    @Excel(name = "任务开始点名称")
    private String taskStartSiteName;

    /**
     * 任务开始点经纬度
     */
    @Excel(name = "任务开始点经纬度")
    private String taskStartSite;

    /**
     * 任务结束点名称
     */
    @Excel(name = "任务结束点名称")
    private String taskEndSiteName;

    /**
     * 任务结束点经纬度
     */
    @Excel(name = "任务结束点经纬度")
    private String taskEndSite;

    /**
     * 任务类型
     */
    @Excel(name = "任务类型")
    private Integer taskType;

    /**
     * 任务状态
     */
    @Excel(name = "任务状态")
    private Integer taskStatus;

    /**
     * $column.columnComment
     */
    private Long createUserId;

    /**
     * $column.columnComment
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * $column.columnComment
     */
    private Long modifyUserId;

    private Long deptId;

    private String deptName;

    private String belong;

    private Integer carryMax;

    private String vehicleId;

    private TDispatchOrder tDispatchOrder;
    /**
     * 产品id
     */
    @Excel(name = "产品id")
    private Long productId;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String productName;

    /**
     * 车辆乘客座位数
     */
    @Excel(name = "车辆乘客座位数")
    private Integer passengerSeatNum;

    /**
     * 剩余座位数
     */
    @Excel(name = "剩余座位数")
    private Integer remainingSeatNum;
    /**
     * 最低拼团人数
     */
    @Excel(name = "最低拼团人数")
    private Long ninPinNum;

    /**
     * 拼座人数
     */
    @Excel(name = "拼座人数")
    private Long pzNum;

    /**
     * 1:不可取消 0：可取消
     */
    @Excel(name = "1:不可取消 0：可取消")
    private String noCancel;

    private String gjDdId;

    private String taskCreatTime;

    private String taskAcceptTime;

    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    private String lockCar;
    private String lockCarRemark;

    public String getLockCar() {
        return lockCar;
    }

    public void setLockCar(String lockCar) {
        this.lockCar = lockCar;
    }

    public String getLockCarRemark() {
        return lockCarRemark;
    }

    public void setLockCarRemark(String lockCarRemark) {
        this.lockCarRemark = lockCarRemark;
    }

    public String getTaskCreatTime() {
        return taskCreatTime;
    }

    public void setTaskCreatTime(String taskCreatTime) {
        this.taskCreatTime = taskCreatTime;
    }

    public String getTaskAcceptTime() {
        return taskAcceptTime;
    }

    public void setTaskAcceptTime(String taskAcceptTime) {
        this.taskAcceptTime = taskAcceptTime;
    }

    public String getGjDdId() {
        return gjDdId;
    }

    public void setGjDdId(String gjDdId) {
        this.gjDdId = gjDdId;
    }

    private int pageNum = 1;

    private int pageSize = 10;


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getNinPinNum() {
        return ninPinNum;
    }

    public void setNinPinNum(Long ninPinNum) {
        this.ninPinNum = ninPinNum;
    }

    public Long getPzNum() {
        return pzNum;
    }

    public void setPzNum(Long pzNum) {
        this.pzNum = pzNum;
    }

    public String getNoCancel() {
        return noCancel;
    }

    public void setNoCancel(String noCancel) {
        this.noCancel = noCancel;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPassengerSeatNum() {
        return passengerSeatNum;
    }

    public void setPassengerSeatNum(Integer passengerSeatNum) {
        this.passengerSeatNum = passengerSeatNum;
    }

    public Integer getRemainingSeatNum() {
        return remainingSeatNum;
    }

    public void setRemainingSeatNum(Integer remainingSeatNum) {
        this.remainingSeatNum = remainingSeatNum;
    }

    public TDispatchOrder gettDispatchOrder() {
        return tDispatchOrder;
    }

    public void settDispatchOrder(TDispatchOrder tDispatchOrder) {
        this.tDispatchOrder = tDispatchOrder;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public Integer getCarryMax() {
        return carryMax;
    }

    public void setCarryMax(Integer carryMax) {
        this.carryMax = carryMax;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getVehicleTaskStatusId() {
        return vehicleTaskStatusId;
    }

    public void setVehicleTaskStatusId(Long vehicleTaskStatusId) {
        this.vehicleTaskStatusId = vehicleTaskStatusId;
    }

    public void setDispatchOrdercode(String dispatchOrdercode) {
        this.dispatchOrdercode = dispatchOrdercode;
    }

    public String getDispatchOrdercode() {
        return dispatchOrdercode;
    }

    public void setVehicleInfoId(String vehicleInfoId) {
        this.vehicleInfoId = vehicleInfoId;
    }

    public String getVehicleInfoId() {
        return vehicleInfoId;
    }

    public void setVehiclePlateNo(String vehiclePlateNo) {
        this.vehiclePlateNo = vehiclePlateNo;
    }

    public String getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskStartSiteName(String taskStartSiteName) {
        this.taskStartSiteName = taskStartSiteName;
    }

    public String getTaskStartSiteName() {
        return taskStartSiteName;
    }

    public void setTaskStartSite(String taskStartSite) {
        this.taskStartSite = taskStartSite;
    }

    public String getTaskStartSite() {
        return taskStartSite;
    }

    public void setTaskEndSiteName(String taskEndSiteName) {
        this.taskEndSiteName = taskEndSiteName;
    }

    public String getTaskEndSiteName() {
        return taskEndSiteName;
    }

    public void setTaskEndSite(String taskEndSite) {
        this.taskEndSite = taskEndSite;
    }

    public String getTaskEndSite() {
        return taskEndSite;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("vehicleTaskStatusId", getVehicleTaskStatusId())
                .append("dispatchOrdercode", getDispatchOrdercode())
                .append("vehicleInfoId", getVehicleInfoId())
                .append("vehiclePlateNo", getVehiclePlateNo())
                .append("driverId", getDriverId())
                .append("driverName", getDriverName())
                .append("driverPhone", getDriverPhone())
                .append("taskStartTime", getTaskStartTime())
                .append("taskEndTime", getTaskEndTime())
                .append("taskStartSiteName", getTaskStartSiteName())
                .append("taskStartSite", getTaskStartSite())
                .append("taskEndSiteName", getTaskEndSiteName())
                .append("taskEndSite", getTaskEndSite())
                .append("taskType", getTaskType())
                .append("taskStatus", getTaskStatus())
                .append("createTime", getCreateTime())
                .append("createUserId", getCreateUserId())
                .append("modifyTime", getModifyTime())
                .append("modifyUserId", getModifyUserId())
                .append("remark", getRemark())
                .toString();
    }
}