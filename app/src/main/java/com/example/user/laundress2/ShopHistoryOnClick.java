package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

public class ShopHistoryOnClick extends AppCompatActivity {
    TextView tv_date, tv_clientName, tv_status, tv_serReq, tv_serExt, tv_serType, tv_weight, label;
    RatingBar rb_rate;
    String clientName,date, status, serReq, serExt, serType, weight;
    float rate;
    Button btn_viewdet;
    int trans_no, client_id, shop_id;
    LinearLayout llservoff, llextra;

    private static final String URL_ALL ="http://192.168.254.117/laundress/shop_history_more.php";

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.shop_history_view);
        tv_date = findViewById(R.id.tv_date);
        tv_clientName = findViewById(R.id.tv_clientName);
        tv_status = findViewById(R.id.tv_status);
        rb_rate = findViewById(R.id.ratingBar);
        label = findViewById(R.id.rateLabel);
        tv_serReq = findViewById(R.id.tv_requested);
        tv_serExt = findViewById(R.id.tv_extra);
        tv_serType = findViewById(R.id.tv_type);
        tv_weight = findViewById(R.id.tv_weight);
        btn_viewdet = findViewById(R.id.btn_viewrequest);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        shop_id = extras.getInt("shopID");
        clientName = extras.getString("client_Name");
        trans_no = extras.getInt("trans_No");
        date = extras.getString("date");
        client_id = extras.getInt("client_id");
        //Toast.makeText(ShopHistoryOnClick.this, "trans_no " + trans_no, Toast.LENGTH_SHORT).show();

        tv_clientName.setText(clientName);
        tv_date.setText(date);
        btn_viewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("trans_No", trans_no);
                Intent intent = new Intent(ShopHistoryOnClick.this, ViewLaundryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        allCategory();
    }

    private void allCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("moreHistory");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String trans_Service=jsonArray.getJSONObject(i).getString("trans_Service").toString();
                                    String trans_ExtService=jsonArray.getJSONObject(i).getString("trans_ExtService").toString();
                                    String trans_ServiceType=jsonArray.getJSONObject(i).getString("trans_ServiceType").toString();
                                    String trans_EstWeight=jsonArray.getJSONObject(i).getString("trans_EstWeight").toString();
                                    String trans_Status=jsonArray.getJSONObject(i).getString("trans_Status").toString();
                                    // Toast.makeText(ShopHistoryOnClick.this, "trans_no " + trans_Status, Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(ShopHistoryOnClick.this, "trans_no " + trans_no, Toast.LENGTH_SHORT).show();
                                    if(trans_Status.equals("Finished")){
                                        rate = Float.parseFloat(object.getString("rating_Score"));
                                        rb_rate.setVisibility(View.VISIBLE);
                                        label.setVisibility(View.VISIBLE);
                                        rb_rate.setEnabled(false);
                                    }
//                                    TextView tv = new TextView(ShopHistoryOnClick.this);
//                                    tv.setText(trans_Service);
//                                    tv.setPadding(10, 10, 10, 10);
//                                    llservoff.addView(tv);
//                                    TextView tv1 = new TextView(ShopHistoryOnClick.this);
//                                    tv1.setText(trans_ExtService);
//                                    tv1.setPadding(10, 10, 10, 10);
//                                    llextra.addView(tv1);
                                    rb_rate.setRating(rate);
                                    tv_status.setText(trans_Status);
                                    tv_serReq.setText(trans_Service);
                                    tv_serExt.setText(trans_ExtService);
                                    tv_serType.setText(trans_ServiceType);
                                    tv_weight.setText(trans_EstWeight);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopHistoryOnClick.this, "Catch Exception on All Notification " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopHistoryOnClick.this, "Response Error on All Notification " + error.toString(), Toast.LENGTH_SHORT).show();
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
