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

import com.fitta.lightsoo.fitta.R;

import java.io.File;

public class FittingInfoActivity extends AppCompatActivity {




    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int REQUEST_CROP = 102;
    private String resultSize1 = "";
    private String resultSize2 = "";
    private Spinner spinner;

    private EditText editSize ;
    private Button btn_post ;
    private static int flag=0;



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
        
        Log.d("test ", String.valueOf(flag));

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
                    if(flag == REQUEST_CAMERA){

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

        Log.d("test", resultSize2);
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
        mSavedFile = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
        return Uri.fromFile(mSavedFile);
//        return Uri.fromFile(getTempFile());
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //액티비티 결과가 이상는경우
        if(resultCode != RESULT_OK){return;}

        switch (requestCode){
            case REQUEST_CAMERA :
                Log.d("RESULT", "카메라");
                Toast.makeText(FittingInfoActivity.this, "카메라액티비티 클릭! ", Toast.LENGTH_SHORT).show();
                break;
            case REQUEST_GALLERY :
                Log.d("RESULT", "갤러리");
                Toast.makeText(FittingInfoActivity.this, "갤러리액티비티 클릭! ", Toast.LENGTH_SHORT).show();
                break;
        }
    }*/
}
