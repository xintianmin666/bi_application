package com.carservice.project.oper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

/**
 * 调度订单对象 t_dispatch_order
 * 
 * @author carservice
 * @date 2020-05-16
 */
public class TDispatchOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String dispatchOrderId;

    /** 调度单号 */
    @Excel(name = "调度单号")
    private String dispatchOrdercode;

    /** 用车数量 */
    @Excel(name = "用车数量")
    private Integer userCarNum;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskStartTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskEndTime;

    /** 线路名称 */
    @Excel(name = "线路名称")
    private String lineName;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private Integer taskType;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Integer taskStatus;

    /** 实付价格 */
    @Excel(name = "实付价格")
    private Double realPrice;

    /** 订单价格 */
    @Excel(name = "订单价格")
    private Double price;

    /** $column.columnComment */
    private Long createUserId;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /** $column.columnComment */
    private Long modifyUserId;

    public void setDispatchOrderId(String dispatchOrderId) 
    {
        this.dispatchOrderId = dispatchOrderId;
    }

    public String getDispatchOrderId() 
    {
        return dispatchOrderId;
    }
    public void setDispatchOrdercode(String dispatchOrdercode) 
    {
        this.dispatchOrdercode = dispatchOrdercode;
    }

    public String getDispatchOrdercode() 
    {
        return dispatchOrdercode;
    }
    public void setUserCarNum(Integer userCarNum) 
    {
        this.userCarNum = userCarNum;
    }

    public Integer getUserCarNum() 
    {
        return userCarNum;
    }
    public void setTaskStartTime(Date taskStartTime) 
    {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskStartTime() 
    {
        return taskStartTime;
    }
    public void setTaskEndTime(Date taskEndTime) 
    {
        this.taskEndTime = taskEndTime;
    }

    public Date getTaskEndTime() 
    {
        return taskEndTime;
    }
    public void setLineName(String lineName) 
    {
        this.lineName = lineName;
    }

    public String getLineName() 
    {
        return lineName;
    }
    public void setTaskType(Integer taskType) 
    {
        this.taskType = taskType;
    }

    public Integer getTaskType() 
    {
        return taskType;
    }
    public void setTaskStatus(Integer taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskStatus() 
    {
        return taskStatus;
    }
    public void setRealPrice(Double realPrice) 
    {
        this.realPrice = realPrice;
    }

    public Double getRealPrice() 
    {
        return realPrice;
    }
    public void setPrice(Double price) 
    {
        this.price = price;
    }

    public Double getPrice() 
    {
        return price;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dispatchOrderId", getDispatchOrderId())
            .append("dispatchOrdercode", getDispatchOrdercode())
            .append("userCarNum", getUserCarNum())
            .append("taskStartTime", getTaskStartTime())
            .append("taskEndTime", getTaskEndTime())
            .append("lineName", getLineName())
            .append("taskType", getTaskType())
            .append("taskStatus", getTaskStatus())
            .append("realPrice", getRealPrice())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("modifyTime", getModifyTime())
            .append("modifyUserId", getModifyUserId())
            .append("remark", getRemark())
            .toString();
    }
}
