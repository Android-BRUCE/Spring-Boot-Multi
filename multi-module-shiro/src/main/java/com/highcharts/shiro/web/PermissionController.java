package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.highcharts.common.base.BaseController;
import com.highcharts.common.utils.JsonUtils;
import com.highcharts.shiro.entity.*;
import com.highcharts.shiro.service.SysPermissionService;
import com.highcharts.shiro.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = "permission")
public class PermissionController extends BaseController {
    @Autowired
    SysRolePermissionService sysRolePermissionService;


    @Autowired
    SysPermissionService sysPermissionService;

    // 跳转到用户管理页面
    @RequestMapping(value = "permissionPage")
    public String userPage(String edit, Model modle) {
        // edit判断是否编辑成功
        modle.addAttribute("edit", edit);
        return "permission/permission";
    }

    // 跳轉到編輯頁面edit
    @RequestMapping(value = "editPage/{Id}")
    public String editPage(@PathVariable("Id") String Id, Model model) {
        if (Id.equals("add")) {
        } else {
            SysPermission permission = sysPermissionService.selectById(Id);
            model.addAttribute("permission", permission);
        }

        return "permission/edit";
    }

    // 增加和修改
    @RequestMapping(value = "edit")
    public String edit(SysPermission permission, Model model) {
        if (sysPermissionService.insertOrUpdate(permission)) {
            return "forward:permissionPage?edit=true";
        } else {
            return "forward:permissionPage?edit=false";
        }
    }

    // 用权限表分页json
    @RequestMapping(value = "getPermissionListWithPager", produces = JSON_UTF8)
    @ResponseBody
    public String getPermissionListWithPager(FrontPage<SysPermission> page) {
        Wrapper<SysPermission> wrapper = new EntityWrapper<SysPermission>();
        String keyWords = page.getKeywords();
        if (keyWords != null && keyWords != "")
            wrapper.like("name", keyWords);
        Page<SysPermission> pageList = sysPermissionService.selectPage(page.getPagePlus(), wrapper);
        CustomPage<SysPermission> customPage = new CustomPage<SysPermission>(pageList);
        return JSON.toJSONString(customPage);
    }

    // 刪除
    @RequestMapping(value = "delete", produces = JSON_UTF8)
    @ResponseBody
    public String delete(@RequestParam(value = "ids[]") String[] ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            sysPermissionService.deleteBatchIds(Arrays.asList(ids));
            resultMap.put("flag", true);
            resultMap.put("msg", "刪除成功！");
        } catch (Exception e) {
            resultMap.put("flag", false);
            resultMap.put("msg", e.getMessage());
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 获取权限 --树
     * @param roleId
     * @return
     */
    @RequestMapping(value = "getPerssionTree.do",produces = JSON_UTF8)
    @ResponseBody
    public String getPerssionTree(String roleId) {
        List<SysRolePermission> sysRolePermissions = null;
        if (roleId != null && !roleId.equals("")) {
            //获取这个角色的所有权限 角色——权限中间表的字段
            Wrapper<SysRolePermission> wrapper = new EntityWrapper<SysRolePermission>();
            wrapper.eq("rid", roleId);
            sysRolePermissions = sysRolePermissionService.selectList(wrapper);
        }
        Wrapper<SysPermission> wrapper = new EntityWrapper<SysPermission>();
        //获取所有的权限 一级节点
        List<SysPermission> parent = sysPermissionService.selectList(wrapper);
        //所有根节点集合
        LinkedHashSet<TreeNode> rroot = new LinkedHashSet<>();
        for (int i = 0; i < parent.size(); i++) {
            TreeNode treeNode = new TreeNode(parent.get(i).getId(), parent.get(i).getName());
            if (sysRolePermissions != null) {
                String id = parent.get(i).getId();
                for (int j = 0; j < sysRolePermissions.size(); j++) {
                    String pid = sysRolePermissions.get(j).getPid();
                    if (pid.equals(id)) {
                        treeNode.setChecked(true);
                    }
                }
            }
            rroot.add(treeNode);
        }
        return JsonUtils.objectToJson(rroot);
    }
}
