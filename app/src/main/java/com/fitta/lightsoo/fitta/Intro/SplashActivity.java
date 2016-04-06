package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * 쉐어드프리퍼런스를 통해서 로그인의 로큰값들이 있으면, /login/?token값을 넘겨주고
 * 성공시 MianActivity를 호출해서 화면이동하자.
 * 로큰값이 없는경우에는 loginActivity를 호출하자
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goMain();
    }

    private void goMain(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
