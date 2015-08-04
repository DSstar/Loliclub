package com.loliclub.bluetoothtools.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.commom.BroadcastType;
import com.lifesense.ble.commom.DeviceType;
import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.util.AccessSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private LsBleManager mBLEManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initParams();
        initUI();
        initEvent();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        mBLEManager = LsBleManager.newInstance();
        // 设置蓝牙设备列表
        boolean pairModelOnly = AccessSharedPreferences.readProperty(getApplicationContext(),
                ScanSettingActivity.CONFIG_FILE_NAME, ScanSettingActivity.PAIR_MODEL_ONLY, true);
        List<DeviceType> deviceTypeList = new ArrayList<DeviceType>();
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_PEDOMETER, true))
            deviceTypeList.add(DeviceType.PEDOMETER);
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_BLOODPRESSURE, true))
            deviceTypeList.add(DeviceType.SPHYGMOMANOMETER);
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_SCALE, true)) {
            deviceTypeList.add(DeviceType.FAT_SCALE);
            deviceTypeList.add(DeviceType.WEIGHT_SCALE);
        }
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_HEIGHT_RULER, true))
            deviceTypeList.add(DeviceType.HEIGHT_RULER);

        // 开始扫描
        mBLEManager.searchLsDevice(new SearchCallback() {
            @Override
            public void onSearchResults(LsDeviceInfo lsDeviceInfo) {
                Log.e("ScanActivity", "Device info is " + lsDeviceInfo.toString());
            }
        }, deviceTypeList, pairModelOnly? BroadcastType.PAIR : BroadcastType.ALL);
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
    }

    @Override
    public void onBackPressed() {
        mBLEManager.stopSearch();
        super.onBackPressed();
    }
}
