package com.example.user.laundress2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AddLaundryDetailsAdapter extends BaseAdapter {
    private final Context context;
    AddLaundryDetailsAdapter.ItemHolder itemHolder;
    ArrayList<AddLaundryDetailList> addLaundryDetailLists;
    AddLaundryDetailList addLaundryDetailList;

    public AddLaundryDetailsAdapter(Context context, ArrayList<AddLaundryDetailList> addLaundryDetailLists) {
        this.context = context;
        this.addLaundryDetailLists = addLaundryDetailLists;
    }

    @Override
    public int getCount() {
        return addLaundryDetailLists.size();
    }

    @Override
    public Object getItem(int position) {
        return addLaundryDetailLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemHolder = new AddLaundryDetailsAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.addclientlaundrydet_adapter, parent, false);
        itemHolder.itemtag = (TextView) convertView.findViewById(R.id.itemtag);
        itemHolder.itemtag.setText(addLaundryDetailLists.get(position).getItemTag());
        itemHolder.itembrand = (TextView) convertView.findViewById(R.id.itembrand);
        itemHolder.itembrand.setText(addLaundryDetailLists.get(position).getItemBrand());
        itemHolder.itemcolor = (TextView) convertView.findViewById(R.id.itemcolor);
        itemHolder.itemcolor.setText(addLaundryDetailLists.get(position).getItemColor());
        itemHolder.itemdescription = (TextView) convertView.findViewById(R.id.itemdescription);
        itemHolder.itemdescription.setText(addLaundryDetailLists.get(position).getItemDescription());
        itemHolder.itemnoofpieces = (TextView) convertView.findViewById(R.id.itemnoofpieces);
        itemHolder.itemnoofpieces.setText(" " +addLaundryDetailLists.get(position).getItemNoofPieces());

        return convertView;
    }

    private class ItemHolder {
        TextView itemtag, itembrand, itemcolor, itemdescription, itemnoofpieces;
    }
}
