package com.loliclub.eater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loliclub.eater.R;

public class NavigationDrawerFragment extends Fragment {

	public static final int REQUEST_CODE = 101;

	private ImageView mUserHeadImage;
	private TextView mUserName;

	private boolean mIsSignIn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

		initParams();
		initUI(rootView);
		initEvent();
		// TODO 功能实现
		// 登录功能

		return rootView;
	}

	private void initParams() {

	}
	
	private void initUI(View rootView){
		mUserHeadImage = (ImageView) rootView.findViewById(R.id.navigation_headimg);
		mUserName = (TextView) rootView.findViewById(R.id.navigation_username);
	}

	private void initEvent() {
		mUserHeadImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mIsSignIn) {

				} else {
					startActivityForResult(new Intent(getActivity(), SignInActivity.class), REQUEST_CODE);
				}
			}
		});
		mUserName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mIsSignIn) {

				} else {
					startActivityForResult(new Intent(getActivity(), SignInActivity.class), REQUEST_CODE);
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_CODE == requestCode) {

		}
	}
}
