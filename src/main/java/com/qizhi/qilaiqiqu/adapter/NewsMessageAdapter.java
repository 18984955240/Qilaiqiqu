package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.ChatRecordFragment;
import com.qizhi.qilaiqiqu.fragment.MessageFragment;

/**
 * Created by dell1 on 2016/9/2.
 */
public class NewsMessageAdapter extends FragmentPagerAdapter {

    public NewsMessageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("NewsMessageAdapter position = "+position);
        switch (position){
            case 0:
                // 聊天Fragment
                ChatRecordFragment chatRecordFragment = new ChatRecordFragment();
                return chatRecordFragment;

            case 1:
                // 系统消息Fragment
                MessageFragment messageFragment = new MessageFragment();
                return messageFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
