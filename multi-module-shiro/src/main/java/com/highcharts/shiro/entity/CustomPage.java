package com.highcharts.shiro.entity;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 
 * 由此对象将page对象转换成json对象，传到前台处理
 * @author z77z
 * 由于jqgrid框架定义的page对象里面的字段和mybatisplus的不一样
 * 所以这个由这个中间对象来转换
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class CustomPage<T>{
	
	//当前页数
	private int page;
	
	//每页显示数量
	private int pagesize;
	
	//总条数
	private int records;
	
	//数据列表
	private List<T> rows;
	
	//总页数
	private int total;
	
	//排序字段
	private String orderByField;
	
	//是否升序
	private boolean isAsc;


	public CustomPage(){}
	
	public CustomPage(Page<T> page){
		this.page = page.getCurrent();
		this.pagesize = page.getSize();
		this.records = page.getTotal();
		this.rows = page.getRecords();
		this.total = page.getPages();
		this.orderByField = page.getOrderByField();
		this.isAsc = page.isAsc();
	}
}
