package com.fitta.lightsoo.fitta.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.fitta.lightsoo.fitta.Camera.CameraActivity;
import com.fitta.lightsoo.fitta.R;

public class FittingFragment extends Fragment {
    private View view ;
    private ImageButton btn_camera, btn_gallery;

    //인텐트 사용시 onActivityResult에서 사용될 reqCode
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_GALLERY = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fitting, container, false);
        init();
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });


        return view;
    }

    public void init(){
        btn_camera = (ImageButton)view.findViewById(R.id.btn_camera);
        btn_gallery = (ImageButton)view.findViewById(R.id.btn_gallery);

    }




}
