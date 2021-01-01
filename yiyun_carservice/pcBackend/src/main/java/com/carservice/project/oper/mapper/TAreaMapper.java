package com.carservice.project.oper.mapper;

import java.util.List;
import com.carservice.project.oper.domain.TArea;

/**
 * 地区信息Mapper接口
 * 
 * @author carservice
 * @date 2020-04-30
 */
public interface TAreaMapper 
{
    /**
     * 查询地区信息
     * 
     * @param id 地区信息ID
     * @return 地区信息
     */
    public TArea selectTAreaById(String id);

    /**
     * 查询地区信息列表
     * 
     * @param tArea 地区信息
     * @return 地区信息集合
     */
    public List<TArea> selectTAreaList(TArea tArea);

    /**
     * 新增地区信息
     * 
     * @param tArea 地区信息
     * @return 结果
     */
    public int insertTArea(TArea tArea);

    /**
     * 修改地区信息
     * 
     * @param tArea 地区信息
     * @return 结果
     */
    public int updateTArea(TArea tArea);

    /**
     * 删除地区信息
     * 
     * @param id 地区信息ID
     * @return 结果
     */
    public int deleteTAreaById(String id);

    /**
     * 批量删除地区信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTAreaByIds(String[] ids);
}