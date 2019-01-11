package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.highcharts.common.base.BaseController;
import com.highcharts.shiro.entity.SysUser;
import com.highcharts.shiro.service.SysPermissionInitService;
import com.highcharts.shiro.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: Spring-Boot-Multi
 * @description: 测试接口
 * @author: Brucezheng
 * @create: 2018-08-11 10:57
 **/
@RestController
@RequestMapping(value = "test")
public class TestController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    private SysPermissionInitService sysPermissionInitService;

    /**
     * 获取所有用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/byid", produces = JSON_UTF8)
    public String getinfobyid(String id) {
        List<SysUser> sysUsers = sysUserService.selectList(new EntityWrapper<SysUser>());
        return JSON.toJSONString(sysUsers, true);
    }

    /**
     * 测试自定义的mapper中写的sql是否有作用
     * @return
     */
    @RequestMapping(value = "/SysPermissionInitService.do")
    public String testSysPermissionInitService() {
        return JSON.toJSONString(sysPermissionInitService.selectAll(), true);
    }

    @RequestMapping(value = "tree.do",produces = JSON_UTF8)
    public String testTree(){
return "[{\"id\":\"p_cisetting_main\",\"text\":\"系统设置\",\"children\":[{\"id\":\"cisetting_main_1\",\"text\":\"账号管理\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"cisetting_main_2\",\"text\":\"登录日志\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":true},{\"id\":\"cisetting_main_3\",\"text\":\"角色管理\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false}],\"state\":null,\"iconCls\":null,\"checked\":false},{\"id\":\"p_complaint_main\",\"text\":\"投诉反馈\",\"children\":[],\"state\":null,\"iconCls\":null,\"checked\":false},{\"id\":\"p_cxleanrecord_main\",\"text\":\"保洁记录\",\"children\":[],\"state\":null,\"iconCls\":null,\"checked\":false},{\"id\":\"p_dpatrol_main\",\"text\":\"巡查记录\",\"children\":[],\"state\":null,\"iconCls\":null,\"checked\":false},{\"id\":\"p_facilities_main\",\"text\":\"设备管理\",\"children\":[{\"id\":\"facilities_main_1\",\"text\":\"添加设备\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"facilities_main_2\",\"text\":\"设备列表\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"facilities_main_3\",\"text\":\"报修记录\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false}],\"state\":null,\"iconCls\":null,\"checked\":false},{\"id\":\"p_man_main\",\"text\":\"人员管理\",\"children\":[{\"id\":\"man_main_1\",\"text\":\"添加人员\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"man_main_2\",\"text\":\"人员列表\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"man_main_3\",\"text\":\"人员分组\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"man_main_4\",\"text\":\"人员考勤\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false}],\"state\":null,\"iconCls\":null,\"checked\":false},{\"id\":\"p_toilet_main\",\"text\":\"公厕管理\",\"children\":[{\"id\":\"toilet_main_1\",\"text\":\"添加公厕\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"toilet_main_2\",\"text\":\"公厕列表\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"toilet_main_3\",\"text\":\"公厕分组\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false},{\"id\":\"toilet_main_4\",\"text\":\"搜索记录\",\"children\":null,\"state\":null,\"iconCls\":\"icon-blank\",\"checked\":false}],\"state\":null,\"iconCls\":null,\"checked\":false}]";
    }
}
