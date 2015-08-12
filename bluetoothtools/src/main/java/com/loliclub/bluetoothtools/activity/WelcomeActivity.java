package com.loliclub.bluetoothtools.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifesense.ble.LsBleManager;
import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.util.AccessSharedPreferences;

public class WelcomeActivity extends AppCompatActivity {

    public static final long DELAY_WELCOME = 3000;
    public static final String CONFIG_VERSION = "configVersion";

    private Handler handler;
    private LsBleManager mBLEManager;
    private int packageVersion;

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
        // 获取APP版本号（名）
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            packageVersion = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 配置文件管理
        int scanConfigVersion = AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME, CONFIG_VERSION, 0);
        if (scanConfigVersion < packageVersion) {
            AccessSharedPreferences.clearData(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME);
            AccessSharedPreferences.saveProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME, CONFIG_VERSION, packageVersion);
        }
        int userConfigVersion = AccessSharedPreferences.readProperty(getApplication(), UserSettingActivity.CONFIG_FILE_NAME, CONFIG_VERSION, 0);
        if (userConfigVersion < packageVersion) {
            AccessSharedPreferences.clearData(getApplication(), UserSettingActivity.CONFIG_FILE_NAME);
            AccessSharedPreferences.saveProperty(getApplication(), UserSettingActivity.CONFIG_FILE_NAME, CONFIG_VERSION, packageVersion);
        }
    }
}
