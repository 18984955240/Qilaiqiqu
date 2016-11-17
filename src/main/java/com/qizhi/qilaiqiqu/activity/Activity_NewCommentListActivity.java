package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.Activity_NewCommentAdapter;
import com.qizhi.qilaiqiqu.model.Activity_NewCommentModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
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
 *  活动点评列表
 */
public class Activity_NewCommentListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    @InjectView(R.id.layout_back)
    LinearLayout layoutBack;
    @InjectView(R.id.list_comment)
    ListView listComment;
    @InjectView(R.id.swipe_container)
    RefreshLayout swipeContainer;

    ImageView imgActiveImg;
    TextView txtActivetitle;

    private int activityId;
    private String title;
    private String activityImage;

    private int pageIndex = 1;

    // ListView 适配器
    Activity_NewCommentAdapter adapter;
    List<Activity_NewCommentModel> list;
    View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__new_comment_list);
        ButterKnife.inject(this);
        initHeaderView();
        init();
        initEvent();
        initView();
        getData();
    }

    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.item_list_commentactivity_header,null);
        imgActiveImg = (ImageView) mHeaderView.findViewById(R.id.img_activeImg);
        txtActivetitle = (TextView) mHeaderView.findViewById(R.id.txt_activetitle);
    }

    private void init() {
        activityId = getIntent().getIntExtra("activityId", -1);

        title = getIntent().getStringExtra("title");
        activityImage = getIntent().getStringExtra("activityImage");

        list = new ArrayList<Activity_NewCommentModel>();
        adapter = new Activity_NewCommentAdapter(list, this);
    }

    private void initView() {
        txtActivetitle.setText(title);
        SystemUtil.Imagexutils_new(activityImage,imgActiveImg,this);


        listComment.addHeaderView(mHeaderView);
        listComment.setAdapter(adapter);
    }

    private void initEvent() {
        swipeContainer.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(Activity_NewCommentListActivity.this);
        swipeContainer.setOnLoadListener(Activity_NewCommentListActivity.this);
    }

    @OnClick(R.id.layout_back)
    public void onClick() {
        finish();
    }


    public void getData() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId", activityId + "");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");

        new XUtilsNew().httpPost("activity/queryComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<Activity_NewCommentModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<List<Activity_NewCommentModel>>(){}.getType());
                    list.clear();
                    list.addAll(lists);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewCommentListActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    private void dataJ(){
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("activityId", activityId + "");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize", "10");

        new XUtilsNew().httpPost("activity/queryComment.html", params, false, this, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {

                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<Activity_NewCommentModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(), new TypeToken<List<Activity_NewCommentModel>>(){}.getType());

                    if(pageIndex == 1){
                        list.clear();
                        list.addAll(lists);
                    }else{
                        list.addAll(lists);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listComment.setAdapter(adapter);
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(Activity_NewCommentListActivity.this, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeContainer.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
                pageIndex = 1;
                dataJ();

            }
        }, 1500);

    }

    @Override
    public void onLoad() {
        swipeContainer.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeContainer.setLoading(false);
                pageIndex = pageIndex + 1;
                dataJ();
            }
        }, 1500);
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
