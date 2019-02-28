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
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopHistoryAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopHistoryList> shopHistoryLists;

    public ShopHistoryAdapter(Context context, ArrayList<ShopHistoryList> historyLists) {
        this.context = context;
        this.shopHistoryLists = historyLists;
    }

    @Override
    public int getCount() {
        return shopHistoryLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopHistoryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ItemHolder();
        convertView = layoutInflater.inflate(R.layout.shop_history_adapter, parent, false);
        itemHolder.date = convertView.findViewById(R.id.tv_date);
        itemHolder.clientName = convertView.findViewById(R.id.tv_clientName);
        itemHolder.status = convertView.findViewById(R.id.tv_status);
        itemHolder.date.setText(shopHistoryLists.get(position).getDate());
        itemHolder.clientName.setText(shopHistoryLists.get(position).getName());
        itemHolder.status.setText(shopHistoryLists.get(position).getStatus());
        return convertView;
    }

    private class ItemHolder {
        TextView date, clientName, status;
    }
}