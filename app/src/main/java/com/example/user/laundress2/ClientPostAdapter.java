package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientPostAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ClientPostList> clientPostLists;

    public ClientPostAdapter(Context context, ArrayList<ClientPostList> clientPostLists) {
        this.context = context;
        this.clientPostLists = clientPostLists;
    }

    @Override
    public int getCount() {
        return clientPostLists.size();
    }

    @Override
    public Object getItem(int position) {
        return clientPostLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ItemHolder();
        convertView = layoutInflater.inflate(R.layout.clientpostadapter, parent, false);
        itemHolder.time = (TextView) convertView.findViewById(R.id.time);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.meters = (TextView) convertView.findViewById(R.id.meters);
        itemHolder.message = convertView.findViewById(R.id.message);
        //final ClientPostList clientPostList=clientPostLists.get(position);


        itemHolder.time.setText(String.valueOf(clientPostLists.get(position).getPost_datetime()));
        itemHolder.name.setText(clientPostLists.get(position).getPost_name());
        itemHolder.meters.setText(clientPostLists.get(position).getPost_showAddress());
        itemHolder.message.setText(clientPostLists.get(position).getPost_message());
        //}

        return convertView;
    }

    private class ItemHolder {
        TextView time;
        TextView name;
        TextView meters;
        TextView message;
        TextView contact1;
        TextView contact2;

    }
}
