package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;

/**
 * Created by LG on 2016-04-21.
 */
public class SignupResultFragment extends Fragment{

    private static final String TAG = "SignupResultFragment";
    private Button btn_start;
    private ImageView iv_avatar;
    private String url="";
    private String age="", height="", weight="";
    //가슴둘레(컵), 허리둘레(inch)
    private String top="", bottom="";

    private RelativeLayout layoutPlace;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_result, container, false);
        init(view);
        setInfo();
        //아바타설정
        Glide.with(getContext())
                .load(url)
//                .load(R.drawable.body100cf)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_avatar);



        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //종료하고 메인액티비티로 이동!
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    public void init(View view){
        btn_start = (Button)view.findViewById(R.id.btn_start2);
        iv_avatar = (ImageView)view.findViewById(R.id.iv_avatar);
        //텍스트뷰를 초기화해줘서 화면에 출력해줘야된다.

        layoutPlace = (RelativeLayout)view.findViewById(R.id.background);
        Glide.with(getContext())
                .load(R.drawable.background)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                layoutPlace.setBackground(drawable);
            }
        });
    }

    //signUpInfo2,3에서 서버로 데이터를 전송한다음 아바타 url을 받아서 쓴다!
    public void setInfo(){
//        age = getArguments().getString("age");
//        height = getArguments().getString("height");
//        weight = getArguments().getString("weight");
//        top = getArguments().getString("top");
//        bottom = getArguments().getString("bottom");

        url = getArguments().getString("url");
//        Log.d(TAG,"age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom + ", url : " + url);

    }
}
