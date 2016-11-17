package com.qizhi.qilaiqiqu.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.PersonalCenterActivity;
import com.qizhi.qilaiqiqu.adapter.CareFragmentListAdapter;
import com.qizhi.qilaiqiqu.model.FansModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.RefreshLayout.OnLoadListener;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Administrator
 *			关注列表
 */
public class CareFragment extends Fragment implements OnItemClickListener,OnRefreshListener,OnLoadListener{

	private View view;

	private ListView careList;


	private ArrayList<FansModel> dataList;

	private CareFragmentListAdapter adapter;
	private int pageIndex = 1;
	private boolean isFirst = true;
	private static SharedPreferences preferences;
	private RefreshLayout swipeLayout;
	private View header;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_care,null);
		careList = (ListView) view.findViewById(R.id.list_careFragment);
		preferences = getActivity().getSharedPreferences("userLogin", 0);
		dataList = new ArrayList<FansModel>();
		header = View.inflate(getActivity(),R.layout.header, null);
		swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		careList.addHeaderView(header);
		data();
		return view;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		int userId = dataList.get(position - 1).getUserId();
		startActivity(new Intent(getActivity(), PersonalCenterActivity.class).putExtra("userId", userId));
	}
	private void data(){
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
		params.addBodyParameter("pageIndex", pageIndex + "");
		params.addBodyParameter("pageSize", "10");
		params.addBodyParameter("tag", "2");
		params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));

		new XUtilsNew().httpPost("personal/queryFans.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
			
			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject object = new JSONObject(responseInfo.result);
					Gson gson = new Gson();
					ArrayList<FansModel> careDataList = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<List<FansModel>>() {}.getType());

					dataList.clear();
					dataList.addAll(careDataList);
					adapter = new CareFragmentListAdapter(getActivity(), dataList);
					careList.setAdapter(adapter);

					careList.setOnItemClickListener(CareFragment.this);
					swipeLayout.setOnRefreshListener(CareFragment.this);
					swipeLayout.setOnLoadListener(CareFragment.this);
					careList.setFocusableInTouchMode(false);
						
						
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onMyFailure(HttpException error, String msg) {
				
			}
		});
	}
	private void dataJ() {

		RequestParams params = new RequestParams();
		int quoteUserId = preferences.getInt("userId", -1);
		String pageSize = "10";
		String uniqueKey = preferences.getString("uniqueKey", null);

		params.addBodyParameter("userId", quoteUserId + "");
		params.addBodyParameter("pageIndex", pageIndex + "");
		params.addBodyParameter("pageSize", pageSize);
		params.addBodyParameter("uniqueKey", uniqueKey);
		params.addBodyParameter("tag", "2");
		new XUtilsNew().httpPost("personal/queryFans.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {

			@Override
			public void onMySuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject object = new JSONObject(responseInfo.result);
					Gson gson = new Gson();
					ArrayList<FansModel> careDataList = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<List<FansModel>>() {}.getType());
					if(pageIndex == 1){
						dataList.clear();
					}
					dataList.addAll(careDataList);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(HttpException error, String msg) {

			}
		});
	}

	@Override
	public void onRefresh() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				swipeLayout.setRefreshing(false);
				pageIndex = 1;
				isFirst =true;
					dataJ();
				
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
				isFirst =false;
				dataJ();
			}
		}, 1500);
	}
	
}
