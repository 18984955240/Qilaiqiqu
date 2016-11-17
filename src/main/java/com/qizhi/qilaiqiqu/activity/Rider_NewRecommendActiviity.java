package com.qizhi.qilaiqiqu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.RiderRecommendAdapter;
import com.qizhi.qilaiqiqu.model.Search_RiderModel;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑金牌推荐
 */
public class Rider_NewRecommendActiviity extends MyBaseActivity implements
		OnClickListener, OnItemClickListener {
	
	private LinearLayout backLayout;
	private ListView riderList;
	private RiderRecommendAdapter adapter;
	private List<Search_RiderModel> list;
	private int pageIndex = 1;
	private SharedPreferences preferences;
	private XUtilsUtil xUtilsUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rider_recommend);
		initView();
		initEvent();
	}

	private void initView() {
		list = new ArrayList<Search_RiderModel>();
		preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
		xUtilsUtil = new XUtilsUtil();
		
		backLayout = (LinearLayout) findViewById(R.id.layout_actioncenteractivity_back);
		riderList = (ListView) findViewById(R.id.list_activityriderrecommend_list);
		adapter = new RiderRecommendAdapter(this, list);
		riderList.setAdapter(adapter);
		riderList.setDividerHeight(0);
		data();
		
	}

	private void initEvent() {
		backLayout.setOnClickListener(this);
		riderList.setOnItemClickListener(this);
	}

	private void data() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("pageIndex","1");
		params.addBodyParameter("pageSize","20");
		new XUtilsNew().httpPost("rider/queryTjRiderList.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject object = new JSONObject(responseInfo.result);

					Gson gson = new Gson();
					List<Search_RiderModel> lists = gson.fromJson(object.optJSONArray("dataList").toString(),
							new TypeToken<ArrayList<Search_RiderModel>>(){}.getType());
					list.clear();
					list.addAll(lists);
					adapter.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {
				Toasts.show(Rider_NewRecommendActiviity.this, "无法连接服务器，请检查网络", 0);
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		startActivity(new Intent(this,
				Rider_NewDetailActivity.class)
		.putExtra("riderId", list.get(position).getId()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_actioncenteractivity_back:
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

}
