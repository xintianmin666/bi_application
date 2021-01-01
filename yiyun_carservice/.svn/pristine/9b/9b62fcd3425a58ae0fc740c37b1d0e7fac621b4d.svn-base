package com.carservice.project.customer.service;

import java.util.List;
import com.carservice.project.customer.domain.CPromote;

/**
 * 推广人员Service接口
 * 
 * @author carservice
 * @date 2020-12-18
 */
public interface ICPromoteService 
{
    /**
     * 查询推广人员
     * 
     * @param id 推广人员ID
     * @return 推广人员
     */
    public CPromote selectCPromoteById(Long id);
    public CPromote selectCPromoteByPhone(String phone);
    public CPromote selectCPromoteByCode(String code);

    /**
     * 查询推广人员列表
     * 
     * @param cPromote 推广人员
     * @return 推广人员集合
     */
    public List<CPromote> selectCPromoteList(CPromote cPromote);

    /**
     * 新增推广人员
     * 
     * @param cPromote 推广人员
     * @return 结果
     */
    public int insertCPromote(CPromote cPromote);

    /**
     * 修改推广人员
     * 
     * @param cPromote 推广人员
     * @return 结果
     */
    public int updateCPromote(CPromote cPromote);

    /**
     * 批量删除推广人员
     * 
     * @param ids 需要删除的推广人员ID
     * @return 结果
     */
    public int deleteCPromoteByIds(Long[] ids);

    /**
     * 删除推广人员信息
     * 
     * @param id 推广人员ID
     * @return 结果
     */
    public int deleteCPromoteById(Long id);
}
