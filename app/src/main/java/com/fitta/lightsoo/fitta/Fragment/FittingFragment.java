package com.fitta.lightsoo.fitta.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.fitta.lightsoo.fitta.Activity.FittingInfoActivity;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;

public class FittingFragment extends Fragment {


    private static final String TAG = "FittingFragment";

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
                startActivity(intent);
            }
        });
        return view;
    }

    public void init() {
        btn_camera = (ImageButton)view.findViewById(R.id.btn_camera);
        btn_gallery = (ImageButton)view.findViewById(R.id.btn_gallery);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "===onActivityResult()===");
//        Log.d(TAG, "REQUSESTCODE : " + requestCode);

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
