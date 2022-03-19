package com.yahumott.tvapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Video implements Serializable {

    @SerializedName("movie_id")
    @Expose
    private String videoFileId;
    @SerializedName("created_at")
    @Expose
    private String label;
//    @SerializedName("stream_key")
//    @Expose
//    private String streamKey;
    @SerializedName("type")
    @Expose
    private String fileType;
    @SerializedName("iframeurl")
    @Expose
    private String fileUrl;
    @SerializedName("ready_url")
    @Expose
    private String ready_url;
    @SerializedName("url_360")
    @Expose
    private String url_360;
    @SerializedName("url_480")
    @Expose
    private String url_480;
    @SerializedName("url_720")
    @Expose
    private String url_720;
    @SerializedName("url_1080")
    @Expose
    private String url_1080;
    @SerializedName("subtitle")
    @Expose
    private List<Subtitle> subtitle = null;

    public String getVideoFileId() {
        return videoFileId;
    }

    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

//    public String getStreamKey() {
//        return streamKey;
//    }
//
//    public void setStreamKey(String streamKey) {
//        this.streamKey = streamKey;
//    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public List<Subtitle> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<Subtitle> subtitle) {
        this.subtitle = subtitle;
    }

    public String getReady_url() {
        return ready_url;
    }

    public void setReady_url(String ready_url) {
        this.ready_url = ready_url;
    }

    public String getUrl_360() {
        return url_360;
    }

    public void setUrl_360(String url_360) {
        this.url_360 = url_360;
    }

    public String getUrl_480() {
        return url_480;
    }

    public void setUrl_480(String url_480) {
        this.url_480 = url_480;
    }

    public String getUrl_720() {
        return url_720;
    }

    public void setUrl_720(String url_720) {
        this.url_720 = url_720;
    }

    public String getUrl_1080() {
        return url_1080;
    }

    public void setUrl_1080(String url_1080) {
        this.url_1080 = url_1080;
    }
}
