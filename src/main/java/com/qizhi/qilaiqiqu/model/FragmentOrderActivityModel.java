package com.qizhi.qilaiqiqu.model;

/**
 * Created by dell1 on 2016/9/1.
 */
public class FragmentOrderActivityModel {


    /**
     * activityDesc :
     * priceDesc :
     * activityId : 12
     * activityTitle : 来个一个看看
     * createDate :
     * id : HD201608301652587019
     * organizaId : 0
     * photo : BANNER_20160810141648048_500_333.jpg,BANNER_20160810141652118_500_330.jpg,BANNER_20160810141652199_500_749.jpg
     * price : 15
     * state : 1
     * total : 4
     * totalPrice : 60
     * userId : 10005
     * userImage :
     * userName : 昵称只能八个字？
     */

    private String activityDesc;
    private String priceDesc;
    private int activityId;
    private String activityTitle;
    private String createDate;
    private String id;
    private int organizaId;
    private String photo;
    private Double price;
    private int state;
    private int total;
    private Double totalPrice;
    private int userId;
    private String userImage;
    private String userName;

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrganizaId() {
        return organizaId;
    }

    public void setOrganizaId(int organizaId) {
        this.organizaId = organizaId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
