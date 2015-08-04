package com.loliclub.bluetoothtools.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lifesense.ble.LsBleManager;
import com.loliclub.bluetoothtools.R;
import com.loliclub.bluetoothtools.view.MyCustomDialog;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_FOR_BT = 100;

    private Toolbar mToolbar;
    private ListView mListView;
    private Button mButton_add;
    private MyCustomDialog dialog;

    private LsBleManager mBLEManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParams();
        initUI();
        initEvent();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        mBLEManager = LsBleManager.newInstance();
        if (!mBLEManager.isSupportLowEnergy()) {
            // 提示不支持蓝牙BLE
            Log.e("MainActiviy", "BLE TEST");
            if (dialog != null)
                dialog.dismiss();
            MyCustomDialog.Builder builder = new MyCustomDialog.Builder(this);
            builder.setContent(R.string.tips_dont_support_ble)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            dialog = builder.create();
            dialog.show();
        }
        if (!mBLEManager.isOpenBluetooth()) {
            // 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
            // 那么将会收到RESULT_OK的结果，
            // 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(mIntent, 1);
            // 用enable()方法来开启，无需询问用户(无声息的开启蓝牙设备)
            // mBluetoothAdapter.enable();
            // mBluetoothAdapter.disable();//关闭蓝牙
        }
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.main_listview);
        mButton_add = (Button) findViewById(R.id.main_btn_add);

        // 初始化Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    /**
     * 初始化事件处理
     */
    private void initEvent() {
        mButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBLEManager.isOpenBluetooth()) {
                    // 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
                    // 那么将会收到RESULT_OK的结果，
                    // 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
                    Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(mIntent, REQUEST_CODE_FOR_BT);
                } else
                    startActivity(new Intent(getApplication(), ScanActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_BT) {
            if (resultCode == RESULT_OK)
                startActivity(new Intent(getApplication(), ScanActivity.class));
            else
                Toast.makeText(getApplication(), getString(R.string.tips_dont_open_bt), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_scan_setting:
                startActivity(new Intent(getApplication(), ScanSettingActivity.class));
                break;
            case R.id.action_user_setting:
                startActivity(new Intent(getApplication(), UserSettingActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(getApplication(), AboutActivity.class));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
