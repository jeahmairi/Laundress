package com.example.user.laundress2;

public class ShopHistoryList {
    private String name, date, status;
    private int trans_No;
    private int client_ID;

    public int getClient_ID() {
        return client_ID;
    }

    public void setClient_ID(int client_ID) {
        this.client_ID = client_ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTrans_No() {
        return trans_No;
    }

    public void setTrans_No(int trans_No) {
        this.trans_No = trans_No;
    }
}
