package com.yahumott.tvapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieSingleDetails implements Serializable {

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
    @SerializedName("duration")
    @Expose
    private String runtime;

    @SerializedName("series")
    @Expose
    private String isTvseries;


    @SerializedName("thumbnail")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster")
    @Expose
    private String posterUrl;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    @SerializedName("download_links")
    @Expose
    private List<DownloadLink> downloadLinks = null;
    @SerializedName("genre")
    @Expose
    private List<Genre> genre = null;

    @SerializedName("director")
    @Expose
    private List<Director> director = null;


    @SerializedName("cast")
    @Expose
    private List<CastAndCrew> castAndCrew = null;
    @SerializedName("seasons")
    @Expose
    private List<Season> season = null;
    @SerializedName("related_movie")
    @Expose
    private List<RelatedMovie> relatedMovie = null;

    @SerializedName("related_tvseries")
    @Expose
    private List<RelatedMovie> relatedTvseries = null;
    @SerializedName("type")
    @Expose
    private String type;

    private String streamFrom;
    private String streamLabel;
    private String streamUrl;
    private List<MediaSource> mediaSource;
    private List<Channel> channelList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

//    public String getVideoQuality() {
//        return videoQuality;
//    }

//    public void setVideoQuality(String videoQuality) {
//        this.videoQuality = videoQuality;
//    }

    public String getIsTvseries() {
        return isTvseries;
    }

    public void setIsTvseries(String isTvseries) {
        this.isTvseries = isTvseries;
    }

//    public String getIsPaid() {
//        return isPaid;
//    }

//    public void setIsPaid(String isPaid) {
//        this.isPaid = isPaid;
//    }

//    public String getEnableDownload() {
//        return enableDownload;
//    }

//    public void setEnableDownload(String enableDownload) {
//        this.enableDownload = enableDownload;
//    }

    public String getThumbnailUrl() {
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

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }



    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }



    public List<DownloadLink> getDownloadLinks() {
        return downloadLinks;
    }

    public void setDownloadLinks(List<DownloadLink> downloadLinks) {
        this.downloadLinks = downloadLinks;
    }



    public List<CastAndCrew> getCastAndCrew() {
        return castAndCrew;
    }

    public void setCastAndCrew(List<CastAndCrew> castAndCrew) {
        this.castAndCrew = castAndCrew;
    }

    public List<Season> getSeason() {
        return season;
    }

    public void setSeason(List<Season> season) {
        this.season = season;
    }

    public List<RelatedMovie> getRelatedMovie() {
        return relatedMovie;
    }

    public void setRelatedMovie(List<RelatedMovie> relatedMovie) {
        this.relatedMovie = relatedMovie;
    }

    public List<RelatedMovie> getRelatedTvseries() {
        return relatedTvseries;
    }

    public void setRelatedTvseries(List<RelatedMovie> relatedTvseries) {
        this.relatedTvseries = relatedTvseries;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreamFrom() {
        return streamFrom;
    }

    public void setStreamFrom(String streamFrom) {
        this.streamFrom = streamFrom;
    }

    public String getStreamLabel() {
        return streamLabel;
    }

    public void setStreamLabel(String streamLabel) {
        this.streamLabel = streamLabel;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public List<MediaSource> getMediaSource() {
        return mediaSource;
    }

    public void setMediaSource(List<MediaSource> mediaSource) {
        this.mediaSource = mediaSource;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
}



