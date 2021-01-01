package com.carservice.project.oper.service;

import java.util.List;
import com.carservice.project.oper.domain.ErpCarInfo;

/**
 * erp车辆Service接口
 * 
 * @author carservice
 * @date 2020-08-27
 */
public interface IErpCarInfoService 
{
    /**
     * 查询erp车辆
     * 
     * @param fId erp车辆ID
     * @return erp车辆
     */
    public ErpCarInfo selectErpCarInfoById(String fId);

    /**
     * 查询erp车辆列表
     * 
     * @param erpCarInfo erp车辆
     * @return erp车辆集合
     */
    public List<ErpCarInfo> selectErpCarInfoList(ErpCarInfo erpCarInfo);

    /**
     * 新增erp车辆
     * 
     * @param erpCarInfo erp车辆
     * @return 结果
     */
    public int insertErpCarInfo(ErpCarInfo erpCarInfo);

    /**
     * 修改erp车辆
     * 
     * @param erpCarInfo erp车辆
     * @return 结果
     */
    public int updateErpCarInfo(ErpCarInfo erpCarInfo);

    /**
     * 批量删除erp车辆
     * 
     * @param fIds 需要删除的erp车辆ID
     * @return 结果
     */
    public int deleteErpCarInfoByIds(String[] fIds);

    /**
     * 删除erp车辆信息
     * 
     * @param fId erp车辆ID
     * @return 结果
     */
    public int deleteErpCarInfoById(String fId);
}
