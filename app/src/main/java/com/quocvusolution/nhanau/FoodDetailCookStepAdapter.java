package com.quocvusolution.nhanau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodDetailCookStepAdapter extends ArrayAdapter<String> {
    private Context mContext;

    public FoodDetailCookStepAdapter(Context context, String[] items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String content = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_detail_cook_cooking_steps_item, parent, false);
        }
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_food_detail_cook_cooking_steps_content);
        tvContent.setText(content);
        return convertView;
    }
}

