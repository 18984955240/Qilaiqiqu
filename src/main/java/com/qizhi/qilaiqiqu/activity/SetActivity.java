package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.AccessTokenKeeper;
import com.qizhi.qilaiqiqu.utils.ConstantsUtil;
import com.qizhi.qilaiqiqu.utils.DataCleanManager;
import com.qizhi.qilaiqiqu.utils.LogoutAPI;
import com.qizhi.qilaiqiqu.utils.PushSlideSwitchView;
import com.qizhi.qilaiqiqu.utils.PushSlideSwitchView.OnSwitchChangedListener;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.WBConstants;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;

/**
 *	设置
 */

public class SetActivity extends MyBaseActivity implements
		OnClickListener {

	private LinearLayout backLayout;
	private LinearLayout opintionLayout;
	private LinearLayout introduceLayout;
	private LinearLayout clearCacheLayout;
	
	private PushSlideSwitchView view1;
	private PushSlideSwitchView view2;
	private PushSlideSwitchView view3;

	private int logoutFlag = 1;// 为1未登录,为2已登录

	private TextView logoutTxt;
	private TextView cacheTxt;
	private TextView nowTxt;

	DataCleanManager cleanManager;

	private SharedPreferences sp;
	Editor editor;

	private Tencent mTencent;

	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 注销操作回调 */
    private LogOutRequestListener mLogoutRequestListener = new LogOutRequestListener();
	
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				httpQuery();
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);

		sp = getSharedPreferences("userLogin", 0);
		if (sp.getInt("userId", -1) == -1) {
			logoutFlag = 1;
		} else {
			logoutFlag = 2;
		}

		initView();
		initEvent();
		getNowVersion();
	}


	private void initView() {
		editor = sp.edit();// 获取编辑器

		view1 = (PushSlideSwitchView) findViewById(R.id.push_set_warm_switchview1);
		// view2 = (PushSlideSwitchView)
		// findViewById(R.id.push_set_warm_switchview2);
		// view3 = (PushSlideSwitchView)
		// findViewById(R.id.push_set_warm_switchview3);

		cacheTxt = (TextView) findViewById(R.id.txt_setActivity_cache);
		logoutTxt = (TextView) findViewById(R.id.txt_setActivity_logout);
		nowTxt = (TextView) findViewById(R.id.txt_setActivity_nowVersion);
		backLayout = (LinearLayout) findViewById(R.id.layout_setActivity_back);
		opintionLayout = (LinearLayout) findViewById(R.id.layout_setActivity_opintion);
		introduceLayout = (LinearLayout) findViewById(R.id.layout_setActivity_introduce);
		clearCacheLayout = (LinearLayout) findViewById(R.id.layout_setActivity_clearCache);

		mTencent = Tencent.createInstance(ConstantsUtil.APP_ID_TX,
				this.getApplicationContext());
	}

	private void initEvent() {
		if (logoutFlag == 2) {
			logoutTxt.setVisibility(View.VISIBLE);
			opintionLayout.setVisibility(View.VISIBLE);
		} else {
			logoutTxt.setVisibility(View.GONE);
			opintionLayout.setVisibility(View.GONE);
		}
		try {
			cacheTxt.setText(cleanManager.getTotalCacheSize(SetActivity.this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		logoutTxt.setOnClickListener(this);
		backLayout.setOnClickListener(this);
		opintionLayout.setOnClickListener(this);
		introduceLayout.setOnClickListener(this);
		clearCacheLayout.setOnClickListener(this);
		view1.setChecked(sp.getBoolean("view1", true));
		System.out.println("sp.getBoolean(view1, true):"
				+ sp.getBoolean("view1", true));
		view1.setOnChangeListener(new OnSwitchChangedListener() {

			@Override
			public void onSwitchChange(PushSlideSwitchView switchView,
					boolean isChecked) {
				EMChatOptions options = EMChatManager.getInstance()
						.getChatOptions();
				options = EMChatManager.getInstance().getChatOptions();
				if (isChecked) {
					System.out.println("isChecked:" + isChecked);
					editor.putBoolean("view1", isChecked);
					editor.commit();
					options.setNotifyBySoundAndVibrate(isChecked);
					System.out.println("sp.getBoolean(view1, true):"
							+ sp.getBoolean("view1", false));
				} else {
					System.out.println("isChecked:" + isChecked);
					editor.putBoolean("view1", isChecked);
					editor.commit();
					options.setNotifyBySoundAndVibrate(isChecked);
					System.out.println("sp.getBoolean(view1, true):"
							+ sp.getBoolean("view1", false));
				}
			}
		});
	}

	private void getNowVersion() {
		String pName = "com.qizhi.qilaiqiqu";
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(
					pName, PackageManager.GET_CONFIGURATIONS);
			nowTxt.setText(pinfo.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_setActivity_back:
			finish();
			break;

		case R.id.layout_setActivity_opintion:
			startActivity(new Intent(SetActivity.this, OpinionActivity.class));
			break;

		case R.id.layout_setActivity_introduce:
			startActivity(new Intent(SetActivity.this, IntroduceActivity.class));
			break;

		case R.id.txt_setActivity_logout:
			logOut();
			logoutQQ();
			logoutWB();
			break;

		case R.id.layout_setActivity_clearCache:
			clearCache();
			break;

		default:
			break;
		}
	}

	private void clearCache() {
		cleanManager.clearAllCache(SetActivity.this);
		try {
			cacheTxt.setText(cleanManager.getTotalCacheSize(SetActivity.this));
			new SystemUtil().makeToast(this, "缓存已清除");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void logOut() {
		EMChatManager.getInstance().logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				Log.d("LOGOUT", "环信登出成功!");
				System.err.println("环信登出成功!");

				SharedPreferences sp = getSharedPreferences("userInfo",
						Context.MODE_PRIVATE);
				Editor userInfo_Editor = sp.edit();
				userInfo_Editor.putBoolean("isLogin", false);
				userInfo_Editor.commit();
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}

			@Override
			public void onProgress(int progress, String status) {

			}

			@Override
			public void onError(int code, String message) {
				Log.d("LOGOUT", "环信登出失败!" + message);
				System.err.println("环信登出失败!" + message);
			}
		});
	}

	public void logoutQQ() {
		mTencent.logout(this);
	}

	public void logoutWB() {
		// 获取当前已保存过的 Token
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.format(new java.util.Date(mAccessToken.getExpiresTime()));
		}
		// 注销按钮
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			new LogoutAPI(SetActivity.this, WBConstants.APP_KEY,
					mAccessToken).logout(mLogoutRequestListener);
		} else {
//			Toasts.show(SetActivity.this, "注销失败，请检查 Token 是否正确（一个 token 不能重复注销多次）", 0);
		}

	}

	/**
     * 注销按钮的监听器，接收注销处理结果。（API请求结果的监听器）
     */
    private class LogOutRequestListener implements RequestListener {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String value = obj.getString("result");
                    
                    if ("true".equalsIgnoreCase(value)) {
                        AccessTokenKeeper.clear(SetActivity.this);
                        mAccessToken = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } 

        @Override
        public void onWeiboException(WeiboException e) {
        	Toasts.show(SetActivity.this, "注销失败", 0);
        }
    }
	
	private void httpQuery() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", sp.getInt("userId", -1) + "");
		params.addBodyParameter("driver", "android");

		new XUtilsNew().httpPost("user/layout.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				editor.putInt("userId", -1);
				editor.putInt("riderId", -1);
				editor.putString("location", null);
				editor.putString("userImage", null);
				editor.putString("uniqueKey", null);
				editor.putString("imPassword", null);
				editor.putString("imUserName", null);
				editor.putString("mobilePhone", null);
				editor.putString("riderId", null);
				editor.putString("userType", null);
				editor.commit();
				SetActivity.this.finish();
				Toasts.show(SetActivity.this, "注销成功", 0);
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(SetActivity.this, "无法连接服务器，请检查网络", 0);
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
