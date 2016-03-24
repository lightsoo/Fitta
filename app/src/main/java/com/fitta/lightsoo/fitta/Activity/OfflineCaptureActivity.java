package com.fitta.lightsoo.fitta.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.fitta.lightsoo.fitta.R;

public class OfflineCaptureActivity extends AppCompatActivity {
    Fragment ClothesTop, ClothesBottom, ClothesEtc, current;
    ImageButton btnTop, btnBottom, btnEtc;

    //tag를 통해서 현재 프레그먼트 구별 한다.
    private static final String ClothesTop_TAG = "ClothesTop";
    private static final String ClothesBottom_TAG = "ClothesBottom";
    private static final String ClothesEtc_TAG = "ClothesEtc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_capture);
        init();

    }

    public void init(){
        btnTop = (ImageButton)findViewById(R.id.btnTop);
        btnBottom = (ImageButton)findViewById(R.id.btnBottom);
        btnEtc = (ImageButton)findViewById(R.id.btnEtc);



    }
}
