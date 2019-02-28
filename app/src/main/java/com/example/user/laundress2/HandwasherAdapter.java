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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HandwasherAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<HandwasherList> handwasherLists;
    private ArrayList<HandwasherList> arraylist;
    int client_id, lsp_id;
//private static String URL_ADD_CATEGORY = "http://192.168.254.113/laundress/addtofavorite.php";

    private static String URL_ADD_CATEGORY = "http://192.168.254.117/laundress/addtofavorite.php";

    public HandwasherAdapter(Context context,  ArrayList<HandwasherList> handwasherLists) {
        this.context = context;
        this.handwasherLists = handwasherLists;
        this.arraylist = new ArrayList<HandwasherList>();
        this.arraylist.addAll(handwasherLists);
    }
    @Override
    public int getCount() {
        return handwasherLists.size();
    }

    @Override
    public Object getItem(int position) {
        return handwasherLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       // if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();
            convertView = layoutInflater.inflate(R.layout.handwasheradapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.hwname);
            itemHolder.handwasherpic = (ImageView) convertView.findViewById(R.id.handwasherpic);
            itemHolder.contact = (TextView) convertView.findViewById(R.id.hwcont);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.hwmeters);
            itemHolder.choose = convertView.findViewById(R.id.btnchoose);
            itemHolder.favorites = convertView.findViewById(R.id.favorites);

            final HandwasherList handwasherList = handwasherLists.get(position);
            if(handwasherList.getSort().equals("Nearest")){
                Collections.sort(handwasherLists, new CustomComparator());
            } else if(handwasherList.getSort().equals("Cheapest")) {
                Collections.sort(handwasherLists, new CustomComparator2());
            } else if(handwasherList.getSort().equals("Recommended")) {
                Collections.sort(handwasherLists, new CustomComparator3());
            } else if(handwasherList.getSort().equals("Favorites")) {
                Collections.sort(handwasherLists, new CustomComparator4());
            }
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("name", handwasherList.getHandwasherName());
                    extras.putString("contact", handwasherList.getContact());
                    extras.putString("location", handwasherList.getHwmeter());
                    extras.putString("locations", String.valueOf(handwasherList.getHwlocation()));
                    extras.putInt("lsp_id", handwasherList.getLsp_id());
                    extras.putInt("client_id", handwasherList.getClient_id());
                    Intent intent = new Intent(context, ChooseHandwasher.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
            itemHolder.favorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Bundle extras = new Bundle();
                    extras.putInt("lsp_id", handwasherList.getLsp_id());
                    extras.putInt("client_id", handwasherList.getClient_id());
                    Intent intent = new Intent(context, AddToFav.class);
                    intent.putExtras(extras);
                    context.startActivity(intent);*/
                    client_id = handwasherList.getClient_id();
                    lsp_id = handwasherList.getLsp_id();
                    addtofavorites();
                }
            });

            //Picasso.get().load(handwasherLists.get(position).getPhoto()).into(itemHolder.handwasherpic);
            itemHolder.name.setText(handwasherLists.get(position).getHandwasherName());
            itemHolder.contact.setText(handwasherLists.get(position).getContact());
            itemHolder.meters.setText(handwasherLists.get(position).getHwmeter());
       // }

        return convertView;
    }

    private void addtofavorites() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                            }else if(success.equals("0")){
                                Toast.makeText(context, "Already in the favorites", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Add failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                params.put("lsp_id", String.valueOf(lsp_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private class ItemHolder {
        TextView name;
        TextView contact;
        TextView meters;
        Button choose;
        ImageView handwasherpic;
        ImageButton favorites;
    }
    public class CustomComparator implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getHwmeter().compareTo(list2.getHwmeter());
        }
    }

    public class CustomComparator2 implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getPrice().compareTo(list2.getPrice());
        }
    }
    public class CustomComparator3 implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getReccom().compareTo(list2.getReccom());
        }
    }
    public class CustomComparator4 implements Comparator<HandwasherList> {
        @Override
        public int compare(HandwasherList list1, HandwasherList list2) {
            return list1.getReccom().compareTo(String.valueOf(list2.getLsp_id()));
        }
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        handwasherLists.clear();
        if (charText.length() == 0) {
            handwasherLists.addAll(arraylist);
        } else {
            for (HandwasherList wp : arraylist) {
                if (wp.getHwlocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    handwasherLists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}