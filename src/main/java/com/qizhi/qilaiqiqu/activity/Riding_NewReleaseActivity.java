package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.RidingReleaseAdapter;
import com.qizhi.qilaiqiqu.model.RidingContentModel;
import com.qizhi.qilaiqiqu.model.Riding_NewDatailModel;
import com.qizhi.qilaiqiqu.utils.LoadingDialog;
import com.qizhi.qilaiqiqu.utils.SoundPlayer;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.soundcloud.android.crop.Crop;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

import static android.R.attr.key;
import static com.qizhi.qilaiqiqu.R.id.btn_newReleaseRiding_add;
import static com.qizhi.qilaiqiqu.utils.SystemUtil.checkSDcard;

/**
 *	游记发布
 */
public class Riding_NewReleaseActivity extends MyBaseActivity implements LocationSource, AMapLocationListener {

    @InjectView(R.id.list_newReleaseRiding_ridingItem)
    ListView ridingIist;
    @InjectView(btn_newReleaseRiding_add)
    Button btnAdd;
    @InjectView(R.id.btn_newReleaseRiding_preview)
    Button btnPreview;
    @InjectView(R.id.btn_newReleaseRiding_release)
    Button btnRelease;
    @InjectView(R.id.btn_newReleaseRiding_keep)
    Button btnKeep;
    @InjectView(R.id.img_ReleaseRidingActivity_back)
    ImageView imgBack;
    @InjectView(R.id.Img_ReleaseRidingActivity_reChoose)
    ImageView imgReChoose;

    @InjectView(R.id.layout_newReleaseRiding_release)
    RelativeLayout layoutRelease;
    @InjectView(R.id.layout_newReleaseRiding_preview)
    RelativeLayout layoutPreview;
    @InjectView(R.id.layout_newReleaseRiding_keep)
    RelativeLayout layoutKeep;

    MapView mMapView;
    TextView txtTitle;
    ImageView imgCover;


    ImageView imgUserImg;
    TagFlowLayout idTag;

    public static Riding_NewReleaseActivity INSTANCE;

    //
    private RidingReleaseAdapter adapter;


    private RidingContentModel ridingContentModel;
    private ArrayList<LatLonPoint> latLonPointList = new ArrayList<LatLonPoint>();
    // 游记信息
    public List<RidingContentModel> list;
    // 七牛上传完成后游记集合
    public List<RidingContentModel> listResult;
    public List<String> listVoice = new ArrayList<String>();
    public List<String> listPicture = new ArrayList<String>();

    // 第一次初始化app
    private SharedPreferences firstInit;
    // 用户信息
    private SharedPreferences preferences;

    // 图片信息集合
    private String label;
    private String title;
    private boolean isRechooseCover = false;
//    private List<PhotoInfo> mCoverPhotoList;
    private String coverPath = "";


    // 录音弹窗
    private PopupWindow popupWindow;

    // 草稿
    private boolean draft;
    private int articleId;
    private Riding_NewDatailModel model;
    private String draftState = "publish";

    // 封面是否需要上传
    private boolean isCoverPublish = true;

    // 七牛
    private UploadManager uploadManager;
    public static String uptoken = "";
    private String timeStamp;

    // 高德地图
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private Marker geoMarker;
    private Marker regeoMarker;

    int voiceFlag = 0;

    // gif图
    private LoadingDialog loadingDialog;

    public static int voiceNumber = 0;
    int pictureFlag = 0;
    int publishNum = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    System.out.println("pictureFlag="+pictureFlag);
                    System.out.println("publishNum="+publishNum);

                    // 上传的数量是否与本地选择的数量相同
                    if(pictureFlag == publishNum){
                        upLoadData();
                    }
                    break;

                case 2:
                    System.out.println("voiceFlag="+voiceFlag);
                    System.out.println("voiceNumber="+voiceNumber);

                    // 上传的数量是否与本地选择的数量相同
                    if(voiceFlag == voiceNumber){
                        upLoadPic();
                    }
                    break;

            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_release);
        ButterKnife.inject(this);
        INSTANCE = this;
        initHeader();
        init();
        mMapView.onCreate(savedInstanceState);// 此方法必须重写

    }

    private void init() {
        uploadManager = new UploadManager();
        loadingDialog = new LoadingDialog(this);

        draft = getIntent().getBooleanExtra("draft",false);
        articleId = getIntent().getIntExtra("articleId",-1);

        firstInit = getSharedPreferences("firstInit", Context.MODE_PRIVATE);
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        if(draft && articleId != -1){
            isCoverPublish = false;
            model = new Riding_NewDatailModel();
            getArticleData();
            layoutPreview.setVisibility(View.GONE);
        }else{
            initView();
//            initGalleryFinal();
//            initRxGalleryFinal();
            startActivityForResult(new Intent(Riding_NewReleaseActivity.this,NativeImagesActivity.class).putExtra("articleFalg",true),3);
            layoutPreview.setVisibility(View.VISIBLE);
        }
    }


    private void initView() {

        label = getIntent().getStringExtra("label");
        title = getIntent().getStringExtra("title");
        coverPath = getIntent().getStringExtra("coverPath");

        // 设置头像
        SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgUserImg, this);

        txtTitle.setText(title);
        Glide.with(this).load(coverPath).into(imgCover);
        idTag.setAdapter(new TagAdapter(label.split(",")) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(Riding_NewReleaseActivity.this).inflate(R.layout.tag_item_activive2,null);
                textView.setText(o.toString());
                return textView;
            }
        });



    }


    private void initHeader() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_new_release_riding_header, null);

        idTag = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);
        txtTitle = (TextView) view.findViewById(R.id.txt_ReleaseRidingActivity_title);
        imgUserImg = (ImageView) view.findViewById(R.id.img_ReleaseRidingActivity_photo);
        imgCover = (ImageView) view.findViewById(R.id.img_ReleaseRidingActivity_headerImg);

        // 初始化地图
        mMapView = (MapView) view.findViewById(R.id.ReleaseRidingActivity_map);
        initMap();

        ridingIist.addHeaderView(view);
        list = new ArrayList<RidingContentModel>();
        listResult = new ArrayList<RidingContentModel>();
        adapter = new RidingReleaseAdapter(this,list,aMap,latLonPointList,popupWindow);
        ridingIist.setAdapter(adapter);

        // 设置在操作地图时listView事件不触发
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    ridingIist.requestDisallowInterceptTouchEvent(false);
                }else {
                    ridingIist.requestDisallowInterceptTouchEvent(true);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgReChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechooseCover();
            }
        });

    }



    private void rechooseCover() {
//        Crop.pickImage(this);

        // 调用android的图库
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, Crop.REQUEST_PICK);
    }


    /**
     * 创建文件夹
     */
    private static String createFile(){
        String filePath = null;
        if(checkSDcard()){
            filePath = "/sdcard/Qilaiqiqu/Crop";
        } else {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Qilaiqiqu/Crop";
        }

        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }

        return filePath;
    }

    private void beginCrop(Uri source) {
        System.out.println("source="+source);
        String s = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        Uri destination = Uri.fromFile(new File(createFile()+ "/" + s +"_crop.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) { // 裁剪成功
            File file = null;
            try {
                file = new File(new URI(Crop.getOutput(result).toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            isCoverPublish = true;
            coverPath = file.getPath();
            Glide.with(Riding_NewReleaseActivity.this).load(coverPath).into(imgCover);

        } else if (resultCode == Crop.RESULT_ERROR) { // 裁剪失败
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.getUiSettings().setCompassEnabled(true);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听

        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 设置默认缩放按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 设置默认缩放按钮是否显示
        aMap.getUiSettings().setCompassEnabled(false);


//        // 禁止缩放手势
//        aMap.getUiSettings().setZoomGesturesEnabled(false);
//        // 禁止平移（滑动）手势
//        aMap.getUiSettings().setTiltGesturesEnabled(false);
//        // 禁止旋转手势(3D)
//        aMap.getUiSettings().setRotateGesturesEnabled(false);
//        // 禁止倾斜手势(3D)
//        aMap.getUiSettings().setScrollGesturesEnabled(false);


        // logo位置
        aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);


        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                .fromResource(R.drawable.));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        //设置圆形区域（以定位位置为圆心，定位半径的圆形区域）的填充颜色。
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();

        SoundPlayer.pause();
        SoundPlayer.realese();

        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                deactivate();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                Toasts.show(Riding_NewReleaseActivity.this,errText,0);
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



    @butterknife.OnClick({btn_newReleaseRiding_add,R.id.btn_newReleaseRiding_release,R.id.btn_newReleaseRiding_preview,R.id.btn_newReleaseRiding_keep})
    public void onClick(View view) {
        switch (view.getId()) {
            case btn_newReleaseRiding_add:

                startActivityForResult(new Intent(Riding_NewReleaseActivity.this,NativeImagesActivity.class).putExtra("articleFalg",true),3);
                break;

            case R.id.btn_newReleaseRiding_release:
                if(listResult.size() == 0){
                    listResult.addAll(list);
                }

                loadingDialog.show();

                draftState = "publish";

                for (int i=0;i<list.size();i++){
                    if(list.get(i).getState().equals("publish")){
                        publishNum++;
                    }
                }

                upLoadCover();
                break;

            case R.id.btn_newReleaseRiding_preview:
                preview();
                break;

            case R.id.btn_newReleaseRiding_keep:
                if(listResult.size() == 0){
                    listResult.addAll(list);
                }

                loadingDialog.show();

                draftState = "draft";

                for (int i=0;i<list.size();i++){
                    if(list.get(i).getState().equals("publish")){
                        publishNum++;
                    }
                }

                upLoadCover();
                break;
        }
    }

    private void preview() {
        startActivity(new Intent(Riding_NewReleaseActivity.this,Riding_NewPreviewActivity.class)
                .putExtra("title",title)
                .putExtra("label",label)
                .putExtra("cover",coverPath)
                .putExtra("list",(Serializable) list));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult.resultCode="+resultCode);
        System.out.println("onActivityResult.requestCode="+requestCode);

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        }
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    break;

                case 2:
                    int n = data.getIntExtra("position", -1);
                    if (n != -1) {
                        // 设置地图返回的地址和经纬度
                        list.get(n).setPosition(data.getStringExtra("address"));
                        list.get(n).setLatitude(data.getDoubleExtra("latitude",0.0));
                        list.get(n).setLongitude(data.getDoubleExtra("longitude",0.0));
                    }
                    adapter.notifyDataSetChanged();

                    // 标注地理位置
                    MobclickAgent.onEvent(Riding_NewReleaseActivity.this,"click21");

                    break;

                case 3:
                    List<RidingContentModel> lists1 = (List<RidingContentModel>) data.getSerializableExtra("list");
                    ArrayList<LatLonPoint> lists2 = data.getParcelableArrayListExtra("latLonPointList");

                    System.out.println("List<RidingContentModel> lists1.size()="+lists1.size());
                    System.out.println("ArrayList<LatLonPoint> lists2.size()="+lists2.size());

                    list.addAll(lists1);
                    latLonPointList.addAll(lists2);

                    adapter.notifyDataSetChanged();
                    ridingIist.smoothScrollToPosition(adapter.getCount() - 1);
                    break;

            }
        }

    }


    /**
     * 七牛文件上传
     */

    // 封面
    private String defaultImage;

    private void upLoadCover(){

        System.out.println("upLoadCover() isCoverPublish="+isCoverPublish);
        if(isCoverPublish){
            if("".equals(uptoken)){
                getQiniuToken(0);
                return;
            }
            String s = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());



            final String upkey = "Android_" + s + ".jpg";
            uploadManager.put(coverPath, upkey, uptoken, new UpCompletionHandler() {
                public void complete(String key, ResponseInfo rinfo, JSONObject response) {


                    System.out.println("upLoadCover() rinfo.isOK()="+rinfo.isOK());

                    if(rinfo.isOK()){
                        defaultImage = key;
                        if(voiceNumber != 0){
                            upLoadVoice();
                        }else{
                            upLoadPic();
                        }
                    }else {
                        errorFlag++;
                        getQiniuToken(0);
                    }
                }
            }, new UploadOptions(null, "test-type", true, null, null));
        }else{
            defaultImage = model.getArticle().getDefaultImage();
            if(voiceNumber != 0){
                upLoadVoice();
            }else{
                upLoadPic();
            }
        }

    }

    private void upLoadPic(){
        if(list.size() == 0){
            loadingDialog.dismiss();
            Toasts.show(Riding_NewReleaseActivity.this, "请选择游记图片", 0);
        }

        for(int i = 0; i<list.size();i++){
            if(list.get(i).getState().equals("publish")){
                if("".equals(uptoken)){
                    getQiniuToken(2);
                    return;
                }
                final int position = i;
                String s = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                final String upkey = "Android_" + s +Integer.toString(i) + "_"+list.get(i).getWidth()+"_"+list.get(i).getHeight()+".jpg";
                //设置上传后文件的key
                uploadManager.put(list.get(i).getPicture(), upkey, uptoken, new UpCompletionHandler() {
                    public void complete(String key, ResponseInfo rinfo, JSONObject response) {

                        String s = key + ", "+ rinfo + ", " + response;

                        System.out.println("upLoadPic() rinfo.isOK()="+rinfo.isOK());

                        if(rinfo.isOK()){

                            Message message = handler.obtainMessage();
                            message.obj = key;
                            message.what = 1;
                            message.arg1 = position;
                            handler.sendMessage(message);
                            pictureFlag++;

                            listResult.get(position).setPicture(key);

                        }else {
                            errorFlag++;
                            getQiniuToken(2);
                        }
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            }else{
                Message message = handler.obtainMessage();
                message.obj = key;
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    }

    private void upLoadVoice(){
        if(list.size() != 0){
            for(int i = 0; i<list.size();i++){

                System.out.println("upLoadVoice() list.get(i).getState()="+ list.get(i).getState());

                if(list.get(i).getState().equals("publish")){

                    System.out.println("upLoadVoice() uptoken="+ uptoken);
                    if("".equals(uptoken)){
                        getQiniuToken(1);
                        return;
                    }

                    System.out.println("upLoadVoice() list.get(i).getVoice()="+ list.get(i).getVoice());
                    if(!"".equals(list.get(i).getVoice())){
                        final int position = i;
                        String s = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                        final String upkey = "Android_" + s +"_"+Integer.toString(i) + ".acc";
                        //设置上传后文件的key
                        uploadManager.put(list.get(i).getVoice(), upkey, uptoken, new UpCompletionHandler() {
                            public void complete(String key, ResponseInfo rinfo, JSONObject response) {

                                String s = key + ", "+ rinfo + ", " + response;

                                System.out.println("upLoadPic() rinfo.isOK()="+rinfo.isOK());

                                if(rinfo.isOK()){
                                    Message message = handler.obtainMessage();
                                    message.obj = key;
                                    message.what = 2;
                                    message.arg1 = position;
                                    handler.sendMessage(message);
                                    voiceFlag++;

                                    listResult.get(position).setVoice(key);
                                }else {
                                    errorFlag++;
                                    getQiniuToken(1);
                                }
                            }
                        }, new UploadOptions(null, "test-type", true, null, null));
                    }
                }else {
                    Message message = handler.obtainMessage();
                    message.obj = key;
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }

    }

    /**
     * @parms
     * 获取七牛云token
     */

    int errorFlag = 0;
    private void getQiniuToken(final int falg){
        if(errorFlag < 3){
            RequestParams params = new RequestParams("UTF-8");
            new XUtilsNew().httpPost("common/getAuthToken.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
                @Override
                public void onMySuccess(com.lidroid.xutils.http.ResponseInfo<String> responseInfo) {
                    try {
                        JSONObject object = new JSONObject(responseInfo.result);
                        uptoken = object.getString("data");

                        if(falg == 0) {
                            upLoadCover();
                        } else if(falg ==1){
                            upLoadVoice();
                        } else {
                            upLoadPic();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onMyFailure(HttpException error, String msg) {
                    System.out.println("getQiniuToken() onMyFailure");
                    loadingDialog.dismiss();
                    Toasts.show(Riding_NewReleaseActivity.this, "无法连接服务器，请检查网络", 0);
                }
            });
        }else{
            System.out.println("getQiniuToken() errorFlag >= 3");
            loadingDialog.dismiss();
        }

    }


    /**
     *
     * 上传其他文字信息
     */
    private void upLoadData(){
        System.out.println("Riding_NewReleaseActivity upLoadData——————正在上传文字");

        // 声明json数组
        JSONArray detail = new JSONArray();
        // 声明json对象
        JSONObject article = new JSONObject();

        for (int i = 0; i < listResult.size(); i ++)
        {
            JSONObject node = new JSONObject();
            try {
                node.put("voicePath", listResult.get(i).getVoice());
                node.put("imageDesc", listResult.get(i).getLegend());
                node.put("imageName", listResult.get(i).getPicture());
                node.put("context", listResult.get(i).getRidingTitle());
                node.put("imageAddress", listResult.get(i).getPosition());

                if(listResult.get(i).getLongitude() == 0.0 || listResult.get(i).getLatitude() == 0.0){
                    node.put("imageInglat", "");
                }else{
                    node.put("imageInglat", listResult.get(i).getLongitude()+","+listResult.get(i).getLatitude());
                }

                if(listResult.get(i).getTime().equals("")){
                    node.put("imageDate",listResult.get(i).getTime());
                }else{
                    node.put("imageDate", listResult.get(i).getTime().split(" ")[0].replaceAll(":","-"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            detail.put(node);
        }

        //把json数组加到json对象中
        try {
            article.put("detail",detail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        release(article);

    }

    public void release(JSONObject article){
        final RequestParams params = new RequestParams("UTF-8");
        if(draft){
            params.addBodyParameter("id",articleId+"");
        }else{
            params.addBodyParameter("id","");
        }
        params.addBodyParameter("title",txtTitle.getText().toString());
        params.addBodyParameter("defaultImage",defaultImage);
        params.addBodyParameter("labels",label);
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("userName",preferences.getString("userName",null));
        params.addBodyParameter("detail",article.toString());
        params.addBodyParameter("state", draftState);
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("article/publishArticle.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(com.lidroid.xutils.http.ResponseInfo<String> responseInfo) {

                // 不再需要打开游记发布引导页面
                SharedPreferences.Editor edit = firstInit.edit();
                edit.putBoolean("isFirst",true);
                edit.commit();

                if(draftState.equals("publish")){

                    // 完成发布游记
                    MobclickAgent.onEvent(Riding_NewReleaseActivity.this,"click22");

                    Toasts.show(Riding_NewReleaseActivity.this, "发布成功", 0);
                }else{

                    // 保存草稿
                    MobclickAgent.onEvent(Riding_NewReleaseActivity.this,"click23");

                    Toasts.show(Riding_NewReleaseActivity.this, "草稿已保存", 0);
                }

                loadingDialog.dismiss();
                finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

                System.out.println("params.getHeaders="+params.getHeaders());
                System.out.println("params.getQueryStringParams="+params.getQueryStringParams());

                Toasts.show(Riding_NewReleaseActivity.this, "无法连接服务器，请检查网络", 0);
                loadingDialog.dismiss();
            }
        });
    }

    public void getArticleData() {
        RequestParams params = new RequestParams("UTF-8");
        System.out.println("articleId="+articleId);
        System.out.println("preferences.getInt(\"userId\",-1)="+preferences.getInt("userId",-1));

        params.addQueryStringParameter("id",articleId+"");
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");

        new XUtilsNew().httpPost("article/queryArticle.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(com.lidroid.xutils.http.ResponseInfo<String> responseInfo) {

                System.out.print("queryArticle.html responseInfo="+responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    Type type = new TypeToken<Riding_NewDatailModel>() {}.getType();
                    model = gson.fromJson(object
                            .toString(), type);

                    setView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Riding_NewReleaseActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    private void setView() {

        txtTitle.setText(model.getArticle().getTitle());
        SystemUtil.Imagexutils_new(model.getArticle().getDefaultImage(),imgCover,this);
        SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgUserImg, this);

        label = model.getArticle().getLabels();
        idTag.setAdapter(new TagAdapter(label.split(",")) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(Riding_NewReleaseActivity.this).inflate(R.layout.tag_item_activive2,null);
                textView.setText(o.toString());
                return textView;
            }
        });

        System.out.println("model.getDetails().size()="+model.getDetails().size());

        for(int i=0; i<model.getDetails().size(); i++){
            ridingContentModel = new RidingContentModel();
            ridingContentModel.setTime(model.getDetails().get(i).getList().get(0).getImageTime());
            ridingContentModel.setPicture(model.getDetails().get(i).getList().get(0).getImageName());
            ridingContentModel.setPosition(model.getDetails().get(i).getList().get(0).getImageAddress());
            ridingContentModel.setState("draft");

            ridingContentModel.setLegend(model.getDetails().get(i).getList().get(0).getImageDesc());
            ridingContentModel.setRidingTitle(model.getDetails().get(i).getList().get(0).getContext());


            if(!"".equals(model.getDetails().get(i).getList().get(0).getImageInglat())){
                String[] Inglat =model.getDetails().get(i).getList().get(0).getImageInglat().split(",");
                if(!Inglat[1].equals("null") && Inglat[0].equals("null") && Inglat[1] != null && Inglat[0] != null){
                    ridingContentModel.setLatitude(Double.parseDouble(Inglat[1]));
                    ridingContentModel.setLongitude(Double.parseDouble(Inglat[0]));
                }
            }

            list.add(ridingContentModel);

        }
        adapter.notifyDataSetChanged();
        ridingIist.smoothScrollToPosition(adapter.getCount() - 1);
    }

}
