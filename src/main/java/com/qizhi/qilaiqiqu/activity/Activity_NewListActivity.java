package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.ManageAdapter;
import com.qizhi.qilaiqiqu.model.Active_NewModel;
import com.qizhi.qilaiqiqu.utils.IMMLeaks;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 *  活动分类列表
 */
public class Activity_NewListActivity extends MyBaseActivity implements AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener,View.OnClickListener {

    private TextView txtTitle;
    private LinearLayout lytBack;
    private ListView listActivity;

    private SharedPreferences preferences;

    private ManageAdapter adapter;
    private List<Active_NewModel> dataList;

    private RefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new_list);

        init();
        initView();
        initEvent();
        getData();
    }


    private void init(){
        preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        title = getIntent().getStringExtra("title");
        cateId = getIntent().getIntExtra("cateId",0);

        dataList = new ArrayList<Active_NewModel>();


    }

    private void initView() {
        txtTitle = (TextView) findViewById(R.id.txt_title);
        lytBack = (LinearLayout) findViewById(R.id.layout_back);
        listActivity = (ListView) findViewById(R.id.list_activity);
        swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lytBack.setOnClickListener(this);

    }

    private void initEvent() {
        txtTitle.setText(title);

        swipeLayout.setOnLoadListener(this);
        swipeLayout.setOnRefreshListener(this);
        listActivity.setOnItemClickListener(this);

        adapter = new ManageAdapter(this, dataList , preferences.getInt("userId", -1),"main");
        listActivity.setAdapter(adapter);
    }

    private int pageIndex = 1;
    private int cateId = 0;
    private String title = "";

    private void getData(){
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("pageIndex",pageIndex+"");
        params.addBodyParameter("pageSize","10");
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        params.addBodyParameter("cateId",cateId+"");

        new XUtilsNew().httpPost("activity/queryListByCate.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    ArrayList<Active_NewModel> lists = gson.fromJson(object.optJSONArray("dataList").toString(), new TypeToken<ArrayList<Active_NewModel>>(){}.getType());

                    if(pageIndex == 1){
                        dataList.clear();
                    }
                    dataList.addAll(lists);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewListActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Activity_NewListActivity.this, Activity_NewDetailActivity.class);
        intent.putExtra("activityId", dataList.get(position).getId());
        intent.putExtra("activityFlag", false);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                pageIndex = 1;
                getData();

            }
        }, 1500);
    }

    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setLoading(false);
                pageIndex = pageIndex + 1;
                getData();
            }
        }, 1500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_back:
                finish();
                break;
        }
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
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }
}
