package com.carservice.project.oper.controller;

import com.carservice.common.constant.Constants;
import com.carservice.common.utils.DateUtils;
import com.carservice.common.utils.IdUtils;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.framework.redis.RedisCache;
import com.carservice.framework.security.LoginUser;
import com.carservice.framework.security.service.TokenService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.framework.web.page.TableDataInfo;
import com.carservice.project.oper.domain.TDriverInfo;
import com.carservice.project.oper.service.ITDriverInfoService;
import com.carservice.project.system.domain.SysDept;
import com.carservice.project.system.service.ISysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 驾驶员信息Controller
 * 
 * @author carservice
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/oper/driver")
public class TDriverInfoController extends BaseController
{
    @Autowired
    private ITDriverInfoService tDriverInfoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询驾驶员信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:list')")
    @GetMapping("/list")
    public TableDataInfo list(TDriverInfo tDriverInfo)
    {
        startPage();
        List<TDriverInfo> list = tDriverInfoService.selectTDriverInfoList(tDriverInfo);
        return getDataTable(list);
    }

    /**
     * 查询可用排班驾驶员信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:list')")
    @GetMapping("/listCanUse")
    public AjaxResult listCanUse(TDriverInfo tDriverInfo)
    {
        List<TDriverInfo> list = tDriverInfoService.selectCanUseTDriverInfoList(tDriverInfo);
        return AjaxResult.success(list);
    }

    /**
     * 导出驾驶员信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:export')")
    @Log(title = "驾驶员信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TDriverInfo tDriverInfo)
    {
        List<TDriverInfo> list = tDriverInfoService.selectTDriverInfoList(tDriverInfo);
        ExcelUtil<TDriverInfo> util = new ExcelUtil<TDriverInfo>(TDriverInfo.class);
        return util.exportExcel(list, "driver");
    }

    /**
     * 获取驾驶员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:query')")
    @GetMapping(value = "/{driverId}")
    public AjaxResult getInfo(@PathVariable("driverId") String driverId)
    {
        return AjaxResult.success(tDriverInfoService.selectTDriverInfoById(driverId));
    }

    /**
     * 新增驾驶员信息
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:add')")
    @Log(title = "驾驶员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TDriverInfo tDriverInfo,HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        tDriverInfo.setCreateUserId(loginUser.getUser().getUserId()+"");
        tDriverInfo.setDriverId(IdUtils.simpleUUID());
        //可用
        if (tDriverInfo.getState().equals("1")){
            tDriverInfo.setDisableEndTime(null);
        }else if (tDriverInfo.getState().equals("0")){
            //不可用,且过期时间不为空，存入redis缓存
            if (StringUtils.isNotEmpty(tDriverInfo.getDisableEndTime())){
                Long expireTime = 0l;
                expireTime = (DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",tDriverInfo.getDisableEndTime()).getTime()
                        - System.currentTimeMillis())/1000;
                if (expireTime>0){
                    redisCache.setCacheObject(Constants.DRIVER_DISABLE_END_TIME_KEY+tDriverInfo.getDriverId(),
                            tDriverInfo.getDisableEndTime(),Integer.valueOf(expireTime.toString()), TimeUnit.SECONDS);
                }
            }
        }
        AjaxResult ajaxResult = toAjax(tDriverInfoService.insertTDriverInfo(tDriverInfo));
        return ajaxResult;
    }

    /**
     * 修改驾驶员信息
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:edit')")
    @Log(title = "驾驶员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TDriverInfo tDriverInfo,HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        tDriverInfo.setModifyUserId(loginUser.getUser().getUserId()+"");

        //可用
        if (tDriverInfo.getState().equals("1")){
            tDriverInfo.setDisableEndTime(null);
        }else if (tDriverInfo.getState().equals("0")){
            //不可用,且过期时间不为空，存入redis缓存
            if (StringUtils.isNotEmpty(tDriverInfo.getDisableEndTime())){
                Long expireTime = 0l;
                expireTime = (DateUtils.dateTime("yyyy-MM-dd HH:mm:ss",tDriverInfo.getDisableEndTime()).getTime()
                        - System.currentTimeMillis())/1000;
                if (expireTime>0){
                    redisCache.setCacheObject(Constants.DRIVER_DISABLE_END_TIME_KEY+tDriverInfo.getDriverId(),
                            tDriverInfo.getDisableEndTime(),Integer.valueOf(expireTime.toString()), TimeUnit.SECONDS);
                }
            }
        }

        return toAjax(tDriverInfoService.updateTDriverInfo(tDriverInfo));
    }

    /**
     * 删除驾驶员信息
     */
    @PreAuthorize("@ss.hasPermi('oper:driver:remove')")
    @Log(title = "驾驶员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{driverIds}")
    public AjaxResult remove(@PathVariable String[] driverIds)
    {
        return toAjax(tDriverInfoService.deleteTDriverInfoByIds(driverIds));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getCompanyInfo")
    public AjaxResult getInfo()
    {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("depts", depts);
        return ajax;
    }
}
