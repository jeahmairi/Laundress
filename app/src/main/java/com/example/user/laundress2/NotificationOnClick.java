package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class NotificationOnClick extends AppCompatActivity {
    TextView status, name, extraservice, servicereq, servicetype, weight;
    EditText estdatetime;
    String client_name;
    Button accept, decline;
    int trans_no, client_id;
    private static final String URL_ALL ="http://192.168.254.117/laundress/alltrans.php";
    private static final String URL_ALL_UPDATE ="http://192.168.254.117/laundress/updatetrans.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosehandwashernotif);
        status = findViewById(R.id.status);
        name = findViewById(R.id.name);
        extraservice = findViewById(R.id.extraservice);
        servicereq = findViewById(R.id.servicereq);
        servicetype = findViewById(R.id.servicetype);
        weight = findViewById(R.id.weight);
        estdatetime = findViewById(R.id.estdatetime);
        accept = findViewById(R.id.accept);
        decline = findViewById(R.id.decline);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        trans_no = extras.getInt("trans_no");
        client_id = extras.getInt("client_id");
        name.setText(client_name);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        allCategory();
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

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NotificationOnClick.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                    extraservice.setText(trans_ExtService);
                                    servicereq.setText(trans_Service);
                                    servicetype.setText(trans_ServiceType);
                                    weight.setText(trans_EstWeight);
                                    estdatetime.setText(trans_EstDateTime);
                                    estdatetime.setEnabled(false);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NotificationOnClick.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
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
