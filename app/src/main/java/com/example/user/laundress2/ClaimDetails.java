package com.example.user.laundress2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClaimDetails extends AppCompatActivity {
    LinearLayout llhori, llverti;
    String name, names, table;
    int trans_No, client_id, shop_id;
    Button btnconfirm;
    String rating_Date, rating_Comment, comments;
    float accommodation, qualityofservice, ontime, overall;
    ArrayList<String> allcheckid = new ArrayList<>();
//    private static final String URL_ALL="http://192.168.254.113/laundress/shop_viewlaundrydetails.php";
// private static final String URL_TRANS ="http://192.168.254.113/laundress/updatetransaction.php";

    private static final String URL_ALL="http://192.168.254.117/laundress/shop_viewlaundrydetails.php";
    private static final String URL_TRANS ="http://192.168.254.117/laundress/updatetransactions.php";
    private static String URL_ADDPOST = "http://192.168.254.117/laundress/addratehandwasher.php";
    private static String URL_ADDDISPUTE = "http://192.168.254.117/laundress/adddispute.php";
    private static String URL_ADDPOSTSHOP = "http://192.168.254.117/laundress/addrateshop.php";
    private int lsp_id, handwasher_id;
    Button btncancel;

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
        setContentView(R.layout.claimdetails);
        llverti = findViewById(R.id.llverti);
        btnconfirm = findViewById(R.id.btnconfirm);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        trans_No = extras.getInt("trans_No");
        lsp_id = extras.getInt("lsp_id");
        client_id = extras.getInt("client_id");
        table = extras.getString("table");
        allServices();
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransStatus();
                showChangeLangDialog();
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
                                    llhori = new LinearLayout(ClaimDetails.this);
                                    llhori.setOrientation(LinearLayout.HORIZONTAL);
                                    llhori.setPadding(5, 5, 5, 5);

                                    CheckBox cb = new CheckBox(ClaimDetails.this);
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
                                    ImageView tv = new ImageView(ClaimDetails.this);
                                    Picasso.get().load(cinv_ItemTag).into(tv);
                                    llhori.addView(tv);

                                    TextView tv1 = new TextView(ClaimDetails.this);
                                    tv1.setText(description);
                                    tv1.setLayoutParams(params);
                                    tv1.setGravity(Gravity.CENTER);
                                    llhori.addView(tv1);

                                    TextView tv2 = new TextView(ClaimDetails.this);
                                    tv2.setText(detail_Count);
                                    tv2.setLayoutParams(params);
                                    tv2.setGravity(Gravity.CENTER);
                                    llhori.addView(tv2);
                                    llverti.addView(llhori);
                                }
                            } else if(success.equals("0")){
                                Toast.makeText(ClaimDetails.this, "No data",  Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClaimDetails.this, " Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClaimDetails.this, "Failed " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ClaimDetails.this, "Laundry Claimed ", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClaimDetails.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClaimDetails.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

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
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClaimDetails.this);
        dialogBuilder.setMessage("Do you want to rate or you want to dispute?");
        dialogBuilder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(table.equals("Shop")){
                    showChangeLangDialog2();
                } else if(table.equals("Handwasher")) {
                    showChangeLangDialog1();
                }
            }
        });
        dialogBuilder.setNegativeButton("Dispute", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                showChangeLangDialog3();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public void showChangeLangDialog3() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClaimDetails.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.disputelsp, null);
        final EditText comment = dialogView.findViewById(R.id.comment);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Dispute", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                comments = comment.getText().toString().trim();
                addDispute(lsp_id, client_id,  trans_No, comments);
                //showChangeLangDialogReceipt();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showChangeLangDialog1() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClaimDetails.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rateclient, null);
        final RatingBar ratingaccom = dialogView.findViewById(R.id.ratingaccom);
        final RatingBar ratingqualityofservice = dialogView.findViewById(R.id.ratingqualityofservice);
        final RatingBar ratingontime = dialogView.findViewById(R.id.ratingontime);
        final RatingBar ratingoverall = dialogView.findViewById(R.id.ratingoverall);
        final EditText comment = dialogView.findViewById(R.id.comment);
        ratingaccom.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingaccom.setRating(rating);
            }
        });
        ratingqualityofservice.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingqualityofservice.setRating(rating);
            }
        });
        ratingontime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingontime.setRating(rating);
            }
        });
        ratingoverall.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingoverall.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                accommodation = ratingaccom.getRating();
                qualityofservice = ratingqualityofservice.getRating();
                ontime = ratingontime.getRating();
                overall = ratingoverall.getRating();
                comments = comment.getText().toString().trim();
                addRating(client_id, lsp_id, trans_No, accommodation, qualityofservice, ontime, overall, comments);
                //showChangeLangDialogReceipt();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public void showChangeLangDialog2() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClaimDetails.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rateshop, null);
        final RatingBar ratingaccom = dialogView.findViewById(R.id.ratingaccom);
        final RatingBar ratingqualityofservice = dialogView.findViewById(R.id.ratingqualityofservice);
        final RatingBar ratingontime = dialogView.findViewById(R.id.ratingontime);
        final RatingBar ratingoverall = dialogView.findViewById(R.id.ratingoverall);
        final EditText comment = dialogView.findViewById(R.id.comment);
        ratingaccom.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingaccom.setRating(rating);
            }
        });
        ratingqualityofservice.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingqualityofservice.setRating(rating);
            }
        });
        ratingontime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingontime.setRating(rating);
            }
        });
        ratingoverall.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingoverall.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                accommodation = ratingaccom.getRating();
                qualityofservice = ratingqualityofservice.getRating();
                ontime = ratingontime.getRating();
                overall = ratingoverall.getRating();
                comments = comment.getText().toString().trim();
                addRatingShop(client_id, lsp_id, trans_No, accommodation, qualityofservice, ontime, overall, comments);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private void addRatingShop(final int client_id, final int lsp_id,  final int trans_no, final float accommodation, final float qualityofservice, final float ontime, final float overall, final String comments) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDPOSTSHOP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                //getActivity().recreate();
                                Toast.makeText(ClaimDetails.this, "Your rate has been sent", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putString("name",names);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", lsp_id);
                                Intent intent = new Intent(ClaimDetails.this, HandwasherHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClaimDetails.this, "Rate Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClaimDetails.this, "Rate Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(client_id));
                params.put("handwasher_lspid", String.valueOf(lsp_id));
                params.put("trans_No", String.valueOf(trans_no));
                params.put("accommodation", String.valueOf(accommodation));
                params.put("qualityofservice", String.valueOf(qualityofservice));
                params.put("ontime", String.valueOf(ontime));
                params.put("overall", String.valueOf(overall));
                params.put("comments", comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ClaimDetails.this);
        requestQueue.add(stringRequest);

    }
    private void addRating(final int client_ID, final int handwasher_lspid, final int trans_No, final float accommodation, final float qualityofservice, final float ontime, final float overall, final String comments) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                //getActivity().recreate();
                                Toast.makeText(ClaimDetails.this, "Your rate has been sent", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putString("name",names);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", lsp_id);
                                Intent intent = new Intent(ClaimDetails.this, HandwasherHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClaimDetails.this, "Rate Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClaimDetails.this, "Rate Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(client_ID));
                params.put("handwasher_lspid", String.valueOf(handwasher_lspid));
                params.put("trans_No", String.valueOf(trans_No));
                params.put("accommodation", String.valueOf(accommodation));
                params.put("qualityofservice", String.valueOf(qualityofservice));
                params.put("ontime", String.valueOf(ontime));
                params.put("overall", String.valueOf(overall));
                params.put("comments", comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ClaimDetails.this);
        requestQueue.add(stringRequest);
    }

    private void addDispute(final int handwasher_lspid, final int client_ID,  final int trans_No, final String comments) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDDISPUTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                //getActivity().recreate();
                                Toast.makeText(ClaimDetails.this, "Your rate has been sent", Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putString("name",names);
                                extras.putInt("id", handwasher_id);
                                extras.putInt("lspid", lsp_id);
                                Intent intent = new Intent(ClaimDetails.this, HandwasherHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClaimDetails.this, "Rate Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClaimDetails.this, "Rate Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("handwasher_lspid", String.valueOf(handwasher_lspid));
                params.put("client_ID", String.valueOf(client_ID));
                params.put("trans_No", String.valueOf(trans_No));
                params.put("comments", comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ClaimDetails.this);
        requestQueue.add(stringRequest);
    }
}

