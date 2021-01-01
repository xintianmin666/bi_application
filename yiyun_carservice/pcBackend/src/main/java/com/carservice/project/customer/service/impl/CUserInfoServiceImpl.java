package com.carservice.project.customer.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.customer.mapper.CUserInfoMapper;
import com.carservice.project.customer.domain.CUserInfo;
import com.carservice.project.customer.service.ICUserInfoService;

/**
 * C端用户信息Service业务层处理
 * 
 * @author carservice
 * @date 2020-12-18
 */
@Service
public class CUserInfoServiceImpl implements ICUserInfoService 
{
    @Autowired
    private CUserInfoMapper cUserInfoMapper;

    /**
     * 查询C端用户信息
     * 
     * @param id C端用户信息ID
     * @return C端用户信息
     */
    @Override
    public CUserInfo selectCUserInfoById(Long id)
    {
        return cUserInfoMapper.selectCUserInfoById(id);
    }

    @Override
    public CUserInfo selectCUserInfoByUserPhone(String userPhone) {
        return cUserInfoMapper.selectCUserInfoByUserPhone(userPhone);
    }

    /**
     * 查询C端用户信息列表
     * 
     * @param cUserInfo C端用户信息
     * @return C端用户信息
     */
    @Override
    public List<CUserInfo> selectCUserInfoList(CUserInfo cUserInfo)
    {
        return cUserInfoMapper.selectCUserInfoList(cUserInfo);
    }

    /**
     * 新增C端用户信息
     * 
     * @param cUserInfo C端用户信息
     * @return 结果
     */
    @Override
    public int insertCUserInfo(CUserInfo cUserInfo)
    {
        return cUserInfoMapper.insertCUserInfo(cUserInfo);
    }

    /**
     * 修改C端用户信息
     * 
     * @param cUserInfo C端用户信息
     * @return 结果
     */
    @Override
    public int updateCUserInfo(CUserInfo cUserInfo)
    {
        return cUserInfoMapper.updateCUserInfo(cUserInfo);
    }

    /**
     * 批量删除C端用户信息
     * 
     * @param ids 需要删除的C端用户信息ID
     * @return 结果
     */
    @Override
    public int deleteCUserInfoByIds(Long[] ids)
    {
        return cUserInfoMapper.deleteCUserInfoByIds(ids);
    }

    /**
     * 删除C端用户信息信息
     * 
     * @param id C端用户信息ID
     * @return 结果
     */
    @Override
    public int deleteCUserInfoById(Long id)
    {
        return cUserInfoMapper.deleteCUserInfoById(id);
    }
}
