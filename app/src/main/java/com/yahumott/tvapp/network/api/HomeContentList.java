package com.yahumott.tvapp.network.api;

import com.google.gson.annotations.SerializedName;
import com.yahumott.tvapp.model.HomeContent;

import java.util.List;

public class HomeContentList {
    @SerializedName("data")
    private List<HomeContent> result = null;

    public List<HomeContent> getResult() {
        return result;
    }

    public void setResult(List<HomeContent> result) {
        this.result = result;
    }

}
