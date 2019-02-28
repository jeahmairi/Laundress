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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientMyAdapter extends BaseAdapter {
    Context context;
    ClientMyAdapter.ItemHolder itemHolder;
    ArrayList<ClientMyList> clientMyLists;

    public ClientMyAdapter(Context context, ArrayList<ClientMyList> clientMyLists) {
        this.context = context;
        this.clientMyLists = clientMyLists;
    }

    @Override
    public int getCount() {
        return clientMyLists.size();
    }

    @Override
    public Object getItem(int position) {
        return clientMyLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ClientMyAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.client_mylaundry_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
        itemHolder.contact = (TextView) convertView.findViewById(R.id.tv_phone);
        itemHolder.address = (TextView) convertView.findViewById(R.id.tv_address);
        itemHolder.status = (TextView) convertView.findViewById(R.id.status);
        itemHolder.viewReq = convertView.findViewById(R.id.btn_viewrequest);
        itemHolder.viewLaun = convertView.findViewById(R.id.btn_viewlaundry);
        itemHolder.btn_cancel = convertView.findViewById(R.id.btn_cancel);
        itemHolder.btn_claim = convertView.findViewById(R.id.btn_claim);
        itemHolder.btnmap = convertView.findViewById(R.id.btnmap);
        itemHolder.name.setText(clientMyLists.get(position).getName());
        itemHolder.contact.setText(clientMyLists.get(position).getContact());
        itemHolder.address.setText(clientMyLists.get(position).getAddress());
        itemHolder.status.setText(clientMyLists.get(position).getTrans_status());
        final ClientMyList clientMyList = clientMyLists.get(position);
        if(clientMyList.getTrans_status().equals("Pending")){
            itemHolder.btn_cancel.setVisibility(View.VISIBLE);
        } else if(clientMyList.getTrans_status().equals("Finished")){
            itemHolder.btn_cancel.setVisibility(View.GONE);
            itemHolder.btn_claim.setVisibility(View.VISIBLE);
        }

        itemHolder.btn_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",clientMyList.getTransNo());
                extras.putInt("lsp_id",clientMyList.getLspID());
                extras.putInt("client_id",clientMyList.getClientID());
                extras.putString("table",clientMyList.getTable());
                Intent intent = new Intent(context, ClaimDetails.class);
                intent.putExtras(extras);
                context.startActivity(intent);

            }
        });
        itemHolder.viewLaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",clientMyList.getTransNo());
                Intent intent = new Intent(context, ViewLaundryDetails.class);
                intent.putExtras(extras);
                context.startActivity(intent);

            }
        });
        itemHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",clientMyList.getTransNo());
                extras.putInt("client_id",clientMyList.getClientID());
                extras.putString("client_name",clientMyList.getClient_name());
                Intent intent = new Intent(context, CancelBooking.class);
                intent.putExtras(extras);
                context.startActivity(intent);

            }
        });
        itemHolder.viewReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No",clientMyList.getTransNo());
                Intent intent = new Intent(context, ViewRequestDetails.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
        itemHolder.btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",clientMyList.getName());
                extras.putString("handwasher_location", clientMyList.getAddress());
                extras.putString("handwasher_contact", clientMyList.getContact());
                Intent intent = new Intent(context, LaundryShopLocation.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    private class ItemHolder {
        TextView name;
        TextView status;
        TextView address;
        TextView contact;
        ImageButton btnmap;
        Button viewReq;
        Button viewLaun;
        Button btn_cancel;
        Button btn_claim;
    }
}
