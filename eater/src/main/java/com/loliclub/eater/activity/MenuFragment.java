package com.loliclub.eater.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loliclub.eater.R;
import com.loliclub.eater.view.MenuFragmentPagerAdapter;

public class MenuFragment extends Fragment {

	private ViewPager mViewPager;
	private PagerTabStrip mPagerTabStrip;


	private List<String> titleList;
	private List<Fragment> viewList;

	public MenuFragment(){
		super();
	}

	public static final MenuFragment newInstance() {
		MenuFragment fragment = new MenuFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_menu, null);

		initParams();
		initUI(rootView);
		initEvent();

		return rootView;
	}

	private void initParams() {
		titleList = new ArrayList<String>();
		viewList = new ArrayList<Fragment>();

		/**
		 * 初始化加载数据
		 */

		titleList.add("tab1");
		titleList.add("tab2");
		titleList.add("tab3");
		titleList.add("tab4");

		List<String> menuList = new ArrayList<String>();
		menuList.add("menu1");
		menuList.add("menu2");
		menuList.add("menu3");
		menuList.add("menu4");
		menuList.add("menu5");

//		viewList.add(MenuFragment.newInstance(menuList));
//		viewList.add(MenuFragment.newInstance(menuList));
//		viewList.add(MenuFragment.newInstance(menuList));
//		viewList.add(MenuFragment.newInstance(menuList));

	}

	private void initUI(View rootView) {
		mViewPager = (ViewPager) rootView.findViewById(R.id.menuFragment_viewPager);
		mPagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.menuFragment_tab);

		mViewPager.setAdapter(new MenuFragmentPagerAdapter(getActivity().getSupportFragmentManager(), viewList, titleList));
	}

	private void initEvent() {

	}

}
