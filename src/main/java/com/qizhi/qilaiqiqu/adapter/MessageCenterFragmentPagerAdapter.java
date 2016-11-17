package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.ChatRecordFragment;
import com.qizhi.qilaiqiqu.fragment.MessageFragment;

/**
 * 
 * @author Administrator
 *			系统消息和聊天消息中心
 */

public class MessageCenterFragmentPagerAdapter extends FragmentPagerAdapter {
	public final static int TAB_COUNT = 2;
	public MessageCenterFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			
			ChatRecordFragment chatRecordFragment = new ChatRecordFragment();
			return chatRecordFragment;
		case 1:
			MessageFragment messageFragment = new MessageFragment();
			return messageFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}


}
