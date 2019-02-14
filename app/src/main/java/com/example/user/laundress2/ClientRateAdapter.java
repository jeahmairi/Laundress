package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientRateAdapter extends BaseAdapter {
    Context context;
    ClientRateAdapter.ItemHolder itemHolder;
    ArrayList<RateList> rateLists = new ArrayList<RateList>();

    public ClientRateAdapter(Context context, ArrayList<RateList> rateLists) {
        this.context = context;
        this.rateLists = rateLists;
    }

    @Override
    public int getCount() {
        return rateLists.size();
    }

    @Override
    public Object getItem(int position) {
        return rateLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ClientRateAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.clientrate_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.postdate = (TextView) convertView.findViewById(R.id.postdate);
        itemHolder.ratings =  convertView.findViewById(R.id.ratings);
        itemHolder.comment =  convertView.findViewById(R.id.comment);
        //final ClientPostList clientPostList=clientPostLists.get(position);

        itemHolder.name.setText(rateLists.get(position).getName());
        itemHolder.postdate.setText(rateLists.get(position).getDate());
        itemHolder.ratings.setRating(rateLists.get(position).getRating());
        itemHolder.comment.setText(rateLists.get(position).getComment());

        return convertView;
    }

    private class ItemHolder {
        TextView name, postdate,comment;
        RatingBar ratings;
    }
}
