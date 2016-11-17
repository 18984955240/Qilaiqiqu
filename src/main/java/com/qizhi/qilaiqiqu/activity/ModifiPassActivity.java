package com.qizhi.qilaiqiqu.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * 修改密码
 */

public class ModifiPassActivity extends MyBaseActivity implements OnClickListener {

	private LinearLayout backLayout;

	private EditText oldEdt;
	private EditText newEdt;
	private EditText confirmEdt;

	private TextView commitTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_modifi_pass);
		super.onCreate(savedInstanceState);
		initView();
		initEvent();
	}

	private void initView() {
		backLayout = (LinearLayout) findViewById(R.id.layout_modifiPassActivity_back);

		oldEdt = (EditText) findViewById(R.id.edt_modifiPassActivity_old);
		newEdt = (EditText) findViewById(R.id.edt_modifiPassActivity_new);
		confirmEdt = (EditText) findViewById(R.id.edt_modifiPassActivity_confirm);

		commitTxt = (TextView) findViewById(R.id.txt_modifiPassActivity_commit);
	}

	private void initEvent() {
		commitTxt.setOnClickListener(this);
		backLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_modifiPassActivity_back:
			finish();
			break;

		case R.id.txt_modifiPassActivity_commit:
			String password = newEdt.getText().toString().trim();
			String confirm = confirmEdt.getText().toString().trim();
			if (!password.equals(confirm)) {
				new SystemUtil().makeToast(ModifiPassActivity.this, "两次输入不一致!"
						+ "password:" + password + ",confirm:" + confirm);
			} else {
				new SystemUtil().makeToast(ModifiPassActivity.this, "两次输入一致!"+"password:"
						+ password + ",confirm:" + confirm);
			}
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
	
}
