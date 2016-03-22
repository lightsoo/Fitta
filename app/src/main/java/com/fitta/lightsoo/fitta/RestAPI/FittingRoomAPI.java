package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.ClothesItems;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by LG on 2016-03-22.
 */
public interface FittingRoomAPI {

    @GET("/clothes")
    Call<ClothesItems> getClothes();

}
