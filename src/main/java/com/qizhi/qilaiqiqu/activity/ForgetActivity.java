package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
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

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

/**
 * 忘记密码
 */
public class ForgetActivity extends MyBaseActivity implements OnClickListener {

    @InjectView(R.id.layout_forgetActivity_back)
    LinearLayout backLauout;
    @InjectView(R.id.txt_forgetActivity_commit)
    TextView commitTxt;// 确认
    @InjectView(R.id.edt_forgetActivity_phone)
    EditText phoneEdt;// 手机号码
    @InjectView(R.id.edt_forgetActivity_code)
    EditText codeEdt;// 验证码
    @InjectView(R.id.btn_forgetActivity_send)
    Button sendBtn; // 发送验证码
    @InjectView(R.id.edt_forgetActivity_newPass)
    EditText newPassEdt;// 密码
    @InjectView(R.id.edt_forgetActivity_confirm)
    EditText confirmEdt;// 确认密码

    private Timer timer;
    private TimerTask task;

    private int i = 60;
    private int timeFlag = 1;
    private int passFlag = 1; // 密码输入标识 1为不满足6至13位输入条件，2为满足
    private int phoneFlag = 1;
    private int finishFlag = 1; // 退出标识 1为不允许推出，2为允许推出

    private int length; //新密码长度

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            sendBtn.setFocusableInTouchMode(false);
            sendBtn.setText("重新发送(" + i + ")");
            if (msg.arg1 == 0) {
                stopTime();
            } else {
                startTime();
            }
            super.handleMessage(msg);
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgets);
        ButterKnife.inject(this);
        initView();
        initEvent();

    }

    private void initView() {

        sendBtn = (Button) findViewById(R.id.btn_forgetActivity_send);
        commitTxt = (TextView) findViewById(R.id.txt_forgetActivity_commit);

        codeEdt = (EditText) findViewById(R.id.edt_forgetActivity_code);
        phoneEdt = (EditText) findViewById(R.id.edt_forgetActivity_phone);
        confirmEdt = (EditText) findViewById(R.id.edt_forgetActivity_confirm);
        newPassEdt = (EditText) findViewById(R.id.edt_forgetActivity_newPass);

        backLauout = (LinearLayout) findViewById(R.id.layout_forgetActivity_back);
    }

    private void initEvent() {
        // 使确认密码输入框失去焦点
        confirmEdt.setFocusable(false);
        confirmEdt.setFocusableInTouchMode(false);
        confirmEdt.requestFocus();
        confirmEdt.findFocus();

        // 使新的密码输入框失去焦点
        newPassEdt.setFocusable(false);
        newPassEdt.setFocusableInTouchMode(false);
        newPassEdt.requestFocus();
        newPassEdt.findFocus();


        sendBtn.setOnClickListener(this);
        commitTxt.setOnClickListener(this);
        backLauout.setOnClickListener(this);
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

                    confirmEdt.setFocusable(true);
                    confirmEdt.setFocusableInTouchMode(true);
                    confirmEdt.requestFocus();
                    confirmEdt.findFocus();

                    newPassEdt.setFocusable(true);
                    newPassEdt.setFocusableInTouchMode(true);
                    newPassEdt.requestFocus();
                    newPassEdt.findFocus();

                }
            }
        });

        newPassEdt.addTextChangedListener(new TextWatcher() {
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
                if (e.length() > 5 && e.length() <= 13) {
                    passFlag = 2;
                    length = e.length();
                } else {
                    passFlag = 1;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forgetActivity_send:
                if (phoneFlag == 2) {
                    if (timeFlag == 1) {
                        startTime();
                        httpSendCode();
                        timeFlag = 2;
                    } else {
                        new SystemUtil().makeToast(ForgetActivity.this,
                                "验证码每60秒发送发送一次");
                    }
                } else {
                    new SystemUtil().makeToast(ForgetActivity.this, "手机号码为11位");
                }
                break;

            case R.id.layout_forgetActivity_back:
                finish();
                break;

            case R.id.txt_forgetActivity_commit:
                if (!confirmEdt.getText().toString().trim().equals(newPassEdt.getText().toString().trim())) {
                    new SystemUtil().makeToast(ForgetActivity.this, "两次输入不一致");
//                    newPassEdt.setText("");
//                    confirmEdt.setText("");
                } else {
                    httpModifi();
                }
                break;

            default:
                break;
        }

    }

    private void httpSendCode() {
        String mobilePhone = phoneEdt.getText().toString().trim();

        RequestParams params = new RequestParams();
        params.addBodyParameter("mobile", mobilePhone);
        params.addBodyParameter("type", "ZHMM");

        new XUtilsNew().httpPost("common/sendSmsAuthCode.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                new SystemUtil().makeToast(ForgetActivity.this, "验证码已发送");
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(ForgetActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    private void httpModifi() {
        finishFlag = 2;
        String mobilePhone = phoneEdt.getText().toString().trim();
        String password = confirmEdt.getText().toString().trim();
        String codeNo = codeEdt.getText().toString().trim();

        RequestParams params = new RequestParams();
        params.addBodyParameter("mobile", mobilePhone);
        params.addBodyParameter("password", password);
        params.addBodyParameter("yzm", codeNo);

        new XUtilsNew().httpPost("user/updPassword.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                new SystemUtil().makeToast(ForgetActivity.this, "修改成功");
                finish();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(ForgetActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 秒启动handle
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
        sendBtn.setText("重新发送");
        timer.cancel();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
