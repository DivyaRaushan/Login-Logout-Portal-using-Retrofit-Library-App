package com.example.loginandlogoutusingretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    private static final String url = "http://192.168.29.74/api/";
    private static ApiController controllerObject;
    private static Retrofit retrofit;

    ApiController() {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiController getInstance() {
        if (controllerObject == null) {
            controllerObject = new ApiController();
        }
        return controllerObject;
    }

    SetApi getApi() {
        return retrofit.create(SetApi.class);
    }

}
