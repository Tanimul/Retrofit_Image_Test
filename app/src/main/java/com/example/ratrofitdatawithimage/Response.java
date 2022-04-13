package com.example.ratrofitdatawithimage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("response")
    @Expose
    private int response;

    @SerializedName("status")
    @Expose
    private  String status;

    public Response(int response, String status) {
        this.response = response;
        this.status = status;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
