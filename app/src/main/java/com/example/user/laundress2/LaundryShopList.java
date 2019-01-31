package com.example.user.laundress2;

public class LaundryShopList {
    private  int id;
    private  int lsp_id;
    private  String name;
    private  String location;
    private  String meter;
    private  String openhours;
    private  String closehours;
    private  String contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getOpenhours() {
        return openhours;
    }

    public void setOpenhours(String openhours) {
        this.openhours = openhours;
    }

    public String getClosehours() {
        return closehours;
    }

    public void setClosehours(String closehours) {
        this.closehours = closehours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public int getLsp_id() {
        return lsp_id;
    }

    public void setLsp_id(int lsp_id) {
        this.lsp_id = lsp_id;
    }
}
