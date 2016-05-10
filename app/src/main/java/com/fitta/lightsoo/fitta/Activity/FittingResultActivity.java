package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.Manager.PropertyManager;
import com.fitta.lightsoo.fitta.R;

public class FittingResultActivity extends AppCompatActivity {
    private ImageView avatar, clothes;
    private Button btn_refitting, btn_fittingroom;

    private static final String TAG = "FittingResultActivity";
    //이거 텍스트 뷰로 바꿔야해
    private TextView tv_clothesFeedback;
    private String clothesFeedback = ""; //서버로부터 리턴받은 피드백
    private String clothesUrl = ""; //사진촬영 or 갤러리이미지를 서버에 보낸다음 리턴받은 이미지의 경로

    //배경화면 세팅
    RelativeLayout background_fitting_result ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting_result);
        init();

        /**
         * 1. 나중에는 서버에서 이미지 처리하기위해 받으면 액티비티 생성후 데이터를 보내서
         * 피드백과 처리된 이미지를 받아서 화면에 적용한다.
         * -> 카메라, 갤러리 둘다 코딩을 할필요가없이 여기서만 한번 작업해주면 된다!!!..
         *
         * 2. RittingResultActivity를 호출하기 이전에 하면은 좀더 깔끔할수도 있을것 같다...
         *
         */

        Intent intent = new Intent(getIntent());
        clothesUrl = intent.getExtras().getString("clothesUrl");
        clothesFeedback = intent.getExtras().getString("clothesFeedback");
        tv_clothesFeedback.setText(clothesFeedback);
//        Log.d(TAG, "FittingResultActivity에서 이미지 받은 유알엘 : "+clothesUrl);

        //아바타에 입힐 옷을 세팅하는거야!!
        setClothes(clothesUrl);

        //다시 피팅하기 클릭시 처음부터 다시
        btn_refitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent retIntent = new Intent();
//                retIntent.putExtra("retVal", "555");
                retIntent.putExtra("retVal", 1);
                setResult(RESULT_OK, retIntent);
                finish();
            }
        });

        //피팅룸으로 가기
        btn_fittingroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent retIntent = new Intent();
//                retIntent.putExtra("retVal", "555");
                retIntent.putExtra("retVal", 2);
                setResult(RESULT_OK, retIntent);
                finish();
//                setResult(10);
//                MainActivity.getInstance().switchTab(1);
//                MainActivity.getCurrentTabHost().setCurrentTab(1);
//                MainActivity.getInstance().switchTab(1);
//                MainActivity.getInstance().setFragment2();

                //이건 아닌듯하다...
//                Intent intent = new Intent(FittingResultActivity.this, MainActivity.class);
//                startActivityForResult(intent, TEST);
                //프레그먼트 이동!
            }
        });

        /*layoutPlace = (LinearLayout)findViewById(R.id.login_background);
        Glide.with(getApplicationContext())
                .load(R.drawable.background_image)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                layoutPlace.setBackground(drawable);
            }
        });*/
    }

    public void init(){

        tv_clothesFeedback = (TextView)findViewById(R.id.tv_clothesFeedback);
        clothes = (ImageView)findViewById(R.id.result_clothes);
        avatar = (ImageView)findViewById(R.id.result_avatar);
        Glide.with(getApplicationContext())
                .load(PropertyManager.getInstance().getUserAvatar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(avatar);

//        background_fitting_result = (RelativeLayout)findViewById(R.id.background);
//        Glide.with(getApplicationContext())
//                .load(R.drawable.avatar_background)
//                .asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Drawable drawable = new BitmapDrawable(resource);
//                background_fitting_result.setBackground(drawable);
//            }
//        });


        btn_refitting = (Button)findViewById(R.id.btn_refitting);
        btn_fittingroom = (Button)findViewById(R.id.btn_fittingroom);
    }

    //아바타에 입힐옷 세팅
    public void setClothes(String clothesUrl){
        Glide.with(getApplicationContext())
                .load(clothesUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(clothes);
    }



 /*   //카메라or갤러리를 통해 입력받은 정보들을 받은다음 서버에 보낼꺼야
    //카메라나 갤러리에서 작업을 하면 2번의 작업을 거치게될테니 여기서 한번만 해서 보내주는게 맞다
    public void setInfo(){
        age = getArguments().getString("age");
        height = getArguments().getString("height");
        weight = getArguments().getString("weight");
    }*/
}