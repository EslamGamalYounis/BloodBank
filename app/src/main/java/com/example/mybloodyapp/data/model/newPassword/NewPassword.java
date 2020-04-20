
package com.example.mybloodyapp.data.model.newPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPassword {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NewPasswordData data;

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

    public NewPasswordData getData() {
        return data;
    }

    public void setData(NewPasswordData data) {
        this.data = data;
    }

}
