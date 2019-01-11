package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.highcharts.shiro.base.BaseController;;
import com.highcharts.common.utils.IDUtils;
import com.highcharts.shiro.entity.*;
import com.highcharts.shiro.service.SysRoleService;
import com.highcharts.shiro.service.SysUserRoleService;
import com.highcharts.shiro.service.SysUserService;
import com.highcharts.shiro.util.MyDES;
import com.highcharts.shiro.util.WebUtilsPro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author z77z
 * @since 2017-03-11
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;

    /**
     * 权限异常(页面请求 直接跳转至login页面 ajax请求 返回json格式数据)
     */
    @ExceptionHandler({UnauthorizedException.class})
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if (WebUtilsPro.isAjaxRequest(request)) {
            // 输出JSON
            Map<String, Object> map = new HashMap<>();
            map.put("code", "-998");
            map.put("message", "无权限访问");
            WebUtilsPro.writeJson(map, response);
            return null;
        } else {
            return "redirect:/403";
        }
    }


    // 跳转到用户管理页面
    //@RequiresPermissions("create1")
    @RequestMapping(value = "userPage")
    public String userPage(String edit, Model modle) {
        // edit判断是否编辑成功
        modle.addAttribute("edit", edit);
        return "user";
    }

    // 跳轉到編輯頁面edit
    @RequestMapping(value = "editPage/{Id}")
    public String editPage(@PathVariable("Id") String Id, Model model) {
        Wrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
        List<SysRole> sysRoles = sysRoleService.selectList(wrapper);
        model.addAttribute("sysRoles", sysRoles);
        if (Id.equals("add")) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRid("-1");
            model.addAttribute("userrole", sysUserRole);
        } else {
            EntityWrapper<SysUserRole> sysUserRoleEntityWrapper = new EntityWrapper<>();
            sysUserRoleEntityWrapper.eq("uid", Id);
            SysUserRole sysUserRole = sysUserRoleService.selectOne(sysUserRoleEntityWrapper);
            if (sysUserRole == null ){
                SysUserRole sysUserRole2 = new SysUserRole();
                sysUserRole2.setRid("-1");
                model.addAttribute("userrole", sysUserRole2);
            }else {
                model.addAttribute("userrole", sysUserRole);

            }
            SysUser user = sysUserService.selectById(Id);
            model.addAttribute("user", user);
        }
        return "edit";
    }

    // 增加和修改
    @RequestMapping(value = "edit")
    @Transactional
    public String edit(SysUser user, String isEffective, Model model, String rid) {
        int status = 0;
        String userId = String.valueOf(IDUtils.getId());
        //给用户添加角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRid(rid);
        if (status == 1) {
            sysUserRole.setUid(user.getId());
        }
        //这是新添加
        if (user.getId() == null || user.getId().equals("")) {
            user.setId(userId);
            user.setCreateTime(new Date());
            user.setLastLoginTime(new Date());
            //密码进行加密处理  明文为  password+email
            String paw = user.getPswd() + user.getEmail();
            String pawDES = MyDES.encryptBasedDes(paw);
            user.setPswd(pawDES);
        } else {            //这是更新操作
            //删除旧的权限，加上新的权限
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uid", user.getId());
            sysUserRoleService.deleteByMap(map);
            //status = 1;
            SysUser OperatorUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
            user.setLastUpdateTime(new Date());
            //记录操作这条记录者的id
            user.setLastUpdateNameId(OperatorUser.getId());
            if (user.getPswd().equals("") || user.equals("/") || user.getPswd() == null){
                //不操作
            }else {
                //密码进行加密处理  明文为  password+email
                String paw = user.getPswd() + user.getEmail();
                String pawDES = MyDES.encryptBasedDes(paw);
                user.setPswd(pawDES);
            }
        }
        if (isEffective == null || isEffective == "") {
            user.setStatus("0");
        } else {
            user.setStatus("1");
        }
        //记录回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //添加角色
        SysUserRole sysUserRole1 = new SysUserRole();
        sysUserRole1.setUid(user.getId());
        sysUserRole1.setRid(rid);
        boolean insert = sysUserRoleService.insert(sysUserRole1);
        if (sysUserService.insertOrUpdate(user) && insert) {
            return "forward:userPage?edit=true";
        } else {
            //更新或插入失败，回滚。
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return "forward:userPage?edit=false";
        }
    }

    // 用户列表分页json
    @RequestMapping(value = "getUserListWithPager", produces = JSON_UTF8)
    @ResponseBody
    public String getUserListWithPager(FrontPage<SysUser> page) {
        Wrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
        String keyWords = page.getKeywords();
        if (keyWords != null && keyWords != "")
            wrapper.like("nickname", keyWords);
        Page<SysUser> pageList = sysUserService.selectPage(page.getPagePlus(), wrapper);
        CustomPage<SysUser> customPage = new CustomPage<SysUser>(pageList);
        return JSON.toJSONString(customPage);
    }

    // 刪除用户
    @RequestMapping(value = "delete", produces = JSON_UTF8)
    @ResponseBody
    public String delete(@RequestParam(value = "ids[]") String[] ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            /**
             * 删除用户角色
             */
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("uid", ids[i]);
                sysUserRoleService.deleteByMap(map);
            }


            sysUserService.deleteBatchIds(Arrays.asList(ids));
            resultMap.put("flag", true);
            resultMap.put("msg", "刪除成功！");
        } catch (Exception e) {
            resultMap.put("flag", false);
            resultMap.put("msg", e.getMessage());
        }
        return JSON.toJSONString(resultMap);
    }

    // 跳转到在线用户管理页面
    @RequestMapping(value = "onlineUserPage")
    public String onlineUserPage() {
        return "onlineUser";
    }

    // 在线用户列表json
    @RequestMapping(value = "onlineUsers", produces = JSON_UTF8)
    @ResponseBody
    public String OnlineUsers(FrontPage<UserOnlineBo> frontPage) {
        Page<UserOnlineBo> pageList = sysUserService.getPagePlus(frontPage);
        CustomPage<UserOnlineBo> customPage = new CustomPage<UserOnlineBo>(pageList);
        return JSON.toJSONString(customPage);
    }

    // 强制踢出用户
    @RequestMapping(value = "kickout", produces = JSON_UTF8)
    @ResponseBody
    public String kickout(@RequestParam(value = "ids[]") String[] ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            for (String sessionId : ids) {
                sysUserService.kickout(sessionId);
            }
            resultMap.put("flag", true);
            resultMap.put("msg", "强制踢出成功！");
        } catch (Exception e) {
            resultMap.put("flag", false);
            resultMap.put("msg", e.getMessage());
        }
        return JSON.toJSONString(resultMap);
    }


}
