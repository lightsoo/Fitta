package com.fitta.lightsoo.fitta.Camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.Activity.FittingResultActivity;
import com.fitta.lightsoo.fitta.Data.Message;
import com.fitta.lightsoo.fitta.Dialog.DialogLoadingFragment;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
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

public class CameraActivity extends AppCompatActivity implements CameraPreview.OnCameraStatusListener {

    private static final String TAG = "CameraActivity";
    private CameraPreview cameraPreview;
    private ImageView captureClothes, capturedImage;    //프리뷰에 나오는 옷이미지, 촬영결과 이미지
    private File mSaveFile;
    private String cameraPath;
    RelativeLayout takePhotoLayout, photoResultLayout;

    private static final int REQUEST_CROP = 2;
    private static final int REQUEST_CAMERA = 3;
    private static final int FITTING_RESULT = 10;

    private String clothesUnit = ""; //단위 결과, 이거는 분류표!
    //서버에 전송할 데이터
    private String clothesSize = ""; //사이즈, 이것만 서버에 보낸다.
    private String clothesCategory = ""; //빅데이터 분류용 어떤 이미지인지, 서버에만 보내주면괜찮아
    //서버로 전송받을 데이터 + FittingResultActivity로 전달
    private String clothesUrl = ""; //이미지 결과
    private String clothesFeedback = "";//피드백 결과

    private int clothesImage = 0;//촬영 이미지 아이디값 ex) R.drawable.top1 ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "===onCreate()===");
        setContentView(R.layout.activity_camera);

        Intent intent = new Intent(getIntent());
        clothesSize = intent.getExtras().getString("clothesSize");
        clothesUnit = intent.getExtras().getString("clothesUnit");

        clothesImage = intent.getExtras().getInt("clothesImage");

        clothesCategory = intent.getExtras().getString("clothesCategory");

        Log.d(TAG, "clothesSize : " + clothesSize + ", clothesUnit : " + clothesUnit + ", clothesImage : " + clothesImage);

        //초기화할때 이미지경로를 넣어주자
        initCameraPreview(clothesImage);
    }

    // Show the camera view on the activity
    private void initCameraPreview(int clothesImage) {
        Log.d(TAG, "===initCameraPreview()===");
        takePhotoLayout =(RelativeLayout)findViewById(R.id.take_photo_layout);//기본화면
        photoResultLayout = (RelativeLayout)findViewById(R.id.photo_result_layout);//촬영결과 화면

        capturedImage = (ImageView) findViewById(R.id.capturedImage);//사진찍는 버튼의 이미지뷰
        cameraPreview =(CameraPreview)findViewById(R.id.cameraPreview); //카메라 surfaceview
        cameraPreview.setOnCameraStatusListener(this);

        captureClothes = (ImageView)findViewById(R.id.clothes);

        Glide.with(getApplicationContext())
                .load(clothesImage)
                .crossFade()
//                .centerCrop()
//                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(captureClothes);

    }

    //촬영이후 결과화면에서 결과화면에서 재촬영 버튼 클릭스
    private void showTakePhotoLayout(){
        takePhotoLayout.setVisibility(View.VISIBLE);
        photoResultLayout.setVisibility(View.GONE);

    }
    public void reTakePhoto(View button){
        showTakePhotoLayout();
    }

    //카메라에서 촬영버튼 눌럿을때, 화면 전환!
    private void showPhotoResultLayout(){
        Log.d(TAG, "===showPhotoResultLayout()===");
        //카메라 촬영 callback()
        takePhotoLayout.setVisibility(View.GONE);
        photoResultLayout.setVisibility(View.VISIBLE);
        cameraPreview.start();  //카메라는 계속 돌린다.
    }

    //단순히 화면 전환만 되고있어.
    public void takePhoto(View view) {
        Log.d(TAG, "===takePhoto()===");
        if(cameraPreview != null){
            cameraPreview.takePicture();
        }
    }

    @Override
    public void onCameraStopped(byte[] data) {
        Log.i(TAG, "===onCameraStopped===");
        //지정해둔 디렉토리에 현재 시간으로 파일객체 생성
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        mSaveFile = getOutputMediaFile();
        SaveBitmapToFileCache(rotatedBitmap);
        cameraPath = mSaveFile.getAbsolutePath();

        /*//만든 파일의 절대 경로리턴턴 + 파일객체생성후 data저장
        cameraPath = savePictureToFileSystem(data);*/
        Log.d(TAG, "cameraPath : " + cameraPath);

        //한번 촬영이후 찍힌이미지를 출력하기위해 저장을 한다. savePicureToFileSystem
        Glide.with(getApplicationContext())
                .load(cameraPath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(capturedImage);
        showPhotoResultLayout();
    }

    //크롭처리를 하지않고 원본파일을 넘겨준다.
    public void getCameraImage(View button){

        //로딩 다이얼로그
        final DialogLoadingFragment dialog = new DialogLoadingFragment();
        dialog.show(getSupportFragmentManager(), "loading");

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

//                    clothesFeedback = message.clothesFeedback;


                    Log.d(TAG, "response = " + new Gson().toJson(message));
                    Toast.makeText(CameraActivity.this, "파일업로드 성공인경우code(200~300)" + message.getMsg(),
                            Toast.LENGTH_SHORT).show();

//                    Log.i(TAG, "Got image path1: " + imgPath1);
                    Intent intent1 = new Intent(CameraActivity.this, FittingResultActivity.class);
                    intent1.putExtra("clothesUrl", clothesUrl);
                    intent1.putExtra("clothesFeedback", clothesFeedback);

                    Log.d(TAG, "clothesUrl : " + clothesUrl + ", clothesFeedback : " + clothesFeedback);
                    startActivityForResult(intent1, FITTING_RESULT);

                    dialog.dismiss();
                } else {
                    Toast.makeText(CameraActivity.this, "파일업로드 실패는 아닌데 다른 코드..",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    //사진촬영 이후 결과화면에서 확인 누른 다면 이후, 사진 크롭 처리로 이동할 함수
    public void cropImage(View button){
        try {
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), cameraPath, "카메라 이미지", "기존 이미지");
            Uri photouri = Uri.parse(url);
            Log.d(TAG,"photouri : " + photouri);
            //ContentResolver가 처리할수있는 value들을 저장하는데 사용
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.ORIENTATION, 90);
            //뭔지 잘모르겟지만, URL의 value로 대체한다
            getContentResolver().update(photouri, values, null, null);

            if(photouri != null){
                Intent photoPickerIntent = new Intent(
                        "com.android.camera.action.CROP", photouri);
                photoPickerIntent.putExtra("scale", false);
                photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mSaveFile));
                photoPickerIntent.putExtra("outputFormat",
                        Bitmap.CompressFormat.JPEG.toString());
                startActivityForResult(photoPickerIntent, REQUEST_CROP);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //사용하려면 mSaveFile을 static으로 바꿔야해
//    private static String savePictureToFileSystem(byte[] data) {
//        mSaveFile = getOutputMediaFile();
//        saveToFile(data, mSaveFile);
//
//        return mSaveFile.getAbsolutePath();
//    }

    public void close(View view) {
        finish();
    }

    //crop이미지 처리이후에 reqCode를 통해서 CROP처리된 이미지를 받는다!!!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //액티비티 결과가 이상는경우
        if(resultCode != RESULT_OK){return;}
        switch (requestCode){
            //여기서 크롭이미지 처리된걸 받아온다
            case  REQUEST_CROP :
                //사용하기 누르면 여기로 온다.
                Log.d(TAG, "REQUEST_CROP");
                //여기서 이미지경로를 받아와서 FittingResultActivity로 이동한다.
                if ( resultCode == RESULT_OK) {
                    String imgPath = Uri.fromFile(mSaveFile).toString();
                    Log.i(TAG, "Got image path: " + imgPath);
                    Intent intent = new Intent(CameraActivity.this, FittingResultActivity.class);
                    intent.putExtra("clothesUrl", imgPath);
                    intent.putExtra("clothesSize", clothesSize);
                    intent.putExtra("clothesUnit", clothesUnit);
                    Log.d(TAG, "filePath : " + imgPath + ", clothesSize : " + clothesSize + ", clothesUnit : " + clothesUnit);
                    startActivityForResult(intent , FITTING_RESULT);
                } else if (resultCode == RESULT_CANCELED) {
                    Log.i(TAG,"User didn't take an image");
                }
//                finish();
                break;

            case FITTING_RESULT:
//                String ret = data.getStringExtra("retVal");
                int ret = data.getIntExtra("retVal", 12345);
                Log.d(TAG, "return msg : "+  ret);
                Intent retIntent = new Intent();
                retIntent.putExtra("retVal", ret);
                setResult(RESULT_OK, retIntent);
                finish();
                break;
        }
    }

    //bitmapToFile
    private void SaveBitmapToFileCache(Bitmap bitmap) {

//        File fileCacheItem = new File(strFilePath);
        mSaveFile = getOutputMediaFile();

        OutputStream out = null;

        try
        {
            mSaveFile.createNewFile();
            out = new FileOutputStream(mSaveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
