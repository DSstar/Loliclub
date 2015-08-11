package com.loliclub.bluetoothtools.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.commom.BroadcastType;
import com.lifesense.ble.commom.DeviceType;
import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.util.AccessSharedPreferences;
import com.loliclub.bluetoothtools.view.MyDeviceListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RelativeLayout mRlShow;
    private View mViewBT;
    private Button mBtnReflash;
    private ListView mDeviceListView;

    private Handler mHandler;
    private LsBleManager mBLEManager;
    private MyDeviceListAdapter mDeviceAdapter;
    private boolean mPairModelOnly;
    private List<DeviceType> mDeviceTypeList;
    private List<LsDeviceInfo> mDeviceInfoList;

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
        mHandler = new Handler();
        mDeviceInfoList = new ArrayList<LsDeviceInfo>();
        mDeviceAdapter = new MyDeviceListAdapter(getApplication(), mDeviceInfoList);

        mBLEManager = LsBleManager.newInstance();
        // 设置蓝牙设备列表
        mPairModelOnly = AccessSharedPreferences.readProperty(getApplicationContext(),
                ScanSettingActivity.CONFIG_FILE_NAME, ScanSettingActivity.PAIR_MODEL_ONLY, true);
        mDeviceTypeList = new ArrayList<DeviceType>();
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_PEDOMETER, true))
            mDeviceTypeList.add(DeviceType.PEDOMETER);
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_BLOODPRESSURE, true))
            mDeviceTypeList.add(DeviceType.SPHYGMOMANOMETER);
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_SCALE, true)) {
            mDeviceTypeList.add(DeviceType.FAT_SCALE);
            mDeviceTypeList.add(DeviceType.WEIGHT_SCALE);
        }
        if (AccessSharedPreferences.readProperty(getApplication(), ScanSettingActivity.CONFIG_FILE_NAME,
                ScanSettingActivity.ENABLE_HEIGHT_RULER, true))
            mDeviceTypeList.add(DeviceType.HEIGHT_RULER);
        startScan();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnReflash = (Button) findViewById(R.id.scan_btn_reflash);
        mViewBT = findViewById(R.id.scan_view_anim);
        mRlShow = (RelativeLayout) findViewById(R.id.scan_rl_show);
        mDeviceListView = (ListView) findViewById(R.id.scan_listview);
        mDeviceListView.setAdapter(mDeviceAdapter);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent() {
        Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_bt_scan);
        mViewBT.startAnimation(animation);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        mBtnReflash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBLEManager.stopSearch();
                mDeviceInfoList.clear();
                mDeviceAdapter.notifyDataSetChanged();
                startScan();
            }
        });
        mDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 设置控件动画效果
                Animation reflashBTNAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_btn_disappear);
                reflashBTNAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBtnReflash.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mBtnReflash.startAnimation(reflashBTNAnimation);
            }
        });
    }

    @Override
    protected void onPause() {
        mBLEManager.stopSearch();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * 开始扫描设备
     */
    protected void startScan() {
        // 开始扫描
        mBLEManager.searchLsDevice(new SearchCallback() {
            @Override
            public void onSearchResults(LsDeviceInfo lsDeviceInfo) {
                Log.e("ScanActivity", "Device info is " + lsDeviceInfo.toString());
                for (LsDeviceInfo deviceInfo : mDeviceInfoList)
                    if (deviceInfo.getMacAddress().equals(lsDeviceInfo.getMacAddress()))
                        return;
                mDeviceInfoList.add(lsDeviceInfo);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDeviceAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, mDeviceTypeList, mPairModelOnly ? BroadcastType.PAIR : BroadcastType.ALL);
    }
}
