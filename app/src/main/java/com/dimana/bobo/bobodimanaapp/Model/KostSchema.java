package com.dimana.bobo.bobodimanaapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KostSchema {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Kost> data;

    public KostSchema(String status, String message, List<Kost> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Kost> getData() {
        return data;
    }

    public void setData(List<Kost> data) {
        this.data = data;
    }
}
