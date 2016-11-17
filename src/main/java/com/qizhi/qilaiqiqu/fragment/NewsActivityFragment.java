package com.qizhi.qilaiqiqu.fragment;

import android.content.Context;
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
import com.qizhi.qilaiqiqu.adapter.ManageAdapter;
import com.qizhi.qilaiqiqu.model.Active_NewModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell1 on 2016/9/1.
 */
public class NewsActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener, AdapterView.OnItemClickListener {

    View view;

    private ListView listView;
    private ManageAdapter adapter;

    private RefreshLayout swipeLayout;

    private SharedPreferences preferences;

    private List<Active_NewModel> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_activity, null);

        init();
        initView();
        initEvent();
        getData();

        return view;
    }

    private void init() {
        adapter = new ManageAdapter(getActivity(),list,0,"news");
        list = new ArrayList<Active_NewModel>();
        preferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.list_fragment_news_activity);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
    }

    private void initEvent() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        swipeLayout.setOnLoadListener(this);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    int pageIndex;
    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("uniqueKey", preferences.getString("uniqueKey", null));
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");

        new XUtilsNew().httpPost("message/queryActivityList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<Active_NewModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<Active_NewModel>() {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                    List<Active_NewModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<Active_NewModel>() {
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

}
