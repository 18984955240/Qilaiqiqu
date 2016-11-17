package com.qizhi.qilaiqiqu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.CharacterCommentAdapter;
import com.qizhi.qilaiqiqu.model.CharacterCommentModel;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 人物志评论
 */
public class CharacterCommentActivity extends MyBaseActivity {

    @InjectView(R.id.layout_character_comment_back)
    LinearLayout layoutBack;
    @InjectView(R.id.layout_character_comment_title)
    TextView layoutTitle;
    @InjectView(R.id.layout_character_comment_time)
    TextView layoutTime;
    @InjectView(R.id.layout_character_comment_browse)
    TextView layoutBrowse;
    @InjectView(R.id.layout_character_comment_comment)
    TextView layoutComment;
    @InjectView(R.id.edt_character_comment_content)
    EditText layoutContent;
    @InjectView(R.id.layout_character_comment_submit)
    TextView layoutSubmit;
    @InjectView(R.id.layout_character_comment_list)
    ListView layoutList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_comment);
        ButterKnife.inject(this);
        initListView();
        initView();
        getCommentData();



    }

    private InputMethodManager imm;
    private void initView() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(layoutContent.getWindowToken(), 0);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        layoutTitle.setText(getIntent().getStringExtra("title"));
        layoutTime.setText(getIntent().getStringExtra("createdate"));
        layoutBrowse.setText(getIntent().getIntExtra("scannum",-1)+"次浏览");
        layoutComment.setText("("+getIntent().getIntExtra("comment_num",-1)+")");
    }

    private CharacterCommentAdapter adapter;
    private SharedPreferences sharedPreferences;
    private void initListView() {
        characterId = getIntent().getIntExtra("characterId",-1);
        sharedPreferences = getSharedPreferences("userLogin",
                Context.MODE_PRIVATE);
        adapter = new CharacterCommentAdapter(CharacterCommentActivity.this,list);
        layoutList.setAdapter(adapter);
    }

    @OnClick({R.id.layout_character_comment_back, R.id.layout_character_comment_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_character_comment_back:
                finish();
                break;
            case R.id.layout_character_comment_submit:
                if (sharedPreferences.getInt("userId", -1) == -1) {
                    Toasts.show(this, "请登录", 0);
                    // new SystemUtil().makeToast(this, "请登录");
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;
                }
                if(isSend){
                    if(layoutContent.getText().toString().trim() == null ||layoutContent.getText().toString().trim().equals("") ){
                        Toasts.show(CharacterCommentActivity.this,"评论内容不能为空",0);
                    }else{
                        comnent();
                    }
                } else {
                    Toasts.show(CharacterCommentActivity.this,"请稍侯再评论",0);
                }

                break;
        }
    }

    List<CharacterCommentModel> list = new ArrayList<CharacterCommentModel>();

    public void getCommentData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("characterId",characterId+"");
        params.addBodyParameter("pageIndex","1");
        params.addBodyParameter("pageSize","20");

        new XUtilsNew().httpPost("character/queryComments.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                try {
                    JSONObject object = new JSONObject(result);

                    Gson gson = new Gson();
                    List<CharacterCommentModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(),new TypeToken<List<CharacterCommentModel>>(){}.getType());

                    layoutBrowse.setText(object.getString("scanNum")+"次浏览");
                    layoutComment.setText("("+object.getString("commentNum")+")");

                    list.clear();
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(CharacterCommentActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });

    }

    int characterId;
    private void comnent() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("characterId",characterId+"");
        params.addQueryStringParameter("commentMemo", layoutContent.getText().toString());
        params.addQueryStringParameter("userId", sharedPreferences.getInt("userId", -1) + "");
        params.addQueryStringParameter("uniqueKey", sharedPreferences.getString("uniqueKey", null));

        new XUtilsNew().httpPost("character/releaseComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                startTime();
                layoutContent.setText("");
                getCommentData();
                imm.hideSoftInputFromWindow(layoutContent.getWindowToken(), 0);
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(CharacterCommentActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });


    }


    /**
     * 显示进度框
     */
    private ProgressDialog progDialog = null;// 评论时进度条
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在评论...");
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

    /**
     * 3秒启动handle
     */

    private boolean isSend = true;

    private Timer timer;
    private TimerTask task;

    private void startTime() {
        isSend = false;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 4000);
    }

    private void stopTime() {
        isSend = true;
        timer.cancel();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                stopTime();
            }
        }
    };

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
        super.onPause();
    }
}
