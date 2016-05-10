package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.fitta.lightsoo.fitta.Data.User;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.Manager.PropertyManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.LoginAPI;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 쉐어드프리퍼런스를 통해서 로그인의 로큰값들이 있으면, /login/?token값을 넘겨주고
 * 성공시 MianActivity를 호출해서 화면이동하자.
 * 로큰값이 없는경우에는 loginActivity를 호출하자
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    Handler mHandler = new Handler(Looper.getMainLooper());
    RelativeLayout background_splash ;
    String loginType;
    String userLoginId;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    LoginManager mLoginManager = LoginManager.getInstance();
    AccessTokenTracker mTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        //이거를 로그인하고 나서 넣자
        PropertyManager.getInstance().setUserAvatar(R.drawable.avatar110af);


//        Intent intent = new Intent(this, UserActivity.class);
//        startActivity(intent);
        goMainActivity();
//        goLoginActivity();
        //완성 했는데 테스트할때는 바로 메인으로 가게끔해서 하자 그래야 디버그가 편해
//        doRealStart();
    }

    public void init(){

        background_splash = (RelativeLayout)findViewById(R.id.background_splash);
        Glide.with(getApplicationContext())
                .load(R.drawable.background_splash)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                background_splash.setBackground(drawable);
            }
        });
    }

    private void doRealStart(){
        loginType = PropertyManager.getInstance().getLoginType();
        userLoginId = PropertyManager.getInstance().getUserLoginId();
        //로그인 한적이 없을 경우 혹은 로그아웃했을 경우 → 로그인 액티비티로 이동
        if(TextUtils.isEmpty(loginType)){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "로그인 한적이 없어서 로그인페이지로 이동");
                    goLoginActivity();
                }
            }, 2500);
        }else {
            switch (loginType){
                case PropertyManager.LOGIN_TYPE_FACEBOOK:
                    //로그인 id가 존재할경우
                    if(!TextUtils.isEmpty(userLoginId)){
                        Log.d(TAG, "id가 있는경우 :!TextUtils.isEmpty(userLoginId))");
                        Log.d(TAG, "userLoginId : " + userLoginId );

                        loginType = PropertyManager.getInstance().getLoginType();
                        User user = new User(userLoginId, loginType);

                        Call call = NetworkManager.getInstance().getAPI(LoginAPI.class).login(user);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Response response, Retrofit retrofit) {
                                if (response.isSuccess()) {//이전에 가입되었던 사람이라면 OK,
                                    Toast.makeText(SplashActivity.this, "페이스북 연동 로그인으로 입장 합니다.", Toast.LENGTH_SHORT).show();
                                    goMainActivity();
                                } else {
                                    //아니라면 not registered
                                    mLoginManager.logOut();
                                    goLoginActivity();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(SplashActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                                goLoginActivity();
                            }
                        });
                        mLoginManager.logInWithReadPermissions(this, null);
                    }else{//id가 없을경우에 로그인 페이지로 이동!!!
                        Log.d(TAG, "id가 없는경우 : !TextUtils.isEmpty(userLoginId))");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SplashActivity.this, "Welcome! please log-in!", Toast.LENGTH_SHORT).show();

                                goLoginActivity();
                            }
                        }, 1500);
                    }
                    break;
                case PropertyManager.LOGIN_TYPE_KAKAO:
                    Log.d(TAG, "case PropertyManager.LOGIN_TYPE_KAKAO:");
                    //로그인 id가 존재할 경우
                    if(!TextUtils.isEmpty(userLoginId)){

                        Log.d(TAG, "id가 있는경우 :!TextUtils.isEmpty(userLoginId))");
                        Log.d(TAG, "userLoginId : " + userLoginId );

                        loginType = PropertyManager.getInstance().getLoginType();
                        User user = new User(userLoginId, loginType);

                        Call call = NetworkManager.getInstance().getAPI(LoginAPI.class).login(user);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Response response, Retrofit retrofit) {
                                if (response.isSuccess()) {//이전에 가입되었던 사람이라면 OK,

                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SplashActivity.this, "카카오톡 연동 로그인으로 입장 합니다.", Toast.LENGTH_SHORT).show();
                                            goMainActivity();
                                        }
                                    }, 2000);
                                } else {
                                    //아니라면 not registered
                                    UserManagement.requestLogout(new LogoutResponseCallback() {
                                        @Override
                                        public void onCompleteLogout() {
                                            //기존에 카카오앱에 로그인 되어있던 id를 로그아웃한다.
                                            goLoginActivity();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(SplashActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                                goLoginActivity();
                            }
                        });
                    }else{
                        Log.d(TAG, "id가 없는경우 : !TextUtils.isEmpty(userLoginId))");
                        //페북 로그인 했는데 일전에 레몬클립에서 페북으로 로그인한 id와 다를 경우
                        //즉, 이앱으로 페북로그인했다가 다른 페북id로 페북 앱을 로그인 했을 경우
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SplashActivity.this, "Welcome! please log-in!", Toast.LENGTH_SHORT).show();
                                UserManagement.requestLogout(new LogoutResponseCallback() {
                                    @Override
                                    public void onCompleteLogout() {
                                        goLoginActivity();
                                    }
                                });
                            }
                        }, 1500);
                    }
                    break;
            }
        }
    }

    private void goMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void goLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
