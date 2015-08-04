package com.loliclub.bluetoothtools.activity;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.util.AccessSharedPreferences;

public class ScanSettingActivity extends AppCompatActivity {

    public static final String CONFIG_FILE_NAME = "scan_config";
    public static final String PAIR_MODEL_ONLY = "PAIR_MODEL_ONLY";
    public static final String ENABLE_PEDOMETER = "ENABLE_PEDOMETER";
    public static final String ENABLE_BLOODPRESSURE = "ENABLE_BLOODPRESSURE";
    public static final String ENABLE_SCALE = "ENABLE_SCALE";
    public static final String ENABLE_HEIGHT_RULER = "ENABLE_HEIGHT_RULER";

    private Toolbar mToolbar;
    private ImageView mSwitchPairModelOnly;
    private ImageView mSwitchEnablePedometer;
    private ImageView mSwitchEnableBloodPressure;
    private ImageView mSwitchEnableScale;
    private ImageView mSwitchEnableHeightRuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_setting);

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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSwitchPairModelOnly = (ImageView) findViewById(R.id.scan_setting_pair_only);
        mSwitchEnablePedometer = (ImageView) findViewById(R.id.scan_setting_enable_pedometer);
        mSwitchEnableBloodPressure = (ImageView) findViewById(R.id.scan_setting_enable_bloodpressure);
        mSwitchEnableScale = (ImageView) findViewById(R.id.scan_setting_enable_scale);
        mSwitchEnableHeightRuler = (ImageView) findViewById(R.id.scan_setting_enable_height_ruler);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(AccessSharedPreferences.readProperty(this, CONFIG_FILE_NAME, PAIR_MODEL_ONLY, true))
            mSwitchPairModelOnly.setImageResource(R.mipmap.ic_switch_on);
        else
            mSwitchPairModelOnly.setImageResource(R.mipmap.ic_switch_off);

        if(AccessSharedPreferences.readProperty(this, CONFIG_FILE_NAME, ENABLE_PEDOMETER, true))
            mSwitchEnablePedometer.setImageResource(R.mipmap.ic_switch_on);
        else
            mSwitchEnablePedometer.setImageResource(R.mipmap.ic_switch_off);

        if(AccessSharedPreferences.readProperty(this, CONFIG_FILE_NAME, ENABLE_BLOODPRESSURE, true))
            mSwitchEnableBloodPressure.setImageResource(R.mipmap.ic_switch_on);
        else
            mSwitchEnableBloodPressure.setImageResource(R.mipmap.ic_switch_off);

        if(AccessSharedPreferences.readProperty(this, CONFIG_FILE_NAME, ENABLE_SCALE, true))
            mSwitchEnableScale.setImageResource(R.mipmap.ic_switch_on);
        else
            mSwitchEnableScale.setImageResource(R.mipmap.ic_switch_off);

        if(AccessSharedPreferences.readProperty(this, CONFIG_FILE_NAME, ENABLE_HEIGHT_RULER, true))
            mSwitchEnableHeightRuler.setImageResource(R.mipmap.ic_switch_on);
        else
            mSwitchEnableHeightRuler.setImageResource(R.mipmap.ic_switch_off);


    }

    /**
     * 初始化事件处理
     */
    private void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        mSwitchPairModelOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, PAIR_MODEL_ONLY, true)) {
                    mSwitchPairModelOnly.setImageResource(R.mipmap.ic_switch_off);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, PAIR_MODEL_ONLY, false);
                } else {
                    mSwitchPairModelOnly.setImageResource(R.mipmap.ic_switch_on);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, PAIR_MODEL_ONLY, true);
                }
            }
        });
        mSwitchEnablePedometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_PEDOMETER, true)) {
                    mSwitchEnablePedometer.setImageResource(R.mipmap.ic_switch_off);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_PEDOMETER, false);
                } else {
                    mSwitchEnablePedometer.setImageResource(R.mipmap.ic_switch_on);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_PEDOMETER, true);
                }
            }
        });
        mSwitchEnableBloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_BLOODPRESSURE, true)) {
                    mSwitchEnableBloodPressure.setImageResource(R.mipmap.ic_switch_off);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_BLOODPRESSURE, false);
                } else {
                    mSwitchEnableBloodPressure.setImageResource(R.mipmap.ic_switch_on);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_BLOODPRESSURE, true);
                }
            }
        });
        mSwitchEnableScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_SCALE, true)) {
                    mSwitchEnableScale.setImageResource(R.mipmap.ic_switch_off);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_SCALE, false);
                } else {
                    mSwitchEnableScale.setImageResource(R.mipmap.ic_switch_on);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_SCALE, true);
                }
            }
        });
        mSwitchEnableHeightRuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_HEIGHT_RULER, true)) {
                    mSwitchEnableHeightRuler.setImageResource(R.mipmap.ic_switch_off);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_HEIGHT_RULER, false);
                } else {
                    mSwitchEnableHeightRuler.setImageResource(R.mipmap.ic_switch_on);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, ENABLE_HEIGHT_RULER, true);
                }
            }
        });
    }

}
