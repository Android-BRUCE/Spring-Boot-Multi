package com.highcharts.shiro.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import lombok.Data;

import java.util.Date;

/**
 * @program: Spring-Boot-Multi
 * @description: 用户和拥有角色dao
 * @author: Brucezheng
 * @create: 2018-12-06 11:21
 **/
@Data
public class UserRoleInfoDao {
    private String id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 邮箱|登录帐号
     */
    private String email;
    /**
     * 密码
     */
    private String pswd;
    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
    /**
     * 1:有效，0:禁止登录
     */
    private String status;
    /**
     * 最后修改人Id
     */
    @TableField(value="last_update_name_id",validate=FieldStrategy.IGNORED)
    private String lastUpdateNameId;
    /**
     * 创建人Id
     */
    @TableField(value="create_name_id")
    private String createNameId;
    /**
     * 最后修改时间
     */
    @TableField(value="last_update_time",validate=FieldStrategy.IGNORED)
    private Date lastUpdateTime;
    /**
     * 创建时间
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String type;
}
