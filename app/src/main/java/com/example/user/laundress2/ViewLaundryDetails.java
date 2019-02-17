package com.example.user.laundress2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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

public class ViewLaundryDetails extends AppCompatActivity {
    LinearLayout llhori, llverti;
    String name;
    int trans_No, lsp_id, handwasher_id;
    private static final String URL_ALL="http://192.168.254.113/laundress/viewlaundrydetails.php";
    @Override
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
        setContentView(R.layout.viewlaundrydetails);
        //llhori = findViewById(R.id.llhori);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        llverti = findViewById(R.id.llverti);
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
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("launddet");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String detail_Count=jsonArray2.getJSONObject(i).getString("detail_Count").toString();
                                    String cinv_ItemTag=jsonArray2.getJSONObject(i).getString("cinv_ItemTag").toString();
                                    String description=jsonArray2.getJSONObject(i).getString("description").toString();
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                                    //llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori = new LinearLayout(ViewLaundryDetails.this);
                                    llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori.setPadding(5, 5, 5, 5);

                                    TextView tv1 = new TextView(ViewLaundryDetails.this);
                                    tv1.setText(cinv_ItemTag);
                                    tv1.setLayoutParams(params);
                                    tv1.setGravity(Gravity.CENTER);
                                    llhori.addView(tv1);

                                    TextView tv2 = new TextView(ViewLaundryDetails.this);
                                    tv2.setText(description);
                                    tv2.setLayoutParams(params);
                                    tv2.setGravity(Gravity.CENTER);
                                    llhori.addView(tv2);


                                    TextView tv = new TextView(ViewLaundryDetails.this);
                                    tv.setText(detail_Count);
                                    tv.setLayoutParams(params);
                                    tv.setGravity(Gravity.CENTER);
                                    llhori.addView(tv);
                                    llverti.addView(llhori);


                                   // Toast.makeText(ViewLaundryDetails.this, "detail_Count " + detail_Count+" cinv_ItemTag "+cinv_ItemTag+" description" +description,  Toast.LENGTH_SHORT).show();
                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ViewLaundryDetails.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ViewLaundryDetails.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewLaundryDetails.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
