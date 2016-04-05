package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.fitta.lightsoo.fitta.R;

public class LoginActivity extends AppCompatActivity {

   private ImageButton btn_fb, btn_naver, btn_kakao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            }
        });


    }

    public void init(){
        btn_fb = (ImageButton)findViewById(R.id.btn_fb);
        btn_kakao = (ImageButton)findViewById(R.id.btn_kakao);
        btn_naver=(ImageButton)findViewById(R.id.btn_naver);


    }


}
