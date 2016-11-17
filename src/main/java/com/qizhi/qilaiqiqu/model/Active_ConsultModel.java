package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/8/18.
 */
public class Active_ConsultModel {


    /**
     * activityId : 18
     * comment : 死胖子
     * commentDate : 2016-08-17 01:52:28
     * id : 5
     * list : []
     * parentId : 0
     * reward : 0
     * superId : 0
     * userId : 10002
     * userImage : USER_20160030100726585_188_188_9.png
     * userName : 死胖子
     */

    private int activityId;
    private String comment;
    private String commentDate;
    private int id;
    private int parentId;
    private int reward;
    private int superId;
    private int userId;
    private String userImage;
    private String userName;
    private List<LisetBean> list;

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

    public List<LisetBean> getList() {
        return list;
    }

    public String getListComment() {
        return list.get(0).getComment();
    }

    public String getListCommenter() {
        return list.get(0).getUserName();
    }

    public void setList(List<LisetBean> list) {
        this.list = list;
    }

    class LisetBean{
        private int activityId;
        private String comment;
        private String commentDate;
        private int id;
        private int parentId;
        private int reward;
        private int superId;
        private int userId;
        private String userImage;
        private String userName;
        private List<?> list;

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

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
    }

}
