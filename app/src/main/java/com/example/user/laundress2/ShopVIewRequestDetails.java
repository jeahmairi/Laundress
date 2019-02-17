package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShopVIewRequestDetails extends AppCompatActivity {
    LinearLayout llreqserv, llxtrserv, servtype, estweight;
    EditText estdatetime;
    int trans_No;
    private static final String URL_ALL = "http://192.168.254.113/laundress/shop_viewrequestdetails.php";

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
        setContentView(R.layout.shop_view_request_details);
        llreqserv = findViewById(R.id.llreqserv);
        llxtrserv = findViewById(R.id.llxtrserv);
        servtype = findViewById(R.id.servtype);
        estweight = findViewById(R.id.estweight);
        estdatetime = findViewById(R.id.estdatetime);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        trans_No = extras.getInt("trans_No");
        allServices();
    }

    private void allServices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("ShopRequestDetails");
                            if (success.equals("1")) {
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    String trans_Service = jsonArray2.getJSONObject(i).getString("trans_Service");
                                    String trans_ExtService = jsonArray2.getJSONObject(i).getString("trans_ExtService");
                                    String trans_ServiceType = jsonArray2.getJSONObject(i).getString("trans_ServiceType");
                                    String trans_EstWeight = jsonArray2.getJSONObject(i).getString("trans_EstWeight");
                                    String trans_EstDateTime = jsonArray2.getJSONObject(i).getString("trans_EstDateTime");
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                                    //llhori.setOrientation(LinearLayout.HORIZONTAL);

                                    TextView tv = new TextView(ShopVIewRequestDetails.this);
                                    tv.setText(trans_Service);
                                    tv.setPadding(10, 10, 10, 10);
                                    llreqserv.addView(tv);
                                    TextView tv1 = new TextView(ShopVIewRequestDetails.this);
                                    tv1.setText(trans_ExtService);
                                    tv1.setPadding(10, 10, 10, 10);
                                    llxtrserv.addView(tv1);
                                    TextView tv2 = new TextView(ShopVIewRequestDetails.this);
                                    tv2.setText(trans_ServiceType);
                                    tv2.setPadding(10, 10, 10, 10);
                                    servtype.addView(tv2);
                                    TextView tv3 = new TextView(ShopVIewRequestDetails.this);
                                    tv3.setText(trans_EstWeight);
                                    tv3.setPadding(10, 10, 10, 10);
                                    estweight.addView(tv3);
                                    estdatetime.setText(trans_EstDateTime);
                                    estdatetime.setEnabled(false);

                                    // Toast.makeText(ViewLaundryDetails.this, "detail_Count " + detail_Count+" cinv_ItemTag "+cinv_ItemTag+" description" +description,  Toast.LENGTH_SHORT).show();
                                }
                            } else if (success.equals("0")) {
                                Toast.makeText(ShopVIewRequestDetails.this, "No data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ;
                            Toast.makeText(ShopVIewRequestDetails.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopVIewRequestDetails.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
