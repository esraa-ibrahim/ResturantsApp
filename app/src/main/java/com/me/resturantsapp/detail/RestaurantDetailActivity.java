package com.me.resturantsapp.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.me.resturantsapp.R;
import com.me.resturantsapp.model.Venue;
import com.squareup.picasso.Picasso;

public class RestaurantDetailActivity extends AppCompatActivity
        implements RestaurantDetailContract.View {

    private ImageView mRestaurantImage;
    private RestaurantDetailPresenter mRestaurantDetailPresenter;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        btnShare = findViewById(R.id.btn_share);



        mRestaurantDetailPresenter = new RestaurantDetailPresenter(this);

        mRestaurantImage = findViewById(R.id.restaurant_image);
        TextView tvName = findViewById(R.id.restaurant_name);

        Venue venue = (Venue) getIntent().getSerializableExtra("VENUE");

        if (venue != null) {
            tvName.setText(venue.getName());

            mRestaurantDetailPresenter.getRestaurantPhoto(venue.getId());
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri baseUrl = Uri.parse("https://computersciencegeeks.wordpress.com/about/");
                String domain = "https://resturantsapp.page.link";

                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance()
                        .createDynamicLink()
                        .setLink(baseUrl)
                        .setDomainUriPrefix(domain)
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.me.resturantsapp").build())
                        .buildDynamicLink();

                Uri link =  dynamicLink.getUri();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, link.toString());

                startActivity(Intent.createChooser(intent, "Share Link"));
            }
        });
    }

    @Override
    public void showRestaurantPhoto(String photoUrl) {
        Picasso.get().load(photoUrl).into(mRestaurantImage);
    }
}
