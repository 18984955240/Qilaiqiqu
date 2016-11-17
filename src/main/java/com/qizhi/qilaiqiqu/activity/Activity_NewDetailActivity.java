package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.AccessTokenKeeper;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.WBConstants;
import com.qizhi.qilaiqiqu.utils.WBShareUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  活动详情
 */
public class Activity_NewDetailActivity extends Activity implements IWeiboHandler.Response {

    @InjectView(R.id.layout_NewActiveDetail_back)
    LinearLayout layoutlBack;
    @InjectView(R.id.img_NewActiveDetail_share)
    ImageView imgShare;
    @InjectView(R.id.txt_NewActiveDetail_revamp)
    TextView txtRevamp;
    @InjectView(R.id.img_NewActiveDetail_cllection)
    ImageView imgCllection;
    @InjectView(R.id.txt_NewActiveDetail_delete)
    TextView txtDelete;
    @InjectView(R.id.NewActiveDetail_web)
    WebView web;
    @InjectView(R.id.txt_NewActiveDetail_consult)
    LinearLayout layoutConsult;
    @InjectView(R.id.txt_NewActiveDetail_price)
    TextView txtPrice;
    @InjectView(R.id.txt_NewActiveDetail_button)
    TextView txtButton;

    public static int userId;
    public static int activityId;
    public static String activityTitle;
    private String orderId;

    // 活动信息
    private Double price;
    private Double totalPrice;
    private int number;
    private String url;
    private boolean state;
    private String title;
    private int slxz;
    private String photo;
    private String priceDesc;
    private String descript;

    private SharedPreferences preferences;

    // 微博分享接口实例
    private IWeiboShareAPI mWeiboShareAPI = null;

    // 腾讯分享接口实例
    private Tencent mTencent;
    private IUiListener baseUiListener; // 监听器

    // 微信分享实例
    private IWXAPI api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_detail);
        ButterKnife.inject(this);

        // 查看活动详情
        MobclickAgent.onEvent(Activity_NewDetailActivity.this,"click26");

        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }

    private void init(Bundle savedInstanceState) {
        activityId = getIntent().getIntExtra("activityId",-1);
        activityTitle = getIntent().getStringExtra("activityTitle");
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        userId = preferences.getInt("userId",-1);

        api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, WBConstants.APP_KEY);
        mWeiboShareAPI.registerApp();
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }

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

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId",activityId+"");
        params.addBodyParameter("userId",userId+"");

        new XUtilsNew().httpPost("activity/querySignState.html", params, false, Activity_NewDetailActivity.this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    slxz = object.getInt("slxz");
                    number = object.getInt("total");
                    price = object.getDouble("price");
//                    totalPrice = object.getDouble("totalPrice");

                    state = object.getBoolean("state");

                    url = object.getString("url");
                    title = object.getString("title");
                    photo = object.getString("photo");
                    orderId = object.getString("orderId");
                    priceDesc = object.getString("priceDesc");
                    descript = object.getString("descript");

                    iniView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewDetailActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    private void iniView() {
        //支持javascript
        web.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        web.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        web.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadWithOverviewMode(true);
        // 加载需要显示的网页
        web.loadUrl(url);
        // 设置Web视图
        web.setWebViewClient(new HelloWebViewClient());

        setView();
    }

    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void setView() {
        if(state){
            txtButton.setText("待支付");
        }else{
            txtButton.setText("我要参加");
        }

        if(price == 0){
            txtPrice.setText("免费");
        }else{
            txtPrice.setText(price+"￥");
        }

    }

    @OnClick({R.id.layout_NewActiveDetail_back, R.id.txt_NewActiveDetail_consult, R.id.txt_NewActiveDetail_button, R.id.img_NewActiveDetail_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_NewActiveDetail_back:
                finish();
                break;

            case R.id.txt_NewActiveDetail_consult:
                if (userId != -1) {
                    // 活动咨询
                    MobclickAgent.onEvent(Activity_NewDetailActivity.this, new UmengEventUtil().click31);

                    startActivity(new Intent(Activity_NewDetailActivity.this,Activity_NewConsultActivity.class).putExtra("activityId",activityId));
                } else {
                    Toasts.show(Activity_NewDetailActivity.this, "请登录", 0);
                    Intent intent1 = new Intent(Activity_NewDetailActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    Activity_NewDetailActivity.this.finish();
                }
                break;

            case R.id.txt_NewActiveDetail_button:
                if("我要参加".equals(txtButton.getText().toString())){
                    if (userId != -1) {
                        startActivity(new Intent(Activity_NewDetailActivity.this,Activity_NewApplyActivity.class)
                                .putExtra("slxz",slxz)
                                .putExtra("price",price)
                                .putExtra("priceDesc",priceDesc)
                                .putExtra("activityId",activityId)
                                .putExtra("activityTitle",activityTitle));
                    } else {
                        Toasts.show(Activity_NewDetailActivity.this, "请登录", 0);
                        Intent intent1 = new Intent(Activity_NewDetailActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        Activity_NewDetailActivity.this.finish();
                    }

                }else{
                    if (userId != -1) {
                        startActivity(new Intent(Activity_NewDetailActivity.this,Activity_NewOrderActivity.class)
                                .putExtra("inWay","dzf")
                                .putExtra("orderId",orderId));

                    } else {
                        Toasts.show(Activity_NewDetailActivity.this, "请登录", 0);
                        Intent intent1 = new Intent(Activity_NewDetailActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        Activity_NewDetailActivity.this.finish();
                    }

                }
                break;

            case R.id.img_NewActiveDetail_share:
                showShare(view);



                break;

        }
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(web.canGoBack()){
                web.goBack();
            }else {
                finish();
            }
            return true;
        }
        return false;
    }

    /**
     * 活动分享
     * @param view
     */
    private void showShare(View view) {

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

                // 活动分享
                MobclickAgent.onEvent(Activity_NewDetailActivity.this, new UmengEventUtil().click61);
            }
        });

        wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onClickWXShare();
                popupWindow.dismiss();

                // 活动分享
                MobclickAgent.onEvent(Activity_NewDetailActivity.this, new UmengEventUtil().click61);
            }
        });
        pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onClickWXPYQShare();
                popupWindow.dismiss();

                // 活动分享
                MobclickAgent.onEvent(Activity_NewDetailActivity.this, new UmengEventUtil().click61);
            }
        });
        wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_32);

                WBShareUtil wbShareUtil = new WBShareUtil(Activity_NewDetailActivity.this);
                wbShareUtil.shareToWB(mWeiboShareAPI,bitmap,
                        title,
                        XUtilsNew.URL+"activity/activityShow.html?id=" + activityId,
                        "骑来骑去——让骑行更精彩");
                popupWindow.dismiss();

                // 活动分享
                MobclickAgent.onEvent(Activity_NewDetailActivity.this, new UmengEventUtil().click61);
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_layout));
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
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "http://weride.com.cn/weride/mobile/activity/activityShowShare.html?id" + activityId);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, SystemUtil.IMGPHTH_NEW + photo);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, SystemUtil.IMGPHTH_NEW + photo);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "骑来骑去");

        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, title);// 选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                "http://weride.com.cn/weride/mobile/activity/activityShowShare.html?id"
                        + activityId);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, SystemUtil.IMGPHTH_NEW
                + photo);
        mTencent.shareToQQ(Activity_NewDetailActivity.this, params, baseUiListener);
    }


    /**
     * 分享到微信
     */
    private void onClickWXShare() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://weride.com.cn/weride/mobile/activity/activityShowShare.html?id" + activityId;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
//        msg.description = model.getActivitys().getActivityMemo();

        try {
            Bitmap bmp = SystemUtil.compressImageFromFile(SystemUtil.IMGPHTH_NEW
                    + photo, 300);
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
        webpage.webpageUrl = "http://weride.com.cn/weride/mobile/activity/activityShowShare.html?id" + activityId;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
//        msg.description = model.getActivitys().getActivityMemo();

        try {
            Bitmap bmp = SystemUtil.compressImageFromFile(SystemUtil.IMGPHTH_NEW
                    + photo, 300);
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
     *
     */
    private static final String TAG = WBShareUtil.class.getSimpleName();
    private void shareWEIBO() {
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 分享网页
        weiboMessage.mediaObject = getWebpageObj();
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        AuthInfo mAuthInfo = new AuthInfo(this, WBConstants.APP_KEY, WBConstants.REDIRECT_URL, WBConstants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mWeiboShareAPI.sendRequest(this, request, mAuthInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException( WeiboException e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete( Bundle bundle ) {
                Oauth2AccessToken mToken = Oauth2AccessToken.parseAccessToken(bundle);
                if ( mToken.isSessionValid() ) {
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(Activity_NewDetailActivity.this, newToken);
                    Toast.makeText(Activity_NewDetailActivity.this, "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
                } else {
                    // 以下几种情况，您会收到 Code：
                    // 1. 当您未在平台上注册的应用程序的包名与签名时；
                    // 2. 当您注册的应用程序包名与签名不正确时；
                    // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                    String code = bundle.getString("code");
                    String message = ".WEIBO_AUTH_FAILED";
                    if (!TextUtils.isEmpty(code)) {
                        Log.i(TAG, "msg = " + message + code);
                    }
                    Toast.makeText(Activity_NewDetailActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(Activity_NewDetailActivity.this, "微博分享取消",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = descript;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_32);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = "http://weride.com.cn/weride/mobile/activity/activityShowShare.html?id" + activityId;
        mediaObject.defaultText = "骑来骑去——让骑行更精彩";
        return mediaObject;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, Activity_NewDetailActivity.this);
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if(baseResp!= null){
            switch (baseResp.errCode) {
                case 0:
                    Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(this, "分享失败" + "Error Message: " + baseResp.errMsg, Toast.LENGTH_LONG).show();
                    break;
            }
        }
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

}
