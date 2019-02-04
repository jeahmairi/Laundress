package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientNotifAdapter extends BaseAdapter {
    Context context;
    ClientNotifAdapter.ItemHolder itemHolder;
    ArrayList<ClientNotifList> clientNotifLists;

    public ClientNotifAdapter(Context context, ArrayList<ClientNotifList> clientNotifLists) {
        this.context = context;
        this.clientNotifLists = clientNotifLists;
    }

    @Override
    public int getCount() {
        return clientNotifLists.size();
    }

    @Override
    public Object getItem(int position) {
        return clientNotifLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ClientNotifAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.clientnotif_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.status = (TextView) convertView.findViewById(R.id.status);
        //final ClientPostList clientPostList=clientPostLists.get(position);
        //}

        // itemHolder.status.setText(handwasherNotifLists.get(position).getNotification_message());
        String message = clientNotifLists.get(position).getNotification_message();
        if(message.equals("Approved")){
            itemHolder.name.setText(clientNotifLists.get(position).getClient_name());
            itemHolder.status.setText("Approved your service. Please wait for laundry Details confirmation.");
        }

        return convertView;
    }

    private class ItemHolder {
        TextView name, status;
    }
}