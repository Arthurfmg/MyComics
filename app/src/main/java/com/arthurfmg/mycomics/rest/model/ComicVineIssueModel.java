package com.arthurfmg.mycomics.rest.model;

import com.google.firebase.database.Exclude;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineIssueModel extends ComicVineModel {

    private ComicVineModel volume;
    private Integer issue_number;
    private String store_date;
    private String cover_date;
    private ComicVineImageModel image;
    private String description;

    public Integer getIssue_number() {
        return issue_number;
    }

    public void setIssue_number(Integer issue_number) {
        this.issue_number = issue_number;
    }

    @Exclude
    public ComicVineModel getVolume() {
        return volume;
    }

    public void setVolume(ComicVineModel volume) {
        this.volume = volume;
    }

    @Exclude
    public String getStore_date() {
        return store_date;
    }

    public void setStore_date(String store_date) {
        this.store_date = store_date;
    }

    @Exclude
    public ComicVineImageModel getImage() {
        return image;
    }

    public void setImage(ComicVineImageModel image) {
        this.image = image;
    }

    @Exclude
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    public String getCover_date() {
        return cover_date;
    }

    public void setCover_date(String cover_date) {
        this.cover_date = cover_date;
    }
}
