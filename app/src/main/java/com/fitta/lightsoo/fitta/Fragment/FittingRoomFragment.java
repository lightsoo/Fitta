package com.fitta.lightsoo.fitta.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by LG on 2016-03-17.
 */
public class FittingRoomFragment extends Fragment {
    ListView lv_top, lv_bottom, lv_etc;
    View view;
    private ClothesAdapter topAdapter, bottomAdapter,etcAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fittingroom, container, false);
        init();
        getClothes();
        return view;
    }

    public void init(){
        lv_top = (ListView) view.findViewById(R.id.lv_top);
        lv_bottom = (ListView) view.findViewById(R.id.lv_bottom);
        lv_etc = (ListView) view.findViewById(R.id.lv_etc);
        topAdapter = new ClothesAdapter();
        bottomAdapter = new ClothesAdapter();
        etcAdapter = new ClothesAdapter();
    }

    public void getClothes(){
        //리턴받을 데이터형인거야
        Call<ClothesItems> call = NetworkManager.getInstance().getAPI(FittingRoomAPI.class).getClothes();
        call.enqueue(new Callback<ClothesItems>() {
            @Override
            public void onResponse(Response<ClothesItems> response, Retrofit retrofit) {

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


                topAdapter.addAll(clotheTop);
                bottomAdapter.addAll(clotheBottom);
                etcAdapter.addAll(clotheEtc);

                lv_top.setAdapter(topAdapter);
                lv_bottom.setAdapter(bottomAdapter);
                lv_etc.setAdapter(etcAdapter);

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