package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.OrderActivityFragment;
import com.qizhi.qilaiqiqu.fragment.OrderMyFragment;

/**
 * Created by dell1 on 2016/9/2.
 */
public class NewsOrderAdapter extends FragmentPagerAdapter {

    public NewsOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("NewsOrderAdapter position = "+position);

        switch (position){
            case 0:
                // 活动订单Fragment
                OrderActivityFragment activityFragment = new OrderActivityFragment();
                return activityFragment;

            case 1:
                // 陪骑订单Fragment
                OrderMyFragment orderMyFragment = new OrderMyFragment();
                return orderMyFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
