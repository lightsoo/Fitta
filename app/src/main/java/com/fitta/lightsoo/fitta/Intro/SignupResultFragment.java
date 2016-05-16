package com.fitta.lightsoo.fitta.Intro;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LG on 2016-04-21.
 */
public class SignupResultFragment extends Fragment{

    private static final String TAG = "SignupResultFragment";
    private Button btn_start;
    private ImageView iv_avatar;
    //이거 url을 프로퍼티 매니저로 아바타를 대신할예정이다.
    //신체값을 보냈을때 맞는 아바타를 받아서 그거를 쉐어드프리퍼런스로 저장!
    private String url="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_result, container, false);
        init(view);
        //회원가입후 서버로부터 이미지url을 받아온다
        setInfo();

        //아바타설정
       /* Glide.with(getContext())
                .load(PropertyManager.getInstance().getUserAvatar())
//                .load(url)
//                .load(R.drawable.body100cf)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_avatar);*/

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //종료하고 메인액티비티로 이동!
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    public void init(View view){
        btn_start = (Button)view.findViewById(R.id.btn_start2);
        iv_avatar = (ImageView)view.findViewById(R.id.iv_avatar);
        //텍스트뷰를 초기화해줘서 화면에 출력해줘야된다.
    }

    //signUpInfo2,3에서 서버로 데이터를 전송한다음 아바타 url을 받아서 쓴다!
    public void setInfo(){
        url = getArguments().getString("url");

         }


                 //url to bitmap

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
        }catch(Exception e) {
            e.printStackTrace();
            Log.d(TAG,"getBitmap ERROR");
            return null;
        }finally {
            Log.d(TAG,"getBitmap finally");

            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    //저장한 이미지를 불러오는거야
    private void loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            ImageView img=(ImageView)findViewById(R.id.imgPicker);
//            img.setImageBitmap(b);

            Glide.with(getContext())
                    .load(b)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(iv_avatar);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }







}
