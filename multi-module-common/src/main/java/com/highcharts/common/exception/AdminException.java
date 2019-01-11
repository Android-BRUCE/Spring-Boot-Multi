package com.highcharts.common.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: multi-module
 * @description:  自定义的实体异常类
 * @author: Brucezheng
 * @create: 2018-04-28 14:36
 **/
@Data
@Accessors(chain = true)
public class AdminException extends RuntimeException {

    public AdminException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
