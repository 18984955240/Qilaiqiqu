package com.qizhi.qilaiqiqu.model;

/**
 * Created by dell1 on 2016/6/13.
 */
public class OrderModel {


    /**
     * state : 1
     * type : 1
     * userName : 就是不告诉你
     * riderId : null
     * userId : 10103
     * create_date : 2016-06-08 13:57
     * productid : 41
     * atterStart_date : 2016-06-11 09:00
     * product_name : 陪骑费用
     * orderId : PQ201606081357719813
     * total_price : 450
     * riderName : 423126
     * total_time : 5
     * price : 90
     * order_source : null
     * end_date : null
     * lastpay_date : 2016-06-12 15:00
     * pay_date : null
     * atterEnd_date : 2016-06-11 15:00
     * argeeTimes : 2016-06-11 09:00-15:00
     */

    private String state;
    private int type;
    private String userName;
    private int riderId;
    private int userId;
    private String create_date;
    private int productid;
    private String atterStart_date;
    private String product_name;
    private String orderId;
    private float total_price;
    private String riderName;
    private int total_time;
    private float price;
    private String order_source;
    private String end_date;
    private String lastpay_date;
    private String pay_date;
    private String atterEnd_date;
    private String argeeTimes;
    private String orderMemo;
    private String message;
    private String phone;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getAtterStart_date() {
        return atterStart_date;
    }

    public void setAtterStart_date(String atterStart_date) {
        this.atterStart_date = atterStart_date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Object getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getLastpay_date() {
        return lastpay_date;
    }

    public void setLastpay_date(String lastpay_date) {
        this.lastpay_date = lastpay_date;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public String getAtterEnd_date() {
        return atterEnd_date;
    }

    public void setAtterEnd_date(String atterEnd_date) {
        this.atterEnd_date = atterEnd_date;
    }

    public String getArgeeTimes() {
        return argeeTimes;
    }

    public void setArgeeTimes(String argeeTimes) {
        this.argeeTimes = argeeTimes;
    }
    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }
}
