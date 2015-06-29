package com.loliclub.eater.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loliclub.eater.R;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mImageView_userHead;
    private EditText mEditText_userName;
    private EditText mEditText_password;
    private Button mButton_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        mImageView_userHead = (ImageView) findViewById(R.id.signActivity_userHead);
        mEditText_userName = (EditText) findViewById(R.id.signActivity_username);
        mEditText_password = (EditText) findViewById(R.id.signActivity_password);
        mButton_signUp = (Button) findViewById(R.id.signActivity_btn_signup);

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
        mImageView_userHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEditText_userName.getText().toString();
                String password = mEditText_password.getText().toString();
                if (userName == null || "".equals(userName)) {

                } else if (password == null || "".equals(password)) {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
