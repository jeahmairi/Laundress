package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    private ProgressBar load;
    private static String URL_LOGIN = "http://192.168.254.117/laundress/login.php";
    //private static String URL_LOGIN = "http://192.168.1.5/laundress/login.php";
   // private static String URL_LOGIN = "http://192.168.1.7/laundress/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        load = findViewById(R.id.loading);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = email.getText().toString().trim();
                String mpass = password.getText().toString().trim();
                if(!memail.isEmpty() && !mpass.isEmpty()){
                    loginl(memail, mpass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }

            }
        });

    }

    private void loginl(final String email, final String pass){

        load.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        final Context context = this;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            //Toast.makeText(Login.this, "sud" + success, Toast.LENGTH_SHORT).show();
                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String user = object.getString("user").trim();
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    Toast.makeText(Login.this, "Login Successfully Your Name: " + name + "Your email: " + email, Toast.LENGTH_SHORT).show();
                                    load.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                    if(user.equals("laundryclient")) {
                                        Intent intent = new Intent(context, ClientHomepage.class);
                                        startActivity(intent);
                                    }
                                    else if(user.equals("Laundry Handwasher")) {
                                        Intent intent = new Intent(context, HandwasherHomepage.class);
                                        startActivity(intent);
                                    }
                                }
                            } else if (success.equals("0")) {

                                Toast.makeText(Login.this, "failed: " + email+ " " +pass, Toast.LENGTH_SHORT).show();
                                load.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Login failed " +e.toString(), Toast.LENGTH_SHORT).show();
                            load.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Login failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
