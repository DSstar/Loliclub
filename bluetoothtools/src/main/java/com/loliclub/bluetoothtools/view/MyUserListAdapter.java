package com.loliclub.bluetoothtools.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lifesense.ble.bean.DeviceUserInfo;
import com.loliclub.bluetoothtools.R;

import java.util.List;

public class MyUserListAdapter extends ArrayAdapter<DeviceUserInfo> {

    private List<DeviceUserInfo> mUserInfoList;

    public MyUserListAdapter(Context context, List<DeviceUserInfo> userInfoList) {
        super(context, R.layout.list_userinfo_item);
        this.mUserInfoList = userInfoList;
    }

    @Override
    public int getCount() {
        if ( mUserInfoList == null)
            return 0;
        return mUserInfoList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (mUserInfoList.size() > 0) {
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.list_userinfo_item, null);
                holder.list_content_main = (TextView) convertView.findViewById(R.id.list_content_main);
                holder.list_content_sub = (TextView) convertView.findViewById(R.id.list_content_sub);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            holder.list_content_main.setText(mUserInfoList.get(position).getUserName());
            holder.list_content_sub.setText(mUserInfoList.get(position).getUserNumber() + "");
            // 设置动画效果
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_slide_from_bottom);
            convertView.setAnimation(animation);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView list_content_main;
        TextView list_content_sub;
    }

}
