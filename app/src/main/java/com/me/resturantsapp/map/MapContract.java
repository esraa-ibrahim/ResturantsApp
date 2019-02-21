package com.me.resturantsapp.map;

import android.widget.ImageView;

import com.me.resturantsapp.model.Venue;

import java.util.List;

public interface MapContract {
    interface View {
        void showRestaurants(List<Venue> venues);

        void showRestaurantPhoto(String photoUrl);
    }

    interface Presenter {
        void getRestaurantsList();

        void getRestaurantPhoto(String restaurantId);
    }
}
