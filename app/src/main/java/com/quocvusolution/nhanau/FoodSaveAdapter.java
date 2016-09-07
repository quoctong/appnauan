package com.quocvusolution.nhanau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodSaveAdapter extends ArrayAdapter<FoodSave> {
    private Context mContext;

    public FoodSaveAdapter(Context context, ArrayList<FoodSave> items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodSave item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_save_list_item, parent, false);
        }

        final ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.iv_food_save_photo);
        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_food_save_title);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_food_save_price);

        if (item.getFood() != null) {
            if (item.getFood().getBitmapThumbPhoto() != null) {
                ivPhoto.setImageBitmap(item.getFood().getBitmapThumbPhoto());
            }
            tvTitle.setText(item.getFood().getTitle());
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            tvPrice.setText(nf.format(item.getFood().getPrice()) + " " + convertView.getResources().getString(R.string.text_symbol_currency));
        }

        if (item.getFood() != null) {
            ivPhoto.setTag(item.getFood().getId());
            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) ivPhoto.getTag();
                    ((MainActivity) mContext).showFoodDetailFragment(foodId);
                }
            });

            tvTitle.setTag(item.getFood().getId());
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) tvTitle.getTag();
                    ((MainActivity) mContext).showFoodDetailFragment(foodId);
                }
            });
        }

        return convertView;
    }
}