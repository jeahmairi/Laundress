package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopRateAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopRatingList> shopRatingLists;

    public ShopRateAdapter(Context context, ArrayList<ShopRatingList> shopRatingLists) {
        this.context = context;
        this.shopRatingLists = shopRatingLists;
    }
    @Override
    public int getCount() {
        return shopRatingLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopRatingLists.get(position);
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
            convertView = layoutInflater.inflate(R.layout.shop_rate_adapter, parent, false);
            itemHolder.clientName = (TextView) convertView.findViewById(R.id.tv_clientName);
            itemHolder.rating = convertView.findViewById(R.id.ratings);
            itemHolder.comment = (TextView) convertView.findViewById(R.id.tv_comment);
            itemHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
            itemHolder.rateVal = (TextView) convertView.findViewById(R.id.rateval);
            itemHolder.clientName.setText(shopRatingLists.get(position).getClientName());
            itemHolder.comment.setText(shopRatingLists.get(position).getComment());
            itemHolder.date.setText(shopRatingLists.get(position).getDate());
            itemHolder.rating.setRating(shopRatingLists.get(position).getRate());
            //String rateValue = String.valueOf(shopRatingLists.get(position).getRate());
            //itemHolder.rateVal.setText(rateValue);
        }

        return convertView;
    }

    private class ItemHolder{
        TextView clientName;
        RatingBar rating;
        TextView comment;
        TextView date;
        TextView rateVal;
    }
}