package com.fitta.lightsoo.fitta.Intro;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


    //tag를 통해서 현재 프레그먼트 구별 한다.
    private static final String info1_TAG = "info1";
    private static final String info2_TAG = "info2";
    private static final String info3_TAG = "info3";


    Fragment info1, info2, info3, current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        info1 = new SignupInfo1Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, info1, info1_TAG);
        ft.commit();
        current = info1;

    }





}
