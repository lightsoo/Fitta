package com.fitta.lightsoo.fitta.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fitta.lightsoo.fitta.Adapter.ClothesAdapter;
import com.fitta.lightsoo.fitta.Data.ClothesItems;
import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.R;
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
    private ClothesAdapter topAdapter, bottomAdapter,etcAdapter;
    Button btn_top, btn_bottom, btn_etc, btn_like;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fittingroom, container, false);
        init(view);
        getClothes();

        //리스트뷰에 어댑터 세팅
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(topAdapter);
            }
        });
        btn_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(bottomAdapter);
            }
        });
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(etcAdapter);
            }
        });
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_clothes.setAdapter(topAdapter);
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

        btn_top = (Button)view.findViewById(R.id.btn_lv_top);
        btn_bottom = (Button)view.findViewById(R.id.btn_lv_bottom);
        btn_etc = (Button)view.findViewById(R.id.btn_lv_etc);
        btn_like = (Button)view.findViewById(R.id.btn_lv_like);



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