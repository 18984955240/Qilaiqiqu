package com.qizhi.qilaiqiqu.model;

import java.util.Date;
import java.util.List;

/**
 * Created by dell1 on 2016/8/24.
 */
public class DateModel {

    // 当前月份
    private int month;
    // 当月天数
    private int dayTotal;
    // 当天号数
    private int day;
    // 每月第一天星期几
    private int firstDayInWeek;
    // 每天信息
    private List<DayInfo> dayInfo;
    // 当天日期
    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(int dayTotal) {
        this.dayTotal = dayTotal;
    }

    public List<DayInfo> getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(List<DayInfo> dayInfo) {
        this.dayInfo = dayInfo;
    }

    public int getFirstDayInWeek() {
        return firstDayInWeek;
    }

    public void setFirstDayInWeek(int firstDayInWeek) {
        this.firstDayInWeek = firstDayInWeek;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
