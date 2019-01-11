package com.highcharts.shiro.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
@Data
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    @TableId("sys_menu_id")
    private String sysMenuId;
    /**
     * 菜单名
     */
    @TableField("sys_menu_name")
    private String sysMenuName;
    /**
     * 菜单链接
     */
    @TableField("sys_menu_url")
    private String sysMenuUrl;

    private String sys_parent;

    private Integer sys_menu_sort;

    @Override
    protected Serializable pkVal() {
        return this.sysMenuId;
    }

}
