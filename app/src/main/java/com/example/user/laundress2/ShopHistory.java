package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ShopHistory extends AppCompatActivity {
    ListView history;
    ShopHistoryAdapter shopHistoryAdapter;
    ArrayList<ShopHistoryList> shopHistoryLists = new ArrayList<>();
    private Context context;
    //private static final String URL_ALL ="http://192.168.254.113/laundress/shop_history.php";
    private static final String URL_ALL ="http://192.168.254.117/laundress/shop_history.php";
    String shop_name;
    int shop_id;

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
        setContentView(R.layout.shop_history);
        history = findViewById(R.id.lvhistory);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        shop_name = extras.getString("shop_name");
        shop_id = extras.getInt("shop_id");
        allHistory();
    }
    private void allHistory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("shopHistory");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String name=jsonArray2.getJSONObject(i).getString("name").toString();
                                    String date=jsonArray2.getJSONObject(i).getString("date").toString();
                                    String weight=jsonArray2.getJSONObject(i).getString("weight").toString();
                                    float rating_Score= Float.parseFloat(jsonArray2.getJSONObject(i).getString("rating_Score").toString());
                                    int trans_No= Integer.parseInt(jsonArray2.getJSONObject(i).getString("trans_No").toString());

                                    ShopHistoryList shopHistoryList = new ShopHistoryList();
                                    shopHistoryList.setName(name);
                                    shopHistoryList.setDate(date);
                                    shopHistoryList.setLaundryweight(weight);
                                    shopHistoryList.setRatings(rating_Score);
                                    shopHistoryList.setTrans_No(trans_No);
                                    shopHistoryLists.add(shopHistoryList);
                                }
                                shopHistoryAdapter = new ShopHistoryAdapter(ShopHistory.this, shopHistoryLists);
                                history.setAdapter(shopHistoryAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopHistory.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopHistory.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("shop_id", String.valueOf(shop_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ShopHistory.this);
        requestQueue.add(stringRequest);
    }
}
