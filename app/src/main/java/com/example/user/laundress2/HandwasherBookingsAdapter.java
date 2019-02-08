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

import java.util.ArrayList;

public class HandwasherBookingsAdapter extends BaseAdapter {
    Context context;
    HandwasherBookingsAdapter.ItemHolder itemHolder;
    ArrayList<HandwasherBookingsList> handwasherBookingsLists;

    public HandwasherBookingsAdapter(Context context, ArrayList<HandwasherBookingsList> handwasherBookingsLists) {
        this.context = context;
        this.handwasherBookingsLists = handwasherBookingsLists;
    }

    @Override
    public int getCount() {
        return handwasherBookingsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return handwasherBookingsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new HandwasherBookingsAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.handwasherbooking_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.servicereq = (TextView) convertView.findViewById(R.id.servicereq);
        itemHolder.extraservice = (TextView) convertView.findViewById(R.id.extraservice);
        itemHolder.servicetype = (TextView) convertView.findViewById(R.id.servicetype);
        itemHolder.weight = (TextView) convertView.findViewById(R.id.weight);
        itemHolder.datetime = (TextView) convertView.findViewById(R.id.datetime);
        itemHolder.btnconfirmreq = convertView.findViewById(R.id.btnconfirmreq);
        itemHolder.btnviewlaundry = convertView.findViewById(R.id.btnviewlaundry);
        itemHolder.name.setText(handwasherBookingsLists.get(position).getName());
        itemHolder.servicereq.setText(handwasherBookingsLists.get(position).getServices());
        itemHolder.extraservice.setText(handwasherBookingsLists.get(position).getExtraservices());
        itemHolder.servicetype.setText(handwasherBookingsLists.get(position).getServicetype());
        itemHolder.weight.setText(handwasherBookingsLists.get(position).getWeight());
        itemHolder.datetime.setText(handwasherBookingsLists.get(position).getDatetime());
        final HandwasherBookingsList handwasherBookingsList = handwasherBookingsLists.get(position);
        itemHolder.btnconfirmreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("name", handwasherBookingsList.getName());
                extras.putInt("trans_No", handwasherBookingsList.getTrans_no());
                extras.putInt("lsp_id", handwasherBookingsList.getLsp_id());
                extras.putInt("handwasher_id", handwasherBookingsList.getHandwasher_id());
                Intent intent = new Intent(context, ConfirmBooking.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
        itemHolder.btnviewlaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*String message = handwasherBookingsLists.get(position).getNotification_message();
        if(message.equals("Pending")){
            itemHolder.status.setText("Requested your service.");
        }*/

        return convertView;
    }
    private class ItemHolder {
        TextView name, servicereq, extraservice, servicetype, weight, datetime;
        Button btnviewlaundry, btnconfirmreq;

    }
}
