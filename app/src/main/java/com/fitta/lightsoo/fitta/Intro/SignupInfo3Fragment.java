package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;


//남자 상세정보입력
public class SignupInfo3Fragment extends Fragment {

    private static final String TAG = "SignupInfo3Fragment";


    private String age="", height="", weight="";
    //가슴둘레(cm), 허리둘레(inch)
    private String top="", bottom="";
    private EditText et_top, et_bottom;
    private Button btn_post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info3, container, false);
        init(view);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기는 선택사항이니깐 나중에 입력해도되
                //editText에 빈칸이 있다면!!
//                if(!preInspection()){
//                    Toast.makeText(getActivity(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
//                }else{
//
//                }
                setInfo();
                top = et_top.getText().toString();
                bottom = et_bottom.getText().toString();
                Log.d(TAG,"age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom);
                //입력받은 값들을 이제 서버에 보내줘야해 그리고 나서





                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


    public void init(View view){
        btn_post = (Button)view.findViewById(R.id.btn_post);

        et_top = (EditText)view.findViewById(R.id.et_top);
        et_bottom = (EditText)view.findViewById(R.id.et_bottom);
    }

    public boolean preInspection(){
        if(TextUtils.isEmpty(et_top.getText().toString()) ||
                TextUtils.isEmpty(et_bottom.getText().toString())){
            return false;
        }else{

            top = et_top.getText().toString();
            bottom = et_bottom.getText().toString();
            return true;
        }
    }

    //signUpInfo1으로 받은 정보!
    public void setInfo(){
        age = getArguments().getString("age");
        height = getArguments().getString("height");
        weight = getArguments().getString("weight");
    }



}
