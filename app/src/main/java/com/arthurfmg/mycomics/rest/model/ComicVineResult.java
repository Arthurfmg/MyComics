package com.arthurfmg.mycomics.rest.model;


import java.util.ArrayList;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineResult<E>{

    private String error;
    private Long limit;
    private Long offset;
    private Long number_of_page_results;
    private Long number_of_total_results;
    private Long status_code;
    private E results;

    public E getResults() {
        return results;
    }

    public void setResults(E results) {
        this.results = results;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getNumber_of_page_results() {
        return number_of_page_results;
    }

    public void setNumber_of_page_results(Long number_of_page_results) {
        this.number_of_page_results = number_of_page_results;
    }

    public Long getNumber_of_total_results() {
        return number_of_total_results;
    }

    public void setNumber_of_total_results(Long number_of_total_results) {
        this.number_of_total_results = number_of_total_results;
    }

    public Long getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Long status_code) {
        this.status_code = status_code;
    }
}
