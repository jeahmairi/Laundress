package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ClientAccountDetails extends AppCompatActivity {
    String client_name, genders;
    int client_id;
    EditText fname, midname, lname, address, bdate, contact, gender, email, oldpass, newpass;
    TextView tvfname, tvmidname, tvlname, tvaddress, tvbdate, tvcontact, tvgender, tvemail, tvoldpass, tvnewpass;
    Button btnupdate, btnsaveupdate, btnupdateaccount, btnsaveaccount;
    RadioButton radiogendermale, radiogenderfemale;
    RadioGroup radiogender;
    LinearLayout layoutgender;
    DatePickerDialog picker;
    private static final String URL_ALL ="http://192.168.254.117/laundress/laundryclientdetails.php";
    //private static final String URL_ALL ="http://192.168.1.12/laundress/laundryclientdetails.php";
    private static final String URL_UPDATE_PROFILE ="http://192.168.254.117/laundress/laundryclientupdateprofile.php";
    //private static final String URL_UPDATE_PROFILE ="http://192.168.1.12/laundress/laundryclientupdateprofile.php";
    private static final String URL_UPDATE_ACCOUNT ="http://192.168.254.117/laundress/laundryclientupdateaccount.php";
    //private static final String URL_UPDATE_ACCOUNT ="http://192.168.1.12/laundress/laundryclientupdateaccount.php";
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
        setContentView(R.layout.clientaccountdetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");
         layoutgender = findViewById(R.id.linearLayout1);

        fname = findViewById(R.id.firstname);
        midname = findViewById(R.id.middlename);
        lname = findViewById(R.id.lastname);
        address = findViewById(R.id.address);
        bdate = findViewById(R.id.birthdate);
        contact = findViewById(R.id.phonenumber);
        email = findViewById(R.id.emailadd);
        oldpass = findViewById(R.id.oldpassword);
        newpass = findViewById(R.id.newpassword);
        radiogender = findViewById(R.id.radioGender);

        tvfname = findViewById(R.id.fname);
        tvmidname = findViewById(R.id.midname);
        tvlname = findViewById(R.id.lname);
        tvaddress = findViewById(R.id.addr);
        tvbdate = findViewById(R.id.bdate);
        tvcontact = findViewById(R.id.phonenum);
        tvemail = findViewById(R.id.email);
        tvoldpass = findViewById(R.id.oldpass);
        tvnewpass = findViewById(R.id.newpass);
        tvgender = findViewById(R.id.gender);

        btnupdate = findViewById(R.id.updateprofile);
        btnsaveupdate = findViewById(R.id.saveprofile);
        btnupdateaccount = findViewById(R.id.updateaccount);
        btnsaveaccount = findViewById(R.id.saveaccount);

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ClientAccountDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                bdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname.setEnabled(true);
                midname.setEnabled(true);
                lname.setEnabled(true);
                address.setEnabled(true);
                bdate.setEnabled(true);
                contact.setEnabled(true);
                btnupdate.setVisibility(View.GONE);
                btnupdateaccount.setVisibility(View.GONE);
                btnsaveupdate.setVisibility(View.VISIBLE);

            }
        });

        btnsaveupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname.setEnabled(false);
                midname.setEnabled(false);
                lname.setEnabled(false);
                address.setEnabled(false);
                bdate.setEnabled(false);
                contact.setEnabled(false);
                btnupdate.setVisibility(View.VISIBLE);
                btnupdateaccount.setVisibility(View.VISIBLE);
                btnsaveupdate.setVisibility(View.GONE);

                updateProfile();
            }
        });

        btnupdateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname.setVisibility(View.GONE);
                midname.setVisibility(View.GONE);
                lname.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                bdate.setVisibility(View.GONE);
                contact.setVisibility(View.GONE);
                layoutgender.setVisibility(View.GONE);

                tvfname.setVisibility(View.GONE);
                tvmidname.setVisibility(View.GONE);
                tvlname.setVisibility(View.GONE);
                tvaddress.setVisibility(View.GONE);
                tvbdate.setVisibility(View.GONE);
                tvcontact.setVisibility(View.GONE);
                tvgender.setVisibility(View.GONE);

                email.setVisibility(View.VISIBLE);
                oldpass.setVisibility(View.VISIBLE);
                newpass.setVisibility(View.VISIBLE);
                tvemail.setVisibility(View.VISIBLE);
                tvoldpass.setVisibility(View.VISIBLE);
                tvnewpass.setVisibility(View.VISIBLE);

                btnupdate.setVisibility(View.GONE);
                btnupdateaccount.setVisibility(View.GONE);
                btnsaveupdate.setVisibility(View.GONE);
                btnsaveaccount.setVisibility(View.VISIBLE);

            }
        });

        btnsaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname.setVisibility(View.VISIBLE);
                midname.setVisibility(View.VISIBLE);
                lname.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                bdate.setVisibility(View.VISIBLE);
                contact.setVisibility(View.VISIBLE);
                layoutgender.setVisibility(View.VISIBLE);

                tvfname.setVisibility(View.VISIBLE);
                tvmidname.setVisibility(View.VISIBLE);
                tvlname.setVisibility(View.VISIBLE);
                tvaddress.setVisibility(View.VISIBLE);
                tvbdate.setVisibility(View.VISIBLE);
                tvcontact.setVisibility(View.VISIBLE);
                tvgender.setVisibility(View.VISIBLE);

                email.setVisibility(View.GONE);
                oldpass.setVisibility(View.GONE);
                newpass.setVisibility(View.GONE);
                tvemail.setVisibility(View.GONE);
                tvoldpass.setVisibility(View.GONE);
                tvnewpass.setVisibility(View.GONE);

                btnupdate.setVisibility(View.VISIBLE);
                btnupdateaccount.setVisibility(View.VISIBLE);
                btnsaveupdate.setVisibility(View.GONE);
                btnsaveaccount.setVisibility(View.GONE);

                updateAccount();
            }
        });
        //Toast.makeText(this, "name: "+client_name+"id: "+client_id, Toast.LENGTH_LONG).show();


        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            getJsonResponse(response);
                            System.out.println("RESPONSEesponse");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ClientAccountDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("client_id", String.valueOf(client_id));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ClientAccountDetails.this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {
            Toast.makeText(ClientAccountDetails.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioMale:
                if(checked)
                    genders = "M";
                break;
            case R.id.radioFemale:
                if(checked)
                    genders = "F";
                break;
        }
    }

    private void updateAccount() {
        final Context context = this;
        final String email = this.email.getText().toString().trim();
        final String old_password = this.oldpass.getText().toString().trim();
        final String new_password = this.newpass.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_ACCOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ClientAccountDetails.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();

                                /*Intent intent = new Intent(context, Login.class);
                                startActivity(intent);*/
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClientAccountDetails.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientAccountDetails.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("old_password", old_password);
                params.put("new_password", new_password);
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateProfile() {
        final Context context = this;
        final String fname = this.fname.getText().toString().trim();
        final String midname = this.midname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String addr = this.address.getText().toString().trim();
        final String bdate = this.bdate.getText().toString().trim();
        final String phonenumber = this.contact.getText().toString().trim();
        final String gender = this.genders.trim();
        //final String gender = this.radioButton.getText().toString().trim();
        //final String email = this.email.getText().toString().trim();
        //final String password = this.password.getText().toString().trim();
      //  Toast.makeText(ClientAccountDetails.this, "bdate" +bdate, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ClientAccountDetails.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();

                                /*Intent intent = new Intent(context, Login.class);
                                startActivity(intent);*/
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ClientAccountDetails.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientAccountDetails.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("midname", midname);
                params.put("lname", lname);
                params.put("addr", addr);
                params.put("bdate", bdate);
                params.put("gender", gender);
                params.put("phonenumber", phonenumber);
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("laundryclient");
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {
                    String client_fname=jsonArray.getJSONObject(i).getString("client_fname").toString();
                    String client_midname=jsonArray.getJSONObject(i).getString("client_midname").toString();
                    String client_lname=jsonArray.getJSONObject(i).getString("client_lname").toString();
                    String client_address=jsonArray.getJSONObject(i).getString("client_address").toString();
                    String client_contact=jsonArray.getJSONObject(i).getString("client_contact").toString();
                    String client_bdate=jsonArray.getJSONObject(i).getString("client_bdate").toString();
                    String client_gender=jsonArray.getJSONObject(i).getString("client_gender").toString();
                    String client_email=jsonArray.getJSONObject(i).getString("client_email").toString();

                    if(client_gender.equals("Male")) {
                        radiogender.check(R.id.radioMale);
                    } else if(client_gender.equals("Female")) {
                    radiogender.check(R.id.radioFemale);
                    }
                    fname.setText(client_fname);
                    fname.setEnabled(false);
                    midname.setText(client_midname);
                    midname.setEnabled(false);
                    lname.setText(client_lname);
                    lname.setEnabled(false);
                    address.setText(client_address);
                    address.setEnabled(false);
                    bdate.setText(client_bdate);
                    bdate.setEnabled(false);
                    contact.setText(client_contact);
                    contact.setEnabled(false);
                    email.setText(client_email);

                }
            } else if(success.equals("0")) {
                Toast.makeText(ClientAccountDetails.this, "no datas", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ClientAccountDetails.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
