package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fitta.lightsoo.fitta.R;

public class FittingInfoActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //액티비티 결과가 이상는경우
        if(resultCode != RESULT_OK){return;}

        switch (requestCode){
            case REQUEST_CAMERA :
                break;
            case REQUEST_GALLERY :
                break;
        }
    }
}
