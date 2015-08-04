package com.loliclub.bluetoothtools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lifesense.ble.LsBleManager;
import com.loliclub.bluetoothtools.R;

public class WelcomeActivity extends AppCompatActivity {

    public static final long DELAY_WELCOME = 3000;

    private Handler handler;
    private LsBleManager mBLEManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(getApplication(), MainActivity.class));
                finish();
            }

        }, DELAY_WELCOME);
        // 初始化蓝牙模块
        mBLEManager = LsBleManager.newInstance();
        mBLEManager.initialize(getApplicationContext());
    }
}
