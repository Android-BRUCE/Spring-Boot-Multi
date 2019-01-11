package com.alibaba.study.rpc.test;

/**
 * @program: Spring-Boot-Multi
 * @description: 实现服务
 * @author: Brucezheng
 * @create: 2018-06-19 15:47
 **/
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
