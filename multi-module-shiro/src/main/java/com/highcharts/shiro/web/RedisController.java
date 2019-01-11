package com.highcharts.shiro.web;

import com.alibaba.fastjson.JSON;
import com.highcharts.common.base.BaseController;
import com.highcharts.shiro.entity.Operate;
import com.highcharts.shiro.entity.RedisInfoDetail;
import com.highcharts.shiro.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * redis监控页面Controller
 * 
 * @author 作者: z77z
 * @date 创建时间：2017年3月29日 下午1:32:02
 */
@Controller
@RequestMapping(value="redis")
public class RedisController extends BaseController {
	
	@Autowired
	RedisService redisService;
	
	//跳转到监控页面
	@RequestMapping(value="redisMonitor")
	public String redisMonitor(Model model) {
		//获取redis的info
		List<RedisInfoDetail> ridList = redisService.getRedisInfo();
		//获取redis的日志记录
		List<Operate> logList = redisService.getLogs(1000);
		//获取日志总数
		long logLen = redisService.getLogLen();
		model.addAttribute("infoList", ridList);
		model.addAttribute("logList", logList);
		model.addAttribute("logLen", logLen);
		return "redisMonitor";
	}
	//清空日志按钮
	@RequestMapping(value="logEmpty",produces = JSON_UTF8)
	@ResponseBody
	public String logEmpty(){
		return redisService.logEmpty();
	}
	
	//获取当前数据库中key的数量
	@RequestMapping(value="getKeysSize",produces = JSON_UTF8)
	@ResponseBody
	public String getKeysSize(){
		return JSON.toJSONString(redisService.getKeysSize());
	}
	
	//获取当前数据库内存使用大小情况
	@RequestMapping(value="getMemeryInfo",produces = JSON_UTF8)
	@ResponseBody
	public String getMemeryInfo(){
		return JSON.toJSONString(redisService.getMemeryInfo());
	}
}
