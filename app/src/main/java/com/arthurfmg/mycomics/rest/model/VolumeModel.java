package com.arthurfmg.mycomics.rest.model;

/**
 * Created by Arthur on 29/05/2017.
 */

public class VolumeModel extends ComicVineModel {
    private ComicVineImageModel image;
    private String count_of_issues;
    private Integer number;
    private String start_year;
    private Publisher publisher;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public ComicVineImageModel getImage() {
        return image;
    }

    public void setImage(ComicVineImageModel image) {
        this.image = image;
    }

    public String getCount_of_issues() {
        return count_of_issues;
    }

    public void setCount_of_issues(String count_of_issues) {
        this.count_of_issues = count_of_issues;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
