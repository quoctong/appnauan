package com.quocvusolution.nhanau;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FoodListAdapter extends ArrayAdapter<FoodPair> {
    private Context mContext;

    public FoodListAdapter(Context context, ArrayList<FoodPair> items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FoodPair item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_list_item, parent, false);
        }
        final TextView tvTitleLeft = (TextView) convertView.findViewById(R.id.tv_food_title_left);
        TextView tvDescriptionLeft = (TextView) convertView.findViewById(R.id.tv_food_description_left);
        TextView tvPriceLeft = (TextView) convertView.findViewById(R.id.tv_food_price_left);
        final ImageView ivPhotoLeft = (ImageView) convertView.findViewById(R.id.iv_food_photo_left);
        final ImageButton btnLikeLeft = (ImageButton) convertView.findViewById(R.id.btn_food_like_left);
        ImageView ivCreatedUserPhotoLeft = (ImageView) convertView.findViewById(R.id.iv_food_created_user_photo_left);
        TextView tvCreatedUserDisplayNameLeft = (TextView) convertView.findViewById(R.id.tv_food_created_user_display_name_left);
        TextView tvCreatedUserLikedCountLeft = (TextView) convertView.findViewById(R.id.tv_food_created_user_liked_count_left);
        TextView tvCreatedUserCommentedCountLeft = (TextView) convertView.findViewById(R.id.tv_food_created_user_commented_count_left);

        tvTitleLeft.setText(item.getFoodLeft().getTitle());
        tvTitleLeft.setTag(item.getFoodLeft().getId());
        tvTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int foodId = (int) tvTitleLeft.getTag();
                ((MainActivity) mContext).showFoodDetailFragment(foodId);
            }
        });

        tvDescriptionLeft.setText(item.getFoodLeft().getDescription());
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        tvPriceLeft.setText(nf.format(item.getFoodLeft().getPrice()) + " " + convertView.getResources().getString(R.string.text_symbol_currency));

        ivPhotoLeft.setImageBitmap(item.getFoodLeft().getBitmapThumbPhoto());
        ivPhotoLeft.setTag(item.getFoodLeft().getId());
        ivPhotoLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int foodId = (int) ivPhotoLeft.getTag();
                ((MainActivity) mContext).showFoodDetailFragment(foodId);
            }
        });

        btnLikeLeft.setTag(item.getFoodLeft().getId());
        btnLikeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int foodId = (int) btnLikeLeft.getTag();
            }
        });

        if (item.getFoodLeft().getBimapCreatedUserPhoto() != null) {
            ivCreatedUserPhotoLeft.setImageBitmap(item.getFoodLeft().getBimapCreatedUserPhoto());
        }
        tvCreatedUserDisplayNameLeft.setText(item.getFoodLeft().getCreatedUserDisplayName());
        tvCreatedUserLikedCountLeft.setText(Integer.toString(item.getFoodLeft().getCreatedUserLikedCount()));
        tvCreatedUserCommentedCountLeft.setText(Integer.toString(item.getFoodLeft().getCreatedUserCommentedCount()));

        if (item.getFoodRight() != null) {
            final TextView tvTitleRight = (TextView) convertView.findViewById(R.id.tv_food_title_right);
            TextView tvDescriptionRight = (TextView) convertView.findViewById(R.id.tv_food_description_right);
            TextView tvPriceRight = (TextView) convertView.findViewById(R.id.tv_food_price_right);
            final ImageView ivPhotoRight = (ImageView) convertView.findViewById(R.id.iv_food_photo_right);
            final ImageButton btnLikeRight = (ImageButton) convertView.findViewById(R.id.btn_food_like_right);
            ImageView ivCreatedUserPhotoRight = (ImageView) convertView.findViewById(R.id.iv_food_created_user_photo_right);
            TextView tvCreatedUserDisplayNameRight = (TextView) convertView.findViewById(R.id.tv_food_created_user_display_name_right);
            TextView tvCreatedUserLikedCountRight = (TextView) convertView.findViewById(R.id.tv_food_created_user_liked_count_right);
            TextView tvCreatedUserCommentedCountRight = (TextView) convertView.findViewById(R.id.tv_food_created_user_commented_count_right);

            tvTitleRight.setText(item.getFoodRight().getTitle());
            tvTitleRight.setTag(item.getFoodRight().getId());
            tvTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) tvTitleRight.getTag();
                    ((MainActivity) mContext).showFoodDetailFragment(foodId);
                }
            });

            tvDescriptionRight.setText(item.getFoodRight().getDescription());
            tvPriceRight.setText(nf.format(item.getFoodRight().getPrice()) + " " + convertView.getResources().getString(R.string.text_symbol_currency));

            ivPhotoRight.setImageBitmap(item.getFoodRight().getBitmapThumbPhoto());
            ivPhotoRight.setTag(item.getFoodRight().getId());
            ivPhotoRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) ivPhotoRight.getTag();
                    ((MainActivity) mContext).showFoodDetailFragment(foodId);
                }
            });

            btnLikeRight.setTag(item.getFoodRight().getId());
            btnLikeRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) btnLikeRight.getTag();
                }
            });

            if (item.getFoodRight().getBimapCreatedUserPhoto() != null) {
                ivCreatedUserPhotoRight.setImageBitmap(item.getFoodRight().getBimapCreatedUserPhoto());
            }
            tvCreatedUserDisplayNameRight.setText(item.getFoodRight().getCreatedUserDisplayName());
            tvCreatedUserLikedCountRight.setText(Integer.toString(item.getFoodRight().getCreatedUserLikedCount()));
            tvCreatedUserCommentedCountRight.setText(Integer.toString(item.getFoodRight().getCreatedUserCommentedCount()));
        } else {
            LinearLayout laRight = (LinearLayout) convertView.findViewById(R.id.la_food_right);
            laRight.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}