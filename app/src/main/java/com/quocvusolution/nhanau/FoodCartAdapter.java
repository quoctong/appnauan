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

public class FoodCartAdapter extends ArrayAdapter<FoodCart> {
    private Context mContext;

    public FoodCartAdapter(Context context, ArrayList<FoodCart> items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodCart item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_cart_list_item, parent, false);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.iv_food_cart_photo);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_food_cart_title);
        TextView tvCount = (TextView) convertView.findViewById(R.id.tv_food_cart_count);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_food_cart_price);

        if (item.getFood() != null) {
            if (item.getFood().getBitmapThumbPhoto() != null) {
                ivPhoto.setImageBitmap(item.getFood().getBitmapThumbPhoto());
            }
            tvTitle.setText(item.getFood().getTitle());
            tvCount.setText(Integer.toString(item.getFoodCount()));
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            tvPrice.setText(nf.format(item.getFood().getPrice()) + " " + convertView.getResources().getString(R.string.text_symbol_currency));
        }

        return convertView;
    }
}