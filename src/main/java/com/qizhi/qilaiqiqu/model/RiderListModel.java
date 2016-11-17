package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/5/10.
 */
public class RiderListModel {


    /**
     * dataList : ["ATTENDRIDER_201603281502034750_375_378_16.png","ACTIVITY_201603301448344520_177_177_36.jpg","ACTIVITY_201603301342317170_177_177_23.jpg","ACTIVITY_201603301456527770_221_221_54.jpg","ATTENDRIDER_201603301801596730_750_750_85.png","ATTENDRIDER_201604081745280870_414_413_59.png","ATTENDRIDER_201604091108272140_640_480_66.jpg"]
     * result : true
     */

    private boolean result;
    public List<String> dataList;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }
}
