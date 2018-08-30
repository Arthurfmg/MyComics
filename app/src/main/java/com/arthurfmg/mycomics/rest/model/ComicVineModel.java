package com.arthurfmg.mycomics.rest.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by phesto on 11/18/2016.
 */

@IgnoreExtraProperties
public class ComicVineModel{
    private Long id;
    private String name;
    private String api_detail_url;

    public ComicVineModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApi_detail_url() {
        return api_detail_url;
    }

    public void setApi_detail_url(String api_detail_url) {
        this.api_detail_url = api_detail_url;
    }



}
