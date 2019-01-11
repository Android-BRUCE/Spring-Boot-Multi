package com.highcharts.shiro.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.highcharts.shiro.entity.SysMenu;
import com.highcharts.shiro.mapper.SysMenuMapper;
import com.highcharts.shiro.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> seleMenuByRoleId(String rid) {
        return baseMapper.seleMenuByRoleId(rid);
    }
}
