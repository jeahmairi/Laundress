package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopPostsAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopPostsList> shopPostsLists;

    public ShopPostsAdapter(Context context, ArrayList<ShopPostsList> shopPostsLists) {
        this.context = context;
        this.shopPostsLists = shopPostsLists;
    }
    @Override
    public int getCount() {
        return shopPostsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopPostsLists.get(position);
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
            convertView = layoutInflater.inflate(R.layout.shop_post_adapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.tv_meters);
            itemHolder.message = (TextView) convertView.findViewById(R.id.tv_message);
            itemHolder.postDate = (TextView) convertView.findViewById(R.id.tv_postDate);
            itemHolder.contact = (TextView) convertView.findViewById(R.id.tv_contact);
            itemHolder.name.setText(shopPostsLists.get(position).getName());
            itemHolder.meters.setText(shopPostsLists.get(position).getMeters());
            itemHolder.message.setText(shopPostsLists.get(position).getMessage());
            itemHolder.postDate.setText(shopPostsLists.get(position).getPostDate());
            itemHolder.contact.setText(shopPostsLists.get(position).getContact());
        }

        return convertView;
    }

    private class ItemHolder{
        TextView name;
        TextView meters;
        TextView message;
        TextView postDate;
        TextView contact;
    }
}
