package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/5/25.
 */
public class CharacterCommentModel {


    /**
     * characterId : 5
     * commentDate : 2016-09-13 05:29:03
     * commentId : 28
     * commentList : []
     * commentMemo : 将就
     * parentId : 0
     * state : 1
     * superId : 0
     * userId : 10004
     * userImage : IMAGE_IOS_UPLOAD_1472611551566734_w:375_h:375.png
     * userName : 这就是我的车
     */

    private int characterId;
    private String commentDate;
    private int commentId;
    private String commentMemo;
    private int parentId;
    private int state;
    private int superId;
    private int userId;
    private String userImage;
    private String userName;
    private List<String> commentList;

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public List<String> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<String> commentList) {
        this.commentList = commentList;
    }
}
