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

public class ShopNotification extends AppCompatActivity {
    ArrayList<ShopNotificationList> shopNotificationLists = new ArrayList<>();
    ShopNotifAdapter shopNotifAdapter;
    private static final String URL_ALL ="http://192.168.254.113/laundress/shop_notification.php";
    //private static final String URL_ALL ="http://192.168.254.117/laundress/shop_notification.php";
    ListView lvnotif;
    String shop_name, client_name;
    String notification_Message;
    int shop_id, handwasher_lspid, rate_NO;
    String rating_Date, rating_Comment, comments;
    float rating_Cust_Service, rating_QualityService, rating_Overall, rating_Ontime;
    private String client_Photo;

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
        setContentView(R.layout.shop_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lvnotif=findViewById(R.id.lv_notification);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        shop_name = extras.getString("shop_name");
        shop_id = extras.getInt("shop_id");
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
                            JSONArray jsonArray = jsonObject.getJSONArray("shopNotification");
                            if (success.equals("1")){

                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    notification_Message=jsonArray.getJSONObject(i).getString("notification_Message");
                                    int lsp_ID= Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID"));
                                    int client_ID= Integer.parseInt(jsonArray.getJSONObject(i).getString("client_ID"));
                                    int trans_No= Integer.parseInt(jsonArray.getJSONObject(i).getString("trans_No"));
                                    client_name = jsonArray.getJSONObject(i).getString("client_Name");
                                    client_Photo = jsonArray.getJSONObject(i).getString("client_Photo");
                                    String table = jsonArray.getJSONObject(i).getString("fromtable");
                                    if(notification_Message.equals("Pending") || notification_Message.equals("Missed") || notification_Message.equals("Accepted by lsp") || notification_Message.equals("Declined by lsp") || notification_Message.equals("Finished")){

                                        ShopNotificationList shopNotificationList = new ShopNotificationList();
                                        shopNotificationList.setClientID(client_ID);
                                        shopNotificationList.setLspID(lsp_ID);
                                        shopNotificationList.setImage(client_Photo);
                                        if(notification_Message.equals("Finished")){
                                            rating_Cust_Service = Float.parseFloat(jsonArray.getJSONObject(i).getString("rating_Cust_Service"));
                                            rating_QualityService = Float.parseFloat(jsonArray.getJSONObject(i).getString("rating_QualityService"));
                                            rating_Ontime = Float.parseFloat(jsonArray.getJSONObject(i).getString("rating_Ontime"));
                                            rating_Overall = Float.parseFloat(jsonArray.getJSONObject(i).getString("rating_Overall"));
                                            rating_Comment = jsonArray.getJSONObject(i).getString("rating_Comment");
                                            rating_Date = jsonArray.getJSONObject(i).getString("rating_Date");
                                            rate_NO = Integer.parseInt(jsonArray.getJSONObject(i).getString("rating_No"));

                                            shopNotificationList.setRatingCustService(rating_Cust_Service);
                                            shopNotificationList.setRatingQualityService(rating_QualityService);
                                            shopNotificationList.setRatingOntime(rating_Ontime);
                                            shopNotificationList.setRatingOverall(rating_Overall);
                                            shopNotificationList.setComment(rating_Comment);
                                            shopNotificationList.setDateRate(rating_Date);
                                            shopNotificationList.setRateNo(rate_NO);

                                        }
                                        //if(notification_Message.equals("Pending") || notification_Message.equals("Missed")){
                                        lvnotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                // transno = handwasherNotifLists.get(position).getTrans_no();
                                                // showChangeLangDialog();
                                                if((shopNotificationLists.get(position).getMessage().equals("Pending"))||(shopNotificationLists.get(position).getMessage().equals("Missed"))){
                                                    Bundle extras = new Bundle();
                                                    extras.putString("handwasher_name",shop_name);
                                                    extras.putInt("handwasher_id", shop_id);
                                                    //extras.putInt("handwasher_lspid", handwasher_lspid);
                                                    extras.putString("client_name", shopNotificationLists.get(position).getClientName());
                                                    extras.putString("notif_message", shopNotificationLists.get(position).getMessage());
                                                    extras.putInt("trans_no", shopNotificationLists.get(position).getTransNo());
                                                    extras.putInt("client_id", shopNotificationLists.get(position).getClientID());

                                                    //extras.putString("client_name", client_name);
                                                    Intent intent = new Intent(ShopNotification.this, ShopNotificationOnClick.class);
                                                    intent.putExtras(extras);
                                                    startActivity(intent);
                                                }
                                               // Toast.makeText(ShopNotification.this, "sud " +position+"Message "+shopNotificationLists.get(position).getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        //}
                                        shopNotificationList.setTransNo(trans_No);
                                        shopNotificationList.setMessage(notification_Message);
                                        shopNotificationList.setClientName(client_name);
                                        shopNotificationList.setTable(table);
                                        shopNotificationLists.add(shopNotificationList);
                                    }
                                }
                                //if(notification_Message.equals("Pending") || notification_Message.equals("Missed")){
                                shopNotifAdapter = new ShopNotifAdapter(ShopNotification.this,shopNotificationLists);
                                lvnotif.setAdapter(shopNotifAdapter);

                                //}
                                shopNotifAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotification.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotification.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
