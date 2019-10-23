package com.beauty.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Businessresponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("content")
    @Expose
    private List<BusinessList> content = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BusinessList> getContent() {
        return content;
    }

    public void setContent(List<BusinessList> content) {
        this.content = content;
    }

}

