package com.fitta.lightsoo.fitta.RestAPI;

import com.fitta.lightsoo.fitta.Data.Message;
import com.fitta.lightsoo.fitta.Data.Test;
import com.fitta.lightsoo.fitta.Data.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;


/**
 *  Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $ 이거 에러뜨는경우에 콜백받을때 call<><
 */

public interface LoginAPI {

    @POST("/auth")
    Call<Message> login(@Body Test test);

    @POST("/login")
    Call<Message> login(@Body User user);

}
