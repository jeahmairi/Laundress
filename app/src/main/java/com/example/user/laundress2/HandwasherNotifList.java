package com.example.user.laundress2;

public class HandwasherNotifList {
    private int client_id;
    private int lsp_id;
    private int trans_no;
    private String notification_message;
    private String client_name;
    private String table;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getLsp_id() {
        return lsp_id;
    }

    public void setLsp_id(int lsp_id) {
        this.lsp_id = lsp_id;
    }

    public int getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(int trans_no) {
        this.trans_no = trans_no;
    }

    public String getNotification_message() {
        return notification_message;
    }

    public void setNotification_message(String notification_message) {
        this.notification_message = notification_message;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
