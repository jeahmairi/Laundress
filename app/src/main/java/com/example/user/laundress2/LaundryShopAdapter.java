package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LaundryShopAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<LaundryShopList> laundryShopLists;

    public LaundryShopAdapter(Context context, ArrayList<LaundryShopList> laundryShopLists) {
        this.context = context;
        this.laundryShopLists = laundryShopLists;
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
        //if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new LaundryShopAdapter.ItemHolder();
            convertView = layoutInflater.inflate(R.layout.laundryshopadapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.lsname);
            itemHolder.location = (TextView) convertView.findViewById(R.id.lslocation);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.lsmeters);
            itemHolder.choose = convertView.findViewById(R.id.btnchoose);
            final LaundryShopList laundryShopList=laundryShopLists.get(position);
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChooseLaundryShop.class);
                    intent.putExtra("name", laundryShopList.getName());
                    intent.putExtra("location", laundryShopList.getLocation());
                    context.startActivity(intent);
                }
            });

            itemHolder.name.setText(laundryShopLists.get(position).getName());
            itemHolder.location.setText(laundryShopLists.get(position).getLocation());
            itemHolder.meters.setText(laundryShopLists.get(position).getMeter());
        //}

        return convertView;
    }

    private class ItemHolder {
        TextView name;
        TextView location;
        TextView meters;
        Button choose;
    }
}
