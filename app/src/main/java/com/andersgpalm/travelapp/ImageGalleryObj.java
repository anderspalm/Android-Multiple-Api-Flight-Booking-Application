package com.andersgpalm.travelapp;

/**
 * Created by ander on 9/10/2016.
 */
public class ImageGalleryObj {


    String id, secret, server, title;
    int farm;

    public ImageGalleryObj(String id, String secret, String server, String title, int farm) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.title = title;
        this.farm = farm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }
}
