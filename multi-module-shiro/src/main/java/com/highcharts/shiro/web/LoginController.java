package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.highcharts.common.base.BaseController;
import com.highcharts.shiro.config.shiro.ShiroService;
import com.highcharts.shiro.entity.*;
import com.highcharts.shiro.service.SysRoleService;
import com.highcharts.shiro.service.SysUserRoleService;
import com.highcharts.shiro.service.SysUserService;
import com.highcharts.shiro.service.impl.MenuInitService;
import com.highcharts.shiro.util.WebUtilsPro;
import com.highcharts.shiro.util.vcode.Captcha;
import com.highcharts.shiro.util.vcode.GifCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-08-09 15:50
 **/
@Controller
public class LoginController extends BaseController {
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private MenuInitService menuInitService;
    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 登录认证异常(页面请求 直接跳转至login页面 ajax请求 返回json格式数据)
     */
    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public String authenticationException(HttpServletRequest request, HttpServletResponse response) {
        if (WebUtilsPro.isAjaxRequest(request)) {
            // 输出JSON
            Map<String, Object> map = new HashMap<>();
            map.put("code", "-999");
            map.put("message", "未登录");
            WebUtilsPro.writeJson(map, response);
            return null;
        } else {
            return "redirect:/login";
        }
    }

    /**
     * 权限异常(页面请求 直接跳转至login页面 ajax请求 返回json格式数据)
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if (WebUtilsPro.isAjaxRequest(request)) {
            // 输出JSON
            Map<String, Object> map = new HashMap<>();
            map.put("code", "-998");
            map.put("message", "无权限");
            WebUtilsPro.writeJson(map, response);
            return null;
        } else {
            return "redirect:/login";
        }
    }

    //首页
    @RequestMapping(value = "index")
    public String index(Model model) {


        SysUser principal = (SysUser) SecurityUtils.getSubject().getPrincipal();

        // sysMenuService.seleMenuByRoleId(principal.get)
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", principal.getId());
        List<SysUserRole> sysUserRoles = sysUserRoleService.selectByMap(map);
        if (sysUserRoles != null) {
            MenuInitPojo menuInt = menuInitService.getMenuInt(sysUserRoles.get(0).getRid());
            String s = JSON.toJSONString(menuInt);
            model.addAttribute("menuInt", s);
            String rid = sysUserRoles.get(0).getRid();
            SysRole sysRole = sysRoleService.selectById(rid);
            model.addAttribute("rname", sysRole.getName());
        } else {

        }
        model.addAttribute("uname", principal.getNickname());
        return "index";
    }

    //登录
    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    //权限测试用
    @RequestMapping(value = "add")
    @ResponseBody
    public String add() {
        return "add";
    }

    //未授权跳转的页面
    @RequestMapping(value = "403")
    public String noPermissions() {
        return "403";
    }

    //更新权限
    @RequestMapping(value = "updatePermission")
    @ResponseBody
    public String updatePermission() {
        shiroService.updatePermission();
        return "true";
    }

    //踢出用户
    @RequestMapping(value = "kickouting")
    @ResponseBody
    public String kickouting() {

        return "kickout";
    }

    //被踢出后跳转的页面
    @RequestMapping(value = "kickout")
    public String kickout() {
        return "kickout";
    }

    /**
     * 在ShiroConfiguration配置了这个路径可以被任何人访问
     *
     * @param a
     * @return
     */
    @RequestMapping(value = "/get.do")
    public String get(String a) {
        return a;
    }

/*
    //post登录
    @RequestMapping(value = "/login.do", method = RequestMethod.POST, produces = JSON_UTF8)
    @ResponseBody
    public String login11(UserTwo user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUsername().toString(),
                user.getPassword().toString());
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.login(usernamePasswordToken);
        } catch (DisabledAccountException e) {
            return e.getLocalizedMessage();
        } catch (AuthenticationException e) {
            return "登录失败2";
        }
        return "login11";
    }*/

    //注解的使用，需要使用@ExceptionHandler注解对 UnauthorizedException（当不允许访问会抛出该异常） 进行异常的捕捉。
    //角色 多个角色时候，必须全部满足，否则任被限制
    @RequiresRoles(value = {"100004"})
    //两个权限都需要满足，满足其一任被限制
    //@RequiresPermissions(value = {"用户Session踢出1","testPerssion"})
    @RequestMapping(value = "/create.do", produces = JSON_UTF8)
    @ResponseBody
    public String create() {
        return "Create success!";
    }

    /**
     * 使用subject.hasRole判断是否有该角色
     * 使用 subject.checkPermission();判断是否有该权限
     *
     *
     * @return
     */
    @RequestMapping(value = "/create2.do", produces = JSON_UTF8)
    @ResponseBody
    public String create2() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin")) {
            return success(subject.getPrincipal().toString());
        } else {
            //
            return error("未授权");
        }

    }


    /**
     * 获取用户信息通过id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/userinfo.do", produces = JSON_UTF8)
    @ResponseBody
    public String getByid(String id) {
        SysUser sysUser = sysUserService.selectById(id);
        return JSON.toJSONString(sysUser, true);

    }

    // 用户列表分页json
    @RequestMapping(value = "getUserListWithPager")
    @ResponseBody
    public String getUserListWithPager(FrontPage<SysUser> page) {

        Wrapper<SysUser> wrapper = new EntityWrapper<SysUser>();

        String keyWords = page.getKeywords();

        if (keyWords != null && keyWords != "") {
            wrapper.like("nickname", keyWords);
        }

        Page<SysUser> pageList = sysUserService.selectPage(page.getPagePlus(), wrapper);
        CustomPage<SysUser> customPage = new CustomPage<SysUser>(pageList);
        return JSON.toJSONString(customPage, true);
    }


    /**
     * 获取验证码（Gif版本）
     *
     * @param response
     */
    @RequestMapping(value = "getGifCode.do", method = RequestMethod.GET)
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
            Captcha captcha = new GifCaptcha(146, 33, 4);
            //输出
            captcha.out(response.getOutputStream());
            HttpSession session = request.getSession(true);
            //存入Session
            session.setAttribute("_code", captcha.text().toLowerCase());
        } catch (Exception e) {
            System.err.println("获取验证码异常：" + e.getMessage());
        }
    }

    /**
     * ajax登录请求
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "ajaxLogin", method = RequestMethod.POST,produces = JSON_UTF8)
    @ResponseBody
    public Map<String, Object> submitLogin(String username, String password, String vcode, Boolean rememberMe, Model model) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        if (vcode == null || vcode == "") {
            resultMap.put("status", 500);
            resultMap.put("message", "验证码不能为空！");
            return resultMap;
        }
        Session session = SecurityUtils.getSubject().getSession();
        //转化成小写字母
        vcode = vcode.toLowerCase();
        String v = (String) session.getAttribute("_code");
        //还可以读取一次后把验证码清空，这样每次登录都必须获取验证码
        //session.removeAttribute("_come");
        if (!vcode.equals(v)) {
            resultMap.put("status", 500);
            resultMap.put("message", "验证码错误！");
            return resultMap;
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            SecurityUtils.getSubject().login(token);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");

        } catch (Exception e) {
            resultMap.put("status", 500);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
    /**
     * 退出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET,produces = JSON_UTF8)
    @ResponseBody
    public Map<String, Object> logout() {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            //退出
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultMap;
    }
}
