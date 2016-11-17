package com.qizhi.qilaiqiqu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.qizhi.qilaiqiqu.activity.Activity_NewCommentActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewOrderActivity;
import com.qizhi.qilaiqiqu.adapter.OrderActivityAdapter;
import com.qizhi.qilaiqiqu.model.FragmentOrderActivityModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell1 on 2016/9/1.
 */
public class OrderActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener, AdapterView.OnItemClickListener {

    private View view;

    private ListView listView;
    private RefreshLayout swipeLayout;
    private OrderActivityAdapter adapter;

    private SharedPreferences preferences;

    private List<FragmentOrderActivityModel> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_activity, null);

        // 订单tab
        MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click51);

        init();
        initView();
        initEvent();

        getData();

        return view;
    }

    private void init() {
        list = new ArrayList<FragmentOrderActivityModel>();

        preferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        adapter = new OrderActivityAdapter(getActivity(), list);

    }

    private void initView() {
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);

        listView = (ListView) view.findViewById(R.id.list_fragment_order_activity);
        listView.setAdapter(adapter);
    }

    private void initEvent() {

        listView.setOnItemClickListener(this);

        swipeLayout.setOnLoadListener(this);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    int pageIndex = 1;

    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("tag", "HD");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");
        new XUtilsNew().httpPost("message/queryOrderList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<FragmentOrderActivityModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<List<FragmentOrderActivityModel>>() {
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

            }
        });
    }

    public void dataJ() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("tag", "HD");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");

        new XUtilsNew().httpPost("message/queryOrderList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<FragmentOrderActivityModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken< List<FragmentOrderActivityModel>>() {
                    }.getType());

                    if(pageIndex == 1){
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

            }
        });
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // 活动订单查看
        MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click55);

        if(list.get(position).getState() == 1){
            // 未支付
            Intent intent = new Intent(getActivity(), Activity_NewOrderActivity.class);

            intent.putExtra("inWay","dzf");
            intent.putExtra("orderId",list.get(position).getId());

            startActivity(intent);

        }else if(list.get(position).getState() == 2){

            // 支付完成

        }else if(list.get(position).getState() == 3){
            // 支付取消

        }else if(list.get(position).getState() == 4){
            // 待评价
            startActivity(new Intent(getActivity(), Activity_NewCommentActivity.class)
                    .putExtra("activityId",list.get(position).getActivityId())
                    .putExtra("orderId",list.get(position).getId()));
        }else{
            // 已完成

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndex = 1;
        dataJ();
    }
}
