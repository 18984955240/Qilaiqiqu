package com.qizhi.qilaiqiqu.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.adapter.NewsActivityAdapter;
import com.qizhi.qilaiqiqu.adapter.NewsAttendAdapter;
import com.qizhi.qilaiqiqu.adapter.NewsMessageAdapter;
import com.qizhi.qilaiqiqu.adapter.NewsOrderAdapter;
import com.qizhi.qilaiqiqu.utils.NoScrollViewPager;
import com.qizhi.qilaiqiqu.utils.XUtilsNew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell1 on 2016/8/8.
 */
public class NewsFragment extends Fragment implements View.OnClickListener {

    // fragment主视图
    private View view;

    //
    private NoScrollViewPager orderPager;
    private NoScrollViewPager attendPager;
    private NoScrollViewPager activePager;
    private NoScrollViewPager messagePager;

    private List<NoScrollViewPager> list;

    //
    private NewsOrderAdapter newsOrderAdapter;
    private NewsAttendAdapter newsAttendAdapter;
    private NewsMessageAdapter newsMessageAdapter;
    private NewsActivityAdapter newsActivityAdapter;

    // HeaderView 控件
    private TextView btn1;
    private TextView btn2;
    private LinearLayout buttonLayout;

    private LinearLayout orderLayout;
    private LinearLayout attendLayout;
    private LinearLayout messageLayout;
    private LinearLayout activityLayout;

    private SharedPreferences preferences;

    // 未读消息红点
    private TextView orderPnt;
    private TextView riderPnt;
    private TextView messagePnt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, null);

        init();
        initView();
        initEvent();

        return view;
    }

    private void init() {
        preferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
    }

    private void initPager() {

        orderPnt = (TextView) view.findViewById(R.id.txt_NewsFragment_orderPoint);
        riderPnt = (TextView) view.findViewById(R.id.txt_NewsFragment_attendPoint);
        messagePnt = (TextView) view.findViewById(R.id.txt_NewsFragment_messagePoint);

        messagePager = (NoScrollViewPager) view.findViewById(R.id.viewPager_news_message);
        // Fragment管理器
        newsMessageAdapter = new NewsMessageAdapter(getChildFragmentManager());
        messagePager.setAdapter(newsMessageAdapter);

        orderPager = (NoScrollViewPager) view.findViewById(R.id.viewPager_news_order);
        // Fragment管理器
        newsOrderAdapter = new NewsOrderAdapter(getChildFragmentManager());
        orderPager.setAdapter(newsOrderAdapter);

        attendPager = (NoScrollViewPager) view.findViewById(R.id.viewPager_news_attend);
        // Fragment管理器
        newsAttendAdapter = new NewsAttendAdapter(getChildFragmentManager());
        attendPager.setAdapter(newsAttendAdapter);

        activePager = (NoScrollViewPager) view.findViewById(R.id.viewPager_news_active);
        // Fragment管理器
        newsActivityAdapter = new NewsActivityAdapter(getChildFragmentManager());
        activePager.setAdapter(newsActivityAdapter);

        messagePager.setCurrentItem(0);

        list = new ArrayList<NoScrollViewPager>();
        list.add(orderPager);
        list.add(attendPager);
        list.add(activePager);
        list.add(messagePager);

    }

    private void initView() {

        orderLayout = (LinearLayout) view.findViewById(R.id.layout_NewsFragment_order);
        attendLayout = (LinearLayout) view.findViewById(R.id.layout_NewsFragment_attend);
        activityLayout = (LinearLayout) view.findViewById(R.id.layout_NewsFragment_active);
        messageLayout = (LinearLayout) view.findViewById(R.id.layout_NewsFragment_message);

        btn1 = (TextView) view.findViewById(R.id.txt_NewsFragment_button1);
        btn2 = (TextView) view.findViewById(R.id.txt_NewsFragment_button2);
        buttonLayout = (LinearLayout) view.findViewById(R.id.layout_NewsFragment_button);

        initPager();
    }

    private void initEvent() {
        orderLayout.setOnClickListener(this);
        attendLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        activityLayout.setOnClickListener(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    // 遍历ViewPager设置可视并设置CurrentItem
    private void setViewVisibility(NoScrollViewPager view){
        for (NoScrollViewPager viewPager : list){
            if(viewPager ==  view){
                viewPager.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layout_NewsFragment_message:
                initColor(messageLayout);
                btn1.setText("聊天");
                btn2.setText("系统消息");

//                messagePager.setVisibility(View.VISIBLE);
//                messagePager.setCurrentItem(0);
//
//                orderPager.setVisibility(View.GONE);
//                attendPager.setVisibility(View.GONE);
//                activePager.setVisibility(View.GONE);
                setViewVisibility(messagePager);

                break;

            case R.id.layout_NewsFragment_order:
                initColor(orderLayout);
                btn1.setText("活动订单");
                btn2.setText("陪骑订单");

                setViewVisibility(orderPager);

                break;

            case R.id.layout_NewsFragment_attend:
                initColor(attendLayout);
                btn1.setText("我的陪骑");
                btn2.setText("我的约骑");

                setViewVisibility(attendPager);
                break;

            case R.id.layout_NewsFragment_active:
                initColor(activityLayout);
                buttonLayout.setVisibility(View.GONE);

                setViewVisibility(activePager);

                break;



            case R.id.txt_NewsFragment_button1:
                btn1.setBackgroundResource(R.drawable.corners_news_button1);
                btn1.setTextColor(getResources().getColor(R.color.white));
                btn2.setBackgroundResource(R.drawable.corners_news_button2_no);
                btn2.setTextColor(getResources().getColor(R.color.mainActivity_title));



                if ("聊天".equals(btn1.getText().toString().trim())) {
                    messagePager.setCurrentItem(0);

                } else if ("活动订单".equals(btn1.getText().toString().trim())) {
                    orderPager.setCurrentItem(0);

                } else if("我的陪骑".equals(btn1.getText().toString().trim())){}{

                    attendPager.setCurrentItem(0);
                }

                break;

            case R.id.txt_NewsFragment_button2:
                btn2.setBackgroundResource(R.drawable.corners_news_button2);
                btn2.setTextColor(getResources().getColor(R.color.white));
                btn1.setBackgroundResource(R.drawable.corners_news_button1_no);
                btn1.setTextColor(getResources().getColor(R.color.mainActivity_title));


                if ("系统消息".equals(btn2.getText().toString().trim())) {
                    messagePager.setCurrentItem(1);

                } else if ("陪骑订单".equals(btn2.getText().toString().trim())) {
                    orderPager.setCurrentItem(1);

                } else if("我的约骑".equals(btn1.getText().toString().trim())){}{
                    attendPager.setCurrentItem(1);
                }

                break;

        }
    }

    private void initColor(LinearLayout layout) {

        orderLayout.setBackgroundColor(getResources().getColor(R.color.graye6));
        attendLayout.setBackgroundColor(getResources().getColor(R.color.graye6));
        messageLayout.setBackgroundColor(getResources().getColor(R.color.graye6));
        activityLayout.setBackgroundColor(getResources().getColor(R.color.graye6));

        btn1.setBackgroundResource(R.drawable.corners_news_button1);
        btn1.setTextColor(getResources().getColor(R.color.white));
        btn2.setBackgroundResource(R.drawable.corners_news_button2_no);
        btn2.setTextColor(getResources().getColor(R.color.mainActivity_title));

        buttonLayout.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(getResources().getColor(R.color.white));

    }

    /**
     * 获取是否有未读消息
     */
    private void getUnreadMessage() {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("userId",preferences.getInt("userId",-1)+"");
        new XUtilsNew().httpPost("message/queryMessageState.html", params, false, getActivity(), new XUtilsNew.XUtilsCallBackPost() {
            @Override
            public void onMySuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object  = new JSONObject(responseInfo.result);
                    // 是否有未读系统消息
                    if(object.getBoolean("messageState")){
                        messagePnt.setVisibility(View.VISIBLE);
                    }else{
                        messagePnt.setVisibility(View.GONE);
                    }

                    // 是否有未支付订单消息
                    if(object.getBoolean("orderState")){
                        orderPnt.setVisibility(View.VISIBLE);
                    }else{
                        orderPnt.setVisibility(View.GONE);
                    }

                    // 是否有未处理陪骑信息
                    if(object.getBoolean("riderState")){
                        riderPnt.setVisibility(View.VISIBLE);
                    }else{
                        riderPnt.setVisibility(View.GONE);
                    }


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
    public void onResume() {
        super.onResume();
        getUnreadMessage();
    }
}
