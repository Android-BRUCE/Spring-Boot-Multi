package com.highcharts.service;

import com.highcharts.common.pojo.EUDataGridResult;
import com.highcharts.common.pojo.QueryCondition;
import com.highcharts.mapper.UserMapper;
import com.highcharts.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: Spring-Boot-Multi
 * @description: 用户信息
 * @author: Brucezheng
 * @create: 2018-03-29 15:20
 **/
public interface UserServiceIn extends CommonService{
     User getByName(String username);
}
