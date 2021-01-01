package com.yiyun.yiyuncarservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyun.yiyuncarservice.entity.CPointsHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author hxx
 * @version 1.0
 * @title: CPointsHistoryMapper
 * @projectName yiyun
 * @description: Bus365车站信息
 * @date 2020/2/22 14:55
 */
@Mapper
public interface CPointsHistoryMapper extends BaseMapper< CPointsHistory > {
    List< Map< String, Object > > getPointsList(@Param("cm") Map< String, Object > param);
}
