package com.qizhi.qilaiqiqu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.UserLoginModel;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 *  注册
 */
public class RegisterActivity extends MyBaseActivity implements OnClickListener {

	private LinearLayout backLayout;// 返回按钮

	private TextView sendTxt;

	private EditText phoneEdt;
	private EditText passEdt;
	private EditText codeEdt;

	private Timer timer;
	private TimerTask task;

	private int i = 60;
	private int timeFlag = 1;
	private int phoneFlag = 1; // 电话号码输入标识 1为未满11位，2为满11位
	private int passFlag = 1; // 密码输入标识 1为不满足6至13位输入条件，2为满足
	private int finishFlag = 1; // 退出标识 1为不允许推出，2为允许推出

	private ProgressDialog progDialog = null;// 搜索时进度条

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
		setContentView(R.layout.activity_register);
		initView();
		initEvnet();
	}

	private void initView() {
		backLayout = (LinearLayout) findViewById(R.id.layout_registerActivity_back);
		sendTxt = (TextView) findViewById(R.id.btn_registerActivity_send);
		phoneEdt = (EditText) findViewById(R.id.edt_registerActivity_phone);
		passEdt = (EditText) findViewById(R.id.edt_registerActivity_pass);
		codeEdt = (EditText) findViewById(R.id.edt_registerActivity_code);
	}

	private void initEvnet() {
		sendTxt.setOnClickListener(this);
		backLayout.setOnClickListener(this);
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
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable e) {
				if (e.length() == 11) {
					phoneFlag = 2;
				} else {

					phoneFlag = 1;
				}
			}
		});
		passEdt.addTextChangedListener(new TextWatcher() {

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
				if (e.length() > 7 && e.length() <= 13) {
					passFlag = 2;
				} else {
					passFlag = 1;
				}

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_registerActivity_back:
			finish();
			break;

		case R.id.btn_registerActivity_send:
			if (phoneFlag == 2) {
				if (passFlag == 2) {
					if (timeFlag == 1) {
						startTime();
						httpSendCode();
						timeFlag = 2;
					} else {
						new SystemUtil().makeToast(RegisterActivity.this,
								"验证码每60秒发送发送一次");
					}
				} else {
					new SystemUtil().makeToast(RegisterActivity.this,
							"密码为8至13位");
				}
			} else {
				new SystemUtil().makeToast(RegisterActivity.this, "手机号码为11位");
			}
			break;

		default:
			break;
		}
	}

	private void httpSendCode() {
		String url = "common/sendSmsAuthCode.html";
		String mobilePhone = phoneEdt.getText().toString().trim();

		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", mobilePhone);
		params.addBodyParameter("type", "YHZC");

		new XUtilsNew().httpPost(url, params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				new SystemUtil().makeToast(RegisterActivity.this, "验证码已发送");
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(RegisterActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});
	}

	private void httpRegister() {
		finishFlag = 2;
		String url = "user/register.html";
		String mobilePhone = phoneEdt.getText().toString().trim();
		String loginPwd = passEdt.getText().toString().trim();
		String codeNo = codeEdt.getText().toString().trim();

		RequestParams params = new RequestParams();
		params.addBodyParameter("mobilePhone", mobilePhone);
		params.addBodyParameter("password", loginPwd);
		params.addBodyParameter("yzm", codeNo);

		new XUtilsNew().httpPost(url, params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				new SystemUtil().makeToast(RegisterActivity.this,
						"注册成功,自动帮您登录");
				// 友盟计数统计事件
				MobclickAgent.onEvent(RegisterActivity.this,"click2");
				showProgressDialog();
				userLogin();
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				dissmissProgressDialog();
				Toasts.show(RegisterActivity.this, "无法连接服务器，请检查网络", 0);
				if (finishFlag == 2) {
					codeEdt.setText("");
				}
			}
		});
	}

	/**
	 *  5秒启动handle
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

	private void userLogin(){
		String registrationID = JPushInterface
				.getRegistrationID(RegisterActivity.this);

		if(registrationID == null || registrationID.equals("")){
			registrationID = "qilaiqiqu_registrationID";
		}
		RequestParams params = new RequestParams("UTF-8");
		params.addQueryStringParameter("username", phoneEdt.getText().toString());
		params.addQueryStringParameter("password", passEdt.getText().toString());
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
					editor.putString("uniqueKey",
							userLogin.getUniqueKey());
					editor.putString("userName",
							userLogin.getUserName());
					editor.putString("imPassword",
							userLogin.getImPassword());
					editor.putString("mobilePhone",
							userLogin.getMobilePhone());
					editor.putInt("riderId",
							userLogin.getRiderId());
					editor.putString("imUserName",
							userLogin.getImUserName());
					editor.putString("userImage",
							userLogin.getUserImage());
					editor.putString("userType",
							userLogin.getUserType());
					editor.putInt("loginFlag", 1);
					// 提交
					editor.commit();

					// 友盟计数统计事件
					MobclickAgent.onEvent(RegisterActivity.this,"click3");

					startActivity(new Intent(RegisterActivity.this,
							MainActivity_NewActivity.class).putExtra(
							"loginFlag", 1));
					RegisterActivity.this.finish();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				dissmissProgressDialog();
				Toasts.show(RegisterActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});

	}


//	private void login() {
//		RequestParams params = new RequestParams("UTF-8");
//		String registrationID = JPushInterface
//				.getRegistrationID(RegisterActivity.this);
//
//		params.addQueryStringParameter("mobilePhone", phoneEdt.getText().toString());
//		params.addQueryStringParameter("loginPwd", passEdt.getText().toString());
//		params.addQueryStringParameter("pushToken", registrationID);
//		params.addQueryStringParameter("driver", "android");
//
//
//		new XUtilsNew().httpPost("user/login.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
//
//					@Override
//					public void onMyFailure(HttpException error, String msg) {
//						Toasts.show(RegisterActivity.this, "无法连接服务器，请检查网络", 0);
//					}
//
//					@SuppressLint("CommitPrefEdits")
//					@Override
//					public void onMySuccess(ResponseInfo<String> responseInfo) {
//						try {
//							JSONObject jsonObject = new JSONObject(responseInfo.result);
//							Gson gson = new Gson();
//							Type type = new TypeToken<UserLoginModel>() {
//							}.getType();
//							final UserLoginModel userLogin = gson.fromJson(
//									jsonObject.getJSONObject("data").toString(), type);
//
//							/**
//							 * SharedPreferences存储用户Id和uniqueKey
//							 */
//							SharedPreferences sharedPreferences = getSharedPreferences(
//									"userLogin", Context.MODE_PRIVATE);
//							Editor editor = sharedPreferences.edit();// 获取编辑器
//							editor.putInt("userId", userLogin.getUserId());
//							editor.putString("uniqueKey",
//									userLogin.getUniqueKey());
//							editor.putString("userName",
//									userLogin.getUserName());
//							editor.putString("imPassword",
//									userLogin.getImPassword());
//							editor.putString("mobilePhone",
//									userLogin.getMobilePhone());
//							editor.putString("riderId",
//									userLogin.getRiderId()+"");
//							editor.putString("imUserName",
//									userLogin.getImUserName());
//							editor.putString("userImage",
//									userLogin.getUserImage());
//							editor.putInt("loginFlag", 1);
//							editor.commit();
//
//							startActivity(new Intent(RegisterActivity.this,
//									MainActivity_NewActivity.class).putExtra(
//									"loginFlag", 1));
//							RegisterActivity.this.finish();
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				});
//	}

	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("自动为您登录...");
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
		dissmissProgressDialog();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}
}
