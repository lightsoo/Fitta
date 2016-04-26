package com.fitta.lightsoo.fitta.TabFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fitta.lightsoo.fitta.Activity.FittingInfoActivity;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;

public class FittingFragment extends Fragment {


    private static final String TAG = "FittingFragment";
    private static final int RESULT_OK = -1;

    LinearLayout layoutPlace ;

    private View view ;
    private ImageButton btn_camera, btn_gallery;

    //인텐트 사용시 onActivityResult에서 사용될 reqCode
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;

    private static final int FITTING_RESULT = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fitting, container, false);
        init();
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FittingInfoActivity.class);
                intent.putExtra("flag", REQUEST_CAMERA);
                startActivityForResult(intent, FITTING_RESULT);

//                startActivity(intent);
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FittingInfoActivity.class);
                intent.putExtra("flag", REQUEST_GALLERY);
                startActivityForResult(intent, FITTING_RESULT);
//                startActivity(intent);
            }
        });
        return view;
    }

    public void init() {
        btn_camera = (ImageButton)view.findViewById(R.id.btn_camera);
        btn_gallery = (ImageButton)view.findViewById(R.id.btn_gallery);


        layoutPlace = (LinearLayout)view.findViewById(R.id.background);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "===onActivityResult()===");
//        Log.d(TAG, "REQUSESTCODE : " + requestCode);
        //액티비티 결과가 이상는경우, 이거 필수!!!!!
        if(resultCode != RESULT_OK){return;}
//        String ret = data.getStringExtra("retVal");
        int ret = data.getIntExtra("retVal", 12345);
        Log.d(TAG, "return msg : "+  ret);
        //리턴받는 값이 1이면 피팅화면, 2면 피팅룸화면 출력한다.
        switch (ret){
            //다시 피팅하는 프레그먼트 이동
            case 1 :
                MainActivity.getInstance().switchTab(0);
//                Log.d(TAG, "111REQUSESTCODE : " + requestCode);
                break;
            //피팅룸으로 이동
            case 2 :
                MainActivity.getInstance().switchTab(1);
//                Log.d(TAG, "222REQUSESTCODE : " + requestCode);
                break;
        }


    }
}
