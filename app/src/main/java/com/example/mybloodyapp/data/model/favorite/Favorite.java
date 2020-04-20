
package com.example.mybloodyapp.data.model.favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FavoriteData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FavoriteData getData() {
        return data;
    }

    public void setData(FavoriteData data) {
        this.data = data;
    }

}
