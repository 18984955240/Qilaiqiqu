package com.qizhi.qilaiqiqu.model;

import java.util.Date;

/**
 * Created by dell1 on 2016/9/9.
 */
public class Rider_NewRefundModel {

    private RefundBean refund;
    private OrderBean order;

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RefundBean getRefund() {
        return refund;
    }

    public void setRefund(RefundBean refund) {
        this.refund = refund;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class RefundBean{
        /**
         * 退款订单
         */
        private String refundId;
        /**
         * 订单信息ID
         */
        private String orderId;
        /**
         * 0，陪骑订单;1,活动订单
         */
        private Integer type;
        /**
         * 支付用户ID
         */
        private Integer userId;
        /**
         * 退款金额
         */
        private float refPrice;
        /**
         * 实际退款金额
         */
        private float factPrice;
        /**
         * 申请日期
         */
        private Date applyDate;
        /**
         * 退款时间
         */
        private Date refDate;
        /**
         * 操作人员
         */
        private Integer operaUser;
        /**
         * 退款手机号码
         */
        private String phone;
        /**
         * 退款备注
         */
        private String message;
        /**
         * 状态（1：申请中，2：同意，3：拒绝，4：已退款）
         */
        private Integer state;

        public String getRefundId() {
            return refundId;
        }

        public void setRefundId(String refundId) {
            this.refundId = refundId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public float getRefPrice() {
            return refPrice;
        }

        public void setRefPrice(float refPrice) {
            this.refPrice = refPrice;
        }

        public float getFactPrice() {
            return factPrice;
        }

        public void setFactPrice(float factPrice) {
            this.factPrice = factPrice;
        }

        public Date getApplyDate() {
            return applyDate;
        }

        public void setApplyDate(Date applyDate) {
            this.applyDate = applyDate;
        }

        public Date getRefDate() {
            return refDate;
        }

        public void setRefDate(Date refDate) {
            this.refDate = refDate;
        }

        public Integer getOperaUser() {
            return operaUser;
        }

        public void setOperaUser(Integer operaUser) {
            this.operaUser = operaUser;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }
    }


    public static class OrderBean{
        /**
         * 订单ID
         */
        private String orderId;
        /**
         * 用户ID
         */
        private Integer userId;
        /**
         * 用户名
         */
        private String userName;
        /**
         * 陪骑日期
         */
        private String date;
        /**
         * 用户消息
         */
        private String message;
        /**
         * 商品ID(陪骑申请的id)
         */
        private Integer productid;
        /**
         * 商品名称
         */
        private String product_name;
        /**
         * 陪骑总时间
         */
        private Integer total_time;
        /**
         * 价格
         */
        private float price;
        /**
         * 总金额
         */
        private float total_price;
        /**
         * 支付状态
         */
        private String state;
        /**
         * 下单时间
         */
        private Date create_date;

        /**
         * 用户支付时间
         */
        private Date pay_date;
        /**
         * 结单时间
         */
        private Date end_date;
        /**
         * 规定最后支付时间(用来通知用户)
         */
        private Date lastpay_date;

        private Integer riderId;
        /**
         * 陪骑士名称
         */
        private String riderName;
        /**
         * 陪骑时间
         */
        private String riderTime;
        /**
         * 电话号码
         */
        private String phone;


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public Integer getTotal_time() {
            return total_time;
        }

        public void setTotal_time(Integer total_time) {
            this.total_time = total_time;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getTotal_price() {
            return total_price;
        }

        public void setTotal_price(float total_price) {
            this.total_price = total_price;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Date getCreate_date() {
            return create_date;
        }

        public void setCreate_date(Date create_date) {
            this.create_date = create_date;
        }

        public Date getPay_date() {
            return pay_date;
        }

        public void setPay_date(Date pay_date) {
            this.pay_date = pay_date;
        }

        public Date getEnd_date() {
            return end_date;
        }

        public void setEnd_date(Date end_date) {
            this.end_date = end_date;
        }

        public Date getLastpay_date() {
            return lastpay_date;
        }

        public void setLastpay_date(Date lastpay_date) {
            this.lastpay_date = lastpay_date;
        }

        public Integer getRiderId() {
            return riderId;
        }

        public void setRiderId(Integer riderId) {
            this.riderId = riderId;
        }

        public String getRiderName() {
            return riderName;
        }

        public void setRiderName(String riderName) {
            this.riderName = riderName;
        }

        public String getRiderTime() {
            return riderTime;
        }

        public void setRiderTime(String riderTime) {
            this.riderTime = riderTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
