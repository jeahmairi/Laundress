package com.example.user.laundress2;

public class RateList {
    private int rate_no;
    private String name;
    private float accommodation;
    private float qualityofservice;
    private float ontime;
    private float overall;
    private float rating;
    private String comment;
    private String date;

    public float getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(float accommodation) {
        this.accommodation = accommodation;
    }

    public float getQualityofservice() {
        return qualityofservice;
    }

    public void setQualityofservice(float qualityofservice) {
        this.qualityofservice = qualityofservice;
    }

    public float getOntime() {
        return ontime;
    }

    public void setOntime(float ontime) {
        this.ontime = ontime;
    }

    public float getOverall() {
        return overall;
    }

    public void setOverall(float overall) {
        this.overall = overall;
    }

    public int getRate_no() {
        return rate_no;
    }

    public void setRate_no(int rate_no) {
        this.rate_no = rate_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
