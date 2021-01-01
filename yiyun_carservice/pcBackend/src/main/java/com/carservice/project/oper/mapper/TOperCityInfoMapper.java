package com.carservice.project.oper.mapper;

import java.util.List;
import com.carservice.project.oper.domain.TOperCityInfo;

/**
 * 营运城市信息Mapper接口
 * 
 * @author carservice
 * @date 2020-05-04
 */
public interface TOperCityInfoMapper 
{
    /**
     * 查询营运城市信息
     * 
     * @param operCityId 营运城市信息ID
     * @return 营运城市信息
     */
    public TOperCityInfo selectTOperCityInfoById(Long operCityId);

    /**
     * 查询营运城市信息列表
     * 
     * @param tOperCityInfo 营运城市信息
     * @return 营运城市信息集合
     */
    public List<TOperCityInfo> selectTOperCityInfoList(TOperCityInfo tOperCityInfo);

    /**
     * 新增营运城市信息
     * 
     * @param tOperCityInfo 营运城市信息
     * @return 结果
     */
    public int insertTOperCityInfo(TOperCityInfo tOperCityInfo);

    /**
     * 修改营运城市信息
     * 
     * @param tOperCityInfo 营运城市信息
     * @return 结果
     */
    public int updateTOperCityInfo(TOperCityInfo tOperCityInfo);

    /**
     * 删除营运城市信息
     * 
     * @param operCityId 营运城市信息ID
     * @return 结果
     */
    public int deleteTOperCityInfoById(Long operCityId);

    /**
     * 批量删除营运城市信息
     * 
     * @param operCityIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOperCityInfoByIds(Long[] operCityIds);
}
