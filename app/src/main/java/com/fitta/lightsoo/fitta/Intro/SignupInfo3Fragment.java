package com.fitta.lightsoo.fitta.Intro;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fitta.lightsoo.fitta.Data.Fitta;
import com.fitta.lightsoo.fitta.Data.Message;
import com.fitta.lightsoo.fitta.Dialog.DialogSignupFragment;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//남자 상세정보입력
public class SignupInfo3Fragment extends Fragment {

    private static final String TAG = "SignupInfo3Fragment";

    RelativeLayout background_signup2 ;
    private String age="", height="", weight="";
    //가슴둘레(cm), 허리둘레(inch)
    private String top="", bottom="";
    private EditText et_top, et_bottom;
    private Button btn_post;

    Fragment result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info3, container, false);
        init(view);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo();
                top = et_top.getText().toString();
                bottom = et_bottom.getText().toString();
                Log.d(TAG,"age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom);
                //입력받은 값들을 이제 서버에 보내줘야해 그리고 나서

                //로딩 다이얼로그
                final DialogSignupFragment dialog = new DialogSignupFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "loading");
                //서버에 사용자의 정보를 입력한다음 디비에 저장하고
                //해당되는 아바타 이미지url을 리턴받는다.
                Fitta fitta = new Fitta("남", age, height, weight, top, bottom) ;

                Call call = NetworkManager.getInstance().getAPI(FittaAPI.class).signup(fitta);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response, Retrofit retrofit) {
                        if (response.isSuccess()) {//이전에 가입되었던 사람이라면 OK,
                            Toast.makeText(getActivity(), "서버전송 성공", Toast.LENGTH_SHORT).show();
                            Message msg = (Message)response.body();
                            Log.d(TAG, msg.toString());

                            Bundle bundle = new Bundle();
//                            bundle.putString("age", age);
//                            bundle.putString("weight", weight);
//                            bundle.putString("height", height);
//                            bundle.putString("top", top);
//                            bundle.putString("bottom", bottom);
                            bundle.putString("url", msg.url);

                            result = new SignupResultFragment();
                            result.setArguments(bundle);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, result).commit();

                        /*Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();*/

                            dialog.dismiss();


                        } else {
                            //아니라면 not registered

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(), "서버전송 실패 : " + result, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


               /* mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "로딩바 테스트 ");

                        Bundle bundle = new Bundle();
                        bundle.putString("age",age);
                        bundle.putString("weight", weight);
                        bundle.putString("height", height);
                        bundle.putString("top", top);
                        bundle.putString("bottom", bottom);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, result).commit();


                        *//*Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();*//*


                        dialog.dismiss();
                    }
                }, 4500);*/


//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
            }
        });
        return view;
    }


    public void init(View view){
        background_signup2 = (RelativeLayout)view.findViewById(R.id.background_signup2);
        Glide.with(getContext())
                .load(R.drawable.background_signup2)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                background_signup2.setBackground(drawable);
            }
        });

        btn_post = (Button)view.findViewById(R.id.btn_post);

        et_top = (EditText)view.findViewById(R.id.et_top);
        et_bottom = (EditText)view.findViewById(R.id.et_female_bottom);



    }

    public boolean preInspection(){
        if(TextUtils.isEmpty(et_top.getText().toString()) ||
                TextUtils.isEmpty(et_bottom.getText().toString())){
            return false;
        }else{

            top = et_top.getText().toString();
            bottom = et_bottom.getText().toString();
            return true;
        }
    }

    //signUpInfo1으로 받은 정보!
    public void setInfo(){
        age = getArguments().getString("age");
        height = getArguments().getString("height");
        weight = getArguments().getString("weight");
    }



}
