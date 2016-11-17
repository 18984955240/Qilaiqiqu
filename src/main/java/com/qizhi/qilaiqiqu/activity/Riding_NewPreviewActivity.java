package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.Riding_NewPreviewAdapter;
import com.qizhi.qilaiqiqu.model.RidingContentModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 *	游记预览
 */
public class Riding_NewPreviewActivity extends MyBaseActivity implements LocationSource, AMapLocationListener {


    private String title;
    private String label;
    private String cover;
    private List<RidingContentModel> list;

    private ListView mListView;
    private LinearLayout mlayoutBack;
    private Riding_NewPreviewAdapter mAdapter;

    // 高德地图
    private AMap aMap;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    // 头部View 控件
    private View mHeaderView;
    private ImageView imgCover;
    private ImageView imgPhoto;
    private TextView txtTitle;
    private MapView mMapView;
    private RelativeLayout layoutHeader;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding_new_preview);
        init();
        initView();
        initHeaderView();
        initEvent();
        setView();

        mMapView.onCreate(savedInstanceState);
    }

    public void init(){
        title = getIntent().getStringExtra("title");
        label = getIntent().getStringExtra("label");
        cover = getIntent().getStringExtra("cover");
        list = (List<RidingContentModel>) getIntent().getSerializableExtra("list");

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_preview);
        mlayoutBack = (LinearLayout) findViewById(R.id.layout_back);
    }

    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.activity_new_riding_details_header, null);

        mMapView = (MapView) mHeaderView.findViewById(R.id.NewRidingDetailActivity_map);
        txtTitle = (TextView) mHeaderView.findViewById(R.id.txt_NewRidingDetailActivity_title);
        imgPhoto = (ImageView) mHeaderView.findViewById(R.id.img_NewRidingDetailActivity_photo);
        imgCover = (ImageView) mHeaderView.findViewById(R.id.img_NewRidingDetailActivity_headerImg);
        layoutHeader = (RelativeLayout) mHeaderView.findViewById(R.id.layout_NewRidingDetailActivity_headerImg);

        initMapView();
    }

    private void initEvent(){
        mlayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setView() {
        txtTitle.setText(title);
        // 设置封面图片
        Glide.with(this).load(cover).into(imgCover);
        // 设置头像
        SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgPhoto, this);

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

        mAdapter = new Riding_NewPreviewAdapter(this,list,aMap);
        mListView.addHeaderView(mHeaderView);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
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
                Toasts.show(Riding_NewPreviewActivity.this,errText,0);
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




}
