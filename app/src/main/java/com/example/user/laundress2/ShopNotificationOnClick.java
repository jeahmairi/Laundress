package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

public class ShopNotificationOnClick extends AppCompatActivity {
    TextView status, name, extraservice, servicereq, servicetype, weight;
    ImageView photo;
    EditText estdatetime;
    String client_name,notif_message;
    Button accept, decline, btnviewdet;
    int trans_no, client_id;
    String handwasher_name;
    LinearLayout llservoff, llextra;
    int handwasher_id, handwasher_lspid;
    private static final String URL_ALL ="http://192.168.254.113/laundress/alltrans.php";
    private static final String URL_ALL_UPDATE ="http://192.168.254.113/laundress/updatetrans.php";
    private static final String URL_ALL_UPDATE_DECLINE ="http://192.168.254.113/laundress/updatetransdecline.php";
    private String image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosehandwashernotif);
        status = findViewById(R.id.status);
        name = findViewById(R.id.name);
        llextra = findViewById(R.id.llextra);
        llservoff = findViewById(R.id.llservoff);
        servicetype = findViewById(R.id.servicetype);
        weight = findViewById(R.id.weight);
        estdatetime = findViewById(R.id.estdatetime);
        accept = findViewById(R.id.accept);
        decline = findViewById(R.id.decline);
        btnviewdet = findViewById(R.id.btnviewdet);
        photo = findViewById(R.id.photo);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        notif_message = extras.getString("notif_message");
        image = extras.getString("image");
        trans_no = extras.getInt("trans_no");
        client_id = extras.getInt("client_id");
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");
        name.setText(client_name);
        Picasso.get().load(image).into(photo);
        if(notif_message.equals("Missed")){
            accept.setVisibility(View.GONE);
            decline.setVisibility(View.GONE);
            btnviewdet.setVisibility(View.GONE);
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransactionDecline();
            }
        });
        btnviewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("name", client_name);
                extras.putInt("trans_No", trans_no);
                extras.putInt("lsp_id", handwasher_id);
                extras.putInt("handwasher_id", handwasher_lspid);
                Intent intent = new Intent(ShopNotificationOnClick.this, ViewLaundryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        allCategory();
    }

    private void updateTransactionDecline() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_UPDATE_DECLINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Bundle extras = new Bundle();
                                extras.putString("name",handwasher_name);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", handwasher_lspid);
                                Intent intent = new Intent(ShopNotificationOnClick.this, ShopHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                                Toast.makeText(ShopNotificationOnClick.this, "Laundry Client Declined", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotificationOnClick.this, "Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_no", String.valueOf(trans_no));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateTransaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Bundle extras = new Bundle();
                                extras.putString("name",handwasher_name);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", handwasher_lspid);
                                Intent intent = new Intent(ShopNotificationOnClick.this, ShopHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                                Toast.makeText(ShopNotificationOnClick.this, "Laundry Client Accepted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotificationOnClick.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_no", String.valueOf(trans_no));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void allCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("allnotif");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    String trans_Service=jsonArray.getJSONObject(i).getString("trans_Service").toString();
                                    String trans_ExtService=jsonArray.getJSONObject(i).getString("trans_ExtService").toString();
                                    String trans_ServiceType=jsonArray.getJSONObject(i).getString("trans_ServiceType").toString();
                                    String trans_EstWeight=jsonArray.getJSONObject(i).getString("trans_EstWeight").toString();
                                    String trans_EstDateTime=jsonArray.getJSONObject(i).getString("trans_EstDateTime").toString();
                                    String trans_Status=jsonArray.getJSONObject(i).getString("trans_Status").toString();
                                    if(trans_Status.equals("Pending")){
                                        status.setText("Request your service.");
                                    }
                                    TextView tv = new TextView(ShopNotificationOnClick.this);
                                    tv.setText(trans_Service);
                                    tv.setPadding(10, 10, 10, 10);
                                    llservoff.addView(tv);
                                    TextView tv1 = new TextView(ShopNotificationOnClick.this);
                                    tv1.setText(trans_ExtService);
                                    tv1.setPadding(10, 10, 10, 10);
                                    llextra.addView(tv1);
                                    servicetype.setText(trans_ServiceType);
                                    weight.setText(trans_EstWeight);
                                    estdatetime.setText(trans_EstDateTime);
                                    estdatetime.setEnabled(false);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotificationOnClick.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_no", String.valueOf(trans_no));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
