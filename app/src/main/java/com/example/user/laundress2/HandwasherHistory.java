package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class HandwasherHistory extends AppCompatActivity {
    ListView history;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryList> historyLists = new ArrayList<HistoryList>();
    private Context context;
    private static final String URL_ALL ="http://192.168.254.117/laundress/handwasherhistory.php";
    //private static final String URL_ALL ="http://192.168.254.117/laundress/handwasherhistory.php";
    String handwasher_name;
    int handwasher_id, handwasher_lspid;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        history = findViewById(R.id.lvhistory);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");
        //Toast.makeText(HandwasherHistory.this, "Failed" +handwasher_lspid, Toast.LENGTH_SHORT).show();
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
                            JSONArray jsonArray2 = jsonObject.getJSONArray("history");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String name=jsonArray2.getJSONObject(i).getString("name").toString();
                                    String date=jsonArray2.getJSONObject(i).getString("date").toString();
                                    String status=jsonArray2.getJSONObject(i).getString("trans_Status").toString();
                                    int client_ID= Integer.parseInt(jsonArray2.getJSONObject(i).getString("client_ID").toString());
                                    int trans_No= Integer.parseInt(jsonArray2.getJSONObject(i).getString("trans_No").toString());

                                    history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Bundle extras = new Bundle();
                                            extras.putInt("handwasher_lspid", handwasher_lspid);
                                            extras.putString("client_Name", historyLists.get(position).getName());
                                            extras.putInt("trans_No", historyLists.get(position).getTrans_No());
                                            extras.putString("date", historyLists.get(position).getDate());
                                            Intent intent = new Intent(HandwasherHistory.this, ShopHistoryOnClick.class);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                    });

                                    HistoryList historyList = new HistoryList();
                                    historyList.setName(name);
                                    historyList.setDate(date);
                                    historyList.setStatus(status);
                                    historyList.setClient_ID(client_ID);
                                    historyList.setTrans_No(trans_No);
                                    historyLists.add(historyList);
                                }
                                historyAdapter = new HistoryAdapter(HandwasherHistory.this, historyLists);
                                history.setAdapter(historyAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HandwasherHistory.this, "Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherHistory.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lspid", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(HandwasherHistory.this);
        requestQueue.add(stringRequest);
    }
}
