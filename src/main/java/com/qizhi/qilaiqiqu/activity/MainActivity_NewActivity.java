package com.qizhi.qilaiqiqu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.fragment.ActivityFragment;
import com.qizhi.qilaiqiqu.fragment.NewsFragment;
import com.qizhi.qilaiqiqu.fragment.RiderFragment;
import com.qizhi.qilaiqiqu.fragment.RidingFragment;
import com.qizhi.qilaiqiqu.model.LocateState;
import com.qizhi.qilaiqiqu.receiver.EMReceiver;
import com.qizhi.qilaiqiqu.utils.ActivityCollectorUtil;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.StringUtils;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UpdateManager;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.soundcloud.android.crop.Crop;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.qizhi.qilaiqiqu.utils.SystemUtil.checkSDcard;


//    ┏┓　　　┏┓                      ┏┓　　　┏┓                       ┏┓　　　┏┓
//  ┏┛┻━━━┛┻┓                  ┏┛┻━━━┛┻┓                   ┏┛┻━━━┛┻┓ + + + +
//  ┃　　　　　　　┃                  ┃　　　　　　　┃                   ┃　　　　　　　┃
//  ┃　　　━　　　┃                  ┃　　　━　　　┃                   ┃　　　━　　　┃ + + + +
//  ┃　┳┛　┗┳　┃                  ┃　＞　　　＜　┃                 ████━████┃
//  ┃　　　　　　　┃                  ┃　　　　　　　┃                   ┃　　　　　　　┃ + + + +
//  ┃　　　┻　　　┃                  ┃ ...　⌒　... ┃                   ┃　　　┻　　　┃
//  ┗━┓　　　┏━┛                  ┗━┓　　　┏━┛                   ┗━┓　　　┏━┛+ + + +
//      ┃　　　┃                          ┃　　　┃　                         ┃　　　┃
//      ┃　　　┃                          ┃　　　┃                           ┃　　　┃ + + + +
//      ┃　　　┗━━━┓                  ┃　　　┗━━━┓                   ┃　 　 ┗━━━┓
//      ┃　　　　　　　┣┓                ┃　　　　　　　┣┓                 ┃ 　　　　　　 ┣┓ + + + +
//      ┃　　　　　　　┏┛                ┃　　　　　　　┏┛                 ┃ 　　　　　　 ┏┛
//      ┗┓┓┏━┳┓┏┛                  ┗┓┓┏━┳┓┏┛                   ┗┓┓┏━┳┓┏┛ + + + +
//        ┃┫┫　┃┫┫                      ┃┫┫　┃┫┫                       ┃┫┫　┃┫┫
//        ┗┻┛　┗┻┛                      ┗┻┛　┗┻┛                       ┗┻┛　┗┻┛+ + + +
//
//                            Code is far away from bug with the caonima protecting　
//                                           神兽保佑,代码无bug


/**
 *  主页
 */
public class MainActivity_NewActivity extends MyBaseActivity {

    @InjectView(R.id.id_content)
    FrameLayout idContent;
    @InjectView(R.id.layout_mainActivity_mian)
    LinearLayout layoutMian;
    @InjectView(R.id.layout_mainActivity_active)
    LinearLayout layoutActive;
    @InjectView(R.id.layout_mainActivity_attender)
    LinearLayout layoutAttender;
    @InjectView(R.id.layout_mainActivity_new)
    LinearLayout layoutNew;
    @InjectView(R.id.img_mainActivity_release)
    ImageView imgRelease;

    // 底部导航栏控件
    @InjectView(R.id.btn_img_main)
    ImageView btnImgMain;
    @InjectView(R.id.btn_txt_main)
    TextView btnTxtMain;
    @InjectView(R.id.btn_img_activity)
    ImageView btnImgActivity;
    @InjectView(R.id.btn_txt_activity)
    TextView btnTxtActivity;
    @InjectView(R.id.btn_img_attender)
    ImageView btnImgAttender;
    @InjectView(R.id.btn_txt_attender)
    TextView btnTxtAttender;
    @InjectView(R.id.btn_img_news)
    ImageView btnImgNews;
    @InjectView(R.id.btn_txt_news)
    TextView btnTxtNews;

    // 头部导航栏控件
    @InjectView(R.id.layout_title)
    LinearLayout layoutTitle;
    @InjectView(R.id.img_mainActivity_photo)
    CircleImageViewUtil imgPhoto;
    @InjectView(R.id.layout_mainActivity_search)
    LinearLayout layoutSearch;
    @InjectView(R.id.txt_mainActivity_location)
    TextView txtLocation;
    @InjectView(R.id.layout_mainActivity_location)
    LinearLayout layoutLocation;
    @InjectView(R.id.txt_mainActivity_title)
    TextView txtTitle;
    @InjectView(R.id.img_mainActivity_setting)
    ImageView imgSetting;
    @InjectView(R.id.txt_NewsFragment_msgPoint)
    TextView txtMsgPoint;

    // 内容fragment
    private Fragment ridingFragment;
    private Fragment activeFragment;
    private Fragment riderFragment;
    private Fragment newsFragment;

    public Set<String> chatUserList;
    public Set<String> groupChatUserList;

    private Gson gson;
    private Type type;

    private NewMessageBroadcastReceiver receiver;

    // 第一次初始化app
    private SharedPreferences firstInit;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    boolean isdialog = true;

    private AMapLocationClient mLocationClient;

    // 定位位置
    public String locatedCity = "北京";
    // 定位状态
    public int locateState = LocateState.LOCATING;

    // 屏幕实际宽度
    public int screenWidth;

    public static MainActivity_NewActivity INSTANCE;

    // 支付订单，用于微信支付验证订单
    public String orderId;

    // 版本更新管理类
    UpdateManager updateManager;

    public int remindFlag = 0;
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    logoutService();
                    break;

                case 2:

                    if(SystemUtil.canMakeSmores()) {
                        SystemUtil.verifyStoragePermissions(MainActivity_NewActivity.this);
                    }
                    break;

                case 3:
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                    timer.cancel();
                    Intent i = new Intent(MainActivity_NewActivity.this, Riding_NewCoverSelect.class);
                    startActivity(i);
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity__new);
        ButterKnife.inject(this);

        INSTANCE = this;

        // 计算屏幕实际宽度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
//        int height = metric.heightPixels;   // 屏幕高度（像素）
//        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        initView();
        // 初始城市按钮
        updateLocateState(locateState,locatedCity);
        setSelect(0);
        // 定位
        initLocation();
        //
        updateManager = new UpdateManager(this);
        updateManager.checkUpdateInfo();

    }

    private void initView() {
        chatUserList = new HashSet<String>();
        groupChatUserList = new HashSet<String>();
        firstInit = getSharedPreferences("firstInit", Context.MODE_PRIVATE);
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = preferences.edit();// 获取编辑器

        gson = new Gson();
        type = new TypeToken<HashSet<String>>() {
        }.getType();
        String chat = preferences.getString(
                "Chat" + preferences.getString("uniqueKey", null), null);
        if (chat != null) {
            chatUserList = gson.fromJson(chat, type);
        }
        String groupChat = preferences.getString(
                "GroupChat" + preferences.getString("uniqueKey", null), null);
        if (groupChat != null) {
            groupChatUserList = gson.fromJson(groupChat, type);
        }


        // 注册接收消息广播
        receiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager
                .getInstance().getNewMessageBroadcastAction());
        // 设置广播的优先级别大于Mainacitivity,这样如果消息来的时候正好在chat页面，直接显示消息，而不是提示消息未读
        intentFilter.setPriority(3);
        registerReceiver(receiver, intentFilter);
        EMChat.getInstance().setAppInited();

        startTime();
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        Log.e("onLocationChanged", "city: " + city);
                        Log.e("onLocationChanged", "district: " + district);
                        String location = StringUtils.extractLocation(city, district);
                        updateLocateState(LocateState.SUCCESS, location);
                    } else {
                        //定位失败
                        updateLocateState(LocateState.FAILED, "");
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }


    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        locatedCity = city;
        locateState = state;
        switch (locateState){
            case LocateState.LOCATING:
                txtLocation.setText(this.getString(R.string.locating));
                break;
            case LocateState.FAILED:
                txtLocation.setText(R.string.located_failed);
                break;
            case LocateState.SUCCESS:
                txtLocation.setText(city);
                RidingFragment.INSTANCE.txtLocation.setText(city);
                // 所在地
                editor.putString("location",city);
                editor.commit();
                break;
        }
    }

    int time = 1000;
    private Timer timer;
    private TimerTask task;
    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
//                if(remindFlag == 1){
                    stopTime();
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
//                }else{
//                    startTime();
//                }

            }
        };
        timer.schedule(task, time);
    }

    private void stopTime() {
        remindFlag = 0;
        timer.cancel();
    }

    /**
     * 选择Fragment
     *
     * @param i
     */
    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (ridingFragment == null) {
                    ridingFragment = new RidingFragment();
                    transaction.add(R.id.id_content, ridingFragment);
                } else {
                    transaction.show(ridingFragment);
                }
                btnTxtMain.setTextColor(getResources().getColor(R.color.mainActivity_title));
                btnImgMain.setImageResource(R.drawable.main_homepage_press);
                break;
            case 1:
                if (activeFragment == null) {
                    activeFragment = new ActivityFragment(true);
                    transaction.add(R.id.id_content, activeFragment);
                } else {
                    transaction.show(activeFragment);

                }
                btnTxtActivity.setTextColor(getResources().getColor(R.color.mainActivity_title));
                btnImgActivity.setImageResource(R.drawable.main_activity_press);
                break;
            case 2:
                if (riderFragment == null) {
                    riderFragment = new RiderFragment();
                    transaction.add(R.id.id_content, riderFragment);
                } else {
                    transaction.show(riderFragment);
                }
                btnTxtAttender.setTextColor(getResources().getColor(R.color.mainActivity_title));
                btnImgAttender.setImageResource(R.drawable.main_attender_press);
                break;
            case 3:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.id_content, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                btnTxtNews.setTextColor(getResources().getColor(R.color.mainActivity_title));
                btnImgNews.setImageResource(R.drawable.main_news_press);
                break;

            default:
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (ridingFragment != null) {
            transaction.hide(ridingFragment);
        }
        if (activeFragment != null) {
            transaction.hide(activeFragment);
        }
        if (riderFragment != null) {
            transaction.hide(riderFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
    }

    @OnClick({R.id.layout_mainActivity_mian, R.id.layout_mainActivity_active, R.id.layout_mainActivity_attender,
            R.id.layout_mainActivity_new, R.id.img_mainActivity_release,R.id.img_mainActivity_photo,
            R.id.layout_mainActivity_search, R.id.layout_mainActivity_location,R.id.img_mainActivity_setting})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.layout_mainActivity_mian:
                resetImgs();
                setSelect(0);

                layoutTitle.setVisibility(View.GONE);
                layoutSearch.setVisibility(View.VISIBLE);
                layoutLocation.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                imgSetting.setVisibility(View.GONE);
                break;

            case R.id.layout_mainActivity_active:
                resetImgs();
                setSelect(1);

                // 活动Tab
                MobclickAgent.onEvent(MainActivity_NewActivity.this,"click4");

                layoutTitle.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.VISIBLE);
                layoutLocation.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                imgSetting.setVisibility(View.GONE);
                break;

            case R.id.layout_mainActivity_attender:
                resetImgs();
                setSelect(2);

                // 达人Tab
                MobclickAgent.onEvent(MainActivity_NewActivity.this,"click5");

                layoutTitle.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.VISIBLE);
                layoutLocation.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                imgSetting.setVisibility(View.GONE);
                break;

            case R.id.layout_mainActivity_new:
                if(preferences.getInt("userId",-1) != -1){
                    resetImgs();
                    setSelect(3);

                    // 消息Tab
                    MobclickAgent.onEvent(MainActivity_NewActivity.this,"click6");

                    layoutTitle.setVisibility(View.VISIBLE);
                    layoutSearch.setVisibility(View.GONE);
                    layoutLocation.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    imgSetting.setVisibility(View.VISIBLE);
                }else{
                    Toasts.show(MainActivity_NewActivity.this, "请先登录", 0);
                    Intent intent1 = new Intent(MainActivity_NewActivity.this,
                            LoginActivity.class);
                    startActivity(intent1);
                    MainActivity_NewActivity.this.finish();
                }

                break;

            case R.id.img_mainActivity_setting:
                Intent intent = new Intent(MainActivity_NewActivity.this, SetActivity.class);
                startActivity(intent);
                break;

            case R.id.img_mainActivity_release:

                System.out.println("preferences.getInt(\"userId\", -1)="+preferences.getInt("userId", -1));
                if (preferences.getInt("userId", -1) != -1) {

                    // 发布
                    MobclickAgent.onEvent(MainActivity_NewActivity.this,"click15");
//                    initGalleryFinal();
//                    initRxGalleryFinal();
                    relaese(view);
//                    relaeseArticle(view);

                } else {
                    Toasts.show(MainActivity_NewActivity.this, "请登录", 0);
                    Intent intent3 = new Intent(MainActivity_NewActivity.this, LoginActivity.class);
                    startActivity(intent3);
                    MainActivity_NewActivity.this.finish();
                    // finish();
                }

                break;

            case R.id.img_mainActivity_photo:

                if (preferences.getInt("userId", -1) != -1) {
                    startActivity(new Intent(MainActivity_NewActivity.this,PersonalCenterActivity.class).putExtra("userId",preferences.getInt("userId", -1)));
                } else {
                    Toasts.show(MainActivity_NewActivity.this, "请登录", 0);
                    Intent intent1 = new Intent(MainActivity_NewActivity.this,
                            LoginActivity.class);
                    startActivity(intent1);
                    MainActivity_NewActivity.this.finish();
                }
                break;

            case R.id.layout_mainActivity_search:
                startActivity(new Intent(MainActivity_NewActivity.this,SearchActivity.class));
                break;

            case R.id.layout_mainActivity_location:
                startActivityForResult(new Intent(MainActivity_NewActivity.this,CityPickerActivity.class).putExtra("location_city",locatedCity),0);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //data为B中回传的Intent
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);

        }else{
            switch (resultCode) {
                case RESULT_OK:
                    locatedCity = data.getExtras().getString("picked_city");
                    locateState = data.getExtras().getInt("locate_state");

                    updateLocateState(locateState,locatedCity);
                    break;
                default:



                    break;
            }
        }


    }

    /**
     * 切换图片至暗色
     */
    private void resetImgs() {
        btnTxtMain.setTextColor(getResources().getColor(R.color.gray_4d));
        btnTxtNews.setTextColor(getResources().getColor(R.color.gray_4d));
        btnTxtAttender.setTextColor(getResources().getColor(R.color.gray_4d));
        btnTxtActivity.setTextColor(getResources().getColor(R.color.gray_4d));

        btnImgNews.setImageResource(R.drawable.main_news);
        btnImgMain.setImageResource(R.drawable.main_homepage);
        btnImgActivity.setImageResource(R.drawable.main_activity);
        btnImgAttender.setImageResource(R.drawable.main_attender);
    }


    /**
     * 发布弹窗
     * @param view
     */
    private PopupWindow popupWindow;

    private void relaese(View view) {

        // 一个自定义的布局，作为显示的内容
        View mview = LayoutInflater.from(this).inflate(
                R.layout.item_popup_release, null);

        TextView youjiTxt = (TextView) mview
                .findViewById(R.id.txt_releasePopup_youji);
        TextView activeTxt = (TextView) mview
                .findViewById(R.id.txt_releasePopup_active);
        TextView cancelTxt = (TextView) mview
                .findViewById(R.id.txt_releasePopup_cancel);

        LinearLayout quxiao = (LinearLayout) mview.findViewById(R.id.quxiao);

        popupWindow = new PopupWindow(mview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);

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

        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        youjiTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(SystemUtil.canMakeSmores()) {
                    SystemUtil.verifyStoragePermissions(MainActivity_NewActivity.this);
                }

                if (preferences.getInt("userId", -1) != -1) {

                    // 发布骑游记
                    MobclickAgent.onEvent(MainActivity_NewActivity.this,"click16");


                    if(firstInit.getBoolean("isFirst",false)){
                        // 调用android的图库
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/*");
                        startActivityForResult(i, Crop.REQUEST_PICK);
                    }else {
                        startActivity(new Intent(MainActivity_NewActivity.this,Riding_NewGuideActivity.class));
                    }
                } else {
                    Toasts.show(MainActivity_NewActivity.this, "请登录", 0);
                    Intent intent = new Intent(MainActivity_NewActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    MainActivity_NewActivity.this.finish();
                    // finish();
                }
                popupWindow.dismiss();
            }
        });

        activeTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(SystemUtil.canMakeSmores()) {
                    SystemUtil.verifyStoragePermissions(MainActivity_NewActivity.this);
                }

                if (preferences.getInt("userId", -1) != -1) {

                    // 发布活动
                    MobclickAgent.onEvent(MainActivity_NewActivity.this,"click17");

                    // 活动发布
                    // 提示用户到网页端发布
                    String url = "http://weride.com.cn/weride/mobile/activity/queryActivity.html";
                    startActivity(new Intent(MainActivity_NewActivity.this, WebActivity.class).putExtra("URL",url));

                } else {
                    Toasts.show(MainActivity_NewActivity.this, "请登录", 0);
                    Intent intent = new Intent(MainActivity_NewActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    MainActivity_NewActivity.this.finish();
                    // finish();
                }
                popupWindow.dismiss();
            }
        });

        cancelTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, Gravity.BOTTOM);



    }


    /**
     * 创建文件夹`
     */
    private String createFile(){
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

            Intent intent = new Intent(MainActivity_NewActivity.this, Riding_NewCoverSelect.class);
            intent.putExtra("photoPath", file.getPath());
            startActivity(intent);

        } else if (resultCode == Crop.RESULT_ERROR) { // 裁剪失败
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popupWindow != null && popupWindow.isShowing()) {
//                popupWindow.dismiss();
//                popupWindow = null;
                return false;
            }
            exitBy2Click(); // 调用双击退出函数
        }

        return false;
    }

    /**
     * 双击退出
     */
    private Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toasts.show(this, "再按一次退出程序", 0);
            // Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onStart() {
        if (preferences.getInt("userId", -1) != -1) {
            loginHuanXin();
        }
        headPortrait();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        getUnreadMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        ActivityCollectorUtil.removeActivity(this);
        unregisterReceiver(receiver);
    }

    /**
     * 设置头像
     */
    private void headPortrait() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
//        isNews();
        if (preferences.getInt("userId", -1) != -1) {
            SystemUtil.Imagexutils_photo(preferences.getString("userImage", null), imgPhoto, MainActivity_NewActivity.this);

        } else {
            imgPhoto.setImageResource(R.drawable.user_default);
//            dotView.setVisibility(View.GONE);
        }

    }


    private void loginHuanXin() {

        // final Editor userInfo_Editor = sp.edit();
        // 登录环信
        EMChatManager.getInstance().login(
                preferences.getString("imUserName", null),
                preferences.getString("imPassword", null), new EMCallBack() {

                    @Override
                    public void onSuccess() {

                        EMGroupManager.getInstance().loadAllGroups();

                        Log.d("main", "环信登录成功！");
                        System.out.println("环信登录成功！");

                        // 注册一个监听连接状态的listener
                        EMChatManager.getInstance().addConnectionListener(
                                new MyConnectionListener());
                        initEMSDK();

                    }

                    @Override
                    public void onProgress(int code, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "环信登录失败！" + message);
                        System.out.println("环信登录失败！" + message);
                    }
                });
    }

    private void initEMSDK() {
        // 只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
        EMReceiver msgReceiver = new EMReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager
                .getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);

        EMChatManager.getInstance().getChatOptions().setRequireAck(true);
        // 如果用到已读的回执需要把这个flag设置成true

        IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
                .getInstance().getAckMessageBroadcastAction());
        ackMessageIntentFilter.setPriority(3);
        registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

        EMChat.getInstance().setAppInited();
    }

    // 注册接收ack回执消息的BroadcastReceiver
    private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();
            String msgid = intent.getStringExtra("msgid");
            String from = intent.getStringExtra("from");
            EMConversation conversation = EMChatManager.getInstance()
                    .getConversation(from);
            if (conversation != null) {
                // 把message设为已读
                EMMessage msg = conversation.getMessage(msgid);
                if (msg != null) {
                    msg.isAcked = true;
                }
            }

        }
    };

    // 实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            // 已连接到服务器
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        Toasts.show(MainActivity_NewActivity.this, "帐号已经被移除", 0);
						/*
						 * new SystemUtil() .makeToast(adsd.this, "帐号已经被移除");
						 */
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登陆
                        Toasts.show(MainActivity_NewActivity.this, isdialog + "", 0);
                        if (isdialog) {

                            logOut();
                            logoutQQ();
                            isdialog = false;
                        }
                    } else {
                        if (NetUtils.hasNetwork(MainActivity_NewActivity.this)) {
                            // 连接不到聊天服务器
                            Toasts.show(MainActivity_NewActivity.this, "连接不到聊天服务器", 0);
							/*
							 * new SystemUtil().makeToast(adsd.this,
							 * "连接不到聊天服务器");
							 */
                        } else {
                            // 当前网络不可用，请检查网络设置
                            Toasts.show(MainActivity_NewActivity.this, "当前网络不可用，请检查网络设置", 0);
							/*
							 * new SystemUtil().makeToast(adsd.this,
							 * "当前网络不可用，请检查网络设置");
							 */
                        }
                    }
                }
            });
        }
    }

    private void logOut() {
        EMChatManager.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d("LOGOUT", "环信登出成功!");
                System.err.println("环信登出成功!");

                SharedPreferences sp = getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor userInfo_Editor = sp.edit();
                userInfo_Editor.putBoolean("isLogin", false);
                userInfo_Editor.commit();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("LOGOUT", "环信登出失败!" + message);
                System.err.println("环信登出失败!" + message);
            }
        });
    }
    private Tencent mTencent;
    public void logoutQQ() {
        mTencent = Tencent.createInstance(ConstantsUtil.APP_ID_TX,
                this.getApplicationContext());
        mTencent.logout(this);
    }

    private void logoutService() {

        String url = "mobile/push/releaseToken.html";
        RequestParams params = new RequestParams();

        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("driver", "android");

        new XUtilsNew().httpPost("user/layout.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                try {

                    JSONObject jsonObject = new JSONObject(result);

                        editor.putInt("userId", -1);
                        editor.putInt("riderId", -1);
                        editor.putString("location", null);
                        editor.putString("userImage", null);
                        editor.putString("uniqueKey", null);
                        editor.putString("imPassword", null);
                        editor.putString("imUserName", null);
                        editor.putString("mobilePhone", null);
                        editor.putString("riderId", null);
                        editor.putString("userType", null);
                        editor.commit();

                        // 实例化Intent
                        Intent intent = new Intent();
                        // 设置Intent的action属性
                        intent.setAction("com.qizhi.qilaiqiqu.receiver.LogoutReceiver");
                        // 发出广播
                        sendBroadcast(intent);
                        isdialog = true;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(MainActivity_NewActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    /**
     * 消息广播接收者
     *
     */
    private class NewMessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 记得把广播给终结掉
            abortBroadcast();
            // String from = intent.getStringExtra("from");
            String msgid = intent.getStringExtra("msgid");
            EMMessage message = EMChatManager.getInstance().getMessage(msgid);

            if (message.getChatType() == EMMessage.ChatType.Chat) {
                chatUserList.add(message.getFrom());
            } else if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                groupChatUserList.add(message.getTo());
            }
            String chat = gson.toJson(chatUserList);
            String groupChat = gson.toJson(groupChatUserList);

            // 消息不是发给当前会话，return
            notifyNewMessage(message);

            SharedPreferences sharedPreferences = getSharedPreferences(
                    "userLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();// 获取编辑器
            editor.putString(
                    "Chat" + sharedPreferences.getString("uniqueKey", null),
                    chat);
            editor.putString(
                    "GroupChat"
                            + sharedPreferences.getString("uniqueKey", null),
                    groupChat);
            editor.commit();

            try {
                message.getStringAttribute("IMUserNameExpand");
            } catch (EaseMobException e) {
                e.printStackTrace();
            }
            return;

        }
    }

    /**
     * 获取是否有未读消息
     */
    private void getUnreadMessage() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        new XUtilsNew().httpPost("message/queryMessageState.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object  = new JSONObject(responseInfo.result);
                    if(object.getBoolean("state")){
                        txtMsgPoint.setVisibility(View.VISIBLE);
                    }else{
                        txtMsgPoint.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });
    }

}
