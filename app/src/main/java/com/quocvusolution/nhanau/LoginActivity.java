package com.quocvusolution.nhanau;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private CallbackManager mCallbackMgr;
    private LoginButton mBtnLogin;
    private TextView mTvLogin;
    private ProgressDialog mProgressDialog;
    UserAccount mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mUser = SharedPref.getCurrentUser(LoginActivity.this);
        if(mUser != null) {
            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
            setLoginIntentParam(homeIntent);
            startActivity(homeIntent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCallbackMgr =CallbackManager.Factory.create();
        mBtnLogin = (LoginButton)findViewById(R.id.btn_login_facebook_widget);
        mBtnLogin.setReadPermissions("public_profile", "email", "user_friends");
        mTvLogin = (TextView) findViewById(R.id.btn_login_facebook);
        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();
                mBtnLogin.performClick();
                mBtnLogin.setPressed(true);
                mBtnLogin.invalidate();
                mBtnLogin.registerCallback(mCallbackMgr, mCallBack);
                mBtnLogin.setPressed(false);
                mBtnLogin.invalidate();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            mCallbackMgr.onActivityResult(requestCode, resultCode, data);
        }catch (Exception e){
        }
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            final String accessToken = loginResult.getAccessToken().getToken();
            mProgressDialog.dismiss();
            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                            Log.e("response: ", response + "");
                            try {
                                mUser = new UserAccount();
                                mUser.setToken(accessToken);
                                mUser.setUsername(object.getString("id"));
                                mUser.setEmail(object.getString("email"));
                                mUser.setName(object.getString("name"));
                                mUser.setGender(object.getString("gender"));
                            }catch (Exception e){
                            }
                            try {
                                SharedPref.setCurrentUser(mUser,LoginActivity.this);
                            }catch (Exception e){
                            }
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            setLoginIntentParam(intent);

                            startActivity(intent);
                            finish();
                        }

                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            mProgressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            mProgressDialog.dismiss();
        }
    };

    public void setLoginIntentParam(Intent myIntent) {
        myIntent.putExtra("username", mUser.getUsername());
        myIntent.putExtra("name", mUser.getName());
        myIntent.putExtra("email", mUser.getEmail());
        myIntent.putExtra("gender", mUser.getGender());
        myIntent.putExtra("token", mUser.getToken());
    }
}
