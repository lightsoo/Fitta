package com.fitta.lightsoo.fitta.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fitta.lightsoo.fitta.R;

//바로 회원정보를 불러온다음 힌트로 넣어주고 수정하게끔한다.
//레이아웃을 2개만들어 두고 남자와 여자를 구별해서 출력하게끔하자.
public class UserActivity extends AppCompatActivity {

    private Spinner spinner;
    private String[] spinnerItem;
    private ArrayAdapter arrayAdapter;



    private String age="", height="", weight="";
    //가슴둘레(컵), 허리둘레(inch)
    private String top="", bottom="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //이걸로 기존에 뜨는 Title을 안보이게 한다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //백키가 나온다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //백키 이벤트
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        init();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = spinner.getSelectedItemPosition();
//                Toast.makeText(getApplicationContext(), "선택번호 : " + sid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        top = (String)spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
//        bottom = et_bottom.getText().toString();



    }


    public void init(){

        spinner = (Spinner)findViewById(R.id.spinner3);
        spinnerItem = new String[]{"A", "B", "C", "D", "E"};
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerItem);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

    }

}
