package com.oxootv.spagreen.network.api;

import com.google.gson.annotations.SerializedName;
import com.oxootv.spagreen.model.Movie;

import java.io.Serializable;
import java.util.List;

public class MovieList implements Serializable {
    @SerializedName("movie")
    private List<Movie> result = null;

    public List<Movie> getResult() {
        return result;
    }

    public void setResult(List<Movie> result) {
        this.result = result;
    }

    @SerializedName("tvseries")
    private List<Movie> resulttv = null;

    public List<Movie> getResulttv() {
        return resulttv;
    }

    public void setResulttv(List<Movie> resulttv) {
        this.resulttv = resulttv;
    }
}
