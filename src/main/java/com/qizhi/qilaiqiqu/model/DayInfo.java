package com.qizhi.qilaiqiqu.model;

import java.util.Date;

/**
 * Created by dell1 on 2016/8/25.
 */
public class DayInfo{

    // 这一天的状态 can:可点 no:不可点 yet:已点
    private String state;
    // 实际日期
    private Date dayData;
    // 在日历里的数字
    private int dayNum;
    // 当天日期的年月日
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getData() {
        return dayData;
    }

    public void setData(Date dayData) {
        this.dayData = dayData;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }
}