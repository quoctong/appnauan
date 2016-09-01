package com.quocvusolution.nhanau;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;

import com.quocvusolution.utility.AndroidUtility;

public class FoodRatingDialogFragment extends DialogFragment {
    private Activity mActivity;
    private int mFoodId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mFoodId = getArguments().getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_rating_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        final RatingBar rtbarFoodRating = (RatingBar) view.findViewById(R.id.rtbar_food_rating);

        Button btnFoodRating = (Button) view.findViewById(R.id.btn_food_rating);
        btnFoodRating.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mActivity).doRateFood(mFoodId, rtbarFoodRating.getRating());
                getDialog().cancel();
            }
        });

        Button btnCancelRating = (Button) view.findViewById(R.id.btn_cancel_rating);
        btnCancelRating.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

        return view;
    }

    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onResume();
    }
}