package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HandwasherAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<HandwasherList> handwasherLists;

    public HandwasherAdapter(Context context,  ArrayList<HandwasherList> handwasherLists) {
        this.context = context;
        this.handwasherLists = handwasherLists;
    }
    @Override
    public int getCount() {
        return handwasherLists.size();
    }

    @Override
    public Object getItem(int position) {
        return handwasherLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();
            convertView = layoutInflater.inflate(R.layout.handwasheradapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.hwname);
            itemHolder.contact = (TextView) convertView.findViewById(R.id.hwcont);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.hwmeters);


            itemHolder.name.setText(handwasherLists.get(position).getHandwasherName());
            itemHolder.contact.setText(handwasherLists.get(position).getContact());
            itemHolder.meters.setText(handwasherLists.get(position).getHwmeter());
        }

        return convertView;
    }
    private class ItemHolder {
        TextView name;
        TextView contact;
        TextView meters;
    }
}