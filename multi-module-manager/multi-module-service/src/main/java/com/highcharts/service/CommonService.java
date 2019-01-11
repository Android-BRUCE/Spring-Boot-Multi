package com.highcharts.service;

import com.highcharts.common.pojo.EUDataGridResult;
import com.highcharts.common.pojo.QueryCondition;

/**
 * @program: Spring-Boot-Multi
 * @description: 通用接口
 * @author: Brucezheng
 * @create: 2018-11-20 09:38
 **/
public interface CommonService {
    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param queryCondition
     * @return
     */
    EUDataGridResult getPageList(int page, int rows, QueryCondition queryCondition);

    /**
     * 根据id获取实体信息
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T getById(Class<T> clazz, int id);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据实体修改
     *
     * @param entity
     * @param <T>
     * @return
     */
    <T> int updateByEntity(T entity);

    /**
     * 根据实体保存
     *
     * @param entity
     * @param <T>
     * @return
     */
    <T> int saveByEntidy(T entity);
}
