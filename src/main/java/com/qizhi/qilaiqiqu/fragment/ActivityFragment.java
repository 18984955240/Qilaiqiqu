package com.qizhi.qilaiqiqu.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.Activity_NewDetailActivity;
import com.qizhi.qilaiqiqu.activity.Activity_NewListActivity;
import com.qizhi.qilaiqiqu.activity.LoginActivity;
import com.qizhi.qilaiqiqu.activity.WebActivity;
import com.qizhi.qilaiqiqu.adapter.ManageAdapter;
import com.qizhi.qilaiqiqu.model.Active_NewModel;
import com.qizhi.qilaiqiqu.utils.RefreshLayout;
import com.qizhi.qilaiqiqu.utils.RefreshLayout.OnLoadListener;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Administrator
 *			活动主页
 */
@SuppressLint("ValidFragment")
public class ActivityFragment extends Fragment implements OnItemClickListener,OnRefreshListener,OnLoadListener,OnClickListener{

    private ListView manageList;
    private View view;
    private List<Active_NewModel> dataList;
    private ManageAdapter adapter;
    private Context context;
    private SharedPreferences preferences;
    private int pageIndex = 1;
    private RefreshLayout swipeLayout;

    // HeaderView
    // 休闲骑行
    private LinearLayout latRelaxation;
    // 骑行拉练
    private LinearLayout latZipper;
    // 单车赛事
    private LinearLayout glatGame;
    // 大神分享
    private LinearLayout layShare;
    // 单车DIY
    private LinearLayout latDiy;
    // 附近活动
    private LinearLayout latNear;
    // 专业课堂
    private LinearLayout latClass;
    // 其他
    private LinearLayout latElse;


    private boolean isInitHeader;

    public ActivityFragment(boolean isInitHeader){
        this.isInitHeader = isInitHeader;
    }

    public ActivityFragment(){

    }

    @SuppressLint("InlinedApi")
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_manage,null);
        manageList = (ListView) view.findViewById(R.id.list_fragment_manage);
        context = getActivity();
        preferences = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        dataList = new ArrayList<Active_NewModel>();
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // 活动订单查看
        MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click58);

        initViewHeader();

        return view;
    }


    // 热门活动
    private TextView hot;
    private TextView hotImg;
    // 附近活动
    private TextView near;
    private TextView nearImg;
    // 达人活动
    private TextView expert;
    private TextView expertImg;
    // 活动分类 1:附近活动,2:达人活动,3:热门活动
    private String tag = "1";



    private void initViewHeader() {
        View view = View.inflate(getActivity(),R.layout.rideractivity_activity_header, null);

        latRelaxation = (LinearLayout) view.findViewById(R.id.layout_activityFragment_relaxation);
        latZipper = (LinearLayout) view.findViewById(R.id.layout_activityFragment_zipper);
        glatGame = (LinearLayout) view.findViewById(R.id.layout_activityFragment_game);
        layShare = (LinearLayout) view.findViewById(R.id.layout_activityFragment_share);
        latDiy = (LinearLayout) view.findViewById(R.id.layout_activityFragment_diy);
        latNear = (LinearLayout) view.findViewById(R.id.layout_activityFragment_near);
        latClass = (LinearLayout) view.findViewById(R.id.layout_activityFragment_class);
        latElse = (LinearLayout) view.findViewById(R.id.layout_activityFragment_else);

        hot = (TextView) view.findViewById(R.id.txt_activityFragment_hot);
        near = (TextView) view.findViewById(R.id.txt_activityFragment_near);
        expert = (TextView) view.findViewById(R.id.txt_activityFragment_expert);

        hotImg = (TextView) view.findViewById(R.id.img_activityFragment_hot);
        nearImg = (TextView) view.findViewById(R.id.img_activityFragment_near);
        expertImg = (TextView) view.findViewById(R.id.img_activityFragment_expert);

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 查看热门活动
                MobclickAgent.onEvent(getActivity(),"click25");

                initImage();
                hotImg.setBackgroundResource(R.color.mainActivity_title);
                tag = "3";
                dataList.clear();
                data(tag);
                showProgressDialog();
            }
        });
        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImage();
                nearImg.setBackgroundResource(R.color.mainActivity_title);
                tag = "1";
                dataList.clear();
                data(tag);
                showProgressDialog();
            }
        });
        expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 查看达人活动
                MobclickAgent.onEvent(getActivity(),"click24");

                initImage();
                expertImg.setBackgroundResource(R.color.mainActivity_title);
                tag = "2";
                dataList.clear();
                data(tag);
                showProgressDialog();
            }
        });

        String from;
        if(isInitHeader){
            manageList.addHeaderView(view);
            from = "main";
        }else{
            from = "news";
        }

        adapter = new ManageAdapter(context, dataList , preferences.getInt("userId", -1),from);
        manageList.setAdapter(adapter);
        manageList.setDividerHeight(0);
        manageList.setOnItemClickListener(this);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);

        latRelaxation.setOnClickListener(new HeaderViewOnClickListener());
        latZipper.setOnClickListener(new HeaderViewOnClickListener());
        glatGame.setOnClickListener(new HeaderViewOnClickListener());
        layShare.setOnClickListener(new HeaderViewOnClickListener());
        latDiy.setOnClickListener(new HeaderViewOnClickListener());
        latNear.setOnClickListener(new HeaderViewOnClickListener());
        latClass.setOnClickListener(new HeaderViewOnClickListener());
        latElse.setOnClickListener(new HeaderViewOnClickListener());
    }


    private void initImage(){
        hotImg.setBackgroundResource(R.color.white);
        nearImg.setBackgroundResource(R.color.white);
        expertImg.setBackgroundResource(R.color.white);
    }


    /**
     * dp转px
     *
     * @param context
     * @param
     * @return
     */
    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(2,
                dpVal, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onResume() {
        super.onResume();
        data(tag);
        MobclickAgent.onPageStart("ActivityTab");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ActivityTab");
    }

    private void data(String tag) {

        pageIndex = 1;
        RequestParams params = new RequestParams();
        params.addBodyParameter("tag", tag);
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("address", preferences.getString("location",null));
        new XUtilsNew().httpPost("activity/queryList.html", params, false, context, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    dissmissProgressDialog();
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    ArrayList<Active_NewModel> lists = gson.fromJson(jsonObject.optJSONArray("dataList").toString(), new TypeToken<ArrayList<Active_NewModel>>(){}.getType());
                    dataList.clear();
                    dataList.addAll(lists);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                dissmissProgressDialog();
                Toasts.show(context, "无法连接服务器，请检查网络", 0);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if(position == 0){
            startActivity(new Intent(getActivity(), WebActivity.class).putExtra("URL","http://weride.com.cn/weride/mobile/activity/queryIntroduce.html"));
        }else{
            Intent intent = new Intent(getActivity(), Activity_NewDetailActivity.class);
            intent.putExtra("activityId", dataList.get(position-1).getId());
            intent.putExtra("activityTitle", dataList.get(position-1).getTitle());
            intent.putExtra("activityFlag", false);
            getActivity().startActivity(intent);
        }
    }

    private void dataJ(String tag) {
        String url = "activity/queryList.html";

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", preferences.getInt("userId", -1) + "");
        params.addBodyParameter("pageIndex", pageIndex + "");
        params.addBodyParameter("pageSize",  "10");
        params.addBodyParameter("address", preferences.getString("location",null));
        params.addBodyParameter("tag", tag);

        new XUtilsNew().httpPost(url, params, false, context, new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    pageIndex = jsonObject.optInt("pageIndex");
                    int pageCount = jsonObject.optInt("totalCount");

                    Gson gson = new Gson();
                    ArrayList<Active_NewModel> lists = gson.fromJson(jsonObject.optJSONArray("dataList").toString(), new TypeToken<ArrayList<Active_NewModel>>(){}.getType());
                    if(pageIndex == 1){
                        dataList.clear();
                        dataList.addAll(lists);
                        Toasts.show(getActivity(), "刷新成功", 0);
                    }else if(1 < pageIndex && pageIndex <= pageCount){
                        dataList.addAll(lists);
                        Toasts.show(getActivity(), "加载成功", 0);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                Toasts.show(context, "无法连接服务器，请检查网络", 0);
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
                dataJ(tag);

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
                dataJ(tag);
            }
        }, 1500);
    }

    public void onClick(View v) {
        switch (v.getId()) {


            default:
                break;
        }
    }

    /**
     * 显示进度框
     */
    private ProgressDialog progDialog = null;// 搜索时进度条
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(context);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在加载...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    class HeaderViewOnClickListener implements  OnClickListener{
        Intent intent = new Intent(getActivity(), Activity_NewListActivity.class);

        @Override
        public void onClick(View v) {
            if(preferences.getInt("userId",-1) != -1){
                switch (v.getId()){
                    case R.id.layout_activityFragment_relaxation:
                        intent.putExtra("cateId",1);
                        intent.putExtra("title","休闲骑行");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_game:
                        intent.putExtra("cateId",2);
                        intent.putExtra("title","单车赛事");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_near:
                        intent.putExtra("cateId",3);
                        intent.putExtra("title","骑行旅游");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_zipper:
                        intent.putExtra("cateId",4);
                        intent.putExtra("title","腐败拉练");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_class:
                        intent.putExtra("cateId",5);
                        intent.putExtra("title","专业课堂");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_share:
                        intent.putExtra("cateId",6);
                        intent.putExtra("title","大咖分享");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_diy:
                        intent.putExtra("cateId",7);
                        intent.putExtra("title","单车DIY");
                        startActivity(intent);
                        break;

                    case R.id.layout_activityFragment_else:
                        intent.putExtra("cateId",8);
                        intent.putExtra("title","其他");
                        startActivity(intent);
                        break;
                }
            }else{
                Toasts.show(getActivity(), "请先登录", 0);
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
            }


        }
    }

}