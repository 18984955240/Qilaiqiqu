package com.qizhi.qilaiqiqu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Riding_NewDatailModel;
import com.qizhi.qilaiqiqu.utils.AMapUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 *	游记地图
 */
public class Riding_NewMapActivity extends MyBaseActivity implements LocationSource, AMapLocationListener {

    MapView mMapView = null;

    // 高德地图
    private AMap aMap;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private Riding_NewDatailModel model;

    private List<Marker> markerList;
    private List<String> latitudeList;
    private List<String> longitudeList;
    private List<LatLonPoint> latLonPointList;

    private List<Riding_NewDatailModel.DetailsBean.ListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding__new_map);
        init();
        initView();

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
    }

    private void init() {
        model = (Riding_NewDatailModel) getIntent().getSerializableExtra("model");
        mList = new ArrayList<Riding_NewDatailModel.DetailsBean.ListBean>();
        // 遍历然后重组需要的数据

        markerList = new ArrayList<Marker>();
        latitudeList = new ArrayList<String>();
        longitudeList = new ArrayList<String>();
        latLonPointList = new ArrayList<LatLonPoint>();

        if (null != model.getDetails()) {
            for (int i = 0; i < model.getDetails().size(); i++) {
                for (int j = 0; j < model.getDetails().get(i).getList().size(); j++) {
                    mList.add(model.getDetails().get(i).getList().get(j));
                }
            }
        }
    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.NewMapActivity_map);


        initMapView();
    }

    private void initMapView() {

        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.getUiSettings().setCompassEnabled(true);
            aMap.getUiSettings().setZoomControlsEnabled(false);


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
        setMarkerAndPolyLine();
    }

    private void setMarkerAndPolyLine() {

        latitudeList.clear();
        longitudeList.clear();
        latLonPointList.clear();
        // 遍历是否有经纬度
        for(int i=0;i<mList.size();i++){
            if(!"".equals(mList.get(i).getImageInglat())){
                String[] Inglat =mList.get(i).getImageInglat().split(",");
                longitudeList.add(Inglat[0]);
                latitudeList.add(Inglat[1]);
            }
        }

        System.out.println("mList.size() = "+mList.size());
        System.out.println("latitudeList.size() = "+latitudeList.size());
        System.out.println("longitudeList.size() = "+longitudeList.size());

        // 添加所有的经纬度点
        for(int i=0;i<longitudeList.size();i++){

            System.out.println("for longitudeList.size() = "+longitudeList.size());
            System.out.println("for latLonPointList.size() = "+latLonPointList.size());

            System.out.println("for latitudeList.get(i) = "+latitudeList.get(i));
            System.out.println("for latitudeList.get(i) = "+latitudeList.get(i));

            LatLonPoint mlatLonPoint = new LatLonPoint(Double.parseDouble(latitudeList.get(i)),Double.parseDouble(longitudeList.get(i)));
            latLonPointList.add(mlatLonPoint);
        }

        System.out.println("latLonPointList.size() = "+latLonPointList.size());

        // 添加所有的marker
        markerList.clear();
        for(int i = 0; i < latLonPointList.size();i++){

            if(null != latitudeList.get(i) && null != longitudeList.get(i)){
                markerList.add(aMap.addMarker(new MarkerOptions().anchor(0.5f,1.0f)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.strat_map))));

                markerList.get(markerList.size() - 1).setPosition(
                        AMapUtil.convertToLatLng(latLonPointList.get(i)));
            }
        }

        // 给marker连线
        if (markerList.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(10f);
            polylineOptions.color(Color.RED);
            for(Marker marker : markerList){
                polylineOptions.add(marker.getPosition());
            }
            aMap.addPolyline(polylineOptions);
        }
        // 显示所有Marker
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(LatLonPoint latLonPoint : latLonPointList){
            builder.include(AMapUtil.convertToLatLng(latLonPoint));
        }
        LatLngBounds bounds = builder.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
                Toasts.show(Riding_NewMapActivity.this,errText,0);
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

}
