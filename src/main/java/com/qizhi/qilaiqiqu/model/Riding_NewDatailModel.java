package com.qizhi.qilaiqiqu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell1 on 2016/8/3.
 */
public class Riding_NewDatailModel implements Serializable {


    /**
     * message : ok
     * rewardState : false
     * details : [{"list":[{"attendRiderId":"60","context":"","createDate":"2016-09-23 03:02:30","id":125,"imageAddress":"","imageDate":"2016-04-18 12:00:00","imageDesc":"","imageId":"698","imageInglat":"","imageName":"IMAGE_IOS_UPLOAD_1474614054565910_w:312_h:554.png","imageTime":"2016-04-18","imageType":"","tag":"","type":"","voicePath":""}],"time":"2016-04-18"},{"list":[{"attendRiderId":"60","context":"","createDate":"2016-09-23 03:02:30","id":126,"imageAddress":"","imageDate":"2015-09-29 12:00:00","imageDesc":"","imageId":"699","imageInglat":"","imageName":"IMAGE_IOS_UPLOAD_1474614081634266_w:480_h:358.png","imageTime":"2015-09-29","imageType":"","tag":"","type":"","voicePath":""}],"time":"2015-09-29"},{"list":[{"attendRiderId":"60","context":"","createDate":"2016-09-23 03:02:30","id":127,"imageAddress":"浙江省杭州市余杭区余杭街道西城时代家园小区东田·小城之春","imageDate":"2015-12-12 12:00:00","imageDesc":"","imageId":"700","imageInglat":"","imageName":"IMAGE_IOS_UPLOAD_1474614081642173_w:360_h:480.png","imageTime":"2015-12-12","imageType":"","tag":"","type":"","voicePath":""}],"time":"2015-12-12"},{"list":[{"attendRiderId":"60","context":"","createDate":"2016-09-23 03:02:30","id":128,"imageAddress":"安徽省阜阳市太和县税镇镇083乡道","imageDate":"2016-02-12 12:00:00","imageDesc":"","imageId":"701","imageInglat":"","imageName":"IMAGE_IOS_UPLOAD_1474614111282101_w:480_h:360.png","imageTime":"2016-02-12","imageType":"","tag":"","type":"","voicePath":""}],"time":"2016-02-12"},{"list":[{"attendRiderId":"60","context":"","createDate":"2016-09-23 03:02:30","id":129,"imageAddress":"浙江省杭州市余杭区仓前街道余家圩","imageDate":"2016-08-08 12:00:00","imageDesc":"","imageId":"702","imageInglat":"","imageName":"IMAGE_IOS_UPLOAD_1474614140046801_w:340_h:256.png","imageTime":"2016-08-08","imageType":"","tag":"","type":"","voicePath":""}],"time":"2016-08-08"}]
     * article : {"commentNum":2,"createDate":"2016-09-23 03:02:30","defaultImage":"IMAGE_IOS_UPLOAD_1474613995.798722.jpg","id":60,"labels":"打酱油,有意思,旅行粉","praiseNum":3,"praiseState":true,"scanNum":106,"state":"publish","title":"死胖子叫我发的","updateDate":"","userId":10004,"userImage":"IMAGE_IOS_UPLOAD_1472611551566734_w:375_h:375.png","userName":"机器人小Q","virtualPraise":0,"virtualScan":0}
     * grade : 0
     * praiseState : true
     * code : 0
     * activities : [{"adress":"","category":"","categoryId":6,"collectNum":0,"create_date":"2016-08-11 10:10:54","dcyq":0,"descript":"","end_date":"2016-08-24 12:00:00","id":13,"jb_time":"10:25,10:35","jbdz":"","jbrq":3,"line":"浙江省杭州市下城区朝晖街道河西南38号院施家花园(朝晖路)","mzcf":"2,3,5","organizerId":1,"orginName":"","phone":"18051481253","photo":"BANNER_20160811101053753_127_92.jpg","praiseNum":336,"praiseState":false,"price":0.01,"price_desc":"活动费用","price_name":"","route":"浙江省杭州市下城区朝晖街道河西南38号院施家花园(朝晖路)","routeLine":"120.172974 30.276934","scanNum":103,"signCount":0,"slxz":0,"start_date":"2016-08-16 12:00:00","state":0,"stop_car":0,"tip":"这是一次分享会","title":"一次分享会","userAddress":"","userId":10001,"userImage":"","userMemo":"","userName":"","wifi":0,"ydjzsj":"0","ydsl":22},{"adress":"","category":"","categoryId":1,"collectNum":0,"create_date":"2016-08-10 10:55:09","dcyq":0,"descript":"","end_date":"2016-09-03 12:00:00","id":10,"jb_time":"11:10,11:20","jbdz":"","jbrq":1,"line":"9.499KM","mzcf":"","organizerId":1,"orginName":"","phone":"18051481253","photo":"BANNER_20160810105411198_900_600.jpg","praiseNum":13,"praiseState":false,"price":0.01,"price_desc":"活动费用","price_name":"","route":"浙江省杭州市江干区笕桥镇相埠路黄家村百姓农贸市场,浙江省杭州市上城区湖滨街道长生路小区东坡路社区","routeLine":"120.235115 30.318731,120.166451 30.257364","scanNum":703,"signCount":0,"slxz":0,"start_date":"2016-08-31 12:00:00","state":0,"stop_car":0,"tip":"瑞丰高大哥发的","title":"楚大师带你上山耍","userAddress":"","userId":10001,"userImage":"","userMemo":"","userName":"","wifi":0,"ydjzsj":"0","ydsl":5}]
     * collectState : false
     * comments : [{"articleId":60,"attendRiderComments":[],"commentDate":"2016-09-23 05:04:35","commentId":193,"commentMemo":"啪啪啪","followNum":0,"parentId":0,"parentName":"","superId":0,"userId":10004,"userImage":"IMAGE_IOS_UPLOAD_1472611551566734_w:375_h:375.png","userName":"机器人小Q"},{"articleId":60,"attendRiderComments":[],"commentDate":"2016-09-23 05:22:46","commentId":194,"commentMemo":"dgsdhsrthrstgrts","followNum":0,"parentId":0,"parentName":"","superId":0,"userId":10001,"userImage":"BANNER_20160903162439772_250_250.jpg","userName":"骑来骑去"}]
     */

    private String message;
    private boolean rewardState;
    /**
     * commentNum : 2
     * createDate : 2016-09-23 03:02:30
     * defaultImage : IMAGE_IOS_UPLOAD_1474613995.798722.jpg
     * id : 60
     * labels : 打酱油,有意思,旅行粉
     * praiseNum : 3
     * praiseState : true
     * scanNum : 106
     * state : publish
     * title : 死胖子叫我发的
     * updateDate :
     * userId : 10004
     * userImage : IMAGE_IOS_UPLOAD_1472611551566734_w:375_h:375.png
     * userName : 机器人小Q
     * virtualPraise : 0
     * virtualScan : 0
     */

    private ArticleBean article;
    private int grade;
    private boolean praiseState;
    private int code;
    private boolean collectState;
    /**
     * list : [{"attendRiderId":"60","context":"","createDate":"2016-09-23 03:02:30","id":125,"imageAddress":"","imageDate":"2016-04-18 12:00:00","imageDesc":"","imageId":"698","imageInglat":"","imageName":"IMAGE_IOS_UPLOAD_1474614054565910_w:312_h:554.png","imageTime":"2016-04-18","imageType":"","tag":"","type":"","voicePath":""}]
     * time : 2016-04-18
     */

    private List<DetailsBean> details;
    /**
     * adress :
     * category :
     * categoryId : 6
     * collectNum : 0
     * create_date : 2016-08-11 10:10:54
     * dcyq : 0
     * descript :
     * end_date : 2016-08-24 12:00:00
     * id : 13
     * jb_time : 10:25,10:35
     * jbdz :
     * jbrq : 3
     * line : 浙江省杭州市下城区朝晖街道河西南38号院施家花园(朝晖路)
     * mzcf : 2,3,5
     * organizerId : 1
     * orginName :
     * phone : 18051481253
     * photo : BANNER_20160811101053753_127_92.jpg
     * praiseNum : 336
     * praiseState : false
     * price : 0.01
     * price_desc : 活动费用
     * price_name :
     * route : 浙江省杭州市下城区朝晖街道河西南38号院施家花园(朝晖路)
     * routeLine : 120.172974 30.276934
     * scanNum : 103
     * signCount : 0
     * slxz : 0
     * start_date : 2016-08-16 12:00:00
     * state : 0
     * stop_car : 0
     * tip : 这是一次分享会
     * title : 一次分享会
     * userAddress :
     * userId : 10001
     * userImage :
     * userMemo :
     * userName :
     * wifi : 0
     * ydjzsj : 0
     * ydsl : 22
     */

    private List<ActivitiesBean> activities;
    /**
     * articleId : 60
     * attendRiderComments : []
     * commentDate : 2016-09-23 05:04:35
     * commentId : 193
     * commentMemo : 啪啪啪
     * followNum : 0
     * parentId : 0
     * parentName :
     * superId : 0
     * userId : 10004
     * userImage : IMAGE_IOS_UPLOAD_1472611551566734_w:375_h:375.png
     * userName : 机器人小Q
     */

    private List<CommentsBean> comments;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRewardState() {
        return rewardState;
    }

    public void setRewardState(boolean rewardState) {
        this.rewardState = rewardState;
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isPraiseState() {
        return praiseState;
    }

    public void setPraiseState(boolean praiseState) {
        this.praiseState = praiseState;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isCollectState() {
        return collectState;
    }

    public void setCollectState(boolean collectState) {
        this.collectState = collectState;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public List<ActivitiesBean> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesBean> activities) {
        this.activities = activities;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class ArticleBean {
        private int commentNum;
        private String createDate;
        private String defaultImage;
        private int id;
        private String labels;
        private int praiseNum;
        private boolean praiseState;
        private int scanNum;
        private String state;
        private String title;
        private String updateDate;
        private int userId;
        private String userImage;
        private String userName;
        private int virtualPraise;
        private int virtualScan;

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

        public String getDefaultImage() {
            return defaultImage;
        }

        public void setDefaultImage(String defaultImage) {
            this.defaultImage = defaultImage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
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

        public int getScanNum() {
            return scanNum;
        }

        public void setScanNum(int scanNum) {
            this.scanNum = scanNum;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
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

        public int getVirtualPraise() {
            return virtualPraise;
        }

        public void setVirtualPraise(int virtualPraise) {
            this.virtualPraise = virtualPraise;
        }

        public int getVirtualScan() {
            return virtualScan;
        }

        public void setVirtualScan(int virtualScan) {
            this.virtualScan = virtualScan;
        }
    }

    public static class DetailsBean {
        private String time;
        /**
         * attendRiderId : 60
         * context :
         * createDate : 2016-09-23 03:02:30
         * id : 125
         * imageAddress :
         * imageDate : 2016-04-18 12:00:00
         * imageDesc :
         * imageId : 698
         * imageInglat :
         * imageName : IMAGE_IOS_UPLOAD_1474614054565910_w:312_h:554.png
         * imageTime : 2016-04-18
         * imageType :
         * tag :
         * type :
         * voicePath :
         */

        private List<ListBean> list;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String attendRiderId;
            private String context;
            private String createDate;
            private int id;
            private String imageAddress;
            private String imageDate;
            private String imageDesc;
            private String imageId;
            private String imageInglat;
            private String imageName;
            private String imageTime;
            private String imageType;
            private String tag;
            private String type;
            private String voicePath;
            // 播放状态
            private String palyState = "none";

            public String getPalyState() {
                return palyState;
            }

            public void setPalyState(String palyState) {
                this.palyState = palyState;
            }

            public String getAttendRiderId() {
                return attendRiderId;
            }

            public void setAttendRiderId(String attendRiderId) {
                this.attendRiderId = attendRiderId;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
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

            public String getImageAddress() {
                return imageAddress;
            }

            public void setImageAddress(String imageAddress) {
                this.imageAddress = imageAddress;
            }

            public String getImageDate() {
                return imageDate;
            }

            public void setImageDate(String imageDate) {
                this.imageDate = imageDate;
            }

            public String getImageDesc() {
                return imageDesc;
            }

            public void setImageDesc(String imageDesc) {
                this.imageDesc = imageDesc;
            }

            public String getImageId() {
                return imageId;
            }

            public void setImageId(String imageId) {
                this.imageId = imageId;
            }

            public String getImageInglat() {
                return imageInglat;
            }

            public void setImageInglat(String imageInglat) {
                this.imageInglat = imageInglat;
            }

            public String getImageName() {
                return imageName;
            }

            public void setImageName(String imageName) {
                this.imageName = imageName;
            }

            public String getImageTime() {
                return imageTime;
            }

            public void setImageTime(String imageTime) {
                this.imageTime = imageTime;
            }

            public String getImageType() {
                return imageType;
            }

            public void setImageType(String imageType) {
                this.imageType = imageType;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVoicePath() {
                return voicePath;
            }

            public void setVoicePath(String voicePath) {
                this.voicePath = voicePath;
            }
        }
    }

    public static class ActivitiesBean {
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
        private double price;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
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
