package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.Riding_NewDetailAdapter;
import com.qizhi.qilaiqiqu.model.Riding_NewDatailModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.RotateTextView;
import com.qizhi.qilaiqiqu.utils.SoundPlayer;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.WBConstants;
import com.qizhi.qilaiqiqu.utils.WBShareUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

import cn.jpush.android.api.JPushInterface;

/**
 *	骑游记详情
 */
public class Riding_NewDetailActivity extends MyBaseActivity implements LocationSource, AMapLocationListener {

    private RelativeLayout layoutTitle;
    private ListView mListView;
    private TextView txtDelete;
    private View mHeaderView;
    private View mFooterView;
    private Riding_NewDetailAdapter adapter;

    private Riding_NewDatailModel model;

    // 高德地图
    private AMap aMap;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    // 头部View 控件
    private ImageView imgBack;
    private ImageView imgAttention;
    private ImageView imgShare;
    private ImageView imgCover;
    private ImageView imgPhoto;
    private TextView txtTitle;
    private MapView mMapView;
    private RelativeLayout layoutHeader;



    // 浮动按钮
    private ImageView imgComment;
    private ImageView imgReward;
    private ImageView imgLike;
    private TextView txtLike;
    private TextView numTxt;
    private Animation animation;

    // 打赏弹窗控件
    private int markPointInt;
    private ImageView popup_mark0;
    private ImageView popup_mark1;
    private ImageView popup_mark2;
    private ImageView popup_mark3;
    private ImageView popup_mark4;
    private ImageView popup_mark5;
    private ImageView popup_mark6;
    private ImageView popup_mark7;
    private ImageView popup_mark8;
    private ImageView popup_mark9;
    private TextView popup_ok;
    private TextView markPointTxt;

    private TextView popup_yes;
    private TextView popup_cancel;

    // 尾部View 控件
    private TagFlowLayout label;
    TextView txtTitle1;
    TextView txtBrowse1;
    ImageView imgPhoto1;
    TextView txtUserName1;
    RotateTextView txtMoney1;
    LinearLayout layoutActivity1;
    CircleImageViewUtil imgUserImage1;

    TextView txtActivityInfo;
    TextView txtTitle2;
    TextView txtBrowse2;
    ImageView imgPhoto2;
    TextView txtUserName2;
    RotateTextView txtMoney2;
    LinearLayout layoutActivity2;
    CircleImageViewUtil imgUserImage2;


    // 游记ID
    private int articleId;
    // 游记查看者是否为自己
    private boolean isMe;

    private SharedPreferences preferences;

    // 微博分享接口实例
    private IWeiboShareAPI mWeiboShareAPI = null;

    // 腾讯分享接口实例
    private Tencent mTencent;
    private IUiListener baseUiListener; // 监听器

    // 微信分享实例
    private IWXAPI api;

    // 系统消息Type
    private String inType;

    private boolean isFromPerson;

    //是否游记修改
    private boolean ReleaseActivityfalg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding_new_detail);


        // 查看游记详情
        MobclickAgent.onEvent(Riding_NewDetailActivity.this,"click12");

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        System.out.println("onCreate() width = "+width);
        System.out.println("onCreate() height = "+height);
        System.out.println("onCreate() density = "+density);
        System.out.println("onCreate() densityDpi = "+densityDpi);


        initHeaderView();
        initFooterView();
        initView();
        getData();

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
//        mBigMapView.onCreate(savedInstanceState);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
            System.out.println("dafocus="+hasFocus);
        if(hasFocus){
        }
    }

    private int ImageHeight;
    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.activity_new_riding_details_header, null);

        mMapView = (MapView) mHeaderView.findViewById(R.id.NewRidingDetailActivity_map);

        txtTitle = (TextView) mHeaderView.findViewById(R.id.txt_NewRidingDetailActivity_title);
        imgPhoto = (ImageView) mHeaderView.findViewById(R.id.img_NewRidingDetailActivity_photo);
        imgCover = (ImageView) mHeaderView.findViewById(R.id.img_NewRidingDetailActivity_headerImg);
        layoutHeader = (RelativeLayout) mHeaderView.findViewById(R.id.layout_NewRidingDetailActivity_headerImg);

        initMapView();

        if(height == 0){
            imgCover.measure(0, 0);
            ImageHeight = layoutHeader.getMeasuredHeight();
        }


    }

    private void initMapView() {

        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.getUiSettings().setCompassEnabled(true);
            aMap.getUiSettings().setZoomControlsEnabled(false);


            // 设置在操作地图时listView事件不触发
            aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
                @Override
                public void onTouch(MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        mListView.requestDisallowInterceptTouchEvent(false);
                    }else {
                        mListView.requestDisallowInterceptTouchEvent(true);
                    }
                }
            });

            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent mIntent = new Intent(Riding_NewDetailActivity.this,Riding_NewMapActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("model",model);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);


                    System.out.println("model");

                }
            });

            aMap.setLocationSource(this);// 设置定位监听

            // 设置默认定位按钮是否显示
            aMap.getUiSettings().setMyLocationButtonEnabled(false);
            // 设置默认缩放按钮是否显示
            aMap.getUiSettings().setZoomControlsEnabled(false);
            // 设置默认缩放按钮是否显示
            aMap.getUiSettings().setCompassEnabled(false);
            // logo位置
            aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);


            // 自定义系统定位蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            // 自定义定位蓝点图标
            //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.));
            // 自定义精度范围的圆形边框颜色
            myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));
            // 自定义精度范围的圆形边框宽度
            myLocationStyle.strokeWidth(0);
            //设置圆形区域（以定位位置为圆心，定位半径的圆形区域）的填充颜色。
            myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));
            // 将自定义的 myLocationStyle 对象添加到地图上
            aMap.setMyLocationStyle(myLocationStyle);
        }
    }

    private void initFooterView() {
        mFooterView = LayoutInflater.from(this).inflate(R.layout.activity_new_riding_details_footer, null);

        label = (TagFlowLayout) mFooterView.findViewById(R.id.NewRidingDetailActivity_label);

        txtTitle1 = (TextView) mFooterView.findViewById(R.id.txt_title1);
        txtBrowse1 = (TextView) mFooterView.findViewById(R.id.txt_browse1);
        imgPhoto1 = (ImageView) mFooterView.findViewById(R.id.img_photo1);
        txtMoney1 = (RotateTextView) mFooterView.findViewById(R.id.txt_money1);
        txtUserName1 = (TextView) mFooterView.findViewById(R.id.txt_userName1);
        layoutActivity1 = (LinearLayout) mFooterView.findViewById(R.id.layout_activity1);
        imgUserImage1 = (CircleImageViewUtil) mFooterView.findViewById(R.id.img_userImage1);

        txtTitle2 = (TextView) mFooterView.findViewById(R.id.txt_title2);
        txtBrowse2 = (TextView) mFooterView.findViewById(R.id.txt_browse2);
        imgPhoto2 = (ImageView) mFooterView.findViewById(R.id.img_photo2);
        txtMoney2 = (RotateTextView) mFooterView.findViewById(R.id.txt_money2);
        txtUserName2 = (TextView) mFooterView.findViewById(R.id.txt_userName2);
        layoutActivity2 = (LinearLayout) mFooterView.findViewById(R.id.layout_activity2);
        imgUserImage2 = (CircleImageViewUtil) mFooterView.findViewById(R.id.img_userImage2);

        layoutActivity1.setOnClickListener(new FooderViewOnClickListener());
        layoutActivity2.setOnClickListener(new FooderViewOnClickListener());

    }

    private void setView() {
        isClection = model.isCollectState();
        isPraise = model.isPraiseState();

        SystemUtil.Imagexutils_new(model.getArticle().getDefaultImage(),imgCover,this);
        SystemUtil.Imagexutils_photo(model.getArticle().getUserImage(),imgPhoto,this);
        txtTitle.setText(model.getArticle().getTitle());

        if(isClection){
            imgAttention.setImageResource(R.drawable.clection_chosen);
        }else {
            imgAttention.setImageResource(R.drawable.cllection_unchosen);
        }
        if(isFromPerson){
            if(model.getArticle().getUserId() == preferences.getInt("userId",-1)){
                imgShare.setVisibility(View.GONE);
                imgAttention.setVisibility(View.GONE);
                txtDelete.setVisibility(View.VISIBLE);
            }else{
                txtDelete.setVisibility(View.GONE);
                imgShare.setVisibility(View.VISIBLE);
                imgAttention.setVisibility(View.VISIBLE);
            }
        }


        System.out.println("isPraise="+isPraise);
        if(isPraise){
            imgLike.setImageResource(R.drawable.like_chosen);
        }else {
            imgLike.setImageResource(R.drawable.like_unchosen);
        }

        txtLike.setText(model.getArticle().getPraiseNum()+"");

        if ("QYJDZ".equals(inType) || "QYJDS".equals(inType)) {
            showReward(inType);
        }

        label.setAdapter(new TagAdapter(model.getArticle().getLabels().split(",")) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(Riding_NewDetailActivity.this).inflate(R.layout.tag_item_activive2,null);
                textView.setText(o.toString());
                return textView;
            }
        });

        if(model.getActivities().size() == 1){
            txtTitle1.setText(model.getActivities().get(0).getTitle());
            txtUserName1.setText(model.getActivities().get(0).getOrginName());
            txtBrowse1.setText(model.getActivities().get(0).getScanNum()+"次浏览");
            if(model.getActivities().get(0).getPrice() == 0){
                txtMoney1.setVisibility(View.GONE);
            }else{
                txtMoney1.setVisibility(View.VISIBLE);
            }
            SystemUtil.Imagexutils_photo(model.getActivities().get(0).getPhoto(),imgPhoto1,this);
            SystemUtil.Imagexutils_photo(model.getActivities().get(0).getUserImage(),imgUserImage1,this);
        }else if(model.getActivities().size() > 1){
            txtTitle1.setText(model.getActivities().get(0).getTitle());
            txtUserName1.setText(model.getActivities().get(0).getOrginName());
            txtBrowse1.setText(model.getActivities().get(0).getScanNum()+"次浏览");
            if(model.getActivities().get(0).getPrice() == 0){
                txtMoney1.setVisibility(View.GONE);
            }else{
                txtMoney1.setVisibility(View.VISIBLE);
            }
            SystemUtil.Imagexutils_photo(model.getActivities().get(0).getPhoto(),imgPhoto1,this);
            SystemUtil.Imagexutils_photo(model.getActivities().get(0).getUserImage(),imgUserImage1,this);

            txtTitle2.setText(model.getActivities().get(1).getTitle());
            txtUserName2.setText(model.getActivities().get(1).getOrginName());
            txtBrowse2.setText(model.getActivities().get(1).getScanNum()+"次浏览");
            if(model.getActivities().get(1).getPrice() == 0){
                txtMoney2.setVisibility(View.GONE);
            }else{
                txtMoney2.setVisibility(View.VISIBLE);
            }
            SystemUtil.Imagexutils_photo(model.getActivities().get(1).getPhoto(),imgPhoto2,this);
            SystemUtil.Imagexutils_photo(model.getActivities().get(1).getUserImage(),imgUserImage2,this);
        }


    }

    private void initView() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        inType = getIntent().getStringExtra("inType");
        isMe = getIntent().getBooleanExtra("isMe",false);
        articleId = getIntent().getIntExtra("articleId",-1);
        isFromPerson = getIntent().getBooleanExtra("fromFlag",false);
        ReleaseActivityfalg = getIntent().getBooleanExtra("ReleaseActivityfalg", false);

        layoutTitle = (RelativeLayout) findViewById(R.id.layout_newRidingDeail_title);
        height = layoutTitle.getMeasuredHeight();

        imgBack = (ImageView) findViewById(R.id.img_NewRidingDetailActivity_back);
        imgAttention = (ImageView) findViewById(R.id.img_NewRidingDetailActivity_attention);
        imgShare = (ImageView) findViewById(R.id.img_NewRidingDetailActivity_share);

        numTxt = (TextView) findViewById(R.id.animation);
        txtLike = (TextView) findViewById(R.id.txt_newRidingDeail_like);
        imgLike = (ImageView) findViewById(R.id.img_newRidingDeail_like);
        imgReward = (ImageView) findViewById(R.id.img_newRidingDeail_reward);
        imgComment = (ImageView) findViewById(R.id.img_newRidingDeail_comment);
        txtDelete = (TextView) findViewById(R.id.txt_NewRidingDetailActivity_delete);

        animation = AnimationUtils.loadAnimation(this, R.anim.applaud_animation);

        mListView = (ListView) findViewById(R.id.list_newRidingDeail_list);
        mListView.addHeaderView(mHeaderView);
        mListView.addFooterView(mFooterView);
        model = new Riding_NewDatailModel();

        imgShare.setOnClickListener(new ViewOnClickListener());
        imgBack.setOnClickListener(new ViewOnClickListener());
        imgAttention.setOnClickListener(new ViewOnClickListener());

        imgLike.setOnClickListener(new ViewOnClickListener());
        imgReward.setOnClickListener(new ViewOnClickListener());
        imgComment.setOnClickListener(new ViewOnClickListener());

        txtDelete.setOnClickListener(new ViewOnClickListener());

        api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, WBConstants.APP_KEY);
        mWeiboShareAPI.registerApp();

        mTencent = Tencent.createInstance(ConstantsUtil.APP_ID_TX,
                this.getApplicationContext());
        baseUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                System.out.print("QQ分享成功");
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
    }

    private void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("id",articleId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");

        new XUtilsNew().httpPost("article/queryArticle.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                System.out.print("queryArticle.html responseInfo="+responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    Type type = new TypeToken<Riding_NewDatailModel>() {}.getType();
                    model = gson.fromJson(object
                            .toString(), type);

                    adapter = new Riding_NewDetailAdapter(model,Riding_NewDetailActivity.this,aMap);
                    mListView.setAdapter(adapter);
                    setView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getApplicationContext(), "无法连接服务器，请检查网络", 0);
            }
        });

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                mlocationClient.onDestroy();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                Toasts.show(getApplicationContext(),errText,0);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    class ViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_NewRidingDetailActivity_back:
                    finish();
                    break;

                case R.id.img_NewRidingDetailActivity_attention:
                    if (preferences.getInt("userId", -1) != -1) {
                        if(isClection){// 已收藏
                            cancelCollect();
                        }else{// 未收藏
                            collected();
                        }
                    } else {
                        new SystemUtil().makeToast(getApplicationContext(), "请登录");
                        startActivity(new Intent(Riding_NewDetailActivity.this,
                                LoginActivity.class));
                        Riding_NewDetailActivity.this.finish();
                    }

                    break;

                case R.id.img_NewRidingDetailActivity_share:
                    // 分享
                    share(v);
                    break;

                case R.id.img_newRidingDeail_comment:
                    // 评论
                    if (preferences.getInt("userId", -1) != -1) {
                        Intent intent = new Intent(Riding_NewDetailActivity.this, Riding_NewCommentListActivity.class);
                        intent.putExtra("articleId", articleId);
                        intent.putExtra("flag", 0);
                        startActivity(intent);
                    } else {
                        new SystemUtil().makeToast(Riding_NewDetailActivity.this, "请登录");
                        startActivity(new Intent(Riding_NewDetailActivity.this,
                                LoginActivity.class));
                        Riding_NewDetailActivity.this.finish();
                    }
                    break;

                case R.id.img_newRidingDeail_reward:
                    // 打赏
                    if (preferences.getInt("userId", -1) != -1) {
                        reward(v);
                    } else {
                        new SystemUtil().makeToast(Riding_NewDetailActivity.this, "请登录");
                        startActivity(new Intent(Riding_NewDetailActivity.this,
                                LoginActivity.class));
                        Riding_NewDetailActivity.this.finish();
                    }

                    break;

                case R.id.img_newRidingDeail_like:
                    // 点赞
                    if (preferences.getInt("userId", -1) != -1) {
                        if (isPraise) {
                            praiseOrCancel(2);
                        } else {
                            praiseOrCancel(1);
                        }
                    } else {
                        new SystemUtil().makeToast(Riding_NewDetailActivity.this, "请登录");
                        startActivity(new Intent(Riding_NewDetailActivity.this,
                                LoginActivity.class));
                        Riding_NewDetailActivity.this.finish();
                    }


                    break;

                case R.id.txt_NewRidingDetailActivity_delete:
                    deleteTipPopup();
                    break;

            }
        }

    }

    public class FooderViewOnClickListener implements View.OnClickListener{

        Intent intent = new Intent(Riding_NewDetailActivity.this, Activity_NewDetailActivity.class);
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_activity1:
                    intent.putExtra("activityId", model.getActivities().get(0).getId());
                    intent.putExtra("activityTitle", model.getActivities().get(0).getTitle());
                    intent.putExtra("activityFlag", false);
                    startActivity(intent);
                    break;

                case R.id.layout_activity2:
                    intent.putExtra("activityId", model.getActivities().get(1).getId());
                    intent.putExtra("activityTitle", model.getActivities().get(1).getTitle());
                    intent.putExtra("activityFlag", false);
                    startActivity(intent);

                    break;
            }
        }
    }

    /**
     * 点赞 或 取消点赞
     * @parms tag 1:点赞，2：取消点赞
     */
    boolean isPraise = false;
    private void praiseOrCancel(final int tag) {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("id",articleId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null)+"");
        params.addQueryStringParameter("tag",tag+"");

        new XUtilsNew().httpPost("article/praiseArticle.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    if(tag == 1){
                        isPraise = true;
                        imgLike.setImageResource(R.drawable.like_chosen);
                        txtLike.setText(object.getInt("data")+"");

                        numTxt.setVisibility(View.VISIBLE);
                        numTxt.startAnimation(animation);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                numTxt.setVisibility(View.GONE);
                            }
                        }, 1000);

                    }else{
                        isPraise = false;
                        imgLike.setImageResource(R.drawable.like_unchosen);
                        txtLike.setText(object.getInt("data")+"");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getApplicationContext(), "无法连接服务器，请检查网络", 0);
            }
        });
    }



    /**
     * 打赏弹窗
     *
     * @param view
     * popup所依附的布局
     */
    private void reward(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.item_popup_grade, null);

        popup_mark0 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark0);
        popup_mark1 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark1);
        popup_mark2 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark2);
        popup_mark3 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark3);
        popup_mark4 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark4);
        popup_mark5 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark5);
        popup_mark6 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark6);
        popup_mark7 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark7);
        popup_mark8 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark8);
        popup_mark9 = (ImageView) contentView
                .findViewById(R.id.img_gradePopup_mark9);
        popup_ok = (TextView) contentView.findViewById(R.id.txt_gradePopup_ok);
        markPointTxt = (TextView) contentView
                .findViewById(R.id.txt_gradePopup_markPoint);
        popup_cancel = (TextView) contentView
                .findViewById(R.id.txt_gradePopup_cancel);
        selectMark();

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        popup_mark0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                markPointInt = 1;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                markPointInt = 2;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                markPointInt = 3;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                markPointInt = 4;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                markPointInt = 5;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                markPointInt = 6;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                markPointInt = 7;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                popup_mark7.setImageResource(R.drawable.award_yellow);
                markPointInt = 8;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                popup_mark7.setImageResource(R.drawable.award_yellow);
                popup_mark8.setImageResource(R.drawable.award_yellow);
                markPointInt = 9;
                markPointTxt.setText(markPointInt + "分");
            }
        });
        popup_mark9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectMark();
                popup_mark0.setImageResource(R.drawable.award_yellow);
                popup_mark1.setImageResource(R.drawable.award_yellow);
                popup_mark2.setImageResource(R.drawable.award_yellow);
                popup_mark3.setImageResource(R.drawable.award_yellow);
                popup_mark4.setImageResource(R.drawable.award_yellow);
                popup_mark5.setImageResource(R.drawable.award_yellow);
                popup_mark6.setImageResource(R.drawable.award_yellow);
                popup_mark7.setImageResource(R.drawable.award_yellow);
                popup_mark8.setImageResource(R.drawable.award_yellow);
                popup_mark9.setImageResource(R.drawable.award_yellow);
                markPointInt = 10;
                markPointTxt.setText(markPointInt + "分");
            }
        });

        popup_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                rewardIntegral(markPointInt);
                popupWindow.dismiss();
            }
        });

        popup_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new SystemUtil()
                        .makeToast(Riding_NewDetailActivity.this, "您未打赏积分");
                popupWindow.dismiss();
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 50);

    }

    /**
     * 设置所有评分图片暗色
     */
    public void selectMark() {
        popup_mark0.setImageResource(R.drawable.award_gray);
        popup_mark1.setImageResource(R.drawable.award_gray);
        popup_mark2.setImageResource(R.drawable.award_gray);
        popup_mark3.setImageResource(R.drawable.award_gray);
        popup_mark4.setImageResource(R.drawable.award_gray);
        popup_mark5.setImageResource(R.drawable.award_gray);
        popup_mark6.setImageResource(R.drawable.award_gray);
        popup_mark7.setImageResource(R.drawable.award_gray);
        popup_mark8.setImageResource(R.drawable.award_gray);
        popup_mark9.setImageResource(R.drawable.award_gray);
        markPointInt = 0;
    }

    /**
     *
     */
    public void rewardIntegral(int point){

        if(point<1 || point>10){
            return;
        }
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("id",articleId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));
        params.addQueryStringParameter("grade",point+"");

        new XUtilsNew().httpPost("article/rewardArticle.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                // 游记打赏分数
                MobclickAgent.onEvent(Riding_NewDetailActivity.this, new UmengEventUtil().click66);

                Toasts.show(getApplicationContext(),"打赏成功",0);
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getApplicationContext(), "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 收藏
     */
    // 点赞状态
    boolean isClection = false;
    private void collected(){
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("artId",articleId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));



        new XUtilsNew().httpPost("article/collected.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(getApplicationContext(),"已收藏该游记",0);
                imgAttention.setImageResource(R.drawable.clection_chosen);
                isClection = true;
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getApplicationContext(), "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 取消收藏
     */
    private void cancelCollect(){
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("artId",articleId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("article/cancelCollect.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(getApplicationContext(),"已取消对该游记的收藏",0);
                imgAttention.setImageResource(R.drawable.cllection_unchosen);
                isClection = false;
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getApplicationContext(), "无法连接服务器，请检查网络", 0);
            }
        });
    }


    /**
     * 打赏弹窗
     *
     * @param
     */
    private void showReward(String inType) {
        int integral = -1;
        int praiseNum = -1;
        int sumIntegral = -1;
        String userName = null;
        String title = null;


        // 一个自定义的布局，作为显示的内容
        View v = LayoutInflater.from(this).inflate(R.layout.item_popup_jpush, null);
        markPointTxt = (TextView) v.findViewById(R.id.txt_JpushPopup_message);
        popup_cancel = (TextView) v.findViewById(R.id.txt_JpushPopup_cancel);
        popup_yes = (TextView) v.findViewById(R.id.txt_JpushPopup_ok);
        LinearLayout quxiao = (LinearLayout) v.findViewById(R.id.quxiao);
        final PopupWindow popupWindow = new PopupWindow(v,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        if (inType.equals("QYJDZ")) {
            praiseNum = getIntent().getIntExtra("praiseNum",-1);
            title = getIntent().getStringExtra("title");
            userName = getIntent().getStringExtra("userName");

            markPointTxt.setText(Html.fromHtml("骑友 " + "<font color='#6dbfed'>"
                    + userName + "</font>" + " 给您的游记《"
                    + "<font color='#6dbfed'>" + title + "</font>"
                    + "》点了赞哟!当前被点赞量为" + "<font color='#ff0000'>" + praiseNum
                    + "</font>"));
        } else if (inType.equals("QYJDS")) {

            sumIntegral = getIntent().getIntExtra("sumIntegral",-1);
            title = getIntent().getStringExtra("title");
            integral = getIntent().getIntExtra("integral",-1);
            userName = getIntent().getStringExtra("userName");

            markPointTxt.setText(Html.fromHtml("骑友 " + "<font color='#6dbfed'>"
                    + userName + "</font>" + " 觉得您的游记《"
                    + "<font color='#6dbfed'>" + title + "</font>"
                    + "》写得不错哟!给您打赏了" + "<font color='#ff0000'>" + integral
                    + "</font>" + ",你现在的总积分是" + "<font color='#ff0000'>"
                    + sumIntegral + "</font>" + "分"));
        }

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Riding_NewDetailActivity.this.inType = "";
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Riding_NewDetailActivity.this.inType = "";
            }
        });
        popup_cancel.setVisibility(View.GONE);
        popup_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
                Riding_NewDetailActivity.this.inType = "";
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(Riding_NewDetailActivity.this
                        .findViewById(R.id.layout_ridingNewDetailsActivity),
                Gravity.CENTER, 0, 50);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
//        mBigMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        SoundPlayer.pause();
        SoundPlayer.realese();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
        deactivate();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
//        mBigMapView.onSaveInstanceState(outState);
    }



    /**
     *  分享
     * @param view
     */
    private void share(View view) {

        // 一个自定义的布局，作为显示的内容
        View mview = LayoutInflater.from(this).inflate(R.layout.share, null);

        LinearLayout qq = (LinearLayout) mview.findViewById(R.id.qq);
        LinearLayout wx = (LinearLayout) mview.findViewById(R.id.wx);
        LinearLayout pyq = (LinearLayout) mview.findViewById(R.id.pyq);
        LinearLayout wb = (LinearLayout) mview.findViewById(R.id.wb);
        LinearLayout qx = (LinearLayout) mview.findViewById(R.id.qx);

        final PopupWindow popupWindow = new PopupWindow(mview,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onClickQQShare();
                popupWindow.dismiss();

                // 提交分享
                MobclickAgent.onEvent(Riding_NewDetailActivity.this, new UmengEventUtil().click64);
            }
        });

        wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onClickWXShare();
                popupWindow.dismiss();

                // 提交分享
                MobclickAgent.onEvent(Riding_NewDetailActivity.this, new UmengEventUtil().click64);
            }
        });
        pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onClickWXPYQShare();
                popupWindow.dismiss();

                // 提交分享
                MobclickAgent.onEvent(Riding_NewDetailActivity.this, new UmengEventUtil().click64);
            }
        });
        wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_32);

                WBShareUtil wbShareUtil = new WBShareUtil(Riding_NewDetailActivity.this);
                wbShareUtil.shareToWB(mWeiboShareAPI,bitmap,
                        model.getArticle().getTitle(),
                       "http://weride.com.cn/weride/attendRider/queryAttendRiderInfoPhone.html?id=" + model.getArticle().getId(),
                        "每一篇游记都是骑友分享的美好骑行时光，让幸福传递下去吧！");
                popupWindow.dismiss();

                // 提交分享
                MobclickAgent.onEvent(Riding_NewDetailActivity.this, new UmengEventUtil().click64);
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 50);

    }


    /**
     * 分享到QQ与QQ空间
     */
    private void onClickQQShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, model.getArticle()
                .getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,
               "http://weride.com.cn/weride/attendRider/queryAttendRiderInfoPhone.html?id="
                        + model.getArticle().getId());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, SystemUtil.IMGPHTH
                + model.getArticle().getDefaultImage());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, SystemUtil.IMGPHTH
                + model.getArticle().getDefaultImage());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "骑来骑去");


        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, model.getArticle()
                .getTitle());// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "每一篇游记都是骑友分享的美好骑行时光，让幸福传递下去吧！");// 选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                "http://weride.com.cn/weride/attendRider/queryAttendRiderInfoPhone.html?id="
                        + model.getArticle().getId());// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, SystemUtil.IMGPHTH
                + model.getArticle().getDefaultImage());
        mTencent.shareToQQ(Riding_NewDetailActivity.this, params, baseUiListener);
    }


    /**
     * 分享到微信
     */
    private void onClickWXShare() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://weride.com.cn/weride/attendRider/queryAttendRiderInfoPhone.html?id="
                + model.getArticle().getId();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = model.getArticle().getTitle();
        msg.description = "每一篇游记都是骑友分享的美好骑行时光，让幸福传递下去吧！";

        try {
            Bitmap bmp = SystemUtil.compressImageFromFile(SystemUtil.IMGPHTH
                    + model.getArticle().getDefaultImage(), 300);
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.qizhi_app);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp1, 100, 100, true);
            bmp.recycle();
            msg.thumbData = Bitmap2Bytes(thumbBmp);
            // msg.setThumbImage(thumbBmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("图文链接");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    // 分享到微信朋友圈
    private void onClickWXPYQShare() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://weride.com.cn/weride/attendRider/queryAttendRiderInfoPhone.html?id="
                + model.getArticle().getId();

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = model.getArticle().getTitle();
        msg.description = "每一篇游记都是骑友分享的美好骑行时光，让幸福传递下去吧！";

        try {
            Bitmap bmp = SystemUtil.compressImageFromFile(SystemUtil.IMGPHTH
                    + model.getArticle().getDefaultImage(), 300);
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.qizhi_app);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp1, 100, 100, true);
            bmp.recycle();
            msg.thumbData = Bitmap2Bytes(thumbBmp);
            // msg.setThumbImage(thumbBmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("图文链接");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 构造一个用于请求的唯一标识
     *
     * @param type 分享的内容类型
     * @return
     */
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 删除提醒
     */
    private void deleteTipPopup(){
        // 一个自定义的布局，作为显示的内容
        View view = LayoutInflater.from(this).inflate(R.layout.item_popup_jpush, null);

        TextView content = (TextView) view.findViewById(R.id.txt_JpushPopup_message);
        TextView ok = (TextView) view.findViewById(R.id.txt_JpushPopup_ok);
        TextView cancel = (TextView) view.findViewById(R.id.txt_JpushPopup_cancel);

        content.setText("您确定要删除这篇游记吗？被删除的游记将不能恢复！");
        ok.setTextColor(getResources().getColor(R.color.red));

        final PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();

                delateArticle();

            }

        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(Riding_NewDetailActivity.this
                        .findViewById(R.id.list_newRidingDeail_list),
                Gravity.CENTER, 0, 50);

    }

    private void delateArticle() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("id",articleId+"");
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("article/deleteArticle.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(getApplicationContext(), "游记已删除", 0);
                finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Riding_NewDetailActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }


//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//    }

    private float moveY;
    private int height;
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            moveY = getScrollY();
//            if(!ReleaseActivityfalg){
//                if(moveY == 0){
//                    layoutTitle.setBackgroundResource(R.drawable.background_shade);
//                }else if(moveY/(ImageHeight - height) < 1){
//                    layoutTitle.setBackgroundColor(0xff6dbfed);
//                    layoutTitle.getBackground().setAlpha((int) (moveY/(ImageHeight - height) * 255));
//                }else{
//                    layoutTitle.setBackgroundColor(0xff6dbfed);
//                }
//            }else{
//                if(moveY == 0){
//                    layoutTitle.setBackgroundResource(R.drawable.background_shade);
//                }else if(moveY/(Previewadapter.height - height) < 1){
//                    layoutTitle.setBackgroundColor(0xff6dbfed);
//                    layoutTitle.getBackground().setAlpha((int) (moveY/(Previewadapter.height - height) * 255));
//                }else{
//                    layoutTitle.setBackgroundColor(0xff6dbfed);
//                }
//            }
//    }
//
//    public int getScrollY() {
//        View c = mListView.getChildAt(0);
//        if (c == null) {
//            return 0;
//        }
//        int firstVisiblePosition = mListView.getFirstVisiblePosition();
//        int top = c.getTop();
//        return -top + firstVisiblePosition * c.getHeight() ;
//    }


}
