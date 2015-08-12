package com.loliclub.bluetoothtools.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loliclub.bluetoothtools.R;

import java.util.List;

public class MyCaptionAdapter extends ArrayAdapter<String> {

    private List<String> mCaptionList;

    public MyCaptionAdapter(Context context, List<String> captionList) {
        super(context, R.layout.list_caption_item);
        this.mCaptionList = captionList;
    }

    @Override
    public int getCount() {
        if ( mCaptionList == null)
            return 0;
        return mCaptionList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (mCaptionList.size() > 0) {
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.list_caption_item, null);
                holder.list_content_main = (TextView) convertView.findViewById(R.id.list_content_main);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            holder.list_content_main.setText(mCaptionList.get(position));
            // 设置显示效果
            if (mCaptionList.size() == 5 && position == 0) {
                holder.list_content_main.setTextColor(getContext().getResources().getColor(R.color.text_white_sub));
            }
            // 设置动画效果
            if (position == mCaptionList.size() - 1) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_slide_from_bottom);
                convertView.setAnimation(animation);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView list_content_main;
    }

}
