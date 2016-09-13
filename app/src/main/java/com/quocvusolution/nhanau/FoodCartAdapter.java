package com.quocvusolution.nhanau;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodCartAdapter extends ArrayAdapter<FoodCart> {
    private Context mContext;
    private FoodCartFragment mParent;
    private FoodCartStore mFoodCartStore;

    public FoodCartAdapter(Context context, ArrayList<FoodCart> items, FoodCartFragment parent) {
        super(context, 0, items);
        mContext = context;
        mParent = parent;
        mFoodCartStore = ((MainActivity) mContext).getFoodCartStore();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FoodCart item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_cart_list_item, parent, false);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.iv_food_cart_photo);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_food_cart_title);
        final TextView tvCount = (TextView) convertView.findViewById(R.id.tv_food_cart_count);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_food_cart_price);
        ImageButton btnIncr = (ImageButton) convertView.findViewById(R.id.btn_food_cart_incr);
        ImageButton btnDecr = (ImageButton) convertView.findViewById(R.id.btn_food_cart_decr);
        ImageButton btnDel = (ImageButton) convertView.findViewById(R.id.btn_food_cart_del);

        if (item.getFood() != null) {
            if (item.getFood().getBitmapThumbPhoto() != null) {
                ivPhoto.setImageBitmap(item.getFood().getBitmapThumbPhoto());
            }
            tvTitle.setText(item.getFood().getTitle());
            tvCount.setText(Integer.toString(item.getFoodCount()));
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            tvPrice.setText(nf.format(item.getFood().getPrice()) + " " + convertView.getResources().getString(R.string.text_symbol_currency));

            btnIncr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setFoodCount(item.getFoodCount() + 1);
                    tvCount.setText(Integer.toString(item.getFoodCount()));
                    mFoodCartStore.update(item);
                    mParent.updateTotal();
                }
            });

            btnDecr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = item.getFoodCount() - 1;
                    if (count >= 1) {
                        item.setFoodCount(count);
                        tvCount.setText(Integer.toString(item.getFoodCount()));
                        mFoodCartStore.update(item);
                        mParent.updateTotal();
                    }
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFoodCartStore.delete(item.getFoodId());
                    mParent.removeFoodCart(item.getFoodId());
                }
            });
        }

        return convertView;
    }
}