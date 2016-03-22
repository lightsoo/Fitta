package com.fitta.lightsoo.fitta.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fitta.lightsoo.fitta.Manager.NetworkManager;
import com.fitta.lightsoo.fitta.R;
import com.fitta.lightsoo.fitta.RestAPI.FittingRoomAPI;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fittingroom, container, false);
        init();

        return view;
    }

    public void init(){
        lv_top = (ListView) view.findViewById(R.id.lv_top);
        lv_top = (ListView) view.findViewById(R.id.lv_bottom);
        lv_top = (ListView) view.findViewById(R.id.lv_etc);
    }

    public void getUniform(){
        Call call = NetworkManager.getInstance().getAPI(FittingRoomAPI.class).getClothes();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
