package com.yahumott.tvapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("id")
    @Expose
    private String videosId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("detail")
    @Expose
    private String description;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("released")
    @Expose
    private String release;
    @SerializedName("series")
    @Expose
    private String isTvseries;
    @SerializedName("duration")
    @Expose
    private String runtime;
//    @SerializedName("video_quality")
//    @Expose
    private String videoQuality;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster")
    @Expose
    private String posterUrl;
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getIsTvseries() {
        return isTvseries;
    }

    public void setIsTvseries(String isTvseries) {
        this.isTvseries = isTvseries;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(String videoQuality) {
        this.videoQuality = videoQuality;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getThumbnailUrltv() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


}
