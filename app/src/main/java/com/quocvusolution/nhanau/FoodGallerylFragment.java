package com.quocvusolution.nhanau;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.ImageUtility;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodGallerylFragment extends Fragment {
    private int mFoodId = 0;
    private Food mFood = null;
    FoodStore mFoodStore;
    private View mView;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.food_gallery, container, false);
        mFoodId = getArguments().getInt("id");
        mFoodStore = ((MainActivity) getActivity()).getFoodStore();
        mFood = mFoodStore.getById(mFoodId);

        mPager = (ViewPager) mView.findViewById(R.id.food_gallery_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        if (mFood != null) {
            getActivity().setTitle(mFood.getTitle());
            final LinearLayout laGallery = (LinearLayout) mView.findViewById(R.id.food_gallery);
            if (mFood.getPhotos() != null) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                for (int i = 0; i < mFood.getPhotos().length; i++) {
                    File photoFile = FileUtility.getOutputMediaFile(mFood.getPhotos()[i], ((MainActivity) getActivity()).getStorageDirName());
                    View vFoodGalleryItem = inflater.inflate(R.layout.food_gallery_item, container, false);
                    final ImageView ivFoodGalleryImage = (ImageView) vFoodGalleryItem.findViewById(R.id.food_gallery_image);
                    ivFoodGalleryImage.setImageBitmap(ImageUtility.scaleWImageFile(photoFile.getAbsolutePath(), width/2));
                    ivFoodGalleryImage.setTag(i);
                    ivFoodGalleryImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int photoId = (int)ivFoodGalleryImage.getTag();
                            mPager.setCurrentItem(photoId);
                        }
                    });
                    laGallery.addView(vFoodGalleryItem);
                }
            }
        }

        ((MainActivity) getActivity()).getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getActionBarDrawerToggle().setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((MainActivity) getActivity()).getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                ((MainActivity) getActivity()).getActionBarDrawerToggle().setToolbarNavigationClickListener(null);
                ((MainActivity) getActivity()).showFoodDetailFragment(mFoodId);
                ((MainActivity) getActivity()).getSpeech().stop();
            }
        });

        return mView;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle args = new Bundle();
            args.putInt("photo_id", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mFood.getPhotos() != null) {
                return mFood.getPhotos().length;
            }
            return 0;
        }
    }

    private class ScreenSlidePageFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.food_gallery_item, container, false);
            int photoId = getArguments().getInt("photo_id");
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            File photoFile = FileUtility.getOutputMediaFile(mFood.getPhotos()[photoId], ((MainActivity) getActivity()).getStorageDirName());
            ImageView ivFoodGalleryImage = (ImageView) rootView.findViewById(R.id.food_gallery_image);
            ivFoodGalleryImage.setImageBitmap(ImageUtility.scaleWImageFile(photoFile.getAbsolutePath(), width));
            return rootView;
        }
    }
}