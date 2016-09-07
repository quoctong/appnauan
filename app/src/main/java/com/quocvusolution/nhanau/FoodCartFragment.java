package com.quocvusolution.nhanau;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.quocvusolution.utility.AndroidUtility;
import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.HttpUtility;
import com.quocvusolution.utility.ImageUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodCartFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView mLView;
    FoodCartAdapter mAdapter;
    ArrayList<FoodCart> mItems = new ArrayList<FoodCart>();
    FoodCartStore mFoodCartStore;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.text_food_cart));
        mFoodCartStore = ((MainActivity) getActivity()).getFoodCartStore();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.food_cart_list, container, false);
        mItems = mFoodCartStore.getFoodCartList(0, 100);
        double total = 0;
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                FoodCart item = mItems.get(i);
                fillImage(item);
                total += item.getFood().getPrice() * item.getFoodCount();
            }
            mLView = (ListView) mView.findViewById(R.id.lv_food_cart_list);
            mAdapter = new FoodCartAdapter(getActivity(), mItems);
            mLView.setAdapter(mAdapter);
            AndroidUtility.setDynamicHeight(mLView);
            mLView.requestLayout();
            TextView tvTotal = (TextView) mView.findViewById(R.id.tv_food_cart_total);
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            tvTotal.setText(nf.format(total) + " " + mView.getResources().getString(R.string.text_symbol_currency));
        }
        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void fillImage(FoodCart item) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (item.getFood() != null) {
            File userPhotoFile = FileUtility.getOutputMediaFile(item.getFood().getThumbPhoto(), ((MainActivity) getActivity()).getStorageDirName());
            item.getFood().setBitmapThumbPhoto(ImageUtility.loadImageFile(userPhotoFile.getAbsolutePath()));
        }
    }
}
