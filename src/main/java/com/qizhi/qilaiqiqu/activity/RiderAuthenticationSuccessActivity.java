package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.RecommendActivityModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.RotateTextView;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 *	陪骑认证成功
 */
public class RiderAuthenticationSuccessActivity extends Activity {


    @InjectView(R.id.txt_tip)
    TextView txtTip;
    @InjectView(R.id.txt_title1)
    TextView txtTitle1;
    @InjectView(R.id.txt_browse1)
    TextView txtBrowse1;
    @InjectView(R.id.img_photo1)
    ImageView imgPhoto1;
    @InjectView(R.id.txt_money1)
    RotateTextView txtMoney1;
    @InjectView(R.id.img_userImage1)
    CircleImageViewUtil imgUserImage1;
    @InjectView(R.id.txt_userName1)
    TextView txtUserName1;
    @InjectView(R.id.txt_activityInfo)
    TextView txtActivityInfo;
    @InjectView(R.id.layout_activity1)
    LinearLayout layoutActivity1;
    @InjectView(R.id.txt_itle2)
    TextView txtTitle2;
    @InjectView(R.id.txt_browse2)
    TextView txtBrowse2;
    @InjectView(R.id.img_photo2)
    ImageView imgPhoto2;
    @InjectView(R.id.txt_money2)
    RotateTextView txtMoney2;
    @InjectView(R.id.img_userImage2)
    CircleImageViewUtil imgUserImage2;
    @InjectView(R.id.txt_userName2)
    TextView txtUserName2;
    @InjectView(R.id.txt_activityInfo2)
    TextView txtActivityInfo2;
    @InjectView(R.id.layout_activity2)
    LinearLayout layoutActivity2;
    @InjectView(R.id.layout_back)
    TextView layoutBack;
    private RecommendActivityModel activityModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_authentication_success);
        ButterKnife.inject(this);

        init();
        getTips();
    }

    private void init() {
        activityModel = (RecommendActivityModel) getIntent().getSerializableExtra("activityModel");
        System.out.println(activityModel.getHint());
        setView();
    }

    private void setView() {
        txtTitle1.setText(activityModel.getDataList().get(0).getTitle());
        txtUserName1.setText(activityModel.getDataList().get(0).getUserName());
        txtBrowse1.setText(activityModel.getDataList().get(0).getScanNum() + "次浏览");
        if (activityModel.getDataList().get(0).getPrice() == 0) {
            txtMoney1.setVisibility(View.GONE);
        } else {
            txtMoney1.setVisibility(View.VISIBLE);
        }
        SystemUtil.Imagexutils_new(activityModel.getDataList().get(0).getPhoto().split(",")[0], imgPhoto1, this);
        SystemUtil.Imagexutils_photo(activityModel.getDataList().get(0).getUserImage(), imgUserImage1, this);


        txtTitle2.setText(activityModel.getDataList().get(1).getTitle());
        txtUserName2.setText(activityModel.getDataList().get(1).getUserName());
        txtBrowse2.setText(activityModel.getDataList().get(1).getScanNum() + "次浏览");
        if (activityModel.getDataList().get(1).getPrice() == 0) {
            txtMoney2.setVisibility(View.GONE);
        } else {
            txtMoney2.setVisibility(View.VISIBLE);
        }
        SystemUtil.Imagexutils_new(activityModel.getDataList().get(1).getPhoto().split(",")[0], imgPhoto2, this);
        SystemUtil.Imagexutils_photo(activityModel.getDataList().get(1).getUserImage(), imgUserImage2, this);


    }

    private void getTips() {
        // ride_note :陪骑须知
        // ride_hint ：陪骑温馨提示
        // soft_desc ： 软件介绍
        // refund_desc ： 退款处理说明

        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("name", "ride_hint");
        new XUtilsNew().httpPost("common/queryParams.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    txtTip.setText(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(RiderAuthenticationSuccessActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    @OnClick({R.id.layout_activity1, R.id.layout_activity2, R.id.layout_back})

    public void onClick(View view) {
        Intent intent = new Intent(RiderAuthenticationSuccessActivity.this, Activity_NewDetailActivity.class);
        switch (view.getId()) {
            case R.id.layout_activity1:
                // 查看达人推荐活动
                MobclickAgent.onEvent(RiderAuthenticationSuccessActivity.this, new UmengEventUtil().click41);

                intent.putExtra("activityId", activityModel.getDataList().get(0).getId());
                intent.putExtra("activityTitle", activityModel.getDataList().get(0).getTitle());
                intent.putExtra("activityFlag", false);
                startActivity(intent);
                break;

            case R.id.layout_activity2:
                // 查看达人推荐活动
                MobclickAgent.onEvent(RiderAuthenticationSuccessActivity.this, new UmengEventUtil().click41);

                intent.putExtra("activityId", activityModel.getDataList().get(1).getId());
                intent.putExtra("activityTitle", activityModel.getDataList().get(1).getTitle());
                intent.putExtra("activityFlag", false);
                startActivity(intent);
                break;

            case R.id.layout_back:
                finish();
                break;
        }
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
