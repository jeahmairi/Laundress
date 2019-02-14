package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
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

public class HandwasherMyClients extends AppCompatActivity {
    ListView llclients;
    ViewClientsAdapter viewClientsAdapter;
    ArrayList<ViewClientsList> viewClientsLists = new ArrayList<ViewClientsList>();
    private String location, name, contact;
    private int lsp_id;
    private static final String URL_ALL_ClIENTS ="http://192.168.254.117/laundress/viewallclients.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewclients);
        llclients = findViewById(R.id.llclients);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        name = extras.getString("handwasher_name");
        lsp_id = extras.getInt("handwasher_lspid");
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
                                    float rate= Float.parseFloat(jsonArray.getJSONObject(i).getString("rate").toString());

                                    ViewClientsList viewClientsList = new ViewClientsList();
                                    viewClientsList.setName(name);
                                    viewClientsList.setBookings(bookings+" bookings");
                                    viewClientsList.setRate(rate);
                                    viewClientsLists.add(viewClientsList);
                                }
                                viewClientsAdapter = new ViewClientsAdapter(HandwasherMyClients.this,viewClientsLists);
                                llclients.setAdapter(viewClientsAdapter);
                            } else if(success.equals("0")) {
                                Toast.makeText(HandwasherMyClients.this, "error " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HandwasherMyClients.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherMyClients.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
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
