package com.qizhi.qilaiqiqu.model;

import java.io.Serializable;

/**
 * Created by dell1 on 2016/7/22.
 */
public class RidingContentModel implements Serializable {

    private static final long serialVersionUID = 1L;
    // 游记标题
    private String ridingTitle = "";
    // 图注
    private String legend = "";
    // 地理位置
    private String position = "";
    // 地理纬度
    private Double longitude = 0.0;
    // 地理经度
    private Double latitude = 0.0;
    // 图片
    private String picture = "";
    // 时间
    private String time = "";
    // 语音
    private String voice = "";
    // 录音状态
    private String recorderState = "none";
    // 播放状态
    private String palyState = "none";
    // 是否正在播放
    private boolean isPlaying = false;
    // 设备品牌
    private String deviceName = "";
    // 设备型号
    private String deviceModel = "";
    // 游记信息状态
    private String state = "";
    // 图片高度
    private String width = "";
    // 图片宽度
    private String height = "";

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRidingTitle() {
        return ridingTitle;
    }

    public void setRidingTitle(String ridingTitle) {
        this.ridingTitle = ridingTitle;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecorderState() {
        return recorderState;
    }

    public void setRecorderState(String recorderState) {
        this.recorderState = recorderState;
    }

    public String getPalyState() {
        return palyState;
    }

    public void setPalyState(String palyState) {
        this.palyState = palyState;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
