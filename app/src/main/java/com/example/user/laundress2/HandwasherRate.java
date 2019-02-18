package com.example.user.laundress2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class HandwasherRate extends AppCompatActivity {
    ImageView picture;
    TextView rateval;
    RatingBar ratings;
    ListView allratings;
    String handwasher_name, comments;
    float rating;
    int pos, rate_no;
    int handwasher_id, handwasher_lspid;
    HandwasherRateAdapter clientRateAdapter;
    ArrayList<RateList> rateLists = new ArrayList<RateList>();
    private static final String URL_ALL ="http://192.168.254.113/laundress/allratehandwasher.php";
    private static final String URL_UPDATE ="http://192.168.254.113/laundress/updateratehandwasher.php";
    /*private static final String URL_ALL ="http://192.168.254.117/laundress/allratehandwasher.php";
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updateratehandwasher.php";*/
    float average;

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
        setContentView(R.layout.handwasherrate);
        picture = findViewById(R.id.picture);
        rateval = findViewById(R.id.rateval);
        ratings = findViewById(R.id.ratings);
        allratings = findViewById(R.id.allratings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");
        allratings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                showChangeLangDialog();
            }
        });
        allRatings();
    }
    private void allRatings() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allrate");
                            //Toast.makeText(Login.this, "sud" + success, Toast.LENGTH_SHORT).show();
                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int rating_No = Integer.parseInt(object.getString("rating_No"));
                                    float rating_Accommodation = Float.parseFloat(object.getString("rating_Accommodation"));
                                    float rating = Float.parseFloat(object.getString("rating_Score"));
                                    float rating_QualityService = Float.parseFloat(object.getString("rating_QualityService"));
                                    float rating_Ontime = Float.parseFloat(object.getString("rating_Ontime"));
                                    float rating_Overall = Float.parseFloat(object.getString("rating_Overall"));
                                    String client_Photo = object.getString("handwasher_Photo");
                                    average = Float.parseFloat(object.getString("rating_Overall"));
                                    Picasso.get().load(client_Photo).into(picture);
                                    String rating_Comment = object.getString("rating_Comment");
                                    String rating_Date= object.getString("rating_Date");
                                    String name = object.getString("name");

                                    RateList rateList = new RateList();
                                    rateList.setRate_no(rating_No);
                                    rateList.setRating(rating);
                                    rateList.setAccommodation(rating_Accommodation);
                                    rateList.setQualityofservice(rating_QualityService);
                                    rateList.setOntime(rating_Ontime);
                                    rateList.setOverall(rating_Overall);
                                    rateList.setComment(rating_Comment);
                                    rateList.setDate(rating_Date);
                                    rateList.setName(name);
                                    rateLists.add(rateList);
                                }
                                ratings.setRating(average);
                                String ave = String.valueOf(average);
                                rateval.setText(ave);
                                clientRateAdapter = new HandwasherRateAdapter(HandwasherRate.this,rateLists);
                                allratings.setAdapter(clientRateAdapter);
                                //Toast.makeText(ClientRate.this, "trans_Status " + trans_Status, Toast.LENGTH_SHORT).show();

                            } else if (success.equals("0")) {
                                Toast.makeText(HandwasherRate.this, "failed: ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HandwasherRate.this, "failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherRate.this, "Login failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lsp_id", String.valueOf(handwasher_lspid));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HandwasherRate.this);
        requestQueue.add(stringRequest);

    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HandwasherRate.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate, null);
        final RatingBar rate = dialogView.findViewById(R.id.ratings);
        final EditText comment = dialogView.findViewById(R.id.comment);
        comment.setText(rateLists.get(pos).getComment());
        rate.setRating(rateLists.get(pos).getRating());
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rate_no = rateLists.get(pos).getRate_no();
                rating = rate.getRating();
                comments = comment.getText().toString().trim();
                updateRating(rating, comments, rate_no);
            }
        });
        dialogBuilder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void updateRating(final float rating, final String comments, final int rate_no) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(HandwasherRate.this, "Rate Updated Successfully", Toast.LENGTH_SHORT).show();
                                HandwasherRate.this.recreate();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HandwasherRate.this, "Rate Update failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherRate.this, "Rate Update failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("rating", String.valueOf(rating));
                params.put("comments", comments);
                params.put("rate_no", String.valueOf(rate_no));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
