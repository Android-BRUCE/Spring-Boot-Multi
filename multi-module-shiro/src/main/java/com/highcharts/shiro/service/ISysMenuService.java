package com.highcharts.shiro.service;

import com.baomidou.mybatisplus.service.IService;
import com.highcharts.shiro.entity.SysMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
public interface ISysMenuService extends IService<SysMenu> {
    List<SysMenu> seleMenuByRoleId(String rid);
}
