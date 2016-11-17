package com.qizhi.qilaiqiqu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.CharacterListAdapter;
import com.qizhi.qilaiqiqu.model.CharacterModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.Toasts;
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
 *  人物志列表
 */
public class CharacterListActivity extends MyBaseActivity implements SwipeRefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener{

    @InjectView(R.id.layout_characterListActivity_back)
    LinearLayout layoutBack;
    @InjectView(R.id.list_characterListActivity_list)
    ListView listList;
    @InjectView(R.id.swipe_container)
    RefreshLayout swipeLayout;


    CharacterListAdapter adapter;
    CharacterModel model = new CharacterModel();
    List<CharacterModel> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        ButterKnife.inject(this);
        initEvent();
        getCharacterList();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
        getCharacterList();
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
        super.onPause();
    }

    private void initEvent() {
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
        adapter = new CharacterListAdapter(list,this);
        listList.setAdapter(adapter);
        listList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CharacterListActivity.this,CharacterDetailsActivity.class)
                        .putExtra("scannum",list.get(position).getScanNum())
                        .putExtra("characterId",list.get(position).getCharacterId())
                        .putExtra("comment_num",list.get(position).getComment_num())
                        .putExtra("url",list.get(position).getUrl())
                        .putExtra("title",list.get(position).getTitle())
                        .putExtra("createdate",list.get(position).getCreateDate())
                );
            }
        });
    }
    @OnClick({R.id.layout_characterListActivity_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_characterListActivity_back:
                finish();
                break;
        }

    }



    int pageIndex = 1;

    public void getCharacterList() {
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("pageIndex","1");
        params.addQueryStringParameter("pageSize","10");
        new XUtilsNew().httpPost("character/queryCharacterList.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("responseInfo.result=" + responseInfo.result);
                try {
                    JSONObject object = new JSONObject(result);
                    Gson gson = new Gson();
                    List<CharacterModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(),new TypeToken<ArrayList<CharacterModel>>() {}.getType());
                    list.clear();
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(CharacterListActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    /**
     * 数据的刷新和加载
     */
    private void dataJ() {
        String url = "common/queryCharacterDemo.html";
        RequestParams params = new RequestParams("UTF-8");
        params.addQueryStringParameter("pageIndex",pageIndex+"");
        params.addQueryStringParameter("pageSize","10");
        new XUtilsNew().httpPost("character/queryCharacterList.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {

                    @Override
                    public void onMySuccess(ResponseInfo<String> responseInfo) {
                        String s = responseInfo.result;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);

                            pageIndex = jsonObject.optInt("pageIndex");
                            int pageCount = jsonObject.optInt("totalCount");
                            Gson gson = new Gson();
                            List<CharacterModel> lists = gson.fromJson(jsonObject.getJSONArray("dataList").toString(),new TypeToken<ArrayList<CharacterModel>>() {}.getType());
                            if (pageIndex == 1) {
                                list.clear();
                                list.addAll(lists);
                            } else if (1 < pageIndex && pageIndex <= pageCount) {
                                list.addAll(lists);
                                Toasts.show(CharacterListActivity.this, "加载成功", 0);
                            } else {
                                pageIndex = jsonObject.optInt("pageIndex");
                                Toasts.show(CharacterListActivity.this, "已显示全部内容", 0);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyFailure(HttpException error, String msg) {
                        Toasts.show(CharacterListActivity.this, "无法连接服务器，请检查网络", 0);
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
                // 更新数据
                // 更新完后调用该方法结束刷新

            }
        }, 1500);
    }
}
