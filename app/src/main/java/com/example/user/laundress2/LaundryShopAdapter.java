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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class LaundryShopAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<LaundryShopList> laundryShopLists;
    private ArrayList<LaundryShopList> arraylist;

    public LaundryShopAdapter(Context context, ArrayList<LaundryShopList> laundryShopLists) {
        this.context = context;
        this.laundryShopLists = laundryShopLists;
        this.arraylist = new ArrayList<LaundryShopList>();
        this.arraylist.addAll(laundryShopLists);
    }
    @Override
    public int getCount() {
        return laundryShopLists.size();
    }

    @Override
    public Object getItem(int position) {
        return laundryShopLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator());
        //if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new LaundryShopAdapter.ItemHolder();
            convertView = layoutInflater.inflate(R.layout.laundryshopadapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.lsname);
            itemHolder.location = (TextView) convertView.findViewById(R.id.lslocation);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.lsmeters);
            itemHolder.choose = convertView.findViewById(R.id.btnchoose);
            final LaundryShopList laundryShopList=laundryShopLists.get(position);
        if(laundryShopList.getSort().equals("Nearest")){
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator());
        } else if(laundryShopList.getSort().equals("Cheapest")) {
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator2());
        } else if(laundryShopList.getSort().equals("Recommended")) {
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator3());
        }
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();

                    extras.putString("name", laundryShopList.getName());
                    extras.putString("client_name", laundryShopList.getClient_name());
                    extras.putString("location", laundryShopList.getLocation());
                    extras.putString("contact", laundryShopList.getContact());
                    extras.putInt("id", laundryShopList.getId());
                    extras.putInt("lspid", laundryShopList.getLsp_id());
                    extras.putInt("client_id", laundryShopList.getClient_id());
                    extras.putString("openhours", laundryShopList.getOpenhours());
                    extras.putString("closehours", laundryShopList.getClosehours());
                    Intent intent = new Intent(context, ChooseLaundryShop.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });

            itemHolder.name.setText(laundryShopLists.get(position).getName());
            itemHolder.location.setText(laundryShopLists.get(position).getLocation());
            itemHolder.meters.setText(laundryShopLists.get(position).getContact());
        //}

        return convertView;
    }

    private class ItemHolder {
        TextView name;
        TextView location;
        TextView meters;
        Button choose;
    }

    public class CustomComparator implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getMeter().compareTo(list2.getMeter());
        }
    }

    public class CustomComparator2 implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getPrice().compareTo(list2.getPrice());
        }
    }
    public class CustomComparator3 implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getRecomm().compareTo(list2.getRecomm());
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        laundryShopLists.clear();
        if (charText.length() == 0) {
            laundryShopLists.addAll(arraylist);
        } else {
            for (LaundryShopList wp : arraylist) {
                if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    laundryShopLists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
