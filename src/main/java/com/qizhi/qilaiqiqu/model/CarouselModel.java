package com.qizhi.qilaiqiqu.model;

public class CarouselModel {


	/**
	 * banner_order : 1
	 * banner_type : APP
	 * create_date : 2016-09-13 01:37:47
	 * id : 1
	 * imageName : lunbotu1.jpg
	 * memo : 轮播图测试
	 * open_type : app
	 * type : RIDER
	 * value : 1
	 */

	private int banner_order;
	private String banner_type;
	private String create_date;
	private int id;
	private String imageName;
	private String memo;
	private String open_type;
	private String type;
	private String value;

	public int getBanner_order() {
		return banner_order;
	}

	public void setBanner_order(int banner_order) {
		this.banner_order = banner_order;
	}

	public String getBanner_type() {
		return banner_type;
	}

	public void setBanner_type(String banner_type) {
		this.banner_type = banner_type;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOpen_type() {
		return open_type;
	}

	public void setOpen_type(String open_type) {
		this.open_type = open_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
