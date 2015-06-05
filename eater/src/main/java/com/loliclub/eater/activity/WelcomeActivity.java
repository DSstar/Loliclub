package com.loliclub.eater.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.loliclub.eater.R;
import com.loliclub.eater.services.NetworkService;

public class WelcomeActivity extends Activity {

	private static final long DELAY = 1000l;

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
				WelcomeActivity.this.finish();
			}
		}, DELAY);

		Intent service = new Intent(getApplicationContext(),
				NetworkService.class);
		startService(service);
	}

}
