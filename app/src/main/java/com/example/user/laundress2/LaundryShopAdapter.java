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

public class LaundryShopAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<LaundryShopList> laundryShopLists;
    private ArrayList<LaundryShopList> arraylist;
    int client_id, lsp_id;
//private static String URL_ADD_CATEGORY = "http://192.168.254.113/laundress/addtofavorite.php";

    private static String URL_ADD_CATEGORY = "http://192.168.254.117/laundress/addtofavorite.php";
    public LaundryShopAdapter(Context context, ArrayList<LaundryShopList> laundryShopLists) {
        this.context = context;
        this.laundryShopLists = laundryShopLists;
        this.arraylist = new ArrayList<LaundryShopList>();
        this.arraylist.addAll(laundryShopLists);
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
       // Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator());
        //if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new LaundryShopAdapter.ItemHolder();
            convertView = layoutInflater.inflate(R.layout.laundryshopadapter, parent, false);
            itemHolder.name = (TextView) convertView.findViewById(R.id.lsname);
            itemHolder.location = (TextView) convertView.findViewById(R.id.lslocation);
            itemHolder.meters = (TextView) convertView.findViewById(R.id.lsmeters);
            itemHolder.choose = convertView.findViewById(R.id.btnchoose);
            itemHolder.favorites = convertView.findViewById(R.id.favorites);
            final LaundryShopList laundryShopList=laundryShopLists.get(position);
        if(laundryShopList.getSort().equals("Nearest")){
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator());
        } else if(laundryShopList.getSort().equals("Cheapest")) {
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator2());
        } else if(laundryShopList.getSort().equals("Recommended")) {
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator3());
        } else if(laundryShopList.getSort().equals("Favorites")) {
            Collections.sort(laundryShopLists, new LaundryShopAdapter.CustomComparator4());
        }
        if(laundryShopList.getTable().equals("from shop")) {
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("name", laundryShopList.getName());
                    extras.putString("client_name", laundryShopList.getClient_name());
                    extras.putString("location", laundryShopList.getLocation());
                    extras.putString("contact", laundryShopList.getContact());
                    extras.putInt("id", laundryShopList.getId());
                    extras.putInt("lspid", laundryShopList.getLsp_id());
                    extras.putInt("client_id", laundryShopList.getClient_id());
                    extras.putString("openhours", laundryShopList.getOpenhours());
                    extras.putString("closehours", laundryShopList.getClosehours());
                    Intent intent = new Intent(context, ChooseLaundryShop.class);
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
                    client_id = laundryShopList.getClient_id();
                    lsp_id = laundryShopList.getLsp_id();
                    addtofavorites();
                }
            });
        } else if(laundryShopList.getTable().equals("from handwasher")){
            itemHolder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putString("name", laundryShopList.getName());
                    extras.putString("contact", laundryShopList.getContact());
                    extras.putString("location", laundryShopList.getMeter());
                    extras.putString("locations", String.valueOf(laundryShopList.getLocation()));
                    extras.putInt("lsp_id", laundryShopList.getLsp_id());
                    extras.putInt("client_id", laundryShopList.getClient_id());
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
                    client_id = laundryShopList.getClient_id();
                    lsp_id = laundryShopList.getLsp_id();
                    addtofavorites();
                }
            });
        }

            itemHolder.name.setText(laundryShopLists.get(position).getName());

            if(laundryShopList.getTable().equals("from shop")) {
                itemHolder.location.setText(laundryShopLists.get(position).getLocation());
            } else if(laundryShopList.getTable().equals("from handwasher")){
                itemHolder.location.setText(laundryShopLists.get(position).getMeter());
            }
            itemHolder.meters.setText(laundryShopLists.get(position).getContact());
        //}

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
        TextView location;
        TextView meters;
        Button choose;
        ImageButton favorites;
    }

    public class CustomComparator implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getMeter().compareTo(list2.getMeter());
        }
    }

    public class CustomComparator2 implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getPrice().compareTo(list2.getPrice());
        }
    }
    public class CustomComparator3 implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getRecomm().compareTo(list2.getRecomm());
        }
    }
    public class CustomComparator4 implements Comparator<LaundryShopList> {
        @Override
        public int compare(LaundryShopList list1, LaundryShopList list2) {
            return list1.getRecomm().compareTo(String.valueOf(list2.getId()));
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        laundryShopLists.clear();
        if (charText.length() == 0) {
            laundryShopLists.addAll(arraylist);
        } else {
            for (LaundryShopList wp : arraylist) {
                if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    laundryShopLists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
