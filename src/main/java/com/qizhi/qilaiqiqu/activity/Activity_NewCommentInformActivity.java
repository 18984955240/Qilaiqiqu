package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
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
 *  活动点评信息
 */
public class Activity_NewCommentInformActivity extends Activity {

    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;
    @InjectView(R.id.img_photo)
    CircleImageViewUtil imgPhoto;
    @InjectView(R.id.txt_title)
    TextView txtTitle;
    @InjectView(R.id.txt_comment)
    TextView txtComment;
    @InjectView(R.id.txt_seeAll)
    TextView txtSeeAll;

    private int integral;
    private int activityId;

    private String title;
    private String comment;
    private String userName;
    private String userImage;
    private String activityImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_comment_inform);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        integral = getIntent().getIntExtra("integral",-1);
        activityId = getIntent().getIntExtra("activityId",-1);

        title = getIntent().getStringExtra("title");
        comment = getIntent().getStringExtra("comment");
        userName = getIntent().getStringExtra("userName");
        userImage = getIntent().getStringExtra("userImage");
        activityImage = getIntent().getStringExtra("activityImage").split(",")[0];


        SystemUtil.Imagexutils_photo(userImage,imgPhoto,this);
        txtComment.setText(comment);
        txtTitle.setText(Html.fromHtml("<font color='#6dbfed'>"+userName+"</font>打赏了你的活动-<font color='#6dbfed'>" + title + "</font>"+integral+"个积分"));
    }

    @OnClick({R.id.layout_back, R.id.txt_seeAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                finish();
                break;

            case R.id.txt_seeAll:
                startActivity(new Intent(this,Activity_NewCommentListActivity.class).putExtra("title",title).putExtra("activityImage",activityImage).putExtra("activityId",activityId));
                break;
        }
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
}
