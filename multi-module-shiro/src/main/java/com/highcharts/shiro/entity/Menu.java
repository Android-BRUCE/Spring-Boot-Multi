package com.highcharts.shiro.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
@Data
public class Menu {
	/**
	 * 一级菜单名称
	 */
	private String text;
	/**
	 * 该一级菜单下所有子项目
	 */
	private List<MenuItems> items;
}
