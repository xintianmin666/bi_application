package com.yiyun.yiyuncarservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyun.yiyuncarservice.entity.COrder;
import com.yiyun.yiyuncarservice.entity.COrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author hxx
 * @version 1.0
 * @title: TripStationConfigMapper
 * @projectName yiyun
 * @description: 站点mapper
 * @date 2020/5/06 08:30
 */
@Mapper
public interface COrderMapper extends BaseMapper< COrder > {
    List< COrderVo > getOrderList(@Param("cm") Map< String, Object > param);

    COrderVo getOrderInfo(@Param("cm") Map< String, Object > param);

}
