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

import com.quocvusolution.utility.AndroidUtility;

public class InstallSpeechDialogFragment extends DialogFragment {
    private String mSpeechPackageName = "com.google.android.tts";

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
        View view = inflater.inflate(R.layout.install_speech_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Button btnInstallSpeech = (Button) view.findViewById(R.id.btn_install_speech);
        btnInstallSpeech.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtility.openAppRating(getContext(), mSpeechPackageName);
                getDialog().cancel();
            }
        });

        Button btnCancelInstall = (Button) view.findViewById(R.id.btn_cancel_install);
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