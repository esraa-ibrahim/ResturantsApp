package com.me.resturantsapp.map;

import android.util.Log;

import com.me.resturantsapp.Item;
import com.me.resturantsapp.model.BaseObject;
import com.me.resturantsapp.model.Photos;
import com.me.resturantsapp.network.ApiEndPointClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mMapView;

    public MapPresenter (MapContract.View mapView) {
        this.mMapView = mapView;
    }

    @Override
    public void getRestaurantsList() {
        Call<BaseObject> call = ApiEndPointClient.newInstance().getRestaurantsList();
        call.enqueue(new Callback<BaseObject>() {
            @Override
            public void onResponse(Call<BaseObject> call, Response<BaseObject> response) {
                if (response.body() != null) {
                    mMapView.showRestaurants(response.body().getResponse().getVenues());
                }
            }

            @Override
            public void onFailure(Call<BaseObject> call, Throwable t) {
                Log.e("Failuer", "Request Failed");
            }
        });
    }

    @Override
    public void getRestaurantPhoto(String restaurantId) {
        Call<BaseObject> call = ApiEndPointClient.newInstance().getRestaurantPhoto(restaurantId);
        call.enqueue(new Callback<BaseObject>() {
            @Override
            public void onResponse(Call<BaseObject> call, Response<BaseObject> response) {
                if (response.body() != null &&
                        response.body().getResponse().getPhotos() != null) {
                    Photos photos = response.body().getResponse().getPhotos();
                    Item item = photos.getItems().get(0);
                    String photoUrl = item.getPrefix() + "100x100" + item.getSuffix();
                    mMapView.showRestaurantPhoto(photoUrl);
                }
            }

            @Override
            public void onFailure(Call<BaseObject> call, Throwable t) {

            }
        });
    }
}
