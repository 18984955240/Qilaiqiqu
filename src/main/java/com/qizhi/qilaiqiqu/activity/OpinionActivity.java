package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

/**
 * 建议反馈
 */

public class OpinionActivity extends MyBaseActivity implements OnClickListener{

	private TextView sendTxt;

	private EditText emailEdt;
	private EditText opintionEdt;

	private LinearLayout backLayout;

	private XUtilsUtil xUtilsUtil;

	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_opintion);
		super.onCreate(savedInstanceState);
		initView();
		initEvent();
	}

	private void initView() {
		xUtilsUtil = new XUtilsUtil();
		preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

		sendTxt = (TextView) findViewById(R.id.txt_opintionActivity_send);

		emailEdt = (EditText) findViewById(R.id.edt_opintionActivity_email);
		opintionEdt = (EditText) findViewById(R.id.edt_opintionActivity_opintion);

		backLayout = (LinearLayout) findViewById(R.id.layout_opinionActivity_back);

	}

	private void initEvent() {
		sendTxt.setOnClickListener(this);
		backLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_opintionActivity_send:
			if (preferences.getInt("userId", -1) != -1) {
				if (isEmail(emailEdt.getText().toString().trim())) {
					commit();
				}else{
					new SystemUtil().makeToast(this, "请输入正确的邮箱格式");
				}
			}

			break;

		case R.id.layout_opinionActivity_back:
			finish();
			break;

		default:
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

	private void commit(){
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
		params.addBodyParameter("email", emailEdt.getText().toString().trim());
		params.addBodyParameter("content", opintionEdt.getText().toString().trim());
		new XUtilsNew().httpPost("common/suggest.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				Toasts.show(OpinionActivity.this, "谢谢您的反馈，我们会尽快处理",0);
				finish();
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(OpinionActivity.this, "无法连接服务器，请检查网络", 0);
			}
		});
	}

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|"
				+ "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
}
