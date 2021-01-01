package com.carservice.project.shop.service.impl;

import java.util.List;
import com.carservice.common.utils.DateUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.shop.mapper.CEvaluateMapper;
import com.carservice.project.shop.domain.CEvaluate;
import com.carservice.project.shop.service.ICEvaluateService;

/**
 * 用户评价Service业务层处理
 * 
 * @author carservice
 * @date 2020-12-17
 */
@Service
public class CEvaluateServiceImpl implements ICEvaluateService 
{
    @Autowired
    private CEvaluateMapper cEvaluateMapper;

    /**
     * 查询用户评价
     * 
     * @param id 用户评价ID
     * @return 用户评价
     */
    @Override
    public CEvaluate selectCEvaluateById(Long id)
    {
        return cEvaluateMapper.selectCEvaluateById(id);
    }

    /**
     * 查询用户评价列表
     * 
     * @param cEvaluate 用户评价
     * @return 用户评价
     */
    @Override
    @DataScope(deptAlias = "s")
    public List<CEvaluate> selectCEvaluateList(CEvaluate cEvaluate)
    {
        return cEvaluateMapper.selectCEvaluateList(cEvaluate);
    }

    @Override
    public List<CEvaluate> getByShopId(Long shopId) {
        return cEvaluateMapper.getByShopId(shopId);
    }

    /**
     * 新增用户评价
     * 
     * @param cEvaluate 用户评价
     * @return 结果
     */
    @Override
    public int insertCEvaluate(CEvaluate cEvaluate)
    {
        cEvaluate.setCreateTime(DateUtils.getNowDate());
        return cEvaluateMapper.insertCEvaluate(cEvaluate);
    }

    /**
     * 修改用户评价
     * 
     * @param cEvaluate 用户评价
     * @return 结果
     */
    @Override
    public int updateCEvaluate(CEvaluate cEvaluate)
    {
        return cEvaluateMapper.updateCEvaluate(cEvaluate);
    }

    /**
     * 批量删除用户评价
     * 
     * @param ids 需要删除的用户评价ID
     * @return 结果
     */
    @Override
    public int deleteCEvaluateByIds(Long[] ids)
    {
        return cEvaluateMapper.deleteCEvaluateByIds(ids);
    }

    /**
     * 删除用户评价信息
     * 
     * @param id 用户评价ID
     * @return 结果
     */
    @Override
    public int deleteCEvaluateById(Long id)
    {
        return cEvaluateMapper.deleteCEvaluateById(id);
    }
}
