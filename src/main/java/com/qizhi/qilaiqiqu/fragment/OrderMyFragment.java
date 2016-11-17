package com.qizhi.qilaiqiqu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.Rider_NewOrderActivity;
import com.qizhi.qilaiqiqu.adapter.OrderFragmentAdapter;
import com.qizhi.qilaiqiqu.model.FragmentOrderRiderModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leiqian on 2016/6/7.
 * 我约别人:我的订单
 */
public class OrderMyFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    ListView listFragmentOrderList;
    RefreshLayout swipeLayout;

    private SharedPreferences preferences;
    private List<FragmentOrderRiderModel> list = new ArrayList<FragmentOrderRiderModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_myorder, null);

        // 陪骑订单
        MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click53);

        initView(view);
        initEvent();
        getData();

        return view;
    }

    OrderFragmentAdapter adapter;

    private void initView(View view) {

        swipeLayout = (RefreshLayout) view.findViewById(R.id.fragment_container);
        listFragmentOrderList = (ListView) view.findViewById(R.id.list_fragment_orderList);

        preferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        adapter = new OrderFragmentAdapter(getActivity(), list, "my");
        listFragmentOrderList.setAdapter(adapter);

    }

    private void initEvent() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);

        listFragmentOrderList.setOnItemClickListener(this);

    }

    int pageIndex = 0;
    int productId = -1;

    public void getData() {
        pageIndex = 1;

        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("tag", "PQ");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        new XUtilsNew().httpPost("message/queryOrderList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<FragmentOrderRiderModel> lists = gson.fromJson(object.optJSONArray("dataList").toString(), new TypeToken<List<FragmentOrderRiderModel>>() {
                    }.getType());

                    list.clear();
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(getActivity(), "无法连接服务器，请检查网络", 0);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 陪骑订单查看
        MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click54);

        startActivity(new Intent(getActivity(), Rider_NewOrderActivity.class).putExtra("orderId", list.get(position).getOrderId()));
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setLoading(false);
                pageIndex = pageIndex + 1;
                reData();
            }
        }, 1500);
    }


    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                pageIndex = 1;
                reData();

            }

        }, 1500);

    }

    public void reData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("tag", "PQ");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        new XUtilsNew().httpPost("message/queryOrderList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<FragmentOrderRiderModel> lists = gson.fromJson(object.optJSONArray("dataList").toString(), new TypeToken<ArrayList<FragmentOrderRiderModel>>() {
                    }.getType());

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
                Toasts.show(getActivity(), "无法连接服务器，请检查网络", 0);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if(null != preferences.getString("uniqueKey", null)){
            pageIndex = 1;
            reData();
        }

    }
}
