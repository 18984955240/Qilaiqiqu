package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.UserLoginModel;
import com.qizhi.qilaiqiqu.utils.AccessTokenKeeper;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.ErrorInfo;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UsersAPI;
import com.qizhi.qilaiqiqu.utils.WBConstants;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;

/**
 * 	登录
 */

public class LoginActivity extends Activity implements
		OnClickListener {

	private Button loginBtn;// 登录按钮
	private Button visitorBtn;// 游客模式
	private Button registerBtn;// 注册按钮

	private EditText usernameEdt;// 用户账户
	private EditText passwordEdt;// 用户密码

	private ImageView qqImg;
	private ImageView wechatImg;
	private ImageView weiboImg;

	private TextView fogetTxt;// 忘记密码

	private XUtilsUtil httpUtils;

	private IWXAPI api;

	private IUiListener loginListener; // 腾讯授权登录监听器

	private Tencent mTencent;
	private String registrationID;

	private ProgressDialog progDialog = null;// 搜索时进度条

	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	private AuthInfo mAuthInfo;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 用户信息接口 */
	private UsersAPI mUsersAPI;
	private static final String TAG = LoginActivity.class.getName();

	public static LoginActivity instanceLogin = null;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		api = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID_WX);
		mAuthInfo = new AuthInfo(this, WBConstants.APP_KEY,
				WBConstants.REDIRECT_URL, WBConstants.SCOPE);

		mTencent = Tencent.createInstance(ConstantsUtil.APP_ID_TX,
				this.getApplicationContext());
		instanceLogin = this;
		initView();
		initEvent();
	}

	private void initView() {
		httpUtils = new XUtilsUtil();
		registrationID = JPushInterface.getRegistrationID(LoginActivity.this);
		System.out.println("registrationID:" + registrationID);
		boolean getConnectionState = JPushInterface.getConnectionState(LoginActivity.this);
		System.out.println("getConnectionState:" + getConnectionState);

		fogetTxt = (TextView) findViewById(R.id.txt_loginActivity_forget);

		loginBtn = (Button) findViewById(R.id.btn_loginActivity_login);
		visitorBtn = (Button) findViewById(R.id.btn_loginActivity_visitor);
		registerBtn = (Button) findViewById(R.id.btn_loginActivity_register);

		usernameEdt = (EditText) findViewById(R.id.edt_loginActivity_username);
		passwordEdt = (EditText) findViewById(R.id.edt_loginActivity_password);

		qqImg = (ImageView) findViewById(R.id.img_loginactivity_qq);
		wechatImg = (ImageView) findViewById(R.id.img_loginactivity_wechat);
		weiboImg = (ImageView) findViewById(R.id.img_loginactivity_weibo);

		loginListener = new IUiListener() {

			@Override
			public void onError(UiError arg0) {

			}

			@Override
			public void onComplete(Object value) {

				System.out.println("有数据返回..");
				System.out.println("value.toString()="+value.toString());
				if (value == null) {
					return;
				}

				try {
					final JSONObject jo = (JSONObject) value;

					int ret = jo.getInt("ret");

					System.out.println("json=" + String.valueOf(jo));

					if (ret == 0) {
						String openID = jo.getString("openid");
						String accessToken = jo.getString("access_token");
						String expires = jo.getString("expires_in");
						mTencent.setOpenId(openID);
						mTencent.setAccessToken(accessToken, expires);

						QQToken qqToken = mTencent.getQQToken();
						UserInfo info = new UserInfo(getApplicationContext(),
								qqToken);
						info.getUserInfo(new IUiListener() {

							@Override
							public void onError(UiError msg) {
								Toasts.show(LoginActivity.this, "未知错误：" + msg,
										0);
							}

							@Override
							public void onComplete(Object response) {
								JSONObject re = (JSONObject) response;
								System.out.println("response="
										+ String.valueOf(response));

								sendTencentCode(re, jo);
							}

							@Override
							public void onCancel() {
							}
						});
					}

				} catch (Exception e) {
				}

			}

			@Override
			public void onCancel() {

			}
		};

	}

	private void initEvent() {
		fogetTxt.setOnClickListener(this);

		loginBtn.setOnClickListener(this);
		visitorBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);

		qqImg.setOnClickListener(this);
		wechatImg.setOnClickListener(this);
		weiboImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(registrationID == null || registrationID.equals("")){
			registrationID = "qilaiqiqu_registrationID";
		}
		switch (v.getId()) {
		case R.id.btn_loginActivity_login:

			// 友盟计数统计事件
			MobclickAgent.onEvent(this,"click3");

			userLogin();

			break;

		case R.id.btn_loginActivity_visitor:
			finish();
			startActivity(new Intent(LoginActivity.this, MainActivity_NewActivity.class)
					.putExtra("loginFlag", 1));
			break;

		case R.id.btn_loginActivity_register:

			// 友盟计数统计事件
			MobclickAgent.onEvent(this,"click1");

			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;

		case R.id.txt_loginActivity_forget:
			startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
			break;

		case R.id.img_loginactivity_qq:

			// 友盟计数统计事件
			MobclickAgent.onEvent(this,"click1");
			showProgressDialog();
			// 如果session无效，就开始登录
			if (!mTencent.isSessionValid()) {
				// 开始qq授权登录
				mTencent.login(LoginActivity.this, "all", loginListener);
			} else {
				dissmissProgressDialog();
				Toasts.show(LoginActivity.this, "您已登陆QQ,请先退出登陆", 0);
			}

			break;

		case R.id.img_loginactivity_wechat:

			showProgressDialog();

			final SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "wechat_sdk_demo_test";
			api.sendReq(req);

			break;

		case R.id.img_loginactivity_weibo:


			showProgressDialog();
			// showProgressDialog();
			mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
			mSsoHandler.authorize(new AuthListener());

			break;
		}
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			dissmissProgressDialog();
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 显示 Token
				updateTokenView(false);

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this,
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginActivity.this,
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 显示当前 Token 信息。
	 * 
	 * @param hasExisted
	 *            配置文件中是否已存在 token 信息并且合法
	 */
	private void updateTokenView(boolean hasExisted) {
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
		// mTokenText.setText(String.format(format, mAccessToken.getToken(),
		// date));

		String message = String.format(format, mAccessToken.getToken(), date);
		if (hasExisted) {
			message = getString(R.string.weibosdk_demo_token_has_existed)
					+ "\n" + message;
		}
		System.out.println("微博登录信息:" + message);
		getWBUserInfo();
	}

	private void getWBUserInfo() {
		mUsersAPI = new UsersAPI(this, WBConstants.APP_KEY, mAccessToken);
		long uid = Long.parseLong(mAccessToken.getUid());
		mUsersAPI.show(uid, mListener);
	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private User user;
	private RequestListener mListener = new RequestListener() {

		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				LogUtil.i(TAG, response);
				user = User.parse(response);
				if (user != null) {
					sendWBUserInfo();
				} else {
					Toast.makeText(LoginActivity.this, response,
							Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			LogUtil.e(TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(LoginActivity.this, info.toString(),
					Toast.LENGTH_LONG).show();
		}
	};

	private void sendWBUserInfo() {
		RequestParams params = new RequestParams("UTF-8");

		params.addQueryStringParameter("pushToken", registrationID);
		params.addQueryStringParameter("adviceType", "android");
		params.addQueryStringParameter("uId", mAccessToken.getUid());
		params.addQueryStringParameter("accessToken", mAccessToken.getToken());
		params.addQueryStringParameter("screenName", user.screen_name);
		params.addQueryStringParameter("gender", user.gender);
		params.addQueryStringParameter("avatarLarge", user.avatar_large);
		params.addQueryStringParameter("profileImageUrl", user.avatar_hd);


		System.out.println("pushToken="+registrationID);
		System.out.println("adviceType="+"android");
		System.out.println("uId="+mAccessToken.getUid());
		System.out.println("accessToken="+mAccessToken.getToken());
		System.out.println("screenName="+user.screen_name);
		System.out.println("gender="+user.gender);
		System.out.println("avatarLarge="+user.avatar_large);
		System.out.println("profileImageUrl="+user.avatar_hd);


		new XUtilsNew().httpPost("", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

					@Override
					public void onMySuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						System.out.println("微博用户信息:" + result);

						try {
							JSONObject jsonObject = new JSONObject(result);
							if (jsonObject.getBoolean("result")) {
								JSONObject data = jsonObject
										.getJSONObject("data");
								/**
								 * SharedPreferences存储用户Id和uniqueKey
								 */
								SharedPreferences sharedPreferences = getSharedPreferences(
										"userLogin", Context.MODE_PRIVATE);
								Editor editor = sharedPreferences.edit();// 获取编辑器
								editor.putString("imId", data.optString("imId"));
								editor.putString("mobilePhone",
										data.optString("mobilePhone"));
								editor.putString("imPassword",
										data.optString("imPassword"));
								editor.putInt("userId", data.optInt("userId"));
								editor.putString("userImage",
										data.optString("userImage"));
								editor.putString("userName",
										data.optString("userName"));
								editor.putString("imUserName",
										data.optString("imUserName"));
								editor.putString("uniqueKey",
										data.optString("uniqueKey"));
								editor.putInt("loginFlag", 1);
								editor.commit();
								Toast.makeText(LoginActivity.this, "登录成功",
										Toast.LENGTH_LONG).show();
								LoginActivity.this.finish();
								startActivity(new Intent(LoginActivity.this,
										MainActivity_NewActivity.class).putExtra(
										"loginFlag", 1));
							} else {
								Toasts.show(LoginActivity.this, "登录失败:"
										+ jsonObject.getString("message"), 0);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onMyFailure(HttpException error, String msg) {
						Toasts.show(LoginActivity.this, "无法连接服务器，请检查网络", 0);
					}

				});
	}

	private void sendTencentCode(JSONObject re, JSONObject jo) {
		RequestParams params = new RequestParams("UTF-8");

		System.out.println("pushToken:" + registrationID);
		System.out.println("openId:" + jo.optString("openid"));
		System.out.println("accessToken:" + jo.optString("access_token"));
		System.out.println("nickname:" + re.optString("nickname"));
		System.out.println("gender:" + re.optString("gender"));
		System.out.println("avatarURL50:" + re.optString("figureurl_1"));
		System.out.println("avatarURL100:" + re.optString("figureurl_2"));

		params.addQueryStringParameter("pushToken", registrationID);
		params.addQueryStringParameter("adviceType", "android");
		params.addQueryStringParameter("openId", jo.optString("openid"));
		params.addQueryStringParameter("accessToken", jo.optString("access_token"));
		params.addQueryStringParameter("nickname", re.optString("nickname"));
		params.addQueryStringParameter("gender", re.optString("gender"));
		params.addQueryStringParameter("avatarURL50",
				re.optString("figureurl_1"));
		params.addQueryStringParameter("avatarURL100",
				re.optString("figureurl_2"));

		new XUtilsNew().httpPost("user/userQQLogin.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("QQ用户信息:" + result);

				try {
					JSONObject jsonObject = new JSONObject(result);
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
                    Toast.makeText(LoginActivity.this, "登录成功",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,
                            MainActivity_NewActivity.class).putExtra("loginFlag", 1));
                    LoginActivity.this.finish();

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(LoginActivity.this, "无法连接服务器，请检查网络", 0);
			}

		});

	}

	private void userLogin(){
		RequestParams params = new RequestParams("UTF-8");
		params.addQueryStringParameter("username", usernameEdt.getText().toString());
		params.addQueryStringParameter("password", passwordEdt.getText().toString());
		params.addQueryStringParameter("token", registrationID);
		params.addQueryStringParameter("driver", "android");

		new XUtilsNew().httpPost("user/login.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);
					Gson gson = new Gson();
					Type type = new TypeToken<UserLoginModel>() {
					}.getType();
					final UserLoginModel userLogin = gson.fromJson(
							jsonObject.getJSONObject("data").toString(), type);

					//SharedPreferences存储用户Id和uniqueKey
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
					// 提交
					editor.commit();

					startActivity(new Intent(LoginActivity.this,
							MainActivity_NewActivity.class).putExtra(
							"loginFlag", 1));
					LoginActivity.this.finish();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(LoginActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		MobclickAgent.onResume(this);

		boolean getConnectionState = JPushInterface.getConnectionState(LoginActivity.this);
		System.out.println("LoginActivity getConnectionState onResume:" + getConnectionState);

		dissmissProgressDialog();
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		MobclickAgent.onPause(this);

		boolean getConnectionState = JPushInterface.getConnectionState(LoginActivity.this);
		System.out.println("LoginActivity getConnectionState onPause:" + getConnectionState);

	}

	@SuppressWarnings("static-access")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("TAG", "-->onActivityResult " + requestCode + " resultCode="
				+ resultCode);


		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}


		mTencent.onActivityResultData(requestCode, resultCode, data,
				loginListener);

		if (requestCode == Constants.REQUEST_API) {
			// if(resultCode == Constants.RESULT_LOGIN) {
			System.out
					.println("data:" + data + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Tencent.handleResultData(data, loginListener);
			Log.d("TAG", "-->onActivityResult handle logindata");
			// }
		} else if (requestCode == Constants.REQUEST_APPBAR) {
			// if (resultCode == Constants.RESULT_LOGIN) {
			Toasts.show(LoginActivity.this,
					data.getStringExtra(Constants.LOGIN_INFO) + "........", 0);
			// }
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			startActivity(new Intent(LoginActivity.this, MainActivity_NewActivity.class)
					.putExtra("loginFlag", 1));
		}
		return false;
	}

	/**
	 * 显示进度框
	 */
	public void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("拉取第三方授权页面...");
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	public void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}


}