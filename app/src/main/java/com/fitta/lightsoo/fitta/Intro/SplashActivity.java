package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;


/**
 * 쉐어드프리퍼런스를 통해서 로그인의 로큰값들이 있으면, /login/?token값을 넘겨주고
 * 성공시 MianActivity를 호출해서 화면이동하자.
 * 로큰값이 없는경우에는 loginActivity를 호출하자
 */

public class SplashActivity extends AppCompatActivity {
    Handler mHandler = new Handler(Looper.getMainLooper());

    private String id;
    private String flag;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    LoginManager mLoginManager = LoginManager.getInstance();
    AccessTokenTracker mTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        goLoginActivity();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void goMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void goLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTokenTracker != null){
            mTokenTracker.stopTracking();;
            mTokenTracker = null; //이거안해도됨?
        }
    }
}
