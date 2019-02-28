package com.example.user.laundress2;

public class HandwasherBookingsList {
    private String name;
    private String location;
    private String contact;
    private String services;
    private String extraservices;
    private String servicetype;
    private String datetime;
    private String weight;
    private String image;
    private int client_id;
    private int trans_no;
    private int lsp_id;
    private int handwasher_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExtraservices() {
        return extraservices;
    }

    public void setExtraservices(String extraservices) {
        this.extraservices = extraservices;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(int trans_no) {
        this.trans_no = trans_no;
    }

    public int getLsp_id() {
        return lsp_id;
    }

    public void setLsp_id(int lsp_id) {
        this.lsp_id = lsp_id;
    }

    public int getHandwasher_id() {
        return handwasher_id;
    }

    public void setHandwasher_id(int handwasher_id) {
        this.handwasher_id = handwasher_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
