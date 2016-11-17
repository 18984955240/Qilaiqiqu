package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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
 *  申请退款
 */
public class OrderRefundApplyActivity extends Activity {

    @InjectView(R.id.refundApply_back)
    LinearLayout refundApplyBack;
    @InjectView(R.id.refundApply_commit)
    TextView refundApplyCommit;
    @InjectView(R.id.refundApply_phone)
    EditText refundApplyPhone;
    @InjectView(R.id.refundApply_reason)
    EditText refundApplyReason;
    @InjectView(R.id.refund_Explain)
    TextView refundExplain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refund_apply);
        ButterKnife.inject(this);
        init();
    }

    private void getDate() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("name","refund_desc");
        new XUtilsNew().httpPost("common/queryParams.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    refundExplain.setText(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });

    }

    String orderId;
    SharedPreferences preferences;
    private void init() {
        orderId = getIntent().getStringExtra("orderId");
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        getDate();

    }

    @OnClick({R.id.refundApply_back, R.id.refundApply_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refundApply_back:
                finish();
                break;

            case R.id.refundApply_commit:
                if(refundApplyPhone.getText().toString().equals("")){
                    Toasts.show(OrderRefundApplyActivity.this,"请填写联系号码",0);
                } else if (refundApplyReason.getText().toString().equals("")){
                    Toasts.show(OrderRefundApplyActivity.this,"请填写退款理由",0);
                }else {
                    postApply();
                }
                break;
        }
    }

    public void postApply(){
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
        params.addQueryStringParameter("phone",refundApplyPhone.getText().toString());
        params.addQueryStringParameter("message",refundApplyReason.getText().toString());
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("refund/applyRefund.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                // 申请陪骑退款
                MobclickAgent.onEvent(OrderRefundApplyActivity.this, new UmengEventUtil().click47);

                Toasts.show(OrderRefundApplyActivity.this,"申请成功，等待陪骑士退款",0);
                finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(OrderRefundApplyActivity.this, "无法连接服务器，请检查网络", 0);
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
