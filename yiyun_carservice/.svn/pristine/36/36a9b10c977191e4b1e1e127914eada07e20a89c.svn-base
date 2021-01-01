package com.carservice.project.business.mapper;

import com.carservice.project.business.domain.COrder;
import com.carservice.project.business.domain.COrderGoods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface COrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(COrder record);

    int insertSelective(COrder record);

    COrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(COrder record);

    int updateByPrimaryKey(COrder record);

    List<COrderGoods> getOrderByBusinessID(COrder cOrder);

    void updateByOrderCode(COrder order);

    List<COrderGoods> getCheckOrderGoods(COrderGoods pojo);

    List<COrder> selectCOrderList(COrder cOrder);
}