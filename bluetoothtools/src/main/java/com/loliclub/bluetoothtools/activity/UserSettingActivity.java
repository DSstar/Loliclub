package com.loliclub.bluetoothtools.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.util.AccessSharedPreferences;
import com.loliclub.bluetoothtools.util.UnitConversionUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UserSettingActivity extends AppCompatActivity {

    public static final String CONFIG_FILE_NAME = "config";
    public static final String USER_NAME = "user_name";
    public static final String USER_AGE = "user_age";
    public static final String USER_HEIGHT = "user_height";
    public static final String USER_WEIGHT = "user_weight";
    public static final String USER_STEP_GOAL = "user_step_goal";
    public static final String USER_WEIGHT_GOAL = "user_weight_goal";
    public static final String IS_MAN = "isMan";
    public static final String IS_24HOUR_MODEL = "is24HourModel";
    public static final String IS_WEIGHT_UNIT_KG = "isWeightUnitKG";
    public static final String IS_DISTANCE_UNIT_KM = "isDistanceUnitKM";

    private Toolbar mToolbar;
    private MaterialEditText mETName;
    private MaterialEditText mETAge;
    private MaterialEditText mETHeight;
    private MaterialEditText mETWeight;
    private MaterialEditText mETStepGoal;
    private MaterialEditText mETWeightGoal;
    private Spinner mSPHeightUnit;
    private Spinner mSPWeightUnit;
    private Spinner mSPWeightGoalUnit;
    private ImageView mImgSwitchTimeModel;
    private RadioButton mButtonMan;
    private RadioButton mButtonWoman;
    private RadioButton mButtonKG;
    private RadioButton mButtonLB;
    private RadioButton mButtonKM;
    private RadioButton mButtonFT;

    private int spHeightUnit;
    private int spWeightUnit;
    private int spWeightGoalUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        initParams();
        initUI();
        initEvent();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        spHeightUnit = 0;
        spWeightUnit = 0;
        spWeightGoalUnit = 0;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mETName = (MaterialEditText) findViewById(R.id.user_setting_et_name);
        mETAge = (MaterialEditText) findViewById(R.id.user_setting_et_age);
        mETHeight = (MaterialEditText) findViewById(R.id.user_setting_et_height);
        mETWeight = (MaterialEditText) findViewById(R.id.user_setting_et_weight);
        mETStepGoal = (MaterialEditText) findViewById(R.id.user_setting_et_step_goal);
        mETWeightGoal = (MaterialEditText) findViewById(R.id.user_setting_et_weight_goal);
        mSPHeightUnit = (Spinner) findViewById(R.id.user_setting_spinner_height);
        mSPWeightUnit = (Spinner) findViewById(R.id.user_setting_spinner_weight);
        mSPWeightGoalUnit = (Spinner) findViewById(R.id.user_setting_spinner_weight_goal);
        mImgSwitchTimeModel = (ImageView) findViewById(R.id.user_setting_switch_24_hour);
        mButtonMan = (RadioButton) findViewById(R.id.user_setting_sex_man);
        mButtonWoman = (RadioButton) findViewById(R.id.user_setting_sex_woman);
        mButtonKG = (RadioButton) findViewById(R.id.user_setting_unit_kg);
        mButtonLB = (RadioButton) findViewById(R.id.user_setting_unit_lb);
        mButtonKM = (RadioButton) findViewById(R.id.user_setting_unit_km);
        mButtonFT = (RadioButton) findViewById(R.id.user_setting_unit_ft);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 初始化控件
        mETName.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, USER_NAME, ""));
        mETAge.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, USER_AGE, ""));
        mETHeight.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, USER_HEIGHT, ""));
        mETWeight.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, USER_WEIGHT, ""));
        mETStepGoal.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, USER_STEP_GOAL, ""));
        mETWeightGoal.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, USER_WEIGHT_GOAL, ""));
        if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, IS_MAN, true)) {
            mButtonMan.setChecked(true);
            mButtonWoman.setChecked(false);
        } else {
            mButtonMan.setChecked(false);
            mButtonWoman.setChecked(true);
        }
        if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, IS_24HOUR_MODEL, true))
            mImgSwitchTimeModel.setImageResource(R.mipmap.ic_switch_on);
        else
            mImgSwitchTimeModel.setImageResource(R.mipmap.ic_switch_off);
        if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, IS_WEIGHT_UNIT_KG, true)) {
            mButtonKG.setChecked(true);
            mButtonLB.setChecked(false);
        } else {
            mButtonKG.setChecked(false);
            mButtonLB.setChecked(true);
        }
        if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, IS_DISTANCE_UNIT_KM, true)) {
            mButtonKM.setChecked(true);
            mButtonFT.setChecked(false);
        } else {
            mButtonKM.setChecked(false);
            mButtonFT.setChecked(true);
        }
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
        mSPHeightUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String height = mETHeight.getText().toString().trim();
                if (!"".equals(height)) {
                    if (position == 0 && spHeightUnit != 0) {
                        mETHeight.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME,
                                USER_HEIGHT, ""));
                    } else if (position != 0 && spHeightUnit == 0) {
                        double value = Double.parseDouble(height);
                        mETHeight.setText(String.valueOf(UnitConversionUtil.unitConversionCM2FT(value, 1)));
                    }
                }
                spHeightUnit = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSPWeightUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String weight = mETWeight.getText().toString();
                if (!"".equals(weight)) {
                    if (position == 0 && spWeightUnit != 0) {
                        mETWeight.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME,
                                USER_WEIGHT, ""));
                    } else if (position != 0 && spWeightUnit == 0) {
                        double value = Double.parseDouble(weight);
                        mETWeight.setText(String.valueOf(UnitConversionUtil.unitConversionKG2LB(value, 1)));
                    }
                }
                spWeightUnit = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSPWeightGoalUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String weightGoal = mETWeightGoal.getText().toString().trim();
                if (!"".equals(weightGoal)) {
                    if (position == 0 && spWeightGoalUnit != 0) {
                        mETWeightGoal.setText(AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME,
                                USER_WEIGHT_GOAL, ""));
                    } else if (position != 0 && spWeightGoalUnit == 0) {
                        double value = Double.parseDouble(weightGoal);
                        mETWeightGoal.setText(String.valueOf(UnitConversionUtil.unitConversionKG2LB(value, 1)));
                    }
                }
                spWeightGoalUnit = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mImgSwitchTimeModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessSharedPreferences.readProperty(getApplication(), CONFIG_FILE_NAME, IS_24HOUR_MODEL, true)) {
                    mImgSwitchTimeModel.setImageResource(R.mipmap.ic_switch_off);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_24HOUR_MODEL, false);
                } else {
                    mImgSwitchTimeModel.setImageResource(R.mipmap.ic_switch_on);
                    AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_24HOUR_MODEL, true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 保存数据
        String name = mETName.getText().toString().trim();
        if (!"".equals(name))
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_NAME, name);
        String age = mETAge.getText().toString().trim();
        if (!"".equals(age))
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_AGE, age);
        String height = mETHeight.getText().toString().trim();
        if (!"".equals(height)) {
            double value = Double.parseDouble(height);
            if (spHeightUnit != 0)
                AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_HEIGHT,
                        String.valueOf(UnitConversionUtil.unitConversionFT2CM(value, 1)));
            else
                AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_HEIGHT,
                        String.valueOf(value));
        }
        String weight = mETWeight.getText().toString().trim();
        if (!"".equals(weight)) {
            double value = Double.parseDouble(weight);
            if (spWeightUnit != 0)
                AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_WEIGHT,
                        String.valueOf(UnitConversionUtil.unitConversionLB2KG(value, 1)));
            else
                AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_WEIGHT,
                        String.valueOf(value));
        }
        String stepGoal = mETStepGoal.getText().toString().trim();
        if (!"".equals(stepGoal))
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_STEP_GOAL, stepGoal);
        String weightGoal = mETWeightGoal.getText().toString().trim();
        if (!"".equals(weightGoal)) {
            double value = Double.parseDouble(weightGoal);
            if (spWeightGoalUnit != 0)
                AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_WEIGHT_GOAL,
                        String.valueOf(UnitConversionUtil.unitConversionLB2KG(value, 1)));
            else
                AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, USER_WEIGHT_GOAL,
                        String.valueOf(value));
        }
        if (mButtonMan.isChecked())
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_MAN, true);
        else
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_MAN, false);
        if (mButtonKG.isChecked())
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_WEIGHT_UNIT_KG, true);
        else
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_WEIGHT_UNIT_KG, false);
        if (mButtonKM.isChecked())
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_DISTANCE_UNIT_KM, true);
        else
            AccessSharedPreferences.saveProperty(getApplication(), CONFIG_FILE_NAME, IS_DISTANCE_UNIT_KM, false);
        super.onBackPressed();
    }
}
