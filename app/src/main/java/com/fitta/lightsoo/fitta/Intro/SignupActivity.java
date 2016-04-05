package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fitta.lightsoo.fitta.R;

/**
 * 회원가입 프레그먼트 관리
 * info : 1 -> 2(남자)
 * info : 1 -> 3(여자)
 */


public class SignupActivity extends AppCompatActivity {


    //tag를 통해서 현재 프레그먼트 구별 한다.
    private static final String Info1_TAG = "info1";
    private static final String Info2_TAG = "info2";
    private static final String Info3_TAG = "info3";

    Fragment info1, info2, info3, current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}
