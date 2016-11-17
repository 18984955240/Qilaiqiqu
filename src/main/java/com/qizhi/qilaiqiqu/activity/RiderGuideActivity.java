package com.qizhi.qilaiqiqu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 *	陪骑引导
 */
public class RiderGuideActivity extends Activity {

    private ViewPager viewPager;

    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<TextView> flagContainter = new ArrayList<TextView>();

    TextView flag1;
    TextView flag2;
    TextView flag3;
    TextView flag4;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_guide);
        initView();
        initEvent();
    }
    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.riderGuide_viewpager);
        imageView = (ImageView) findViewById(R.id.riderGuide_back);

        flag1 = (TextView) findViewById(R.id.riderGuide_flag1);
        flag2 = (TextView) findViewById(R.id.riderGuide_flag2);
        flag3 = (TextView) findViewById(R.id.riderGuide_flag3);
        flag4 = (TextView) findViewById(R.id.riderGuide_flag4);

        flagContainter.add(flag1);
        flagContainter.add(flag2);
        flagContainter.add(flag3);
        flagContainter.add(flag4);


        View view1 = LayoutInflater.from(this).inflate(R.layout.rider_guide_tab1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.rider_guide_tab2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.rider_guide_tab3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.rider_guide_tab4, null);

        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
        viewContainter.add(view4);
    }

    private void initEvent(){

        viewPager.setAdapter(new PagerAdapter() {
            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }

            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initFlag();
                flagContainter.get(position).setBackgroundResource(R.drawable.corners_flag_gray);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initFlag(){
        flag1.setBackgroundResource(R.drawable.corners_flag_white);
        flag2.setBackgroundResource(R.drawable.corners_flag_white);
        flag3.setBackgroundResource(R.drawable.corners_flag_white);
        flag4.setBackgroundResource(R.drawable.corners_flag_white);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

}
