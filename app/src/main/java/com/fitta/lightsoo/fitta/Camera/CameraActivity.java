package com.fitta.lightsoo.fitta.Camera;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.Activity.FittingResultActivity;
import com.fitta.lightsoo.fitta.R;

import java.io.File;
import java.io.FileNotFoundException;

import static com.fitta.lightsoo.fitta.Util.MediaHelper.getOutputMediaFile;
import static com.fitta.lightsoo.fitta.Util.MediaHelper.saveToFile;

public class CameraActivity extends Activity implements CameraPreview.OnCameraStatusListener {

    private static final int TEST = 10;
    private static final String TAG = "CameraActivity";
    private CameraPreview cameraPreview;
    private ImageView Clothes, capturedImage;
    private File mSaveFile;
    private String cameraPath;
    RelativeLayout takePhotoLayout, photoResultLayout;

    private static final int REQUEST_CROP = 2;
    private static final int REQUEST_CAMERA = 3;

    private static final int FITTING_RESULT = 10;
    private String clothesSize = ""; //사이즈
    private String clothesUnit = ""; //단위 결과
    private int clothesImage = 0;//이미지 아이디값인가 R.drawable.top1 ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "===onCreate()===");
        setContentView(R.layout.activity_camera);

        Intent intent = new Intent(getIntent());
        clothesSize = intent.getExtras().getString("clothesSize");
        clothesUnit = intent.getExtras().getString("clothesUnit");
        clothesImage = intent.getExtras().getInt("clothesImage");

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

        Clothes = (ImageView)findViewById(R.id.clothes);

        Glide.with(getApplicationContext())
                .load(clothesImage)
                .crossFade()
                .centerCrop()
//                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(Clothes);

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
        Log.i("TAG", "===onCameraStopped===");
        //지정해둔 디렉토리에 현재 시간으로 파일객체 생성
        mSaveFile = getOutputMediaFile();
        //파일저장
        saveToFile(data, mSaveFile);

        //만든 파일의 절대 경로
        cameraPath = savePictureToFileSystem(data);
//        setResult(cameraPath);

        Log.d(TAG, "cameraPath : " + cameraPath);

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
//        Uri fileUri = Uri.fromFile(mSaveFile);
        Log.d(TAG, "REQUEST_CAMERA");
        String imgPath1 = Uri.fromFile(mSaveFile).toString();
        Log.i(TAG, "Got image path1: " + imgPath1);
        Intent intent1 = new Intent(CameraActivity.this, FittingResultActivity.class);
        intent1.putExtra("clothesUrl", imgPath1);
        intent1.putExtra("clothesSize", clothesSize);
        intent1.putExtra("clothesUnit", clothesUnit);
        Log.d("data ", "filePath : " + imgPath1 + ", clothesSize : " + clothesSize + ", clothesUnit : " + clothesUnit);
        startActivityForResult(intent1, FITTING_RESULT);
        finish();

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

    private static String savePictureToFileSystem(byte[] data) {
        File file = getOutputMediaFile();
        saveToFile(data, file);
        return file.getAbsolutePath();
    }

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
            //원본파일 사용시
            case REQUEST_CAMERA :
                Log.d(TAG, "REQUEST_CAMERA");
                String imgPath1 = Uri.fromFile(mSaveFile).toString();
                Log.i(TAG, "Got image path1: " + imgPath1);
                Intent intent1 = new Intent(CameraActivity.this, FittingResultActivity.class);
                intent1.putExtra("clothesUrl", imgPath1);
                intent1.putExtra("clothesSize", clothesSize);
                intent1.putExtra("clothesUnit", clothesUnit);
                Log.d("data ", "filePath : " + imgPath1 + ", clothesSize : " + clothesSize + ", clothesUnit : " + clothesUnit);
                startActivityForResult(intent1, FITTING_RESULT);
                finish();
                break;


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
                    Log.d("data ", "filePath : " + imgPath + ", clothesSize : " + clothesSize + ", clothesUnit : " + clothesUnit);
                    startActivityForResult(intent , FITTING_RESULT);
//                    startActivity(intent);
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
}
