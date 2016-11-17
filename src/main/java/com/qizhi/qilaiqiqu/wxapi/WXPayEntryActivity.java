package com.qizhi.qilaiqiqu.wxapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.MainActivity_NewActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewOrderActivity;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信支付结果界面
 * @author hfk
 * create at 2016/5/3 10:42
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private static WXCallBack wxCallBack;

    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wx_entry);
        api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
        api.handleIntent(getIntent(), this);

        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 结果回调方法
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(resp.errCode == 0){
                Toasts.show(WXPayEntryActivity.this,"支付成功",0);
                Rider_NewOrderActivity.instance.finish();
            } else if(resp.errCode == -2) {
                Toasts.show(WXPayEntryActivity.this,"支付取消",0);
            } else {
                checkPayResult(resp.errCode);
            }
            finish();
        }
    }

    private void checkPayResult(final int err){
        if(MainActivity_NewActivity.INSTANCE.orderId != null){
            RequestParams params = new RequestParams("UTF-8");
            params.addQueryStringParameter("orderId", MainActivity_NewActivity.INSTANCE.orderId);
            params.addQueryStringParameter("userId",preferences.getInt("userId",-1)+"");
            params.addQueryStringParameter("uniqueKey",preferences.getString("uniqueKey",null));

            new XUtilsNew().httpPost("paymen/wxPayConfirm.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

                @Override
                public void onMySuccess(ResponseInfo<String> responseInfo) {

                    try {
                        JSONObject object = new JSONObject(responseInfo.result);
                        if(object.getString("state").equals("2")){
                            Toasts.show(WXPayEntryActivity.this,"支付成功",0);
                        }else {
                            Toasts.show(WXPayEntryActivity.this,"resp.errCode="+err,0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onMyFailure(HttpException error, String msg) {
                    Toasts.show(WXPayEntryActivity.this, "无法连接服务器，请检查网络", 0);
                }
            });
        }
    }

    public static void setWXCallBack(WXCallBack callBack){
        wxCallBack=callBack;
    }

    public interface WXCallBack {
        void msgCallBask(int msg);
    }
}