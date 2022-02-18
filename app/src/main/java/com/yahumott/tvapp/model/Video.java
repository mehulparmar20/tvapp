package com.yahumott.tvapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Video implements Serializable {

    @SerializedName("movie_id")
    @Expose
    private String videoFileId;
    @SerializedName("label")
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

}
