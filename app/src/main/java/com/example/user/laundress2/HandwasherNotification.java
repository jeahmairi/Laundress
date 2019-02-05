package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class HandwasherNotification extends AppCompatActivity {
    ArrayList<Integer> arrclientid = new ArrayList<>();
    ArrayList<Integer> arrlspid = new ArrayList<>();
    ArrayList<Integer> arrtransno = new ArrayList<>();
    ArrayList<String> arrnotifmes= new ArrayList<>();
    ArrayList<HandwasherNotifList> handwasherNotifLists = new ArrayList<HandwasherNotifList>();
    HandwasherNotifAdapter handwasherNotifAdapter;
    private static final String URL_ALL ="http://192.168.254.117/laundress/allnotification.php";
    ListView lvnotif;
    String handwasher_name, client_name;
    String notification_Message;
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
        setContentView(R.layout.handwashernotification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lvnotif=findViewById(R.id.lvnotif);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");
        allCategory();

        lvnotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(handwasherNotifLists.get(position).getTable().equals("Notification")) {
                    Bundle extras = new Bundle();
                    extras.putString("client_name", handwasherNotifLists.get(position).getClient_name());
                    extras.putInt("trans_no", handwasherNotifLists.get(position).getTrans_no());
                    extras.putInt("client_id", handwasherNotifLists.get(position).getClient_id());
                    //extras.putString("client_name", client_name);
                    Intent intent = new Intent(HandwasherNotification.this, NotificationOnClick.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                } else{

                }
            }
        });
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
                                    notification_Message=jsonArray.getJSONObject(i).getString("notification_Message").toString();
                                    int lsp_ID= Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID").toString());
                                    int client_ID= Integer.parseInt(jsonArray.getJSONObject(i).getString("client_ID").toString());
                                    int trans_No= Integer.parseInt(jsonArray.getJSONObject(i).getString("trans_No").toString());
                                    client_name = jsonArray.getJSONObject(i).getString("client_name");
                                    String table = jsonArray.getJSONObject(i).getString("fromtable");
                                    HandwasherNotifList handwasherNotifList = new HandwasherNotifList();
                                    handwasherNotifList.setClient_id(client_ID);
                                    handwasherNotifList.setLsp_id(lsp_ID);
                                    handwasherNotifList.setTrans_no(trans_No);
                                    handwasherNotifList.setNotification_message(notification_Message);
                                    handwasherNotifList.setClient_name(client_name);
                                    handwasherNotifList.setTable(table);
                                    handwasherNotifLists.add(handwasherNotifList);
                                }
                                if(notification_Message.equals("Pending")){
                                    handwasherNotifAdapter = new HandwasherNotifAdapter(HandwasherNotification.this,handwasherNotifLists);
                                    lvnotif.setAdapter(handwasherNotifAdapter);
                                    handwasherNotifAdapter.notifyDataSetChanged();
                                }
                            }
                          //
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HandwasherNotification.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherNotification.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
