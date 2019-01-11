package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.highcharts.shiro.base.BaseController;
import com.highcharts.shiro.entity.CustomPage;
import com.highcharts.shiro.entity.FrontPage;
import com.highcharts.shiro.entity.SysMenu;
import com.highcharts.shiro.service.impl.SysMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhengjiahui
 * @since 2019-01-09
 */
@Controller
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @RequestMapping(value = "/listPage")
    public String indeListPage(String edit, Model modle) {
        modle.addAttribute("edit", edit);
        return "menu/menuAd";
    }

    @RequestMapping(value = "/editPage/{Id}")
    @Transactional
    public String editPage(@PathVariable("Id") String Id, Model model) {
        Wrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("sys_parent", "1");
        List<SysMenu> sysMenus = sysMenuService.selectList(wrapper);
        model.addAttribute("sysMenus", sysMenus);
        if (!Id.equals("add")) {
            SysMenu sysMenu = sysMenuService.selectById(Id);
            model.addAttribute("sysMenu", sysMenu);
            String sys_parent = sysMenu.getSys_parent();
            model.addAttribute("sys_parent", sys_parent);

        } else {
            model.addAttribute("sys_parent", "-1");
        }
        return "menu/menuedit";
    }


    @RequestMapping(value = "edit")
    @Transactional
    public String edit(SysMenu sysMenu, String isEffective) {

        if (sysMenu.getSys_parent() == null && sysMenu.getSys_parent().equals("")) {
            return "forward:listPage?edit=false";
        }
        if (sysMenu.getSysMenuId() != null) {
            sysMenuService.updateById(sysMenu);
        } else {
            sysMenuService.insert(sysMenu);
        }
        return "forward:listPage?edit=true";
    }


    @RequestMapping(value = "getListWithPager", produces = JSON_UTF8)
    @ResponseBody
    public String getPageListData(FrontPage<SysMenu> page) {
        Wrapper<SysMenu> wrapper = new EntityWrapper<>();
        String keyWords = page.getKeywords();
        if (keyWords != null && keyWords != "") {
            wrapper.like("sys_menu_name", keyWords);
        }
        wrapper.notLike("sys_parent", "1");
        Page<SysMenu> sysMenuPage = sysMenuService.selectPage(page.getPagePlus(), wrapper);
        CustomPage<SysMenu> sysMenuCustomPage = new CustomPage<>(sysMenuPage);
        return JSON.toJSONString(sysMenuCustomPage);
    }


    @RequestMapping(value = "delete", produces = JSON_UTF8)
    @ResponseBody
    @Transactional
    public String delete(@RequestParam(value = "ids[]") String[] ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        ArrayList<SysMenu> wxBusinesses = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysMenu sysMenu = sysMenuService.selectById(Integer.parseInt(ids[i]));
            if (sysMenu.getSys_parent().equals("1")) {
                resultMap.put("flag", true);
                resultMap.put("msg", "父级菜单不允许删除！");
                return JSON.toJSONString(resultMap);
            }
            wxBusinesses.add(sysMenu);
        }
        sysMenuService.deleteBatchIds(wxBusinesses);
        resultMap.put("flag", true);
        resultMap.put("msg", "刪除成功！");
        return JSON.toJSONString(resultMap);
    }
}
