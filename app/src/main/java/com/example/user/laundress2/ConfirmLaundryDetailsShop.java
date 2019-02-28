package com.example.user.laundress2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmLaundryDetailsShop extends AppCompatActivity {
    LinearLayout llhori, llverti;
    String name, names;
    int trans_No, client_id, shop_id;
    Button btnconfirm, btncancel;
    ArrayList<String> allcheckid = new ArrayList<>();
//    private static final String URL_ALL="http://192.168.254.113/laundress/shop_viewlaundrydetails.php";
// private static final String URL_TRANS ="http://192.168.254.113/laundress/updatetransaction.php";

    private static final String URL_ALL="http://192.168.254.117/laundress/shop_viewlaundrydetails.php";
    private static final String URL_TRANS ="http://192.168.254.117/laundress/updatetransaction.php";
    private int lsp_id, handwasher_id;

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
        setContentView(R.layout.shop_view_laundry_details);
        llverti = findViewById(R.id.llverti);
        btnconfirm = findViewById(R.id.btnconfirm);
        btncancel = findViewById(R.id.btncancel);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        trans_No = extras.getInt("trans_No");
        lsp_id = extras.getInt("lsp_id");
        handwasher_id = extras.getInt("handwasher_id");
        names = extras.getString("name");
        allServices();
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransStatus();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("name",names);
                extras.putInt("id", handwasher_id);
                extras.putInt("trans_No", trans_No);
                Intent intent = new Intent(ConfirmLaundryDetailsShop.this, CancelBookingShop.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
    private void allServices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("ShopLaundryDetails");
                            if (success.equals("1")){
                                //CheckBox[] cbserviceoffered = new CheckBox[jsonArray2.length()];
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    int cinv_ID= Integer.parseInt(jsonArray2.getJSONObject(i).getString("cinv_ID").toString());
                                    String detail_Count=jsonArray2.getJSONObject(i).getString("detail_Count").toString();
                                    String cinv_ItemTag=jsonArray2.getJSONObject(i).getString("cinv_ItemTag").toString();
                                    String description=jsonArray2.getJSONObject(i).getString("description").toString();
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                                    //llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori = new LinearLayout(ConfirmLaundryDetailsShop.this);
                                    llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori.setPadding(5, 5, 5, 5);

                                    CheckBox cb = new CheckBox(ConfirmLaundryDetailsShop.this);
                                    cb.setChecked(false);
                                    cb.setTag(cinv_ID);
                                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                int extraid = Integer.parseInt(buttonView.getTag().toString());
                                                //Toast.makeText(ChooseLaundryShop.this, "service offered" + servicesOffered,  Toast.LENGTH_SHORT).show();
                                                if(allcheckid.indexOf(extraid)< 0 ){
                                                    allcheckid.add(String.valueOf(extraid));
                                                } else{
                                                    int extraids = Integer.parseInt(buttonView.getTag().toString());
                                                    allcheckid.remove(extraids);
                                                }
                                            }
                                            /*String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                                            Toast.makeText(ChooseLaundryShop.this, msg, Toast.LENGTH_SHORT).show();*/
                                        }
                                    });

                                    llhori.addView(cb);
                                    ImageView tv = new ImageView(ConfirmLaundryDetailsShop.this);
                                    Picasso.get().load(cinv_ItemTag).into(tv);
                                    llhori.addView(tv);

                                    TextView tv1 = new TextView(ConfirmLaundryDetailsShop.this);
                                    tv1.setText(description);
                                    tv1.setLayoutParams(params);
                                    tv1.setGravity(Gravity.CENTER);
                                    llhori.addView(tv1);

                                    TextView tv2 = new TextView(ConfirmLaundryDetailsShop.this);
                                    tv2.setText(detail_Count);
                                    tv2.setLayoutParams(params);
                                    tv2.setGravity(Gravity.CENTER);
                                    llhori.addView(tv2);
                                    llverti.addView(llhori);
                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ConfirmLaundryDetailsShop.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ConfirmLaundryDetailsShop.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmLaundryDetailsShop.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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
    private void updateTransStatus() {
        final JSONArray cinvid = new JSONArray();
        for(int i = 0; i < allcheckid.size(); i++)
        {
            JSONObject invid = new JSONObject();
            try {
                invid.put("cinvid", allcheckid.get(i).toString());
                cinvid.put(invid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TRANS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                //ConfirmLaundryDetails.this.recreate();
                                Toast.makeText(ConfirmLaundryDetailsShop.this, "Laundry Details Confirmed ", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putString("name",names);
                                extras.putInt("id", handwasher_id);
                                //extras.putInt("lspid", lsp_id);
                                Intent intent = new Intent(ConfirmLaundryDetailsShop.this, ShopHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ConfirmLaundryDetailsShop.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmLaundryDetailsShop.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                params.put("cinv_id", cinvid.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
