package com.highcharts.shiro.base;

import com.highcharts.shiro.entity.FrontPage;
import com.highcharts.shiro.entity.SysUser;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: multi-module
 * @description:
 * @author: Brucezheng
 * @create: 2018-12-22 10:15
 **/
public interface BaseControllerImplment {
    /**
     * 管理页面
     * @param edit false or true
     * @param modle
     * @return
     */
    String indexListPage(String edit, Model modle);

    /**
     * 跳轉到編輯頁面edit
     * @param Id
     * @param model
     * @return
     */
    String editPage(@PathVariable("Id") String Id, Model model);


    String edit(SysUser user, String isEffective, Model model);

    /**
     * 列表分页json
     * @param page
     * @return
     */
    String getListWithPager(FrontPage<T> page);
}
