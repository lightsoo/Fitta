package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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

    //replace를 써서 화면 이동하자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Fragment info1 = new SignupInfo1Fragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.container, info1, info1_TAG);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, info1).commit();

//        ft.commit();
//        current = info1;
    }
}
