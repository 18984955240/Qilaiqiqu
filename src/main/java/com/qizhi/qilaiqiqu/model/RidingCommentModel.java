package com.qizhi.qilaiqiqu.model;

import java.util.List;

public class RidingCommentModel {
	private String userName;
	private Integer userId;
	private String commentMemo;
	private Integer superId;
	private Integer parentId;
	private Integer commentId;
	private List<RidingCommentModel> subCommentList;
	private String parentName;
	private String userImage;
	private String commentDate;
	private Integer riderId;
	private Integer integral;

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getRiderId() {
		return riderId;
	}

	public void setRiderId(Integer riderId) {
		this.riderId = riderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCommentMemo() {
		return commentMemo;
	}

	public void setCommentMemo(String commentMemo) {
		this.commentMemo = commentMemo;
	}

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	public List<RidingCommentModel> getSubCommentList() {
		return subCommentList;
	}

	public void setSubCommentList(List<RidingCommentModel> subCommentList) {
		this.subCommentList = subCommentList;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
