package com.example.user.laundress2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HandwasherMyAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<HandwasherMyList> handwasherMyLists;
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updatetransactionfinish.php";
    private static String URL_ADDPOST = "http://192.168.254.117/laundress/addrateclient.php";
    private static String URL_ADDRECEIPT = "http://192.168.254.117/laundress/addreceipt.php";
    private static String URL_RECEIPT = "http://192.168.254.117/laundress/receipttransaction.php";
    float prices;
    private float rating;
    private String comments;
    private int client_ID, handwasher_lspid, trans_No;
    private int handwasher_id;
    private String name;

    public HandwasherMyAdapter(Context context, ArrayList<HandwasherMyList> handwasherMyLists) {
        this.context = context;
        this.handwasherMyLists = handwasherMyLists;
    }
    @Override
    public int getCount() {
        return handwasherMyLists.size();
    }

    @Override
    public Object getItem(int position) {
        return handwasherMyLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new HandwasherMyAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.shop_laundries_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
        itemHolder.contact = (TextView) convertView.findViewById(R.id.tv_phone);
        itemHolder.address = (TextView) convertView.findViewById(R.id.tv_address);
        //itemHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
        itemHolder.viewReq = convertView.findViewById(R.id.btn_viewrequest);
        itemHolder.viewLaun = convertView.findViewById(R.id.btn_viewlaundry);
        itemHolder.finish = convertView.findViewById(R.id.btn_finish);
        itemHolder.btnmap = convertView.findViewById(R.id.btnmap);
        itemHolder.photo = convertView.findViewById(R.id.photo);

        final HandwasherMyList handwasherMyList = handwasherMyLists.get(position);
        itemHolder.viewLaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewLaundryDetails.class);
                intent.putExtra("trans_No", handwasherMyList.getTransNo());
                context.startActivity(intent);
            }
        });
        itemHolder.viewReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewRequestDetails.class);
                intent.putExtra("trans_No", handwasherMyList.getTransNo());
                context.startActivity(intent);
            }
        });
        /*itemHolder.viewLaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConfirmLaundryDetails.class);
                intent.putExtra("clientID", shopMyLaundryList.getClientID());
                intent.putExtra("shopID", shopMyLaundryList.getShopID());
                intent.putExtra("transNo", shopMyLaundryList.getTransNo());
                intent.putExtra("name", shopMyLaundryList.getName());
                intent.putExtra("address", shopMyLaundryList.getAddress());
                intent.putExtra("contact", shopMyLaundryList.getContact());
                context.startActivity(intent);
            }
        });*/
        itemHolder.finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle extras = new Bundle();
                extras.putInt("clientID", shopMyLaundryList.getClientID());
                extras.putInt("lsp_ID", shopMyLaundryList.getLspID());
                extras.putInt("trans_No", shopMyLaundryList.getTransNo());
                extras.putInt("shop_id", shopMyLaundryList.getShopID());
                extras.putString("shop_name", shopMyLaundryList.getShopName());
                extras.putString("cue", "noUpdate");
                Intent intent = new Intent(context, ShopFinishLaundry.class);
                intent.putExtras(extras);
                context.startActivity(intent);*/
                client_ID = handwasherMyList.getClientID();
                handwasher_lspid = handwasherMyList.getLspID();
                handwasher_id = handwasherMyList.getId();
                trans_No = handwasherMyList.getTransNo();
                name = handwasherMyList.getName();
                allprice(trans_No);
                showChangeLangDialog();
            }
        });
        Picasso.get().load(handwasherMyList.getPhoto()).into(itemHolder.photo);
        itemHolder.name.setText(handwasherMyList.getName());
        itemHolder.address.setText(handwasherMyList.getAddress());
        itemHolder.contact.setText(handwasherMyList.getContact());
       // itemHolder.date.setText(handwasherMyList.getDate());
        itemHolder.btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",handwasherMyList.getName());
                extras.putString("handwasher_location", handwasherMyList.getAddress());
                extras.putString("handwasher_contact", handwasherMyList.getContact());
                Intent intent = new Intent(context, LaundryShopLocation.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate, null);
        final RatingBar rate = dialogView.findViewById(R.id.ratings);
        final EditText comment = dialogView.findViewById(R.id.comment);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                rating = rate.getRating();
                comments = comment.getText().toString().trim();
                //Toast.makeText(getActivity(), "rating " + rating+" comments "+comments+ " client_ID "+client_ID+ " handwasher_lspid "+handwasher_lspid + " trans_No "+trans_No+" price "+prices , Toast.LENGTH_LONG).show();
                addRating(client_ID, handwasher_lspid, trans_No, rating, comments);
                addReceipt(client_ID, handwasher_lspid, trans_No, prices);
                Bundle extras = new Bundle();
                extras.putString("name",name);
                extras.putInt("id", handwasher_id);
                extras.putInt("lspid", handwasher_lspid);
                Intent intent = new Intent(context, HandwasherHomepage.class);
                intent.putExtras(extras);
                context.startActivity(intent);
               // Toast.makeText(getActivity(), "rating " + rating+" comments "+comments+ " client_ID "+client_ID+ " handwasher_lspid "+handwasher_lspid + " trans_No "+trans_No , Toast.LENGTH_LONG).show();
/*                String updatemessage = message.getText().toString().trim();
                String location = showlocation.toString().trim();
                int post_no = clientPostLists.get(pos).getPost_no();
                updatePost(updatemessage, location, post_no);
                ClientMyPost.this.recreate();*/
            }
        });
        dialogBuilder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               updateTransactionFinish(trans_No);
                addReceipt(client_ID, handwasher_lspid, trans_No, prices);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addReceipt(final int client_ID, final int handwasher_lspid, final int trans_No, final float prices) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDRECEIPT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){

                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(context, "Add Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Add Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(client_ID));
                params.put("handwasher_lspid", String.valueOf(handwasher_lspid));
                params.put("trans_No", String.valueOf(trans_No));
                params.put("price", String.valueOf(prices));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void allprice(final int trans_No){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RECEIPT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allreceipt");

                            if (success.equals("1")) {
                                //Toast.makeText(getActivity(), "sud" + success, Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    prices = Float.parseFloat(object.getString("prices"));
                                    //Toast.makeText(getActivity(), "sud" + prices, Toast.LENGTH_SHORT).show();
                                }
                            } else if (success.equals("0")) {
                                Toast.makeText(context, "failed: 1", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Login failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    private void addRating(final int client_ID, final int handwasher_lspid, final int trans_No, final float rating, final String comments) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                updateTransactionFinish(trans_No);
                                //getActivity().recreate();
                               // Toast.makeText(getActivity(), "Added Successfully ", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(context, "Add Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Add Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(client_ID));
                params.put("handwasher_lspid", String.valueOf(handwasher_lspid));
                params.put("trans_No", String.valueOf(trans_No));
                params.put("rating", String.valueOf(rating));
                params.put("comments", comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void updateTransactionFinish(final int trans_No) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                // Toast.makeText(ClientM.this, "Post Updated Successfully", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    private class ItemHolder {
        TextView name;
        TextView date;
        TextView address;
        TextView contact;
        ImageButton btnmap;
        ImageView photo;
        Button viewReq;
        ImageButton viewLaun;
        Button finish;
    }
}
