package com.highcharts.mq.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 16:18
 * Description:
 */
@Data
public class Msg implements Serializable {
    private int id;
    private String content;
	private long ttl;
}
