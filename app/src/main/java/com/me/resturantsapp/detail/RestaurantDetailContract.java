package com.me.resturantsapp.detail;

public interface RestaurantDetailContract {
    interface View {

        void showRestaurantPhoto(String photoUrl);
    }

    interface Presenter {
        void getRestaurantPhoto(String restaurantId);
    }
}
