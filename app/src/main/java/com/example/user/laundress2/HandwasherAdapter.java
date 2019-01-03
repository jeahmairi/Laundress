package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
       // if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();
            convertView = layoutInflater.inflate(R.layout.handwasheradapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.hwname);
            itemHolder.contact = (TextView) convertView.findViewById(R.id.hwcont);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.hwmeters);
            itemHolder.choose = convertView.findViewById(R.id.btnchoose);
            final HandwasherList handwasherList = handwasherLists.get(position);
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChooseHandwasher.class);
                    intent.putExtra("name", handwasherList.getHandwasherName());
                    intent.putExtra("contact", handwasherList.getContact());
                    context.startActivity(intent);
                }
            });

            itemHolder.name.setText(handwasherLists.get(position).getHandwasherName());
            itemHolder.contact.setText(handwasherLists.get(position).getContact());
            itemHolder.meters.setText(handwasherLists.get(position).getHwmeter());
       // }

        return convertView;
    }
    private class ItemHolder {
        TextView name;
        TextView contact;
        TextView meters;
        Button choose;
    }
}