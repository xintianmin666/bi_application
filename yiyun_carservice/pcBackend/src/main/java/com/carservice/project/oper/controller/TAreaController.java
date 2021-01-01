package com.carservice.project.oper.controller;

import java.util.ArrayList;
import java.util.List;

import com.carservice.framework.redis.RedisCache;
import com.carservice.framework.web.domain.TreeSelect;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carservice.framework.aspectj.lang.annotation.Log;
import com.carservice.framework.aspectj.lang.enums.BusinessType;
import com.carservice.project.oper.domain.TArea;
import com.carservice.project.oper.service.ITAreaService;
import com.carservice.framework.web.controller.BaseController;
import com.carservice.framework.web.domain.AjaxResult;
import com.carservice.common.utils.poi.ExcelUtil;
import com.carservice.framework.web.page.TableDataInfo;

/**
 * 地区信息Controller
 * 
 * @author carservice
 * @date 2020-04-30
 */
@RestController
@RequestMapping("/oper/area")
public class TAreaController extends BaseController
{
    @Autowired
    private ITAreaService tAreaService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询地区信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(TArea tArea)
    {
        startPage();
        List<TArea> list = tAreaService.selectTAreaList(tArea);
        return getDataTable(list);
    }

    /**
     * 导出地区信息列表
     */
    @PreAuthorize("@ss.hasPermi('oper:area:export')")
    @Log(title = "地区信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TArea tArea)
    {
        List<TArea> list = tAreaService.selectTAreaList(tArea);
        ExcelUtil<TArea> util = new ExcelUtil<TArea>(TArea.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 获取地区信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('oper:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tAreaService.selectTAreaById(id));
    }

    /**
     * 新增地区信息
     */
    @PreAuthorize("@ss.hasPermi('oper:area:add')")
    @Log(title = "地区信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TArea tArea)
    {
        return toAjax(tAreaService.insertTArea(tArea));
    }

    /**
     * 修改地区信息
     */
    @PreAuthorize("@ss.hasPermi('oper:area:edit')")
    @Log(title = "地区信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TArea tArea)
    {
        return toAjax(tAreaService.updateTArea(tArea));
    }

    /**
     * 删除地区信息
     */
    @PreAuthorize("@ss.hasPermi('oper:area:remove')")
    @Log(title = "地区信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tAreaService.deleteTAreaByIds(ids));
    }

    /**
     * 获取城市下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(TArea tArea)
    {
        List<TreeSelect> treeTAreas = new ArrayList<TreeSelect>();
        treeTAreas = redisCache.getCacheObject("treeTAreas");
        if (treeTAreas==null||treeTAreas.size()==0){
            List<TArea> list = tAreaService.selectTAreaList(tArea);
            treeTAreas= tAreaService.buildTAreaTreeSelect(list);
            redisCache.setCacheObject("treeTAreas",treeTAreas);
        }
        return AjaxResult.success(treeTAreas);
    }
}