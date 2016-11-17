 package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.CareFragment;
import com.qizhi.qilaiqiqu.fragment.FansFragment;

 /**
 * 
 * @author Administrator
 *			粉丝与关注
 */
public class FansAndCareFragmentPagerAdapter extends FragmentPagerAdapter {
	public final static int TAB_COUNT = 2;
	public FansAndCareFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			FansFragment fansFragment = new FansFragment();
			return fansFragment;
		case 1:
			CareFragment careFragment = new CareFragment();
			return careFragment;
		}
		return null;
	}
	@Override
	public int getCount() {
		return TAB_COUNT;
	}

}
