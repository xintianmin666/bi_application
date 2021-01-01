package com.carservice.project.shop.service.impl;

import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.SecurityUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.framework.aspectj.lang.annotation.DataScope;
import com.carservice.project.shop.domain.CShop;
import com.carservice.project.shop.mapper.CShopMapper;
import com.carservice.project.shop.service.ICShopService;
import com.carservice.project.system.domain.SysDept;
import com.carservice.project.system.domain.SysUser;
import com.carservice.project.system.mapper.SysDeptMapper;
import com.carservice.project.system.mapper.SysUserMapper;
import com.carservice.project.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺商户Service业务层处理
 * 
 * @author carservice
 * @date 2020-12-12
 */
@Service
public class CShopServiceImpl implements ICShopService 
{
    @Autowired
    private CShopMapper cShopMapper;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询店铺商户
     * 
     * @param id 店铺商户ID
     * @return 店铺商户
     */
    @Override
    public CShop selectCShopById(Long id)
    {
        return cShopMapper.selectCShopById(id);
    }

    /**
     * 查询店铺商户列表
     * 
     * @param cShop 店铺商户
     * @return 店铺商户
     */
    @Override
    @DataScope(deptAlias = "s",userAlias="s")
    public List<CShop> selectCShopList(CShop cShop)
    {
        return cShopMapper.selectCShopList(cShop);
    }

    /**
     * 新增店铺商户
     * 
     * @param cShop 店铺商户
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCShop(CShop cShop)
    {
        String shopType = SecurityUtils.getShopType();
        if(shopType!=null){
            //登录用户类型对应店铺类型
            cShop.setShopmaintype(shopType);
        }
        //先判断该部门下有没有唯一店铺，如果没有，该店铺就属于该部门；
        //如果该部门已经有店铺，就直接新建部门，将该店铺归属新建的部门，
        //并且将新建的部门归属到上级部门   omg
        Long deptId = cShop.getDeptId();
        int shopCount = cShopMapper.selectCountByDeptId(deptId);
        if (shopCount>=1){//新建部门
            SysDept dept = new SysDept();
            dept.setDeptName(cShop.getName());
            dept.setDeptType("3");//商户类型
            dept.setStatus("0");//正常
            dept.setParentId(deptId);//父节点id
            SysDept info = deptMapper.selectDeptById(deptId);//父节点
            dept.setAncestors(info.getAncestors() + "," + deptId);//上节点id
            deptMapper.insertDept(dept);
            cShop.setDeptId(dept.getDeptId());
        }else{
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            dept.setDeptName(cShop.getName());
            deptMapper.updateDept(dept);
        }
        cShop.setCreateTime(DateUtils.getNowDate());
        return cShopMapper.insertCShop(cShop);
    }

    /**
     * 新增店铺商户，登录用户
     *
     * @param cShop 店铺商户
     * @param user 登录用户
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCShop(CShop cShop, SysUser user) {
        // 新增用户信息
        user.setCreateTime(DateUtils.getNowDate());
        user.setNickName(cShop.getName());
        user.setPhonenumber(cShop.getPhone());
        userMapper.insertUser(user);
        // 新增用户与角色管理
        sysUserService.insertUserRole(user);
        cShop.setCreateTime(DateUtils.getNowDate());
        cShop.setUserId(user.getUserId());
        return cShopMapper.insertCShop(cShop);
    }

    /**
     *获取商铺名称数量
     * @param name
     * @return
     */
    @Override
    public int getNameCount(String name) {
        return cShopMapper.getNameCount(name);
    }


    /**
     * 修改店铺商户
     * 
     * @param cShop 店铺商户
     * @return 结果
     */
    @Override
    @Transactional
    public int updateCShop(CShop cShop)
    {
        SysDept dept = new SysDept();
        dept.setDeptId(cShop.getDeptId());
        dept.setDeptName(cShop.getName());
        deptMapper.updateDept(dept);
        cShop.setUpdateTime(DateUtils.getNowDate());
        return cShopMapper.updateCShop(cShop);
    }

    @Override
    @Transactional
    public int updateCShop(CShop cShop, SysUser user) {
        cShop.setUpdateTime(DateUtils.getNowDate());
        user.setUpdateTime(DateUtils.getNowDate());
        user.setNickName(cShop.getName());
        user.setPhonenumber(cShop.getPhone());
        userMapper.updateUser(user);
        return cShopMapper.updateCShop(cShop);
    }

    @Override
    public CShop selectCShopByUserId(Long userId) {
        return cShopMapper.selectCShopByUserId(userId);
    }

    /**
     * 距离排序
     * @param cShop
     * @return
     */
    @Override
    public List<CShop> selectShopListOrderByJuli(CShop cShop) {
        return cShopMapper.selectShopListOrderByJuli(cShop);
    }

    /**
     * 批量删除店铺商户
     * 
     * @param ids 需要删除的店铺商户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCShopByIds(Long[] ids)
    {
        CShop cShop = cShopMapper.selectCShopById(ids[0]);
        deptMapper.deleteDeptById(cShop.getDeptId());
        return cShopMapper.deleteCShopByIds(ids);
    }

    /**
     * 删除店铺商户信息
     * 
     * @param id 店铺商户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCShopById(Long id)
    {
        CShop cShop = cShopMapper.selectCShopById(id);
        deptMapper.deleteDeptById(cShop.getDeptId());
        return cShopMapper.deleteCShopById(id);
    }


}
