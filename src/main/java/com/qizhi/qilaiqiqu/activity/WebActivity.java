package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.qizhi.qilaiqiqu.R;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *	WebActivity
 */
public class  WebActivity extends Activity {

    @InjectView(R.id.webActvity_web)
    WebView webView;
    @InjectView(R.id.img_advertisementActivity_back)
    ImageView imgAdvertisementActivityBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);
        intView();
    }

    String webURL = null;

    int ScreenHeight;
    int ScreenHeight2;

    private void intView() {

        webURL = getIntent().getStringExtra("URL");

//        ScreenHeight = getDpi(WebActivity.this) - (getBottomStatusHeight(WebActivity.this)*2);
//        ScreenHeight2 = getWindowManager().getDefaultDisplay().getHeight();
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//        webView.setLayoutParams(params);
//
//        System.out.println("getScreenHeight="+getWindowManager().getDefaultDisplay().getHeight());
//        System.out.println("getScreenHeight="+getScreenHeight(WebActivity.this));
//
//
//        System.out.println("getScreenHeight="+getScreenHeight(WebActivity.this));
//        System.out.println("getDpi="+getDpi(WebActivity.this));
//        System.out.println("getBottomStatusHeight="+getBottomStatusHeight(WebActivity.this));


        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
//        // 设置可以支持缩放
//        webView.getSettings().setSupportZoom(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 加载需要显示的网页
        webView.loadUrl(webURL);
        // 设置Web视图
        webView.setWebViewClient(new HelloWebViewClient());

    }

    @OnClick(R.id.img_advertisementActivity_back)
    public void onClick() {
        finish();
    }

    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }


//    /**
//     * 获取 虚拟按键的高度
//     * @param context
//     * @return
//     */
//    public static  int getBottomStatusHeight(Context context){
//        int totalHeight = getDpi(context);
//
//        int contentHeight = getScreenHeight(context);
//
//        return totalHeight  - contentHeight;
//    }
//
//    /**
//     * 获得屏幕高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getScreenHeight(Context context)
//    {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.heightPixels;
//    }

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
