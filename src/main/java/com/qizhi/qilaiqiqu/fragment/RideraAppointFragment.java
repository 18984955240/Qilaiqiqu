package com.qizhi.qilaiqiqu.fragment;

import android.content.Context;
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
import com.qizhi.qilaiqiqu.adapter.RiderAppointAdapter;
import com.qizhi.qilaiqiqu.model.NewsRiderModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.RefreshLayout.OnLoadListener;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.qizhi.qilaiqiqu.utils.XUtilsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *         约骑
 */

public class RideraAppointFragment extends Fragment implements OnItemClickListener, OnRefreshListener, OnLoadListener {
    private View view;
    private ListView agreementList;
    private List<NewsRiderModel> list;
    private RiderAppointAdapter adapter;
    private int pageIndex = 1;
    private SharedPreferences preferences;
    private XUtilsUtil xUtilsUtil;
    private RefreshLayout swipeLayout;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rider_frament_agreementandserve, null);
        agreementList = (ListView) view.findViewById(R.id.rider_fragment_agreementandserve);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        xUtilsUtil = new XUtilsUtil();
        preferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        list = new ArrayList<NewsRiderModel>();
        adapter = new RiderAppointAdapter(getActivity(), list);
        agreementList.setAdapter(adapter);
        agreementList.setOnItemClickListener(this);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);


        return view;
    }

    @Override
    public void onResume() {
        data();
        super.onResume();
    }

    private void data() {
        pageIndex = 1;
        RequestParams params = new RequestParams();

        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("tag", "1");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        new XUtilsNew().httpPost("message/queryRiderList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                System.out.println("message/queryRiderList.html tag=1    responseInfo = "+responseInfo.result);

                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<NewsRiderModel>>() {
                    }.getType();
                    List<NewsRiderModel> lists = gson.fromJson
                            (jsonObject.optJSONArray("dataList").toString(), type);
                    list.clear();
                    list.addAll(lists);
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
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        /*
		if(list.get(position).getIsAgree().equals("normal")){
			intent.putExtra("isRider", 1);
		}else if(list.get(position).getIsAgree().equals("true")){
			intent.putExtra("isRider", 2);
		}else if(list.get(position).getIsAgree().equals("false")){
			intent.putExtra("isRider", 3);
		}
		intent.putExtra("userId", list.get(position).getUserId());
		intent.putExtra("applyId", list.get(position).getApplyId());
		startActivity(intent);*/


    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                pageIndex = 1;
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
                dataJ();
            }
        }, 1500);
    }

    private void dataJ() {
        RequestParams params = new RequestParams();

        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("tag", "1");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        new XUtilsNew().httpPost("message/queryRiderList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {

            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                System.out.println("message/queryRiderList.html tag=1    responseInfo = "+responseInfo.result);

                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<NewsRiderModel>>() {
                    }.getType();
                    List<NewsRiderModel> lists = gson.fromJson
                            (jsonObject.optJSONArray("dataList").toString(), type);
                    if (pageIndex == 1) {
                        list.clear();
                    }
                    list.addAll(lists);

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getActivity(), "网络异常", 0);
            }
        });
    }
}
