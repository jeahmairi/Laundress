package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    HistoryAdapter.ItemHolder itemHolder;
    ArrayList<HistoryList> historyLists;

    public HistoryAdapter(Context context, ArrayList<HistoryList> historyLists) {
        this.context = context;
        this.historyLists = historyLists;
    }

    @Override
    public int getCount() {
        return historyLists.size();
    }

    @Override
    public Object getItem(int position) {
        return historyLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new HistoryAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.clienthistory_adapter, parent, false);
        itemHolder.date = convertView.findViewById(R.id.date);
        itemHolder.washername = convertView.findViewById(R.id.washername);
        itemHolder.laundryweight = convertView.findViewById(R.id.laundryweight);
        itemHolder.ratings = convertView.findViewById(R.id.ratings);
        itemHolder.btnviewlaundrydet = convertView.findViewById(R.id.btnviewlaundrydet);
        itemHolder.btnviewlaundrydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        itemHolder.date.setText(historyLists.get(position).getDate());
        itemHolder.washername.setText(historyLists.get(position).getName());
        itemHolder.laundryweight.setText(historyLists.get(position).getLaundryweight());
        itemHolder.ratings.setRating(historyLists.get(position).getRatings());
        return convertView;
    }

    private class ItemHolder {
        TextView date, washername, laundryweight;
        RatingBar ratings;
        Button btnviewlaundrydet;

    }
}
