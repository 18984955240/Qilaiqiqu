package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.RiderAccompanyFragment;
import com.qizhi.qilaiqiqu.fragment.RideraAppointFragment;

/**
 * Created by dell1 on 2016/9/2.
 */
public class NewsAttendAdapter extends FragmentPagerAdapter {

    public NewsAttendAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("NewsAttendAdapter position = "+position);
        switch (position){
            case 0:
                // 我的陪骑Fragment
                RiderAccompanyFragment riderAccompanyFragment = new RiderAccompanyFragment();
                return riderAccompanyFragment;

            case 1:
                // 我的约骑Fragment
                RideraAppointFragment agreementFragment = new RideraAppointFragment();
                return agreementFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
