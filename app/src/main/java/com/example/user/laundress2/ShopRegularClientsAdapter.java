package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopRegularClientsAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopRegularClientsList> shopRegularClientsLists;

    public ShopRegularClientsAdapter(Context context, ArrayList<ShopRegularClientsList> shopRegularClientsLists) {
        this.context = context;
        this.shopRegularClientsLists = shopRegularClientsLists;
    }

    @Override
    public int getCount() {
        return shopRegularClientsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopRegularClientsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ItemHolder();
        convertView = layoutInflater.inflate(R.layout.shop_regular_clients_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.bookings = (TextView) convertView.findViewById(R.id.bookings);
        itemHolder.imageView10 = (ImageView) convertView.findViewById(R.id.imageView10);
        itemHolder.rate = (RatingBar) convertView.findViewById(R.id.rate);
        //final ClientPostList clientPostList=clientPostLists.get(position);

        if(!shopRegularClientsLists.get(position).getImage().isEmpty()){
            Picasso.get().load(shopRegularClientsLists.get(position).getImage()).into(itemHolder.imageView10 );
        }
        itemHolder.name.setText(shopRegularClientsLists.get(position).getName());
        itemHolder.bookings.setText(shopRegularClientsLists.get(position).getBookings());
        itemHolder.rate.setRating(shopRegularClientsLists.get(position).getRate());
        return convertView;
    }
    private class ItemHolder {
        ImageView imageView10;
        TextView name, bookings;
        RatingBar rate;
    }
}