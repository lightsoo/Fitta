package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
        return view;
    }

    public void init(View view){
        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinnerItem = new String[]{"A", "B", "C", "D", "E"};
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, spinnerItem);
        spinner.setAdapter(arrayAdapter);
//        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);



    }
}
