package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
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
 *	约骑处理
 */
public class RiderDisposalActivity extends Activity {

    @InjectView(R.id.disposal_back)
    LinearLayout disposalBack;
    @InjectView(R.id.disposal_userName)
    TextView disposalUserName;
    @InjectView(R.id.disposal_time)
    LinearLayout disposalTime;
    @InjectView(R.id.disposal_phone)
    TextView disposalPhone;
    @InjectView(R.id.disposal_message)
    TextView disposalMessage;
    @InjectView(R.id.disposal_agree)
    TextView disposalAgree;
    @InjectView(R.id.disposal_decline)
    TextView disposalDecline;
    @InjectView(R.id.disposal_checkOrder)
    TextView disposalCheckOrder;
    @InjectView(R.id.disposal_time1)
    TextView disposalTime1;
    @InjectView(R.id.disposal)
    TextView disposal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_disposal);
        ButterKnife.inject(this);

        // 陪骑处理
        MobclickAgent.onEvent(RiderDisposalActivity.this, new UmengEventUtil().click52);

        init();
        getData();
        getParams();
    }

    int isRider;
    int applyId;
    String orderId = null;
    String userName;
    SharedPreferences preferences;

    private void init() {
        applyId = getIntent().getIntExtra("applyId", -1);
        isRider = getIntent().getIntExtra("isRider", -1);
        userName = getIntent().getStringExtra("userName");
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    @OnClick({R.id.disposal_back, R.id.disposal_agree, R.id.disposal_decline, R.id.disposal_checkOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.disposal_back:
                finish();
                break;

            case R.id.disposal_agree:
                agree();
                break;

            case R.id.disposal_decline:
                decline();
                break;

            case R.id.disposal_checkOrder:
                if(disposalCheckOrder.getText().toString().equals("查看订单")){
                    if(orderId != null){
                        startActivity(new Intent(RiderDisposalActivity.this,RiderOrderActivity.class).putExtra("orderId",orderId));
                    }
                }
                break;
        }
    }

    String isAgree;
    String message;
    String phone;
    String time;
    String date;

    private void getData() {
        showProgressDialog();

        RequestParams params = new RequestParams();
        params.addBodyParameter("applyId", applyId + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));

        System.out.print("applyId="+applyId);
        System.out.print("uniqueKey="+preferences.getString("uniqueKey", null));

        new XUtilsNew().httpPost("rider/queryRiderApply.html", params, false,this ,new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                dissmissProgressDialog();
                String result = responseInfo.result;
                System.out.println(result);
                JSONObject object;
                try {
                    object = new JSONObject(result);
                    time = object.getJSONObject("data").getString("time");
                    date = object.getJSONObject("data").getString("date");
                    phone = object.getJSONObject("data").getString("phone");
                    isAgree = object.getJSONObject("data").getString("isAgree");
                    message = object.getJSONObject("data").getString("message");
                    orderId = object.getJSONObject("data").getString("orderId");

                    setView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(RiderDisposalActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    private void setView() {
        disposalUserName.setText(Html.fromHtml("<font color=\"#6dbfed\">" + userName + "</font>约您骑行，约骑时间为"));
        disposalTime1.setText(date+" "+time+"小时");
        disposalMessage.setText(message);
        if(isAgree.equals("0")){
            disposalCheckOrder.setVisibility(View.GONE);
        }else if(isAgree.equals("1")){
            disposalPhone.setText(phone);
            disposalCheckOrder.setVisibility(View.VISIBLE);
            disposalCheckOrder.setText("查看订单");
            disposalCheckOrder.setBackgroundResource(R.drawable.corners_rider_text_blue);

        }else if(isAgree.equals("2")){
            disposalCheckOrder.setVisibility(View.VISIBLE);
            disposalCheckOrder.setText("已拒绝");
            disposalCheckOrder.setBackgroundResource(R.drawable.corners_rider_text_gray);
        }
    }

    private void decline(){
        //拒绝
        showProgressDialog();
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId",preferences.getInt("userId", -1) + "");
        params.addBodyParameter("applyId", applyId + "");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey", null));
        new XUtilsNew().httpPost("rider/cancelApply.html", params, false,this ,new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                dissmissProgressDialog();

                disposalCheckOrder.setVisibility(View.VISIBLE);
                disposalCheckOrder.setText("已拒绝");
                disposalCheckOrder.setBackgroundResource(R.drawable.corners_rider_text_gray);
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(RiderDisposalActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }



    private void agree(){
        //同意
        showProgressDialog();
        RequestParams params = new RequestParams();

        System.out.println("userId="+preferences.getInt("userId", -1));
        System.out.println("applyId="+applyId);
        System.out.println("uniqueKey="+preferences.getString("uniqueKey", null));

        params.addBodyParameter("userId",preferences.getInt("userId", -1) + "");
        params.addBodyParameter("applyId", applyId + "");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey", null));

        new XUtilsNew().httpPost("rider/agreeApply.html", params, false,this ,new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                dissmissProgressDialog();
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    orderId = object.getString("data");

                    disposalPhone.setText(phone);
                    disposalCheckOrder.setVisibility(View.VISIBLE);
                    disposalCheckOrder.setText("查看订单");
                    disposalCheckOrder.setBackgroundResource(R.drawable.corners_rider_text_blue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(RiderDisposalActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 显示进度框
     */
    private ProgressDialog progDialog = null;// 操作进度条
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("请稍等...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    public void getParams() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("name","rider_desc");
        new XUtilsNew().httpPost("common/queryParams.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    disposal.setText(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });
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
