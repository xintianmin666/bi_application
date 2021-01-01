package com.carservice.project.shop.controller;

import com.carservice.common.constant.UserConstants;
import com.carservice.common.utils.SecurityUtils;
import com.carservice.common.utils.StringUtils;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.shop.domain.CShop;
import com.carservice.project.shop.service.ICShopService;
import com.carservice.project.system.domain.SysRole;
import com.carservice.project.system.domain.SysUser;
import com.carservice.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 店铺商户Controller
 * 
 * @author carservice
 * @date 2020-12-12
 */
@RestController
@RequestMapping("/shop/shop")
public class CShopController extends BaseController
{
    @Autowired
    private ICShopService cShopService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询店铺商户列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:list')")
    @GetMapping("/list")
    public TableDataInfo list(CShop cShop)
    {
        startPage();
        List<CShop> list = cShopService.selectCShopList(cShop);
        return getDataTable(list);
    }

    /**
     * 导出店铺商户列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:export')")
    @Log(title = "店铺商户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CShop cShop)
    {
        List<CShop> list = cShopService.selectCShopList(cShop);
        ExcelUtil<CShop> util = new ExcelUtil<CShop>(CShop.class);
        return util.exportExcel(list, "shop");
    }

    /**
     * 获取店铺商户详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cShopService.selectCShopById(id));
    }

    /**
     * 新增店铺商户
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:add')")
    @Log(title = "店铺商户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CShop cShop)
    {

        /*if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(cShop.getUserName())))
        {
            return AjaxResult.error("新增商户'" + cShop.getUserName() + "'失败，登录账号已存在");
        }*/
        if (cShopService.getNameCount(cShop.getName())>=1)
        {
            return AjaxResult.error("新增商户'" + cShop.getName() + "'失败，商户名称已存在");
        }
        cShop.setCreateBy(SecurityUtils.getUsername());
        return toAjax(cShopService.insertCShop(cShop));
    }

    /**
     * 修改店铺商户
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:edit')")
    @Log(title = "店铺商户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CShop cShop)
    {
        CShop oldShop = cShopService.selectCShopById(cShop.getId()) ;
        /*if (!oldShop.getUserName().equals(cShop.getUserName())
                &&UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(cShop.getUserName())))
        {
            return AjaxResult.error("修改商户'" + cShop.getUserName() + "'失败，登录账号已存在");
        }*/
        if (!oldShop.getName().equals(cShop.getName())
                &&cShopService.getNameCount(cShop.getName())>=1)
        {
            return AjaxResult.error("修改商户'" + cShop.getName() + "'失败，商户名称已存在");
        }
        /*SysUser user = new SysUser();
        user.setUserId(cShop.getUserId());
        user.setUpdateBy(SecurityUtils.getUsername());
        user.setUserName(cShop.getUserName());*/
        cShop.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(cShopService.updateCShop(cShop));
    }

    /**
     * 删除店铺商户
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:remove')")
    @Log(title = "店铺商户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cShopService.deleteCShopByIds(ids));
    }
}
