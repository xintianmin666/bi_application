package com.carservice.project.shop.service;

import com.carservice.project.shop.domain.CShop;
import com.carservice.project.system.domain.SysUser;

import java.util.List;

/**
 * 店铺商户Service接口
 * 
 * @author carservice
 * @date 2020-12-12
 */
public interface ICShopService 
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
     * 批量删除店铺商户
     * 
     * @param ids 需要删除的店铺商户ID
     * @return 结果
     */
    public int deleteCShopByIds(Long[] ids);

    /**
     * 删除店铺商户信息
     * 
     * @param id 店铺商户ID
     * @return 结果
     */
    public int deleteCShopById(Long id);

    /**
     * 新增店铺商户，登录用户
     *
     * @param cShop 店铺商户
     * @param user 登录用户
     * @return 结果
     */
    int insertCShop(CShop cShop, SysUser user);

    /**
     *获取商铺名称数量
     * @param name
     * @return
     */
    int getNameCount(String name);

    int updateCShop(CShop cShop, SysUser user);

    CShop selectCShopByUserId(Long userId);

    List<CShop> selectShopListOrderByJuli(CShop cShop);
}
