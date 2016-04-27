package com.fitta.lightsoo.fitta.MainFragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fitta.lightsoo.fitta.Adapter.ClothesAdapter;
import com.fitta.lightsoo.fitta.Data.ClothesItems;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittaAPI;
import com.fitta.lightsoo.fitta.RestAPI.FittingRoomAPI;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FittingRoomFragment extends Fragment {

    private static final String TAG = "FittingRoomFragment";

    ListView lv_top, lv_bottom, lv_etc, lv_clothes;
    View view;
    private ClothesAdapter topAdapter, bottomAdapter,etcAdapter, likeAdapter;
    RadioButton btn_top, btn_bottom, btn_etc, btn_like;
    Button btn_refresh, btn_add_like;
    private ImageView iv_fittingroom_avatar,  iv_fittingroom_top, iv_fittingroom_bottom, iv_fittingroom_etc, iv_fittingroom_like;
    //입력된 url
    private String fittingroom_top, fittingroom_bottom, fittingroom_etc;
    //1 : top, 2 : bottom, 3 : etc, 4 : like 플래그를 둬서 리스트뷰 클릭시 구별하자!!!
    private int cntAdapterFlag=1;

    RelativeLayout layoutPlace ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fittingroom, container, false);
        init(view);
        //서버로부터 의상 이미지를 받는다.
        getClothes();

        //리스트뷰에 어댑터 세팅
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(topAdapter);
                cntAdapterFlag =1;
            }
        });
        btn_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(bottomAdapter);
                cntAdapterFlag = 2;
            }
        });
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(etcAdapter);
                cntAdapterFlag =3;
            }
        });
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(likeAdapter);
                cntAdapterFlag =4 ;
            }
        });


        //리스트뷰에 아이템 클릭시
        lv_clothes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object data = lv_clothes.getItemAtPosition(position);
                if (data instanceof String) {
                    if (cntAdapterFlag == 1) {
                        Toast.makeText(getActivity(), "top : " + (String) data, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "top의 positon : " + position);
                        setFitting(iv_fittingroom_top, (String) data);
                        fittingroom_top=(String)data;
                        setClearFitting();
                    } else if (cntAdapterFlag == 2) {
                        Toast.makeText(getActivity(), "bottom : " + (String) data, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "bottom의 positon : " + position);
                        setFitting(iv_fittingroom_bottom, (String) data);
                        fittingroom_bottom = (String)data;
                        setClearFitting();
                    } else if (cntAdapterFlag == 3) {
                        Toast.makeText(getActivity(), "etc : " + (String) data, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "etc의 positon : " + position);
                        setFitting(iv_fittingroom_etc, (String) data);
                        fittingroom_etc = (String)data;
                        setClearFitting();
                    } else if (cntAdapterFlag == 4) {
                        //1,2,3번은 중복이 되는데 4번을 누를경우에는 이전에 입힌게 리셋되도록 하자
                        Toast.makeText(getActivity(), "like : " + (String) data, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "like의 positon : " + position);
                        setFitting(iv_fittingroom_like, (String) data);
                        setClearFitting();
                    } else {
                        Toast.makeText(getActivity(), "Header : " + (String) data, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_fittingroom_top.setImageResource(0);
                iv_fittingroom_bottom.setImageResource(0);
                iv_fittingroom_etc.setImageResource(0);
                iv_fittingroom_like.setImageResource(0);
            }
        });

        btn_add_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call call = NetworkManager.getInstance()
                        .getAPI(FittaAPI.class)
//                        .uploadLikeImage(like_top,like_bottom,like_etc);
                        .uploadLikeImage2(fittingroom_top, fittingroom_bottom,fittingroom_etc);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response, Retrofit retrofit) {

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        return view;
    }


    public void init(View view){

//        lv_top = (ListView) view.findViewById(R.id.lv_top);
//        lv_bottom = (ListView) view.findViewById(R.id.lv_bottom);
//        lv_etc = (ListView) view.findViewById(R.id.lv_etc);

        lv_clothes = (ListView)view.findViewById(R.id.lv_clothes);

        topAdapter = new ClothesAdapter();
        bottomAdapter = new ClothesAdapter();
        etcAdapter = new ClothesAdapter();

        btn_top = (RadioButton)view.findViewById(R.id.btn_lv_top);
        btn_bottom = (RadioButton)view.findViewById(R.id.btn_lv_bottom);
        btn_etc = (RadioButton)view.findViewById(R.id.btn_lv_etc);
        btn_like = (RadioButton)view.findViewById(R.id.btn_lv_like);

        btn_refresh = (Button)view.findViewById(R.id.btn_refresh);
        btn_add_like = (Button)view.findViewById(R.id.btn_add_like);

        iv_fittingroom_avatar = (ImageView)view.findViewById(R.id.iv_fittingroom_avatar);
        Glide.with(getContext())
                .load(R.drawable.body100cf)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_fittingroom_avatar);


        iv_fittingroom_top = (ImageView)view.findViewById(R.id.iv_fittingroom_top);
        iv_fittingroom_bottom = (ImageView)view.findViewById(R.id.iv_fittingroom_bottom);
        iv_fittingroom_etc = (ImageView)view.findViewById(R.id.iv_fittingroom_etc);
        iv_fittingroom_like = (ImageView)view.findViewById(R.id.iv_fittingroom_like);

        layoutPlace = (RelativeLayout)view.findViewById(R.id.background);
        Glide.with(getContext())
                .load(R.drawable.avatar_background)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                layoutPlace.setBackground(drawable);
            }
        });

    }

    public void setClearFitting(){
        if(cntAdapterFlag==4){
            iv_fittingroom_top.setImageResource(0);fittingroom_top="";
            iv_fittingroom_bottom.setImageResource(0);fittingroom_bottom="";
            iv_fittingroom_etc.setImageResource(0);fittingroom_etc="";
            iv_fittingroom_like.setImageResource(0);
        }else {
            iv_fittingroom_like.setImageResource(0);
        }
    }

    //url을 해당 이미지뷰에 출력한다.!
    public void setFitting(ImageView iv, String url){
        Glide.with(getContext())
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv);
    }

    public void getClothes(){
        //리턴받을 데이터형을Call<리턴형>인거야
        Call<ClothesItems> call = NetworkManager.getInstance().getAPI(FittingRoomAPI.class).getClothes();
        call.enqueue(new Callback<ClothesItems>() {
            @Override
            public void onResponse(Response<ClothesItems> response, Retrofit retrofit) {

                //Call에 안넣었으면 타입캐스팅을 해줘야되
                //ClothesItems clothesItems = (ClotheItems)response.body();
                ClothesItems clothesItems = response.body();

                List<String> clotheTop = clothesItems.clotheTop;
                List<String> clotheBottom = clothesItems.clotheBottom;
                List<String> clotheEtc = clothesItems.clotheEtc;

                Log.d("please", "response = " +clotheTop );
                Log.d("please", "response = " +clotheBottom);
                Log.d("please", "response = " +clotheEtc);

                /*for(int i = 0; i<response.body().clotheTop.size();i++){
                    Log.d("please", "clotheTop[" +i+"] = " +response.body().clotheTop.get(i));
                    String temp = response.body().clotheTop.get(i);
                    topAdapter.add(temp);
//                    topAdapter.add(response.body().clotheTop.get(i));
                }*/

//                Log.d("please", "response = " +response.body().clotheTop);
//                Log.d("please", "response = " +response.body().clotheBottom);
//                Log.d("please", "response = " +response.body().clotheEtc);

                //한번의 통신으로 어댑터들에 데이터를 저장해둬 그리고
                topAdapter.addAll(clotheTop);
                bottomAdapter.addAll(clotheBottom);
                etcAdapter.addAll(clotheEtc);

                //리스트뷰 1개만 써서  '상의', '하의', '원피스', '즐겨찾기' 클릭시 어댑터랑 set해주는걸로 하자
                //이걸 이해해야되!!! 한개만 써서 버튼 누를때마다 어댑터를 설정해주자
                lv_clothes.setAdapter(topAdapter);
//                lv_top.setAdapter(topAdapter);
//                lv_bottom.setAdapter(bottomAdapter);
//                lv_etc.setAdapter(etcAdapter);

//                topAdapter.addAll(response.body().clotheTop);
//                bottomAdapter.addAll(response.body().clotheBottom);
//                etcAdapter.addAll(response.body().clotheEtc);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("network : ", t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}