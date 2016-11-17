package com.qizhi.qilaiqiqu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell1 on 2016/10/10.
 */

public class RecommendActivityModel implements Serializable {


    /**
     * message : ok
     * dataList : [{"adress":"","category":"","categoryId":4,"collectNum":0,"create_date":"2016-10-08 05:27:21","dcyq":1,"descript":"","end_date":"2016-10-22 12:00:00","id":1,"jb_time":"","jbdz":"","jbrq":2,"line":"26.961KM","mzcf":"","organizerId":1,"orginName":"骑来骑去","phone":"13588766234","photo":"WEB_20161008172715889_700_400.jpg,WEB_20161008172721727_500_500.jpg","praiseNum":0,"praiseState":false,"price":120,"price_desc":"费用主要针对骑行过程中产生的补给费用。本活动为骑来骑去公益健身活动，基本费用将全部应用于骑友骑行过程中的补给，骑来骑去也将投入费用确保活动顺利完成！","price_name":"骑行优惠票","route":"","routeLine":"","scanNum":172,"signCount":0,"slxz":0,"start_date":"2016-10-15 12:00:00","state":0,"stop_car":0,"tip":"骑友需要自备装备，同时关注活动期间的天气状况，骑来骑去后勤人员会做好后勤保障，最新消息请关注骑来骑去微信公众号：we_like_riding。","title":"第一届\u201c骑来骑去\u201d杭州地区腐败骑行拉练比赛","userAddress":"","userId":10002,"userImage":"Android_User_20161009_100300_:1083_:1077.jpg","userMemo":"","userName":"","wifi":0,"ydjzsj":"2016-10-21","ydsl":2},{"adress":"","category":"","categoryId":5,"collectNum":0,"create_date":"2016-10-09 10:39:05","dcyq":0,"descript":"","end_date":"2017-02-09 12:00:00","id":2,"jb_time":"","jbdz":"","jbrq":3,"line":"浙江省杭州市西湖区蒋村街道西溪国家湿地公园","mzcf":"6","organizerId":1,"orginName":"骑来骑去","phone":"13588766234","photo":"WEB_20161009103858736_1024_692.jpg,WEB_20161009103900606_1024_692.jpg,WEB_20161009103902085_1024_683.jpg","praiseNum":0,"praiseState":false,"price":0,"price_desc":"活动费用","price_name":"","route":"","routeLine":"","scanNum":34,"signCount":3,"slxz":0,"start_date":"2016-10-09 12:00:00","state":0,"stop_car":0,"tip":"活动分享会不间断，也欢迎想分享的人主动联系我们，在骑来骑去微信公众号里回复：达人分享+联系方式。我们将会尽快联系您，安排分享档期。欢迎关注骑来骑去微信公众号：we_like_ridin，。获取最新动态。","title":"\u201c骑来骑去\u201d自行车行业分享会","userAddress":"","userId":10002,"userImage":"Android_User_20161009_100300_:1083_:1077.jpg","userMemo":"","userName":"","wifi":0,"ydjzsj":"2016-10-09","ydsl":2}]
     * totalCount : 2
     * hint : 陪骑资料提交后只有陪骑时间可以改，其他资料如需更改 请联系骑来骑去工作人员。 如果陪骑有任何疑问，请关注骑来骑去公众号：we_like_riding， 向人工客服提交您的问题！我们将第一时间给您反馈。
     * code : 0
     */

    private String message;
    private int totalCount;
    private String hint;
    private int code;
    /**
     * adress :
     * category :
     * categoryId : 4
     * collectNum : 0
     * create_date : 2016-10-08 05:27:21
     * dcyq : 1
     * descript :
     * end_date : 2016-10-22 12:00:00
     * id : 1
     * jb_time :
     * jbdz :
     * jbrq : 2
     * line : 26.961KM
     * mzcf :
     * organizerId : 1
     * orginName : 骑来骑去
     * phone : 13588766234
     * photo : WEB_20161008172715889_700_400.jpg,WEB_20161008172721727_500_500.jpg
     * praiseNum : 0
     * praiseState : false
     * price : 120
     * price_desc : 费用主要针对骑行过程中产生的补给费用。本活动为骑来骑去公益健身活动，基本费用将全部应用于骑友骑行过程中的补给，骑来骑去也将投入费用确保活动顺利完成！
     * price_name : 骑行优惠票
     * route :
     * routeLine :
     * scanNum : 172
     * signCount : 0
     * slxz : 0
     * start_date : 2016-10-15 12:00:00
     * state : 0
     * stop_car : 0
     * tip : 骑友需要自备装备，同时关注活动期间的天气状况，骑来骑去后勤人员会做好后勤保障，最新消息请关注骑来骑去微信公众号：we_like_riding。
     * title : 第一届“骑来骑去”杭州地区腐败骑行拉练比赛
     * userAddress :
     * userId : 10002
     * userImage : Android_User_20161009_100300_:1083_:1077.jpg
     * userMemo :
     * userName :
     * wifi : 0
     * ydjzsj : 2016-10-21
     * ydsl : 2
     */

    private List<DataListBean> dataList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean implements Serializable {
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
}
