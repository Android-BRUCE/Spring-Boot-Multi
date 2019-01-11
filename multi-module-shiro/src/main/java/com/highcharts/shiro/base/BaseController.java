/*
 * Copyright 2015-2016 RonCoo(http://www.roncoo.com) Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.highcharts.shiro.base;

import com.alibaba.fastjson.JSON;
//import com.highcharts.common.exception.TokenException;
import com.highcharts.common.pojo.CustomResult;
import com.highcharts.common.utils.JsonUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 控制基础类，所以controller都应该继承这个类
 *
 * @author brucezheng
 */
public class BaseController extends Base {

/*	@ExceptionHandler(TokenException.class)
	@ResponseBody
	public String defaultExceptionHandler(HttpServletRequest req, Exception e) {
		TokenException e1 = (TokenException) e;
		CustomResult customResult = new CustomResult(Integer.parseInt(e1.getStatus()), e1.getMsg(), e1.getData());
		return JSON.toJSONString(customResult);

	}*/

	public static final String TEXT_UTF8 = "text/html;charset=UTF-8";
	public static final String JSON_UTF8 = "application/json;charset=UTF-8";
	public static final String XML_UTF8 = "application/xml;charset=UTF-8";

	public static final String LIST = "list";
	public static final String VIEW = "view";
	public static final String ADD = "add";
	public static final String SAVE = "save";
	public static final String EDIT = "edit";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String PAGE = "page";

	public static final RequestMethod[] method_get_post = {RequestMethod.GET,RequestMethod.POST};


	public boolean check() {
		Object user = httpServletRequest.getSession().getAttribute("user");
		if (user == null) {
			return false;
		}
		return true;
	}

	public static String redirect(String format, Object... arguments) {
		return new StringBuffer("redirect:").append(MessageFormat.format(format, arguments)).toString();
	}

	public static String success(String navTabId) {
		return JsonUtils.objectToJson(CustomResult.build(200, navTabId));
	}

	public static String success(String navTabId, Object object) {
		return JsonUtils.objectToJson(CustomResult.build(200, navTabId, object));
	}

	public static String success() {
		return JsonUtils.objectToJson(CustomResult.build(200, "操作成功"));
	}

	public static String error(String message) {
		return JsonUtils.objectToJson(CustomResult.build(500, message));
	}

	public static String error(String message, Object object) {
		return JsonUtils.objectToJson(CustomResult.build(500, message, object));
	}

	public static String error() {
		return JsonUtils.objectToJson(CustomResult.build(500, "操作失败"));
	}

}
