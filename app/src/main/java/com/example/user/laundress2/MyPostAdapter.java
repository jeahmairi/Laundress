package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPostAdapter extends BaseAdapter {
    Context context;
    MyPostAdapter.ItemHolder itemHolder;
    ArrayList<ClientPostList> clientPostLists;

    public MyPostAdapter(Context context, ArrayList<ClientPostList> clientPostLists) {
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
        itemHolder = new MyPostAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.mypost_adapter, parent, false);
        itemHolder.date = (TextView) convertView.findViewById(R.id.date);
        itemHolder.message = (TextView) convertView.findViewById(R.id.post);
        //final ClientPostList clientPostList=clientPostLists.get(position);


        itemHolder.date.setText(clientPostLists.get(position).getDate());
        itemHolder.message.setText(clientPostLists.get(position).getPost_message());

        return convertView;
    }
    private class ItemHolder {
        TextView date, message;
    }
}
