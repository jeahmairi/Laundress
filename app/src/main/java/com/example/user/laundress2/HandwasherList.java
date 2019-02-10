package com.example.user.laundress2;

public class HandwasherList {
    private  String HandwasherName;
    private  String hwmeter;
    private  String contact;
    private  int lsp_id;
    private  int client_id;
    private  double hwmeterdouble;

  /*  private  String photo;
    private  int handwasherid;*/

    public String getHandwasherName() {
        return HandwasherName;
    }

    public void setHandwasherName(String handwasherName) {
        HandwasherName = handwasherName;
    }

    public String getHwmeter() {
        return hwmeter;
    }

    public void setHwmeter(String hwmeter) {
        this.hwmeter = hwmeter;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getLsp_id() {
        return lsp_id;
    }

    public void setLsp_id(int lsp_id) {
        this.lsp_id = lsp_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public double getHwmeterdouble() {
        return hwmeterdouble;
    }

    public void setHwmeterdouble(double hwmeterdouble) {
        this.hwmeterdouble = hwmeterdouble;
    }
/*
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getHandwasherid() {
        return handwasherid;
    }

    public void setHandwasherid(int handwasherid) {
        this.handwasherid = handwasherid;
    }*/
}
