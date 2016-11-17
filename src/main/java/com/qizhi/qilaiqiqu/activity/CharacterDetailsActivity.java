package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.LoadingDialog;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 人物志详情
 */
public class CharacterDetailsActivity extends MyBaseActivity {

    @InjectView(R.id.layout_characterback)
    LinearLayout layoutCharacterback;
    @InjectView(R.id.character_web)
    WebView characterWeb;
    @InjectView(R.id.comment)
    TextView comment;
    @InjectView(R.id.content)
    TextView content;

    int scannum;
    int comment_num;
    int characterId;

    String url;
    String createdate;
    String title;

    private SharedPreferences sharedPreferences;

    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        ButterKnife.inject(this);

        dialog = new LoadingDialog(this);
        dialog.show();


        scannum = getIntent().getIntExtra("scannum",-1);
        comment_num = getIntent().getIntExtra("comment_num",-1);
        characterId = getIntent().getIntExtra("characterId",-1);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        createdate = getIntent().getStringExtra("createdate");

        sharedPreferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        loadWeb();
    }

    private void loadWeb() {
        System.out.print("url="+url);


        //支持javascript
        characterWeb.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        characterWeb.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        characterWeb.getSettings().setBuiltInZoomControls(true);
        //将图片调整到适合webview的大小
        characterWeb.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        characterWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        characterWeb.getSettings().setLoadWithOverviewMode(true);
        // 加载需要显示的网页
        characterWeb.loadUrl(url);
        // 设置Web视图
        characterWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);   //在当前的webview中跳转到新的url

                return true;
            }

            // webView 加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
                super.onPageFinished(view, url);
            }
        });

    }


    @Override
    protected void onResume() {
        characterWeb.reload();  //刷新
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @OnClick({R.id.layout_characterback, R.id.content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_characterback:
                finish();
                break;
            case R.id.content:
                if (sharedPreferences.getInt("userId", -1) == -1) {
                    Toasts.show(this, "请登录", 0);
                    // new SystemUtil().makeToast(this, "请登录");
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;
                }

                System.out.println("title="+title);
                System.out.println("scannum="+scannum);
                System.out.println("createdate="+createdate);
                System.out.println("comment_num="+comment_num);
                System.out.println("characterId="+characterId);

                startActivity(new Intent(CharacterDetailsActivity.this,CharacterCommentActivity.class)
                        .putExtra("characterId",characterId)
                        .putExtra("scannum",scannum)
                        .putExtra("createdate",createdate)
                        .putExtra("comment_num",comment_num)
                        .putExtra("title",title)

                );
                break;
        }
    }

//    private void comnent() {
//
//        RequestParams params = new RequestParams("UTF-8");
//        params.addQueryStringParameter("characterId",characterId);
//        params.addQueryStringParameter("commentMemo", content.getText().toString());
//        params.addQueryStringParameter("userId",
//                sharedPreferences.getInt("userId", -1) + "");
//        params.addQueryStringParameter("uniqueKey",
//                sharedPreferences.getString("uniqueKey", null));
//
//        System.out.println("characterId="+characterId);
//        System.out.println("commentMemo="+content.getText().toString());
//        System.out.println("userId="+sharedPreferences.getInt("userId", -1));
//        System.out.println("uniqueKey="+sharedPreferences.getString("uniqueKey", null));
//
//        new XUtilsUtil().httpPost("common/addCharacterComment.html", params, new XUtilsUtil.CallBackPost() {
//            @Override
//            public void onMySuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//
//                System.out.println("responseInfo.result="+responseInfo.result);
//
//                try {
//                    JSONObject object = new JSONObject(result);
//                    if (object.getBoolean("result")) {
//
//                        Toasts.show(CharacterDetailsActivity.this, "评论成功", 0);
//                        content.setText("");
//                        characterWeb.reload();  //刷新
//                    } else {
//                        Toasts.show(CharacterDetailsActivity.this, object.getString("message"), 0);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onMyFailure(HttpException error, String msg) {
//                Toasts.show(CharacterDetailsActivity.this, "无法连接服务器，请检查网络", 0);
//            }
//        });
//
//
//    }


    // remove所有子view 解决webview的WindowLeaked问题
    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }


    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
        super.onPause();
    }

}
