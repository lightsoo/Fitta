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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fitta.lightsoo.fitta.Data.Fitta;
import com.fitta.lightsoo.fitta.Data.Message;
import com.fitta.lightsoo.fitta.Dialog.DialogLoadingFragment;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.Manager.PropertyManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//남자 상세정보입력
public class SignupInfo3Fragment extends Fragment {

    private static final String TAG = "SignupInfo3Fragment";
    ScrollView background_signup3 ;
//    RelativeLayout background_signup2 ;

    private TextView tv_signup_ex;
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
                btn_post.setEnabled(false);
//                setInfo();
                age = getArguments().getString("age");
                height = getArguments().getString("height");
                weight = getArguments().getString("weight");

                top = et_top.getText().toString();
                bottom = et_bottom.getText().toString();
                Log.d(TAG,"age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom);
                //입력받은 값들을 이제 서버에 보내줘야해 그리고 나서

                //로딩 다이얼로그
                final DialogLoadingFragment dialog = new DialogLoadingFragment();
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
                            //여기서개인치수를 프로퍼티로 넣어야될듯 하다...
                            Message msg = (Message)response.body();
                            Log.d(TAG,"서버로부터 받은 이미지url : "+ msg.imageUrl);
//                            Log.d(TAG, msg.toString());

                            Bundle bundle = new Bundle();
//                            bundle.putString("age", age);
//                            bundle.putString("weight", weight);
//                            bundle.putString("height", height);
//                            bundle.putString("top", top);
//                            bundle.putString("bottom", bottom);

                            bundle.putString("url", msg.imageUrl);

                            //이거를 로그인하고 나서 넣자
                            //ex) msg.avatar를 setUserAvatar()여기에 파라미터로주자
                            PropertyManager.getInstance().setUserAvatar(R.drawable.avatar110af);

                            //테스트용
//                            PropertyManager.getInstance().setUserAvatar2(msg.url);


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
//        background_signup2 = (RelativeLayout)view.findViewById(R.id.background_signup2);

        background_signup3 = (ScrollView)view.findViewById(R.id.background_signup3);
        Glide.with(getContext())
                .load(R.drawable.background_signup)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                background_signup3.setBackground(drawable);
            }
        });
        tv_signup_ex = (TextView)view.findViewById(R.id.tv_signup_ex);
        tv_signup_ex.setText("이내용은 아바타의 정확도를 위한 입력 사항이며\n옵션 설정에서 얼마든지 수정할 수 있습니다.\n잘 모르는 사이즈는 나중에 작성해주세요!");

        btn_post = (Button)view.findViewById(R.id.btn_post);
        btn_post.setEnabled(true);
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

    //signUpInfo1으로 받은 정보
    public void setInfo(){
        age = getArguments().getString("age");
        height = getArguments().getString("height");
        weight = getArguments().getString("weight");
    }



}
