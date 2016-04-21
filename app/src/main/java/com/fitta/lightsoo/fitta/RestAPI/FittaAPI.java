package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.Fitta;
import com.fitta.lightsoo.fitta.Data.Message;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by LG on 2016-04-21.
 */
public interface FittaAPI {


    @POST("/signup")
    Call<Message> signup(@Body Fitta fitta);

}
