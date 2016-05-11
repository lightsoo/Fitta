package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fitta.lightsoo.fitta.Data.Test;
import com.fitta.lightsoo.fitta.Data.User;
import com.fitta.lightsoo.fitta.Handler.BackPressCloseHandler;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.Manager.PropertyManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.LoginAPI;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    //server response code
    private static final int CODE_ID_PASS_INCORRECT = 531;
    private static final int CODE_NOT_FOUND = 404;


    RelativeLayout background_login ;
//   private ImageButton btn_kakao;
    LoginButton btn_kakao;

    //for facebook
    CallbackManager callbackManager;
    ImageButton btn_fb;
    LoginManager mLoginManager;
    AccessTokenTracker tracker;

    String userLoginToken;
    User user;
    Test test;

    private BackPressCloseHandler backPressCloseHandler;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backPressCloseHandler = new BackPressCloseHandler(this);
        init();
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignupActivity();
            }
        });

//        btn_kakao.setBackground(R.drawable.kakao_login);
//        btn_kakao.onAtt

        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginOrLogout();
                /*Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                //쉐어드 하면은 로그인을 안오고 바로 홈으로 갈꺼니깐 안해야해
//               finish();*/
            }
        });
        //이렇게 생성해주기만하면 트래킹이 작동한다. 그래서 액티비티 종료되면 트랙킹도 종료해야한다.
        //로그인 매니저에서 콜밷 등록을 해서 작업이 종료되면 호출된다!!!
        tracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d(TAG, "트랙커 토큰 체인지!");
                final AccessToken token = AccessToken.getCurrentAccessToken();


//                Log.d(TAG, "token : " + token.getToken());
                if(token != null){
//                    userLoginId = token.getUserId();
                    userLoginToken = token.getToken();
                    Log.d(TAG, "userLoginToken : " + userLoginToken);
                    user = new User(userLoginToken, PropertyManager.LOGIN_TYPE_FACEBOOK);
                    test = new Test("테스트이름", "1", userLoginToken );

                    Call call = NetworkManager.getInstance().getAPI(LoginAPI.class).login(user);
//                    Call call = NetworkManager.getInstance().getAPI(LoginAPI.class).login(test);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Response response, Retrofit retrofit) {
                            if(response.isSuccess()){
                                Log.d(TAG, "페이스북 Login success");
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                PropertyManager.getInstance().setUserLoginToken(userLoginToken);
                                PropertyManager.getInstance().setLoginType(PropertyManager.LOGIN_TYPE_FACEBOOK);

                                goSignupActivity();
//                                goMainActivity();
                            } else {
                                if(response.code() == CODE_ID_PASS_INCORRECT){
                                    Toast.makeText(LoginActivity.this, "ID or Password incorrect", Toast.LENGTH_SHORT).show();
                                }else if(response.code() == CODE_NOT_FOUND){
                                    Toast.makeText(LoginActivity.this, "404 NOT FOUND", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Server Failure.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d(TAG, t.toString());
                            Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        Session.getCurrentSession().addCallback(new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                Toast.makeText(LoginActivity.this, "accessToken : " + Session.getCurrentSession().getAccessToken(), Toast.LENGTH_SHORT).show();
                userLoginToken = Session.getCurrentSession().getAccessToken();
                Log.d(TAG, "userLoginToken : " + userLoginToken);
                UserManagement.requestMe(new MeResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onNotSignedUp() {

                    }

                    @Override
                    public void onSuccess(UserProfile result) {
                        Toast.makeText(LoginActivity.this, "User : " + result.getId(), Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "User : " + result.getId());
//                        userLoginId = ""+ result.getId();

                        user = new User(userLoginToken, PropertyManager.LOGIN_TYPE_KAKAO);
                        test = new Test("테스트이름", "2", userLoginToken );

                        Call call = NetworkManager.getInstance().getAPI(LoginAPI.class).login(user);
//                        Call call = NetworkManager.getInstance().getAPI(LoginAPI.class).login(test);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Response response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    Log.d(TAG, "카카오톡 Login success");
                                    Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                    PropertyManager.getInstance().setUserLoginToken(userLoginToken);
                                    PropertyManager.getInstance().setLoginType(PropertyManager.LOGIN_TYPE_KAKAO);

                                    goSignupActivity();
//                                    goMainActivity();
                                } else {
                                    if (response.code() == CODE_ID_PASS_INCORRECT) {
                                        Toast.makeText(LoginActivity.this, "ID or Password incorrect", Toast.LENGTH_SHORT).show();
                                    }else if(response.code() == CODE_NOT_FOUND){
                                        Toast.makeText(LoginActivity.this, "404 NOT FOUND", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(LoginActivity.this, "Server Failure.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(LoginActivity.this, "실패 ", Toast.LENGTH_SHORT).show();
                if (exception != null) {
                    Logger.e(exception);
                }
            }
        });
    }

    public void init(){
        btn_fb = (ImageButton)findViewById(R.id.btn_fb);
        btn_fb.setEnabled(true);
        background_login = (RelativeLayout)findViewById(R.id.background_login);
        Glide.with(getApplicationContext())
                .load(R.drawable.background_signup)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                background_login.setBackground(drawable);
            }
        });

//        Glide.with(getApplicationContext())
//                .load(R.drawable.background_main)
//                .asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Drawable drawable = new BitmapDrawable(resource);
//                background_login.setBackground(drawable);
//            }
//        });

//        btn_kakao = (ImageButton)findViewById(R.id.btn_kakao);

        mLoginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onBackPressed() {backPressCloseHandler.onBackPressed();}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //트랙킹 종료
        tracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        //이걸 반드시해줘야한다. 얘가 있어야 콜백이 호출된다. 액티비티가 받은 결과를 callbackmanager로 토스!!!
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //로그인 성공하면 메인으로 이동하고 이전액티비티는 종료한다.
    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    //로그인 성공하면 메인으로 이동하고 이전액티비티는 종료한다.
    private void goSignupActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        tracker.stopTracking();
        finish();
    }

//for facebook
    private void loginOrLogout(){
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) {
            btn_fb.setEnabled(false);
            mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

            }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
            mLoginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
            mLoginManager.setDefaultAudience(DefaultAudience.FRIENDS);
            mLoginManager.logInWithReadPermissions(this, null);
        } else {
            mLoginManager.logOut();
        }
    }
}