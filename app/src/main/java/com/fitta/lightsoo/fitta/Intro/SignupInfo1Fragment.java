package com.fitta.lightsoo.fitta.Intro;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fitta.lightsoo.fitta.R;


/**
 * signup2, 3에 번들을 통해 setArgumet()로 signup1의 데이터를 전달하자!
 */

public class SignupInfo1Fragment extends Fragment {

    private static final String TAG = "SignupInfo1Fragment";

    private static int flag =0;

    private String age="", height="", weight="";
    private EditText et_age,  et_height, et_weight;
    private Button btn_next1;
    private RadioGroup radioGroup;
    private RadioButton radio_man, radio_woman;

    Fragment info2, info3;
    RelativeLayout layoutPlace ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info1, container, false);
        init(view);


        Bundle bundle = new Bundle();


//                getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.container, info1).commit();




        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //빈칸이 있는경우
                if (!preInspection()) {
                    Toast.makeText(getActivity(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("age",age);
                    bundle.putString("weight",weight);
                    bundle.putString("height",height);

                    //flag에 따라서 2가여자, 3이 남자
                    if (flag == 2) {
                        info2 = new SignupInfo2Fragment();
                        info2.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, info2).commit();
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.add(R.id.container, info2, info2_TAG);
//                        ft.commit();
                    } else {
                        info3 = new SignupInfo3Fragment();
                        info3.setArguments(bundle);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, info3).commit();

//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.add(R.id.container, info3, info3_TAG);
//                        ft.commit();
                    }
                }
            }
        });
        return view;
    }

    public void init(View view){
        btn_next1 = (Button)view.findViewById(R.id.btn_next1);

        info2 = new SignupInfo2Fragment();
        info3 = new SignupInfo3Fragment();

        et_age = (EditText)view.findViewById(R.id.et_age);
        et_height=(EditText)view.findViewById(R.id.et_height);
        et_weight=(EditText)view.findViewById(R.id.et_weight);

        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);
//        radioGroup.clearCheck();
        radio_man= (RadioButton)view.findViewById(R.id.radio_man);
        radio_woman = (RadioButton)view.findViewById(R.id.radio_woman);

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

    public boolean preInspection(){
        if(TextUtils.isEmpty(et_age.getText().toString()) ||
                TextUtils.isEmpty(et_height.getText().toString())||
                TextUtils.isEmpty(et_weight.getText().toString())||
                !(radio_man.isChecked()||radio_woman.isChecked())) {

//            Log.d(TAG, "radio_man : " + radio_man.isChecked());
//            Log.d(TAG, "radio_woman : " + radio_woman.isChecked());
            return false;
        }else {
            //성별 클릭 유무
            if(radio_man.isChecked()){
                Toast.makeText(getActivity(), "남자 클릭", Toast.LENGTH_SHORT).show();
                flag = 3;
            }else if(radio_woman.isChecked()){
                Toast.makeText(getActivity(), "여자 클릭", Toast.LENGTH_SHORT).show();
                flag = 2;
            }

            Log.d(TAG, "flag : " + flag);
            age = et_age.getText().toString();
            height = et_height.getText().toString();
            weight=et_weight.getText().toString();
            return true;
        }
    }
}

