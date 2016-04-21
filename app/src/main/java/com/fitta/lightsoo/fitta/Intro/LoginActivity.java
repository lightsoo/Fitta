package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.fitta.lightsoo.fitta.Handler.BackPressCloseHandler;
import com.fitta.lightsoo.fitta.R;

public class LoginActivity extends AppCompatActivity {

   private ImageButton btn_fb, btn_kakao;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backPressCloseHandler = new BackPressCloseHandler(this);
        init();
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                //쉐어드 하면은 로그인을 안오고 바로 홈으로 갈꺼니깐 안해야해
//               finish();
            }
        });



    }

    public void init(){
        btn_fb = (ImageButton)findViewById(R.id.btn_fb);
        btn_kakao = (ImageButton)findViewById(R.id.btn_kakao);


    }

    @Override
    public void onBackPressed() {backPressCloseHandler.onBackPressed();}
}
