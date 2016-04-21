package com.fitta.lightsoo.fitta.Intro;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.Toast;

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

      Handler mHandler = new Handler(Looper.getMainLooper());

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
                //여기는 선택사항이니깐 나중에 입력해도되
                //editText에 빈칸이 있다면!!
//                if(!preInspection()){
//                    Toast.makeText(getActivity(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
//                }else{
//
//                }
                setInfo();
                top = et_top.getText().toString();
                bottom = et_bottom.getText().toString();
                Log.d(TAG,"age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom);
                //입력받은 값들을 이제 서버에 보내줘야해 그리고 나서


                //로딩 다이얼로그
                final DialogSignupFragment dialog = new DialogSignupFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "loading");

                Fitta fitta = new Fitta(age, height, weight, top, bottom) ;

                Call call = NetworkManager.getInstance().getAPI(FittaAPI.class).signup(fitta);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response, Retrofit retrofit) {
                        if (response.isSuccess()) {//이전에 가입되었던 사람이라면 OK,
                            Toast.makeText(getActivity(), "서버전송 성공", Toast.LENGTH_SHORT).show();

                            Message msg = (Message)response.body();
                            Log.d(TAG, msg.toString());

                            Bundle bundle = new Bundle();
                            bundle.putString("age", age);
                            bundle.putString("weight", weight);
                            bundle.putString("height", height);
                            bundle.putString("top", top);
                            bundle.putString("bottom", bottom);
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
        btn_post = (Button)view.findViewById(R.id.btn_post);

        et_top = (EditText)view.findViewById(R.id.et_top);
        et_bottom = (EditText)view.findViewById(R.id.et_bottom);
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
