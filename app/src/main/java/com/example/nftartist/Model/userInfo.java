package com.example.nftartist.Model;

import android.widget.ImageView;

public class userInfo {
    private String name;
    private String nicName;
    private String email;
    private String photoUrl;

    public userInfo(String name, String nicName, String email) {
        this.name = name;
        this.nicName = nicName;
        this.email = email;
    }
    public userInfo(String name, String nicName, String email, String photoUrl) {
        this.name = name;
        this.nicName = nicName;
        this.email = email;
        this.photoUrl = photoUrl;
    }



    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNicName(String nicName) {
        this.nicName = nicName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getNicName() {
        return nicName;
    }

    public String getEmail() {
        return email;
    }
}
