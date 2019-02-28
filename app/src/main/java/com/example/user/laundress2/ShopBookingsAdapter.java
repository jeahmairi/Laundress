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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class ShopBookingsAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopBookingsList> shopBookingsLists;

    public ShopBookingsAdapter(Context context, ArrayList<ShopBookingsList> shopBookingsLists) {
        this.context = context;
        this.shopBookingsLists = shopBookingsLists;
    }
    @Override
    public int getCount() {
        return shopBookingsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopBookingsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();
            convertView = layoutInflater.inflate(R.layout.shop_bookings_adpater, parent, false);
            itemHolder.name = convertView.findViewById(R.id.name);
            itemHolder.btnmap = convertView.findViewById(R.id.btnmap);
            itemHolder.photo = convertView.findViewById(R.id.photo);
            itemHolder.reqservice1 = convertView.findViewById(R.id.tv_reqservice1);
            itemHolder.extservice1 = convertView.findViewById(R.id.tv_extservice1);
            itemHolder.serviceType = convertView.findViewById(R.id.tv_servicetype);
            itemHolder.weight = convertView.findViewById(R.id.tv_weight);
            itemHolder.dateTime = convertView.findViewById(R.id.tv_datetime);
            itemHolder.viewLaundry = convertView.findViewById(R.id.btn_viewlaundry);
            //itemHolder.confirm = convertView.findViewById(R.id.btn_confirm);

            final ShopBookingsList shopBookingsList = shopBookingsLists.get(position);
        Picasso.get().load(shopBookingsList.getPhoto()).into(itemHolder.photo);
            itemHolder.viewLaundry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putInt("lsp_id", shopBookingsList.getId());
                    extras.putInt("trans_No", shopBookingsList.getTransNo());
                    extras.putInt("handwasher_id", shopBookingsList.getShopID());
                    extras.putString("name", shopBookingsList.getShopName());
                    Intent intent = new Intent(context, ConfirmLaundryDetailsShop.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
            /*itemHolder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle extras = new Bundle();
                    extras.putInt("lsp_id", shopBookingsList.getId());
                    extras.putInt("trans_No", shopBookingsList.getTransNo());
                    extras.putInt("handwasher_id", shopBookingsList.getShopID());
                    extras.putString("name", shopBookingsList.getShopName());
                    Intent intent = new Intent(context, ShopConfirmBooking.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });*/
            itemHolder.name.setText(shopBookingsLists.get(position).getName());
            itemHolder.reqservice1.setText(shopBookingsLists.get(position).getTransServ1());
            itemHolder.extservice1.setText(shopBookingsLists.get(position).getTransExtra1());
            itemHolder.serviceType.setText(shopBookingsLists.get(position).getTransServType());
            itemHolder.dateTime.setText(shopBookingsLists.get(position).getTransDateTime());
            itemHolder.weight.setText(" "+shopBookingsLists.get(position).getTransWeight());

        itemHolder.btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",shopBookingsList.getName());
                extras.putString("handwasher_location", shopBookingsList.getLocation());
                extras.putString("handwasher_contact", shopBookingsList.getContact());
                Intent intent = new Intent(context, LaundryShopLocation.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ItemHolder {
        TextView name;
        TextView reqservice1;
        TextView reqservice2;
        TextView reqservice3;
        TextView extservice1;
        TextView extservice2;
        TextView extservice3;
        TextView serviceType;
        TextView weight;
        TextView dateTime;
        ImageView photo;
        ImageButton btnmap;
        Button viewLaundry;
        Button confirm;
    }
}
