package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.IMMLeaks;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 启动页
 */
public class StartActivity extends Activity {

    boolean isFirst = true;

    private String openUrl;
    private String openImage;

    // 第一次初始化app
//    private SharedPreferences firstInit;
//
//    public Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//
//                    stopTime();
//                    getData();
//                    break;
//
//                default:
//                    break;
//            }
//        }
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (isFirst) {
            getData();
        }

    }


//    int time = 1000;
//    private Timer timer;
//    private TimerTask task;
//
//    private void startTime() {
//        timer = new Timer();
//        task = new TimerTask() {
//
//            @Override
//            public void run() {
//                Message msg = new Message();
//                msg.what = 1;

//                handler.sendMessage(msg);
//            }
//        };
//        timer.schedule(task, time);
//    }
//
//    private void stopTime() {
//        timer.cancel();
//    }

    private void getData() {
        isFirst = false;
//        RequestParams params = new RequestParams(XUtilsNew.URL+"banner/openBanner.html");
//        x.http().get(params, new Callback.CacheCallback<String>() {
//            @Override
//            public boolean onCache(String result) {
//                System.out.println("x.http().get().onCache="+result);
//                return false;
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                System.out.println("x.http().get().onSuccess="+result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                System.out.println("x.http().get().onError="+ex.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                System.out.println("x.http().get().onCancelled");
//            }
//
//            @Override
//            public void onFinished() {
//                System.out.println("x.http().get().onFinished");
//            }
//        });



        new XUtilsNew().httpGet("banner/openBanner.html", false, this, new XUtilsNew.XUtilsCallBackGet() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
//                // 不再需要打开游记发布引导页面
//                SharedPreferences.Editor edit = firstInit.edit();
//                edit.putBoolean("isFirst",false);
//                edit.commit();

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    openUrl = object.getString("openUrl");
                    openImage = object.getString("openImage");


                    if ("".equals(openUrl)) {
                        startActivity(new Intent(StartActivity.this, MainActivity_NewActivity.class)
                                .putExtra("loginFlag", 1));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }else{
                        startActivity(new Intent(StartActivity.this, AdvertisementActivity.class)
                                .putExtra("webURL", openUrl)
                                .putExtra("imageURL", openImage));
                        // Activity切换效果 渐入渐出
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                startActivity(new Intent(StartActivity.this, MainActivity_NewActivity.class).putExtra("loginFlag", 1));
                finish();
            }
        });

    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return false;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMMLeaks.fixFocusedViewLeak(getApplication());
//        handler.removeCallbacksAndMessages(null);
    }
}
