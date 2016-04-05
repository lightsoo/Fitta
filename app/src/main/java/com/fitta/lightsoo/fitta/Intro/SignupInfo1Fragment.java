package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.R;

public class SignupInfo1Fragment extends Fragment {

    private static int flag =0;
    public RadioButton radio_man, radio_woman;

    private String age="", height="", weight="";
    private EditText et_age,  et_height, et_weight;
    private Button btn_next1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info1,container, false);
        init(view);

        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //빈칸이 있는경우
                if(preInspection()){
                    Toast.makeText(getActivity(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //flag에 따라서
                }



            }
        });


        return view;
    }

    public void init(View view){
        radio_man = (RadioButton)view.findViewById(R.id.radio_man);
        radio_woman = (RadioButton)view.findViewById(R.id.radio_woman);
        btn_next1 = (Button)view.findViewById(R.id.btn_next1);

        et_age = (EditText)view.findViewById(R.id.et_age);
        et_height=(EditText)view.findViewById(R.id.et_height);
        et_weight=(EditText)view.findViewById(R.id.et_weight);

    }

    public boolean preInspection(){
        if(TextUtils.isEmpty(et_age.getText().toString()) ||
                TextUtils.isEmpty(et_height.getText().toString())||
        TextUtils.isEmpty(et_weight.getText().toString())) {
            return false;

        }else {
            age = et_age.getText().toString();
            height = et_height.getText().toString();
            weight=et_weight.getText().toString();
            return true;
        }
    }
}

