package com.fitta.lightsoo.fitta.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.Data.Fitta;
import com.fitta.lightsoo.fitta.Dialog.DialogLoadingFragment;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.Manager.PropertyManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//바로 회원정보를 불러온다음 힌트로 넣어주고 수정하게끔한다.
//레이아웃을 2개만들어 두고 남자와 여자를 구별해서 출력하게끔하자.
public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    //성별 플래그
    private String sex_flag = "";
    private RelativeLayout female_layout, male_layout;

    private Button btn_put;

    //남자
    private EditText et_male_age, et_male_height, et_male_weight, et_male_top, et_male_bottom;

    //여자
    private EditText et_female_age, et_female_height, et_female_weight, et_female_bottom;
    private Spinner female_spinner;//top
    private String[] spinnerItem;
    private ArrayAdapter arrayAdapter;

    //전송데이터
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

        PropertyManager.getInstance().setUserSex("여");
        PropertyManager.getInstance().setUserAge("21");
        PropertyManager.getInstance().setUserTop("B");

        Log.d(TAG, "성별은 : " + PropertyManager.getInstance().getUserSex()
                + ", 나이는 : " + PropertyManager.getInstance().getUserAge());
        init();
        getUserInfo();


        female_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = female_spinner.getSelectedItemPosition();
//                Toast.makeText(getApplicationContext(), "선택번호 : " + sid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putUserInfo();
            }
        });

    }

    //처음 정보를 받아온다.
    public void getUserInfo(){

        //로딩 다이얼로그
        final DialogLoadingFragment dialog = new DialogLoadingFragment();
        dialog.show(getSupportFragmentManager(), "loading");

        sex_flag = PropertyManager.getInstance().getUserSex();

        if(sex_flag.equals("여")){
            female_layout.setVisibility(View.VISIBLE);
            male_layout.setVisibility(View.GONE);

            et_female_age.setText(PropertyManager.getInstance().getUserAge());
            et_female_weight.setHint(PropertyManager.getInstance().getUserWeight());
            et_female_height.setHint(PropertyManager.getInstance().getUserHeight());
            setFemaleTop(PropertyManager.getInstance().getUserTop());


            et_female_bottom.setHint(PropertyManager.getInstance().getUserBottom());

        }else{
            male_layout.setVisibility(View.VISIBLE);
            female_layout.setVisibility(View.GONE);

            et_male_age.setText(PropertyManager.getInstance().getUserAge());
            et_male_weight.setHint(PropertyManager.getInstance().getUserWeight());
            et_male_height.setHint(PropertyManager.getInstance().getUserHeight());
            et_male_top.setHint(PropertyManager.getInstance().getUserTop());
            et_female_bottom.setHint(PropertyManager.getInstance().getUserBottom());
        }
        dialog.dismiss();
    }

    //수정된 정보를 서버에전송
    public void putUserInfo(){
        //로딩 다이얼로그
        final DialogLoadingFragment dialog = new DialogLoadingFragment();
        dialog.show(getSupportFragmentManager(), "loading");
        //여자라면
        if(sex_flag == "여"){
            age = et_female_age.getText().toString();
            weight = et_female_weight.getText().toString();
            height = et_female_height.getText().toString();
            top = (String)female_spinner.getAdapter().getItem(female_spinner.getSelectedItemPosition());
            bottom = et_female_bottom.getText().toString();
        }else{//남자라면
            age = et_male_age.getText().toString();
            weight = et_male_weight.getText().toString();
            height = et_male_height.getText().toString();
            top = et_male_top.getText().toString();
            bottom = et_male_bottom.getText().toString();
        }

        Fitta fitta = new Fitta(PropertyManager.getInstance().getUserSex(), age, height, weight, top, bottom) ;
        Call call = NetworkManager.getInstance().getAPI(FittaAPI.class).signup(fitta);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Toast.makeText(getApplicationContext(), "서버 성공", Toast.LENGTH_SHORT).show();
//                    Fitta user = (Fitta)response.body();
                    finish();
                    dialog.dismiss();
                } else {
                    //아니라면 not registered

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }



    public void init(){
        btn_put = (Button)findViewById(R.id.btn_put);

        female_layout = (RelativeLayout)findViewById(R.id.female_layout);
        male_layout = (RelativeLayout)findViewById(R.id.male_layout);

        //남자
        et_male_age = (EditText)findViewById(R.id.et_male_age);
        et_male_height = (EditText)findViewById(R.id.et_male_height);
        et_male_weight = (EditText)findViewById(R.id.et_male_weight);
        et_male_top = (EditText)findViewById(R.id.et_male_top);
        et_male_bottom = (EditText)findViewById(R.id.et_male_bottom);

        //여자
        et_female_age = (EditText)findViewById(R.id.et_female_age);
        et_female_height = (EditText)findViewById(R.id.et_female_height);
        et_female_weight = (EditText)findViewById(R.id.et_female_weight);
        et_female_bottom = (EditText)findViewById(R.id.et_female_bottom);
        female_spinner = (Spinner)findViewById(R.id.female_spinner);
        spinnerItem = new String[]{"A", "B", "C", "D", "E"};
        arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, spinnerItem);
        //어댑터 리스트 커스텀
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        female_spinner.setAdapter(arrayAdapter);
    }

    //여자가 어떤상의인지에 따라서 스피너 설정
    public void setFemaleTop(String cntTop){
        switch (cntTop){
            case "A": female_spinner.setSelection(0);
                break;
            case "B": female_spinner.setSelection(1);
                break;
            case "C": female_spinner.setSelection(2);
                break;
            case "D": female_spinner.setSelection(3);
                break;
            case "E": female_spinner.setSelection(4);
                break;

        }


    }


}
