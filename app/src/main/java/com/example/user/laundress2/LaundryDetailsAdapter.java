package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LaundryDetailsAdapter extends BaseAdapter {
    private final Context context;
    ItemHolder itemHolder;
    ArrayList<LaundryDetailList> laundryDetailLists;

    public LaundryDetailsAdapter(Context context, ArrayList<LaundryDetailList> laundryDetailLists) {
        this.context = context;
        this.laundryDetailLists = laundryDetailLists;
    }

    @Override
    public int getCount() {
        return laundryDetailLists.size();
    }

    @Override
    public Object getItem(int position) {
        return laundryDetailLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      //  if (convertView == null) {
            LayoutInflater layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemHolder = new LaundryDetailsAdapter.ItemHolder();
            convertView = layoutInflater.inflate(R.layout.clntlaundrydet_layout, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.android_gridview_text);
            itemHolder.name.setText(laundryDetailLists.get(position).getName());

        //}

        return convertView;
    }
    private class ItemHolder {
        TextView name;
    }
}