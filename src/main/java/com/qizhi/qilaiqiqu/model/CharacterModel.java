package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/5/24.
 */
public class CharacterModel {


    /**
     * characterDesc :
     * characterId : 2
     * comment_date :
     * comment_num : 2
     * comments : []
     * createDate : 2016-06-22 09:14:29
     * defaultImage : BANNER_20160622205318121_863_480_256.jpg
     * introduction : 这个男人 煮得了咖啡 骑得了川藏 上得了赛场
     * keyword :
     * scanNum : 84
     * showDate : 2016-06-22 09:14:08
     * state : 3
     * title : 一个咖啡师的自白
     * url : http://weride.com.cn/weride/mobile/character/queryCharacterDetail.html?characterId=
     * userImage : USER_201603161021137330_177_177_33.jpg
     * userName : 行天下
     * userid :
     */

    private String characterDesc;
    private int characterId;
    private String comment_date;
    private int comment_num;
    private String createDate;
    private String defaultImage;
    private String introduction;
    private String keyword;
    private int scanNum;
    private String showDate;
    private String state;
    private String title;
    private String url;
    private String userImage;
    private String userName;
    private String userid;
    private List<?> comments;

    public String getCharacterDesc() {
        return characterDesc;
    }

    public void setCharacterDesc(String characterDesc) {
        this.characterDesc = characterDesc;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getScanNum() {
        return scanNum;
    }

    public void setScanNum(int scanNum) {
        this.scanNum = scanNum;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<?> getComments() {
        return comments;
    }

    public void setComments(List<?> comments) {
        this.comments = comments;
    }
}
