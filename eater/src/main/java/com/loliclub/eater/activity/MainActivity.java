package com.loliclub.eater.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loliclub.eater.R;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = "MainActivity";

	private NavigationDrawerFragment navigationFragment;

	private Toolbar toolbar;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initParams();
		initUI();
		initEvent();
	}

	/**
	 * 初始化参数
	 */
	private void initParams() {

	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		// 初始化导航栏
		navigationFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mainActivity_drawer_navigation);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		drawerLayout = (DrawerLayout) findViewById(R.id.mainActivity_drawer);

		// 初始化Toolbar
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
				R.string.app_name, R.string.app_name);
		drawerToggle.syncState();
		drawerLayout.setDrawerListener(drawerToggle);

//		setContentFragment(MenuFragment.newInstance(null));
	}

	/**
	 * 初始化事件处理
	 */
	private void initEvent() {

	}

	/**
	 * 设置content中的Fragment
	 *
	 * @param fragment
	 */
	protected void setContentFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.mainActivity_content, fragment).commit();
	}

	/**
	 * 导航栏列表按键处理
	 * @param v
	 */
	public void onNavigationItemClick(View v) {
		switch (v.getId()) {
			case R.id.navigation_menu:

				break;
			case R.id.navigation_order:

				break;
			case R.id.navigation_analysis:

				break;
			case R.id.navigation_chat:

				break;
			case R.id.navigation_setting:

				break;
			case R.id.navigation_about:

				break;
			default:
				break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_eaterinfo) {
//			return true;
//		} else if (id == R.id.action_about) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
	
}
