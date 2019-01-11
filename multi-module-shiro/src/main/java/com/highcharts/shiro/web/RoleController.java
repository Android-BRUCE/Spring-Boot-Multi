package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.highcharts.common.utils.JsonUtils;
import com.highcharts.shiro.base.BaseController;;
import com.highcharts.shiro.entity.*;
import com.highcharts.shiro.service.SysRolePermissionService;
import com.highcharts.shiro.service.SysRoleService;
import com.highcharts.shiro.service.impl.MenuInitService;
import com.highcharts.shiro.service.impl.SysMenuRoleServiceImpl;
import com.highcharts.shiro.service.impl.SysMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.highcharts.common.base.BaseController.JSON_UTF8;

/**
 * 角色管理Controller
 *
 * @author 作者: z77z
 * @date 创建时间：2017年3月8日 下午1:32:02
 */
@Controller
@RequestMapping(value = "role")
public class RoleController extends BaseController {

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysMenuServiceImpl sysMenuService;
    @Autowired
    private MenuInitService menuInitService;
    @Autowired
    private SysMenuRoleServiceImpl sysMenuRoleService;

    //跳转到role管理页面
    @RequestMapping(value = "rolePage")
    public String role(String edit, Model modle) {
        //edit判断是否编辑成功
        modle.addAttribute("edit", edit);
        return "role/role";
    }

    //获取角色分页对象
    @RequestMapping(value = "getRoleListWithPager", produces = JSON_UTF8)
    @ResponseBody
    public String getRoleListWithPager(FrontPage<SysRole> page) {
        Wrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
        String keyWords = page.getKeywords();
        if (keyWords != null && keyWords != "") wrapper.like("name", keyWords);
        Page<SysRole> pageList = sysRoleService.selectPage(page.getPagePlus(), wrapper);
        CustomPage<SysRole> customPage = new CustomPage<SysRole>(pageList);
        return JSON.toJSONString(customPage);
    }

    //跳轉到編輯頁面edit
    @RequestMapping(value = "editPage/{Id}")
    public String editPage(@PathVariable("Id") String Id, Model model) {
        if (Id.equals("add")) {
        } else {
            SysRole role = sysRoleService.selectById(Id);
            model.addAttribute("role", role);
        }
        return "role/edit";
    }


    //刪除
    @RequestMapping(value = "delete", produces = JSON_UTF8)
    @ResponseBody
    public String delete(@RequestParam(value = "ids[]") String[] ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            /**
             * 删除角色的所有权限
             */
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rid", ids[i]);
                sysRolePermissionService.deleteByMap(map);
            }
            /**
             * 删除角色
             */
            sysRoleService.deleteBatchIds(Arrays.asList(ids));
            resultMap.put("flag", true);
            resultMap.put("msg", "刪除成功！");
        } catch (Exception e) {
            resultMap.put("flag", false);
            resultMap.put("msg", e.getMessage());
        }
        return JSON.toJSONString(resultMap);
    }

    //增加和修改
    @RequestMapping(value = "edit")
    public String edit(SysRole role, Model model) {

        if (role.getId() == null) {
            //给新添加的角色初始化一级菜单

        }

        if (sysRoleService.insertOrUpdate(role)) {
            return "redirect:rolePage?edit=true";
        } else {
            return "redirect:rolePage?edit=false";
        }
    }

    /**
     * 删除或添加角色的权限
     *
     * @param roleId
     * @param perssionId
     * @param status
     * @return
     */
    @RequestMapping(value = "changeTreeRolePerssion.do", produces = JSON_UTF8)
    @ResponseBody
    public String changeRolePerssion(String roleId, String perssionId, int status) {
        if (status == 1) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPid(perssionId);
            sysRolePermission.setRid(roleId);
            Wrapper<SysRolePermission> wrapper = new EntityWrapper<SysRolePermission>();
            wrapper.eq("rid", roleId);
            wrapper.eq("pid", perssionId);
            int i = sysRolePermissionService.selectCount(wrapper);
            if (i == 0) {
                boolean insert = sysRolePermissionService.insert(sysRolePermission);
                System.out.println(insert);
            }
        } else {
            Wrapper<SysRolePermission> wrapper = new EntityWrapper<SysRolePermission>();
            wrapper.eq("rid", roleId);
            wrapper.eq("pid", perssionId);
            boolean delete = sysRolePermissionService.delete(wrapper);
            System.out.println(delete);
        }
        return success();
    }

    //跳轉到編輯頁面edit
    @RequestMapping(value = "editRolePerssionPage/{Id}")
    public String editRolePerssionPage(@PathVariable("Id") String Id, Model model) {
        if (Id.equals("add")) {
        } else {
            SysRole role = sysRoleService.selectById(Id);
            model.addAttribute("role", role);
        }
        return "role/addPerssion";
    }

    @RequestMapping(value = "MenuTree.do", produces = JSON_UTF8)
    @ResponseBody
    public String getAllMenu(String roleId, Model model) {

        //所有根节点集合
        LinkedHashSet<TreeNode> rroot = new LinkedHashSet<>();
        /**
         * 获取所有菜单
         */
        Wrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.notLike("sys_parent", "1");
        // wrapper.notIn("sys_parent", "1");
        List<SysMenu> sysMenus = sysMenuService.selectList(wrapper);

        MenuInitPojo menuInt = menuInitService.getMenuInt(roleId);
        List<Menu> menu = menuInt.getMenu();
        ArrayList<MenuItems> arrayList = new ArrayList();
        for (int j = 0; j < menu.size(); j++) {
            List<MenuItems> items = menu.get(j).getItems();
            for (int i = 0; i < items.size(); i++) {
                MenuItems menuItems = items.get(i);
                arrayList.add(menuItems);
            }
        }

        for (int i = 0; i < sysMenus.size(); i++) {
            String sysMenuId = sysMenus.get(i).getSysMenuId();
            TreeNode treeNode = new TreeNode(sysMenus.get(i).getSysMenuId(), sysMenus.get(i).getSysMenuName());
            for (int j = 0; j < arrayList.size(); j++) {
                MenuItems menuItems = arrayList.get(j);
                if (menuItems.getId().equals(sysMenuId)) {
                    treeNode.setChecked(true);
                }
            }
            rroot.add(treeNode);
        }

        return JsonUtils.objectToJson(rroot);
    }

    //跳轉到角色菜单編輯頁面edit
    @RequestMapping(value = "editRoleMenuPage/{Id}")
    public String editRoleMenuPage(@PathVariable("Id") String Id, Model model) {
        if (Id.equals("add")) {
        } else {
            model.addAttribute("roleId", Id);
        }
        return "role/addMenu";
    }

    @RequestMapping(value = "/roleMenu/add.do", produces = JSON_UTF8)
    @ResponseBody
    @Transactional
    public String setRoleMenu(String roleId, String menuId) {
        EntityWrapper<SysMenuRole> sysMenuRoleEntityWrapper = new EntityWrapper<>();
        sysMenuRoleEntityWrapper.eq("sys_menu_id", menuId);
        sysMenuRoleEntityWrapper.eq("sys_role_id", roleId);
        int i = sysMenuRoleService.selectCount(sysMenuRoleEntityWrapper);
        if (i > 0) {
            return error("不能重复添加");
        }
        SysMenu sysMenu = sysMenuService.selectById(menuId);
        //获取一级菜单id
        String sys_parent = sysMenu.getSys_parent();
        EntityWrapper<SysMenuRole> sysMenuRoleEntityWrapper2 = new EntityWrapper<>();
        sysMenuRoleEntityWrapper2.eq("sys_menu_id", sys_parent);
        sysMenuRoleEntityWrapper2.eq("sys_role_id", roleId);
        int i1 = sysMenuRoleService.selectCount(sysMenuRoleEntityWrapper2);
        if (i1 == 0){
            //一级菜单尚未添加，所以要先加进去
            //当前角色添加的这个二级菜单未添加一级菜单，所以要给她加上去，不然二级找不到
            SysMenuRole sysMenuRole = new SysMenuRole();
            sysMenuRole.setSysMenuId(sys_parent);
            sysMenuRole.setSysRoleId(roleId);
            sysMenuRoleService.insert(sysMenuRole);
        }
        /**
         * 获取一级菜单
         */
/*        Wrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("sys_parent", "1");
        List<SysMenu> sysMenus = sysMenuService.selectList(wrapper);*/
        /*for (int j = 0; j < sysMenus.size(); j++) {
            String sysMenuId = sysMenus.get(j).getSysMenuId();
            EntityWrapper<SysMenuRole> sysMenuRoleEntityWrapper2 = new EntityWrapper<>();
            sysMenuRoleEntityWrapper2.eq("sys_menu_id", sysMenuId);
            sysMenuRoleEntityWrapper2.eq("sys_role_id", roleId);
            int i1 = sysMenuRoleService.selectCount(sysMenuRoleEntityWrapper2);
            if (i1 == 0) {
                //当前角色添加的这个二级菜单未添加一级菜单，所以要给她加上去，不然二级找不到
                SysMenuRole sysMenuRole = new SysMenuRole();
                sysMenuRole.setSysMenuId(sysMenuId);
                sysMenuRole.setSysRoleId(roleId);
                sysMenuRoleService.insert(sysMenuRole);
            }
        }*/
        SysMenuRole sysMenuRole = new SysMenuRole();
        sysMenuRole.setSysMenuId(menuId);
        sysMenuRole.setSysRoleId(roleId);
        sysMenuRoleService.insert(sysMenuRole);
        return success();
    }

    @RequestMapping(value = "/roleMenu/delete.do", produces = JSON_UTF8)
    @ResponseBody
    @Transactional
    public String deleteRoleMenu(String roleId, String menuId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("sys_menu_id",menuId);
        map.put("sys_role_id",roleId);
        sysMenuRoleService.deleteByMap(map);
        return success();
    }


    @RequestMapping(value = "/selectRole.do", produces = JSON_UTF8)
    @ResponseBody
    public String getRoleSelect() {
        Wrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
        List<SysRole> sysRoles = sysRoleService.selectList(wrapper);
        return JSON.toJSONString(sysRoles);
    }
}
