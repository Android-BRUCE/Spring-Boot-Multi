package com.highcharts.shiro.entity;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用来接收页面传过来的查询字段   对象
 *
 * @author z77z
 */
@Data
@Accessors(chain = true)
public class FrontPage<T> {
    //是否是查询
    private boolean _search;

    //时间戳（毫秒）
    private String nd;

    //每页显示条数
    private int rows;

    //当前页数
    private int page;

    //排序的字段
    private String sidx;

    //排序方式 asc升序  desc降序
    private String sord;

    //搜索条件
    private String keywords;


    //获取mybatisPlus封装的Page对象 以下几个字段必传
    public Page<T> getPagePlus() {
        Page<T> pagePlus = new Page<T>();
        pagePlus.setCurrent(this.page);
        pagePlus.setSize(this.rows);
        pagePlus.setAsc(this.sord.equals("asc"));
        pagePlus.setOrderByField(this.sidx);
        return pagePlus;
    }
}
