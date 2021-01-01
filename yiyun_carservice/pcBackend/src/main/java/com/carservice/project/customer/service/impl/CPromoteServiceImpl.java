package com.carservice.project.customer.service.impl;

import java.util.List;

import com.carservice.common.utils.StringUtils;
import com.carservice.project.customer.domain.CUserInfo;
import com.carservice.project.customer.mapper.CUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.customer.mapper.CPromoteMapper;
import com.carservice.project.customer.domain.CPromote;
import com.carservice.project.customer.service.ICPromoteService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 推广人员Service业务层处理
 * 
 * @author carservice
 * @date 2020-12-18
 */
@Service
public class CPromoteServiceImpl implements ICPromoteService 
{
    @Autowired
    private CPromoteMapper cPromoteMapper;
    @Autowired
    private CUserInfoMapper cUserInfoMapper;

    /**
     * 查询推广人员
     * 
     * @param id 推广人员ID
     * @return 推广人员
     */
    @Override
    public CPromote selectCPromoteById(Long id)
    {
        return cPromoteMapper.selectCPromoteById(id);
    }

    @Override
    public CPromote selectCPromoteByPhone(String phone) {
        return cPromoteMapper.selectCPromoteByPhone(phone);
    }

    @Override
    public CPromote selectCPromoteByCode(String code) {
        return cPromoteMapper.selectCPromoteByCode(code);
    }

    /**
     * 查询推广人员列表
     * 
     * @param cPromote 推广人员
     * @return 推广人员
     */
    @Override
    public List<CPromote> selectCPromoteList(CPromote cPromote)
    {
        return cPromoteMapper.selectCPromoteList(cPromote);
    }

    /**
     * 新增推广人员
     * 
     * @param cPromote 推广人员
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCPromote(CPromote cPromote)
    {
        CUserInfo userInfo = cUserInfoMapper.selectCUserInfoByUserPhone(cPromote.getPromotePhone());
        if (userInfo!=null){
            userInfo.setPromoteCode(cPromote.getPromoteCode());
            cUserInfoMapper.updateCUserInfo(userInfo);
        }
        return cPromoteMapper.insertCPromote(cPromote);
    }

    /**
     * 修改推广人员
     * 
     * @param cPromote 推广人员
     * @return 结果
     */
    @Override
    @Transactional
    public int updateCPromote(CPromote cPromote)
    {
        CUserInfo userInfo = cUserInfoMapper.selectCUserInfoByUserPhone(cPromote.getPromotePhone());
        if (userInfo!=null){
            userInfo.setPromoteCode(cPromote.getPromoteCode());
            cUserInfoMapper.updateCUserInfo(userInfo);
        }
        return cPromoteMapper.updateCPromote(cPromote);
    }

    /**
     * 批量删除推广人员
     * 
     * @param ids 需要删除的推广人员ID
     * @return 结果
     */
    @Override
    public int deleteCPromoteByIds(Long[] ids)
    {
        return cPromoteMapper.deleteCPromoteByIds(ids);
    }

    /**
     * 删除推广人员信息
     * 
     * @param id 推广人员ID
     * @return 结果
     */
    @Override
    public int deleteCPromoteById(Long id)
    {
        return cPromoteMapper.deleteCPromoteById(id);
    }
}
