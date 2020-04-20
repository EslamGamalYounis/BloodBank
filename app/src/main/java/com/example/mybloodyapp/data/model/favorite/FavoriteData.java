
package com.example.mybloodyapp.data.model.favorite;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteData {

    @SerializedName("attached")
    @Expose
    private List<Integer> attached = null;
    @SerializedName("detached")
    @Expose
    private List<Object> detached = null;

    public List<Integer> getAttached() {
        return attached;
    }

    public void setAttached(List<Integer> attached) {
        this.attached = attached;
    }

    public List<Object> getDetached() {
        return detached;
    }

    public void setDetached(List<Object> detached) {
        this.detached = detached;
    }

}
