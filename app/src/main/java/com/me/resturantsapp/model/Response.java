
package com.me.resturantsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("venues")
    @Expose
    private List<Venue> venues = null;
    @SerializedName("confident")
    @Expose
    private Boolean confident;
    @SerializedName("photos")
    @Expose
    private Photos photos;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
