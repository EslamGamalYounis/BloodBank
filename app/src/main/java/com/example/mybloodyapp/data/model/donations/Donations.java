
package com.example.mybloodyapp.data.model.donations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donations {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DonationsPaging data;

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

    public DonationsPaging getData() {
        return data;
    }

    public void setData(DonationsPaging data) {
        this.data = data;
    }

}
