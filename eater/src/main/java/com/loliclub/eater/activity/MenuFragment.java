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

import com.loliclub.dbhelper.database.DatabaseHelper;
import com.loliclub.dbhelper.database.DatabaseService;
import com.loliclub.eater.R;
import com.loliclub.eater.bean.Restaurant;
import com.loliclub.eater.beanservices.MenuService;
import com.loliclub.eater.beanservices.RestaurantService;
import com.loliclub.eater.properties.MyDatabaseHandler;
import com.loliclub.eater.view.MenuFragmentPagerAdapter;

public class MenuFragment extends Fragment {

	private ViewPager mViewPager;
	private PagerTabStrip mPagerTabStrip;

	private DatabaseHelper databaseHelper;

	private List<String> titleList;
	private List<Fragment> viewList;
	private Restaurant restaurant;

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

		databaseHelper = DatabaseService.connectDatabase(getActivity(), MyDatabaseHandler.getInstance());

		/**
		 * 初始化加载数据
		 */
		// 加载餐厅数据
		RestaurantService restaurantService = new RestaurantService(databaseHelper.getReadableDatabase());
		List<Restaurant> list = restaurantService.execQuery(null, null);
		if (list != null && list.size() > 0)
			restaurant = list.get(0);
		// 加载菜单标题
		if (restaurant != null) {
			MenuService menuService = new MenuService(databaseHelper.getReadableDatabase());
			titleList = menuService.queryCaregory(restaurant.id);
			if (titleList != null && titleList.size() > 0)
				for (String title : titleList) {
					viewList.add(MenuListFragment.newInstance(restaurant.id, title));
				}
		}

	}

	private void initUI(View rootView) {
		mViewPager = (ViewPager) rootView.findViewById(R.id.menuFragment_viewPager);
		mPagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.menuFragment_tab);

		mViewPager.setAdapter(new MenuFragmentPagerAdapter(getActivity().getSupportFragmentManager(), viewList, titleList));
	}

	private void initEvent() {

	}

}
