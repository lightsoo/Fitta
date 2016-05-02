package com.fitta.lightsoo.fitta.Intro;

import android.content.Context;
import android.util.AttributeSet;

import com.fitta.lightsoo.fitta.R;
import com.kakao.usermgmt.LoginButton;

public class KakaoLoginButton extends LoginButton{

    public KakaoLoginButton(Context context) {
        super(context);
    }

    public KakaoLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KakaoLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        여기서 이름을 내가 하고싶은걸로 하려니깐 제대로 안되더라...그래서 이름을 같게했다
        inflate(getContext(), R.layout.kakao_login_layout, this);


    }
}
