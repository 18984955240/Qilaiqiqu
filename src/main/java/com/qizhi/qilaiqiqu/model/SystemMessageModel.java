package com.qizhi.qilaiqiqu.model;

public class SystemMessageModel {


	/**
	 * content : admin赞了你的骑游记！
	 * createDate :
	 * id : 3
	 * operatorId : 12
	 * relationId : 14
	 * state : 1
	 * type : 1
	 * userId : 10004
	 */

	private String content;
	private String createDate;
	private int id;
	private int operatorId;
	private String relationId;
	private String state;
	private String type;
	private int userId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
