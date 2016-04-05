package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.R;

public class SignupInfo1Fragment extends Fragment {

    private static final String TAG = "SignupInfo1Fragment";

    private static int flag =0;

    private String age="", height="", weight="";
    private EditText et_age,  et_height, et_weight;
    private Button btn_next1;
    private RadioGroup radioGroup;
    private RadioButton radio_man, radio_woman;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info1,container, false);
        init(view);





        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //빈칸이 있는경우
                if(!preInspection()){
                    Toast.makeText(getActivity(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //flag에 따라서
                }
            }
        });
        return view;
    }

    public void init(View view){
        btn_next1 = (Button)view.findViewById(R.id.btn_next1);

        et_age = (EditText)view.findViewById(R.id.et_age);
        et_height=(EditText)view.findViewById(R.id.et_height);
        et_weight=(EditText)view.findViewById(R.id.et_weight);

        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);
        radio_man= (RadioButton)view.findViewById(R.id.radio_man);
        radio_woman = (RadioButton)view.findViewById(R.id.radio_woman);
    }

    public boolean preInspection(){
        if(TextUtils.isEmpty(et_age.getText().toString()) ||
                TextUtils.isEmpty(et_height.getText().toString())||
                TextUtils.isEmpty(et_weight.getText().toString())) {

            Log.d(TAG, "radio_man : " + radio_man.isChecked());
            Log.d(TAG, "radio_woman : " + radio_woman.isChecked());
            return false;
        }else {

            //성별 클릭 유무
            if(radio_man.isChecked()){
                Toast.makeText(getActivity(), "남자 클릭", Toast.LENGTH_SHORT).show();
                flag = 2;
            }else if(radio_woman.isChecked()){
                Toast.makeText(getActivity(), "여자 클릭", Toast.LENGTH_SHORT).show();
                flag = 3;
            }

            Log.d(TAG, "flag : " + flag);
            age = et_age.getText().toString();
            height = et_height.getText().toString();
            weight=et_weight.getText().toString();
            return true;
        }
    }
}

