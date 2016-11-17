package com.qizhi.qilaiqiqu.model;

/**
 * Created by dell1 on 2016/9/9.
 */
public class Activity_NewCommentModel {


    /**
     * activityId : 14
     * comment : 和第三代
     * commentDate : 2016-08-12 01:50:33
     * followNum : 0
     * id : 1
     * parentId : 0
     * reward : 5
     * superId : 0
     * userId : 10001
     * userImage : BANNER_20160903162439772_250_250.jpg
     * userName : admin
     */

    private int activityId;
    private String comment;
    private String commentDate;
    private int followNum;
    private int id;
    private int parentId;
    private int reward;
    private int superId;
    private int userId;
    private String userImage;
    private String userName;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
