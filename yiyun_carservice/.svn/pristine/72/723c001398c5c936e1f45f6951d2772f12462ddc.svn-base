package com.carservice.project.oper.mapper;

import java.util.List;
import com.carservice.project.oper.domain.ErpDriverInfo;

/**
 * erp驾驶员Mapper接口
 * 
 * @author carservice
 * @date 2020-08-27
 */
public interface ErpDriverInfoMapper 
{
    /**
     * 查询erp驾驶员
     * 
     * @param fDriverid erp驾驶员ID
     * @return erp驾驶员
     */
    public ErpDriverInfo selectErpDriverInfoById(String fDriverid);

    /**
     * 查询erp驾驶员列表
     * 
     * @param erpDriverInfo erp驾驶员
     * @return erp驾驶员集合
     */
    public List<ErpDriverInfo> selectErpDriverInfoList(ErpDriverInfo erpDriverInfo);

    /**
     * 新增erp驾驶员
     * 
     * @param erpDriverInfo erp驾驶员
     * @return 结果
     */
    public int insertErpDriverInfo(ErpDriverInfo erpDriverInfo);

    /**
     * 修改erp驾驶员
     * 
     * @param erpDriverInfo erp驾驶员
     * @return 结果
     */
    public int updateErpDriverInfo(ErpDriverInfo erpDriverInfo);

    /**
     * 删除erp驾驶员
     * 
     * @param fDriverid erp驾驶员ID
     * @return 结果
     */
    public int deleteErpDriverInfoById(String fDriverid);

    /**
     * 批量删除erp驾驶员
     * 
     * @param fDriverids 需要删除的数据ID
     * @return 结果
     */
    public int deleteErpDriverInfoByIds(String[] fDriverids);
}
