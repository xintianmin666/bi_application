package com.carservice.project.business.mapper;

import com.carservice.project.business.domain.COrderGoods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface COrderGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(COrderGoods record);

    int insertSelective(COrderGoods record);

    COrderGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(COrderGoods record);

    int updateByPrimaryKey(COrderGoods record);

    List<COrderGoods> selectCOrderGoodsByOrderCode(String orderCode);
}