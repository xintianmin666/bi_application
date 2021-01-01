package com.carservice.project.oper.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.SecurityUtils;
import com.carservice.common.utils.spring.SpringUtils;
import com.carservice.framework.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TOperCityInfoMapper;
import com.carservice.project.oper.domain.TOperCityInfo;
import com.carservice.project.oper.service.ITOperCityInfoService;

/**
 * 营运城市信息Service业务层处理
 * 
 * @author carservice
 * @date 2020-05-04
 */
@Service
public class TOperCityInfoServiceImpl implements ITOperCityInfoService 
{
    @Autowired
    private TOperCityInfoMapper tOperCityInfoMapper;

    /**
     * 查询营运城市信息
     * 
     * @param operCityId 营运城市信息ID
     * @return 营运城市信息
     */
    @Override
    public TOperCityInfo selectTOperCityInfoById(Long operCityId)
    {
        return tOperCityInfoMapper.selectTOperCityInfoById(operCityId);
    }

    /**
     * 查询营运城市信息列表
     * 
     * @param tOperCityInfo 营运城市信息
     * @return 营运城市信息
     */
    @Override
    public List<TOperCityInfo> selectTOperCityInfoList(TOperCityInfo tOperCityInfo)
    {
        return tOperCityInfoMapper.selectTOperCityInfoList(tOperCityInfo);
    }

    /**
     * 新增营运城市信息
     * 
     * @param tOperCityInfo 营运城市信息
     * @return 结果
     */
    @Override
    public int insertTOperCityInfo(TOperCityInfo tOperCityInfo)
    {
        tOperCityInfo.setCreateTime(DateUtils.getNowDate());
        tOperCityInfo.setCreateUserId(SecurityUtils.getUserId());
        return tOperCityInfoMapper.insertTOperCityInfo(tOperCityInfo);
    }

    /**
     * 修改营运城市信息
     * 
     * @param tOperCityInfo 营运城市信息
     * @return 结果
     */
    @Override
    public int updateTOperCityInfo(TOperCityInfo tOperCityInfo)
    {
        tOperCityInfo.setModifyUserId(SecurityUtils.getUserId());
        tOperCityInfo.setModifyTime(DateUtils.getNowDate());
        return tOperCityInfoMapper.updateTOperCityInfo(tOperCityInfo);
    }

    /**
     * 批量删除营运城市信息
     * 
     * @param operCityIds 需要删除的营运城市信息ID
     * @return 结果
     */
    @Override
    public int deleteTOperCityInfoByIds(Long[] operCityIds)
    {
        return tOperCityInfoMapper.deleteTOperCityInfoByIds(operCityIds);
    }

    /**
     * 删除营运城市信息信息
     * 
     * @param operCityId 营运城市信息ID
     * @return 结果
     */
    @Override
    public int deleteTOperCityInfoById(Long operCityId)
    {
        return tOperCityInfoMapper.deleteTOperCityInfoById(operCityId);
    }
}
