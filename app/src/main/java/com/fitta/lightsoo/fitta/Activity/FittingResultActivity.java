package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.R;

public class FittingResultActivity extends AppCompatActivity {
    private ImageView avatar, clothes;
    private static final int REQ_CAMERA_IMAGE = 123;

    private static int flag=0;
    private static final String TAG = "FittingResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting_result);
        init();
        Intent intent = new Intent(getIntent());
        //나중에는 서버에서 이미지 처리하기위해 받으면 액티비티 생성후 데이터를 보내서
        //피드백과 처리된 이미지를 받아서 화면에 적용한다.
        String clothesUrl = intent.getExtras().getString("clothesUrl");
        String clothesSize = intent.getExtras().getString("clothesSize");
        String clohthesUnit = intent.getExtras().getString("clothesUnit");
        Log.d(TAG, clothesUrl);
        setClothes(clothesUrl);
    }

    public void init(){
        clothes = (ImageView)findViewById(R.id.clothes);
        avatar = (ImageView)findViewById(R.id.avatar);
        Glide.with(getApplicationContext())
                .load(R.drawable.body100cf)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(avatar);
    }


    public void setClothes(String clothesUrl){
        Glide.with(getApplicationContext())
                .load(clothesUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(clothes);
    }

}
