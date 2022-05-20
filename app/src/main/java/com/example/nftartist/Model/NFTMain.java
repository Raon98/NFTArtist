package com.example.nftartist.Model;

import android.widget.EditText;

import com.example.nftartist.Controller.NFTAdd;

public class NFTMain {
    private String nftTitle;
    private String nftEx;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private String nftUri;

    public NFTMain(String nftTitle, String nftEx, String publisher,String nftUri) {
        this.nftTitle = nftTitle;
        this.nftEx = nftEx;
        this.nftUri = nftUri;
        this.publisher = publisher;
    }

    private String publisher;
    public NFTMain(String nftTitle, String nftUri){
        this.nftTitle = nftTitle;
        this.nftUri = nftUri;
    }
    public NFTMain(String nftTitle, String nftUri,String publisher) {
        this.nftTitle = nftTitle;
        this.nftUri = nftUri;
        this.publisher = publisher;
    }


    public String getNftTitle() {
        return nftTitle;
    }

    public void setNftTitle(String nftTitle) {
        this.nftTitle = nftTitle;
    }

    public String getNftEx() {
        return nftEx;
    }

    public void setNftEx(String nftEx) {
        this.nftEx = nftEx;
    }

    public String getNftUri() {
        return nftUri;
    }

    public void setNftUri(String nftUri) {
        this.nftUri = nftUri;
    }
}
