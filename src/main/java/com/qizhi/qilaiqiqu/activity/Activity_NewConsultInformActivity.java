package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 咨询消息通知
 */
public class Activity_NewConsultInformActivity extends Activity {

    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;
    @InjectView(R.id.img_ridingComment_photo)
    CircleImageViewUtil imgRidingCommentPhoto;
    @InjectView(R.id.text_commentmessageactivity_message)
    TextView textCommentmessageactivityMessage;
    @InjectView(R.id.txt_content)
    TextView txtContent;
    @InjectView(R.id.footerView_ridingPhoto)
    ImageView footerViewRidingPhoto;
    @InjectView(R.id.txt_activity)
    TextView txtActivity;
    @InjectView(R.id.txt_ridingTitle)
    TextView txtRidingTitle;
    @InjectView(R.id.txt_footerView_ridingTitle)
    TextView txtFooterViewRidingTitle;
    @InjectView(R.id.img_footerView_ridingScanNum)
    ImageView imgFooterViewRidingScanNum;
    @InjectView(R.id.txt_footerView_ridingScanNum)
    TextView txtFooterViewRidingScanNum;
    @InjectView(R.id.img_footerView_ridingLike)
    ImageView imgFooterViewRidingLike;
    @InjectView(R.id.txt_footerView_ridingLike)
    TextView txtFooterViewRidingLike;
    @InjectView(R.id.txt_footerView_ridingComment)
    TextView txtFooterViewRidingComment;
    @InjectView(R.id.txt_footerView_checkIt)
    TextView txtFooterViewCheckIt;
    @InjectView(R.id.txt_reply)
    TextView txtReply;
    @InjectView(R.id.txt_footerView_checkAll)
    TextView txtCheckAll;
    @InjectView(R.id.edt_content)
    EditText edtContent;
    @InjectView(R.id.txt_Ncommit)
    TextView txtNcommit;
    @InjectView(R.id.layout_reply)
    LinearLayout layoutReply;

    private int commentId;
    private int activityId;

    private String photo;
    private String title;
    private String reply;
    private String intoType;
    private String userName;
    private String userImage;


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_consult_inform);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        commentId = getIntent().getIntExtra("commentId",-1);
        activityId = getIntent().getIntExtra("activityId",-1);

        photo = getIntent().getStringExtra("photo");
        title = getIntent().getStringExtra("title");
        reply = getIntent().getStringExtra("reply");
        intoType = getIntent().getStringExtra("intoType");
        userName = getIntent().getStringExtra("userName");
        userImage = getIntent().getStringExtra("userImage");


        if(intoType.equals("HDZX")){
            txtReply.setVisibility(View.GONE);
            layoutReply.setVisibility(View.VISIBLE);
            textCommentmessageactivityMessage.setText(userName+"咨询:");
        }else{
            txtReply.setVisibility(View.VISIBLE);
            layoutReply.setVisibility(View.GONE);
            textCommentmessageactivityMessage.setText(userName+"回复:");
        }

        txtContent.setText(reply);
        txtRidingTitle.setText(title);


        SystemUtil.Imagexutils_photo(userImage,imgRidingCommentPhoto,this);
        SystemUtil.Imagexutils_new(photo.split(",")[0],footerViewRidingPhoto,this);
    }

    @OnClick({R.id.layout_back, R.id.txt_Ncommit, R.id.txt_footerView_checkAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                finish();
                break;

            case R.id.txt_Ncommit:
                commit();
                break;

            case R.id.txt_footerView_checkAll:
                startActivity(new Intent(Activity_NewConsultInformActivity.this,Activity_NewConsultActivity.class).putExtra("activityId",activityId));
                break;
        }
    }

    private void commit() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("comment",edtContent.getText().toString());
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("parentId",commentId+"");
        params.addBodyParameter("activityId",activityId+"");

        new XUtilsNew().httpPost("activity/replyAdvice.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(Activity_NewConsultInformActivity.this,"回复成功",0);
                Activity_NewConsultInformActivity.this.finish();

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewConsultInformActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
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
