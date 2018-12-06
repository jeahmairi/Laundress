package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterLc extends AppCompatActivity {

    private EditText fname, midname, lname, addr;
    private EditText bdate, phonenumber, email, password;
    private RadioGroup radiogender;
    //private RadioButton gender;
    private String gender;
    private DatePickerDialog picker;
    private Button reg;
    private ProgressBar load;
   // private static String URL_REGISTER = "http://192.168.254.117/laundress/lcregister.php";
    private static String URL_REGISTER = "http://192.168.1.9/laundress/lcregister.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lcregistration);
        radiogender = findViewById(R.id.radioGender);
        load = findViewById(R.id.loading);
        fname= findViewById(R.id.fname);
        midname = findViewById(R.id.midname);
        lname = findViewById(R.id.lname);
        addr = findViewById(R.id.address);
        reg = findViewById(R.id.register);
        bdate = findViewById(R.id.bdate);
        phonenumber = findViewById(R.id.phonenumber);
       // gender = findViewById(radiogender.getCheckedRadioButtonId());
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        int selectedID = radiogender.getCheckedRadioButtonId();

        if(selectedID == R.id.radioMale)
        {
            gender = "Male";
        } else if(selectedID==R.id.radioFemale) {
            gender = "Female";
        }
        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegisterLc.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                bdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register(){

        load.setVisibility(View.VISIBLE);
        reg.setVisibility(View.GONE);
        final Context context = this;
        final String fname = this.fname.getText().toString().trim();
        final String midname = this.midname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String addr = this.addr.getText().toString().trim();
        final String bdate = this.bdate.getText().toString().trim();
        final String phonenumber = this.phonenumber.getText().toString().trim();
        final String gender = this.gender.trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if(success.equals("1")){
                            Toast.makeText(RegisterLc.this, "Registered Successfully ", Toast.LENGTH_SHORT).show();
                            load.setVisibility(View.GONE);
                            reg.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(context, Login.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();;
                        Toast.makeText(RegisterLc.this, "Register Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                        reg.setVisibility(View.VISIBLE);
                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterLc.this, "Register Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                        reg.setVisibility(View.VISIBLE);
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
               params.put("email", email);
               params.put("password", password);
               return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

