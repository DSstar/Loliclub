package com.loliclub.bluetoothtools.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.PairCallback;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.DeviceUserInfo;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.commom.BroadcastType;
import com.lifesense.ble.commom.DeviceType;
import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.util.AccessSharedPreferences;
import com.loliclub.bluetoothtools.view.MyCaptionAdapter;
import com.loliclub.bluetoothtools.view.MyDeviceListAdapter;
import com.loliclub.bluetoothtools.view.MyUserListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mBTImg;
    private ImageView mConnectImg;
    private ImageView mDeviceImg;
    private View mViewBT;
    private View mBT2Device;
    private View mDevice2BT;
    private Button mBtnReFlash;
    private ListView mDeviceListView;
    private ListView mCaptionListView;
    private ListView mUserListView;

    private Handler mHandler;
    private Runnable connectRunnable;
    private LsBleManager mBLEManager;
    private MyDeviceListAdapter mDeviceAdapter;
    private MyCaptionAdapter mCaptionAdapter;
    private MyUserListAdapter mUserListAdapter;
    private boolean mPairModelOnly;
    private List<DeviceType> mDeviceTypeList;
    private List<LsDeviceInfo> mDeviceInfoList;
    private List<String> mCaptionList;
    private List<DeviceUserInfo> mUserInfoList;

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
        mCaptionList = new ArrayList<String>();
        mCaptionAdapter = new MyCaptionAdapter(getApplication(), mCaptionList);
        mUserInfoList = new ArrayList<DeviceUserInfo>();
        mUserListAdapter = new MyUserListAdapter(getApplication(), mUserInfoList);

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
        mBtnReFlash = (Button) findViewById(R.id.scan_btn_reflash);
        mViewBT = findViewById(R.id.scan_view_anim);
        mBT2Device = findViewById(R.id.scan_line_bt2device);
        mDevice2BT = findViewById(R.id.scan_line_device2bt);
        mBTImg = (ImageView) findViewById(R.id.scan_img_bluetooth);
        mConnectImg = (ImageView) findViewById(R.id.scan_img_connect);
        mDeviceImg = (ImageView) findViewById(R.id.scan_img_device);
        mDeviceListView = (ListView) findViewById(R.id.scan_listview);
        mCaptionListView = (ListView) findViewById(R.id.scan_lv_caption);
        mUserListView = (ListView) findViewById(R.id.scan_lv_userinfo);
        mDeviceListView.setAdapter(mDeviceAdapter);
        mCaptionListView.setAdapter(mCaptionAdapter);
        mUserListView.setAdapter(mUserListAdapter);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent() {
        final Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_bt_scan);
        mViewBT.startAnimation(animation);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        mBtnReFlash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBLEManager.stopSearch();
                mDeviceInfoList.clear();
                mCaptionList.clear();
                mDeviceAdapter.notifyDataSetChanged();
                mCaptionAdapter.notifyDataSetChanged();
                startScan();
            }
        });
        mDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBLEManager.stopSearch();
                LsDeviceInfo deviceInfo = mDeviceInfoList.get(position);
                pairAnimationSet();
                String caption = getResources().getString(R.string.pair_with);
                caption.replace("%s", deviceInfo.getDeviceName());
                mCaptionList.add(caption);
                mBLEManager.startPairing(deviceInfo, new PairCallback() {
                    @Override
                    public void onDiscoverUserInfo(final List list) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Animation showUserAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_slide_from_bottom);
                                showUserAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        Animation captionAnimation = new TranslateAnimation(0, 0,
                                                0, -mDeviceListView.getHeight());
                                        captionAnimation.setFillAfter(true);
                                        captionAnimation.setDuration(500l);
                                        captionAnimation.setInterpolator(getApplication(), android.R.anim.accelerate_decelerate_interpolator);
                                        mCaptionListView.startAnimation(captionAnimation);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        mUserInfoList.addAll(list);
                                        mUserListAdapter.notifyDataSetChanged();
                                        mUserListView.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                                mUserListView.startAnimation(showUserAnimation);
                                mCaptionList.add(getResources().getString(R.string.select_user));
                                mCaptionAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onPairResults(final LsDeviceInfo lsDeviceInfo, final int i) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // i = 0：成功，其他：失败
                                mConnectImg.setVisibility(View.VISIBLE);
                                if (i == 0) {
                                    // TODO 数据保存数据库
                                    mConnectImg.setImageResource(R.mipmap.img_connect);
                                    String result = getResources().getString(R.string.pair_success);
                                    result.replace("%s", lsDeviceInfo.getDeviceName());
                                    mCaptionList.add(result);
                                    mCaptionAdapter.notifyDataSetChanged();
                                } else {
                                    mConnectImg.setImageResource(R.mipmap.img_disconnect);
                                    String result = getResources().getString(R.string.pair_fail);
                                    result.replace("%s", lsDeviceInfo.getDeviceName());
                                    mCaptionList.add(result);
                                    mCaptionAdapter.notifyDataSetChanged();
                                }
                                final Animation flashAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_flash);
                                flashAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }
                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        mConnectImg.startAnimation(flashAnimation);
                                    }
                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                                mConnectImg.startAnimation(flashAnimation);
                            }
                        });
                    }
                });
            }
        });
        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 获取用户名
                String userName = AccessSharedPreferences.readProperty(getApplication(), UserSettingActivity.CONFIG_FILE_NAME,
                        UserSettingActivity.USER_NAME, "BTTools");
                mBLEManager.bindDeviceUser(position + 1, userName);
                Animation hideUserAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_slide_to_bottom);
                hideUserAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mUserListView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mUserListView.startAnimation(hideUserAnimation);
                String userNumber = getResources().getString(R.string.select_user_number);
                userNumber.replace("%s", position + 1 + "");
                mCaptionAdapter.add(userNumber);
                mCaptionAdapter.notifyDataSetChanged();
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
//        getMenuInflater().inflate(R.menu.menu_scan, menu);
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
        mCaptionList.add(getResources().getString(R.string.scanning));
        mCaptionAdapter.notifyDataSetChanged();
        mBLEManager.searchLsDevice(new SearchCallback() {
            @Override
            public void onSearchResults(LsDeviceInfo lsDeviceInfo) {
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

    /**
     * 配对动画效果
     */
    protected void pairAnimationSet() {
        mViewBT.clearAnimation();
        // 刷新按钮动画效果
        Animation reflashBTNAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_btn_disappear);
        mBtnReFlash.startAnimation(reflashBTNAnimation);
        // 列表下滑动画效果
        Animation listViewAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_out_bottom);
        listViewAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mBtnReFlash.setVisibility(View.INVISIBLE);
                mDeviceListView.setVisibility(View.INVISIBLE);
                // 重新设置字幕的位置
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mCaptionListView.getLayoutParams();
                layoutParams.bottomMargin = layoutParams.bottomMargin - mDeviceListView.getHeight();
                mCaptionListView.setLayoutParams(layoutParams);
                mCaptionAdapter.notifyDataSetChanged();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mDeviceListView.startAnimation(listViewAnimation);
        // 字幕下滑动画效果
        Animation captionAnimation = new TranslateAnimation(0, 0,
                0, mDeviceListView.getHeight());
        captionAnimation.setFillAfter(true);
        captionAnimation.setDuration(500l);
        captionAnimation.setInterpolator(getApplication(), android.R.anim.accelerate_decelerate_interpolator);
        mCaptionListView.startAnimation(captionAnimation);

        // 配对动画效果
        mViewBT.setVisibility(View.INVISIBLE);
        mDeviceImg.setVisibility(View.VISIBLE);
        Animation showDeviceAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_btn_show);
        mDeviceImg.startAnimation(showDeviceAnimation);
        connectRunnable = new Runnable() {
            @Override
            public void run() {
                if (mConnectImg.getVisibility() != View.VISIBLE) {
                    final Animation leftAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_line_left2right);
                    final Animation rightAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.anim_line_right2left);
                    mDevice2BT.startAnimation(leftAnimation);
                    mBT2Device.startAnimation(rightAnimation);
                    mHandler.postDelayed(connectRunnable, 2000l);
                }
            }
        };
        mHandler.postDelayed(connectRunnable, 500l);
    }
}
