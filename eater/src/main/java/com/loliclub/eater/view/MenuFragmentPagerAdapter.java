package com.loliclub.eater.view;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MenuFragmentPagerAdapter extends FragmentPagerAdapter {

	
	private List<String> titleList;
	private List<Fragment> fragmentList;
	
	public MenuFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
		super(fm);
		this.fragmentList = fragmentList;
		this.titleList = titleList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	}

}
