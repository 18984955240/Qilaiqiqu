package com.qizhi.qilaiqiqu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qizhi.qilaiqiqu.fragment.RiderAccompanyFragment;
import com.qizhi.qilaiqiqu.fragment.RideraAppointFragment;

/**
 * 
 * @author Administrator
 *			陪骑与约骑
 */

public class RiderManageCenterPagetAdapter extends FragmentPagerAdapter{

	public RiderManageCenterPagetAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			RiderAccompanyFragment accompanyFragment = new RiderAccompanyFragment();
			return accompanyFragment;
		case 1:
			RideraAppointFragment agreementFragment = new RideraAppointFragment();
			return agreementFragment;

		default:
			break;
		}
		
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
