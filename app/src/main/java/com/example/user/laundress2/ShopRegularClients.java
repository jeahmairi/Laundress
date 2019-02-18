package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopRegularClients extends AppCompatActivity {
    ListView llclients;
    ShopRegularClientsAdapter shopRegularClientsAdapter;
    ArrayList<ShopRegularClientsList> shopRegularClientsLists = new ArrayList<ShopRegularClientsList>();
    private String location, name, contact;
    private int lsp_id;
    //private static final String URL_ALL_ClIENTS ="http://192.168.254.113/laundress/shop_regularclients.php";
    private static final String URL_ALL_ClIENTS ="http://192.168.254.117/laundress/shop_regularclients.php";
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {

        // Write your code here

        super.onBackPressed();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.shop_regular_clients);
        llclients = findViewById(R.id.llclients);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        location = extras.getString("handwasher_location");
        name = extras.getString("shop_name");
        contact = extras.getString("handwasher_contact");
        lsp_id = extras.getInt("shop_id");
        allClientPost();
    }
    private void allClientPost() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_ClIENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allclientbooking");
                            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String bookings=jsonArray.getJSONObject(i).getString("bookings").toString();
                                    String photo=jsonArray.getJSONObject(i).getString("photo").toString();
                                    float rate= Float.parseFloat(jsonArray.getJSONObject(i).getString("rate").toString());
                                    //Toast.makeText(ShopRegularClients.this, "" +bookings, Toast.LENGTH_SHORT).show();

                                    ShopRegularClientsList shopRegularClientsList = new ShopRegularClientsList();
                                    shopRegularClientsList.setName(name);
                                    shopRegularClientsList.setImage(photo);
                                    shopRegularClientsList.setBookings(bookings+" bookings");
                                    shopRegularClientsList.setRate(rate);
                                    shopRegularClientsLists.add(shopRegularClientsList);
                                }
                                shopRegularClientsAdapter = new ShopRegularClientsAdapter(ShopRegularClients.this, shopRegularClientsLists);
                                llclients.setAdapter(shopRegularClientsAdapter);
                            } else if(success.equals("0")) {
                                Toast.makeText(ShopRegularClients.this, "error " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopRegularClients.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopRegularClients.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(lsp_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}