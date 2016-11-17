package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
import com.qizhi.qilaiqiqu.adapter.Activie_ConsultAdapter;
import com.qizhi.qilaiqiqu.model.Active_ConsultModel;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 *  活动咨询
 */
public class Activity_NewConsultActivity extends Activity {

    @InjectView(R.id.layout_NewActiveConsult_back)
    LinearLayout layouttBack;
    @InjectView(R.id.NewActiveConsult_list)
    ListView listView;
    @InjectView(R.id.edt_NewActiveConsult_content)
    EditText edtContent;
    @InjectView(R.id.txt_NewActiveConsult_commit)
    TextView txtCommit;

    private int activityId;

    private List<Active_ConsultModel> list;
    private Activie_ConsultAdapter adapter;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_consult);
        ButterKnife.inject(this);
        init();
        initView();
        getData();
    }

    private void init() {
        list = new ArrayList<Active_ConsultModel>();
        activityId = getIntent().getIntExtra("activityId",-1);
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId",activityId+"");
        params.addBodyParameter("pageIndex","1");
        params.addBodyParameter("pageSize","10");
        new XUtilsNew().httpPost("activity/queryAdviceList.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    list.clear();
                    list = gson.fromJson(object.optJSONArray("dataList").toString(), new TypeToken<ArrayList<Active_ConsultModel>>(){}.getType());
                    adapter = new Activie_ConsultAdapter(Activity_NewConsultActivity.this, list);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewConsultActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    private void initView() {

    }



    @OnClick({R.id.layout_NewActiveConsult_back, R.id.txt_NewActiveConsult_commit})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_NewActiveConsult_back:
                finish();
                break;

            case R.id.txt_NewActiveConsult_commit:
                if("".equals(edtContent.getText().toString())){
                    Toasts.show(this,"内容不能为空哦",0);
                }else{

                    // 提交咨询
                    MobclickAgent.onEvent(Activity_NewConsultActivity.this, new UmengEventUtil().click32);

                    commit();
                }
                break;
        }
    }

    private void commit() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId",activityId+"");
        params.addBodyParameter("comment",edtContent.getText().toString());
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("uniqueKey",preferences.getString("uniqueKey",null));
        new XUtilsNew().httpPost("activity/releaseAdvice.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                edtContent.setText("");
                getData();

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewConsultActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
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
}
