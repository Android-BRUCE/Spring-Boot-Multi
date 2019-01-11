package com.highcharts.mq.controller;



import com.highcharts.mq.model.Msg;
import com.highcharts.mq.scenes.delaytask.DelaySender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://blog.battcn.com/2018/05/23/springboot/v2-queue-rabbitmq-delay/
 */

@RestController
@RequestMapping("/delay")
@Api("延迟队列")
public class DelayController {

	@Autowired
	private DelaySender delaySender;


	@ApiOperation("延时队列发送(发送消息的时候设置过期时间)")
	@RequestMapping(value = "/sendDelay", method = RequestMethod.POST)
	public String sendDelayMsg(@RequestBody Msg msg) {

		delaySender.send(msg);

		return "success";

	}

}
