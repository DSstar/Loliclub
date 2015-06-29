package com.loliclub.eater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loliclub.dbhelper.util.AccessSharedPreferences;
import com.loliclub.eater.R;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = "MainActivity";

	public static final String PROPERTIES_FILE_NAME = "config";

	public static boolean IS_SIGN_IN = false;

	public static String USER_NAME = "";

	public static String PASSWORD = "";

	public static final String PARAMS_USER_NAME = "userName";

	public static final String PARAMS_PASSWORD = "password";

	private NavigationDrawerFragment navigationFragment;

	private Toolbar mToolbar;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

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
		USER_NAME = AccessSharedPreferences.readProperty(getApplicationContext(), PROPERTIES_FILE_NAME, PARAMS_USER_NAME, "");
		PASSWORD = AccessSharedPreferences.readProperty(getApplicationContext(),PROPERTIES_FILE_NAME, PARAMS_PASSWORD, "");
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		// 初始化导航栏
		navigationFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mainActivity_drawer_navigation);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.mainActivity_drawer);

		// 初始化Toolbar
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
				R.string.app_name, R.string.app_name);
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		setContentFragment(MenuFragment.newInstance());
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
	 * @param v 控件对象
	 */
	public void onNavigationItemClick(View v) {
		switch (v.getId()) {
			case R.id.navigation_menu:
				setContentFragment(MenuFragment.newInstance());
				mDrawerLayout.closeDrawers();
				break;
			case R.id.navigation_order:
				Toast.makeText(getApplicationContext(), R.string.no_function, Toast.LENGTH_SHORT).show();
				break;
			case R.id.navigation_analysis:
				Toast.makeText(getApplicationContext(), R.string.no_function, Toast.LENGTH_SHORT).show();
				break;
			case R.id.navigation_chat:
				Toast.makeText(getApplicationContext(), R.string.no_function, Toast.LENGTH_SHORT).show();
				break;
			case R.id.navigation_setting:
				Toast.makeText(getApplicationContext(), R.string.no_function, Toast.LENGTH_SHORT).show();
				break;
			case R.id.navigation_admin:

				break;
			case R.id.navigation_about:
				startActivity(new Intent(getApplication(), AboutActivity.class));
				mDrawerLayout.closeDrawers();
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
