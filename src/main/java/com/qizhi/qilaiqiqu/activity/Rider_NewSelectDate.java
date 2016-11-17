package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *	选择约骑时间
 */
public class Rider_NewSelectDate extends Activity {

    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;
    @InjectView(R.id.list)
    ListView listView;
    @InjectView(R.id.txt_time)
    TextView txtTime;
    @InjectView(R.id.txt_commit)
    TextView txtCommit;

    private DateAdapter adapter;

    private List<DateModel> list;
    public static List<String> DateList;
    private HashMap<String, String> dateMap;

    Rider_NewSelectDate instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider__new_select_date2);
        ButterKnife.inject(this);
        init();
        setDate();
    }

    private void init() {
        instance = this;
        list = new ArrayList<DateModel>();
        DateList = new ArrayList<String>();
        dateMap = new HashMap<String,String>();
    }

    List<DayInfo> infos;
    private void setDate() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        int m;
        int y = year;

        for (int i = 0; i < 12; i++) {
            if (month + i > 12) {
                y = year + 1;
                m = month + i - 12;
            } else {
                m = month + i;
            }
            // 创建月份model
            DateModel dateModel = new DateModel();
            // 设置月份
            dateModel.setMonth(m);
            // 设置当月第一天是星期几
            dateModel.setFirstDayInWeek(DateUtils.getWeekOfDate(y + "-" + m + "-" + "01") - 1);
            // 设置当月有多少天
            dateModel.setDayTotal(DateUtils.getDaysByYearMonth(y, m));
            if (i == 0) {
                // 设置今天是第一个月的哪天
                dateModel.setStartDate(new Date());
            }


            // 创建天集合
            infos = new ArrayList<DayInfo>();
            for (int j = 0; j < dateModel.getDayTotal(); j++) {
                // 创建天model
                DayInfo dayInfo = new DayInfo();
                // 设置当天在日历应该显示的数字
                dayInfo.setDayNum(j + 1);
                // 设置当天的真实日期
                dayInfo.setData(DateUtils.strToDate((y + "-" + m + "-" + (j + 1))));

                // 当天日期的年月日
                dayInfo.setDay(y + "-" + m + "-" + (j + 1));
                // 判断是否是第一个月，是否需要将已过去的日期设为不可点
                if(i == 0){
                    if(new Date().after(DateUtils.strToDate((y+"-"+m+"-"+(j+1))))){
                        // 当天时间在每天时间之后
                        dayInfo.setState("no");
                    }else{
                        if(Rider_NewDetailActivity.datelist.contains(DateUtils.dateToString(DateUtils.strToDate(dayInfo.getDay())))){
                            dayInfo.setState("can");
                        }else{
                            dayInfo.setState("no");
                        }
                    }
                }else{
                    if(Rider_NewDetailActivity.datelist.contains(DateUtils.dateToString(DateUtils.strToDate(dayInfo.getDay())))){
                        dayInfo.setState("can");
                    }else{
                        dayInfo.setState("no");
                    }
                }


                infos.add(dayInfo);

            }

            dateModel.setDayInfo(infos);
            list.add(dateModel);
        }

        adapter = new DateAdapter(this, list, DateList,"select",instance);
        listView.setAdapter(adapter);
    }

    public void setTime(){
        txtTime.setText(DateList.get(0));
    }




    @OnClick({R.id.layout_back,R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                // 设置返回日期
                Intent noData = new Intent();
                noData.putExtra("date", "请选择约骑日期");
                setResult(0,noData);

                finish();
                break;

            case R.id.txt_commit:
                // 设置返回日期
                Intent data = new Intent();
                data.putExtra("date", txtTime.getText().toString());
                setResult(0,data);

                finish();
                break;
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 设置返回日期
            Intent data = new Intent();
            data.putExtra("date", "请选择约骑日期");
            setResult(0,data);

            finish();

            return false;
        }
        return false;
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
