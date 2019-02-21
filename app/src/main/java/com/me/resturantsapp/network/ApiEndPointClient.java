package com.me.resturantsapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiEndPointClient {
    private static ApiEndPointInterface instance;

    private ApiEndPointClient() {}

    public static ApiEndPointInterface newInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.foursquare.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(ApiEndPointInterface.class);
        }

        return instance;
    }
}
