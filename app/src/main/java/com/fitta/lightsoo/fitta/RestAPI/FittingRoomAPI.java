package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.ClothesItems;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by LG on 2016-03-22.
 */
public interface FittingRoomAPI {
//리턴받을 형태 : 일단 객체 하나로 리턴받을꺼야 다음 배열들을 파싱할꺼임
    @GET("/fitta")
    Call<ClothesItems> getClothes();

}
