package com.highcharts.shiro.config.shiro;

import com.highcharts.shiro.entity.*;
import com.highcharts.shiro.service.*;
import com.highcharts.shiro.service.impl.MenuInitService;
import com.highcharts.shiro.util.MyDES;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: Spring-Boot-Multi
 * @description:实现AuthorizingRealm接口用户用户认证
 * @author: Brucezheng
 * @create: 2018-08-09 16:21
 **/
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    //用于用户查询
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysRolePermissionService sysRolePermissionService;

    @Autowired
    private MenuInitService menuInitService;
    //用户登录次数计数  redisKey 前缀
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定    一小时 redisKey 前缀
    private String SHIRO_IS_LOCK = "shiro_is_lock_";

    //角色权限和对应权限添加,需要在访问方法中验证权限 匹配则通过
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null){
            return info;
        }
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        String userId = user.getId();
        //根据用户ID查询角色（role），放入到Authorization里。
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", userId);
        //List<SysRole> roleList = sysRoleService.selectByMap(map);
        //获取这个用户所有角色-用户 中间表信息
        Set<String> roleSet = new HashSet<String>();
        Set<String> permissionSet = new HashSet<String>();
        List<SysUserRole> sysUserRoles = sysUserRoleService.selectByMap(map);
        for (int i = 0; i < sysUserRoles.size(); i++) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("id", sysUserRoles.get(i).getRid());
            //获取角色具体信息
            List<SysRole> roleList = sysRoleService.selectByMap(map2);
            for (SysRole role : roleList) {
                //遍历加到容器
                roleSet.add(role.getType());
                //获取每个角色的具体权限
                Map<String, Object> sysRolePermissionServiceMap = new HashMap<String, Object>();
                sysRolePermissionServiceMap.put("rid", role.getId());
                List<SysRolePermission> sysRolePermissions = sysRolePermissionService.selectByMap(sysRolePermissionServiceMap);
                for (int k = 0;k<sysRolePermissions.size();k++){
                    SysPermission sysPermission = sysPermissionService.selectById(sysRolePermissions.get(k).getPid());
                    if (sysPermission != null){
                        permissionSet.add(sysPermission.getName());
                    }
                }
            }
        }
        //把角色编号加入 对应字段type
        info.setRoles(roleSet);
        //Set<String> roleSet = new HashSet<String>();
        // roleSet.add("100002");
        // info.setRoles(roleSet);


        //根据用户ID查询权限（permission），放入到Authorization里。
        //Map<String, Object> map3 = new HashMap<String, Object>();
      //  map3.put("uid", userId);
      //  List<SysPermission> permissionList = sysPermissionService.selectByMap(map3);
       // Set<String> permissionSet = new HashSet<String>();
//        for (SysPermission Permission : permissionList) {
//            //把权限名录入
//            permissionSet.add(Permission.getName());
//        }

        // Set<String> permissionSet = new HashSet<String>();
        // permissionSet.add("权限添加");
        //  permissionSet.add("权限删除");

        MenuInitPojo menuInt = menuInitService.getMenuInt(sysUserRoles.get(0).getRid());
        List<Menu> menu = menuInt.getMenu();
        for (int j = 0; j < menu.size(); j++) {
            List<MenuItems> items = menu.get(j).getItems();
            for (int i = 0; i < items.size(); i++) {
                MenuItems menuItems = items.get(i);
                /**
                 * 把菜单的访问路径加进去
                 */
                permissionSet.add(menuItems.getHref());
            }
        }
        info.setStringPermissions(permissionSet);


        return info;
    }


    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //邮箱
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());

        //获取用户信息
        //  String name = authenticationToken.getPrincipal().toString();

        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.increment(SHIRO_LOGIN_COUNT + name, 1);
        //计数大于5时，设置用户被锁定一小时
        if (Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT + name)) >= 5) {
            stringRedisTemplate.expire(SHIRO_LOGIN_COUNT + name, 10, TimeUnit.SECONDS);
            opsForValue.set(SHIRO_IS_LOCK + name, "LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK + name, 10, TimeUnit.SECONDS);
        }
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK + name))) {
            throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！十秒后重试");
        }
        //SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("email", name));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", name);
        //密码进行加密处理  明文为  password+name
        String paw = password + name;
        String pawDES = MyDES.encryptBasedDes(paw);
        map.put("pswd", pawDES);
        SysUser user = null;
        // 从数据库获取对应用户名密码的用户
        List<SysUser> userList = sysUserService.selectByMap(map);

        if (userList.size() != 0) {
            user = userList.get(0);
        }

        if (null == user) {
            //这里返回后会报出对应异常
            throw new AuthenticationException("帐号或密码不正确！");
        } else if ("0".equals(user.getStatus())) {
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
            //throw new DisabledAccountException("此帐号已经设置为禁止登录！");
            throw new AuthenticationException("此帐号已经设置为禁止登录！");
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息

            //登录成功
            //更新登录时间 last login time
            user.setLastLoginTime(new Date());
            sysUserService.updateById(user);
            //清空登录计数
            opsForValue.set(SHIRO_LOGIN_COUNT + name, "0");
        }
        /**
         * 参数说明 ：
         * 1 name 用户名称
         *  第一个参数principal使用一个可以表示该用户对象唯一身份的标识即可，其实不建议直接使用用户对象本身，因为如果开启了授权缓存，
         *  shiro在存放授权信息到缓存中时会将principal处理后作为key值。使用对象可能会引发一些不可控现象。
         * 2 从数据库中获取的password 用于token（filter中登录时生成的）中的password做对比
         * 3 realm，即当前realm的名称
         *
         * 补充 SecurityUtils.getsubject().getprincipal();可以取出 一个参数值
         */
        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
