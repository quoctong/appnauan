package com.quocvusolution.nhanau;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import com.quocvusolution.utility.AndroidUtility;
import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.ImageUtility;

import java.io.File;

public class FoodShareDialogFragment extends DialogFragment {
    private int mFoodId;
    FoodStore mFoodStore;
    Food mFood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_share_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mFoodId = getArguments().getInt("id");
        mFoodStore = ((MainActivity) getActivity()).getFoodStore();
        mFood = mFoodStore.getById(mFoodId);

        ImageButton btnInstallSpeech = (ImageButton) view.findViewById(R.id.btn_food_share_facebook);
        btnInstallSpeech.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
                if (mFood.getPhotos() != null && mFood.getPhotos().length > 0) {
                    File photoFile = FileUtility.getOutputMediaFile(mFood.getPhotos()[0], ((MainActivity) getActivity()).getStorageDirName());
                    Bitmap bitmap = ImageUtility.loadImageFile(photoFile.getAbsolutePath());
                    if (bitmap != null) {
                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(bitmap)
                                .build();
                        ShareContent content = new ShareMediaContent.Builder()
                                .addMedium(photo)
                                .build();
                        ShareDialog.show(getActivity(), content);
                    }
                }
            }
        });

        Button btnCancelInstall = (Button) view.findViewById(R.id.btn_cancel_share);
        btnCancelInstall.setOnClickListener(new Button.OnClickListener() {
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