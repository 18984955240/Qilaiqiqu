package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  陪骑通知
 */
public class Rider_NewComemntResultActivity extends Activity {

    @InjectView(R.id.Inform_back)
    LinearLayout InformBack;
    @InjectView(R.id.Inform_photo)
    CircleImageViewUtil InformPhoto;
    @InjectView(R.id.Inform_content)
    TextView InformContent;
    @InjectView(R.id.Inform_message)
    TextView InformMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_inform);
        ButterKnife.inject(this);

        initView();

    }

    @OnClick(R.id.Inform_back)
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Inform_back:
                finish();
                break;
        }
    }

    int integral;
    String userImage;
    String userName;
    String commmet;

    public void initView() {
        integral = getIntent().getIntExtra("integral",-1);
        commmet = getIntent().getStringExtra("commmet");
        userName = getIntent().getStringExtra("userName");
        userImage = getIntent().getStringExtra("userImage");

        SystemUtil.Imagexutils_photo(userImage,InformPhoto,Rider_NewComemntResultActivity.this);
        InformContent.setText(Html.fromHtml("<font color=\"#6dbfed\">"+userName+"</font>打赏了你的陪骑"+integral+"个积分"));
        InformMessage.setText(commmet);
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
}
