package com.highcharts.shiro.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.highcharts.shiro.mapper.SysPermissionInitMapper;
import com.highcharts.shiro.entity.SysPermissionInit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
@Service
public class SysPermissionInitService extends ServiceImpl<SysPermissionInitMapper, SysPermissionInit> {
	
	public List<SysPermissionInit> selectAll() {
		return baseMapper.selectAll();
	}
}
