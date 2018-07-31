package com.dledford.comichoarder.rest.model;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineCharacterModel extends ComicVineModel {

    FirstAppearance first_appeared_in_issue;
    ComicVineImageModel image;
    Integer count_of_issue_appearances;

    public Integer getCount_of_issue_appearances() {
        return count_of_issue_appearances;
    }

    public void setCount_of_issue_appearances(Integer count_of_issue_appearances) {
        this.count_of_issue_appearances = count_of_issue_appearances;
    }

    public ComicVineImageModel getImage(){
        return image;
    }

    public void setImage(ComicVineImageModel image){
        this.image = image;
    }

    public FirstAppearance getFirst_appeared_in_issue() {
        return first_appeared_in_issue;
    }

    public void setFirst_appeared_in_issue(FirstAppearance first_appeared_in_issue) {
        this.first_appeared_in_issue = first_appeared_in_issue;
    }
}
