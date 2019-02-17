package com.example.user.laundress2;

public class ShopRatingList {
    private int rate_no;
    private float rate;
    private float avgRate;
    private String comment;
    private String date;
    private String clientName;

    public float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }

    public int getRate_no() {
        return rate_no;
    }

    public void setRate_no(int rate_no) {
        this.rate_no = rate_no;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
