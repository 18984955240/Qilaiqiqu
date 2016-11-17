package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  广告
 */
public class AdvertisementActivity extends Activity {

    @InjectView(R.id.img_advertisementActivity_startImage)
    ImageView startImage;
    @InjectView(R.id.txt_advertisementActivity_pass)
    TextView pass;
    @InjectView(R.id.layout_advertisementActivity_showDetails)
    LinearLayout showDetails;
    @InjectView(R.id.start_app_layout)
    LinearLayout startAppLayout;
    @InjectView(R.id.advertisementActivity_webview)
    WebView webView;
    @InjectView(R.id.img_advertisementActivity_back)
    ImageView back;
    @InjectView(R.id.layout_advertisementActivity_webview)
    RelativeLayout webviewLyaout;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                stopTime();
                startActivity(new Intent(AdvertisementActivity.this, MainActivity_NewActivity.class)
                        .putExtra("loginFlag", 1));

                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ButterKnife.inject(this);
        initView();
    }

    String webURL;
    String imageURL;
    private void initView() {

        webURL = getIntent().getStringExtra("webURL");
        imageURL = getIntent().getStringExtra("imageURL");
        Picasso.with(AdvertisementActivity.this).load(SystemUtil.IMGPHTH_NEW+imageURL).into(startImage);
        startTime();
    }

    /**
     * 启动handle
     */

    int time = 6000;
    private Timer timer;
    private TimerTask task;
    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, time);
    }

    private void stopTime() {
        timer.cancel();
    }


    @OnClick({R.id.txt_advertisementActivity_pass, R.id.layout_advertisementActivity_showDetails, R.id.img_advertisementActivity_back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_advertisementActivity_pass:
                stopTime();
                startActivity(new Intent(AdvertisementActivity.this, MainActivity_NewActivity.class)
                        .putExtra("loginFlag", 1));
                finish();
                break;

            case R.id.layout_advertisementActivity_showDetails:
                stopTime();
                webviewLyaout.setVisibility(View.VISIBLE);

                //支持javascript
                webView.getSettings().setJavaScriptEnabled(true);
                // 设置可以支持缩放
                webView.getSettings().setSupportZoom(true);
                // 设置出现缩放工具
                webView.getSettings().setBuiltInZoomControls(true);
                //扩大比例的缩放
                webView.getSettings().setUseWideViewPort(true);
                //自适应屏幕
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.getSettings().setLoadWithOverviewMode(true);
                // 加载需要显示的网页
                webView.loadUrl(webURL);
                // 设置Web视图
                webView.setWebViewClient(new HelloWebViewClient());
                break;

            case R.id.img_advertisementActivity_back:

                webView.goBack(); // goBack()表示返回WebView的上一页面

                break;
        }
    }


    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            startActivity(new Intent(AdvertisementActivity.this, MainActivity_NewActivity.class)
                    .putExtra("loginFlag", 1));
            finish();

            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
