package com.fitta.lightsoo.fitta.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.fitta.lightsoo.fitta.Intro.LoginActivity;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.Manager.PropertyManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;
import com.fitta.lightsoo.fitta.Setting.AgreementActivity;
import com.fitta.lightsoo.fitta.Setting.GuideActivity;
import com.fitta.lightsoo.fitta.Setting.IntroduceActivity;
import com.fitta.lightsoo.fitta.Setting.UserActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SettingFragment extends Fragment {

    private static final String TAG = "SettingFragment";
    String loginType;
    LoginManager mLoginManager = LoginManager.getInstance();
    RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout4, relativeLayout5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);

        //사용자 정보
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
            }
        });

        //이용가이드
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GuideActivity.class);
                startActivity(intent);
            }
        });

        //이용약관
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AgreementActivity.class);
                startActivity(intent);
            }
        });

        //회사소개
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IntroduceActivity.class);
                startActivity(intent);
            }
        });


        //로그아웃
        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout5.isEnabled();
                switch (loginType){
                    case PropertyManager.LOGIN_TYPE_FACEBOOK:
                        Call call = NetworkManager.getInstance().getAPI(FittaAPI.class).logout(PropertyManager.getInstance().getUserLoginToken());
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Response response, Retrofit retrofit) {
                                mLoginManager.logOut();
                                PropertyManager.getInstance().deleteUserLoginId();
                                PropertyManager.getInstance().deleteLoginType();
                                Toast.makeText(getActivity(), "페이스북 로그아웃 했습니다.", Toast.LENGTH_SHORT).show();
                                goLoginActivity();
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                        break;
                    case PropertyManager.LOGIN_TYPE_KAKAO :
                        Log.d(TAG, "PropertyManager.LOGIN_TYPE_KAKAO");
                        Call call1 = NetworkManager.getInstance().getAPI(FittaAPI.class).logout(PropertyManager.getInstance().getUserLoginToken());
                        call1.enqueue(new Callback() {
                            @Override
                            public void onResponse(Response response, Retrofit retrofit) {
                                PropertyManager.getInstance().deleteUserLoginId();
                                PropertyManager.getInstance().deleteLoginType();
                                Toast.makeText(getActivity(), "카카오톡 로그아웃 했습니다.", Toast.LENGTH_SHORT).show();
                                goLoginActivity();
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                        break;

                    default:
                        goLoginActivity();
                        break;
                }
            }
        });
        return view;
    }

    public void init(View view){
        loginType = PropertyManager.getInstance().getLoginType();
        relativeLayout1 =(RelativeLayout)view.findViewById(R.id.relativeLayout1);
        relativeLayout2 =(RelativeLayout)view.findViewById(R.id.relativeLayout2);
        relativeLayout3 =(RelativeLayout)view.findViewById(R.id.relativeLayout3);
        relativeLayout4 =(RelativeLayout)view.findViewById(R.id.relativeLayout4);
        relativeLayout5 =(RelativeLayout)view.findViewById(R.id.relativeLayout5);
    }

    private void goLoginActivity(){

        startActivity(new Intent(getActivity(), LoginActivity.class));
       getActivity().finish();
    }

}
