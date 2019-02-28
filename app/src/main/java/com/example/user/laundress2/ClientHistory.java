package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class ClientHistory extends AppCompatActivity {
    ListView history;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryList> historyLists = new ArrayList<HistoryList>();
    private Context context;
    //private static final String URL_ALL ="http://192.168.254.113/laundress/clienthistory.php";
    private static final String URL_ALL ="http://192.168.254.117/laundress/clienthistory.php";
    String client_name;
    int client_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clienthistory);
        history = findViewById(R.id.lvhistory);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");
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
                                    String weight=jsonArray2.getJSONObject(i).getString("weight").toString();
                                    float rating_Score= Float.parseFloat(jsonArray2.getJSONObject(i).getString("rating_Score").toString());
                                    int trans_No= Integer.parseInt(jsonArray2.getJSONObject(i).getString("trans_No").toString());

                                    HistoryList historyList = new HistoryList();
                                    historyList.setName(name);
                                    historyList.setDate(date);
                                    historyList.setLaundryweight(weight);
                                    historyList.setRatings(rating_Score);
                                    historyList.setTrans_No(trans_No);
                                    historyLists.add(historyList);
                                }
                                historyAdapter = new HistoryAdapter(ClientHistory.this, historyLists);
                                history.setAdapter(historyAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientHistory.this, "Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientHistory.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ClientHistory.this);
        requestQueue.add(stringRequest);
    }
}
