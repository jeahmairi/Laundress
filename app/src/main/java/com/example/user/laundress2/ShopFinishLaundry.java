package com.example.user.laundress2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class ShopFinishLaundry extends AppCompatActivity {
    int clientID, trans_No, shop_id, lsp_ID;
    String shop_name, cue;

    ArrayList<ShopRatingList> shopRatingLists = new ArrayList<>();
    private static final String URL_TRANS ="http://192.168.254.113/laundress/shop_finish_laundry.php";
    //private static final String URL_TRANS ="http://192.168.254.117/laundress/shop_finish_laundry.php";
    float prices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        clientID = extras.getInt("clientID");
        trans_No = extras.getInt("trans_No");
        shop_id = extras.getInt("shop_id");
        lsp_ID = extras.getInt("lsp_ID");
        shop_name = extras.getString("shop_name");
        cue = extras.getString("cue");

        if(cue.equals("goUpdate")) {
            updateTransStatus(trans_No, clientID);
            //addReceipt();
            //Toast.makeText(ShopFinishLaundry.this, " prices " +prices+" clientID " +clientID+" prices " +trans_No+" lsp_ID " +lsp_ID, Toast.LENGTH_SHORT).show();
        }
        else
            gotoIntent();
    }

    private void updateTransStatus(final int trans_Nos, final int clientIDs) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //Toast.makeText(ShopConfirmBooking.this, "sulod ", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(ShopFinishLaundry.this, "Laundry Finished", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putInt("client_id", clientID);
                                extras.putInt("trans_no", trans_No);
                                extras.putInt("id", shop_id);
                                extras.putString("name", shop_name);
                                extras.putInt("shop_id", shop_id);
                                extras.putString("shop_name", shop_name);
                                extras.putString("finished", "finished");
                                Intent intent = new Intent(ShopFinishLaundry.this, ShopHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ShopFinishLaundry.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopFinishLaundry.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("transNo", String.valueOf(trans_Nos));
                params.put("clientID", String.valueOf(clientIDs));
                params.put("shopID", String.valueOf(shop_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void gotoIntent(){
        Bundle extras = new Bundle();
        extras.putInt("client_id", clientID);
        extras.putInt("trans_no", trans_No);
        extras.putInt("id", shop_id);
        extras.putInt("lsp_ID", lsp_ID);
        extras.putString("name", shop_name);
        extras.putString("finished", "finished");
        Intent intent = new Intent(ShopFinishLaundry.this, ShopRateClient.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}