package com.quocvusolution.nhanau;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.quocvusolution.utility.AndroidUtility;
import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.ImageUtility;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodSaveFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView mLView;
    FoodSaveAdapter mAdapter;
    ArrayList<FoodSave> mItems = new ArrayList<FoodSave>();
    FoodSaveStore mFoodSaveStore;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.text_food_save));
        mFoodSaveStore = ((MainActivity) getActivity()).getFoodSaveStore();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.food_save_list, container, false);
        mItems = mFoodSaveStore.getFoodSaveList(0, 100);
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                FoodSave item = mItems.get(i);
                fillImage(item);
            }
            mLView = (ListView) mView.findViewById(R.id.lv_food_save_list);
            mAdapter = new FoodSaveAdapter(getActivity(), mItems, this);
            mLView.setAdapter(mAdapter);
            AndroidUtility.setDynamicHeight(mLView);
            mLView.requestLayout();
        }
        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void fillImage(FoodSave item) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (item.getFood() != null) {
            File userPhotoFile = FileUtility.getOutputMediaFile(item.getFood().getThumbPhoto(), ((MainActivity) getActivity()).getStorageDirName());
            item.getFood().setBitmapThumbPhoto(ImageUtility.loadImageFile(userPhotoFile.getAbsolutePath()));
        }
    }

    public void removeFoodSave(int id) {
        for (int i = 0; i < mItems.size(); i++) {
            FoodSave item = mItems.get(i);
            if (item.getFoodId() == id) {
                mItems.remove(i);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
