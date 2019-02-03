package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HandwasherNotifAdapter extends BaseAdapter {
    Context context;
    HandwasherNotifAdapter.ItemHolder itemHolder;
    ArrayList<HandwasherNotifList> handwasherNotifLists;

    public HandwasherNotifAdapter(Context context, ArrayList<HandwasherNotifList> handwasherNotifLists) {
        this.context = context;
        this.handwasherNotifLists = handwasherNotifLists;
    }

    @Override
    public int getCount() {
        return handwasherNotifLists.size();
    }

    @Override
    public Object getItem(int position) {
        return handwasherNotifLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new HandwasherNotifAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.handwashernotif_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.status = (TextView) convertView.findViewById(R.id.status);
        //final ClientPostList clientPostList=clientPostLists.get(position);
        //}
        itemHolder.name.setText(handwasherNotifLists.get(position).getClient_name());
       // itemHolder.status.setText(handwasherNotifLists.get(position).getNotification_message());
        String message = handwasherNotifLists.get(position).getNotification_message();
        if(message.equals("Pending")){
            itemHolder.status.setText("Requested your service.");
        }

        return convertView;
    }

    private class ItemHolder {
        TextView name, status;
    }
}
