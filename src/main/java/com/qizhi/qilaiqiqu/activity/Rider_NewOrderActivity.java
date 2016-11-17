package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.AlipayUtil;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.MD5Util;
import com.qizhi.qilaiqiqu.utils.MyAes256;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  陪骑订单
 */
public class Rider_NewOrderActivity extends MyBaseActivity {

    @InjectView(R.id.order_back)
    LinearLayout orderBack;
    @InjectView(R.id.order_userName)
    TextView orderUserName;
    @InjectView(R.id.order_time)
    TextView orderTime;
    @InjectView(R.id.order_agree)
    TextView orderAgree;
    @InjectView(R.id.order_Money)
    TextView orderMoney;
    @InjectView(R.id.order_sumMoney)
    TextView orderSumMoney;
    @InjectView(R.id.order_phone)
    TextView orderPhone;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.order_AliPay)
    ImageView orderAliPay;
    @InjectView(R.id.order_WxPay)
    ImageView orderWxPay;
    @InjectView(R.id.order_agreement)
    ImageView orderAgreement;
    @InjectView(R.id.order_agreementBtn)
    TextView orderAgreementBtn;
    @InjectView(R.id.order_button)
    Button orderButton;
//    @InjectView(R.id.order_button_text)
//    TextView orderButtonText;
    @InjectView(R.id.order_Application)
    TextView orderApplication;
    @InjectView(R.id.pay_layout)
    LinearLayout payLayout;
    @InjectView(R.id.order_explain)
    TextView orderExplain;

    public static Activity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.inject(this);

        // 支付陪骑订单
        MobclickAgent.onEvent(Rider_NewOrderActivity.this, new UmengEventUtil().click45);

        init();
        getParams();
    }

    SharedPreferences preferences;

    private void init() {

        instance = this;

        orderId = getIntent().getStringExtra("orderId");
        MainActivity_NewActivity.INSTANCE.orderId = orderId;
        api = WXAPIFactory.createWXAPI(this,ConstantsUtil.APP_ID_WX);
        api.registerApp(ConstantsUtil.APP_ID_WX);
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    public void getParams() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("name","rider_pay");
        new XUtilsNew().httpPost("common/queryParams.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    orderExplain.setText(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });

    }

    int wxPayFlag = 0; // 0 未选 1 已选
    int aliPayFlag = 1; // 0 未选 1 已选
    int agreementFlag = 1; // 0 未选 1 已选

    @OnClick({R.id.order_back, R.id.order_AliPay, R.id.order_WxPay, R.id.order_agreement, R.id.order_agreementBtn, R.id.order_button, R.id.order_Application})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_back:
                finish();
                break;

            case R.id.order_AliPay:
                if (aliPayFlag == 0) {
                    orderAliPay.setImageResource(R.drawable.choesn_pay);
                    orderWxPay.setImageResource(R.drawable.pay_unchosen);
                    aliPayFlag = 1;
                    wxPayFlag = 0;
                }
                break;

            case R.id.order_WxPay:
                if (wxPayFlag == 0) {
                    orderWxPay.setImageResource(R.drawable.choesn_pay);
                    orderAliPay.setImageResource(R.drawable.pay_unchosen);
                    aliPayFlag = 0;
                    wxPayFlag = 1;
                }
                break;

            case R.id.order_agreement:
                if (agreementFlag == 0) {
                    orderAgreement.setImageResource(R.drawable.choesn_pay);
                    agreementFlag = 1;
                } else {
                    orderAgreement.setImageResource(R.drawable.pay_unchosen);
                    agreementFlag = 0;
                }
                break;

            case R.id.order_agreementBtn:
                Toasts.show(Rider_NewOrderActivity.this, "骑来骑去协议", 0);
                break;

            case R.id.order_button:
                if (orderButton.getText().toString().equals("确认支付")) {
                    if (agreementFlag == 1) {
                        if (wxPayFlag == 1 && aliPayFlag == 0) {
                            // 微信支付
                            wxPay();
                        } else if (aliPayFlag == 1 && wxPayFlag == 0) {
                            // 支付宝支付
                            aliPay();
                        } else {
                            Toasts.show(Rider_NewOrderActivity.this, "请选择支付方式", 0);
                        }
                    } else {
                        Toasts.show(Rider_NewOrderActivity.this, "请阅读并同意协议", 0);
                    }
                } else if (orderButton.getText().toString().equals("待评价")) {
                    startActivity(new Intent(Rider_NewOrderActivity.this, RiderCommentActivity.class)
                            .putExtra("riderId", riderId)
                            .putExtra("applyId", productid)
                            .putExtra("name", riderName)
                            .putExtra("time", argeeTimes));
                }
                break;

            case R.id.order_Application:
                if (orderApplication.getText().toString().equals("取消订单")) {
                    showPopupWindow();
                } else if (orderApplication.getText().toString().equals("申请退款")) {
                    startActivity(new Intent(Rider_NewOrderActivity.this, OrderRefundApplyActivity.class).putExtra("orderId", orderId));
                }
                break;
        }
    }

    String sign;
    String noncestr;
    String timestamp;
    String partnerid;
    String prepayid;

    private IWXAPI api;
    private void wxPay() {
        showProgressDialog("获取订单中..");

        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));
        params.addQueryStringParameter("tag","PQ");

        new XUtilsNew().httpPost("payment/queryPrepayId.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                dissmissProgressDialog();
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    noncestr = object.getString("nonce_str");
                    timestamp = object.getString("timestamp");
                    partnerid = object.getString("partnerid");
                    prepayid = object.getString("prepay_id");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("appid", ConstantsUtil.APP_ID_WX);
                    map.put("noncestr", noncestr);
                    map.put("partnerid", partnerid);
                    map.put("package", "Sign=WXpay");
                    map.put("prepayid",prepayid);
                    map.put("timestamp", timestamp);

                    PayReq req = new PayReq();
                    req.appId = ConstantsUtil.APP_ID_WX;
                    req.sign = createSign(map);
                    req.prepayId = prepayid;
                    req.nonceStr = noncestr;
                    req.timeStamp = timestamp;
                    req.partnerId = partnerid;
                    req.packageValue = "Sign=WXpay";

                    api.sendReq(req);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(Rider_NewOrderActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });


    }

    /**
     * 微信 支付签名
     * @param params
     * @return
     */
    public static String createSign(Map<String,String> params){
        Set<String> keysSet = params.keySet();
        //对参数进行排序
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        //签名参数字符串
        String signStr = "";
        int index = 0;
        for (Object key : keys){
            if(key.equals("sign"))
                continue;
            String value = params.get(key);
            signStr += key + "=" + value;
            index ++ ;
            if(keys.length > index)
                signStr += "&";
        }
        String sign = signStr + "&key=7fx5r0n7j8k1tvnzpn55c3ef0zo9a7be";
        return MD5Util.MD5Encode(sign, "utf-8").toUpperCase();
    }

    private void aliPay() {
        showProgressDialog("获取订单中..");

        final AlipayUtil alipayUtil = new AlipayUtil(Rider_NewOrderActivity.this, total_price, orderId, "陪骑费用");
        final MyAes256 aes256 = new MyAes256();

        System.out.println("alipayUtil.RSA_PRIVATE="+alipayUtil.RSA_PRIVATE);
        if(alipayUtil.RSA_PRIVATE.equals("")){
            new XUtilsNew().httpGet("payment/alipayKey.html", false, this, new XUtilsNew.XUtilsCallBackGet() {
                @Override
                public void onMySuccess(ResponseInfo<String> responseInfo) {
                    dissmissProgressDialog();
                    try {
                        JSONObject object = new JSONObject(responseInfo.result);

                        alipayUtil.RSA_PRIVATE = object.getString("data");
                        alipayUtil.Pay();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onMyFailure(HttpException error, String msg) {
                    dissmissProgressDialog();
                    Toasts.show(Rider_NewOrderActivity.this, "无法连接服务器，请检查网络", 0);
                }
            });
        }else{
            dissmissProgressDialog();
            alipayUtil.Pay();
        }
    }

    int riderId;
    int productid;
    int total_time;
    Double price;
    Double total_price;

    String state;
    String phone;
    String date;
    String userName;
    String riderName;
    String argeeTimes;



    private void initView() {
        if(!state.equals("1")){
            orderPhone.setText(phone);
        }

        if (state.equals("1")) {
            orderButton.setText("确认支付");
            orderButton.setTextColor(this.getResources().getColor(R.color.white));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_blue);
            orderApplication.setVisibility(View.VISIBLE);
            orderApplication.setText("取消订单");
            payLayout.setVisibility(View.VISIBLE);
        } else if (state.equals("2")) {
            orderButton.setText("已支付");
            orderButton.setTextColor(this.getResources().getColor(R.color.white));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_orange);
            orderApplication.setVisibility(View.VISIBLE);
            orderApplication.setText("申请退款");
            payLayout.setVisibility(View.GONE);
        } else if (state.equals("3")) {
            orderButton.setText("已取消");
            orderButton.setTextColor(this.getResources().getColor(R.color.white));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_gray);
            orderApplication.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
        } else if (state.equals("4")) {
            orderButton.setText("退款处理中");
            orderButton.setTextColor(this.getResources().getColor(R.color.white));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_gray);
            orderApplication.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
        } else if (state.equals("5")) {
            orderButton.setText("待评价");
            orderButton.setTextColor(this.getResources().getColor(R.color.orange));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_white);
            orderApplication.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
        } else if (state.equals("6")) {
            orderButton.setText("已完成");
            orderButton.setTextColor(this.getResources().getColor(R.color.white));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_gray);
            orderApplication.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
        } else if (state.equals("7")) {
            orderButton.setText("已退款");
            orderButton.setTextColor(this.getResources().getColor(R.color.white));
            orderButton.setBackgroundResource(R.drawable.corners_rider_text_gray);
            orderApplication.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
        }

        if (aliPayFlag == 1) {
            orderAliPay.setImageResource(R.drawable.choesn_pay);
        }
        if (agreementFlag == 1) {
            orderAgreement.setImageResource(R.drawable.choesn_pay);
        }

        orderUserName.setText(Html.fromHtml("您约<font color=\"#6dbfed\">" + riderName + "</font>骑行，约骑时间为"));
        orderTime.setText(argeeTimes);
        orderAgree.setText("已同意");
        orderMoney.setText(total_time + "小时*" + price + "元/h");
        orderSumMoney.setText(total_price+"元");

    }


    public String orderId = null;
    public void getData() {
        System.out.println("orderId="+orderId);
        System.out.println("preferences.getString(\"uniqueKey\", null)="+preferences.getString("uniqueKey", null));

        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId", orderId);
        params.addQueryStringParameter("uniqueKey", preferences.getString("uniqueKey", null));
        new XUtilsNew().httpPost("rider/queryOrder.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    date = object.getJSONObject("data").getString("date");
                    riderId = object.getJSONObject("data").getInt("riderId");
                    productid = object.getJSONObject("data").getInt("productid");
                    total_time = object.getJSONObject("data").getInt("total_time");

                    price = object.getJSONObject("data").getDouble("price");
                    total_price = object.getJSONObject("data").getDouble("total_price");

                    state = object.getJSONObject("data").getString("state");
                    phone = object.getJSONObject("data").getString("phone");
                    userName = object.getJSONObject("data").getString("userName");
                    riderName = object.getJSONObject("data").getString("riderName");


                    argeeTimes = date+" "+total_time+"小时";

                    initView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewOrderActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    @Override
    protected void onResume() {
        getData();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
        super.onPause();
    }

    /**
     * 显示进度框
     */
    private ProgressDialog progDialog = null;// 进度条
    private void showProgressDialog(String str) {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(str);
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

    /**
     * 取消订单
     */
    private void showPopupWindow(){
        View view = LayoutInflater.from(this).inflate(R.layout.item_popup_order,null);

        TextView confirm = (TextView) view.findViewById(R.id.txt_gradePopup_ok);
        TextView cancel = (TextView) view.findViewById(R.id.txt_gradePopup_cancel);

        final PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
                popupWindow.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.corners_layout));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 50);

    }

    private void cancelOrder(){
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));

        new XUtilsNew().httpPost("rider/cancelOrder.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                // 取消陪骑订单
                MobclickAgent.onEvent(Rider_NewOrderActivity.this, new UmengEventUtil().click46);

                Toasts.show(Rider_NewOrderActivity.this, "取消成功", 0);
                finish();

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Rider_NewOrderActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }




}
