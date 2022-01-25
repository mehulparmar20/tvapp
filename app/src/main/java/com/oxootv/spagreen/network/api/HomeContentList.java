package com.oxootv.spagreen.network.api;

import com.google.gson.annotations.SerializedName;
import com.oxootv.spagreen.model.HomeContent;
import com.oxootv.spagreen.model.Movie;

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
