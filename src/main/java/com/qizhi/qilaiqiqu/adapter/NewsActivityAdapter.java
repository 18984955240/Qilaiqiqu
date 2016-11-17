package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.ActivityFragment;

/**
 * Created by dell1 on 2016/8/15.
 */
public class NewsActivityAdapter extends FragmentPagerAdapter {


    public NewsActivityAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        ActivityFragment fragment = new ActivityFragment(false);

        return fragment;
    }


    @Override
    public int getCount() {
        return 1;
    }

}
