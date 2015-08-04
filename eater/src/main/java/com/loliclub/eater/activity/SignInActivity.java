package com.loliclub.eater.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loliclub.eater.R;

public class SignInActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTextView_name_label;
    private TextView mTextView_password_label;
    private TextView mTextView_signup;
    private TextView mTextView_signIn;
    private EditText mEditText_name;
    private EditText mEditText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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
        mTextView_name_label = (TextView) findViewById(R.id.signActivity_username_label);
        mTextView_password_label = (TextView) findViewById(R.id.signActivity_password_label);
        mTextView_signIn = (TextView) findViewById(R.id.signActivity_btn_signin);
        mTextView_signup = (TextView) findViewById(R.id.signActivity_btn_signup);
        mEditText_name = (EditText) findViewById(R.id.signActivity_username);
        mEditText_password = (EditText) findViewById(R.id.signActivity_password);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        mEditText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() == null || "".equals(s.toString()))
                    mTextView_name_label.setVisibility(View.INVISIBLE);
                else
                    mTextView_name_label.setVisibility(View.VISIBLE);
            }
        });
        mEditText_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    mTextView_name_label.setTextColor(getResources().getColor(R.color.color_primary));
                else
                    mTextView_name_label.setTextColor(getResources().getColor(R.color.text_black_sub));
            }
        });
        mEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() == null || "".equals(s.toString()))
                    mTextView_password_label.setVisibility(View.INVISIBLE);
                else
                    mTextView_password_label.setVisibility(View.VISIBLE);
            }
        });
        mEditText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    mTextView_password_label.setTextColor(getResources().getColor(R.color.color_primary));
                else
                    mTextView_password_label.setTextColor(getResources().getColor(R.color.text_black_sub));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
