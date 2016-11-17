package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomListViewEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.PersonalCenterAdapter;
import com.qizhi.qilaiqiqu.model.Active_NewModel;
import com.qizhi.qilaiqiqu.model.Rider_NewModel;
import com.qizhi.qilaiqiqu.model.Riding_RidingListModel;
import com.qizhi.qilaiqiqu.model.UserLoginModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 *  个人中心
 */
public class PersonalCenterActivity extends Activity implements View.OnClickListener {

    private Button btnChat;
    private Button btnCare;
    private LinearLayout layoutButton;
    private PullToZoomListViewEx listView;
    private PersonalCenterAdapter adapter;

    // 头部View控件
    private View headerView;
    private ImageView imgSex;
    private TextView txtName;
    private TextView txtFans;
    private ImageView imgBack;
    private ImageView imgPhoto;
    private ImageView imgEdit;
    private TextView txtIntegral;
    private TextView txtAttention;
    private TextView txtSignature;
    private LinearLayout layoutFans;
    private RelativeLayout layoutignature;

    private SharedPreferences preferences;
    // 被查询用户的ID
    private int userId;

    // 用户信息Model
    UserLoginModel userModel;
    // 是否关注该用户
    boolean state;

    // 游记列表
    List<Riding_RidingListModel> articleList;
    // 活动列表
    List<Active_NewModel> activityList;
    // 陪骑士列表
    List<Rider_NewModel> riderList;
    // 收藏列表
    List<Riding_RidingListModel> colloctedList;
    // 类型
    List<String> typeList;

    private int articleTotal;
    private int activityTotal;

    private String type = "article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        // 个人中心查看
        MobclickAgent.onEvent(PersonalCenterActivity.this, new UmengEventUtil().click60);

        init();
        initHeaderView();
        initView();
        initListViewItem();
}

    private void init() {
        articleList = new ArrayList<Riding_RidingListModel>();
        activityList = new ArrayList<Active_NewModel>();
        riderList = new ArrayList<Rider_NewModel>();
        colloctedList = new ArrayList<Riding_RidingListModel>();

        typeList = new ArrayList<String>();
        typeList.add(type);

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        userId = getIntent().getIntExtra("userId",-1);

    }

    private void initHeaderView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.profile_head_view,null);

        imgSex = (ImageView) headerView.findViewById(R.id.img_personalCengter_sex);
        imgPhoto = (ImageView) headerView.findViewById(R.id.img_personalCengter_photo);
        imgEdit = (ImageView) headerView.findViewById(R.id.img_personalCengter_edit);

        txtFans = (TextView) headerView.findViewById(R.id.txt_personalCengter_fans);
        txtName = (TextView) headerView.findViewById(R.id.txt_personalCengter_userName);
        txtIntegral = (TextView) headerView.findViewById(R.id.txt_personalCengter_integral);
        txtAttention = (TextView) headerView.findViewById(R.id.txt_personalCengter_attention);
        txtSignature = (TextView) headerView.findViewById(R.id.txt_personalCengter_signature);

        layoutFans = (LinearLayout) headerView.findViewById(R.id.layout_personalCengter_fans);
        layoutignature = (RelativeLayout) headerView.findViewById(R.id.layout_personalCengter_edit);

        layoutFans.setOnClickListener(new HeaderViewOnClickListener());
        layoutignature.setOnClickListener(new HeaderViewOnClickListener());

    }

    private void getData() {
        RequestParams params = new RequestParams("UTF-8");

        System.out.println("userId="+userId);
        System.out.println("followId="+preferences.getInt("userId",-1));
        System.out.println("uniqueKey="+preferences.getString("uniqueKey",null));
        params.addBodyParameter("userId",userId+"");

        if(userId == preferences.getInt("userId",-1)){
            params.addBodyParameter("followId","");
        }else{
            params.addBodyParameter("followId",preferences.getInt("userId",-1)+"");
        }
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("personal/queryInfo.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<UserLoginModel>() {
                    }.getType();
                    userModel = gson.fromJson(object.getJSONObject("info").toString(), type);
                    state = object.getBoolean("state");
                    setHeaderView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });

    }

    /**
     *  设置HeaderView控件值
     */
    private void setHeaderView() {

        if(userId != preferences.getInt("userId",-1)){
            imgEdit.setVisibility(View.GONE);
            layoutButton.setVisibility(View.VISIBLE);

            txtFans.setText("TA的粉丝 "+userModel.getUserFans());
            txtAttention.setText("TA关注的 "+userModel.getConcern());
        }else{
            imgEdit.setVisibility(View.VISIBLE);
            layoutButton.setVisibility(View.GONE);

            txtAttention.setText("我关注的 "+userModel.getConcern());
            txtFans.setText("粉丝 "+userModel.getUserFans());
        }


        if ("M".equals(userModel.getSex())) {
            imgSex.setImageResource(R.drawable.male);
        } else if ("F".equals(userModel.getSex())) {
            imgSex.setImageResource(R.drawable.female);
        } else {
            imgSex.setImageBitmap(null);
        }

        SystemUtil.Imagexutils_photo(userModel.getUserImage(), imgPhoto, PersonalCenterActivity.this);

        txtName.setText(userModel.getUserName());
        txtIntegral.setText(userModel.getIntegral()+"");
        txtSignature.setText(userModel.getUserMemo());

        if(state){
            btnCare.setText("已关注");
            btnCare.setBackgroundResource(R.drawable.corners_bg_orange);
        }else{
            btnCare.setText("关注一下");
            btnCare.setBackgroundResource(R.drawable.corners_bg_blue);
        }

        getListData(1);

    }


    /**
     * 初始化View
     */
    private void initView(){
        listView = (PullToZoomListViewEx) findViewById(R.id.listview);
        imgBack = (ImageView) findViewById(R.id.img_personalCengter_back);

        btnChat = (Button) findViewById(R.id.btn_chat);
        btnCare = (Button) findViewById(R.id.btn_care);
        layoutButton = (LinearLayout) findViewById(R.id.layout_button);


    }


    /**
     * 初始化事件
     */
    private void initEvnet() {

        btnChat.setOnClickListener(this);
        btnCare.setOnClickListener(this);
        imgBack.setOnClickListener(new HeaderViewOnClickListener());

        listView.setHeaderView(headerView);
        listView.setAdapter(adapter);

        // HeaderView点击事件
        listView.getPullRootView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent(PersonalCenterActivity.this,PersonalDataActivity.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putSerializable("userModel",userModel);
//                mIntent.putExtras(mBundle);
//                startActivity(mIntent);
            }
        });

        // ListView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("leiqian", "position = " + position);

                if(position >= 2){
                    Intent intent = null;
                    if(type.equals("article")){
                        if(articleList.size()>0){
                            if(articleList.get(position-2).getState().equals("publish")){
                                    intent = new Intent(PersonalCenterActivity.this,Riding_NewDetailActivity.class);
                                    intent.putExtra("articleId", articleList.get(position-2).getId());
                                    intent.putExtra("fromFlag", true);
                                    startActivity(intent);
                                }else{
                                    intent = new Intent(PersonalCenterActivity.this,Riding_NewReleaseActivity.class);
                                    intent.putExtra("articleId", articleList.get(position-2).getId());
                                    intent.putExtra("fromFlag", true);
                                    intent.putExtra("draft",true);
                                    startActivity(intent);
                            }
                        }

                    }else if(type.equals("activity")){
                        if(activityList.size()>0){

                            // 查看报名活动
                            MobclickAgent.onEvent(PersonalCenterActivity.this, new UmengEventUtil().click59);

                            intent = new Intent(PersonalCenterActivity.this,Activity_NewDetailActivity.class);
                            intent.putExtra("activityId", activityList.get(position - 2).getId());
                            intent.putExtra("activityFlag", false);
                            startActivity(intent);
                        }

                    }else if(type.equals("rider")){
                        if(riderList.size()>0){
                            intent = new Intent(PersonalCenterActivity.this,Rider_NewDetailActivity.class);
                            intent.putExtra("riderId",riderList.get(position-2).getId());
                            startActivity(intent);
                        }

                    }else if(type.equals("collected")){
                        if(colloctedList.size()>0){
                            intent = new Intent(PersonalCenterActivity.this,Riding_NewDetailActivity.class);
                            intent.putExtra("articleId",colloctedList.get(position-2).getId());
                            intent.putExtra("fromFlag", true);
                            startActivity(intent);
                        }

                    }
                }


            }
        });



    }

    /**
     * 配置Header的可视高度
     */
    private void initListViewItem(){
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (7.5F * (mScreenHeight / 16.0F)));
        listView.setHeaderLayoutParams(localObject);

    }

    public void changeType(int i){
        System.out.println("int i="+i);
        switch (i){
            case 0:
                articleIndex = 1;
                type = "article";
                typeList.clear();
                typeList.add(type);
                getListData(1);
                break;
            case 1:
                activityIndex = 1;
                type = "activity";
                typeList.clear();
                typeList.add(type);
                getListData(2);
                break;
            case 2:
                riderIndex = 1;
                type = "rider";
                typeList.clear();
                typeList.add(type);
                getListData(3);
                break;
            case 3:
                colloctedIndex = 1;
                type = "collected";
                typeList.clear();
                typeList.add(type);
                getListData(4);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if(preferences.getInt("userId",-1) != -1){
            switch (v.getId()){
                case R.id.btn_chat:
                    startActivity(new Intent(PersonalCenterActivity.this, ChatSingleActivity.class)
                            .putExtra("username", preferences.getString("userName",null))
                            .putExtra("otherUserName", userModel.getUserName())
                            .putExtra("otherUserImage", userModel.getUserImage())
                            .putExtra("otherUserId", userModel.getUserId()));
                    break;

                case R.id.btn_care:
                    attention(state);
                    break;

            }
        }else{
            Toasts.show(PersonalCenterActivity.this, "请先登录", 0);
            Intent intent1 = new Intent(PersonalCenterActivity.this,
                    LoginActivity.class);
            startActivity(intent1);
            PersonalCenterActivity.this.finish();
        }


    }

    private void attention(final boolean s) {
        final RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId",userId+"");
        params.addBodyParameter("followId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null) + "");
        if(s){
            // state == true ，已关注，点击取消关注
            params.addBodyParameter("tag", "2");
        }else{
            // state == false ，未关注，点击加关注
            params.addBodyParameter("tag", "1");
        }
        new XUtilsNew().httpPost("rider/followRider.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                if(s){
                    state = false;
                    btnCare.setText("关注一下");
                    btnCare.setBackgroundResource(R.drawable.corners_bg_blue);
                }else{
                    state = true;
                    btnCare.setText("已关注");
                    btnCare.setBackgroundResource(R.drawable.corners_bg_orange);
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(PersonalCenterActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    class HeaderViewOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_personalCengter_edit:
                    if(userId == preferences.getInt("userId",-1)){

                        Intent mIntent = new Intent(PersonalCenterActivity.this,PersonalDataActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("userModel",userModel);
                        mIntent.putExtras(mBundle);
                        startActivity(mIntent);
                    }
                    break;

                case R.id.img_personalCengter_back:
                    PersonalCenterActivity.this.finish();
                    break;

                case R.id.layout_personalCengter_fans:
                    if(userId == preferences.getInt("userId",-1)){
                        startActivity(new Intent(PersonalCenterActivity.this,FriendActivity.class).putExtra("friendFlag",0));
                    }
                    break;

            }

        }
    }

    int articleIndex = 1;
    int activityIndex = 1;
    int riderIndex = 1;
    int colloctedIndex = 1;
    private void getListData(final int tag) {
        RequestParams params = new RequestParams("UTF-8");

        params.addBodyParameter("userId",userId+"");
        params.addBodyParameter("tag",tag+"");
        params.addBodyParameter("pageSize","30");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));

        if(tag == 1){
            params.addBodyParameter("pageIndex",articleIndex+"");
        } else if(tag == 2){
            params.addBodyParameter("pageIndex",activityIndex+"");
        } else if(tag == 3){
            params.addBodyParameter("pageIndex",riderIndex+"");
        } else if(tag == 4){
            params.addBodyParameter("pageIndex",colloctedIndex+"");
        }

        new XUtilsNew().httpPost("personal/queryList.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    if(tag == 1){ // 骑游记
                        Type type = new TypeToken<List<Riding_RidingListModel>>() {}.getType();
                        List<Riding_RidingListModel> lists = gson.fromJson(object.optJSONArray("dataList").toString(),
                                type);
                        if (articleIndex == 1){
                            articleList.clear();
                        }
                        articleList.addAll(lists);

                        articleTotal = object.getInt("articleTotal");
                        activityTotal = object.getInt("activityTotal");

                    }else if(tag == 2){ // 活动
                        Type type = new TypeToken<List<Active_NewModel>>() {}.getType();
                        List<Active_NewModel> lists = gson.fromJson(object.optJSONArray("dataList").toString(),
                                type);

                        if (activityIndex == 1){
                            activityList.clear();
                        }
                        activityList.addAll(lists);

                        articleTotal = object.getInt("articleTotal");
                        activityTotal = object.getInt("activityTotal");

                    }else if(tag == 3){ // 陪骑士
                        if(object.optJSONObject("pqsInfo") != null){
                            Type type = new TypeToken<Rider_NewModel>() {}.getType();
                            Rider_NewModel rider_newModel = gson.fromJson(object.optJSONObject("pqsInfo").toString(), type);
                            if (riderIndex == 1){
                                riderList.clear();
                            }
                            riderList.add(rider_newModel);

                            articleTotal = object.getInt("articleTotal");
                            activityTotal = object.getInt("activityTotal");
                        }

                    }else if(tag == 4){ // 收藏
                        Type type = new TypeToken<List<Riding_RidingListModel>>() {}.getType();
                        List<Riding_RidingListModel> lists = gson.fromJson(object.optJSONArray("qyjlist").toString(),
                                type);
                        if (colloctedIndex == 1){
                            colloctedList.clear();
                        }
                        colloctedList.addAll(lists);

                        articleTotal = object.getInt("articleTotal");
                        activityTotal = object.getInt("activityTotal");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new PersonalCenterAdapter(articleList, activityList, riderList, colloctedList, typeList, articleTotal, activityTotal, PersonalCenterActivity.this, PersonalCenterActivity.this, userId);
                initEvnet();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(PersonalCenterActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

//    @Override
//    public void onRefresh() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                swipeLayout.setRefreshing(false);
//                // 更新数据
//                // 更新完后调用该方法结束刷新
//
//            }
//        }, 1500);
//    }

//    @Override
//    public void onLoad() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                swipeLayout.setLoading(false);
//                if(type.equals("article")){
//                    articleIndex = articleIndex + 1;
//                    getListData(1);
//                } else if(type.equals("activity")){
//                    activityIndex = activityIndex + 1;
//                    getListData(2);
//                }else if(type.equals("rider")){
//                    riderIndex = riderIndex + 1;
//                    getListData(3);
//                }else if(type.equals("collected")){
//                    colloctedIndex = colloctedIndex +1;
//                    getListData(4);
//                }
//
//            }
//        }, 1500);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }
}
