package com.oxootv.spagreen.model;

public class SearchContent {
    private String id;
    private String title;
    private String description;
    private String type;
    private String streamUrl;
    private String streamFrom;
    private String thumbnailUrl;

    public SearchContent() {
    }

    public SearchContent(String id, String title, String description, String type, String streamUrl, String streamFrom, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.streamUrl = streamUrl;
        this.streamFrom = streamFrom;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamFrom() {
        return streamFrom;
    }

    public void setStreamFrom(String streamFrom) {
        this.streamFrom = streamFrom;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
