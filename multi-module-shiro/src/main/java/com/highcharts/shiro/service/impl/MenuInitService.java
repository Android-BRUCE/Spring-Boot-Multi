package com.highcharts.shiro.service.impl;

import com.highcharts.shiro.entity.Menu;
import com.highcharts.shiro.entity.MenuInitPojo;
import com.highcharts.shiro.entity.MenuItems;
import com.highcharts.shiro.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: multi-module
 * @description: 初始化index菜单
 * @author: Brucezheng
 * @create: 2019-01-09 16:17
 **/
@Service
public class MenuInitService {
    @Autowired
    private SysMenuServiceImpl sysMenuService;

    public MenuInitPojo getMenuInt(String rid) {

        //String basePath = URLCurrentGet.getURl(httpServletRequest);
        String basePath = "";

        MenuInitPojo menuInitPojo = new MenuInitPojo();
        /**
         * 一级菜单
         */
        ArrayList<Menu> menus = new ArrayList<>();
        /**
         * 该角色的所有的菜单
         */
        List<SysMenu> sysMenus = sysMenuService.seleMenuByRoleId(rid);
        for (int i = 0; i < sysMenus.size(); i++) {
            if (sysMenus.get(i).getSys_parent().equals("1")){
                Menu menu = new Menu();
                ArrayList<MenuItems> menusItems = new ArrayList<>();
                for (int j=0;j<sysMenus.size();j++){
                    if (sysMenus.get(j).getSys_parent().equals(sysMenus.get(i).getSysMenuId())){
                        MenuItems menuItems = new MenuItems();
                        menuItems.setId(sysMenus.get(j).getSysMenuId());
                        menuItems.setHref(basePath+sysMenus.get(j).getSysMenuUrl());
                        menuItems.setText(sysMenus.get(j).getSysMenuName());
                        menusItems.add(menuItems);
                    }
                }
                menu.setItems(menusItems);
                menu.setText(sysMenus.get(i).getSysMenuName());
                menus.add(menu);
            }

        }

        menuInitPojo.setMenu(menus);
        return menuInitPojo;
    }
}
