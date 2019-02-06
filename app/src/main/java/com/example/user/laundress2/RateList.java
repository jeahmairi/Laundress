package com.example.user.laundress2;

public class RateList {
    private int rate_no;
    private String name;
    private float rate;
    private String comment;
    private String date;

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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
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
}
