package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HandwasherAccount extends AppCompatActivity {
    EditText fname, midname, lname, address, bdate, phonenumber, email, oldpass, newpass;
    RadioGroup radioGender;
    RadioButton radioMale, radioFemale;
    Spinner civilstatus;
    DatePickerDialog picker;
    ImageView photo;
    String handwasher_name;
    int handwasher_id, handwasher_lspid;
    TextView tvgender, tvcvstat, tvemail, tvoldpass, tvnewpass;
    Button btnupdate, btnsaveupdate, btnupdateaccount, btnsaveaccount;

    private static final String URL_ALL ="http://192.168.254.113/laundress/handwasheraccount.php";
    private static final String URL_UPDATE_PROFILE ="http://192.168.254.113/laundress/handwasherupdateprofile.php";
    private static final String URL_UPDATE_ACCOUNT ="http://192.168.254.113/laundress/handwasherupdateaccount.php";
    private String genders;
    private Uri imagePath;
    private Bitmap bitmap;
    String cvlstat;

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

        photo = findViewById(R.id.photo);
        fname = findViewById(R.id.fname);
        midname = findViewById(R.id.midname);
        lname = findViewById(R.id.lname);
        address = findViewById(R.id.address);
        bdate = findViewById(R.id.bdate);
        phonenumber = findViewById(R.id.phonenumber);
        radioGender = findViewById(R.id.radioGender);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        civilstatus = findViewById(R.id.civilstatus);
        email = findViewById(R.id.emailadd);
        oldpass = findViewById(R.id.oldpassword);
        newpass = findViewById(R.id.newpassword);

        //Textview
        tvgender = findViewById(R.id.tvgender);
        tvcvstat = findViewById(R.id.tvcvstat);
        tvemail = findViewById(R.id.tvemail);
        tvoldpass = findViewById(R.id.tvoldpass);
        tvnewpass = findViewById(R.id.tvnewpass);

        btnupdate = findViewById(R.id.updateprofile);
        btnsaveupdate = findViewById(R.id.saveprofile);
        btnupdateaccount = findViewById(R.id.updateaccount);
        btnsaveaccount = findViewById(R.id.saveaccount);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        handwasher_name = extras.getString("handwasher_name");
        handwasher_id = extras.getInt("handwasher_id");
        handwasher_lspid = extras.getInt("handwasher_lspid");

        allHandwasher();
        photo.setEnabled(false);
        civilstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cvlstat = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, 1);
            }
        });

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(HandwasherAccount.this,
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
                phonenumber.setEnabled(true);
                civilstatus.setEnabled(true);
                radioGender.setEnabled(true);
                radioFemale.setEnabled(true);
                radioMale.setEnabled(true);
                btnupdate.setVisibility(View.GONE);
                btnupdateaccount.setVisibility(View.GONE);
                btnsaveupdate.setVisibility(View.VISIBLE);
                photo.setEnabled(true);
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
                phonenumber.setEnabled(false);
                civilstatus.setEnabled(false);
                radioGender.setEnabled(false);
                radioFemale.setEnabled(false);
                radioMale.setEnabled(false);
                btnupdate.setVisibility(View.VISIBLE);
                btnupdateaccount.setVisibility(View.VISIBLE);
                btnsaveupdate.setVisibility(View.GONE);
                photo.setEnabled(false);

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
                phonenumber.setVisibility(View.GONE);
                radioGender.setVisibility(View.GONE);
                civilstatus.setVisibility(View.GONE);
                tvgender.setVisibility(View.GONE);
                tvcvstat.setVisibility(View.GONE);

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
                phonenumber.setVisibility(View.VISIBLE);
                radioGender.setVisibility(View.VISIBLE);
                civilstatus.setVisibility(View.VISIBLE);
                tvgender.setVisibility(View.VISIBLE);
                tvcvstat.setVisibility(View.VISIBLE);

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
                                    String handwasher_Photo=jsonArray.getJSONObject(i).getString("handwasher_Photo").toString();
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
                                    //password.setText(handwasher_Password);

                                    fname.setEnabled(false);
                                    midname.setEnabled(false);
                                    lname.setEnabled(false);
                                    address.setEnabled(false);
                                    bdate.setEnabled(false);
                                    phonenumber.setEnabled(false);
                                    radioGender.setEnabled(false);
                                    civilstatus.setEnabled(false);
                                    radioFemale.setEnabled(false);
                                    radioMale.setEnabled(false);
                                    Picasso.get().load(handwasher_Photo).into(photo);
                                    //email.setEnabled(false);
                                    //password.setEnabled(false);

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

    private void updateProfile() {
        final Context context = this;
        final String fname = this.fname.getText().toString().trim();
        final String midname = this.midname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String addr = this.address.getText().toString().trim();
        final String bdate = this.bdate.getText().toString().trim();
        final String gender = this.genders.trim();
        final String cvlstat = this.cvlstat.trim();
        final String phonenumber = this.phonenumber.getText().toString().trim();

        final String profile = imageToString(bitmap);
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
                                Toast.makeText(HandwasherAccount.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();

                                /*Intent intent = new Intent(context, Login.class);
                                startActivity(intent);*/
                            } else if(success.equals("1")){
                                Toast.makeText(HandwasherAccount.this, "Update Failed " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(HandwasherAccount.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherAccount.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

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
                params.put("cvstat", cvlstat);
                params.put("phonenumber", phonenumber);
                params.put("profilepic", profile);
                params.put("handwasher_id", String.valueOf(handwasher_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void updateAccount() {
        final Context context = this;
        final String email = this.email.getText().toString().trim();
        final String old_password = this.oldpass.getText().toString();
        final String new_password = this.newpass.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_ACCOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(HandwasherAccount.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();

                                /*Intent intent = new Intent(context, Login.class);
                                startActivity(intent);*/
                            } else if(success.equals("1")){
                                Toast.makeText(HandwasherAccount.this, "Update Failed " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(HandwasherAccount.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HandwasherAccount.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("old_password", old_password);
                params.put("new_password", new_password);
                params.put("handwasher_id", String.valueOf(handwasher_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                imagePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
