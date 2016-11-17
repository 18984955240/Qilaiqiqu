package com.qizhi.qilaiqiqu.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.wraprecyclerview.WrapRecyclerView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.activity.LoginActivity;
import com.qizhi.qilaiqiqu.activity.RiderAuthenticationActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewDetailActivity;
import com.qizhi.qilaiqiqu.activity.Rider_NewRecommendActiviity;
import com.qizhi.qilaiqiqu.adapter.RidersFagmentAdapter;
import com.qizhi.qilaiqiqu.model.Rider_NewModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;
import com.qizhi.qilaiqiqu.utils.Toasts;
import com.qizhi.qilaiqiqu.utils.UmengEventUtil;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qizhi.qilaiqiqu.R.id.layout_riderImage;

/**
 * Created by dell1 on 2016/8/8.
 */
public class RiderFragment extends Fragment {

    // fragment主视图
    private View view;

    private ListView listView;
    RidersFagmentAdapter adapter;

    // 头部控件
    private View headerView;
    private RelativeLayout becomeRider;
    private LinearLayout riderImageLayout;

    private WrapRecyclerView recyclerView;
    private StaggeredGridLayoutManager manager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Rider_NewModel> list;
    private List<String> riderImageList;

    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rider, null);

        ini();
        initView();
        getData(false);
        getRiderImage();
        return view;
    }

    private void ini() {
        riderImageList = new ArrayList<String>();
        list = new ArrayList<Rider_NewModel>();

        preferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    private void initView() {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);

        recyclerView= (WrapRecyclerView) view.findViewById(R.id.RiderFragment_recycle);
        //设置layoutManager
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        // 设置RecyclerView默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        adapter=new RidersFagmentAdapter(list,getActivity());
        addHeaderView();
        recyclerView.setAdapter(adapter);
        //设置item之间的间距
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

    }

    private void addHeaderView() {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_fragment_rider_header, null);
        becomeRider = (RelativeLayout) headerView.findViewById(R.id.layout_rideractivity_activity_becomerider);
        riderImageLayout = (LinearLayout) headerView.findViewById(layout_riderImage);
        recyclerView.addHeaderView(headerView);
        initEvent();
    }


    private void initEvent() {

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 在此处实现刷新获取数据，然后更新RecyclerView的数据源即可
                // 使之休眠1.5S
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex = 1;
                       getData(true);
                    }
                }, 1500);


            }
        });

        adapter.setOnItemClickListener(new RidersFagmentAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                startActivity(new Intent(getActivity(), Rider_NewDetailActivity.class)
                        .putExtra("riderId",list.get(postion-1).getId()));
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                System.out.println("newState="+newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    System.out.println("manager.getItemCount()="+manager.getItemCount());


//                    int lastVisiblePosition = manager.findLastVisibleItemPositions();
//                    if(lastVisiblePosition >= manager.getItemCount() - 1){
//
//                        System.out.println("====自动加载");
//                    }
                }
            }
        });

        becomeRider.setOnClickListener(new HeaderViewOnClickListener());
        riderImageLayout.setOnClickListener(new HeaderViewOnClickListener());
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = space;
            outRect.top = space/2;
        }
    }

    private void getRiderImage() {
        new XUtilsNew().httpGet("rider/queryTjRider.html", false, getActivity(), new XUtilsNew.XUtilsCallBackGet() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    Gson gson = new Gson();
                    List<String> image = gson.fromJson(object.getJSONArray("dataList").toString(),new TypeToken<List<String>>(){}.getType());
                    riderImageList.clear();
                    riderImageList.addAll(image);
                    setImageView();

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

    private void setImageView() {
        LinearLayout ImageLayout = riderImageLayout;
        ImageLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(getActivity(),35f),dp2px(getActivity(),35f));

        for(String imageUrl : riderImageList){
            CircleImageViewUtil imageViewUtil = new CircleImageViewUtil(getActivity());
            imageViewUtil.setLayoutParams(params);
            SystemUtil.Imagexutils_photo(imageUrl,imageViewUtil,getActivity());
            ImageLayout.addView(imageViewUtil);
        }
    }


    private int pageIndex = 1;
    private void getData(final boolean refreshFlag) {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("pageIndex",pageIndex+"");
        params.addBodyParameter("pageSize","10");

        new XUtilsNew().httpPost("rider/queryList.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    Gson gson = new Gson();
                    List<Rider_NewModel> lists = gson.fromJson(object.getJSONArray("dataList").toString(),new TypeToken<List<Rider_NewModel>>(){}.getType());

                    if(pageIndex == 1){
                        list.clear();
                    }
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(refreshFlag){
                    // 关闭刷新动画
                    swipeRefreshLayout.setRefreshing(false);
                    if(pageIndex == 1){
                        Toasts.show(getActivity(), "刷新成功", 0);
                    }else {
                        Toasts.show(getActivity(), "加载成功", 0);
                    }
                }
            }

            @Override
            public void onMyFailure(HttpException error, String msg) {
                // 关闭刷新动画
                swipeRefreshLayout.setRefreshing(false);
                Toasts.show(getActivity(), "无法连接服务器，请检查网络", 0);
            }
        });
    }


    private class HeaderViewOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_rideractivity_activity_becomerider:
                    if(preferences.getInt("userId",-1) != -1){
                        isRider();

                    }else{
                        Toasts.show(getActivity(), "请先登录", 0);
                        Intent intent = new Intent(getActivity(),
                                LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }


                    break;

                case  layout_riderImage:

                    // 查看推荐达人
                    MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click36);

                    startActivity(new Intent(getActivity(), Rider_NewRecommendActiviity.class));
                    break;
            }
        }
    }

    public void isRider() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");

        new XUtilsNew().httpPost("rider/queryRiderState.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);

                    System.out.println("object.getString(\"data\")="+object.getString("data"));

                    if(object.getString("data").equals("0")){

                        // 认证入口
                        MobclickAgent.onEvent(getActivity(), new UmengEventUtil().click36_1);

                        startActivity(new Intent(getActivity(), RiderAuthenticationActivity.class));
                    } else if(object.getString("data").equals("1")){
                        Toasts.show(getActivity(),"您已提交过认证，请等待审核",0);
                    } else if(object.getString("data").equals("2")){
                        Toasts.show(getActivity(),"您已经通过审核，无需再认证哦，",0);
                    }
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


    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}
