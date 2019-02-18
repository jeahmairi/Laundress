package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class HandwasherAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<HandwasherList> handwasherLists;
    private ArrayList<HandwasherList> arraylist;


    public HandwasherAdapter(Context context,  ArrayList<HandwasherList> handwasherLists) {
        this.context = context;
        this.handwasherLists = handwasherLists;
        this.arraylist = new ArrayList<HandwasherList>();
        this.arraylist.addAll(handwasherLists);
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
            itemHolder.handwasherpic = (ImageView) convertView.findViewById(R.id.handwasherpic);
            itemHolder.contact = (TextView) convertView.findViewById(R.id.hwcont);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.hwmeters);
            itemHolder.choose = convertView.findViewById(R.id.btnchoose);

            final HandwasherList handwasherList = handwasherLists.get(position);
            if(handwasherList.getSort().equals("Nearest")){
                Collections.sort(handwasherLists, new CustomComparator());
            } else if(handwasherList.getSort().equals("Cheapest")) {
                Collections.sort(handwasherLists, new CustomComparator2());
            } else if(handwasherList.getSort().equals("Recommended")) {
                Collections.sort(handwasherLists, new CustomComparator3());
            }
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("name", handwasherList.getHandwasherName());
                    extras.putString("contact", handwasherList.getContact());
                    extras.putString("location", handwasherList.getHwmeter());
                    extras.putString("locations", String.valueOf(handwasherList.getHwlocation()));
                    extras.putInt("lsp_id", handwasherList.getLsp_id());
                    extras.putInt("client_id", handwasherList.getClient_id());
                    Intent intent = new Intent(context, ChooseHandwasher.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });

            //Picasso.get().load(handwasherLists.get(position).getPhoto()).into(itemHolder.handwasherpic);
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
        ImageView handwasherpic;
    }
    public class CustomComparator implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getHwmeter().compareTo(list2.getHwmeter());
        }
    }

    public class CustomComparator2 implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getPrice().compareTo(list2.getPrice());
        }
    }
    public class CustomComparator3 implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getReccom().compareTo(list2.getReccom());
        }
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        handwasherLists.clear();
        if (charText.length() == 0) {
            handwasherLists.addAll(arraylist);
        } else {
            for (HandwasherList wp : arraylist) {
                if (wp.getHwlocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    handwasherLists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}