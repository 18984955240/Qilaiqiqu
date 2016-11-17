package com.qizhi.qilaiqiqu.model;

import java.util.List;

/**
 * Created by dell1 on 2016/8/29.
 */
public class Rider_NewDetailModel {


    /**
     * message : ok
     * detail : {"alipayno":"18051481253","commentNum":0,"createDate":"2016-08-25 01:49:33","id":2,"imUserId":"922fb973f9d44007b1cc7e565dd13fcc","label":"打酱油,没什么好玩的,旅行粉,幽默风趣,无赖,无厘头","name":"001107","personalDesc":"从来不相信世界有到不了的山顶，如果有一定是你自己没努力!爱上了骑车，便再也放不下!五年了，走过的路越来越多，遇到过的人越来越多，每次见面的微笑，每一次离开的不舍，都是人生中最难忘的风景!","personalImage":"aaaa.jpg","phone":"18051481253","recommend":0,"riderArea":"山西省,长治市,古韩镇","riderDesc":"士大夫士大夫士大夫","riderPrice":22,"riderTime":"2016-09-06,2016-09-09,2016-09-06,2016-09-09,2016-09-20,2016-09-30,2016-09-30,2016-10-09,2016-10-12,2016-10-19,2016-10-22,2016-10-24","state":"2","userId":10002,"userImage":"USER_20160030100726585_188_188_9.png","userMemo":"简单平凡的你，走了一条不平凡的路O(∩_∩)O","userName":"骑行浪子","wxno":"18051481253"}
     * status : 0
     * state : true
     * list : [{"adress":"","category":"","categoryId":1,"collectNum":0,"create_date":"2016-08-10 10:55:09","dcyq":0,"descript":"","end_date":"2016-09-03 12:00:00","id":10,"jb_time":"","jbdz":"","jbrq":1,"line":"9.499KM","mzcf":"","organizerId":1,"orginName":"死胖子是二逼","phone":"18051481253","photo":"BANNER_20160810105411198_900_600.jpg","praiseNum":11,"praiseState":false,"price":0,"price_desc":"","price_name":"","route":"","routeLine":"","scanNum":460,"signCount":10017,"slxz":0,"start_date":"2016-08-31 12:00:00","state":0,"stop_car":0,"tip":"瑞丰高大哥发的","title":"楚大师带你上山耍","userAddress":"","userId":10001,"userImage":"BANNER_20160903162439772_250_250.jpg","userMemo":"","userName":"","wifi":0,"ydjzsj":"0","ydsl":5},{"adress":"","category":"","categoryId":5,"collectNum":0,"create_date":"2016-08-12 10:18:46","dcyq":0,"descript":"","end_date":"2017-03-04 12:00:00","id":14,"jb_time":"","jbdz":"","jbrq":1,"line":"浙江省杭州市下城区东新街道东新路280号","mzcf":"","organizerId":1,"orginName":"死胖子是二逼","phone":"18051481253","photo":"BANNER_20160812101827188_757_600.jpg,BANNER_20160812101829342_757_600.jpg,BANNER_20160812101830627_757_600.jpg","praiseNum":0,"praiseState":false,"price":0.1,"price_desc":"","price_name":"","route":"","routeLine":"","scanNum":164,"signCount":51,"slxz":0,"start_date":"2016-09-16 12:00:00","state":0,"stop_car":1,"tip":"二恶丰富的非更好更符合法规","title":"来上一课","userAddress":"","userId":10001,"userImage":"BANNER_20160903162439772_250_250.jpg","userMemo":"","userName":"","wifi":0,"ydjzsj":"0","ydsl":22}]
     * code : 0
     * comments : [{"commentDate":"2016-08-25 04:37:11","commentId":1,"commentMemo":"1313213","grade":5,"parentId":0,"riderId":1,"superId":0,"userId":10002,"userImage":"","userName":"叫我zoe好了~~"},{"commentDate":"2016-08-25 04:37:11","commentId":1,"commentMemo":"1313213","grade":5,"parentId":0,"riderId":1,"superId":0,"userId":10002,"userImage":"","userName":"解大工程师"},{"commentDate":"2016-08-25 04:37:11","commentId":1,"commentMemo":"1313213","grade":5,"parentId":0,"riderId":1,"superId":0,"userId":10002,"userImage":"","userName":"无论刮风下雨"}]
     * orderId :
     * riderNote :  用户约骑陪骑士前请选择约骑时间，申请陪骑。 陪骑士同意后产生陪骑订单，用户完成陪骑后24小时 之前需支付。 陪骑士同意后，用户请与陪骑士联系确认线下见面地点。 陪骑活动中请注意个人安全。
     */

    private String message;
    /**
     * alipayno : 18051481253
     * commentNum : 0
     * createDate : 2016-08-25 01:49:33
     * id : 2
     * imUserId : 922fb973f9d44007b1cc7e565dd13fcc
     * label : 打酱油,没什么好玩的,旅行粉,幽默风趣,无赖,无厘头
     * name : 001107
     * personalDesc : 从来不相信世界有到不了的山顶，如果有一定是你自己没努力!爱上了骑车，便再也放不下!五年了，走过的路越来越多，遇到过的人越来越多，每次见面的微笑，每一次离开的不舍，都是人生中最难忘的风景!
     * personalImage : aaaa.jpg
     * phone : 18051481253
     * recommend : 0
     * riderArea : 山西省,长治市,古韩镇
     * riderDesc : 士大夫士大夫士大夫
     * riderPrice : 22
     * riderTime : 2016-09-06,2016-09-09,2016-09-06,2016-09-09,2016-09-20,2016-09-30,2016-09-30,2016-10-09,2016-10-12,2016-10-19,2016-10-22,2016-10-24
     * state : 2
     * userId : 10002
     * userImage : USER_20160030100726585_188_188_9.png
     * userMemo : 简单平凡的你，走了一条不平凡的路O(∩_∩)O
     * userName : 骑行浪子
     * wxno : 18051481253
     */

    private DetailBean detail;
    private int status;
    private boolean state;
    private int code;
    private String orderId;
    private String riderNote;
    /**
     * adress :
     * category :
     * categoryId : 1
     * collectNum : 0
     * create_date : 2016-08-10 10:55:09
     * dcyq : 0
     * descript :
     * end_date : 2016-09-03 12:00:00
     * id : 10
     * jb_time :
     * jbdz :
     * jbrq : 1
     * line : 9.499KM
     * mzcf :
     * organizerId : 1
     * orginName : 死胖子是二逼
     * phone : 18051481253
     * photo : BANNER_20160810105411198_900_600.jpg
     * praiseNum : 11
     * praiseState : false
     * price : 0
     * price_desc :
     * price_name :
     * route :
     * routeLine :
     * scanNum : 460
     * signCount : 10017
     * slxz : 0
     * start_date : 2016-08-31 12:00:00
     * state : 0
     * stop_car : 0
     * tip : 瑞丰高大哥发的
     * title : 楚大师带你上山耍
     * userAddress :
     * userId : 10001
     * userImage : BANNER_20160903162439772_250_250.jpg
     * userMemo :
     * userName :
     * wifi : 0
     * ydjzsj : 0
     * ydsl : 5
     */

    private List<ListBean> list;
    /**
     * commentDate : 2016-08-25 04:37:11
     * commentId : 1
     * commentMemo : 1313213
     * grade : 5
     * parentId : 0
     * riderId : 1
     * superId : 0
     * userId : 10002
     * userImage :
     * userName : 叫我zoe好了~~
     */

    private List<CommentsBean> comments;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRiderNote() {
        return riderNote;
    }

    public void setRiderNote(String riderNote) {
        this.riderNote = riderNote;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class DetailBean {
        private String alipayno;
        private int commentNum;
        private String createDate;
        private int id;
        private String imUserId;
        private String label;
        private String name;
        private String personalDesc;
        private String personalImage;
        private String phone;
        private int recommend;
        private String riderArea;
        private String riderDesc;
        private float riderPrice;
        private String riderTime;
        private String state;
        private int userId;
        private String userImage;
        private String userMemo;
        private String userName;
        private String wxno;

        public String getAlipayno() {
            return alipayno;
        }

        public void setAlipayno(String alipayno) {
            this.alipayno = alipayno;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
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

        public String getImUserId() {
            return imUserId;
        }

        public void setImUserId(String imUserId) {
            this.imUserId = imUserId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPersonalDesc() {
            return personalDesc;
        }

        public void setPersonalDesc(String personalDesc) {
            this.personalDesc = personalDesc;
        }

        public String getPersonalImage() {
            return personalImage;
        }

        public void setPersonalImage(String personalImage) {
            this.personalImage = personalImage;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getRiderArea() {
            return riderArea;
        }

        public void setRiderArea(String riderArea) {
            this.riderArea = riderArea;
        }

        public String getRiderDesc() {
            return riderDesc;
        }

        public void setRiderDesc(String riderDesc) {
            this.riderDesc = riderDesc;
        }

        public float getRiderPrice() {
            return riderPrice;
        }

        public void setRiderPrice(float riderPrice) {
            this.riderPrice = riderPrice;
        }

        public String getRiderTime() {
            return riderTime;
        }

        public void setRiderTime(String riderTime) {
            this.riderTime = riderTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getWxno() {
            return wxno;
        }

        public void setWxno(String wxno) {
            this.wxno = wxno;
        }
    }

    public static class ListBean {
        private String adress;
        private String category;
        private int categoryId;
        private int collectNum;
        private String create_date;
        private int dcyq;
        private String descript;
        private String end_date;
        private int id;
        private String jb_time;
        private String jbdz;
        private int jbrq;
        private String line;
        private String mzcf;
        private int organizerId;
        private String orginName;
        private String phone;
        private String photo;
        private int praiseNum;
        private boolean praiseState;
        private float price;
        private String price_desc;
        private String price_name;
        private String route;
        private String routeLine;
        private int scanNum;
        private int signCount;
        private int slxz;
        private String start_date;
        private int state;
        private int stop_car;
        private String tip;
        private String title;
        private String userAddress;
        private int userId;
        private String userImage;
        private String userMemo;
        private String userName;
        private int wifi;
        private String ydjzsj;
        private int ydsl;

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(int collectNum) {
            this.collectNum = collectNum;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public int getDcyq() {
            return dcyq;
        }

        public void setDcyq(int dcyq) {
            this.dcyq = dcyq;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJb_time() {
            return jb_time;
        }

        public void setJb_time(String jb_time) {
            this.jb_time = jb_time;
        }

        public String getJbdz() {
            return jbdz;
        }

        public void setJbdz(String jbdz) {
            this.jbdz = jbdz;
        }

        public int getJbrq() {
            return jbrq;
        }

        public void setJbrq(int jbrq) {
            this.jbrq = jbrq;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getMzcf() {
            return mzcf;
        }

        public void setMzcf(String mzcf) {
            this.mzcf = mzcf;
        }

        public int getOrganizerId() {
            return organizerId;
        }

        public void setOrganizerId(int organizerId) {
            this.organizerId = organizerId;
        }

        public String getOrginName() {
            return orginName;
        }

        public void setOrginName(String orginName) {
            this.orginName = orginName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public boolean isPraiseState() {
            return praiseState;
        }

        public void setPraiseState(boolean praiseState) {
            this.praiseState = praiseState;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }

        public String getPrice_name() {
            return price_name;
        }

        public void setPrice_name(String price_name) {
            this.price_name = price_name;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getRouteLine() {
            return routeLine;
        }

        public void setRouteLine(String routeLine) {
            this.routeLine = routeLine;
        }

        public int getScanNum() {
            return scanNum;
        }

        public void setScanNum(int scanNum) {
            this.scanNum = scanNum;
        }

        public int getSignCount() {
            return signCount;
        }

        public void setSignCount(int signCount) {
            this.signCount = signCount;
        }

        public int getSlxz() {
            return slxz;
        }

        public void setSlxz(int slxz) {
            this.slxz = slxz;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getStop_car() {
            return stop_car;
        }

        public void setStop_car(int stop_car) {
            this.stop_car = stop_car;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
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

        public int getWifi() {
            return wifi;
        }

        public void setWifi(int wifi) {
            this.wifi = wifi;
        }

        public String getYdjzsj() {
            return ydjzsj;
        }

        public void setYdjzsj(String ydjzsj) {
            this.ydjzsj = ydjzsj;
        }

        public int getYdsl() {
            return ydsl;
        }

        public void setYdsl(int ydsl) {
            this.ydsl = ydsl;
        }
    }

    public static class CommentsBean {
        private String commentDate;
        private int commentId;
        private String commentMemo;
        private int grade;
        private int parentId;
        private int riderId;
        private int superId;
        private int userId;
        private String userImage;
        private String userName;

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

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getRiderId() {
            return riderId;
        }

        public void setRiderId(int riderId) {
            this.riderId = riderId;
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
    }
}
