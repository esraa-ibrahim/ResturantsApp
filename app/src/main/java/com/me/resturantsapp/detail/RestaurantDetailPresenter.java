package com.me.resturantsapp.detail;

import com.me.resturantsapp.Item;
import com.me.resturantsapp.model.BaseObject;
import com.me.resturantsapp.model.Photos;
import com.me.resturantsapp.network.ApiEndPointClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailPresenter implements RestaurantDetailContract.Presenter {
    private RestaurantDetailContract.View mRestaurantDetailView;

    public RestaurantDetailPresenter (RestaurantDetailContract.View restaurantDetailView) {
        this.mRestaurantDetailView = restaurantDetailView;
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
                    String photoUrl = item.getPrefix() + "300x300" + item.getSuffix();
                    mRestaurantDetailView.showRestaurantPhoto(photoUrl);
                }
            }

            @Override
            public void onFailure(Call<BaseObject> call, Throwable t) {

            }
        });
    }
}
