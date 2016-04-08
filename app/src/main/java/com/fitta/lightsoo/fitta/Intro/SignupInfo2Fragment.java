package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;

//여자 상세정보입력
public class SignupInfo2Fragment extends Fragment {

    private static final String TAG = "SignupInfo2Fragment";


    private Spinner spinner;
    private String[] spinnerItem;
    private ArrayAdapter arrayAdapter;


    private String age="", height="", weight="";
    //가슴둘레(컵), 허리둘레(inch)
    private String top="", bottom="";

    private EditText et_bottom;
    private Button btn_post;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info2, container, false);
        init(view);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = spinner.getSelectedItemPosition();
                Toast.makeText(getActivity(), "선택번호 : " + sid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo();
                top = (String)spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
                bottom = et_bottom.getText().toString();
                Log.d(TAG,"age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });


        return view;
    }

    public void init(View view){
        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinnerItem = new String[]{"A", "B", "C", "D", "E"};
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, spinnerItem);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        et_bottom = (EditText)view.findViewById(R.id.et_bottom);
        btn_post = (Button)view.findViewById(R.id.btn_post);



//        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    //signUpInfo1으로 받은 정보!
    public void setInfo(){
        age = getArguments().getString("age");
        height = getArguments().getString("height");
        weight = getArguments().getString("weight");
    }


}
