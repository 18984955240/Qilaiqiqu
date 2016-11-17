package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
 *  活动订单
 */
public class Activity_NewOrderActivity extends Activity {

    @InjectView(R.id.layout_newActiveOrder_back)
    LinearLayout layoutBack;
    @InjectView(R.id.txt_newActiveOrder_title)
    TextView txtTitle;
    @InjectView(R.id.txt_newActiveOrder_activeTitle)
    TextView txtActiveTitle;
    @InjectView(R.id.txt_newActiveOrder_priceExplain)
    TextView txtPriceExplain;
    @InjectView(R.id.txt_newActiveOrder_price)
    TextView txtPrice;
    @InjectView(R.id.txt_newActiveOrder_total)
    TextView txtTotal;
    @InjectView(R.id.img_zhifubao)
    ImageView imgZhifubao;
    @InjectView(R.id.layout_zhifubao)
    LinearLayout layoutZhifubao;
    @InjectView(R.id.img_weixin)
    ImageView imgWeixin;
    @InjectView(R.id.layout_weixin)
    LinearLayout layoutWeixin;
    @InjectView(R.id.order_agreement)
    ImageView orderAgreement;
    @InjectView(R.id.order_agreementBtn)
    TextView orderAgreementBtn;
    @InjectView(R.id.order_explain)
    TextView orderExplain;
    @InjectView(R.id.layout_newActiveOrder_pay)
    LinearLayout layoutPay;
    @InjectView(R.id.btn_newActiveOrder_order)
    Button btnOrder;
    @InjectView(R.id.txt_newActiveOrder_cancel)
    TextView txtOrder;


    private String payState = "none";
    // 进入方式  tj:提交订单  dzf:待支付订单  dpj:待评价  ywc:已完成
    private String inWay = "order";
    // 订单价格描述
    private String activityTitle;
    // 活动标题
    private String priceDesc;
    // 订单ID
    private String orderId;
    // 订单总价
    private Double totalPrices;
    private Double prices;

    private int number;
    private int activityId;

    // 本地用户数据
    SharedPreferences preferences;
    private Object data;

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_order);
        ButterKnife.inject(this);
        init();
        initView();
        getParams();
        getData();
    }

    private void init(){
        api = WXAPIFactory.createWXAPI(this,ConstantsUtil.APP_ID_WX);
        api.registerApp(ConstantsUtil.APP_ID_WX);
    }

    private void initView() {
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        inWay = getIntent().getStringExtra("inWay");
        orderId = getIntent().getStringExtra("orderId");
        MainActivity_NewActivity.INSTANCE.orderId = orderId;
    }

    private void setView() {
        if("tj".equals(inWay)){
            layoutPay.setVisibility(View.VISIBLE);
            txtOrder.setVisibility(View.VISIBLE);
            txtTitle.setText("订单");
            btnOrder.setText("确认支付");
            btnOrder.setBackgroundResource(R.drawable.corners_rider_text_blue);
        }else if("dzf".equals(inWay)){
            layoutPay.setVisibility(View.VISIBLE);
            txtOrder.setVisibility(View.VISIBLE);
            txtTitle.setText("订单待支付");
            btnOrder.setText("确认支付");
            btnOrder.setBackgroundResource(R.drawable.corners_rider_text_blue);
        }else if("dpj".equals(inWay)){
            layoutPay.setVisibility(View.GONE);
            txtOrder.setVisibility(View.GONE);
            txtTitle.setText("订单待评价");
            btnOrder.setText("待点评");
            btnOrder.setBackgroundResource(R.drawable.corners_rider_text_orange);
        }else if("ywc".equals(inWay)){
            layoutPay.setVisibility(View.GONE);
            txtOrder.setVisibility(View.GONE);
            txtTitle.setText("订单已完成");
            btnOrder.setText("已完成");
            btnOrder.setBackgroundResource(R.drawable.corners_rider_text_gray);
        }

        txtTotal.setText(totalPrices+"元");
        txtPrice.setText(prices+"元*"+number);
        if(activityTitle.length()>15){
            txtActiveTitle.setText(activityTitle.substring(0,15)+"...");
        }else{
            txtActiveTitle.setText(activityTitle);
        }

        if(priceDesc.equals("活动费用")){
            txtPriceExplain.setText(priceDesc+":"+prices+"元");
        }else {
            txtPriceExplain.setText(priceDesc+"(费用名称):"+prices+"元");
        }
    }

    @OnClick({R.id.layout_zhifubao, R.id.layout_weixin, R.id.btn_newActiveOrder_order, R.id.txt_newActiveOrder_cancel,R.id.layout_newActiveOrder_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_zhifubao:
                initPayImage();
                payState = "ALiPay";
                imgZhifubao.setImageResource(R.drawable.choesn_pay);
                break;

            case R.id.layout_weixin:
                initPayImage();
                payState = "WXPay";
                imgWeixin.setImageResource(R.drawable.choesn_pay);
                break;

            case R.id.btn_newActiveOrder_order:

                // 支付订单
                MobclickAgent.onEvent(Activity_NewOrderActivity.this,"click29");

                if("确认支付".equals(btnOrder.getText().toString())){
                    if("ALiPay".equals(payState)){
                        Toasts.show(Activity_NewOrderActivity.this,"支付宝支付",0);
                        aliPay();
                    }else if("WXPay".equals(payState)){
                        Toasts.show(Activity_NewOrderActivity.this,"微信支付",0);
                        wxPay();
                    }else{
                        Toasts.show(Activity_NewOrderActivity.this,"请选择支付方式",0);
                    }
                }else {
                    startActivity(new Intent(Activity_NewOrderActivity.this, Activity_NewCommentActivity.class).putExtra("activityId",activityId));
                }

                break;

            case R.id.txt_newActiveOrder_cancel:
//                Toasts.show(Activity_NewOrderActivity.this,"取消订单",0);
                showCancel();
                break;

            case R.id.layout_newActiveOrder_back:
                finish();
                break;
        }
    }

    /**
     *
     */
    String noncestr;
    String timestamp;
    String partnerid;
    String prepayid;
    private void wxPay() {
        showProgressDialog("订单生成中..");

        // 获取微信预订单
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("orderId",orderId);
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));
        params.addBodyParameter("tag","HD");

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

                    System.out.print("noncestr="+noncestr);
                    System.out.print("prepayid="+prepayid);
                    System.out.print("timestamp="+timestamp);
                    System.out.print("partnerid="+partnerid);


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
                Toasts.show(Activity_NewOrderActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    /**
     * 微信 支付签名
     * @param params
     * @return
     */
    public  String createSign(Map<String,String> params){
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

    /**
     *
     */


    private void aliPay() {
        showProgressDialog("获取订单中..");
        final AlipayUtil alipayUtil = new AlipayUtil(Activity_NewOrderActivity.this, totalPrices, orderId ,"活动费用");
        final MyAes256 aes256 = new MyAes256();

        System.out.println("alipayUtil.RSA_PRIVATE="+alipayUtil.RSA_PRIVATE);
        if(alipayUtil.RSA_PRIVATE.equals("")){
            new XUtilsNew().httpGet("payment/alipayKey.html", false, this, new XUtilsNew.XUtilsCallBackGet() {
                @Override
                public void onMySuccess(ResponseInfo<String> responseInfo) {
                    try {
                        dissmissProgressDialog();
                        JSONObject object = new JSONObject(responseInfo.result);

                        alipayUtil.RSA_PRIVATE = object.getString("data");
                        alipayUtil.Pay();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onMyFailure(HttpException error, String msg) {
                    Toasts.show(Activity_NewOrderActivity.this,"获取私钥失败",0);
                }
            });
        }else{
            dissmissProgressDialog();
            alipayUtil.Pay();
        }
    }


    private void initPayImage(){
        imgWeixin.setImageResource(R.drawable.pay_unchosen);
        imgZhifubao.setImageResource(R.drawable.pay_unchosen);
    }

    public void getParams() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("name","activity_pay");

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

    /**
     * 取消订单
     */
    private void showCancel(){
        View view = LayoutInflater.from(this).inflate(R.layout.item_popup_order,null);

        TextView text = (TextView) view.findViewById(R.id.popup_order_text);
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

        text.setText("取消订单将不能参加此次活动哦，确定要取消订单吗？");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 取消订单
                MobclickAgent.onEvent(Activity_NewOrderActivity.this, new UmengEventUtil().click30);

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

    /**
     * 取消订单
     */
    private void cancelOrder(){
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("orderId",orderId);
        new XUtilsNew().httpPost("activity/cancelOrder.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                Toasts.show(Activity_NewOrderActivity.this,"订单已取消",0);
                finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {

            }
        });
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

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("orderId",orderId);
        new XUtilsNew().httpPost("activity/queryOrder.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    priceDesc = object.getJSONObject("data").getString("priceDesc");
                    activityTitle = object.getJSONObject("data").getString("activityTitle");

                    number = object.getJSONObject("data").getInt("total");
                    prices = object.getJSONObject("data").getDouble("price");
                    totalPrices = object.getJSONObject("data").getDouble("totalPrice");

                    activityId = object.getJSONObject("data").getInt("activityId");

                    setView();
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
