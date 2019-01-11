package com.highcharts.service.ServiceImp;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.github.pagehelper.PageHelper;
import com.highcharts.common.pojo.EUDataGridResult;
import com.highcharts.common.pojo.QueryCondition;
import com.highcharts.mapper.GirlMapper;
import com.highcharts.mapper.MedicineMapper;
import com.highcharts.pojo.Girl;
import com.highcharts.pojo.Medicine;
import com.highcharts.service.GirlServiceIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-11-20 10:42
 **/
@Service
public class GirlServiceImp implements GirlServiceIn {
    @Autowired
    private GirlMapper girlMapper;
    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public List<Girl> likeName(Integer age) {
        return girlMapper.likeAge(age);
    }

    @Override
    public long saveGirl(Girl girl) {
        long l = girlMapper.saveGirl(girl);
        return l;
    }

    @Override
    public Option selectRemoveCauses() {
        //查询前20
        PageHelper.startPage(1, 20, false);
        //数据库查询获取统计数据
        List<Medicine> list = medicineMapper.getMedicineList();

        //创建Option
        Option option = new Option();
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);
        option.title("药品图表").tooltip(Trigger.axis).legend("金额（元）");
        //横轴为值轴
        //option.xAxis(new ValueAxis().boundaryGap(0d, 0.01));
        option.yAxis();
        //创建类目轴
        CategoryAxis category = new CategoryAxis();
        //柱状数据
        Bar bar = new Bar("金额（元）");
        //饼图数据
        Pie pie = new Pie("金额（元）");
        //循环数据
        for (Medicine objectMap : list) {
            //设置类目
            category.data(objectMap.getName());
            //类目对应的柱状图
            bar.data(objectMap.getTotal());
            //饼图数据
            pie.data(new PieData(objectMap.getName(), objectMap.getTotal()));
        }
        //设置类目轴
        //option.yAxis(category);
        option.xAxis(category);
        //饼图的圆心和半径
        pie.center(900, 380).radius(100);
        //设置数据
        option.series(bar, pie);
        //由于药品名字过长，图表距离左侧距离设置180，关于grid可以看ECharts的官方文档
        option.grid().y(180);
        //返回Option
        return option;
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
