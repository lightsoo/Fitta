package com.fitta.lightsoo.fitta.Intro;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import retrofit.Response;
import retrofit.Retrofit;

//여자 상세정보입력
public class SignupInfo2Fragment extends Fragment {

    private static final String TAG = "SignupInfo2Fragment";

    ScrollView background_signup2 ;
//    RelativeLayout background_signup2 ;

    private TextView tv_signup_ex;


    private Spinner spinner;
    private String[] spinnerItem;
    private ArrayAdapter arrayAdapter;

    private String age="", height="", weight="";
    //가슴둘레(컵), 허리둘레(inch)
    private String top="", bottom="";

    private EditText et_bottom;
    private Button btn_post;
    Fragment result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info2, container, false);
        init(view);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = spinner.getSelectedItemPosition();
//                Toast.makeText(getActivity(), "선택번호 : " + sid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_post.setEnabled(false);
//              setInfo();
                age = getArguments().getString("age");
                height = getArguments().getString("height");
                weight = getArguments().getString("weight");

                top = (String)spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
                bottom = et_bottom.getText().toString();
                Log.d(TAG, "age : " + age + ", height : " + height + ", weight : " + weight + ", top : " + top + ", bottom : " + bottom);

                //로딩 다이얼로그
                final DialogLoadingFragment dialog = new DialogLoadingFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "loading");
                //서버에 사용자의 정보를 입력한다음 디비에 저장하고
                //해당되는 아바타 이미지url을 리턴받는다.
                Fitta fitta = new Fitta("여", age, height, weight, top, bottom) ;

                Call call = NetworkManager.getInstance().getAPI(FittaAPI.class).signup(fitta);
                call.enqueue(new retrofit.Callback() {
                    @Override
                    public void onResponse(Response response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                        //이전에 가입되었던 사람이라면 OK,
                            Toast.makeText(getActivity(), "서버전송 성공", Toast.LENGTH_SHORT).show();

                            //여기서개인치수를 프로퍼티로 넣어야될듯 하다...
                            Message msg = (Message)response.body();
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


                            //결과화면을 보여준다
                            result = new SignupResultFragment();
                            result.setArguments(bundle);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, result).commit();

//                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                            startActivity(intent);
//                            getActivity().finish();

                            dialog.dismiss();


                        } else {
                            //아니라면 not registered

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(),"서버전송 실패 : "+result,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    public void init(View view){
        background_signup2 = (ScrollView)view.findViewById(R.id.background_signup2);
        Glide.with(getContext())
                .load(R.drawable.background_signup)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                background_signup2.setBackground(drawable);
            }
        });

        tv_signup_ex = (TextView)view.findViewById(R.id.tv_signup_ex);
        tv_signup_ex.setText("이내용은 아바타의 정확도를 위한 입력 사항이며\n옵션 설정에서 얼마든지 수정할 수 있습니다.\n잘 모르는 사이즈는 나중에 작성해주세요!");
        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinnerItem = new String[]{"A", "B", "C", "D", "E"};
        //컨텍스트, 스피너 커스텀, contents
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, spinnerItem);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        et_bottom = (EditText)view.findViewById(R.id.et_female_bottom);
        btn_post = (Button)view.findViewById(R.id.btn_post);
        btn_post.setEnabled(true);

//        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    //signUpInfo1으로 받은 정보
    public void setInfo(){
        age = getArguments().getString("age");
        height = getArguments().getString("height");
        weight = getArguments().getString("weight");
    }
}
