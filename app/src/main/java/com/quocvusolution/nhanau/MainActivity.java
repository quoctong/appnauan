package com.quocvusolution.nhanau;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.quocvusolution.utility.ImageUtility;

import org.json.JSONObject;

import java.io.IOException;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String LOGIN_URL = "/UserAccount/Login";
    public static final String PHOTO_PATH = "/Public/Photo";
    public static final String FOOD_LIST_URL = "/Food/FoodList";
    public static final String FOOD_DETAIL_URL = "/Food/FoodDetail";
    public static final String RELATED_FOOD_LIST_URL = "/Food/RelatedFoodList";
    public static final String USER_COMMENT_LIST_URL = "/UserComment/UserCommentList";

    public AppSQLiteOpenHelper getAppSQLiteOpenHelper() {
        return mAppSQLiteOpenHelper;
    }

    public void setAppSQLiteOpenHelper(AppSQLiteOpenHelper appSQLiteOpenHelper) {
        mAppSQLiteOpenHelper = appSQLiteOpenHelper;
    }

    public FoodStore getFoodStore() {
        return mFoodStore;
    }

    public void setFoodStore(FoodStore foodStore) {
        mFoodStore = foodStore;
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mDrawerToggle;
    }

    public TextToSpeech getSpeech() {
        return mSpeech;
    }

    private AppSQLiteOpenHelper mAppSQLiteOpenHelper;

    private FoodStore mFoodStore;

    private ActionBarDrawerToggle mDrawerToggle;

    private TextToSpeech mSpeech;

    private UserAccount mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CookieHandler.setDefault(new CookieManager());
        mAppSQLiteOpenHelper = new AppSQLiteOpenHelper(this);
        mFoodStore = new FoodStore(this, mAppSQLiteOpenHelper.getWritableDatabase());

        mUser = SharedPref.getCurrentUser(MainActivity.this);
        if (mUser == null) {
            mUser =  new UserAccount();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().setGroupVisible(R.id.nav_main_group, false);
        navigationView.getMenu().performIdentifierAction(R.id.menu_show_food_list, 0);

        registerRadioButtonPage();
        doLogin();
        showFoodListFragment();

        final String googleTtsPackage = "com.google.android.textToSpeech";
        mSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    mSpeech.setEngineByPackageName(googleTtsPackage);
                    mSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onDone(String utteranceId) {
                        }
                        @Override
                        public void onError(String utteranceId) {
                        }
                        @Override
                        public void onStart(String utteranceId) {
                        }
                    });
                }
            }
        });
        mSpeech.setEngineByPackageName(googleTtsPackage);
        mSpeech.setSpeechRate((float)0.8);
    }

    public String getAbsoluteUrlPath(String url) {
        return getResources().getString(R.string.server_address) + url;
    }

    public String getStorageDirName() {
        return Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getPackageName()
                + "/Files";
    }

    public void showLogin(View v) throws IOException {
        showLoginActivity();
    }

    public void showLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void doLogin() {
        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        if (username != null && !username.equals("")) {
            mUser.setUsername(myIntent.getStringExtra("username"));
            mUser.setEmail(myIntent.getStringExtra("email"));
            mUser.setName(myIntent.getStringExtra("name"));
            mUser.setGender(myIntent.getStringExtra("gender"));
            mUser.setToken(myIntent.getStringExtra("token"));
        }
        if (mUser.getUsername() != null && !mUser.getUsername().equals("")) {
            excuteLoginTask();
        }
    }

    public void registerRadioButtonPage() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_show_views);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setTitle(getResources().getString(R.string.app_name));
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.btn_show_food_list:
                        showFoodListFragment();
                        break;
                }
            }
        });
    }

    private MenuItem mPrevSelectedMenu;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mPrevSelectedMenu != null) {
            mPrevSelectedMenu.setChecked(false);
        }
        item.setChecked(true);
        mPrevSelectedMenu = item;
        setTitle(getResources().getString(R.string.app_name));

        int id = item.getItemId();
        if (id == R.id.menu_show_food_list) {
            showFoodListFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        showFoodListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showMenuPage() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_menu_page);
        layout.setVisibility(View.VISIBLE);
    }

    public void hideMenuPage() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_menu_page);
        layout.setVisibility(View.INVISIBLE);
    }

    public void checkMenuPage(int pageId) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_show_views);
        radioGroup.check(pageId);
    }

    public void doLogout(MenuItem menuItem) {
        logOut();
    }

    public void logOut() {
        SharedPref.clearCurrentUser(MainActivity.this);
        LoginManager.getInstance().logOut();

        Intent i= new Intent(MainActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void showFoodListFragment() {
        if (findViewById(R.id.fc) != null) {
            showMenuPage();
            checkMenuPage(R.id.btn_show_food_list);
            FoodListFragment fragment = new FoodListFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fc, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void showFoodDetailFragment(int foodId) {
        if (findViewById(R.id.fc) != null) {
            hideMenuPage();
            FoodDetailFragment fragment = new FoodDetailFragment();
            Bundle args = new Bundle();
            args.putInt("id", foodId);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fc, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void login(View v) throws IOException {
        EditText etUsername = (EditText) findViewById(R.id.et_username);
        EditText etPassword = (EditText) findViewById(R.id.et_password);
        mUser.setUsername(etUsername.getText().toString());
        mUser.setPassword(etPassword.getText().toString());

        excuteLoginTask();
    }

    public void excuteLoginTask() {
        String loginUrl = getAbsoluteUrlPath(LOGIN_URL);
        LoginTask loginTask = new LoginTask();
        loginTask.execute(loginUrl);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void setUserInfoView() {
        if (mUser != null) {
            ImageView ivPhoto = (ImageView) findViewById(R.id.iv_user_account_photo_login);
            TextView tvName = (TextView) findViewById(R.id.tv_user_account_name_login);
            TextView tvPhone = (TextView) findViewById(R.id.tv_user_account_phone_login);
            ivPhoto.setImageBitmap(mUser.getBitmapPhoto());
            tvName.setText(mUser.getName());
            tvPhone.setText(mUser.getPhone());
        }
    }

    public void showNavigationMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().setGroupVisible(R.id.nav_main_group, true);
    }

    public String loginToServer(String url, RequestQueue requestQueue) {
        String result = "";
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", mUser.getUsername());
                params.put("password", mUser.getPassword());
                params.put("email", mUser.getEmail());
                params.put("name", mUser.getName());
                params.put("gender", mUser.getGender());
                params.put("access_token", mUser.getToken());
                return params;
            }
        };
        requestQueue.add(request);
        try {
            String response = future.get();
            JSONObject jObject = new JSONObject(response);

            UserAccount item = new UserAccount();
            item.setJData(jObject.getJSONObject("UserAccount"));
            mUser = item;

            URL imageURL = null;
            Bitmap bm = null;
            try {
                imageURL = new URL(mUser.getPhoto());
            } catch (MalformedURLException e) {
            }
            try {
                bm  = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            } catch (IOException e) {
            }
            if (bm != null) {
                item.setBitmapPhoto(ImageUtility.getRoundedCornerBitmap(Bitmap.createScaledBitmap(bm, 75, 75, false)));
            }
        } catch (Exception e) {
        }
        return result;
    }

    public class LoginTask extends AsyncTask<String, Void, Object> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                loginToServer(url[0], requestQueue);
            } catch (Exception e) {
            }
            return data;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            try {
                if (mUser != null) {
                    setUserInfoView();
                    showNavigationMenu();
                }
            } catch (Exception e) {
            }
        }
    }
}
