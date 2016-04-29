package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.Fitta;
import com.fitta.lightsoo.fitta.Data.Message;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public interface FittaAPI {

    @POST("/signup")
    Call<Message> signup(@Body Fitta fitta);

    @Multipart
    @POST("/uploadImage")
    Call<Message> uploadImage(
            @Part("movie_img\"; filename=\"image.jpg\" ")RequestBody file1,
            @Part("clothesCategory")String clothesCategory,
            @Part("clothesSize")String clothesSize);


    //어떻게 할지 확인이 필요하다...
    @FormUrlEncoded
    @POST("/uploadLikeImage")
    Call<Message> uploadLikeImage(@Field("like_top")int like_top,
                                  @Field("like_bottom")int like_bottom,
                                  @Field("like_etc")int like_etc );

    //배열로 전송된다.
    @FormUrlEncoded
    @POST("/uploadLikeImage")
    Call<Message> uploadLikeImage2(@Field("like")String like_top,
                                   @Field("like")String like_bottom,
                                   @Field("like")String like_etc );




}
