package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.Message;
import com.fitta.lightsoo.fitta.Data.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface LoginAPI {

    @POST("/login")
    Call<Message> login(@Body User user);

}
