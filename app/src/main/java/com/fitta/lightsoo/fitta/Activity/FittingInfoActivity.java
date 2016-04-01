package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.fitta.lightsoo.fitta.Camera.CameraActivity;
import com.fitta.lightsoo.fitta.R;

import java.io.File;

public class FittingInfoActivity extends AppCompatActivity {

    private static final String TAG = "FittingInfoActivity";

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private String resultSize1 = ""; //사이즈
    private String resultSize2 = ""; //단위 결과

    private Spinner spinner;          //단위 선택
    private EditText editSize ;       //사이즈 선택

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
        editSize = (EditText)findViewById(R.id.editSize);
        btn_post = (Button)findViewById(R.id.btn_input_info);
    }
    //입력체크
    public boolean preInspection(){
        if(TextUtils.isEmpty(editSize.getText().toString())){
            return false;
        }else{
            resultSize1 = editSize.getText().toString();
            return true;
        }
    }

    public void setSpinnerData(View v, int position){
        if(spinner.getSelectedItemPosition()>0){
            resultSize2 = (String)spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
        }else {
            resultSize2="";
        }
        Log.d(TAG, resultSize2);
    }

    //카메라 액티비티 실행
    public void onUseCameraClick() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("clothesSize", resultSize1);
        intent.putExtra("clothesUnit",resultSize2);
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
                Log.d("data ", "filePath : " + filePath + ", clothesSize : " + resultSize1 + ", clothesUnit : " + resultSize2);
                intent.putExtra("clothesSize", resultSize1);
                intent.putExtra("clothesUnit",resultSize2);
                startActivity(intent);
                break;
        }
    }
}
