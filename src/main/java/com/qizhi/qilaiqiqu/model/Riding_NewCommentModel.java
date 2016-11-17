package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/8/11.
 */
public class Riding_NewCommentModel {


    /**
     * articleId : 26
     * attendRiderComments : [{"articleId":26,"attendRiderComments":[],"commentDate":"2016-07-28 02:02:21","commentId":91,"commentMemo":"哥哥哥哥","followNum":0,"parentId":90,"parentName":"admin","superId":90,"userId":10002,"userImage":"","userName":"死胖子"}]
     * commentDate : 2016-07-28 01:59:09
     * commentId : 90
     * commentMemo : 哈哈哈哈
     * followNum : 0
     * parentId : 0
     * parentName :
     * superId : 0
     * userId : 10001
     * userImage : USER_201603161021137330_177_177_33.jpg
     * userName : admin
     */

    private int articleId;
    private String commentDate;
    private int commentId;
    private String commentMemo;
    private int followNum;
    private int parentId;
    private String parentName;
    private int superId;
    private int userId;
    private String userImage;
    private String userName;
    /**
     * articleId : 26
     * attendRiderComments : []
     * commentDate : 2016-07-28 02:02:21
     * commentId : 91
     * commentMemo : 哥哥哥哥
     * followNum : 0
     * parentId : 90
     * parentName : admin
     * superId : 90
     * userId : 10002
     * userImage :
     * userName : 死胖子
     */

    private List<AttendRiderCommentsBean> attendRiderComments;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentMemo() {
        return commentMemo;
    }

    public void setCommentMemo(String commentMemo) {
        this.commentMemo = commentMemo;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    public List<AttendRiderCommentsBean> getAttendRiderComments() {
        return attendRiderComments;
    }

    public void setAttendRiderComments(List<AttendRiderCommentsBean> attendRiderComments) {
        this.attendRiderComments = attendRiderComments;
    }

    public static class AttendRiderCommentsBean {
        private int articleId;
        private String commentDate;
        private int commentId;
        private String commentMemo;
        private int followNum;
        private int parentId;
        private String parentName;
        private int superId;
        private int userId;
        private String userImage;
        private String userName;
        private List<AttendRiderCommentsBean> attendRiderComments;

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public String getCommentDate() {
            return commentDate;
        }

        public void setCommentDate(String commentDate) {
            this.commentDate = commentDate;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getCommentMemo() {
            return commentMemo;
        }

        public void setCommentMemo(String commentMemo) {
            this.commentMemo = commentMemo;
        }

        public int getFollowNum() {
            return followNum;
        }

        public void setFollowNum(int followNum) {
            this.followNum = followNum;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
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

        public List<AttendRiderCommentsBean> getAttendRiderComments() {
            return attendRiderComments;
        }

        public void setAttendRiderComments(List<AttendRiderCommentsBean> attendRiderComments) {
            this.attendRiderComments = attendRiderComments;
        }
    }
}
