package com.loliclub.eater.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loliclub.eater.R;

public class NavigationDrawerFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

		initUI(rootView);

		// TODO 功能实现

		return rootView;
	}
	
	private void initUI(View rootView){

	}
	
}
