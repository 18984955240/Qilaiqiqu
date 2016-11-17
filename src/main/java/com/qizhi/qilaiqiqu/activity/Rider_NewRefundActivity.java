package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.Rider_NewRefundModel;
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
 *	陪骑退款
 */
public class Rider_NewRefundActivity extends Activity {

    @InjectView(R.id.refund_back)
    LinearLayout refundBack;
    @InjectView(R.id.refund_name)
    TextView refundName;
    @InjectView(R.id.rideTime)
    TextView rideTime;
    @InjectView(R.id.refund_time)
    TextView refundTime;
    @InjectView(R.id.refund_order)
    TextView refundOrder;
    @InjectView(R.id.ridePrice)
    TextView ridePrice;
    @InjectView(R.id.refund_price)
    TextView refundPrice;
    @InjectView(R.id.ridePhone)
    TextView ridePhone;
    @InjectView(R.id.refund_phone)
    TextView refundPhone;
    @InjectView(R.id.rideReason)
    TextView rideReason;
    @InjectView(R.id.refund_reason)
    TextView refundReason;
    @InjectView(R.id.riderExplain)
    TextView riderExplain;
    @InjectView(R.id.refund_explain)
    TextView refundExplain;
    @InjectView(R.id.refund_agree)
    TextView refundAgree;
    @InjectView(R.id.refund_decline)
    TextView refundDecline;
    @InjectView(R.id.disposal_normal)
    LinearLayout disposalNormal;
    @InjectView(R.id.refund_checkOrder)
    TextView refundCheckOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refund);
        ButterKnife.inject(this);
        init();
        getData();
    }

    String orderId;
    SharedPreferences preferences;
    private void init() {
        orderId = getIntent().getStringExtra("orderId");
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    @OnClick({R.id.refund_agree, R.id.refund_decline, R.id.refund_checkOrder, R.id.refund_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refund_back:
                finish();
                break;

            case R.id.refund_agree:
                agree();
                break;

            case R.id.refund_decline:
                decline();
                break;

        }
    }

    // 同意退款
    private void agree() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId", orderId);

        new XUtilsNew().httpPost("refund/agreeRefund.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                disposalNormal.setVisibility(View.GONE);
                refundCheckOrder.setVisibility(View.VISIBLE);
                refundCheckOrder.setText("已同意");
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewRefundActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    // 拒绝退款
    private void decline() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId", orderId);

        new XUtilsNew().httpPost("refund/cancelRefund.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                // 拒绝退款
                MobclickAgent.onEvent(Rider_NewRefundActivity.this, new UmengEventUtil().click48);

                disposalNormal.setVisibility(View.GONE);
                refundCheckOrder.setVisibility(View.VISIBLE);
                refundCheckOrder.setText("已拒绝");
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewRefundActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }


//    int riderId;
//    int total_time;
//    Double price;
//    Double total_price;
//
//    String state;
//    String phone;
//    String message;
//    String userName;
//    String orderMemo;
//    String riderName;
//    String argeeTimes;

    private Rider_NewRefundModel model;

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId", orderId);
        new XUtilsNew().httpPost("refund/queryRefund.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    model = gson.fromJson(object.toString(),new TypeToken<Rider_NewRefundModel>(){}.getType());

                    initView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewRefundActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    private void initView(){
        refundName.setText(Html.fromHtml("<font color=\"#6dbfed\">"+model.getOrder().getUserName()+"</font>")+"约您骑行,约骑时间为");
        refundTime.setText(model.getOrder().getDate());
        refundOrder.setText(model.getOrder().getTotal_time() + "小时*" + model.getOrder().getPrice() + "元/h");
        refundPrice.setText(model.getOrder().getTotal_price()+"元");
        refundPhone.setText(model.getOrder().getPhone());
        refundReason.setText(model.getOrder().getMessage());
//        refundExplain.setText();

        if(model.getRefund().getState() == 1){

        } else if(model.getRefund().getState() == 3){
            disposalNormal.setVisibility(View.GONE);
            refundCheckOrder.setVisibility(View.VISIBLE);
            refundCheckOrder.setText("已拒绝");
        }else if(model.getRefund().getState() == 2){
            disposalNormal.setVisibility(View.GONE);
            refundCheckOrder.setVisibility(View.VISIBLE);
            refundCheckOrder.setText("已同意");
        } else {
            disposalNormal.setVisibility(View.GONE);
            refundCheckOrder.setVisibility(View.VISIBLE);
            refundCheckOrder.setText("退款完成");
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
