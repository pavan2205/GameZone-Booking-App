package com.example.gamezonebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gamezonebooking.R;

public class ScreenAdapter extends BaseAdapter {
    private Context context;
    private int screenCount;

    public ScreenAdapter(Context context, int screenCount) {
        this.context = context;
        this.screenCount = screenCount;
    }

    @Override
    public int getCount() {
        return screenCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // Inflate the custom button item layout
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_button_item, parent, false);
            textView = convertView.findViewById(R.id.custom_card_button);
            convertView.setTag(textView);
        } else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(String.valueOf(position + 1));

        // Adjust width and height of the custom button item
        int width = context.getResources().getDimensionPixelSize(R.dimen.custom_button_item_width);
        int height = context.getResources().getDimensionPixelSize(R.dimen.custom_button_item_height);
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        convertView.setLayoutParams(layoutParams);

        return convertView;
    }

}
