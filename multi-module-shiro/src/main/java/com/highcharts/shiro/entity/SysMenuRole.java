package com.highcharts.shiro.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
@TableName("sys_menu_role")
public class SysMenuRole extends Model<SysMenuRole> {

    private static final long serialVersionUID = 1L;

	@TableId(value="sys_menu_role_id", type= IdType.AUTO)
	private Integer sysMenuRoleId;
	@TableField("sys_menu_id")
	private String sysMenuId;
	@TableField("sys_role_id")
	private String sysRoleId;


	public Integer getSysMenuRoleId() {
		return sysMenuRoleId;
	}

	public void setSysMenuRoleId(Integer sysMenuRoleId) {
		this.sysMenuRoleId = sysMenuRoleId;
	}

	public String getSysMenuId() {
		return sysMenuId;
	}

	public void setSysMenuId(String sysMenuId) {
		this.sysMenuId = sysMenuId;
	}

	public String getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(String sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	@Override
	protected Serializable pkVal() {
		return this.sysMenuRoleId;
	}

}
