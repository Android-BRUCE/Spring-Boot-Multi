package com.highcharts.shiro.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.highcharts.shiro.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> seleMenuByRoleId(String rid);
}