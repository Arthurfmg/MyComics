package com.dledford.comichoarder.rest.model;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineIssueModel extends ComicVineModel {

    private ComicVineModel volume;
    private Integer issue_number;
    private String store_date;
    private ComicVineImageModel image;
    private String description;

    public Integer getIssue_number() {
        return issue_number;
    }

    public void setIssue_number(Integer issue_number) {
        this.issue_number = issue_number;
    }

    public ComicVineModel getVolume() {
        return volume;
    }

    public void setVolume(ComicVineModel volume) {
        this.volume = volume;
    }

    public String getStore_date() {
        return store_date;
    }

    public void setStore_date(String store_date) {
        this.store_date = store_date;
    }

    public ComicVineImageModel getImage() {
        return image;
    }

    public void setImage(ComicVineImageModel image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
