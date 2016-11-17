package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.Rider_NewDetailAdapter;
import com.qizhi.qilaiqiqu.model.Rider_NewDetailModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.MySSScrollview;
import com.qizhi.qilaiqiqu.utils.RotateTextView;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  陪骑详情
 */
public class Rider_NewDetailActivity extends MyBaseActivity implements MySSScrollview.OnScrollListener {

    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;
    @InjectView(R.id.layout_button1)
    LinearLayout layoutButton1;
    @InjectView(R.id.layout_button2)
    LinearLayout layoutButton2;
    @InjectView(R.id.layout_about)
    LinearLayout layoutAbout;
    @InjectView(R.id.lyaout_rider)
    LinearLayout layoutRider;
    @InjectView(R.id.txt_about)
    TextView txtAbout;
    @InjectView(R.id.txt_rider)
    TextView txtRider;
    @InjectView(R.id.txt_dptj)
    TextView txtDptj;
    @InjectView(R.id.txt_button1)
    TextView txtButton1;
    @InjectView(R.id.txt_button2)
    TextView txtButton2;
    @InjectView(R.id.img_title_bg)
    ImageView imgTitleBg;
    @InjectView(R.id.img_title_photo)
    CircleImageViewUtil imgTitlePhoto;
    @InjectView(R.id.layout_title_photo)
    RelativeLayout layoutTitlePhoto;
    @InjectView(R.id.txt_title_name)
    TextView txtTitleName;
    @InjectView(R.id.txt_title_intro)
    TextView txtTitleIntro;
    @InjectView(R.id.txt_title_care)
    TextView txtTitleCare;
    @InjectView(R.id.txt_title_chat)
    TextView txtTitleChat;
    @InjectView(R.id.txt_title_about)
    TextView txtTitleAbout;
    @InjectView(R.id.txt_title_rider)
    TextView txtTitleRider;
    @InjectView(R.id.txt_title_dptj)
    TextView txtTitleDptj;
    @InjectView(R.id.rider_lable)
    TagFlowLayout riderLable;
    @InjectView(R.id.txt_Memo)
    TextView txtMemo;
    @InjectView(R.id.txt_address)
    TextView txtAddress;
    @InjectView(R.id.txt_prices)
    TextView txtPrices;
    @InjectView(R.id.txt_time)
    TextView txtTime;
    @InjectView(R.id.txt_selectTime)
    TextView txtSelectTime;
    @InjectView(R.id.txt_info)
    TextView txtInfo;
    @InjectView(R.id.img_individualImage1)
    ImageView imgIndividualImage1;
    @InjectView(R.id.img_individualImage2)
    ImageView imgIndividualImage2;
    @InjectView(R.id.list_comment)
    ListView listComment;
    @InjectView(R.id.txt_notice)
    TextView txtNotice;
    @InjectView(R.id.txt_title1)
    TextView txtTitle1;
    @InjectView(R.id.txt_browse1)
    TextView txtBrowse1;
    @InjectView(R.id.img_photo1)
    ImageView imgPhoto1;
    @InjectView(R.id.txt_money1)
    RotateTextView txtMoney1;
    @InjectView(R.id.img_userImage1)
    CircleImageViewUtil imgUserImage1;
    @InjectView(R.id.txt_userName1)
    TextView txtUserName1;
    @InjectView(R.id.txt_activityInfo)
    TextView txtActivityInfo;
    @InjectView(R.id.txt_itle2)
    TextView txtItle2;
    @InjectView(R.id.txt_browse2)
    TextView txtBrowse2;
    @InjectView(R.id.img_photo2)
    ImageView imgPhoto2;
    @InjectView(R.id.txt_money2)
    RotateTextView txtMoney2;
    @InjectView(R.id.img_userImage2)
    CircleImageViewUtil imgUserImage2;
    @InjectView(R.id.txt_userName2)
    TextView txtUserName2;
    @InjectView(R.id.txt_activityInfo2)
    TextView txtActivityInfo2;
    @InjectView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @InjectView(R.id.layout_title_info)
    RelativeLayout layoutInfo;
    @InjectView(R.id.mySSScrollview)
    MySSScrollview mySSScrollview;


    // 陪骑士信息
    private int riderId;
    private int followId;
    private String address;

    // ListView 数据源
    Rider_NewDetailModel model;

    // ListView 适配器
    Rider_NewDetailAdapter adapter;

    // 用户本地数据
    private SharedPreferences preferences;

    // 陪骑士可约骑时间
    public static List<String> datelist;

    // 顶部布局的高度
    private int infoLayoutTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider__new_detail);
        ButterKnife.inject(this);

        init();
        initEvent();
        getData();
        getParams();
    }


    private void init() {
        datelist = new ArrayList<String>();
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        followId = preferences.getInt("userId",-1);
        address = preferences.getString("location",null);
        riderId = getIntent().getIntExtra("riderId", -1);

        model = new Rider_NewDetailModel();
        mySSScrollview.setOnScrollListener(this);
        System.out.println("init()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 每次启动，设置顶部布局的高度
        if (hasFocus)
        {
            infoLayoutTop = layoutInfo.getBottom();

        }

//        mySSScrollview.fullScroll(ScrollView.FOCUS_UP);
        System.out.println("onWindowFocusChanged()");

    }

    // 自定义ScrollView滑动监听，获取实时的Y轴滑动距离
    @Override
    public void onScroll(int scrollY) {
        // 如果滑动的距离大于等于了顶部布局的高度H
        if (scrollY >= infoLayoutTop) {
            layoutButton2.setVisibility(View.VISIBLE);

            if(layoutButton1.getBottom() <= scrollY && scrollY < layoutAbout.getBottom()){
                initButtonColor();
                txtAbout.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleAbout.setBackgroundColor(getResources().getColor(R.color.white));
            }else if(layoutAbout.getBottom() <= scrollY && scrollY < layoutRider.getBottom()){
                initButtonColor();
                txtRider.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleRider.setBackgroundColor(getResources().getColor(R.color.white));
            }else if(layoutRider.getBottom() <= scrollY){
                initButtonColor();
                txtDptj.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleDptj.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        // 如果滑动距离小于了顶部布局的高度
        else {
            layoutButton2.setVisibility(View.GONE);
        }

        System.out.println("onScroll()");
    }

    private void initEvent() {
        System.out.println("initEvent()");
    }

    public void getParams() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("name","ride_note");
        new XUtilsNew().httpPost("common/queryParams.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    txtNotice.setText(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });
        System.out.println("getParams()");
    }

    public void getData() {

        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("riderId", riderId + "");
        params.addBodyParameter("followId", followId + "");
        params.addBodyParameter("address", address);

        System.out.println("riderId="+riderId);
        System.out.println("followId="+followId);
        System.out.println("address="+address);

        new XUtilsNew().httpPost("rider/queryDetail.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();

                    model = gson.fromJson(object.toString(), new TypeToken<Rider_NewDetailModel>() {}.getType());
                    setView();
                    getTime();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewDetailActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

        System.out.println("getData()");
    }

    private void getTime() {

        String[] riderTime =  model.getDetail().getRiderTime().split(",");
        for(String time : riderTime){
            datelist.add(time);
        }

        mySSScrollview.fullScroll(ScrollView.FOCUS_UP);
        System.out.println("getTime()");
    }

    private void setView() {
        // headerView
        String[] bg = model.getDetail().getPersonalImage().split(",");
        SystemUtil.Imagexutils_new(bg[0],imgTitleBg,this);
        SystemUtil.Imagexutils_photo(model.getDetail().getUserImage(), imgTitlePhoto, this);
        txtTitleName.setText(model.getDetail().getUserName());
        txtTitleIntro.setText(model.getDetail().getUserMemo());

        if (model.isState()) {
            txtTitleCare.setText("已关注");
            txtTitleCare.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            txtTitleCare.setText("加关注");
            txtTitleCare.setBackgroundColor(getResources().getColor(R.color.gray7f));
        }

        // aboutView
        txtMemo.setText(model.getDetail().getPersonalDesc());
        riderLable.setAdapter(new TagAdapter(model.getDetail().getLabel().split(",")) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(Rider_NewDetailActivity.this).inflate(R.layout.tag_item_activive2,null);
                textView.setText(o.toString());
                return textView;
            }
        });

        // infoView
        txtAddress.setText(model.getDetail().getRiderArea());
        txtPrices.setText(model.getDetail().getRiderPrice()+"元/h");
        txtInfo.setText(model.getDetail().getRiderDesc());
        String[] img = model.getDetail().getPersonalImage().split(",");
        if(img.length == 2){
            SystemUtil.Imagexutils_new(img[1],imgIndividualImage1,this);

        }else if(img.length == 3){
            SystemUtil.Imagexutils_new(img[1],imgIndividualImage1,this);
            SystemUtil.Imagexutils_new(img[2],imgIndividualImage2,this);

        }

        // commentView
        adapter = new Rider_NewDetailAdapter(model,Rider_NewDetailActivity.this);
        listComment.setAdapter(adapter);


        // recommendView
        txtTitle1.setText(model.getList().get(0).getTitle());
        txtUserName1.setText(model.getList().get(0).getOrginName());
        txtBrowse1.setText(model.getList().get(0).getScanNum()+"次浏览");
        txtActivityInfo.setText(model.getList().get(0).getCreate_date().split(" ")[0]);
        SystemUtil.Imagexutils_new(model.getList().get(0).getUserImage(), imgUserImage1, this);
        SystemUtil.Imagexutils_new(model.getList().get(0).getPhoto().split(",")[0], imgPhoto1, this);
        if(model.getList().get(0).getPrice() == 0){
            txtMoney1.setVisibility(View.GONE);
            txtMoney1.setText("免费");
        }else{
            txtMoney1.setVisibility(View.VISIBLE);
            txtMoney1.setText("收费");
        }

        txtItle2.setText(model.getList().get(1).getTitle());
        txtUserName2.setText(model.getList().get(1).getOrginName());
        txtBrowse2.setText(model.getList().get(1).getScanNum()+"次浏览");
        txtActivityInfo2.setText(model.getList().get(1).getCreate_date().split(" ")[0]);
        SystemUtil.Imagexutils_new(model.getList().get(0).getUserImage(), imgUserImage1, this);
        SystemUtil.Imagexutils_new(model.getList().get(1).getPhoto().split(",")[0], imgPhoto2, this);
        if(model.getList().get(1).getPrice() == 0){
            txtMoney2.setVisibility(View.GONE);
            txtMoney2.setText("免费");
        }else{
            txtMoney2.setVisibility(View.VISIBLE);
            txtMoney2.setText("收费");
        }

        mySSScrollview.fullScroll(ScrollView.FOCUS_UP);
        System.out.println("setView()");
    }


    private void addCare() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", model.getDetail().getUserId() + "");
        params.addBodyParameter("followId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null) + "");
        params.addBodyParameter("tag", "1");
        new XUtilsNew().httpPost("rider/followRider.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                model.setState(true);
                txtTitleCare.setText("已关注");
                txtTitleCare.setBackgroundColor(getResources().getColor(R.color.orange));

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewDetailActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
        System.out.println("addCare()");
    }

    private void cancelCare() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", model.getDetail().getUserId() + "");
        params.addBodyParameter("followId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null) + "");
        params.addBodyParameter("tag", "2");
        new XUtilsNew().httpPost("rider/followRider.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                model.setState(false);
                txtTitleCare.setText("加关注");
                txtTitleCare.setBackgroundColor(getResources().getColor(R.color.gray7f));
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewDetailActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
        System.out.println("cancelCare()");

    }

    @OnClick({R.id.txt_button1, R.id.txt_button2, R.id.txt_title_care, R.id.txt_title_chat, R.id.img_title_photo, R.id.layout_back,
            R.id.txt_title_about,R.id.txt_title_rider,R.id.txt_title_dptj,R.id.txt_about,R.id.txt_rider,R.id.txt_dptj})
    public void onClick(View view) {
        System.out.println("onClick()");
        switch (view.getId()) {
            case R.id.txt_button1:
                if(preferences.getInt("userId",-1) != -1){
                    startActivityForResult(new Intent(Rider_NewDetailActivity.this,Rider_NewSelectDate.class),0);
                }else{
                    Toasts.show(Rider_NewDetailActivity.this, "请先登录", 0);
                    Intent intent1 = new Intent(Rider_NewDetailActivity.this,
                            LoginActivity.class);
                    startActivity(intent1);
                    Rider_NewDetailActivity.this.finish();
                }


                break;


            case R.id.txt_button2:
                if ("约骑".equals(txtButton2.getText().toString())) {
                    if("请选择约骑日期".equals(txtButton1.getText().toString())){
                        Toasts.show(Rider_NewDetailActivity.this, "请先选择约骑日期", 0);
                    }else{

                        // 选择约骑申请
                        MobclickAgent.onEvent(Rider_NewDetailActivity.this, new UmengEventUtil().click42);

                        startActivity(new Intent(Rider_NewDetailActivity.this,Rider_NewApplyActivity.class)
                                .putExtra("riderId",riderId)
                                .putExtra("prices",model.getDetail().getRiderPrice())
                                .putExtra("userName",model.getDetail().getUserName())
                                .putExtra("attendTime",txtButton1.getText().toString()));
                    }
                } else {

                }
                break;

            case R.id.txt_title_care:
                if (model.isState()) {
                    // 取消关注
                    cancelCare();
                } else {
                    // 加关注
                    addCare();
                }
                break;

            case R.id.txt_title_chat:
//                startActivity(new Intent(Rider_NewDetailActivity.this, ChatSingleActivity.class)
//                        .putExtra("username", preferences.getString("userName",null))
//                        .putExtra("otherUserName", model.getDetail().getUserName())
//                        .putExtra("otherUserImage", model.getDetail().getUserImage())
//                        .putExtra("otherUserId", model.getDetail().getUserId()));
                if(preferences.getInt("userId",-1) != -1){
                    startActivity(new Intent(Rider_NewDetailActivity.this, ChatSingleActivity.class)
                            .putExtra("username", preferences.getString("userName",null))
                            .putExtra("otherUserName", model.getDetail().getUserName())
                            .putExtra("otherUserImage", model.getDetail().getUserImage())
                            .putExtra("otherUserId", model.getDetail().getUserId()));
                }else{
                    Toasts.show(Rider_NewDetailActivity.this, "请先登录", 0);
                    Intent intent1 = new Intent(Rider_NewDetailActivity.this,
                            LoginActivity.class);
                    startActivity(intent1);
                    Rider_NewDetailActivity.this.finish();
                }



                break;

            case R.id.img_title_photo:
                startActivity(new Intent(Rider_NewDetailActivity.this,PersonalCenterActivity.class).putExtra("userId",model.getDetail().getUserId()));
                break;

            case R.id.layout_back:
                finish();
                break;



            case R.id.txt_title_about:
                initButtonColor();
                layoutButton2.setVisibility(View.VISIBLE);
                txtAbout.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleAbout.setBackgroundColor(getResources().getColor(R.color.white));
                mySSScrollview.scrollTo(0,layoutButton1.getBottom());
                break;

            case R.id.txt_title_rider:
                initButtonColor();
                layoutButton2.setVisibility(View.VISIBLE);
                txtRider.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleRider.setBackgroundColor(getResources().getColor(R.color.white));
                mySSScrollview.scrollTo(0,layoutAbout.getBottom());
                break;

            case R.id.txt_title_dptj:
                initButtonColor();
                layoutButton2.setVisibility(View.VISIBLE);
                txtDptj.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleDptj.setBackgroundColor(getResources().getColor(R.color.white));
                mySSScrollview.scrollTo(0,layoutRider.getBottom());
                break;

            case R.id.txt_about:
                initButtonColor();
                txtAbout.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleAbout.setBackgroundColor(getResources().getColor(R.color.white));
                mySSScrollview.scrollTo(0,layoutButton1.getBottom());
                break;

            case R.id.txt_rider:
                initButtonColor();
                txtRider.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleRider.setBackgroundColor(getResources().getColor(R.color.white));
                mySSScrollview.scrollTo(0,layoutAbout.getBottom());
                break;

            case R.id.txt_dptj:
                initButtonColor();
                txtDptj.setBackgroundColor(getResources().getColor(R.color.white));
                txtTitleDptj.setBackgroundColor(getResources().getColor(R.color.white));
                mySSScrollview.scrollTo(0,layoutRider.getBottom());
                break;

        }
    }

    // 更新悬浮按钮颜色
    private void initButtonColor(){

        txtTitleAbout.setBackgroundColor(getResources().getColor(R.color.grayee));
        txtTitleRider.setBackgroundColor(getResources().getColor(R.color.grayee));
        txtTitleDptj.setBackgroundColor(getResources().getColor(R.color.grayee));

        txtAbout.setBackgroundColor(getResources().getColor(R.color.grayee));
        txtRider.setBackgroundColor(getResources().getColor(R.color.grayee));
        txtDptj.setBackgroundColor(getResources().getColor(R.color.grayee));
        System.out.println("initButtonColor()");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //data为B中回传的Intent
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                //str即为回传的值
                txtButton1.setText(data.getExtras().getString("date"));
                break;
            default:
                break;
        }
        System.out.println("onActivityResult()");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
