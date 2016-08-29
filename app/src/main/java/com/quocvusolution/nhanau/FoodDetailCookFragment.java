package com.quocvusolution.nhanau;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.quocvusolution.utility.AndroidUtility;

public class FoodDetailCookFragment extends Fragment {
    private String mSpeechPackageName = "com.google.android.tts";
    private int mFoodId = 0;
    private String[] mMaterials;
    private String[] mCookingStemps;
    private String mTip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFoodId = getArguments().getInt("id");
        mMaterials = getArguments().getStringArray("materials");
        mCookingStemps = getArguments().getStringArray("cooking_steps");
        mTip = getArguments().getString("tip");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.food_detail_cook, container, false);

        TextView tvItemTip = (TextView) view.findViewById(R.id.tv_item_detail_cook_tip);
        final Button btnMaterialsSpeaker = (Button) view.findViewById(R.id.btn_item_detail_cook_materials_speaker);
        final Button btnCookingStepsSpeaker = (Button) view.findViewById(R.id.btn_item_detail_cook_cooking_step_speaker);
        final Button btnTipSpeaker = (Button) view.findViewById(R.id.btn_item_detail_cook_tip_speaker);

        tvItemTip.setText(mTip);
        btnMaterialsSpeaker.setTag(mMaterials);
        btnMaterialsSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSpeechPackage();
                String[] materials = (String[]) btnMaterialsSpeaker.getTag();
                ((MainActivity) getActivity()).getSpeech().stop();
                for (int i = 0; i < materials.length; i++) {
                    ((MainActivity) getActivity()).getSpeech().speak(materials[i], TextToSpeech.QUEUE_ADD, null);
                    ((MainActivity) getActivity()).getSpeech().playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        btnCookingStepsSpeaker.setTag(mCookingStemps);
        btnCookingStepsSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSpeechPackage();
                String[] cookingSteps = (String[]) btnCookingStepsSpeaker.getTag();
                ((MainActivity) getActivity()).getSpeech().stop();
                for (int i = 0; i < cookingSteps.length; i++) {
                    ((MainActivity) getActivity()).getSpeech().speak(cookingSteps[i], TextToSpeech.QUEUE_ADD, null);
                    ((MainActivity) getActivity()).getSpeech().playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        btnTipSpeaker.setTag(mTip);
        btnTipSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSpeechPackage();
                String tip = (String) btnTipSpeaker.getTag();
                ((MainActivity) getActivity()).getSpeech().stop();
                ((MainActivity) getActivity()).getSpeech().speak(tip, TextToSpeech.QUEUE_ADD, null);
            }
        });

        ListView lvMaterials = (ListView) view.findViewById(R.id.lv_item_detail_cook_materials);
        ListView lvCookingSteps = (ListView) view.findViewById(R.id.lv_item_detail_cook_cooking_steps);

        lvMaterials.setAdapter(new FoodDetailCookMaterialAdapter(getActivity(), mMaterials));
        lvCookingSteps.setAdapter(new FoodDetailCookStepAdapter(getActivity(), mCookingStemps));

        AndroidUtility.setDynamicHeight(lvMaterials);
        AndroidUtility.setDynamicHeight(lvCookingSteps);

        return view;
    }

    public void checkSpeechPackage() {
        int minVersion = 210309111;
        if (!AndroidUtility.isAppInstalled(getContext(), mSpeechPackageName)) {
            showInstallSpeechDialogFragment();
        } else {
            int code = AndroidUtility.getPackageVersionCode(getContext(), mSpeechPackageName);
            if (code < minVersion) {
                showInstallSpeechDialogFragment();
            }
        }
    }

    public void showInstallSpeechDialogFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        InstallSpeechDialogFragment fragment = new InstallSpeechDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.show(fm, "install_speech_dialog");
    }
}