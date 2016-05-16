package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.fitta.lightsoo.fitta.R;

/**
 * 회원가입 프레그먼트 관리
 * info : 1 -> 3(여자)
 * info : 1 -> 2(남자)
 *
 *
 * 1번화면을 먼저보여주고
 * setARGUMENT()를 통해서 번들로 데이터를 넘겨준다!!!
 */

public class SignupActivity extends AppCompatActivity {

    //여기에서 적용하면 프레그먼트들에서 각각 배경을 설정할 필요가 없다.
    RelativeLayout background_signup ;

    //replace를 써서 화면 이동하자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /*background_signup = (RelativeLayout)findViewById(R.id.background_signup);
        Glide.with(getApplicationContext())
                .load(R.drawable.background_signup)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                background_signup.setBackground(drawable);
            }
        });*/


        Fragment info0 = new SignupInfo0Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, info0).commit();


//        Fragment info1 = new SignupInfo1Fragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.container, info1).commit();

//        ft.commit();
//        current = info1;
    }
}
