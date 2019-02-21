
package com.me.resturantsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.me.resturantsapp.Item;

import java.io.Serializable;
import java.util.List;

public class Photos implements Serializable {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
