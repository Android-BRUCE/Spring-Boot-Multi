package com.highcharts.shiro.entity;

import lombok.Data;

import java.util.List;

/**
 * @program: multi-module
 * @description:
 * @author: Brucezheng
 * @create: 2019-01-09 16:11
 **/
@Data
public class MenuInitPojo {
    /**
     *
     */
    private String id = "system";
    /**
     * 所有一级菜单
     */
    private List<Menu> menu;
}
