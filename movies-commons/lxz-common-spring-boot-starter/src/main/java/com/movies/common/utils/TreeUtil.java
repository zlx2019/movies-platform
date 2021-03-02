package com.movies.common.utils;


import com.movies.common.model.base.TreeEntity;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形工具类
 * @author lx Zhang.
 * @date 2019/8/31
 */
public class TreeUtil {

    /**
     * * 解析树形数据
     * @param hierarchy 层级状态，0表示查询到所有树，1表示查询到第一层树结构，2表示查询到第二层树结构，3表示查询到第三层树结构
     * @param pId
     * @param entityList
     * @return
     */
    public static <E extends TreeEntity<E>> List<E> getTreeList(Integer hierarchy, Long pId, List<E> entityList) {
        List<E> resultList = new ArrayList<>();
        // 获取顶层元素集合
        Long parentId;
        for (E entity : entityList) {
            parentId = entity.getParentId();
            if (parentId == null || pId.equals(parentId)) {
                resultList.add(entity);
            }
        }
        if(hierarchy == 1){  return resultList;  }
        // 获取每个顶层元素的子数据集合
        for (E entity : resultList) {
            entity.setChildList(getSubList(hierarchy,entity.getId(), entityList));
        }
        return resultList;
    }

    /**
     * * 获取子数据集合
     *
     * @param id
     * @param entityList
     * @return
     */
    @Resource
    private static <E extends TreeEntity<E>> List<E> getSubList(Integer hierarchy,Long id, List<E> entityList) {
        List<E> childList = new ArrayList<>();
        Long parentId;
        // 子集的直接子对象
        for (E entity : entityList) {
            parentId = entity.getParentId();
            if (id.equals(parentId)) {
                childList.add(entity);
            }
        }
        if(hierarchy == 2){  return childList; }
        // 子集的间接子对象
        for (E entity : childList) {
            if(hierarchy == 3){
                entity.setChildList(getSubList(2,entity.getId(), entityList));
            }else{
                entity.setChildList(getSubList(hierarchy,entity.getId(), entityList));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return childList;
        }

        return childList;
    }


}
