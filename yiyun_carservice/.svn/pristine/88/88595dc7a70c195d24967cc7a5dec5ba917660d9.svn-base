package com.carservice.project.customer.service;

import java.util.List;
import com.carservice.project.customer.domain.CUserInfo;

/**
 * C端用户信息Service接口
 * 
 * @author carservice
 * @date 2020-12-18
 */
public interface ICUserInfoService 
{
    /**
     * 查询C端用户信息
     * 
     * @param id C端用户信息ID
     * @return C端用户信息
     */
    public CUserInfo selectCUserInfoById(Long id);
    public CUserInfo selectCUserInfoByUserPhone(String userPhone);
    /**
     * 查询C端用户信息列表
     * 
     * @param cUserInfo C端用户信息
     * @return C端用户信息集合
     */
    public List<CUserInfo> selectCUserInfoList(CUserInfo cUserInfo);

    /**
     * 新增C端用户信息
     * 
     * @param cUserInfo C端用户信息
     * @return 结果
     */
    public int insertCUserInfo(CUserInfo cUserInfo);

    /**
     * 修改C端用户信息
     * 
     * @param cUserInfo C端用户信息
     * @return 结果
     */
    public int updateCUserInfo(CUserInfo cUserInfo);

    /**
     * 批量删除C端用户信息
     * 
     * @param ids 需要删除的C端用户信息ID
     * @return 结果
     */
    public int deleteCUserInfoByIds(Long[] ids);

    /**
     * 删除C端用户信息信息
     * 
     * @param id C端用户信息ID
     * @return 结果
     */
    public int deleteCUserInfoById(Long id);
}
