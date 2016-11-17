package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/6/29.
 */
public class RidingCommentsModel {


    /**
     * message : ok
     * articleImage : BANNER_20160728100205307_396_238.jpg
     * articelComNum : 2
     * articleSceen : 53
     * userImage : BANNER_20160903162439772_250_250.jpg
     * userName : admin
     * code : 0
     * articleprise : 1
     * comment : 哥哥哥哥
     * comments : [{"articleId":26,"attendRiderComments":[],"commentDate":"2016-07-28 02:02:21","commentId":91,"commentMemo":"哥哥哥哥","followNum":0,"parentId":90,"parentName":"","superId":90,"userId":10002,"userImage":"USER_20160030100726585_188_188_9.png","userName":"死瘸子"}]
     */

    private String message;
    private String articleImage;
    private int articelComNum;
    private int articleSceen;
    private String userImage;
    private String userName;
    private int code;
    private int articleprise;
    private String comment;
    /**
     * articleId : 26
     * attendRiderComments : []
     * commentDate : 2016-07-28 02:02:21
     * commentId : 91
     * commentMemo : 哥哥哥哥
     * followNum : 0
     * parentId : 90
     * parentName :
     * superId : 90
     * userId : 10002
     * userImage : USER_20160030100726585_188_188_9.png
     * userName : 死瘸子
     */

    private List<CommentsBean> comments;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public int getArticelComNum() {
        return articelComNum;
    }

    public void setArticelComNum(int articelComNum) {
        this.articelComNum = articelComNum;
    }

    public int getArticleSceen() {
        return articleSceen;
    }

    public void setArticleSceen(int articleSceen) {
        this.articleSceen = articleSceen;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getArticleprise() {
        return articleprise;
    }

    public void setArticleprise(int articleprise) {
        this.articleprise = articleprise;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
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
            private List<?> attendRiderComments;

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

            public List<?> getAttendRiderComments() {
                return attendRiderComments;
            }

            public void setAttendRiderComments(List<?> attendRiderComments) {
                this.attendRiderComments = attendRiderComments;
            }
        }
    }



}
