package com.loliclub.bluetoothtools.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifesense.ble.bean.LsDeviceInfo;
import com.loliclub.bluetoothtools.R;

import java.util.List;

public class MyDeviceListAdapter extends ArrayAdapter<LsDeviceInfo> {

    private Context mContext;
    private List<LsDeviceInfo> mDeviceInfoList;

    public MyDeviceListAdapter(Context context, List<LsDeviceInfo> deviceInfoList) {
        super(context, R.layout.list_device_item);
        this.mContext = context;
        this.mDeviceInfoList = deviceInfoList;
    }

    @Override
    public int getCount() {
        if ( mDeviceInfoList == null)
            return 0;
        return mDeviceInfoList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (mDeviceInfoList.size() > 0) {
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.list_device_item, null);
                holder.list_img = (ImageView) convertView.findViewById(R.id.list_img);
                holder.list_content_main = (TextView) convertView.findViewById(R.id.list_content_main);
                holder.list_content_sub = (TextView) convertView.findViewById(R.id.list_content_sub);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            LsDeviceInfo deviceInfo = mDeviceInfoList.get(position);
            holder.list_content_main.setText(deviceInfo.getDeviceName());
            holder.list_content_sub.setText(deviceInfo.getMacAddress());
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView list_img;
        TextView list_content_main;
        TextView list_content_sub;
    }

}
