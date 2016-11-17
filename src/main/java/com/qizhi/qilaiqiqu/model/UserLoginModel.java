package com.qizhi.qilaiqiqu.model;

import java.io.Serializable;

public class UserLoginModel implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * address :
	 * concern : 0
	 * createDate : {"date":2,"day":2,"hours":13,"minutes":11,"month":7,"seconds":54,"time":1470114714000,"timezoneOffset":-480,"year":116}
	 * email :
	 * idCard :
	 * imId : a5e9b5b0-586f-11e6-9c2e-476a78d54c70
	 * imPassword : f3b932e9d5bc4b8b916a85776c66160e
	 * imUserName : cd91cf786afb4eb99bacecad7d4000fa
	 * integral : 0
	 * loginName :
	 * mobilePhone : 17006423126
	 * password : JfnnlDI7RTiF9RgfG2JNCw==
	 * realName :
	 * sex : Q
	 * state : NORMAL
	 * uniqueKey : oIbeRV5c2olHW+AskqWeSA==
	 * userFans : 0
	 * userId : 10004
	 * userImage : USER_20160030100726585_188_188_9.png
	 * userMemo :
	 * userName : 我叫白宇
	 * userType : MEMBER
	 */


	private String address;
	private int concern;
	/**
	 * date : 2
	 * day : 2
	 * hours : 13
	 * minutes : 11
	 * month : 7
	 * seconds : 54
	 * time : 1470114714000
	 * timezoneOffset : -480
	 * year : 116
	 */

	private String createDate;
	private String email;
	private String idCard;
	private String imId;
	private String imPassword;
	private String imUserName;
	private int integral;
	private String loginName;
	private String mobilePhone;
	private String password;
	private String realName;
	private String sex;
	private String state;
	private String uniqueKey;
	private int userFans;
	private int userId = -1;
	private int riderId = -1;
	private String userImage;
	private String userMemo;
	private String userName;
	private String userType;


	@Override
	public String toString() {
		return "UserClass [imId=" + imId + ", mobilePhone=" + mobilePhone
				+ ", imPassword=" + imPassword + ", userId=" + userId
				+ ", userImage=" + userImage + ", userName=" + userName
				+ ", imUserName=" + imUserName + ", uniqueKey=" + uniqueKey
				+ "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getConcern() {
		return concern;
	}

	public void setConcern(int concern) {
		this.concern = concern;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getImId() {
		return imId;
	}

	public void setImId(String imId) {
		this.imId = imId;
	}

	public String getImPassword() {
		return imPassword;
	}

	public void setImPassword(String imPassword) {
		this.imPassword = imPassword;
	}

	public String getImUserName() {
		return imUserName;
	}

	public void setImUserName(String imUserName) {
		this.imUserName = imUserName;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public int getUserFans() {
		return userFans;
	}

	public void setUserFans(int userFans) {
		this.userFans = userFans;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRiderId() {
		return riderId;
	}

	public void setRiderId(int riderId) {
		this.riderId = riderId;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserMemo() {
		return userMemo;
	}

	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static class CreateDateBean {
		private int date;
		private int day;
		private int hours;
		private int minutes;
		private int month;
		private int seconds;
		private long time;
		private int timezoneOffset;
		private int year;

		public int getDate() {
			return date;
		}

		public void setDate(int date) {
			this.date = date;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getHours() {
			return hours;
		}

		public void setHours(int hours) {
			this.hours = hours;
		}

		public int getMinutes() {
			return minutes;
		}

		public void setMinutes(int minutes) {
			this.minutes = minutes;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getSeconds() {
			return seconds;
		}

		public void setSeconds(int seconds) {
			this.seconds = seconds;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public int getTimezoneOffset() {
			return timezoneOffset;
		}

		public void setTimezoneOffset(int timezoneOffset) {
			this.timezoneOffset = timezoneOffset;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}
	}
}
