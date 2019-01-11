package com.highcharts.shiro.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.highcharts.shiro.entity.SysPermissionInit;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
@Mapper
public interface SysPermissionInitMapper extends BaseMapper<SysPermissionInit> {

	//@Select("SELECT * FROM sys_permission_init ORDER BY sort ASC")
	List<SysPermissionInit> selectAll();

}