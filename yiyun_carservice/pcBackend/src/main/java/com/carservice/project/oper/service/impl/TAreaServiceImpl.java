package com.carservice.project.oper.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.carservice.common.utils.StringUtils;
import com.carservice.framework.web.domain.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carservice.project.oper.mapper.TAreaMapper;
import com.carservice.project.oper.domain.TArea;
import com.carservice.project.oper.service.ITAreaService;

/**
 * 地区信息Service业务层处理
 * 
 * @author carservice
 * @date 2020-04-30
 */
@Service
public class TAreaServiceImpl implements ITAreaService 
{
    @Autowired
    private TAreaMapper tAreaMapper;

    /**
     * 查询地区信息
     * 
     * @param id 地区信息ID
     * @return 地区信息
     */
    @Override
    public TArea selectTAreaById(String id)
    {
        return tAreaMapper.selectTAreaById(id);
    }

    /**
     * 查询地区信息列表
     * 
     * @param tArea 地区信息
     * @return 地区信息
     */
    @Override
    public List<TArea> selectTAreaList(TArea tArea)
    {
        return tAreaMapper.selectTAreaList(tArea);
    }

    /**
     * 新增地区信息
     * 
     * @param tArea 地区信息
     * @return 结果
     */
    @Override
    public int insertTArea(TArea tArea)
    {
        return tAreaMapper.insertTArea(tArea);
    }

    /**
     * 修改地区信息
     * 
     * @param tArea 地区信息
     * @return 结果
     */
    @Override
    public int updateTArea(TArea tArea)
    {
        return tAreaMapper.updateTArea(tArea);
    }

    /**
     * 批量删除地区信息
     * 
     * @param ids 需要删除的地区信息ID
     * @return 结果
     */
    @Override
    public int deleteTAreaByIds(String[] ids)
    {
        return tAreaMapper.deleteTAreaByIds(ids);
    }

    /**
     * 删除地区信息信息
     * 
     * @param id 地区信息ID
     * @return 结果
     */
    @Override
    public int deleteTAreaById(String id)
    {
        return tAreaMapper.deleteTAreaById(id);
    }


    /**
     * 构建前端所需要下拉树结构
     *
     * @param list 城市列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildTAreaTreeSelect(List<TArea> list)
    {
        List<TArea> areaTrees = buildTAreaTree(list);
        return areaTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param list 地区列表
     * @return 树结构列表
     */
    @Override
    public List<TArea> buildTAreaTree(List<TArea> list)
    {
        List<TArea> returnList = new ArrayList<TArea>();
        for (TArea area : list) {
            if (area.getLevel()==1){
                returnList.add(area);
            }
        }
        for (TArea area : list) {
            if (area.getLevel()==2){
                for (TArea level1Area : returnList)
                    if (area.getParentCode().equals(level1Area.getAreaCode())){
                        level1Area.getChildren().add(area);
                    }
            }
        }
        for (TArea area : list) {
            if (area.getLevel()==3){
                for (TArea returnArea : returnList){
                    for (TArea level2Area : returnArea.getChildren()){
                        if (area.getParentCode().equals(level2Area.getAreaCode())){
                            level2Area.getChildren().add(area);
                        }
                    }
                    }
             }
        }
        return returnList;
    }
}