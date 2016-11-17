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

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑订单
 */
public class RiderOrderActivity extends Activity {

    @InjectView(R.id.riderOrder_back)
    LinearLayout riderOrderBack;
    @InjectView(R.id.riderOrder_userName)
    TextView riderOrderUserName;
    @InjectView(R.id.riderOrder_time)
    LinearLayout riderOrderTime;
    @InjectView(R.id.riderOrder_Money)
    TextView riderOrderMoney;
    @InjectView(R.id.riderOrder_sumMoney)
    TextView riderOrderSumMoney;
    @InjectView(R.id.riderOrder_phone)
    TextView riderOrderPhone;
    @InjectView(R.id.riderOrder_message)
    TextView riderOrderMessage;
    @InjectView(R.id.riderOrder_button)
    TextView riderOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rider_order);
        ButterKnife.inject(this);
        init();
        getData();

    }

    String orderId;
    private SharedPreferences preferences;
    private void init() {
        orderId = getIntent().getStringExtra("orderId");
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    int total_time; // 时间
    Double price; // 单价
    Double total_price; // 总价
    String userName;
    String phone;
    String message;
    String orderMemo;


    public void getData() {
        RequestParams params = new RequestParams("UTF-8");

        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("uniqueKey", preferences.getString("uniqueKey", null)+"");
        new XUtilsNew().httpPost("rider/queryOrder.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("responseInfo.result="+responseInfo.result);
                try {
                    JSONObject object = new JSONObject(result);

                    phone = object.getJSONObject("data").getString("phone");
                    message = object.getJSONObject("data").getString("message");
                    userName = object.getJSONObject("data").getString("userName");

                    price = object.getJSONObject("data").getDouble("price");
                    total_time = object.getJSONObject("data").getInt("total_time");
                    total_price = object.getJSONObject("data").getDouble("total_price");

                    state = object.getJSONObject("data").getString("state");

                        initView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(RiderOrderActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    String state = "0";
    private void initView() {
        riderOrderPhone.setText(phone);
        riderOrderMessage.setText(message);
        riderOrderUserName.setText(Html.fromHtml("<font color=\"#6dbfed\">"+userName+"</font>约您骑行，约骑时间为"));
        riderOrderMoney.setText(total_time+"小时*"+price+"元/h");
        riderOrderSumMoney.setText(total_price+"元");

        if(state.equals("1")){
            riderOrderButton.setText("用户未支付订单");
            riderOrderButton.setBackgroundResource(R.drawable.corners_rider_text_orange);
        } else if(state.equals("3")) {
            riderOrderButton.setText("用户已取消订单");
            riderOrderButton.setBackgroundResource(R.drawable.corners_rider_text_gray);
        } else if(state.equals("7")) {
            riderOrderButton.setText("订单已退款");
            riderOrderButton.setBackgroundResource(R.drawable.corners_rider_text_gray);
        } else if(state.equals("4")) {
            Toasts.show(RiderOrderActivity.this,"订单状态错误:state="+state,0);
        } else {
            riderOrderButton.setText("用户已支付订单");
            riderOrderButton.setBackgroundResource(R.drawable.corners_rider_text_orange);
        }
    }
    @OnClick({R.id.riderOrder_back, R.id.riderOrder_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.riderOrder_back:
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
