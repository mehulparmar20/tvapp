package com.oxootv.spagreen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("created_at")
    @Expose
    private String joinDate;
    @SerializedName("access_token")
    @Expose
    private String access_token;

    @SerializedName("image")
    @Expose
    private String imageUrl;


    public User() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getage() {
        return age;
    }

    public void setage(String age) {
        this.age = age;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getaccess_token() {
        return access_token;
    }

    public void setaccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + age + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", lastLogin='" + access_token + '\'' +
                ", image_url='" + imageUrl + '\'' +

                '}';
    }
}
