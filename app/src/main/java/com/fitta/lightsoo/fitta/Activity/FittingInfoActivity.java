package com.fitta.lightsoo.fitta.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.Camera.CameraActivity;
import com.fitta.lightsoo.fitta.Data.Message;
import com.fitta.lightsoo.fitta.Dialog.DialogLoadingFragment;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RadioLayout.RadioButtonWithTableLayout;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.fitta.lightsoo.fitta.Util.MediaHelper.getOutputMediaFile;

/**
 * FittingInfoActivity에서 입력한 값{ clothesCategory : 빅테이터 불류할 값, clothesSize : 결과값, )
 * bundle을 통해 받으면 입력한값과 사진파일 :  mSaveFile을 서버에 전송해서
 * 결과이미지를 clothesUrl로 이미지 경로를 받는다.
 */

public class FittingInfoActivity extends AppCompatActivity {

    private static final String TAG = "FittingInfoActivity";

    //배경화면 세팅
    LinearLayout background_fitting_info;

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int FITTING_RESULT = 10;

    private RelativeLayout relativeLayout2, relativeLayoutGone, relativeLayoutGone2;
    private Button btn_Layout2, btn_Gone;

    private String clothesUnit = ""; //단위 결과, 이거는 분류표!
    //서버에 전송할 데이터
    private String clothesSize = ""; //사이즈, 이것만 서버에 보낸다.
    private String clothesCategory = ""; //빅데이터 분류용 어떤 이미지인지
    //서버로 전송받을 데이터 + FittingResultActivity로 전달
    private String clothesUrl = ""; //이미지 결과
    private String clothesFeedback = "";//피드백 결과

    private Spinner spinner1, spinner2;          //사이즈, 단위 스피너
    private String[] spinner1Item, spinner2Item;
    private ArrayAdapter spinner1Adapter, spinner2Adapter;

    private Button btn_post ;
    private static int flag=0 ;

    private RadioButtonWithTableLayout tableLayoutTop, tableLayoutBottom, tableLayoutEtc;
    public static final int top_id[] = {R.id.top1, R.id.top2,R.id.top3,R.id.top4,R.id.top5,R.id.top6,R.id.top7,R.id.top8,R.id.top9};
    public static final int bottom_id[] = {R.id.bottom1, R.id.bottom2, R.id.bottom3, R.id.bottom4, R.id.bottom5, R.id.bottom6};
    public static final int etc_id[] = {R.id.etc1, R.id.etc2, R.id.etc3};
    RadioButton radioTop[] = new RadioButton[9];
    RadioButton radioBottom[] = new RadioButton[6];
    RadioButton radioEtc[] = new RadioButton[6];
    private RadioButton mBtnCurrentRadio;
    private RadioGroup rg_length;
    private RadioButton rd_length3,rd_length2, rd_length1;

    private File mSaveFile;

    //카메라를 찍은 다음 사진을 임시로 저장해서 이후에 크롭 인텐트를 이용해서
    // THEMP_PHOTO_FILE로 명명해서 크롭된 이미지를 사용
    private static final String TEMP_CAMERA_FILE = "temp_camera.jpg";
    private static String TEMP_PHOTO_FILE = "temp_album.jpg";

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
        relativeLayout2.setVisibility(View.VISIBLE);
        relativeLayoutGone.setVisibility(View.GONE);

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
                if (!preInspection()) {
                    Toast.makeText(FittingInfoActivity.this, "빈칸있다!", Toast.LENGTH_SHORT).show();
                } else {
                    //여기서 액티비티 flag에 따라 카메라, 갤러리액티비티로 이동한다.
                    if (flag == REQUEST_CAMERA) {
                        onUseCameraClick();
                    } else if (flag == REQUEST_GALLERY) {
                        getGalleryImage();
                    }
                }
            }
        });

        //GoneLayout으로 화면 전환
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout2.setVisibility(View.GONE);
                relativeLayoutGone.setVisibility(View.VISIBLE);
            }
        });

        //GoneLayout으로 화면 전환
        btn_Layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout2.setVisibility(View.GONE);
                relativeLayoutGone.setVisibility(View.VISIBLE);
            }
        });

        //기존의 화면으로 전환
        relativeLayoutGone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout2.setVisibility(View.VISIBLE);
                relativeLayoutGone.setVisibility(View.GONE);
            }
        });
        //기존의 화면으로 전환
        btn_Gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout2.setVisibility(View.VISIBLE);
                relativeLayoutGone.setVisibility(View.GONE);
            }
        });

    }

    public void init(){
        tableLayoutTop = (RadioButtonWithTableLayout)findViewById(R.id.tableLayoutTop);
        tableLayoutBottom = (RadioButtonWithTableLayout)findViewById(R.id.tableLayoutBottom);
        tableLayoutEtc = (RadioButtonWithTableLayout)findViewById(R.id.tableLayoutEtc);
        relativeLayout2 = (RelativeLayout)findViewById(R.id.relativeLayout2);
        relativeLayoutGone = (RelativeLayout)findViewById(R.id.relativeLayoutGone);
        relativeLayoutGone2 = (RelativeLayout)findViewById(R.id.relativeLayoutGone2);

        //각
        btn_Layout2 = (Button)findViewById(R.id.btn_Layout2);
        btn_Gone = (Button)findViewById(R.id.btn_gone);

        //버튼 리스너 처리!!!
        MyOnClickListener mcl = new MyOnClickListener();

        for(int i = 0; i< top_id.length; i++){
            radioTop[i] = (RadioButton)findViewById(top_id[i]);
            radioTop[i].setOnClickListener(mcl);
        }
        for(int i = 0; i< bottom_id.length; i++){
            radioBottom[i] = (RadioButton)findViewById(bottom_id[i]);
            radioBottom[i].setOnClickListener(mcl);
        }
        for(int i = 0; i< etc_id.length; i++){
            radioEtc[i] = (RadioButton)findViewById(etc_id[i]);
            radioEtc[i].setOnClickListener(mcl);
        }

//        tableLayoutTop.setOnClickListener(mcl);
//        tableLayoutBottom.setOnClickListener(mcl);
//        tableLayoutEtc.setOnClickListener(mcl);

//        btn_fitting = (Button)findViewById(R.id.btn_fitting);

//        editSize = (EditText)findViewById(R.id.editSize);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1Item = new String[]{"44 55 66", "cm", "Inch", "85 90 95", "S M L"};
        spinner1Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner1Item);
        spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinner1Adapter);

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        btn_post = (Button)findViewById(R.id.btn_post);

        rg_length=(RadioGroup)findViewById(R.id.rg_length);

//        background_fitting_info = (LinearLayout)findViewById(R.id.background_fitting_info);
//        Glide.with(getApplicationContext())
//                .load(R.drawable.background_main)
//                .asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Drawable drawable = new BitmapDrawable(resource);
//                background_fitting_info.setBackground(drawable);
//            }
//        });
    }

    //spinner2데이터 세팅!
    public void setSpinnerItme(int pos){
        switch (pos){
            //일반치수
            case 0 : spinner2Item = new String[]{"44", "55", "66"};
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

    //어떠 이미지를 찍을건지 (상의9, 하의6, 기타6)
    //찍은 이미지의 이미지를 설정하면서 빅데이터 불류한다.
    public int getCaptureImage(int checkedId){
            switch (checkedId){
                case R.id.top1 :
                    clothesCategory ="top1";
                    return R.drawable.top1;
                case R.id.top2 :
                    clothesCategory ="top2";
                    return R.drawable.top2;
                case R.id.top3 :
                    clothesCategory ="top3";
                    return R.drawable.top3;
                case R.id.top4 :
                    clothesCategory ="top4";
                    return R.drawable.top4;
                case R.id.top5 :
                    clothesCategory ="top5";
                    return R.drawable.top5;
                case R.id.top6 :
                    clothesCategory ="top6";
                    return R.drawable.top6;
                case R.id.top7 :
                    clothesCategory ="top7";
                    return R.drawable.top7;
                case R.id.top8 :
                    clothesCategory ="top8";
                    return R.drawable.top8;
                case R.id.top9 :
                    clothesCategory ="top9";
                    break;
                case R.id.bottom1 :
                    clothesCategory ="bottom1";
                    return R.drawable.bottom1;
                case R.id.bottom2 :
                    clothesCategory ="bottom2";
                    return R.drawable.bottom2;
                case R.id.bottom3 :
                    clothesCategory ="bottom3";
                    return R.drawable.bottom3;
                case R.id.bottom4 :
                    clothesCategory ="bottom4";
                    return R.drawable.bottom4;
                case R.id.bottom5 :
                    clothesCategory ="bottom5";
                    return R.drawable.bottom5;
                case R.id.bottom6 :
                    clothesCategory ="bottom6";
                    return R.drawable.bottom6;
                case R.id.etc1:
                    clothesCategory ="etc1";
                    return R.drawable.etc1;
                case R.id.etc2:
                    clothesCategory ="etc2";
                    return R.drawable.etc2;
                case R.id.etc3:
                    clothesCategory ="etc3";
                    return R.drawable.etc3;
            }
        return 0;
    }

    //촬영이미지 선택됬는지 + 소매,길이 선택됬는지 두개를 비교한다.
    public boolean preInspection(){
        //-1이 아니라면
        boolean b = (getCheckedRadioButtonId() != -1);
        Log.d(TAG, String.valueOf(b));
        //비어있는 경우!!
        if(!b||rg_length.getCheckedRadioButtonId() == -1){
            return false;
        }else{
            return true;
        }


//        if(TextUtils.isEmpty(editSize.getText().toString())){
//            return false;
//        }else{
//            resultSize1 = editSize.getText().toString();
//            return true;
//        }
    }

    //스피너에서 이미지 설정!
    public void setSpinnerData(View v, int position){
        clothesUnit = (String)spinner1.getAdapter().getItem(spinner1.getSelectedItemPosition());
        clothesSize = (String)spinner2.getAdapter().getItem(spinner2.getSelectedItemPosition());
        //바로 0번 position이 눌리면 어댑터 추가하자
//        if(spinner1.getSelectedItemPosition()>0){
//            resultSize = (String)spinner1.getAdapter().getItem(spinner1.getSelectedItemPosition());
//        }else {
//            resultSize="";
//        }
        Log.d(TAG, "clothesUnit : " +clothesUnit + ", clothesSize : " +clothesSize);
    }

    //카메라 액티비티 실행
    public void onUseCameraClick() {

        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("clothesUnit", clothesUnit);
        intent.putExtra("clothesSize", clothesSize);

        //이게 어떤걸 클릭했는지에 따라 프리뷰에 뜨는 이미지 설정
        intent.putExtra("clothesImage", getCaptureImage(getCheckedRadioButtonId()));

        //촬영 이미지 클릭하면 결정되!어떤 촬영 이미지를 할건지 + 선택한 이미지 이름 전달
        getCaptureImage(getCheckedRadioButtonId());
        intent.putExtra("clothesCategory", clothesCategory);
        Log.d(TAG, "clothesUnit : " + clothesUnit + ", clothesSize : " + clothesSize + ", clothesCategory : " + clothesCategory);
//        startActivity(intent);
//        finish();
        startActivityForResult(intent, FITTING_RESULT);
//        finish();
    }

    //갤러리 인텐트 시작하고 종료시 REQUEST_GALLERY를 리턴
    private void getGalleryImage(){
        Intent photoPickerIntent = new Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        photoPickerIntent.setType("image/*");
        //크롭처리할지 말지 선택
//        photoPickerIntent.putExtra("crop", "true");
//        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
//        photoPickerIntent.putExtra("outputFormat",
//                Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);
    }

    private Uri getTempUri() {

        //파일객체 생성
        mSaveFile = getOutputMediaFile();
        //정해둔 경로에 파일객체를 만든 다음에 그 객체의 경로를 action_pick에 MediaStore.EXTRA_OUTPUT에 같이 넘겨준다.

       /* //디렉토리
        File mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FittaAPI");
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        mSaveFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg" );*/


//        mSaveFile = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
        Log.d(TAG, "getTempUri() : " + Uri.fromFile(mSaveFile).toString());
//        Bitmap selectedImage = BitmapFactory.decodeFile(mSaveFile.getAbsolutePath());
        //mSaveFile에 bitmap을 파일에 넣어줘
//        SaveBitmapToFileCache(selectedImage);


        return Uri.fromFile(mSaveFile);
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
            //크롭처리를 안한다면 여기서 프리뷰레이아웃을 보여줘서 다시 갤러리 선택하게 하거나 확인 누르게 하거나 해야될것 같다!
            case REQUEST_GALLERY :
                Log.d(TAG, "REQUEST_GALLERY");
//                mSaveFile = getOutputMediaFile();
//                String filePath = Environment.getExternalStorageDirectory() + "/" + TEMP_PHOTO_FILE;
//                Log.d(TAG, "getPath() : " + mSaveFile.getPath()) ;
//                Log.d(TAG, "getAbsolutePath() : " + mSaveFile.getAbsolutePath()) ;

                //이미지 데이터를 비트맵으로 받아온다.
                try {
                    Bitmap image_bitmap  = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    SaveBitmapToFileCache(image_bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ////////////////////////////////////////////////////////////////////////////////////////////////////
                //로딩 다이얼로그
                final DialogLoadingFragment dialog = new DialogLoadingFragment();
                dialog.show(getSupportFragmentManager(), "loading");

                getCaptureImage(getCheckedRadioButtonId());
                //여기서 어떤 사이즈옷인지, 분류할 어떤 데이터인지도 같이 보내줘서 서버에서 처리한다.
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mSaveFile);
                Call call = NetworkManager.getInstance()
                        .getAPI(FittaAPI.class)
                        .uploadImage(requestBody, clothesCategory, clothesSize);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response, Retrofit retrofit) {
                        if (response.isSuccess()) {

                            Message message = (Message) response.body();
                            clothesUrl = message.imageUrl;
                            clothesFeedback = message.clothesFeedback;
                            Log.d(TAG, "response = " + new Gson().toJson(message));

                            Toast.makeText(FittingInfoActivity.this, "파일업로드 성공" + message.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(FittingInfoActivity.this, FittingResultActivity.class);
                            //피드백 결과값을 보내줘야된다.
                            intent1.putExtra("clothesUrl", clothesUrl);
                            intent1.putExtra("clothesFeedback", clothesFeedback);

                            Log.d(TAG, "clothesUrl : " + clothesUrl + ", clothesFeedback : " + clothesFeedback );

                            startActivityForResult(intent1, FITTING_RESULT);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(FittingInfoActivity.this,
                                    "파일업로드 실패는 아닌데 다른 코드",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(FittingInfoActivity.this, "파일업로드 실패",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                ////////////////////////////////////////////////////////////////////////////////////////////////////
//                String imgPath1 = Uri.fromFile(mSaveFile).toString();
                /*Log.i(TAG, "Got image path1: " + filePath);
                Intent intent = new Intent(FittingInfoActivity.this, FittingResultActivity.class);
                intent.putExtra("clothesUrl", filePath);
                intent.putExtra("clothesSize", clothesSize);
                intent.putExtra("clothesUnit", clothesUnit);
                Log.d(TAG, "resultImage : " + getCaptureImage(getCheckedRadioButtonId()) +
                        ", clothesUnit : " + clothesUnit + ", clothesSize : " + clothesSize);
                Log.d(TAG, "filePath : " + filePath + ", clothesSize : " + clothesSize + ", clothesUnit : " + clothesUnit);
                startActivityForResult(intent, FITTING_RESULT);*/
//                startActivity(intent);
                break;
            case FITTING_RESULT:
                int ret = data.getIntExtra("retVal", 12345);
//                String ret = data.getStringExtra("retVal");
                Log.d(TAG, "return msg : "+  ret);
                Intent retIntent = new Intent();
                retIntent.putExtra("retVal", ret);
                setResult(RESULT_OK, retIntent);
                finish();
        }
    }

    //bitmapToFile
    private void SaveBitmapToFileCache(Bitmap bitmap) {

//        File fileCacheItem = new File(strFilePath);
        mSaveFile = getOutputMediaFile();
        OutputStream out = null;

        try {
            mSaveFile.createNewFile();
            out = new FileOutputStream(mSaveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //현재 체크되어있는지
    public int getCheckedRadioButtonId() {
        if ( mBtnCurrentRadio != null ) {
//            mBtnCurrentRadio.getText();
            return mBtnCurrentRadio.getId();
        }
        return -1;
    }


//분리된 레이아웃들의 리스너를 주기위해서 하는건데...되려나
    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            final RadioButton mBtnRadio = (RadioButton) v;

            // select only one radio button at any given time
            if (mBtnCurrentRadio != null) {
                mBtnCurrentRadio.setChecked(false);
            }
            mBtnRadio.setChecked(true);
            mBtnCurrentRadio = mBtnRadio;


           /* switch (v.getId()){
                case R.id.top1 :
                case R.id.top2 :
                case R.id.top3 :
                case R.id.top4 :
                case R.id.top5 :
                case R.id.top6 :
                case R.id.top7 :
                case R.id.top8 :
                case R.id.top9 :
                    if(tableLayoutBottom.getLayoutState())
                        tableLayoutBottom.clearRadioButton();
                    if (tableLayoutEtc.getLayoutState())
                        tableLayoutEtc.clearRadioButton();
//                    tableLayoutBottom.clearRadioButton();
//                    tableLayoutEtc.clearRadioButton();
                    break;
                case R.id.bottom1 :
                case R.id.bottom2 :
                case R.id.bottom3 :
                case R.id.bottom4 :
                case R.id.bottom5 :
                case R.id.bottom6 :
                    if(tableLayoutTop.getLayoutState())
                        tableLayoutTop.clearRadioButton();
                    if (tableLayoutEtc.getLayoutState())
                        tableLayoutEtc.clearRadioButton();

//                    tableLayoutTop.clearRadioButton();
//                    tableLayoutEtc.clearRadioButton();
                    break;
                case R.id.etc1:
                case R.id.etc2:
                case R.id.etc3:
                case R.id.etc4:
                case R.id.etc5:
                case R.id.etc6:
                    if(tableLayoutTop.getLayoutState())
                        tableLayoutTop.clearRadioButton();
                    if (tableLayoutBottom.getLayoutState())
                        tableLayoutBottom.clearRadioButton();
//                    tableLayoutTop.clearRadioButton();
//                    tableLayoutBottom.clearRadioButton();
                    break;
            }*/
        }
    }

}
