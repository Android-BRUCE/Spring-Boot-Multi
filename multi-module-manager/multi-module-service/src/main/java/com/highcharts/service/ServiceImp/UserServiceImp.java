package com.highcharts.service.ServiceImp;

import com.highcharts.common.pojo.EUDataGridResult;
import com.highcharts.common.pojo.QueryCondition;
import com.highcharts.mapper.UserMapper;
import com.highcharts.pojo.Girl;
import com.highcharts.pojo.User;
import com.highcharts.service.UserServiceIn;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-11-20 10:38
 **/
@Service
public class UserServiceImp implements UserServiceIn {

    @Resource
    private UserMapper userMapper;



    @Override
    public User getByName(String username) {
        User userByUserName = userMapper.getUserByUserName(username);
        return userByUserName;
    }

    @Override
    public EUDataGridResult getPageList(int page, int rows, QueryCondition queryCondition) {
        return null;
    }

    @Override
    public <T> T getById(Class<T> clazz, int id) {
        return null;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public <T> int updateByEntity(T entity) {
        return 0;
    }

    @Override
    public <T> int saveByEntidy(T entity) {
        return 0;
    }
}
