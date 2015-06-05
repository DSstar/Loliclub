package com.loliclub.eater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.loliclub.eater.R;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = "MainActivity";

	private Toolbar toolbar;
	private ListView listview;
	private Button btn_flat;

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
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		listview = (ListView) findViewById(R.id.mainActivity_lv_menu);
		btn_flat = (Button) findViewById(R.id.mainActivity_btn_flat);

		// 初始化Toolbar
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
	}

	/**
	 * 初始化事件处理
	 */
	private void initEvent() {
		btn_flat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MenuActivity.class));
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_eaterinfo) {
			startActivity(new Intent(getApplicationContext(), EaterActivity.class));
			return true;
		} else if (id == R.id.action_about) {
			startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
