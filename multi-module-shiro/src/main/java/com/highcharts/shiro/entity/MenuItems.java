package com.highcharts.shiro.entity;

import lombok.Data;

/**
 * @program: multi-module
 * @description:
 * @author: Brucezheng
 * @create: 2019-01-09 16:13
 **/
@Data
public class MenuItems {
    /**
     * item id
     */
    private String id;
    /**
     * item 名字
     */
    private String text;
    /**
     * item 链接
     */
    private String href;
}
