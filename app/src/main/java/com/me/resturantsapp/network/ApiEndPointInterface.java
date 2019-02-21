package com.me.resturantsapp.network;

import com.me.resturantsapp.model.BaseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndPointInterface {
    @GET("venues/search?client_id=AENCJXUJNHV50I4HJ3YCE5DALY5RKNTCXZRL1QTHNOIWBKON" +
            "&client_secret=N0XGK2EBKEW1AEHUW3R0013OIFLRZNUBF5WFAFEDS1J5EVMV" +
            "&v=20190210&ll=30.19,31.45&categoryId=4d4b7105d754a06374d81259")
    Call<BaseObject> getRestaurantsList();

    @GET("venues/{restaurantId}/photos?client_id=AENCJXUJNHV50I4HJ3YCE5DALY5RKNTCXZRL1QTHNOIWBKON" +
            "&client_secret=N0XGK2EBKEW1AEHUW3R0013OIFLRZNUBF5WFAFEDS1J5EVMV&v=20190210")
    Call<BaseObject> getRestaurantPhoto(@Path("restaurantId") String restaurantId);
}
