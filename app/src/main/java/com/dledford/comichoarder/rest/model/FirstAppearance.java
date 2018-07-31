package com.dledford.comichoarder.rest.model;

/**
 * Created by phesto on 11/18/2016.
 */

public class FirstAppearance {
    private Long id;
    private String name;
    private String api_detail_url;
    private Float issue_number;

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

    public Float getIssue_number() {
        return issue_number;
    }

    public void setIssue_number(Float issue_number) {
        this.issue_number = issue_number;
    }
}
