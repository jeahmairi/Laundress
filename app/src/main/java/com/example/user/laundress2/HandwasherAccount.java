package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class HandwasherAccount extends AppCompatActivity {
    EditText fname, midname, lname, address, bdate, phonenumber, email, password;
    RadioGroup radioGender;
    Spinner civilstatus;
    DatePickerDialog picker;
    String handwasher_name;
    int handwasher_id, handwasher_lspid;

    private static final String URL_ALL ="http://192.168.254.117/laundress/handwasheraccount.php";

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
        setContentView(R.layout.handwasheraccount);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fname = findViewById(R.id.fname);
        midname = findViewById(R.id.midname);
        lname = findViewById(R.id.lname);
        address = findViewById(R.id.address);
        bdate = findViewById(R.id.bdate);
        phonenumber = findViewById(R.id.phonenumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        radioGender = findViewById(R.id.radioGender);
        civilstatus = findViewById(R.id.civilstatus);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");

        allHandwasher();
    }

    private void allHandwasher() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("handwasher");
                            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int handwasher_ID = Integer.parseInt(jsonArray.getJSONObject(i).getString("handwasher_ID"));
                                    String handwasher_FName=jsonArray.getJSONObject(i).getString("handwasher_FName").toString();
                                    String handwasher_MidName=jsonArray.getJSONObject(i).getString("handwasher_MidName").toString();
                                    String handwasher_LName=jsonArray.getJSONObject(i).getString("handwasher_LName").toString();
                                    String handwasher_Address=jsonArray.getJSONObject(i).getString("handwasher_Address").toString();
                                    String handwasher_BDate=jsonArray.getJSONObject(i).getString("handwasher_BDate").toString();
                                    String handwasher_Gender=jsonArray.getJSONObject(i).getString("handwasher_Gender").toString();
                                    String handwasher_CivilStatus=jsonArray.getJSONObject(i).getString("handwasher_CivilStatus").toString();
                                    String handwasher_Contact=jsonArray.getJSONObject(i).getString("handwasher_Contact").toString();
                                    String handwasher_Username=jsonArray.getJSONObject(i).getString("handwasher_Username").toString();
                                    String handwasher_Password=jsonArray.getJSONObject(i).getString("handwasher_Password").toString();

                                    fname.setText(handwasher_FName);
                                    midname.setText(handwasher_MidName);
                                    lname.setText(handwasher_LName);
                                    address.setText(handwasher_Address);
                                    bdate.setText(handwasher_BDate);

                                    if(handwasher_Gender.equals("M")) {
                                        radioGender.check(R.id.radioMale);
                                    } else if(handwasher_Gender.equals("F")) {
                                        radioGender.check(R.id.radioFemale);
                                    }
                                    bdate.setText(handwasher_BDate);
                                    for(int j= 0; j < civilstatus.getAdapter().getCount(); j++)
                                    {
                                        if(civilstatus.getAdapter().getItem(j).toString().contains(handwasher_CivilStatus))
                                        {
                                            civilstatus.setSelection(j);
                                        }
                                    }
                                    phonenumber.setText(handwasher_Contact);
                                    email.setText(handwasher_Username);
                                    password.setText(handwasher_Password);

                                    fname.setEnabled(false);
                                    midname.setEnabled(false);
                                    lname.setEnabled(false);
                                    address.setEnabled(false);
                                    bdate.setEnabled(false);
                                    phonenumber.setEnabled(false);
                                    email.setEnabled(false);
                                    password.setEnabled(false);

                                }

                            } else if(success.equals("0")) {
                                Toast.makeText(HandwasherAccount.this, "error " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HandwasherAccount.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherAccount.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("handwasher_id", String.valueOf(handwasher_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
