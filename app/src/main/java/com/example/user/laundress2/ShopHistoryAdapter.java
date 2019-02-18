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
        itemHolder.date = convertView.findViewById(R.id.date);
        itemHolder.washername = convertView.findViewById(R.id.washername);
        itemHolder.laundryweight = convertView.findViewById(R.id.laundryweight);
        itemHolder.ratings = convertView.findViewById(R.id.ratings);
        itemHolder.btnviewlaundrydet = convertView.findViewById(R.id.btnviewlaundrydet);
        itemHolder.btnviewlaundrydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",shopHistoryLists.get(position).getTrans_No());
                Intent intent = new Intent(context, ViewLaundryDetails.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
        itemHolder.date.setText(shopHistoryLists.get(position).getDate());
        itemHolder.washername.setText(shopHistoryLists.get(position).getName());
        itemHolder.laundryweight.setText(shopHistoryLists.get(position).getLaundryweight());
        itemHolder.ratings.setRating(shopHistoryLists.get(position).getRatings());
        return convertView;
    }

    private class ItemHolder {
        TextView date, washername, laundryweight;
        RatingBar ratings;
        Button btnviewlaundrydet;
    }
}