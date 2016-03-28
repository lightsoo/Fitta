package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.R;

public class FittingInfoActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_GALLERY = 1;
    private String resultSize1 = "";
    private String resultSize2 = "";
    private Spinner spinner;

    private EditText editSize ;
    private Button btn_post ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting_info);
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

        String [] spinnerArray = getResources().getStringArray(R.array.spinnerArray1);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View seletedView, int position, long id) {
                setSpinnerData(seletedView, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //빈칸이 있는경우
                if(!preInspection()){
                    Toast.makeText(FittingInfoActivity.this, "빈칸있다!", Toast.LENGTH_SHORT).show();
                }else{//여기서 액티비티 flag에 따라 카메라, 갤러리액티비티로 이동한다.

                }
            }
        });

    }

    public void init(){
        editSize = (EditText)findViewById(R.id.editSize);
        btn_post = (Button)findViewById(R.id.btn_input_info);
    }
    //입력체크
    public boolean preInspection(){
        if(TextUtils.isEmpty(editSize.getText().toString())){
            return false;
        }else{
            resultSize1 = (String)editSize.getText().toString();

            return true;
        }
    }

    public void setSpinnerData(View v, int position){

        if(spinner.getSelectedItemPosition()>0){
            resultSize2 = (String)spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
        }else {
            resultSize2="";
        }

        Log.d("test", resultSize2);
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
