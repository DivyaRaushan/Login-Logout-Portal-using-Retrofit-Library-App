package com.example.loginandlogoutusingretrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SetApi {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> userverify(@Field("email") String email, @Field("password") String password);
}
