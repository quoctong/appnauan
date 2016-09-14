package com.quocvusolution.nhanau;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserCommentListAdapter extends ArrayAdapter<UserComment> {
    private Context mContext;

    public UserCommentListAdapter(Context context, ArrayList<UserComment> items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserComment item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_detail_comment_list_item, parent, false);
        }

        ImageView ivUserPhotoRight = (ImageView) convertView.findViewById(R.id.iv_item_detail_comment_user_photo);
        TextView tvUserDisplayName = (TextView) convertView.findViewById(R.id.tv_item_detail_comment_user_display_name);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_item_detail_comment_content);
        TextView tvCommentedAt = (TextView) convertView.findViewById(R.id.tv_item_detail_comment_commented_at);
        TextView tvLikedCount = (TextView) convertView.findViewById(R.id.tv_item_detail_comment_liked_count);
        TextView tvCommentedCount = (TextView) convertView.findViewById(R.id.tv_item_detail_comment_commented_count);
        TextView tvSharedCount = (TextView) convertView.findViewById(R.id.tv_item_detail_comment_shared_count);

        if (item.getBitmapUserPhoto() != null) {
            ivUserPhotoRight.setImageBitmap(item.getBitmapUserPhoto());
        }
        tvUserDisplayName.setText(item.getUserDisplayName());
        tvContent.setText(item.getContent());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        tvCommentedAt.setText(df.format(item.getCreatedAt()));
        String timeAgo = DateUtils.getRelativeTimeSpanString(item.getCreatedAt().getTime(), (new Date()).getTime(), DateUtils.HOUR_IN_MILLIS).toString();
        tvCommentedAt.setText(timeAgo);
        tvLikedCount.setText(Integer.toString(item.getLikedCount()));
        tvCommentedCount.setText(Integer.toString(item.getCommentedCount()));
        tvSharedCount.setText(Integer.toString(item.getSharedCount()));

        return convertView;
    }
}