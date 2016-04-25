package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.Fitta;
import com.fitta.lightsoo.fitta.Data.Message;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by LG on 2016-04-21.
 */
public interface FittaAPI {


    @POST("/signup")
    Call<Message> signup(@Body Fitta fitta);

    @Multipart
    @POST("/uploadImage")
    Call<Message> uploadImage(
            @Part("movie_img\"; filename=\"image.jpg\" ")RequestBody file1);
}
