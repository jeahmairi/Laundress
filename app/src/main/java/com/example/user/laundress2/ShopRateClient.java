package com.example.user.laundress2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.HashMap;
import java.util.Map;

public class ShopRateClient extends AppCompatActivity {

    int clientID, trans_No, shop_id, lsp_ID;
    String shop_name, comments;
    float rating;
    private static final String URL_UPDATE ="http://192.168.254.113/laundress/shop_rateclient.php";
    private static String URL_ADDRECEIPT = "http://192.168.254.113/laundress/addreceipt.php";
    private static String URL_RECEIPT = "http://192.168.254.113/laundress/receipttransaction.php";
    private float prices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        clientID = extras.getInt("client_id");
        trans_No = extras.getInt("trans_no");
        shop_id = extras.getInt("id");
        lsp_ID = extras.getInt("lsp_ID");
        shop_name = extras.getString("name");
        showChangeLangDialog();
        allprice(trans_No);

    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShopRateClient.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.shop_rateclient, null);
        final RatingBar rate = dialogView.findViewById(R.id.ratings);
        final EditText comment = dialogView.findViewById(R.id.comment);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                rating = rate.getRating();
                comments = comment.getText().toString().trim();
                addRating(rating, comments);
                addReceipt();
            }
        });
        dialogBuilder.setNegativeButton("Don't Rate", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Bundle extras = new Bundle();
                extras.putInt("clientID", clientID);
                extras.putInt("trans_No", trans_No);
                extras.putInt("id", shop_id);
                extras.putString("name", shop_name);
                extras.putInt("shop_id", shop_id);
                extras.putString("shop_name", shop_name);
                extras.putString("cue", "goUpdate");
                Intent intent = new Intent(ShopRateClient.this, ShopFinishLaundry.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private void addReceipt() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDRECEIPT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ShopRateClient.this, "sud " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ShopRateClient.this, "Add Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopRateClient.this, "Add Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(clientID));
                params.put("handwasher_lspid", String.valueOf(lsp_ID));
                params.put("trans_No", String.valueOf(trans_No));
                params.put("price", String.valueOf(prices));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ShopRateClient.this);
        requestQueue.add(stringRequest);
    }

    private void allprice(final int trans_No){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RECEIPT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allreceipt");

                            if (success.equals("1")) {
                                //Toast.makeText(getActivity(), "sud" + success, Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    prices = Float.parseFloat(object.getString("prices"));
                                    //Toast.makeText(getActivity(), "sud" + prices, Toast.LENGTH_SHORT).show();
                                }
                            } else if (success.equals("0")) {
                                Toast.makeText(ShopRateClient.this, "failed: 1", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopRateClient.this, "failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopRateClient.this, "Login failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ShopRateClient.this);
        requestQueue.add(stringRequest);

    }
    private void addRating(final float rating, final String comments) {
//        Toast.makeText(ShopRate.this, "rate_no " +rate_no, Toast.LENGTH_SHORT).show();
//        Toast.makeText(ShopRate.this, "rating " +rating, Toast.LENGTH_SHORT).show();
//        Toast.makeText(ShopRate.this, "comments " +comments, Toast.LENGTH_SHORT).show();
//        Toast.makeText(ShopRate.this, "id " +client_id, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ShopRateClient.this, "Client was Rated Successfully", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putInt("clientID", clientID);
                                extras.putInt("trans_No", trans_No);
                                extras.putInt("id", shop_id);
                                extras.putString("name", shop_name);
                                extras.putInt("shop_id", shop_id);
                                extras.putString("shop_name", shop_name);
                                extras.putString("cue", "goUpdate");
                                Intent intent = new Intent(ShopRateClient.this, ShopFinishLaundry.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopRateClient.this, "Rate Failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopRateClient.this, "Rate Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ratingScore", String.valueOf(rating));
                params.put("ratingComment", comments);
                params.put("clientID", String.valueOf(clientID));
                params.put("shopID", String.valueOf(shop_id));
                params.put("transNo", String.valueOf(trans_No));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
