package com.highcharts.service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.github.pagehelper.PageHelper;
import com.highcharts.mapper.GirlMapper;
import com.highcharts.mapper.MedicineMapper;
import com.highcharts.pojo.Girl;
import com.highcharts.pojo.Medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>spring-boot-mybatis/com.boy.zjh.service</p>
 *
 * @author Created by BruceZheng
 * @date 2018-01-20 15:30
 **/
@Service
public interface GirlServiceIn extends CommonService{

     List<Girl> likeName(Integer age) ;

     long saveGirl(Girl girl) ;

     Option selectRemoveCauses() ;
}
