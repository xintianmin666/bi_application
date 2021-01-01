package com.carservice.project.oper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.carservice.framework.aspectj.lang.annotation.Excel;
import com.carservice.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区信息对象 t_area
 * 
 * @author carservice
 * @date 2020-04-30
 */
public class TArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String areaCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String areaName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long level;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String parentCode;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setAreaCode(String areaCode) 
    {
        this.areaCode = areaCode;
    }

    public String getAreaCode() 
    {
        return areaCode;
    }
    public void setAreaName(String areaName) 
    {
        this.areaName = areaName;
    }

    public String getAreaName() 
    {
        return areaName;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setParentCode(String parentCode) 
    {
        this.parentCode = parentCode;
    }

    public List<TArea> getChildren() {
        return children;
    }

    public void setChildren(List<TArea> children) {
        this.children = children;
    }

    public String getParentCode()
    {
        return parentCode;
    }

    /** 子集 */
    private List<TArea> children = new ArrayList<TArea>();

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("areaCode", getAreaCode())
            .append("areaName", getAreaName())
            .append("level", getLevel())
            .append("parentCode", getParentCode())
            .append("remark", getRemark())
            .toString();
    }
}