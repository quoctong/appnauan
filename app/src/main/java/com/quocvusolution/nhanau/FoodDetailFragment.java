package com.quocvusolution.nhanau;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class FoodDetailFragment extends Fragment {
    private int mFoodId = 0;
    private Food mFood = null;
    FoodStore mFoodStore;
    private View mAddToCartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFoodId = getArguments().getInt("id");
        mFoodStore = ((MainActivity) getActivity()).getFoodStore();
        mFood = mFoodStore.getById(mFoodId);
        fillImage(mFood);
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.food_detail, container, false);
        mAddToCartView = inflater.inflate(R.layout.food_add_msg,(ViewGroup) parent.findViewById(R.id.la_food_add_msg));
        ImageView imageView = (ImageView) mAddToCartView.findViewById(R.id.iv_food_add_msg_image);
        if (mFood.getBitmapPhotos() != null && mFood.getBitmapPhotos().length > 0) {
            imageView.setImageBitmap(mFood.getBitmapPhotos()[0]);
        }
        TextView textView = (TextView) mAddToCartView.findViewById(R.id.iv_food_add_msg_text);
        String text = getResources().getString(R.string.text_add_to_cart).replace(getResources().getString(R.string.text_add_to_cart_var), mFood.getTitle());
        textView.setText(Html.fromHtml(text));
        return parent;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    public void fillImage(Food item) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();
        for (int i = 0; i < item.getPhotos().length; i++) {
            File photoFile = FileUtility.getOutputMediaFile(item.getPhotos()[i], ((MainActivity) getActivity()).getStorageDirName());
            bmList.add(ImageUtility.scaleWImageFile(photoFile.getAbsolutePath(), width));
        }
        Bitmap[] bms = new Bitmap[bmList.size()];
        bmList.toArray(bms);
        item.setBitmapPhotos(bms);
        File createdUserPhotoFile = FileUtility.getOutputMediaFile(item.getCreatedUserPhoto(), ((MainActivity) getActivity()).getStorageDirName());
        item.setBimapCreatedUserPhoto(ImageUtility.loadImageFile(createdUserPhotoFile.getAbsolutePath()));
    }

    public void refresh() {
        if (mFood != null) {
            getActivity().setTitle(mFood.getTitle());
            TextView tvItemLikeCount = (TextView) getView().findViewById(R.id.tv_item_detail_liked_count);
            TextView tvItemPhotoCount = (TextView) getView().findViewById(R.id.tv_item_detail_photo_count);
            final LinearLayout laCart = (LinearLayout) getView().findViewById(R.id.la_item_detail_cart);
            final RelativeLayout laPhotoList = (RelativeLayout) getView().findViewById(R.id.la_item_detail_photo_list);
            final LinearLayout laShare = (LinearLayout) getView().findViewById(R.id.la_item_detail_share);
            final LinearLayout laSpeaker = (LinearLayout) getView().findViewById(R.id.la_item_detail_speaker);
            TextView tvItemTitle = (TextView) getView().findViewById(R.id.tv_item_detail_title);
            ImageView ivItemPhoto = (ImageView) getView().findViewById(R.id.iv_item_detail_photo);
            final ImageButton btnItemLike = (ImageButton) getView().findViewById(R.id.btn_item_detail_like);
            TextView tvItemPrice = (TextView) getView().findViewById(R.id.tv_item_detail_price);
            TextView tvItemCookedTime = (TextView) getView().findViewById(R.id.tv_item_detail_cooked_time);
            TextView tvItemServeCount = (TextView) getView().findViewById(R.id.tv_item_detail_serve_count);
            TextView tvItemCalories = (TextView) getView().findViewById(R.id.tv_item_detail_calories);

            ImageView ivCreatedUserPhoto = (ImageView) getView().findViewById(R.id.iv_item_detail_created_user_photo);
            TextView tvCreatedUserName = (TextView) getView().findViewById(R.id.tv_item_detail_created_user_name);
            TextView tvCreatedAt = (TextView) getView().findViewById(R.id.tv_item_detail_created_at);
            TextView tvDescription = (TextView) getView().findViewById(R.id.tv_item_detail_description);
            final Button btnFollow = (Button) getView().findViewById(R.id.btn_item_detail_follow);
            final Button btnDetailCook = (Button) getView().findViewById(R.id.btn_item_detail_cook);
            final Button btnComment = (Button) getView().findViewById(R.id.btn_item_detail_comment);
            final Button btnRelate = (Button) getView().findViewById(R.id.btn_item_detail_relate);
            final LinearLayout laRating = (LinearLayout) getView().findViewById(R.id.la_item_detail_rating);
            RatingBar rtbarRating = (RatingBar) getView().findViewById(R.id.rtbar_item_detail_rating);

            tvItemLikeCount.setText(Integer.toString(mFood.getLikedCount()));
            if (mFood.getBitmapPhotos() != null && mFood.getBitmapPhotos().length > 0) {
                ivItemPhoto.setImageBitmap(mFood.getBitmapPhotos()[0]);
                tvItemPhotoCount.setText(Integer.toString(mFood.getBitmapPhotos().length));
            }
            laCart.setTag(mFood);
            laCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food food = (Food) laCart.getTag();
                    Toast toast = new Toast(getContext());
                    toast.setView(mAddToCartView);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0);
                    toast.show();
                }
            });
            laPhotoList.setTag(mFood.getId());
            laPhotoList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) laPhotoList.getTag();
                }
            });
            laShare.setTag(mFood.getId());
            laShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int foodId = (int) laShare.getTag();
                }
            });
            laSpeaker.setTag(mFood);
            laSpeaker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food food = (Food) laSpeaker.getTag();
                    ((MainActivity) getActivity()).checkSpeechPackage();
                    TextToSpeech speech = ((MainActivity) getActivity()).getSpeech();
                    if (speech != null) {
                        speech.stop();
                        speech.speak(food.getTitle(), TextToSpeech.QUEUE_ADD, null);
                        speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                        String[] materials = food.getMaterials();
                        if (materials != null) {
                            speech.speak(getResources().getString(R.string.text_materials), TextToSpeech.QUEUE_ADD, null);
                            speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                            for (int i = 0; i < materials.length; i++) {
                                speech.speak(materials[i], TextToSpeech.QUEUE_ADD, null);
                                speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                        String[] cookingSteps = food.getCookingSteps();
                        if (cookingSteps != null) {
                            speech.speak(getResources().getString(R.string.text_cooking_steps), TextToSpeech.QUEUE_ADD, null);
                            speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                            for (int i = 0; i < cookingSteps.length; i++) {
                                speech.speak(cookingSteps[i], TextToSpeech.QUEUE_ADD, null);
                                speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                        if (food.getTip() != null && !food.getTip().equals("")) {
                            speech.speak(getResources().getString(R.string.text_tip), TextToSpeech.QUEUE_ADD, null);
                            speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                            speech.speak(food.getTip(), TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                }
            });
            btnItemLike.setTag(mFood.getId());
            btnItemLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnItemLike.setImageResource(R.drawable.like_circle_active);
                    int foodId = (int) btnItemLike.getTag();
                    ((MainActivity) getActivity()).doLikeFood(foodId);
                }
            });
            if (mFood.isLiked()) {
                btnItemLike.setImageResource(R.drawable.like_circle_active);
            }

            tvItemTitle.setText(mFood.getTitle());

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            tvItemPrice.setText(nf.format(mFood.getPrice()) + " " + getResources().getString(R.string.text_symbol_currency));
            tvItemCookedTime.setText(Integer.toString(mFood.getCookedTime()));
            tvItemServeCount.setText(Integer.toString(mFood.getServeCount()));
            tvItemCalories.setText(Integer.toString(mFood.getCalories()));

            if (mFood.getBimapCreatedUserPhoto() != null) {
                ivCreatedUserPhoto.setImageBitmap(mFood.getBimapCreatedUserPhoto());
            }
            tvCreatedUserName.setText(mFood.getCreatedUserDisplayName());
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            tvCreatedAt.setText(getResources().getString(R.string.text_dang_ngay) + " " + df.format(mFood.getCreatedAt()));
            tvDescription.setText(mFood.getDescription());
            btnFollow.setTag(mFood.getCreatedUser());
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = (String) btnFollow.getTag();
                }
            });

            btnDetailCook.setTag(mFood.getId());
            btnDetailCook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) btnComment.getTag();
                    showFoodDetailCookFragment(mFood);
                }
            });

            btnComment.setTag(mFood.getId());
            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) btnComment.getTag();
                    showUserCommentListFragment(id);
                }
            });

            btnRelate.setTag(mFood.getId());
            btnRelate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) btnRelate.getTag();
                    showRelatedFoodListFragment(id);
                }
            });

            rtbarRating.setRating((float) mFood.getRating());
            if (!mFood.isRated()) {
                laRating.setTag(mFood.getId());
                laRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = (int) laRating.getTag();
                        showFoodRatingDialogFragment(id);
                    }
                });
            }

            showFoodDetailCookFragment(mFood);

            ((MainActivity) getActivity()).getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getActionBarDrawerToggle().setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ((MainActivity) getActivity()).getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                    ((MainActivity) getActivity()).getActionBarDrawerToggle().setToolbarNavigationClickListener(null);
                    ((MainActivity) getActivity()).showFoodListFragment();
                    ((MainActivity) getActivity()).getSpeech().stop();
                }
            });
        }
    }

    public void setBtnExpandColor(int id) {
        Button btnDetailCook = (Button) getView().findViewById(R.id.btn_item_detail_cook);
        Button btnDetailComment = (Button) getView().findViewById(R.id.btn_item_detail_comment);
        Button btnDetailRelate = (Button) getView().findViewById(R.id.btn_item_detail_relate);
        btnDetailCook.setTextColor(ContextCompat.getColor(getContext(), R.color.color_black));
        btnDetailComment.setTextColor(ContextCompat.getColor(getContext(), R.color.color_black));
        btnDetailRelate.setTextColor(ContextCompat.getColor(getContext(), R.color.color_black));

        switch (id) {
            case R.id.btn_item_detail_cook: {
                btnDetailCook.setTextColor(ContextCompat.getColor(getContext(), R.color.color_primary));
                break;
            }
            case R.id.btn_item_detail_comment: {
                btnDetailComment.setTextColor(ContextCompat.getColor(getContext(), R.color.color_primary));
                break;
            }
            case R.id.btn_item_detail_relate: {
                btnDetailRelate.setTextColor(ContextCompat.getColor(getContext(), R.color.color_primary));
                break;
            }
        }
    }

    public void showFoodDetailCookFragment(Food food) {
        if (getActivity().findViewById(R.id.f_food_detail) != null) {
            setBtnExpandColor(R.id.btn_item_detail_cook);
            FoodDetailCookFragment fragment = new FoodDetailCookFragment();
            Bundle args = new Bundle();
            args.putInt("id", food.getId());
            args.putStringArray("materials", food.getMaterials());
            args.putStringArray("cooking_steps", food.getCookingSteps());
            args.putString("tip", food.getTip());
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.f_food_detail, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void showUserCommentListFragment(int id) {
        if (getActivity().findViewById(R.id.f_food_detail) != null) {
            setBtnExpandColor(R.id.btn_item_detail_comment);
            UserCommentListFragment fragment = new UserCommentListFragment();
            Bundle args = new Bundle();
            args.putInt("id", id);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.f_food_detail, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void showRelatedFoodListFragment(int id) {
        if (getActivity().findViewById(R.id.f_food_detail) != null) {
            setBtnExpandColor(R.id.btn_item_detail_relate);
            RelatedFoodListFragment fragment = new RelatedFoodListFragment();
            Bundle args = new Bundle();
            args.putInt("id", id);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.f_food_detail, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void showFoodRatingDialogFragment(int id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FoodRatingDialogFragment fragment = new FoodRatingDialogFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        fragment.show(fm, "food_rating_dialog");
    }
}