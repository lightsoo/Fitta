package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.Camera.CameraActivity;
import com.fitta.lightsoo.fitta.R;

public class FittingResultActivity extends AppCompatActivity {
    private ImageView avatar;
    private static final int REQ_CAMERA_IMAGE = 123;

    private static int flag=0;
    private static final String TAG = "FittingResultActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting_result);
        init();

        Intent intent = new Intent(getIntent());
        flag = intent.getExtras().getInt("flag");
    }



    public void init(){


        avatar = (ImageView)findViewById(R.id.avatar);
        Glide.with(getApplicationContext())
                .load(R.drawable.body100cf)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(avatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //카메라 촬영이후 이미지의 경로를 준다,
        if (requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_OK) {
            //CameraActivity의 onPictureTaken()에서 경로 입력 받아.
            String imgPath = data.getStringExtra(CameraActivity.EXTRA_IMAGE_PATH);
            Log.i(TAG,"Got image path: " + imgPath);

            //이미지를 앨범에 저장하는게 필요.
//            saveImage(imgPath, FittingResultActivity.this);
            //이미지뷰에 설정해서 출력해주는데
//            displayImage(imgPath);

        } else if (requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_CANCELED) {
            Log.i(TAG,"User didn't take an image");
        }

    }
}
