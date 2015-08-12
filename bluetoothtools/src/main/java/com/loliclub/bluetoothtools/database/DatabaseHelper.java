package com.loliclub.bluetoothtools.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 志华 on 2015/8/11.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BluetoothTools";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建设备表
        db.execSQL("create table if not exists Device(id varchar(20) primary key not null, sn varchar(20)," +
                "deviceName varchar(10), modelNumber varchar(10), deviceType varchar(5), password varchar(10)," +
                "broadcastID varchar(10), softwareVersion varchar(5), hardwareVersion varchar(5), firmwareVersion varchar(5)," +
                "manufactureName varchar(10), systemId varchar(10), deviceUserNumber integer, pairStatus integer, maxUserQuantity integer," +
                "pairSingSignature integer, supportDownloadInfoFeature integer, protocolType varchar(5), macAddress varchar(25), rssi integer);");
        // 创建数据表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
