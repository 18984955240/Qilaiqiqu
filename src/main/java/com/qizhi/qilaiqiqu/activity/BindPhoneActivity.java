package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 *  绑定手机
 */
public class BindPhoneActivity extends MyBaseActivity implements OnClickListener {

	private LinearLayout backLayout;// 返回按钮

	private TextView sendTxt;

	private int i = 60;
	private EditText phoneEdt;
	private EditText codeEdt;

	private Timer timer;
	private TimerTask task;
	private int timeFlag = 1;
	private int phoneFlag = 1; // 电话号码输入标识 1为未满11位，2为满11位
	private int finishFlag = 1; // 退出标识 1为不允许推出，2为允许推出
	private int userId;
	private String uniqueKey;


	private SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			sendTxt.setFocusableInTouchMode(false);
			sendTxt.setText("重新发送(" + i + ")");
			if (msg.arg1 == 0) {
				stopTime();
			} else {
				startTime();
			}
			super.handleMessage(msg);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_phone);

		initView();
		initEvnet();
	}

	private void initView() {
		preferences = getSharedPreferences("userLogin", 0);

		backLayout = (LinearLayout) findViewById(R.id.layout_bindphoneactivity_back);

		sendTxt = (TextView) findViewById(R.id.btn_bindphoneactivity_send);

		phoneEdt = (EditText) findViewById(R.id.edt_bindphoneactivity_phone);
		codeEdt = (EditText) findViewById(R.id.edt_bindphoneactivity_code);

		userId = preferences.getInt("userId",-1);
		uniqueKey = preferences.getString("uniqueKey",null);
	}

	private void initEvnet() {
		backLayout.setOnClickListener(this);
		sendTxt.setOnClickListener(this);
		codeEdt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable e) {
				if (e.length() == 6) {
					httpRegister();
					codeEdt.setText("");
				}
			}
		});
		phoneEdt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() != 11) {
					phoneFlag = 1;
				} else {
					phoneFlag = 2;
				}
			}
		});
	}

	/**
	 * 手机获取验证码
	 */
	private void httpSendCode() {
		String url = "common/sendSmsAuthCode.html";
		mobilePhone = phoneEdt.getText().toString().trim();

		RequestParams params = new RequestParams();
		params.addBodyParameter("mobilePhone", mobilePhone);
		params.addBodyParameter("type", "XGSJHM");

		new XUtilsNew().httpPost("common/sendSmsAuthCode.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				if (finishFlag == 1) {
					new SystemUtil().makeToast(BindPhoneActivity.this, "验证码已发送");
				} else {
					Intent intent = new Intent();
					intent.putExtra("mobilePhone", mobilePhone);
					setResult(4, intent);
					finish();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(BindPhoneActivity.this, "无法连接服务器，请检查网络", 0);
				if (finishFlag == 2) {
					codeEdt.setText("");
				}
			}

		});
	}

	String mobilePhone;
	private void httpRegister() {
		finishFlag = 2;
		String url = "mobile/user/changeUserMobilePhone.html";
		String codeNo = codeEdt.getText().toString().trim();

		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", mobilePhone);
		params.addBodyParameter("uniqueKey", uniqueKey);
		params.addBodyParameter("userId", userId + "");
		params.addBodyParameter("yzm", codeNo);

        new XUtilsNew().httpPost("user/updatePhone.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if (finishFlag == 2) {
                        editor = preferences.edit();
                        editor.putString("mobilePhone", mobilePhone);
                        // 记得提交
                        editor.commit();

                        new SystemUtil().makeToast(BindPhoneActivity.this, "绑定成功");
                        Intent intent = new Intent();
                        intent.putExtra("mobilePhone", mobilePhone);
                        setResult(4, intent);
                        finishFlag = 1;
                        finish();
                    }
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(BindPhoneActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_bindphoneactivity_back:
			finish();
			break;
		case R.id.btn_bindphoneactivity_send:
			if (phoneFlag == 2) {
				if (timeFlag == 1) {
					startTime();
					httpSendCode();
					timeFlag = 2;
				} else {
					new SystemUtil().makeToast(BindPhoneActivity.this,
							"验证码每60秒发送发送一次");
				}
			} else {
				new SystemUtil().makeToast(BindPhoneActivity.this, "手机号码为11位");
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 5秒启动handle
	 */
	private void startTime() {
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				i--;
				Message message = handler.obtainMessage();
				message.arg1 = i;
				handler.sendMessage(message);
			}

		};

		timer.schedule(task, 1000);
	}

	private void stopTime() {
		timeFlag = 1;
		i = 60;
		sendTxt.setText("重新发送");
		timer.cancel();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}

}
