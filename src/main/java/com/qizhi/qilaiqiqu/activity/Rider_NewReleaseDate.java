package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.DateAdapter;
import com.qizhi.qilaiqiqu.model.DateModel;
import com.qizhi.qilaiqiqu.model.DayInfo;
import com.qizhi.qilaiqiqu.utils.DateUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑发布
 */
public class Rider_NewReleaseDate extends Activity implements View.OnClickListener {

    private Button btnCommit;
    private ListView listView;
    private LinearLayout layoutBack;
    private DateAdapter adapter;

    private List<DateModel> list;
    public List<String> DateList;
    private HashMap<String,String> dateMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider__new_select_date);

        init();
        initView();
        setDate();

    }



    private void init() {
        list = new ArrayList<DateModel>();
        DateList = new ArrayList<String>();
        dateMap = new HashMap<String,String>();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        layoutBack = (LinearLayout) findViewById(R.id.layout_back);

        btnCommit.setOnClickListener(this);
        layoutBack.setOnClickListener(this);
    }


    List<DayInfo> infos;

    private void setDate() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;

        int m;
        int y = year;

        for(int i = 0; i < 12; i++){
            if(month + i > 12){
                y = year+1;
                m = month+i - 12;
            }else{
                m = month+i;
            }
            // 创建月份model
            DateModel dateModel = new DateModel();
            // 设置月份
            dateModel.setMonth(m);
            // 设置当月第一天是星期几
            dateModel.setFirstDayInWeek(DateUtils.getWeekOfDate(y+"-"+m+"-"+"01")-1);
            // 设置当月有多少天
            dateModel.setDayTotal(DateUtils.getDaysByYearMonth(y,m));
            if(i == 0){
                // 设置今天是第一个月的哪天
                dateModel.setStartDate(new Date());
            }

            // 创建天集合
            infos = new ArrayList<DayInfo>();
            for(int j = 0; j < dateModel.getDayTotal(); j++){
                // 创建天model
                DayInfo dayInfo = new DayInfo();
                // 设置当天在日历应该显示的数字
                dayInfo.setDayNum(j+1);
                // 设置当天的真实日期
                dayInfo.setData(DateUtils.strToDate((y+"-"+m+"-"+(j+1))));
                // 判断是否是第一个月，是否需要将已过去的日期设为不可点
                if(i == 0){
                    if(new Date().after(DateUtils.strToDate((y+"-"+m+"-"+(j+1))))){
                        // 当天时间在每天时间之后
                        dayInfo.setState("no");
                    }else{
                        dayInfo.setState("can");
                    }
                }else{
                    dayInfo.setState("can");
                }
                infos.add(dayInfo);
            }

            dateModel.setDayInfo(infos);
            list.add(dateModel);
        }

        adapter = new DateAdapter(this,list,dateMap,"release");
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_commit:
                String date = "";
                Iterator iter = dateMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object val = entry.getValue();
                    date = date+val.toString()+",";

                }

                Intent data = new Intent();
                data.putExtra("date", date);

                setResult(0,data);
                finish();
                break;

            case R.id.layout_back:
                finish();

                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

}
