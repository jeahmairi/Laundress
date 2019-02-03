package com.example.user.laundress2;

public class LaundryShopList {
    private  int id;
    private  int lsp_id;
    private  int client_id;
    private  String name;
    private  String client_name;
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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
