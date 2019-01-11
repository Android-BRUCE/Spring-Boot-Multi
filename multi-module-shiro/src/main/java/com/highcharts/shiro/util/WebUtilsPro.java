package com.highcharts.shiro.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-08-11 08:33
 **/
public class WebUtilsPro {

    /**
     * 判断是否是  Ajax 请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static void writeJson(Map map, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        String json = JSONObject.toJSON(map).toString();
        try {
            response.getWriter().write(json.toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
