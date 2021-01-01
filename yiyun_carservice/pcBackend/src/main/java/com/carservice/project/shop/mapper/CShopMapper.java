package com.carservice.project.shop.mapper;

import com.carservice.project.shop.domain.CShop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺商户Mapper接口
 * 
 * @author carservice
 * @date 2020-12-12
 */
@Repository
@Mapper
public interface CShopMapper 
{
    /**
     * 查询店铺商户
     * 
     * @param id 店铺商户ID
     * @return 店铺商户
     */
    public CShop selectCShopById(Long id);

    /**
     * 查询店铺商户列表
     * 
     * @param cShop 店铺商户
     * @return 店铺商户集合
     */
    public List<CShop> selectCShopList(CShop cShop);

    /**
     * 新增店铺商户
     * 
     * @param cShop 店铺商户
     * @return 结果
     */
    public int insertCShop(CShop cShop);

    /**
     * 修改店铺商户
     * 
     * @param cShop 店铺商户
     * @return 结果
     */
    public int updateCShop(CShop cShop);

    /**
     * 删除店铺商户
     * 
     * @param id 店铺商户ID
     * @return 结果
     */
    public int deleteCShopById(Long id);

    /**
     * 批量删除店铺商户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCShopByIds(Long[] ids);

    /**
     *获取商铺名称数量
     * @param name
     * @return
     */
    int getNameCount(String name);

    void deleteUserByIds(Long[] ids);

    CShop selectCShopByUserId(Long userId);

    List<CShop> selectShopListOrderByJuli(CShop cShop);

    List<Long> getBusinessIDByDeptID(Long deptId);

    int selectCountByDeptId(Long deptId);
}
