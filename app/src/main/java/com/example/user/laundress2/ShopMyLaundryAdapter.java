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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShopMyLaundryAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopMyLaundryList> shopMyLaundryLists;

    public ShopMyLaundryAdapter(Context context, ArrayList<ShopMyLaundryList> shopMyLaundryLists) {
        this.context = context;
        this.shopMyLaundryLists = shopMyLaundryLists;
    }
    @Override
    public int getCount() {
        return shopMyLaundryLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopMyLaundryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();
            convertView = layoutInflater.inflate(R.layout.shop_laundries_adapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            itemHolder.contact = (TextView) convertView.findViewById(R.id.tv_phone);
            itemHolder.address = (TextView) convertView.findViewById(R.id.tv_address);
            itemHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
            itemHolder.viewReq = convertView.findViewById(R.id.btn_viewrequest);
            itemHolder.viewLaun = convertView.findViewById(R.id.btn_viewlaundry);
            itemHolder.finish = convertView.findViewById(R.id.btn_finish);

            final ShopMyLaundryList shopMyLaundryList = shopMyLaundryLists.get(position);
            itemHolder.viewReq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopVIewRequestDetails.class);
                    intent.putExtra("clientID", shopMyLaundryList.getClientID());
                    intent.putExtra("shopID", shopMyLaundryList.getShopID());
                    intent.putExtra("trans_No", shopMyLaundryList.getTransNo());
                    intent.putExtra("name", shopMyLaundryList.getName());
                    intent.putExtra("address", shopMyLaundryList.getAddress());
                    intent.putExtra("contact", shopMyLaundryList.getContact());
                    context.startActivity(intent);
                }
            });
            itemHolder.viewLaun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopViewLaundryDetails.class);
                    intent.putExtra("clientID", shopMyLaundryList.getClientID());
                    intent.putExtra("shopID", shopMyLaundryList.getShopID());
                    intent.putExtra("transNo", shopMyLaundryList.getTransNo());
                    intent.putExtra("name", shopMyLaundryList.getName());
                    intent.putExtra("address", shopMyLaundryList.getAddress());
                    intent.putExtra("contact", shopMyLaundryList.getContact());
                    context.startActivity(intent);
                }
            });
            itemHolder.finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putInt("clientID", shopMyLaundryList.getClientID());
                    extras.putInt("lsp_ID", shopMyLaundryList.getLspID());
                    extras.putInt("trans_No", shopMyLaundryList.getTransNo());
                    extras.putInt("shop_id", shopMyLaundryList.getShopID());
                    extras.putString("shop_name", shopMyLaundryList.getShopName());
                    extras.putString("cue", "noUpdate");
                    Intent intent = new Intent(context, ShopFinishLaundry.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });

            itemHolder.name.setText(shopMyLaundryLists.get(position).getName());
            itemHolder.address.setText(shopMyLaundryLists.get(position).getAddress());
            itemHolder.contact.setText(shopMyLaundryLists.get(position).getContact());
            itemHolder.date.setText(shopMyLaundryLists.get(position).getDate());

        return convertView;
    }

    private class ItemHolder {
        TextView name;
        TextView date;
        TextView address;
        TextView contact;
        Button viewReq;
        Button viewLaun;
        Button finish;
    }
}
