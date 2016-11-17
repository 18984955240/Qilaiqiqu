package com.qizhi.qilaiqiqu.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.LoginActivity;
import com.qizhi.qilaiqiqu.activity.MainActivity_NewActivity;
import com.qizhi.qilaiqiqu.activity.MyBaseActivity;
import com.qizhi.qilaiqiqu.model.UserLoginModel;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import cn.jpush.android.api.JPushInterface;

public class WXEntryActivity extends MyBaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;
	private String openid;
	private String access_token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wx_entry);
		api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX, false);
		api.registerApp(ConstantsUtil.APP_ID_WX);
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {// 分享
			String result = "";
			System.out.println("resp.errCode="+resp.errCode);
			switch (resp.errCode) {

			case BaseResp.ErrCode.ERR_OK:
				result = "成功";
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = "取消";
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = "拒绝";
				break;
			default:
				result = "。。。。";
				break;
			}
			Toasts.show(this, result, 0);
			finish();
			return;
		}
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
//			LoginActivity.dissmissProgressDialog();
			String code = ((SendAuth.Resp) resp).code;
			// 上面的code就是接入指南里要拿到的code
			wxRequest(code);

			break;

		default:
			finish();
			break;
		}
	}

	public void wxRequest(String code) {
		String url = "common/weixinLogin.html";

		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("code", code);
		params.addBodyParameter("pushToken", JPushInterface.getRegistrationID(WXEntryActivity.this));
		params.addBodyParameter("adviceType", "android");

		new XUtilsNew().httpPost("user/userWeixinLogin.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {

				try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<UserLoginModel>() {
                    }.getType();
                    final UserLoginModel userLogin = gson.fromJson(
                            jsonObject.getJSONObject("data").toString(), type);

                    /**
                     * SharedPreferences存储用户Id和uniqueKey
                     */
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            "userLogin", Context.MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();// 获取编辑器
                    editor.putInt("userId", userLogin.getUserId());
                    editor.putInt("riderId", userLogin.getRiderId());
                    editor.putString("userType", userLogin.getUserType());
                    editor.putString("userName", userLogin.getUserName());
                    editor.putString("uniqueKey", userLogin.getUniqueKey());
                    editor.putString("userImage", userLogin.getUserImage());
                    editor.putString("imUserName", userLogin.getImUserName());
                    editor.putString("imPassword", userLogin.getImPassword());
                    editor.putString("mobilePhone", userLogin.getMobilePhone());
                    editor.putInt("loginFlag", 1);
                    editor.commit();

				} catch (JSONException e) {
					e.printStackTrace();
				}

				startActivity(new Intent(WXEntryActivity.this, MainActivity_NewActivity.class)
						.putExtra("loginFlag", 1));
				finish();

				LoginActivity.instanceLogin.finish();
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				LoginActivity.instanceLogin.dissmissProgressDialog();
				Toasts.show(WXEntryActivity.this, "无法连接服务器，请检查网络", 0);
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
