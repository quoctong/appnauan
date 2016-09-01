package com.quocvusolution.nhanau;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.ImageUtility;

public class FoodAutoCompleteAdapter extends ArrayAdapter<Food> {
    private Context mContext;
    private ArrayList<Food> mItems;
    private int viewResourceId;
    private FoodStore mFoodStore;

    public FoodAutoCompleteAdapter(Context context, int viewResourceId) {
        super(context, viewResourceId);
        mContext = context;
        mFoodStore = ((MainActivity) mContext).getFoodStore();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        if (mItems != null) {
            Food item = mItems.get(position);
            if (item != null) {
                TextView tvTitle = (TextView) v.findViewById(R.id.tv_suggest_food_title);
                tvTitle.setText(item.getTitle());

                TextView tvPrice = (TextView) v.findViewById(R.id.tv_suggest_food_price);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
                tvPrice.setText(nf.format(item.getPrice()) + " " + ((MainActivity) mContext).getResources().getString(R.string.text_symbol_currency));

                ImageView ivThumbPhoto = (ImageView) v.findViewById(R.id.im_icon_suggest);

                File thumbPhotoFile = FileUtility.getOutputMediaFile(item.getThumbPhoto(), ((MainActivity) mContext).getStorageDirName());
                item.setBitmapThumbPhoto(ImageUtility.scaleWImageFile(thumbPhotoFile.getAbsolutePath(), 50));
                ivThumbPhoto.setImageBitmap(item.getBitmapThumbPhoto());
            }
        }

        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return "";
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                String suggest = constraint.toString().toLowerCase();
                mItems = mFoodStore.getSuggestList(suggest, 0, 10);
                FilterResults filterResults = new FilterResults();
                if (mItems != null) {
                    filterResults.values = mItems;
                    filterResults.count = mItems.size();
                }
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Food> filteredList = (ArrayList<Food>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Food c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}