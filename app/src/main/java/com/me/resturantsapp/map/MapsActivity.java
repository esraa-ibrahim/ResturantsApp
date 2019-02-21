package com.me.resturantsapp.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.me.resturantsapp.R;
import com.me.resturantsapp.detail.RestaurantDetailActivity;
import com.me.resturantsapp.model.Venue;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        MapContract.View, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private MapPresenter mMapPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMapPresenter = new MapPresenter(this);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("RESTAURANTS_APP", "getDynamicLink:onFailure", e);
                    }
                });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMapPresenter.getRestaurantsList();

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);



        // Move the camera to cairo
        LatLng cairo = new LatLng(30.0444, 31.2357);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 12));
    }

    @Override
    public void showRestaurants(List<Venue> venues) {
        for (Venue v: venues) {
            LatLng location = new LatLng(v.getLocation().getLat(), v.getLocation().getLng());

            Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(v.getName()));
            marker.setTag(v);
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_place_black_48));
        }
    }

    @Override
    public void showRestaurantPhoto(final String photoUrl) {
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Venue venue = (Venue) marker.getTag();
                View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                TextView tvName = view.findViewById(R.id.rest_name);
                ImageView ivRestaurant = view.findViewById(R.id.rest_image);

                tvName.setText(venue.getName());

                Picasso.get().load(photoUrl).into(ivRestaurant);

                return view;
            }
        });

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Venue venue = (Venue) marker.getTag();
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("VENUE", venue);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Venue venue = (Venue) marker.getTag();
        mMapPresenter.getRestaurantPhoto(venue.getId());
        return false;
    }
}
