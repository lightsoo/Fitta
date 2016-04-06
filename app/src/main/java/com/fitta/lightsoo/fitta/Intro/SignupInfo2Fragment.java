package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitta.lightsoo.fitta.R;

//여자 상세정보입력
public class SignupInfo2Fragment extends Fragment {

    private static final String TAG = "SignupInfo2Fragment";



    private String age="", height="", weight="";
    //가슴둘레(컵), 허리둘레(inch)
    private String top="", bottom="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info2, container, false);
        return view;
    }
}
