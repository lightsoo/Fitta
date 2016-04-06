package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.Camera.CameraActivity;
import com.fitta.lightsoo.fitta.R;

import java.io.File;

public class FittingInfoActivity extends AppCompatActivity {

    private static final String TAG = "FittingInfoActivity";

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;

    private String resultSize = "", resultUnit = ""; //사이즈, 단위
    private Spinner spinner1, spinner2;          //사이즈, 단위 스피너
    private String[] spinner1Item, spinner2Item;
    private ArrayAdapter spinner1Adapter, spinner2Adapter;

    private Button btn_post ;
    private static int flag=0 ;

    private File mSavedFile;

    //카메라를 찍은 다음 사진을 임시로 저장해서 이후에 크롭 인텐트를 이용해서
    // THEMP_PHOTO_FILE로 명명해서 크롭된 이미지를 사용
    private static final String TEMP_CAMERA_FILE = "temp_camera.jpg";
    private static final String TEMP_PHOTO_FILE = "temp_album.jpg";

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

        Intent intent = new Intent(getIntent());
        flag = intent.getExtras().getInt("flag");
        Log.d(TAG, String.valueOf(flag));

        /*String [] spinnerArray = getResources().getStringArray(R.array.spinnerArray1);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(mAdapter);*/


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*초기 어댑터값으로 세팅한다음
            눌렀을때 해당하는 값들을 어댑터에서 넣으면 2개의 스피너를
            동적으로 할수있을것 같다.*/
            @Override
            public void onItemSelected(AdapterView<?> parent, View seletedView, int position, long id) {
                //여기서 두번째 스피너를 set하고 그러자
                int pos = spinner1.getSelectedItemPosition();
                setSpinnerItme(pos);
//                setSpinnerData(seletedView, position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View seletedView, int position, long id) {
                //선택된 데이타
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
                }else{
                //여기서 액티비티 flag에 따라 카메라, 갤러리액티비티로 이동한다.
                    if(flag == REQUEST_CAMERA){
                        onUseCameraClick();
                    }else if(flag == REQUEST_GALLERY){
                        getGalleryImage();
                    }
                }
            }
        });
    }

    public void init(){
//        editSize = (EditText)findViewById(R.id.editSize);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1Item = new String[]{"일반치수", "cm", "Inch", "호", "영문"};
        spinner1Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner1Item);
        spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinner1Adapter);

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        btn_post = (Button)findViewById(R.id.btn_post);
    }
    //spinner2데이터 세팅!
    public void setSpinnerItme(int pos){
        switch (pos){
            //일반치수
            case 0 : spinner2Item = new String[]{"44", "55", "66","1", "2"};
                break;
            //cm
            case 1 : spinner2Item = new String[]{"65", "70", "75"};
                break;
            //Inch
            case 2 : spinner2Item = new String[]{"25", "27", "29"};
                break;
            //호
            case 3 : spinner2Item = new String[]{"85", "90", "95", "100"};
                break;
            case 4 : spinner2Item = new String[]{"S", "M", "L", "XL"};
                break;
        };
        spinner2Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner2Item);
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(spinner2Adapter);
    }


    //입력체크
    public boolean preInspection(){
        return true;
//        if(TextUtils.isEmpty(editSize.getText().toString())){
//            return false;
//        }else{
//            resultSize1 = editSize.getText().toString();
//            return true;
//        }
    }




    public void setSpinnerData(View v, int position){
        resultUnit = (String)spinner1.getAdapter().getItem(spinner1.getSelectedItemPosition());
        resultSize = (String)spinner2.getAdapter().getItem(spinner2.getSelectedItemPosition());
        //바로 0번 position이 눌리면 어댑터 추가하자
//        if(spinner1.getSelectedItemPosition()>0){
//            resultSize = (String)spinner1.getAdapter().getItem(spinner1.getSelectedItemPosition());
//        }else {
//            resultSize="";
//        }
        Log.d(TAG, "resultUnit : " +resultUnit + ", resultSize : " +resultSize);


    }

    //카메라 액티비티 실행
    public void onUseCameraClick() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("clothesUnit",resultUnit);
        intent.putExtra("clothesSize", resultSize);
        startActivity(intent);
        finish();

        //그냥 스타트인텐트할까나...
//        Log.d(TAG , "clothesSize : " + resultSize1 + ", clothesUnit : " + resultSize2);
//        intent.putExtra("clothesSize", resultSize1);
//        intent.putExtra("clothesUnit",resultSize2);
//        startActivity(intent);
    }

    private void getGalleryImage(){
        Intent photoPickerIntent = new Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop", "true");
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        photoPickerIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);
    }

    private Uri getTempUri() {
        //정해둔 경로에 파일객체를 만든 다음에 그 객체의 경로를 action_pick에 MediaStore.EXTRA_OUTPUT에 같이 넘겨준다.
        mSavedFile = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
        Log.d(TAG, "getTempUri() : "+ Uri.fromFile(mSavedFile).toString());
        return Uri.fromFile(mSavedFile);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //액티비티 결과가 이상는경우
        if(resultCode != RESULT_OK){return;}
       /**
        * camera는 내가 만든 액티비티로 이동하니깐 거기서 startIntent(FittingResult)해서 파일 경로를 보내면되
        * 갤러리를 action_pick을 쓰니깐 직접 조작이 안되, 그래서 호출했던 액티비티로 onActivityResult로 와서
        * FittingResult액티비티로 화면이동을 해줘야 될것 같다
        * 공통으로는 파일의 경로를 putExtra()해서 FittingResultActivity를 호출하자!!!!
        */
        switch (requestCode){
//            case REQUEST_CAMERA :
//                break;
            /**
             * 갤러리 인텐트 호출해서 getGalleryImage()함수에서 갤러리 인텐트를 부른 다음
             * 갤러리인텐트 종료 이후의 작업을 여기서 한다.
             */
            case REQUEST_GALLERY :
                String filePath = Environment.getExternalStorageDirectory() + "/" + TEMP_PHOTO_FILE;
                Intent intent = new Intent(FittingInfoActivity.this, FittingResultActivity.class);
                intent.putExtra("clothesUrl", filePath);
                Log.d(TAG, "filePath : " + filePath + ", clothesSize : " + resultSize + ", clothesUnit : " + resultUnit);
                intent.putExtra("clothesSize", resultSize);
                intent.putExtra("clothesUnit",resultUnit);
                startActivity(intent);
                break;
        }
    }
}
