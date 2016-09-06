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
                ((MainActivity) getActivity()).checkSpeechPackage();
                TextToSpeech speech = ((MainActivity) getActivity()).getSpeech();
                speech.stop();
                String[] materials = (String[]) btnMaterialsSpeaker.getTag();
                if (materials != null) {
                    speech.speak(getResources().getString(R.string.text_materials), TextToSpeech.QUEUE_ADD, null);
                    speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    for (int i = 0; i < materials.length; i++) {
                        speech.speak(materials[i], TextToSpeech.QUEUE_ADD, null);
                        speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    }
                }

            }
        });

        btnCookingStepsSpeaker.setTag(mCookingStemps);
        btnCookingStepsSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).checkSpeechPackage();
                TextToSpeech speech = ((MainActivity) getActivity()).getSpeech();
                speech.stop();
                String[] cookingSteps = (String[]) btnCookingStepsSpeaker.getTag();
                if (cookingSteps != null) {
                    speech.speak(getResources().getString(R.string.text_cooking_steps), TextToSpeech.QUEUE_ADD, null);
                    speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    for (int i = 0; i < cookingSteps.length; i++) {
                        speech.speak(cookingSteps[i], TextToSpeech.QUEUE_ADD, null);
                        speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    }
                }
            }
        });

        btnTipSpeaker.setTag(mTip);
        btnTipSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).checkSpeechPackage();
                TextToSpeech speech = ((MainActivity) getActivity()).getSpeech();
                speech.stop();
                String tip = (String) btnTipSpeaker.getTag();
                if (tip != null && !tip.equals("")) {
                    speech.speak(getResources().getString(R.string.text_tip), TextToSpeech.QUEUE_ADD, null);
                    speech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    speech.speak(tip, TextToSpeech.QUEUE_ADD, null);
                }
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
}